package com.svedentsov.aqa.tasks.maps_sets;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Решение задачи №26: Группировка элементов списка по признаку (длине строки).
 * Описание: Например, сгруппировать список строк по первой букве.
 * (Проверяет: Map, циклы, работа с коллекциями)
 * Задание: Напишите метод `Map<Integer, List<String>> groupStringsByLength(List<String> strings)`,
 * который принимает список строк и возвращает `Map`, где ключи - это длина строки,
 * а значения - списки строк этой длины. Ключи в карте должны быть отсортированы.
 * Пример: `groupStringsByLength(List.of("apple", "bat", "cat", "apricot", "ball"))` ->
 * `{3: ["bat", "cat"], 4: ["ball"], 5: ["apple"], 7: ["apricot"]}` (порядок внутри списков может отличаться).
 */
public class GroupListElements {

    /**
     * Группирует список строк по их длине итеративно.
     * Использует TreeMap для хранения групп (ключи отсортированы).
     * Игнорирует null строки в исходном списке.
     *
     * @param strings Список строк для группировки. Может быть null.
     * @return Карта (TreeMap), где ключи - длины строк, а значения - списки строк соответствующей длины.
     * Возвращает пустую карту, если исходный список null или пуст или содержит только null.
     */
    public Map<Integer, List<String>> groupStringsByLength(List<String> strings) {
        // Используем TreeMap для автоматической сортировки ключей (длин)
        Map<Integer, List<String>> groupedMap = new TreeMap<>();
        if (strings == null || strings.isEmpty()) {
            return groupedMap; // Возвращаем пустой TreeMap
        }
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
     * Использует TreeMap для сортировки ключей (длин).
     * Игнорирует null строки в исходном списке.
     *
     * @param strings Список строк для группировки. Может быть null.
     * @return Карта (TreeMap), сгруппированная по длине строк, ключи отсортированы.
     * Возвращает пустую карту, если исходный список null или пуст или содержит только null.
     */
    public Map<Integer, List<String>> groupStringsByLengthStream(List<String> strings) {
        if (strings == null || strings.isEmpty()) {
            return new TreeMap<>(); // Возвращаем пустой TreeMap
        }
        return strings.stream()
                .filter(Objects::nonNull) // Игнорируем null строки
                .collect(Collectors.groupingBy(
                        String::length, // Классификатор (ключ)
                        TreeMap::new, // Фабрика карты (для сортировки ключей)
                        Collectors.toList() // Downstream коллектор (значение - список)
                ));
    }
}
