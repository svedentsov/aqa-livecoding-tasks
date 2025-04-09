package com.svedentsov.aqa.tasks.data_structures;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Решение задачи №89: Реализовать простой кеш в памяти.
 * Эта реализация представляет потокобезопасный кеш с поддержкой времени жизни (Time-To-Live, TTL) для каждой записи.
 *
 * @param <K> Тип ключей кеша.
 * @param <V> Тип значений кеша.
 */
public class Task89_SimpleInMemoryCache<K, V> {

    /**
     * Внутренний класс для хранения записи кеша: значение и время устаревания.
     *
     * @param <V> Тип значения.
     */
    private static class CacheEntry<V> {
        final V value;          // Хранимое значение
        final Instant expiryTime; // Момент времени UTC, когда запись станет невалидной

        /**
         * Создает запись кеша с заданным значением и временем жизни.
         *
         * @param value     Значение для кеширования.
         * @param ttlMillis Время жизни (TTL) в миллисекундах с момента создания.
         */
        CacheEntry(V value, long ttlMillis) {
            this.value = value;
            this.expiryTime = Instant.now().plusMillis(ttlMillis);
        }

        /**
         * Проверяет, истекло ли время жизни записи.
         *
         * @return true, если запись устарела (текущее время >= времени устаревания), иначе false.
         */
        boolean isExpired() {
            // Используем isAfter() или isEqual() для проверки
            return !Instant.now().isBefore(expiryTime); // true если now >= expiryTime
        }

        @Override
        public String toString() {
            return "CacheEntry{value=" + value + ", expiryTime=" + expiryTime + '}';
        }
    }

    // Основное хранилище кеша. ConcurrentHashMap потокобезопасен для основных операций.
    private final Map<K, CacheEntry<V>> cache = new ConcurrentHashMap<>();
    // Время жизни по умолчанию для записей в миллисекундах.
    private final long defaultTtlMillis;
    // Планировщик для выполнения фоновой задачи очистки устаревших записей (опционально).
    private final ScheduledExecutorService cleanupScheduler;

    /**
     * Создает кеш с указанным временем жизни по умолчанию для записей
     * и опционально запускает фоновую очистку.
     *
     * @param defaultTtlMillis        Время жизни записей по умолчанию в миллисекундах (> 0).
     * @param enableBackgroundCleanup {@code true} для запуска периодической фоновой очистки.
     * @throws IllegalArgumentException если defaultTtlMillis не положительное.
     */
    public Task89_SimpleInMemoryCache(long defaultTtlMillis, boolean enableBackgroundCleanup) {
        if (defaultTtlMillis <= 0) {
            throw new IllegalArgumentException("Default TTL must be positive.");
        }
        this.defaultTtlMillis = defaultTtlMillis;

        if (enableBackgroundCleanup) {
            // Создаем планировщик с одним потоком-демоном
            this.cleanupScheduler = Executors.newSingleThreadScheduledExecutor(r -> {
                Thread t = Executors.defaultThreadFactory().newThread(r);
                t.setDaemon(true);
                t.setName("SimpleCache-Cleanup-Thread");
                return t;
            });
            // Запускаем задачу очистки с фиксированной задержкой (например, равной TTL)
            // initialDelay = defaultTtlMillis, period = defaultTtlMillis
            this.cleanupScheduler.scheduleAtFixedRate(this::removeExpiredEntries,
                    defaultTtlMillis, defaultTtlMillis, TimeUnit.MILLISECONDS);
            System.out.println("[Cache] Background cleanup task scheduled every " + defaultTtlMillis + "ms.");
        } else {
            this.cleanupScheduler = null;
        }
    }

    /**
     * Создает кеш с TTL по умолчанию и без фоновой очистки.
     *
     * @param defaultTtlMillis Время жизни по умолчанию в миллисекундах (> 0).
     */
    public Task89_SimpleInMemoryCache(long defaultTtlMillis) {
        this(defaultTtlMillis, false); // Фоновая очистка отключена
    }

    /**
     * Добавляет или обновляет значение в кеше с TTL по умолчанию.
     *
     * @param key   Ключ (не null).
     * @param value Значение (не null).
     */
    public void put(K key, V value) {
        put(key, value, defaultTtlMillis);
    }

    /**
     * Добавляет или обновляет значение в кеше с указанным TTL.
     *
     * @param key       Ключ (не null).
     * @param value     Значение (не null).
     * @param ttlMillis Время жизни для этой записи в миллисекундах (если <= 0, используется defaultTtlMillis).
     * @throws NullPointerException если key или value равны null.
     */
    public void put(K key, V value, long ttlMillis) {
        Objects.requireNonNull(key, "Cache key cannot be null");
        Objects.requireNonNull(value, "Cache value cannot be null");

        long effectiveTtl = (ttlMillis > 0) ? ttlMillis : defaultTtlMillis;
        // Создаем новую запись кеша
        CacheEntry<V> entry = new CacheEntry<>(value, effectiveTtl);
        // Помещаем в потокобезопасную карту
        cache.put(key, entry);
    }

