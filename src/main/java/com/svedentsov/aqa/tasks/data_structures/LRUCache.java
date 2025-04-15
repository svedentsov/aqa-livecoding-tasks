package com.svedentsov.aqa.tasks.data_structures;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Решение задачи №61: Реализация LRU Cache (Least Recently Used Cache).
 * Использует {@link LinkedHashMap} для эффективной реализации кеша.
 * <p>
 * Описание: (Проверяет: структуры данных - LinkedHashMap или комбинация Map + DoublyLinkedList)
 * <p>
 * Задание: Спроектируйте и реализуйте структуру данных `LRUCache`. Она должна
 * поддерживать операции `get(key)` (возвращает значение по ключу или null/спец.значение,
 * если ключ не найден, и делает этот ключ самым свежим) и `put(key, value)`
 * (добавляет/обновляет ключ-значение и делает его самым свежим). При достижении
 * максимальной емкости (`capacity`), перед добавлением нового элемента, должен
 * удаляться наименее недавно использовавшийся элемент (самый старый).
 * <p>
 * Пример:
 * `LRUCache cache = new LRUCache(2);`
 * `cache.put(1, 1);`
 * `cache.put(2, 2);`
 * `cache.get(1);`       // Возвращает 1, делает 1 самым свежим
 * `cache.put(3, 3);`    // Добавляет 3, вытесняет самый старый (2)
 * `cache.get(2);`       // Возвращает null (или -1 в задачах LeetCode)
 * `cache.get(1);`       // Возвращает 1
 * `cache.get(3);`       // Возвращает 3
 *
 * @param <K> Тип ключей кеша.
 * @param <V> Тип значений кеша.
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {

    private final int capacity;

    /**
     * Создает LRU кеш с заданной максимальной емкостью.
     *
     * @param capacity Максимальное количество элементов, которое может хранить кеш. Должна быть > 0.
     * @throws IllegalArgumentException если capacity не положительная.
     */
    public LRUCache(int capacity) {
        // Вызываем конструктор LinkedHashMap:
        // initialCapacity: ~capacity (для уменьшения рехеширований)
        // loadFactor: 0.75f (стандартный)
        // accessOrder: true - Ключевой параметр! Включает режим порядка доступа (LRU).
        super((int) Math.ceil(capacity / 0.75f) + 1, 0.75f, true);
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive: " + capacity);
        }
        this.capacity = capacity;
    }

    /**
     * Возвращает значение по ключу из кеша.
     * Если ключ найден, он становится самым недавно использованным (перемещается в конец).
     * В этой реализации возвращает {@code null}, если ключ не найден.
     *
     * @param key Ключ для поиска.
     * @return Значение, связанное с ключом, или {@code null}, если ключ отсутствует.
     */
    @Override
    public V get(Object key) {
        // LinkedHashMap с accessOrder=true автоматически обрабатывает обновление порядка при get.
        return super.getOrDefault(key, null);
    }

    /**
     * Добавляет или обновляет пару ключ-значение в кеше.
     * Добавленный/обновленный элемент становится самым недавно использованным.
     * Если после добавления размер кеша превышает емкость, самый старый элемент удаляется.
     *
     * @param key   Ключ.
     * @param value Значение.
     * @return Предыдущее значение, связанное с ключом, или null, если ключа не было.
     */
    @Override
    public V put(K key, V value) {
        // LinkedHashMap сам обрабатывает обновление порядка и вызывает removeEldestEntry.
        return super.put(key, value);
    }

    /**
     * Определяет, нужно ли удалять самый старый элемент (eldest) после операции добавления.
     * Вызывается автоматически методами `put` и `putAll`.
     *
     * @param eldest Самая старая (наименее недавно использованная) запись в кеше.
     * @return {@code true}, если текущий размер кеша `size()` превышает емкость `capacity`.
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        // Удаляем самый старый элемент, если размер превысил емкость
        return size() > capacity;
    }

    /**
     * Точка входа для демонстрации работы LRUCache.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        System.out.println("--- LRU Cache Demonstration ---");

        // Пример 1: LeetCode Example
        System.out.println("\n--- LeetCode Example (Capacity=2) ---");
        LRUCache<Integer, Integer> cacheLC = new LRUCache<>(2);
        System.out.println("put(1, 1)");
        cacheLC.put(1, 1);
        printCacheState(cacheLC);
        System.out.println("put(2, 2)");
        cacheLC.put(2, 2);
        printCacheState(cacheLC);
        System.out.println("get(1): " + cacheLC.get(1));
        printCacheState(cacheLC); // 1, makes 1 recent
        System.out.println("put(3, 3)");
        cacheLC.put(3, 3);
        printCacheState(cacheLC); // evicts 2
        System.out.println("get(2): " + cacheLC.get(2));
        printCacheState(cacheLC); // null
        System.out.println("put(4, 4)");
        cacheLC.put(4, 4);
        printCacheState(cacheLC); // evicts 1
        System.out.println("get(1): " + cacheLC.get(1));
        printCacheState(cacheLC); // null
        System.out.println("get(3): " + cacheLC.get(3));
        printCacheState(cacheLC); // 3
        System.out.println("get(4): " + cacheLC.get(4));
        printCacheState(cacheLC); // 4

        // Пример 2: Другой
        System.out.println("\n--- Example 2 (Capacity=3) ---");
        LRUCache<String, Double> cacheStr = new LRUCache<>(3);
        System.out.println("put(\"A\", 1.0)");
        cacheStr.put("A", 1.0);
        printCacheState(cacheStr);
        System.out.println("put(\"B\", 2.0)");
        cacheStr.put("B", 2.0);
        printCacheState(cacheStr);
        System.out.println("put(\"C\", 3.0)");
        cacheStr.put("C", 3.0);
        printCacheState(cacheStr);
        System.out.println("get(\"B\"): " + cacheStr.get("B"));
        printCacheState(cacheStr); // B becomes most recent
        System.out.println("put(\"D\", 4.0)");
        cacheStr.put("D", 4.0);
        printCacheState(cacheStr); // evicts A
        System.out.println("get(\"A\"): " + cacheStr.get("A"));
        printCacheState(cacheStr); // null
        System.out.println("put(\"C\", 3.5)");
        cacheStr.put("C", 3.5);
        printCacheState(cacheStr); // Updates C, makes it most recent
        System.out.println("put(\"E\", 5.0)");
        cacheStr.put("E", 5.0);
        printCacheState(cacheStr); // evicts B
    }

    /**
     * Вспомогательный метод для печати текущего состояния кеша.
     *
     * @param cache Экземпляр LRUCache.
     */
    private static void printCacheState(LRUCache<?, ?> cache) {
        System.out.print("  Cache state (Oldest -> Newest): [");
        String entries = cache.entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining(", "));
        System.out.println(entries + "] (Size: " + cache.size() + ")");
    }
}
