package com.svedentsov.aqa.tasks.arrays_lists;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Решение задачи №4: Найти дубликаты в списке.
 */
public class Task04_FindDuplicatesList {

    /**
     * Находит уникальные дублирующиеся элементы в списке целых чисел.
     * Использует два множества: одно для отслеживания встреченных чисел,
     * другое для хранения найденных дубликатов.
     * Сложность O(n) по времени и O(n) по памяти в худшем случае.
     *
     * @param numbers Список целых чисел для проверки. Может быть null или содержать null.
     * @return Список уникальных дубликатов (не включая null). Если дубликатов нет
     * или список null/пуст, возвращает пустой список.
     */
    public List<Integer> findDuplicates(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return new ArrayList<>();
        }
        Set<Integer> seen = new HashSet<>();
        Set<Integer> duplicates = new HashSet<>(); // Используем Set для уникальности дубликатов
        for (Integer number : numbers) {
            // Игнорируем null элементы при поиске дубликатов
            if (number == null) continue;
            // Метод add() у Set возвращает false, если элемент уже существует
            if (!seen.add(number)) {
                duplicates.add(number); // Добавляем в дубликаты, если уже видели
            }
        }
        return new ArrayList<>(duplicates); // Возвращаем как список
    }

    /**
     * Находит уникальные дублирующиеся элементы с использованием Stream API и группировки.
     * Может быть менее эффективен по памяти для больших списков из-за создания промежуточной карты.
     *
     * @param numbers Список целых чисел для проверки. Может быть null или содержать null.
     * @return Список уникальных дубликатов (не включая null).
     */
    public List<Integer> findDuplicatesStream(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return new ArrayList<>();
        }
        return numbers.stream()
                .filter(Objects::nonNull) // Фильтруем null элементы
                // Группируем по значению и считаем количество вхождений
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream() // Получаем поток записей карты (число -> количество)
                .filter(entry -> entry.getValue() > 1) // Фильтруем те, что встречаются больше одного раза
                .map(Map.Entry::getKey) // Берем только ключ (само число-дубликат)
                .collect(Collectors.toList()); // Собираем в список
    }

    /**
     * Точка входа для демонстрации работы методов поиска дубликатов.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task04_FindDuplicatesList sol = new Task04_FindDuplicatesList();
        List<Integer> list1 = List.of(1, 2, 3, 2, 4, 5, 1, 5);
        System.out.println("findDuplicates(" + list1 + "): " + sol.findDuplicates(list1)); // [1, 2, 5] (порядок может отличаться)
        System.out.println("findDuplicatesStream(" + list1 + "): " + sol.findDuplicatesStream(list1));

        List<Integer> list2 = List.of(1, 2, 3, 4);
        System.out.println("findDuplicates(" + list2 + "): " + sol.findDuplicates(list2)); // []
        System.out.println("findDuplicatesStream(" + list2 + "): " + sol.findDuplicatesStream(list2)); // []

        List<Integer> list3 = List.of(1, 1, 1, 1);
        System.out.println("findDuplicates(" + list3 + "): " + sol.findDuplicates(list3)); // [1]
        System.out.println("findDuplicatesStream(" + list3 + "): " + sol.findDuplicatesStream(list3)); // [1]

        List<Integer> list4 = new ArrayList<>();
        list4.add(1);
        list4.add(null);
        list4.add(2);
        list4.add(null); // Несколько null
        list4.add(1);    // Дубликат 1
        list4.add(2);    // Дубликат 2
        System.out.println("findDuplicates(" + list4 + "): " + sol.findDuplicates(list4)); // [1, 2]
        System.out.println("findDuplicatesStream(" + list4 + "): " + sol.findDuplicatesStream(list4)); // [1, 2]
    }
}
