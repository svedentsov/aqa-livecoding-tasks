package com.svedentsov.aqa.tasks.arrays_lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Решение задачи №17: Объединить два отсортированных списка.
 * <p>
 * Описание: Написать функцию, которая принимает два отсортированных списка/массива
 * и возвращает один объединенный отсортированный список/массив.
 * (Проверяет: циклы, сравнения, работа с индексами)
 * <p>
 * Задание: Напишите метод `List<Integer> mergeSortedLists(List<Integer> list1, List<Integer> list2)`,
 * который принимает два отсортированных по возрастанию списка целых чисел
 * и возвращает новый отсортированный список, содержащий все элементы
 * из обоих исходных списков.
 * <p>
 * Пример: `mergeSortedLists(List.of(1, 3, 5), List.of(2, 4, 6))` -> `[1, 2, 3, 4, 5, 6]`.
 */
public class MergeSortedLists {

    /**
     * Сливает два отсортированных по возрастанию списка целых чисел в один новый
     * отсортированный список.
     * Использует метод двух указателей для эффективного слияния.
     * Сложность: O(m + n) по времени, где m и n - размеры списков.
     * Сложность: O(m + n) по памяти для результирующего списка.
     * Исходные списки не изменяются.
     * Обрабатывает null значения в качестве входных списков как пустые списки.
     * Обрабатывает null значения внутри списков, пропуская их при слиянии.
     *
     * @param list1 Первый отсортированный список. Может быть null или содержать null.
     * @param list2 Второй отсортированный список. Может быть null или содержать null.
     * @return Новый объединенный и отсортированный список, содержащий не-null
     * элементы из обоих исходных списков. Возвращает пустой список,
     * если оба исходных списка null/пусты или содержат только null.
     */
    public List<Integer> mergeSortedLists(List<Integer> list1, List<Integer> list2) {
        // Обрабатываем null входные списки как пустые
        List<Integer> nonNullList1 = (list1 == null) ? Collections.emptyList() : list1;
        List<Integer> nonNullList2 = (list2 == null) ? Collections.emptyList() : list2;

        // Результирующий список. ArrayList обычно эффективнее для добавления в конец.
        List<Integer> mergedList = new ArrayList<>(nonNullList1.size() + nonNullList2.size());
        int i = 0; // Указатель для nonNullList1
        int j = 0; // Указатель для nonNullList2

        // Основной цикл слияния, пока есть элементы в обоих списках
        while (i < nonNullList1.size() && j < nonNullList2.size()) {
            Integer val1 = nonNullList1.get(i);
            Integer val2 = nonNullList2.get(j);

            // Пропускаем null элементы в первом списке
            if (val1 == null) {
                i++;
                continue; // Переходим к следующей итерации while
            }
            // Пропускаем null элементы во втором списке
            if (val2 == null) {
                j++;
                continue; // Переходим к следующей итерации while
            }

            // Сравниваем текущие не-null элементы и добавляем меньший в результат
            if (val1.compareTo(val2) <= 0) { // Используем compareTo для Integer
                mergedList.add(val1);
                i++; // Сдвигаем указатель первого списка
            } else {
                mergedList.add(val2);
                j++; // Сдвигаем указатель второго списка
            }
        }

        // Добавляем оставшиеся не-null элементы из первого списка (если есть)
        while (i < nonNullList1.size()) {
            Integer val1 = nonNullList1.get(i);
            if (val1 != null) { // Проверяем на null перед добавлением
                mergedList.add(val1);
            }
            i++;
        }

        // Добавляем оставшиеся не-null элементы из второго списка (если есть)
        while (j < nonNullList2.size()) {
            Integer val2 = nonNullList2.get(j);
            if (val2 != null) { // Проверяем на null перед добавлением
                mergedList.add(val2);
            }
            j++;
        }

        return mergedList;
    }

    /**
     * Точка входа для демонстрации работы метода слияния списков.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        MergeSortedLists sol = new MergeSortedLists();

        runMergeTest(sol, List.of(1, 3, 5, 7), List.of(2, 4, 6, 8), "Стандартный случай"); // [1, 2, 3, 4, 5, 6, 7, 8]
        runMergeTest(sol, List.of(10, 20), List.of(5, 15, 25), "Разные размеры"); // [5, 10, 15, 20, 25]
        runMergeTest(sol, List.of(1, 2, 3), List.of(), "Один пустой список"); // [1, 2, 3]
        runMergeTest(sol, List.of(), List.of(4, 5), "Первый пустой список"); // [4, 5]
        runMergeTest(sol, List.of(), List.of(), "Оба пустые"); // []
        runMergeTest(sol, null, List.of(1, 5), "Первый null"); // [1, 5]
        runMergeTest(sol, List.of(2, 4), null, "Второй null"); // [2, 4]
        runMergeTest(sol, null, null, "Оба null"); // []
        runMergeTest(sol, Arrays.asList(1, 3, null, 5), Arrays.asList(null, 2, 4, null, 6), "Списки с null элементами"); // [1, 2, 3, 4, 5, 6]
        runMergeTest(sol, List.of(1, 2, 3), List.of(1, 2, 3, 4, 5), "Один - подсписок другого"); // [1, 1, 2, 2, 3, 3, 4, 5]
        runMergeTest(sol, List.of(1, 3, 3, 5), List.of(3, 4, 4, 6), "Списки с дубликатами"); // [1, 3, 3, 3, 4, 4, 5, 6]
        runMergeTest(sol, List.of(-5, -1, 0, 10), List.of(-2, 0, 3, 10), "С отрицательными и нулем"); // [-5, -2, -1, 0, 0, 3, 10, 10]
    }

    /**
     * Вспомогательный метод для тестирования слияния списков.
     *
     * @param sol         Экземпляр решателя.
     * @param list1       Первый список.
     * @param list2       Второй список.
     * @param description Описание теста.
     */
    private static void runMergeTest(MergeSortedLists sol, List<Integer> list1, List<Integer> list2, String description) {
        System.out.println("\n--- " + description + " ---");
        String l1Str = (list1 == null ? "null" : list1.toString());
        String l2Str = (list2 == null ? "null" : list2.toString());
        System.out.println("List 1: " + l1Str);
        System.out.println("List 2: " + l2Str);
        try {
            List<Integer> merged = sol.mergeSortedLists(list1, list2);
            System.out.println("Merged: " + merged.toString());
        } catch (Exception e) {
            System.out.println("Merged: Error - " + e.getMessage());
        }
    }
}