    /**
     * Получает значение из кеша по ключу.
     * Если запись существует, но устарела, она удаляется из кеша ("ленивое" удаление),
     * и метод возвращает пустой Optional.
     *
     * @param key Ключ для поиска (не null).
     * @return {@link Optional}, содержащий значение, если оно найдено и не устарело,
     * иначе пустой {@code Optional}.
     * @throws NullPointerException если key равен null.
     */
    public Optional<V> get(K key) {
        Objects.requireNonNull(key, "Cache key cannot be null");

        CacheEntry<V> entry = cache.get(key);

        // Если записи с таким ключом нет
        if (entry == null) {
            return Optional.empty();
        }

        // Если запись найдена, но она устарела
        if (entry.isExpired()) {
            // Удаляем устаревшую запись атомарно: только если значение все еще то же самое
            // (на случай, если другой поток успел обновить запись между get() и isExpired()).
            cache.remove(key, entry);
            // System.out.println("[Cache] Lazy removal of expired key: " + key);
            return Optional.empty(); // Возвращаем пустой результат
        }

        // Запись найдена и валидна, возвращаем значение
        return Optional.of(entry.value);
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
     * Очищает весь кеш, удаляя все записи.
     */
    public void clear() {
        cache.clear();
    }

    /**
     * Возвращает текущее количество записей в кеше.
     * ВНИМАНИЕ: Может включать устаревшие записи, если не было вызовов `get`
     * или `removeExpiredEntries` для них.
     *
     * @return Количество записей в кеше.
     */
    public long size() {
        // Для получения точного размера актуальных записей, можно сначала вызвать очистку.
        // removeExpiredEntries();
        return cache.size();
    }

    /**
     * Принудительно удаляет все устаревшие записи из кеша.
     * Проходит по всем записям и удаляет те, у которых истек срок жизни.
     * Может быть вызван вручную или фоновым потоком.
     */
    public void removeExpiredEntries() {
        // System.out.println("[Cache] Running explicit cleanup...");
        // removeIf у ConcurrentHashMap - итеративный и потокобезопасный
        cache.entrySet().removeIf(mapEntry -> mapEntry.getValue().isExpired());
        // System.out.println("[Cache] Cleanup finished. Size: " + cache.size());
    }

    /**
     * Останавливает фоновый поток очистки, если он был запущен.
     * Важно вызывать при завершении работы приложения, использующего кеш с фоновой очисткой.
     */
    public void shutdownCleanup() {
        if (cleanupScheduler != null && !cleanupScheduler.isShutdown()) {
            cleanupScheduler.shutdown();
            try {
                // Ожидаем завершения текущей задачи очистки (опционально)
                if (!cleanupScheduler.awaitTermination(1, TimeUnit.SECONDS)) {
                    cleanupScheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                cleanupScheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
            System.out.println("[Cache] Background cleanup task shut down.");
        }
    }

    /**
     * Точка входа для демонстрации работы SimpleTTLCache.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) throws InterruptedException {
        // Кеш с TTL по умолчанию 1 секунда (1000 мс), без фоновой очистки
        System.out.println("--- Cache Demo (Default TTL = 1000ms, No Background Cleanup) ---");
        Task89_SimpleInMemoryCache<String, Integer> cache = new Task89_SimpleInMemoryCache<>(1000);

        System.out.println("Put('A', 100, 500ms)");
        cache.put("A", 100, 500);
        System.out.println("Put('B', 200)"); // Использует default TTL = 1000ms
        cache.put("B", 200);
        System.out.println("Put('C', 300, 1500ms)");
        cache.put("C", 300, 1500);
        System.out.println("Initial Size: " + cache.size()); // 3

        System.out.println("\nGet 'A': " + cache.get("A")); // Optional[100]
        System.out.println("Get 'B': " + cache.get("B")); // Optional[200]
        System.out.println("Get 'C': " + cache.get("C")); // Optional[300]

        System.out.println("\nWaiting 600ms...");
        Thread.sleep(600);

        System.out.println("Get 'A' after 600ms: " + cache.get("A")); // Optional.empty (TTL 500ms истек)
        System.out.println("Get 'B' after 600ms: " + cache.get("B")); // Optional[200] (TTL 1000ms)
        System.out.println("Get 'C' after 600ms: " + cache.get("C")); // Optional[300] (TTL 1500ms)
        System.out.println("Size after gets (A removed lazily): " + cache.size()); // 2

        System.out.println("\nWaiting 500ms more (total 1100ms)...");
        Thread.sleep(500);

        System.out.println("Get 'B' after 1100ms: " + cache.get("B")); // Optional.empty (TTL 1000ms истек)
        System.out.println("Get 'C' after 1100ms: " + cache.get("C")); // Optional[300] (TTL 1500ms)
        System.out.println("Size after gets (B removed lazily): " + cache.size()); // 1

        System.out.println("\nWaiting 500ms more (total 1600ms)...");
        Thread.sleep(500);

        System.out.println("Get 'C' after 1600ms: " + cache.get("C")); // Optional.empty (TTL 1500ms истек)
        System.out.println("Size after gets (C removed lazily): " + cache.size()); // 0

        // Демонстрация ручной очистки
        System.out.println("\n--- Manual Cleanup Demo ---");
        cache.put("X", 1, 100);
        cache.put("Y", 2, 1000);
        System.out.println("Cache state before wait: Size=" + cache.size() + ", Get X:" + cache.get("X") + ", Get Y:" + cache.get("Y"));
        System.out.println("Waiting 150ms...");
        Thread.sleep(150);
        System.out.println("Cache size before cleanup (X expired but not removed): " + cache.size()); // 2
        System.out.println("Running manual cleanup...");
        cache.removeExpiredEntries();
        System.out.println("Cache size after cleanup: " + cache.size()); // 1
        System.out.println("Get 'X' after cleanup: " + cache.get("X")); // Optional.empty
        System.out.println("Get 'Y' after cleanup: " + cache.get("Y")); // Optional[2]
    }
}
