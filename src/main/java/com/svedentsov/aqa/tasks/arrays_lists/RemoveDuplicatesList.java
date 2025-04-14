package com.svedentsov.aqa.tasks.arrays_lists;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Решение задачи №7: Удалить дубликаты из списка.
 * <p>
 * Описание: Написать функцию, которая принимает список и возвращает новый список
 * без дубликатов. (Проверяет: работа с коллекциями, Set, циклы)
 * <p>
 * Задание: Напишите метод `List<String> removeDuplicates(List<String> list)`,
 * который принимает список строк и возвращает новый список, содержащий только
 * уникальные строки из исходного списка, сохраняя порядок первого вхождения.
 * <p>
 * Пример: `removeDuplicates(List.of("a", "b", "a", "c", "b"))` -> `["a", "b", "c"]`.
 */
public class RemoveDuplicatesList {

    /**
     * Удаляет дубликаты из списка строк, сохраняя порядок первого вхождения.
     * Использует LinkedHashSet для эффективного отслеживания уникальных элементов
     * и сохранения порядка. Null элементы в исходном списке будут сохранены как
     * уникальные (если встречаются).
     * Сложность O(n) по времени и O(n) по памяти.
     *
     * @param list Исходный список строк. Может быть null или содержать null.
     * @return Новый список с уникальными строками в порядке их первого появления
     * в исходном списке. Возвращает пустой список, если исходный был null.
     */
    public List<String> removeDuplicates(List<String> list) {
        if (list == null) {
            return new ArrayList<>(); // Возвращаем пустой список для null входа
        }
        // LinkedHashSet автоматически обрабатывает дубликаты (включая null)
        // и сохраняет порядок вставки.
        Set<String> uniqueSet = new LinkedHashSet<>(list);
        // Создаем новый список из уникальных элементов сета
        return new ArrayList<>(uniqueSet);
    }

    /**
     * Удаляет дубликаты из списка строк с использованием Stream API (distinct()).
     * Порядок элементов сохраняется. Null элементы также обрабатываются корректно
     * методом distinct().
     *
     * @param list Исходный список строк. Может быть null или содержать null.
     * @return Новый список с уникальными строками. Возвращает пустой список для null входа.
     */
    public List<String> removeDuplicatesStream(List<String> list) {
        if (list == null) {
            return new ArrayList<>();
        }
        return list.stream()
                // .filter(Objects::nonNull) // Раскомментировать, если null нужно полностью исключить
                .distinct() // Удаляет дубликаты, корректно обрабатывает null
                .collect(Collectors.toList()); // Собирает в новый список
    }

    /**
     * Точка входа для демонстрации работы методов удаления дубликатов.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        RemoveDuplicatesList sol = new RemoveDuplicatesList();

        runDuplicatesTest(sol, List.of("a", "b", "a", "c", "b", "a"), "Стандартный случай");
        // Ожидаемый результат: [a, b, c]

        runDuplicatesTest(sol, List.of("java", "python", "java", "c++", "python"), "Другой набор строк");
        // Ожидаемый результат: [java, python, c++]

        runDuplicatesTest(sol, List.of("a", "a", "a"), "Все элементы одинаковы");
        // Ожидаемый результат: [a]

        runDuplicatesTest(sol, new ArrayList<>(), "Пустой список");
        // Ожидаемый результат: []

        runDuplicatesTest(sol, null, "Null входной список");
        // Ожидаемый результат: []

        // Список с null (нужен ArrayList, т.к. List.of не допускает null)
        List<String> listWithNulls = new ArrayList<>();
        listWithNulls.add("apple");
        listWithNulls.add(null);
        listWithNulls.add("banana");
        listWithNulls.add("apple");
        listWithNulls.add(null);
        runDuplicatesTest(sol, listWithNulls, "Список с null элементами");
        // Ожидаемый результат: [apple, null, banana]

        runDuplicatesTest(sol, List.of("Case", "case", "CASE"), "Строки с разным регистром");
        // Ожидаемый результат: [Case, case, CASE] (т.к. они разные)

        runDuplicatesTest(sol, List.of(" lead ", "lead", " lead "), "Строки с пробелами");
        // Ожидаемый результат: [ lead , lead]

        runDuplicatesTest(sol, List.of("a"), "Список из одного элемента");
        // Ожидаемый результат: [a]
    }

    /**
     * Вспомогательный метод для тестирования методов удаления дубликатов.
     *
     * @param sol         Экземпляр решателя.
     * @param list        Тестовый список.
     * @param description Описание теста.
     */
    private static void runDuplicatesTest(RemoveDuplicatesList sol, List<String> list, String description) {
        System.out.println("\n--- " + description + " ---");
        // Используем toString() для представления списка, включая null
        System.out.println("Input list: " + (list == null ? "null" : list.toString()));
        try {
            List<String> uniqueSet = sol.removeDuplicates(list == null ? null : new ArrayList<>(list));
            System.out.println("removeDuplicates (Set):    " + uniqueSet);
        } catch (Exception e) {
            System.out.println("removeDuplicates (Set):    Error - " + e.getMessage());
        }
        try {
            List<String> uniqueStream = sol.removeDuplicatesStream(list == null ? null : new ArrayList<>(list));
            System.out.println("removeDuplicates (Stream): " + uniqueStream);
        } catch (Exception e) {
            System.out.println("removeDuplicates (Stream): Error - " + e.getMessage());
        }
    }
}
