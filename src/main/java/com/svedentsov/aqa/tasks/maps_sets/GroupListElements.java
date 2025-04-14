package com.svedentsov.aqa.tasks.maps_sets;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Решение задачи №26: Группировка элементов списка по признаку (длине строки).
 * <p>
 * Описание: Например, сгруппировать список строк по первой букве.
 * (Проверяет: Map, циклы, работа с коллекциями)
 * <p>
 * Задание: Напишите метод `Map<Integer, List<String>> groupStringsByLength(List<String> strings)`,
 * который принимает список строк и возвращает `Map`, где ключи - это длина строки,
 * а значения - списки строк этой длины.
 * <p>
 * Пример: `groupStringsByLength(List.of("apple", "bat", "cat", "apricot", "ball"))` ->
 * `{5: ["apple"], 3: ["bat", "cat"], 7: ["apricot"], 4: ["ball"]}`.
 */
public class GroupListElements {

    /**
     * Группирует список строк по их длине итеративно.
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
        // Используем TreeMap для сортировки ключей (длин) в итоговой карте
        Map<Integer, List<String>> groupedMap = new TreeMap<>();
        for (String str : strings) {
            // Пропускаем null строки
            if (str == null) continue;
            int length = str.length();
            // computeIfAbsent: если ключ 'length' отсутствует, создает новый ArrayList,
            // иначе возвращает существующий список. Затем добавляет строку в этот список.
            groupedMap.computeIfAbsent(length, k -> new ArrayList<>()).add(str);
        }
        return groupedMap;
    }

    /**
     * Группирует список строк по их длине с использованием Stream API и Collectors.groupingBy.
     *
     * @param strings Список строк для группировки. Может быть null.
     * @return Карта (TreeMap), сгруппированная по длине строк, ключи отсортированы.
     */
    public Map<Integer, List<String>> groupStringsByLengthStream(List<String> strings) {
        if (strings == null || strings.isEmpty()) {
            // Возвращаем пустой TreeMap
            return new TreeMap<>();
        }
        return strings.stream()
                .filter(Objects::nonNull) // Игнорируем null строки
                // Collectors.groupingBy:
                // 1-й арг: функция-классификатор (String::length)
                // 2-й арг: фабрика для создания карты (TreeMap::new для сортировки ключей)
                // 3-й арг: downstream коллектор (Collectors.toList() - собирает элементы группы в список)
                .collect(Collectors.groupingBy(
                        String::length,
                        TreeMap::new,
                        Collectors.toList()
                ));
    }

    /**
     * Точка входа для демонстрации работы методов группировки.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        GroupListElements sol = new GroupListElements();

        runGroupTest(sol, List.of("apple", "bat", "cat", "apricot", "ball", null, "dog", "kiwi", "a", "zz"), "Стандартный случай с null");
        runGroupTest(sol, List.of("one", "two", "three", "four", "five"), "Случай с уникальными длинами");
        runGroupTest(sol, List.of("a", "b", "c"), "Все строки одной длины (1)");
        runGroupTest(sol, List.of("aaaa", "bbbb"), "Все строки одной длины (4)");
        runGroupTest(sol, List.of(), "Пустой список");
        runGroupTest(sol, null, "Null список");
        runGroupTest(sol, Arrays.asList(null, null), // Используем Arrays.asList для создания списка с null
                "Список только из null");
    }

    /**
     * Вспомогательный метод для тестирования группировки.
     *
     * @param sol         Экземпляр решателя.
     * @param list        Тестовый список.
     * @param description Описание теста.
     */
    private static void runGroupTest(GroupListElements sol, List<String> list, String description) {
        System.out.println("\n--- " + description + " ---");
        String input = (list == null ? "null" : list.toString());
        System.out.println("Input list: " + input);
        try {
            Map<Integer, List<String>> groupedIterative = sol.groupStringsByLength(list);
            System.out.println("Grouped (Iterative):");
            groupedIterative.forEach((key, value) -> System.out.println("  Length " + key + ": " + value));
            if (groupedIterative.isEmpty()) System.out.println("  {}"); // Для пустых результатов
        } catch (Exception e) {
            System.out.println("Grouped (Iterative): Error - " + e.getMessage());
        }
        try {
            Map<Integer, List<String>> groupedStream = sol.groupStringsByLengthStream(list);
            System.out.println("Grouped (Stream):");
            groupedStream.forEach((key, value) -> System.out.println("  Length " + key + ": " + value));
            if (groupedStream.isEmpty()) System.out.println("  {}"); // Для пустых результатов
        } catch (Exception e) {
            System.out.println("Grouped (Stream):    Error - " + e.getMessage());
        }
    }
}
