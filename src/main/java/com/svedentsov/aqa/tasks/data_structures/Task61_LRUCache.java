package com.svedentsov.aqa.tasks.data_structures;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Решение задачи №61: Реализация LRU Cache (Least Recently Used Cache).
 * Использует {@link LinkedHashMap} для эффективной реализации кеша.
 *
 * @param <K> Тип ключей кеша.
 * @param <V> Тип значений кеша.
 */
public class Task61_LRUCache<K, V> extends LinkedHashMap<K, V> {

    private final int capacity;

    /**
     * Создает LRU кеш с заданной максимальной емкостью.
     *
     * @param capacity Максимальное количество элементов, которое может хранить кеш. Должна быть > 0.
     * @throws IllegalArgumentException если capacity не положительная.
     */
    public Task61_LRUCache(int capacity) {
        // Вызываем конструктор LinkedHashMap с параметрами:
        // initialCapacity: Начальная емкость (можно взять capacity или чуть больше, например capacity / 0.75f + 1).
        // loadFactor: Коэффициент загрузки (стандартный 0.75f).
        // accessOrder: true - Ключевой параметр! Включает режим порядка доступа (LRU).
        //                 При вызове get() элемент перемещается в конец (как самый свежий).
        //                 При итерации элементы идут от самого старого к самому свежему.
        super(capacity, 0.75f, true);
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive: " + capacity);
        }
        this.capacity = capacity;
    }

    /**
     * Возвращает значение по ключу из кеша.
     * Если ключ найден, он становится самым недавно использованным (перемещается в конец).
     * Метод {@code get} из {@code LinkedHashMap} уже обеспечивает это поведение при {@code accessOrder=true}.
     *
     * @param key Ключ для поиска.
     * @return Значение, связанное с ключом, или {@code null}, если ключ отсутствует в кеше.
     * (В задачах типа LeetCode часто просят вернуть -1 для Integer кеша, если не найдено).
     */
    @Override
    public V get(Object key) {
        // Просто вызываем getOrDefault родительского класса.
        // Само обращение к элементу через get() уже обновит его позицию в LinkedHashMap.
        return super.getOrDefault(key, null);
    }

    /**
     * Добавляет или обновляет пару ключ-значение в кеше.
     * Добавленный/обновленный элемент становится самым недавно использованным.
     * Если после добавления размер кеша превышает емкость, самый старый элемент удаляется
     * благодаря переопределенному методу {@link #removeEldestEntry(Map.Entry)}.
     *
     * @param key   Ключ (не null).
     * @param value Значение (не null).
     * @return Предыдущее значение, связанное с ключом, или null, если ключа не было.
     */
    @Override
    public V put(K key, V value) {
        // Просто вызываем put родительского класса. Он сам обработает обновление позиции
        // и вызовет removeEldestEntry при необходимости.
        return super.put(key, value);
    }

    /**
     * Определяет, нужно ли удалять самый старый элемент (eldest) после операции добавления.
     * Этот метод автоматически вызывается методами {@code put()} и {@code putAll()} класса {@code LinkedHashMap}.
     *
     * @param eldest Самая старая (наименее недавно использованная) запись в кеше.
     * @return {@code true}, если текущий размер кеша {@code size()} превышает его
     * емкость {@code capacity}, что приведет к удалению {@code eldest};
     * {@code false} в противном случае.
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        // Возвращаем true, если нужно удалить самый старый элемент
        return size() > capacity;
    }

    /**
     * Точка входа для демонстрации работы LRUCache.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        System.out.println("--- LRU Cache Demo (Capacity=3) ---");
        // Создаем кеш с емкостью 3
        Task61_LRUCache<Integer, String> cache = new Task61_LRUCache<>(3);

        Runnable printCache = () -> {
            System.out.print("Cache state (Oldest -> Newest): [");
            // Итерация по LinkedHashMap с accessOrder=true идет от самого старого к самому новому
            String entries = cache.entrySet().stream()
                    .map(e -> e.getKey() + "=" + e.getValue())
                    .collect(Collectors.joining(", "));
            System.out.println(entries + "] (Size: " + cache.size() + ")");
        };

        System.out.println("put(1, \"A\")");
        cache.put(1, "A");
        printCache.run();
        System.out.println("put(2, \"B\")");
        cache.put(2, "B");
        printCache.run();
        System.out.println("put(3, \"C\")");
        cache.put(3, "C");
        printCache.run(); // [1=A, 2=B, 3=C]

        System.out.println("\nget(1): " + cache.get(1)); // "A". Элемент 1 становится самым свежим.
        printCache.run(); // [2=B, 3=C, 1=A]

        System.out.println("\nput(4, \"D\")"); // Превышение емкости, должен вытесниться самый старый (2)
        cache.put(4, "D");
        printCache.run(); // [3=C, 1=A, 4=D]

        System.out.println("\nget(2): " + cache.get(2)); // null (был вытеснен)
        printCache.run(); // [3=C, 1=A, 4=D]

        System.out.println("\nput(3, \"C_new\")"); // Обновляем существующий элемент 3. Он станет самым свежим.
        cache.put(3, "C_new");
        printCache.run(); // [1=A, 4=D, 3=C_new]

        System.out.println("\nput(5, \"E\")"); // Вытесняет самый старый (1)
        cache.put(5, "E");
        printCache.run(); // [4=D, 3=C_new, 5=E]
    }
}
