package com.svedentsov.aqa.tasks.data_structures;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.*;

/**
 * Решение задачи №89: Реализовать простой кеш в памяти.
 * Реализует потокобезопасный кеш с поддержкой времени жизни (Time-To-Live, TTL)
 * для каждой записи и опциональной фоновой очисткой.
 * Описание: С использованием `Map`. (Проверяет: Map, основы кеширования)
 * Задание: Реализуйте простой класс `SimpleCache` с использованием `HashMap`
 * (здесь `ConcurrentHashMap` для потокобезопасности), который имеет методы
 * `put(String key, Object value)` и `get(String key)`. Добавьте базовую
 * логику очистки старых записей (например, по TTL).
 * Пример: `cache.put("user:1", userData); Object data = cache.get("user:1");`
 *
 * @param <K> Тип ключей кеша.
 * @param <V> Тип значений кеша.
 */
public class SimpleInMemoryCache<K, V> {

    // Основное хранилище кеша: потокобезопасная карта K -> CacheEntry<V>
    private final Map<K, CacheEntry<V>> cache = new ConcurrentHashMap<>();
    // Время жизни по умолчанию для записей в миллисекундах.
    private final long defaultTtlMillis;
    // Планировщик для фоновой очистки (опционально).
    private final ScheduledExecutorService cleanupScheduler;
    private final boolean backgroundCleanupEnabled;

    /**
     * Создает кеш с указанным временем жизни по умолчанию и опциональной фоновой очисткой.
     *
     * @param defaultTtlMillis        Время жизни записей по умолчанию в мс (> 0).
     * @param enableBackgroundCleanup {@code true} для запуска периодической фоновой очистки.
     * @throws IllegalArgumentException если defaultTtlMillis не положительное.
     */
    public SimpleInMemoryCache(long defaultTtlMillis, boolean enableBackgroundCleanup) {
        if (defaultTtlMillis <= 0) {
            throw new IllegalArgumentException("Default TTL must be positive.");
        }
        this.defaultTtlMillis = defaultTtlMillis;
        this.backgroundCleanupEnabled = enableBackgroundCleanup;

        if (enableBackgroundCleanup) {
            // Создаем фабрику потоков для именования и установки демона
            ThreadFactory threadFactory = r -> {
                Thread t = Executors.defaultThreadFactory().newThread(r);
                t.setDaemon(true); // Поток не должен мешать завершению JVM
                t.setName("SimpleCache-Cleanup-" + t.getId());
                return t;
            };
            // Создаем и запускаем планировщик
            this.cleanupScheduler = Executors.newSingleThreadScheduledExecutor(threadFactory);
            long cleanupInterval = Math.max(1000, defaultTtlMillis); // Интервал очистки (минимум 1 сек)
            this.cleanupScheduler.scheduleAtFixedRate(this::removeExpiredEntries,
                    cleanupInterval, cleanupInterval, TimeUnit.MILLISECONDS);
        } else {
            this.cleanupScheduler = null;
        }
    }

    /**
     * Создает кеш с TTL по умолчанию и без фоновой очистки.
     *
     * @param defaultTtlMillis Время жизни по умолчанию в мс (> 0).
     */
    public SimpleInMemoryCache(long defaultTtlMillis) {
        this(defaultTtlMillis, false);
    }

    /**
     * Добавляет/обновляет значение в кеше с TTL по умолчанию.
     *
     * @param key   Ключ (не null).
     * @param value Значение (не null).
     */
    public void put(K key, V value) {
        put(key, value, defaultTtlMillis);
    }

    /**
     * Добавляет/обновляет значение в кеше с указанным TTL.
     *
     * @param key       Ключ (не null).
     * @param value     Значение (не null).
     * @param ttlMillis Время жизни для этой записи в мс (<= 0 -> defaultTtlMillis).
     */
    public void put(K key, V value, long ttlMillis) {
        Objects.requireNonNull(key, "Cache key cannot be null");
        Objects.requireNonNull(value, "Cache value cannot be null");

        long effectiveTtl = (ttlMillis > 0) ? ttlMillis : defaultTtlMillis;
        cache.put(key, new CacheEntry<>(value, effectiveTtl));
    }

