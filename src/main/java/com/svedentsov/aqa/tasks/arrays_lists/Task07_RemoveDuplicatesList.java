package com.svedentsov.aqa.tasks.arrays_lists;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Решение задачи №7: Удалить дубликаты из списка.
 */
public class Task07_RemoveDuplicatesList {

    /**
     * Удаляет дубликаты из списка строк, сохраняя порядок первого вхождения.
     * Использует LinkedHashSet для эффективного отслеживания уникальных элементов
     * и сохранения порядка.
     * Сложность O(n) по времени и O(n) по памяти.
     *
     * @param list Исходный список строк. Может быть null.
     * @return Новый список с уникальными строками в порядке их первого появления
     * в исходном списке. Возвращает пустой список, если исходный был null.
     */
    public List<String> removeDuplicates(List<String> list) {
        if (list == null) {
            return new ArrayList<>();
        }
        // LinkedHashSet автоматически обрабатывает дубликаты и сохраняет порядок
        Set<String> uniqueSet = new LinkedHashSet<>(list);
        // Создаем новый список из уникальных элементов сета
        return new ArrayList<>(uniqueSet);
    }

    /**
     * Удаляет дубликаты из списка строк с использованием Stream API (distinct()).
     * Порядок элементов сохраняется.
     *
     * @param list Исходный список строк. Может быть null.
     * @return Новый список с уникальными строками.
     */
    public List<String> removeDuplicatesStream(List<String> list) {
        if (list == null) {
            return new ArrayList<>();
        }
        // Обработка null элементов внутри списка перед distinct, если они допустимы
        return list.stream()
                .filter(Objects::nonNull) // Опционально: убрать null перед поиском уникальных
                .distinct() // Удаляет дубликаты
                .collect(Collectors.toList()); // Собирает в новый список
    }

    /**
     * Точка входа для демонстрации работы методов удаления дубликатов.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task07_RemoveDuplicatesList sol = new Task07_RemoveDuplicatesList();
        List<String> list1 = List.of("a", "b", "a", "c", "b", "a");
        System.out.println("removeDuplicates(" + list1 + "): " + sol.removeDuplicates(list1)); // [a, b, c]
        System.out.println("removeDuplicatesStream(" + list1 + "): " + sol.removeDuplicatesStream(list1)); // [a, b, c]


        List<String> list2 = List.of("java", "python", "java", "c++", "python");
        System.out.println("removeDuplicates(" + list2 + "): " + sol.removeDuplicates(list2)); // [java, python, c++]
        System.out.println("removeDuplicatesStream(" + list2 + "): " + sol.removeDuplicatesStream(list2)); // [java, python, c++]

        List<String> list3 = List.of("a", "a", "a");
        System.out.println("removeDuplicates(" + list3 + "): " + sol.removeDuplicates(list3)); // [a]
        System.out.println("removeDuplicatesStream(" + list3 + "): " + sol.removeDuplicatesStream(list3)); // [a]

        List<String> list4 = new ArrayList<>(); // Пустой список
        System.out.println("removeDuplicates(" + list4 + "): " + sol.removeDuplicates(list4)); // []
        System.out.println("removeDuplicatesStream(" + list4 + "): " + sol.removeDuplicatesStream(list4)); // []

        System.out.println("removeDuplicates(null): " + sol.removeDuplicates(null)); // []
        System.out.println("removeDuplicatesStream(null): " + sol.removeDuplicatesStream(null)); // []
    }
}
