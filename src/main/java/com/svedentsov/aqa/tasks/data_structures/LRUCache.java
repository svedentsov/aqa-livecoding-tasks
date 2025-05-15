package com.svedentsov.aqa.tasks.data_structures;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Решение задачи №61: Реализация LRU Cache (Least Recently Used Cache).
 * Использует {@link LinkedHashMap} для эффективной реализации кеша.
 * Описание: (Проверяет: структуры данных - LinkedHashMap или комбинация Map + DoublyLinkedList)
 * Задание: Спроектируйте и реализуйте структуру данных `LRUCache`. Она должна
 * поддерживать операции `get(key)` (возвращает значение по ключу или null/спец.значение,
 * если ключ не найден, и делает этот ключ самым свежим) и `put(key, value)`
 * (добавляет/обновляет ключ-значение и делает его самым свежим). При достижении
 * максимальной емкости (`capacity`), перед добавлением нового элемента, должен
 * удаляться наименее недавно использовавшийся элемент (самый старый).
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
        // initialCapacity: вычисляется для уменьшения рехеширований, с учетом loadFactor.
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
     * Если после добавления размер кеша превышает емкость, самый старый элемент удаляется
     * через вызов removeEldestEntry.
     *
     * @param key   Ключ.
     * @param value Значение.
     * @return Предыдущее значение, связанное с ключом, или null, если ключа не было.
     */
    @Override
    public V put(K key, V value) {
        // LinkedHashMap сам обрабатывает обновление порядка и вызывает removeEldestEntry
        // после добавления элемента.
        return super.put(key, value);
    }

    /**
     * Определяет, нужно ли удалять самый старый элемент (eldest) после операции добавления.
     * Вызывается автоматически методами `put` и `putAll` из {@link LinkedHashMap}
     * после вставки нового элемента.
     *
     * @param eldest Самая старая (наименее недавно использованная) запись в кеше.
     * @return {@code true}, если текущий размер кеша `size()` превышает емкость `capacity`.
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        // Удаляем самый старый элемент, если размер превысил емкость
        return size() > capacity;
    }
}