    /**
     * Получает значение из кеша по ключу.
     * Если запись устарела, она удаляется ("ленивое" удаление), и возвращается empty Optional.
     *
     * @param key Ключ для поиска (не null).
     * @return {@link Optional} со значением, если найдено и актуально, иначе empty Optional.
     */
    public Optional<V> get(K key) {
        Objects.requireNonNull(key, "Cache key cannot be null");

        CacheEntry<V> entry = cache.get(key);
        if (entry == null) {
            return Optional.empty(); // Не найдено
        }

        if (entry.isExpired()) {
            // Атомарно удаляем, только если значение не изменилось с момента чтения
            cache.remove(key, entry);
            return Optional.empty(); // Устарело
        }
        return Optional.of(entry.value); // Найдено и актуально
    }

    /**
     * Удаляет запись из кеша по ключу.
     *
     * @param key Ключ для удаления (не null).
     */
    public void remove(K key) {
        Objects.requireNonNull(key, "Cache key cannot be null");
        cache.remove(key);
    }

    /**
     * Очищает весь кеш.
     */
    public void clear() {
        cache.clear();
    }

    /**
     * Возвращает текущее количество записей (может включать устаревшие).
     *
     * @return Количество записей.
     */
    public int size() {
        // Для точного размера актуальных записей вызвать removeExpiredEntries() перед size()
        return cache.size();
    }

    /**
     * Внутренний класс для хранения записи кеша: значение и время устаревания.
     * Сделан static, т.к. не требует доступа к полям внешнего класса Cache.
     */
    private static class CacheEntry<V> {
        final V value;
        final Instant expiryTime; // Время устаревания UTC

        CacheEntry(V value, long ttlMillis) {
            this.value = value;
            // Рассчитываем время устаревания от текущего момента
            this.expiryTime = Instant.now().plusMillis(ttlMillis);
        }

        /**
         * Проверяет, истекло ли время жизни записи.
         */
        boolean isExpired() {
            return !Instant.now().isBefore(expiryTime); // Истекло, если now >= expiryTime
        }

        @Override
        public String toString() {
            return "CacheEntry{value=" + value + ", expires=" + expiryTime + '}';
        }
    }

    /**
     * Принудительно удаляет все устаревшие записи из кеша.
     * Может быть вызван вручную или фоновым потоком.
     */
    public void removeExpiredEntries() {
        if (backgroundCleanupEnabled) { // Добавим лог только если включена фоновая очистка
            System.out.println("[Cache Cleanup] Checking for expired entries... Size before: " + cache.size());
        }
        // removeIf - потокобезопасный способ удаления из ConcurrentHashMap во время итерации
        cache.entrySet().removeIf(entry -> entry.getValue().isExpired());
        if (backgroundCleanupEnabled) {
            System.out.println("[Cache Cleanup] Cleanup finished. Size after: " + cache.size());
        }
    }

    /**
     * Останавливает фоновый поток очистки, если он был запущен.
     */
    public void shutdownCleanup() {
        if (cleanupScheduler != null && !cleanupScheduler.isShutdown()) {
            System.out.println("[Cache] Shutting down cleanup scheduler...");
            cleanupScheduler.shutdown();
            try {
                if (!cleanupScheduler.awaitTermination(1, TimeUnit.SECONDS)) {
                    cleanupScheduler.shutdownNow();
                }
                System.out.println("[Cache] Cleanup scheduler shut down.");
            } catch (InterruptedException e) {
                cleanupScheduler.shutdownNow();
                Thread.currentThread().interrupt();
                System.err.println("[Cache] Cleanup shutdown interrupted.");
            }
        }
    }
}
