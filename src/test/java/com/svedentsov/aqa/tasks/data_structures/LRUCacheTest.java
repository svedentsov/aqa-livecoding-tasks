package com.svedentsov.aqa.tasks.data_structures;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для LRUCache")
class LRUCacheTest {

    @Nested
    @DisplayName("Тесты конструктора")
    class ConstructorTests {
        @ParameterizedTest(name = "Емкость {0} -> IllegalArgumentException")
        @ValueSource(ints = {0, -1, -100})
        @DisplayName("Должен выбрасывать IllegalArgumentException для не положительной емкости")
        void constructor_shouldThrowIllegalArgumentException_forNonPositiveCapacity(int invalidCapacity) {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
                new LRUCache<>(invalidCapacity);
            });
            assertTrue(ex.getMessage().contains("Capacity must be positive"));
        }

        @Test
        @DisplayName("Успешное создание с положительной емкостью")
        void constructor_shouldCreateInstance_forPositiveCapacity() {
            assertDoesNotThrow(() -> new LRUCache<>(1), "Создание с capacity=1");
            assertDoesNotThrow(() -> new LRUCache<>(10), "Создание с capacity=10");
        }
    }

    @Nested
    @DisplayName("Операции put и get")
    class PutAndGetTests {
        @Test
        @DisplayName("put и get должны корректно работать в пределах емкости")
        void putAndGet_withinCapacity() {
            LRUCache<Integer, String> cache = new LRUCache<>(2);
            assertNull(cache.put(1, "one"), "put должен вернуть null для нового ключа");
            assertEquals("one", cache.put(1, "one_updated"), "put должен вернуть старое значение для существующего ключа");

            cache.put(2, "two");

            assertEquals("one_updated", cache.get(1));
            assertEquals("two", cache.get(2));
            assertEquals(2, cache.size(), "Размер кеша должен быть 2");
        }

        @Test
        @DisplayName("get несуществующего ключа должен возвращать null")
        void get_nonExistentKey_shouldReturnNull() {
            LRUCache<Integer, String> cache = new LRUCache<>(2);
            cache.put(1, "one");
            assertNull(cache.get(2), "get для несуществующего ключа должен вернуть null");
        }

        @Test
        @DisplayName("get должен обновлять порядок использования элемента (делая его самым свежим)")
        void get_shouldUpdateAccessOrder() {
            LRUCache<Integer, Integer> cache = new LRUCache<>(2);
            cache.put(1, 10); // Порядок доступа: 1 (MRU)
            cache.put(2, 20); // Порядок доступа: 1, 2 (2 MRU)

            assertEquals(10, cache.get(1)); // Доступ к 1. Порядок доступа: 2, 1 (1 MRU)

            cache.put(3, 30); // Добавляем 3. Емкость 2. Вытесняется 2 (LRU).
            // Порядок доступа: 1, 3 (3 MRU)

            assertNull(cache.get(2), "Элемент 2 должен быть вытеснен");
            assertEquals(10, cache.get(1)); // 1 все еще в кеше
            assertEquals(30, cache.get(3)); // 3 в кеше
            assertEquals(2, cache.size(), "Размер кеша должен быть 2 после вытеснения");

            // Проверка порядка (самый старый -> самый новый)
            List<Integer> keysInOrder = new ArrayList<>(cache.keySet());
            assertIterableEquals(List.of(1, 3), keysInOrder, "Порядок ключей после операций должен быть [1, 3]");
        }


        @Test
        @DisplayName("put должен вытеснять самый старый элемент при превышении емкости")
        void put_shouldEvictLeastRecentlyUsed_whenCapacityExceeded() {
            LRUCache<String, Integer> cache = new LRUCache<>(2);
            cache.put("A", 1); // Порядок доступа: A
            cache.put("B", 2); // Порядок доступа: A, B
            cache.put("C", 3); // Вытесняет A. Порядок доступа: B, C

            assertNull(cache.get("A"), "Элемент A должен быть вытеснен");
            assertEquals(2, cache.get("B")); // B становится MRU
            // Порядок доступа после get("B"): C, B
            assertEquals(3, cache.get("C")); // C становится MRU
            // Порядок доступа после get("C"): B, C
            assertEquals(2, cache.size());
        }

        @Test
        @DisplayName("put существующего ключа должен обновлять значение и порядок")
        void put_existingKey_shouldUpdateValueAndUpdateAccessOrder() {
            LRUCache<Integer, String> cache = new LRUCache<>(2);
            cache.put(1, "one");    // Порядок: 1
            cache.put(2, "two");    // Порядок: 1, 2

            cache.put(1, "one_v2"); // Обновляем 1. Он становится MRU. Порядок: 2, 1

            assertEquals("one_v2", cache.get(1)); // Доступ к 1, он остается MRU. Порядок: 2, 1
            assertEquals(2, cache.size());

            cache.put(3, "three");  // Добавляем 3. Он MRU. Вытесняется 2 (LRU). Порядок: 1, 3

            assertNull(cache.get(2), "Элемент 2 должен быть вытеснен");
            assertEquals("one_v2", cache.get(1)); // 1 остается
            assertEquals("three", cache.get(3));  // 3 добавлен
            List<Integer> keysInOrder = new ArrayList<>(cache.keySet());
            assertIterableEquals(List.of(1, 3), keysInOrder, "Порядок ключей после операций должен быть [1, 3]");
        }
    }

    @Nested
    @DisplayName("Тесты для кеша с емкостью 1")
    class CapacityOneTests {
        @Test
        @DisplayName("put и get для емкости 1")
        void capacityOne_putAndGetBehavior() {
            LRUCache<Integer, String> cache = new LRUCache<>(1);

            cache.put(1, "one"); // Cache: {1="one"}
            assertEquals("one", cache.get(1), "Значение для ключа 1");
            assertEquals(1, cache.size());

            cache.put(2, "two"); // Вытесняет 1. Cache: {2="two"}
            assertNull(cache.get(1), "Ключ 1 должен быть вытеснен");
            assertEquals("two", cache.get(2), "Значение для ключа 2");
            assertEquals(1, cache.size());

            assertEquals("two", cache.get(2)); // Доступ к 2, он остается. Cache: {2="two"}
            assertEquals(1, cache.size());

            cache.put(3, "three"); // Вытесняет 2. Cache: {3="three"}
            assertNull(cache.get(2), "Ключ 2 должен быть вытеснен");
            assertEquals("three", cache.get(3), "Значение для ключа 3");
            assertEquals(1, cache.size());
        }
    }

    @Nested
    @DisplayName("Проверка порядка элементов (LRU) по шагам из примера")
    class LruOrderVerificationFromExampleTests {

        @Test
        @DisplayName("Последовательность операций из примера LeetCode")
        void leetCodeExampleSequence() {
            LRUCache<Integer, Integer> cache = new LRUCache<>(2);
            List<Integer> keys;

            // cache.put(1, 1); -> {1=1} (1 MRU)
            cache.put(1, 1);
            keys = new ArrayList<>(cache.keySet());
            assertIterableEquals(List.of(1), keys, "После put(1,1)");

            // cache.put(2, 2); -> {1=1, 2=2} (2 MRU, 1 LRU)
            cache.put(2, 2);
            keys = new ArrayList<>(cache.keySet());
            assertIterableEquals(List.of(1, 2), keys, "После put(2,2)");

            // cache.get(1); -> возвращает 1. {2=2, 1=1} (1 MRU, 2 LRU)
            assertEquals(1, cache.get(1));
            keys = new ArrayList<>(cache.keySet());
            assertIterableEquals(List.of(2, 1), keys, "После get(1)");

            // cache.put(3, 3); -> вытесняет 2. {1=1, 3=3} (3 MRU, 1 LRU)
            cache.put(3, 3);
            keys = new ArrayList<>(cache.keySet());
            assertIterableEquals(List.of(1, 3), keys, "После put(3,3), 2 вытеснен");
            assertNull(cache.get(2), "Ключ 2 должен быть вытеснен");

            // cache.get(2); -> возвращает null. {1=1, 3=3} (порядок не меняется)
            assertNull(cache.get(2));
            keys = new ArrayList<>(cache.keySet());
            assertIterableEquals(List.of(1, 3), keys, "После get(2) (miss)");

            // cache.put(4, 4); -> вытесняет 1. {3=3, 4=4} (4 MRU, 3 LRU)
            cache.put(4, 4);
            keys = new ArrayList<>(cache.keySet());
            assertIterableEquals(List.of(3, 4), keys, "После put(4,4), 1 вытеснен");
            assertNull(cache.get(1), "Ключ 1 должен быть вытеснен");

            // cache.get(1); -> возвращает null. {3=3, 4=4} (порядок не меняется)
            assertNull(cache.get(1));
            keys = new ArrayList<>(cache.keySet());
            assertIterableEquals(List.of(3, 4), keys, "После get(1) (miss)");

            // cache.get(3); -> возвращает 3. {4=4, 3=3} (3 MRU, 4 LRU)
            assertEquals(3, cache.get(3));
            keys = new ArrayList<>(cache.keySet());
            assertIterableEquals(List.of(4, 3), keys, "После get(3)");

            // cache.get(4); -> возвращает 4. {3=3, 4=4} (4 MRU, 3 LRU)
            assertEquals(4, cache.get(4));
            keys = new ArrayList<>(cache.keySet());
            assertIterableEquals(List.of(3, 4), keys, "После get(4)");
        }
    }
}
