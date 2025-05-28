package com.svedentsov.aqa.tasks.data_structures;

import org.junit.jupiter.api.*;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для SimpleInMemoryCache")
class SimpleInMemoryCacheTest {

    private SimpleInMemoryCache<String, Object> cache;
    private final long DEFAULT_TTL = 200; // ms
    private final long SHORT_TTL = 50; // ms
    private final long LONG_TTL = 500; // ms

    // Helper to wait, encapsulating Thread.sleep
    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            fail("Тест прерван во время ожидания: " + e.getMessage());
        }
    }

    @Nested
    @DisplayName("Кеш без фоновой очистки")
    class CacheWithoutBackgroundCleanup {

        @BeforeEach
        void setUp() {
            cache = new SimpleInMemoryCache<>(DEFAULT_TTL, false);
        }

        @AfterEach
        void tearDown() {
            if (cache != null) {
                cache.shutdownCleanup(); // Хотя здесь нет планировщика, вызов безопасен
            }
        }

        @Test
        @DisplayName("put и get существующего ключа")
        void putAndGet_existingKey_shouldReturnValue() {
            String key = "key1";
            Object value = "value1";
            cache.put(key, value);
            Optional<Object> retrieved = cache.get(key);
            assertTrue(retrieved.isPresent(), "Значение должно присутствовать");
            assertEquals(value, retrieved.get(), "Полученное значение должно совпадать");
        }

        @Test
        @DisplayName("get несуществующего ключа")
        void get_nonExistingKey_shouldReturnEmptyOptional() {
            Optional<Object> retrieved = cache.get("nonExistingKey");
            assertFalse(retrieved.isPresent(), "Optional должен быть пустым для несуществующего ключа");
        }

        @Test
        @DisplayName("put обновляет существующий ключ")
        void put_existingKey_shouldUpdateValue() {
            String key = "key1";
            cache.put(key, "value1");
            cache.put(key, "value2"); // Обновление
            Optional<Object> retrieved = cache.get(key);
            assertEquals("value2", retrieved.get(), "Значение должно быть обновлено");
        }

        @Test
        @DisplayName("get удаляет устаревшую запись (ленивое удаление)")
        void get_expiredEntry_shouldReturnEmptyAndRemove() {
            String key = "expiredKey";
            cache.put(key, "value", SHORT_TTL);
            assertEquals(1, cache.size());
            sleep(SHORT_TTL + 50); // Ждем дольше TTL
            Optional<Object> retrieved = cache.get(key);
            assertFalse(retrieved.isPresent(), "Устаревшая запись должна быть удалена при get");
            assertEquals(0, cache.size(), "Размер кеша должен уменьшиться после ленивого удаления");
        }

        @Test
        @DisplayName("get не удаляет еще не устаревшую запись")
        void get_notYetExpiredEntry_shouldReturnValue() {
            String key = "activeKey";
            cache.put(key, "value", LONG_TTL);
            sleep(LONG_TTL / 2);
            Optional<Object> retrieved = cache.get(key);
            assertTrue(retrieved.isPresent());
            assertEquals(1, cache.size());
        }

        @Test
        @DisplayName("remove удаляет запись")
        void remove_existingKey_shouldRemoveEntry() {
            String key = "keyToRemove";
            cache.put(key, "value");
            cache.remove(key);
            assertFalse(cache.get(key).isPresent(), "Запись должна быть удалена");
            assertEquals(0, cache.size());
        }

        @Test
        @DisplayName("clear очищает весь кеш")
        void clear_shouldRemoveAllEntries() {
            cache.put("key1", "v1");
            cache.put("key2", "v2");
            cache.clear();
            assertEquals(0, cache.size(), "Кеш должен быть пуст после clear");
            assertFalse(cache.get("key1").isPresent());
        }

        @Test
        @DisplayName("size возвращает корректное количество элементов (до очистки устаревших)")
        void size_shouldReturnCurrentEntryCount() {
            assertEquals(0, cache.size());
            cache.put("k1", "v1");
            cache.put("k2", "v2", SHORT_TTL);
            assertEquals(2, cache.size());
            sleep(SHORT_TTL + 50);
            // Размер все еще 2 до вызова get или removeExpiredEntries
            assertEquals(2, cache.size(), "Размер должен оставаться прежним до ленивого или активного удаления");
        }

        @Test
        @DisplayName("removeExpiredEntries удаляет устаревшие и оставляет активные")
        void removeExpiredEntries_shouldCleanupCorrectly() {
            cache.put("expired1", "v", SHORT_TTL);
            cache.put("active1", "v", LONG_TTL);
            cache.put("expired2", "v", SHORT_TTL);
            assertEquals(3, cache.size());

            sleep(SHORT_TTL + 50); // Ждем устаревания short_ttl записей
            cache.removeExpiredEntries();

            assertEquals(1, cache.size(), "Должна остаться одна активная запись");
            assertFalse(cache.get("expired1").isPresent());
            assertTrue(cache.get("active1").isPresent());
        }

        @Test
        @DisplayName("Конструктор с невалидным TTL должен выбросить IllegalArgumentException")
        void constructor_invalidTtl_shouldThrowException() {
            assertThrows(IllegalArgumentException.class, () -> new SimpleInMemoryCache<>(0, false));
            assertThrows(IllegalArgumentException.class, () -> new SimpleInMemoryCache<>(-100, false));
        }

        @Test
        @DisplayName("put с null ключом или значением должен выбросить NullPointerException")
        void put_nullKeyOrValue_shouldThrowNullPointerException() {
            assertThrows(NullPointerException.class, () -> cache.put(null, "value"));
            assertThrows(NullPointerException.class, () -> cache.put("key", null));
        }

        @Test
        @DisplayName("get с null ключом должен выбросить NullPointerException")
        void get_nullKey_shouldThrowNullPointerException() {
            assertThrows(NullPointerException.class, () -> cache.get(null));
        }

        @Test
        @DisplayName("remove с null ключом должен выбросить NullPointerException")
        void remove_nullKey_shouldThrowNullPointerException() {
            assertThrows(NullPointerException.class, () -> cache.remove(null));
        }
    }

    @Nested
    @DisplayName("Кеш с фоновой очисткой")
    class CacheWithBackgroundCleanup {
        // Используем более короткий TTL и интервал очистки для тестов
        private final long BG_DEFAULT_TTL = 150; // ms
        private final long BG_SHORT_TTL = 80;   // ms
        private final long BG_CLEANUP_INTERVAL_APPROX = 100; // ms (min for scheduler in class)


        @BeforeEach
        void setUp() {
            // Интервал очистки в SimpleInMemoryCache будет Math.max(100, Math.min(BG_DEFAULT_TTL, 1000))
            // В данном случае 100мс
            cache = new SimpleInMemoryCache<>(BG_DEFAULT_TTL, true);
        }

        @AfterEach
        void tearDown() {
            if (cache != null) {
                cache.shutdownCleanup();
            }
        }

        @Test
        @Timeout(value = 2, unit = TimeUnit.SECONDS) // Таймаут на весь тест, чтобы не зависнуть
        @DisplayName("Фоновая очистка должна удалять устаревшие записи")
        void backgroundCleanup_shouldRemoveExpiredEntries() {
            cache.put("key1_short_ttl", "v1", BG_SHORT_TTL);      // Устареет через 80ms
            cache.put("key2_default_ttl", "v2");                  // Устареет через 150ms
            cache.put("key3_long_ttl", "v3", BG_DEFAULT_TTL * 3); // Устареет через 450ms
            assertEquals(3, cache.size());

            // Ждем немного дольше, чем BG_SHORT_TTL, чтобы key1_short_ttl устарел
            // и чтобы планировщик успел сработать хотя бы раз (интервал ~100ms).
            sleep(BG_SHORT_TTL + BG_CLEANUP_INTERVAL_APPROX + 50); // ~80 + 100 + 50 = 230ms

            // key1_short_ttl должен быть удален фоновой задачей.
            // key2_default_ttl (150ms) также должен быть удален, т.к. прошло 230ms.
            // key3_long_ttl (450ms) должен остаться.
            Optional<Object> key1Opt = cache.get("key1_short_ttl"); // get может вызвать ленивое удаление, если фон не успел
            Optional<Object> key2Opt = cache.get("key2_default_ttl");

            assertFalse(key1Opt.isPresent(), "key1_short_ttl должен быть удален");
            assertFalse(key2Opt.isPresent(), "key2_default_ttl должен быть удален");
            assertTrue(cache.get("key3_long_ttl").isPresent(), "key3_long_ttl должен остаться");

            // Размер кеша может быть неточным из-за конкурентности get и фоновой очистки,
            // но после get вызовов, устаревшие точно будут удалены.
            int currentSize = cache.size();
            assertTrue(currentSize <= 1, "Размер кеша должен быть не более 1 (только key3 или пуст), актуальный: " + currentSize);

            // Ждем еще, чтобы key3_long_ttl устарел и был удален
            sleep(BG_DEFAULT_TTL * 3); // Ждем еще ~450ms. Общее время > 450ms + 230ms

            Optional<Object> key3Opt = cache.get("key3_long_ttl");
            assertFalse(key3Opt.isPresent(), "key3_long_ttl должен быть удален после длительного ожидания");
            currentSize = cache.size();
            assertTrue(currentSize == 0, "Размер кеша должен быть 0, актуальный: " + currentSize);
        }

        @Test
        @DisplayName("shutdownCleanup останавливает планировщик")
        void shutdownCleanup_shouldStopScheduler() {
            // Просто проверяем, что вызов не приводит к ошибке и планировщик останавливается.
            // Сложно проверить, что поток действительно остановлен без глубокой рефлексии
            // или изменения класса для тестирования.
            assertDoesNotThrow(() -> cache.shutdownCleanup());
            // Повторный вызов должен быть безопасен
            assertDoesNotThrow(() -> cache.shutdownCleanup());
        }
    }
}
