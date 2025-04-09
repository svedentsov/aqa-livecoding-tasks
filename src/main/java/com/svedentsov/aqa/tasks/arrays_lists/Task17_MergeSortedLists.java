package com.svedentsov.aqa.tasks.arrays_lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Решение задачи №17: Объединить два отсортированных списка.
 */
public class Task17_MergeSortedLists {

    /**
     * Сливает два отсортированных по возрастанию списка целых чисел в один новый
     * отсортированный список.
     * Использует метод двух указателей для эффективного слияния за O(m+n) времени,
     * где m и n - размеры списков. Создает новый список, не изменяет исходные.
     *
     * @param list1 Первый отсортированный список. Может быть null или содержать null элементы (которые будут проигнорированы при сравнении).
     * @param list2 Второй отсортированный список. Может быть null или содержать null элементы.
     * @return Новый объединенный и отсортированный список. Содержит только не-null элементы.
     * Возвращает пустой список, если оба исходных списка null или пусты.
     */
    public List<Integer> mergeSortedLists(List<Integer> list1, List<Integer> list2) {
        // Обработка null как пустых списков
        List<Integer> nonNullList1 = (list1 == null) ? Collections.emptyList() : list1;
        List<Integer> nonNullList2 = (list2 == null) ? Collections.emptyList() : list2;

        // Результирующий список с начальной емкостью
        List<Integer> mergedList = new ArrayList<>(nonNullList1.size() + nonNullList2.size());
        int i = 0; // Указатель для list1
        int j = 0; // Указатель для list2

        // Идем по обоим спискам
        while (i < nonNullList1.size() && j < nonNullList2.size()) {
            Integer val1 = nonNullList1.get(i);
            Integer val2 = nonNullList2.get(j);

            // Обработка null значений внутри списков (пропускаем их)
            if (val1 == null) {
                i++;
                continue;
            }
            if (val2 == null) {
                j++;
                continue;
            }

            // Сравниваем не-null значения и добавляем меньшее
            if (val1 <= val2) {
                mergedList.add(val1);
                i++;
            } else {
                mergedList.add(val2);
                j++;
            }
        }

        // Добавляем оставшиеся не-null элементы из list1
        while (i < nonNullList1.size()) {
            Integer val1 = nonNullList1.get(i);
            if (val1 != null) {
                mergedList.add(val1);
            }
            i++;
        }

        // Добавляем оставшиеся не-null элементы из list2
        while (j < nonNullList2.size()) {
            Integer val2 = nonNullList2.get(j);
            if (val2 != null) {
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
        Task17_MergeSortedLists sol = new Task17_MergeSortedLists();

        List<Integer> l1 = List.of(1, 3, 5, 7);
        List<Integer> l2 = List.of(2, 4, 6, 8);
        System.out.println("List1: " + l1);
        System.out.println("List2: " + l2);
        System.out.println("Merged: " + sol.mergeSortedLists(l1, l2)); // [1, 2, 3, 4, 5, 6, 7, 8]

        List<Integer> l3 = List.of(10, 20);
        List<Integer> l4 = List.of(5, 15, 25);
        System.out.println("\nList3: " + l3);
        System.out.println("List4: " + l4);
        System.out.println("Merged: " + sol.mergeSortedLists(l3, l4)); // [5, 10, 15, 20, 25]

        List<Integer> l5 = List.of(1, 2, 3);
        List<Integer> l6 = List.of(); // Пустой список
        System.out.println("\nList5: " + l5);
        System.out.println("List6: " + l6);
        System.out.println("Merged: " + sol.mergeSortedLists(l5, l6)); // [1, 2, 3]

        List<Integer> l7 = null; // Null список
        List<Integer> l8 = List.of(1, 5);
        System.out.println("\nList7: " + l7);
        System.out.println("List8: " + l8);
        System.out.println("Merged null and List8: " + sol.mergeSortedLists(l7, l8)); // [1, 5]
        System.out.println("Merged null and null: " + sol.mergeSortedLists(null, null)); // []

        // Списки с null элементами
        List<Integer> l9 = Arrays.asList(1, 3, null, 5);
        List<Integer> l10 = Arrays.asList(null, 2, 4, null, 6);
        System.out.println("\nList9: " + l9);
        System.out.println("List10: " + l10);
        System.out.println("Merged with nulls: " + sol.mergeSortedLists(l9, l10)); // [1, 2, 3, 4, 5, 6]
    }
}
