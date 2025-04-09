package com.svedentsov.aqa.tasks.maps_sets;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Решение задачи №26: Группировка элементов списка по признаку (длине строки).
 */
public class Task26_GroupListElements {

    /**
     * Группирует список строк по их длине.
     * Использует HashMap для хранения групп, где ключ - длина строки,
     * а значение - список строк этой длины.
     * Игнорирует null строки в исходном списке.
     *
     * @param strings Список строк для группировки. Может быть null.
     * @return Карта (Map), где ключи - длины строк, а значения - списки строк соответствующей длины.
     * Возвращает пустую карту, если исходный список null или пуст.
     */
    public Map<Integer, List<String>> groupStringsByLength(List<String> strings) {
        if (strings == null || strings.isEmpty()) {
            return new HashMap<>();
        }
        Map<Integer, List<String>> groupedMap = new HashMap<>();
        for (String str : strings) {
            // Пропускаем null строки, чтобы избежать NullPointerException
            if (str == null) continue;
            int length = str.length();
            // computeIfAbsent упрощает добавление в список внутри карты
            groupedMap.computeIfAbsent(length, k -> new ArrayList<>()).add(str);
        }
        return groupedMap;
    }

    /**
     * Группирует список строк по их длине с использованием Stream API и Collectors.groupingBy.
     *
     * @param strings Список строк для группировки. Может быть null.
     * @return Карта (Map), сгруппированная по длине строк.
     */
    public Map<Integer, List<String>> groupStringsByLengthStream(List<String> strings) {
        if (strings == null || strings.isEmpty()) {
            return new HashMap<>();
        }
        return strings.stream()
                .filter(Objects::nonNull) // Игнорируем null строки
                // Collectors.groupingBy принимает функцию-классификатор (String::length)
                // и (опционально) downstream коллектор (по умолчанию toList())
                .collect(Collectors.groupingBy(String::length));
    }

    /**
     * Точка входа для демонстрации работы методов группировки.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task26_GroupListElements sol = new Task26_GroupListElements();
        List<String> data = List.of("apple", "bat", "cat", "apricot", "ball", null, "dog", "kiwi", "a", "zz");
        System.out.println("Original list: " + data);

        Map<Integer, List<String>> grouped1 = sol.groupStringsByLength(data);
        System.out.println("\nGrouped (Manual):");
        // Сортируем ключи для предсказуемого вывода
        grouped1.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> System.out.println("Length " + entry.getKey() + ": " + entry.getValue()));
        /* Примерный вывод:
           Length 1: [a]
           Length 2: [zz]
           Length 3: [bat, cat, dog]
           Length 4: [ball, kiwi]
           Length 5: [apple]
           Length 7: [apricot]
        */

        Map<Integer, List<String>> grouped2 = sol.groupStringsByLengthStream(data);
        System.out.println("\nGrouped (Stream):");
        grouped2.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> System.out.println("Length " + entry.getKey() + ": " + entry.getValue()));

        List<String> emptyList = List.of();
        System.out.println("\nGrouped (Empty List): " + sol.groupStringsByLength(emptyList)); // {}
        System.out.println("Grouped (Null List): " + sol.groupStringsByLength(null)); // {}
    }
}
