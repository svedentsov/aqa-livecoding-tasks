package com.svedentsov.aqa.tasks.arrays_lists;

import java.util.ArrayList;
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

        // Результирующий список.
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
                continue;
            }
            // Пропускаем null элементы во втором списке
            if (val2 == null) {
                j++;
                continue;
            }

            // Сравниваем текущие не-null элементы и добавляем меньший в результат
            if (val1.compareTo(val2) <= 0) {
                mergedList.add(val1);
                i++;
            } else {
                mergedList.add(val2);
                j++;
            }
        }

        // Добавляем оставшиеся не-null элементы из первого списка (если есть)
        while (i < nonNullList1.size()) {
            Integer val1 = nonNullList1.get(i);
            if (val1 != null) {
                mergedList.add(val1);
            }
            i++;
        }

        // Добавляем оставшиеся не-null элементы из второго списка (если есть)
        while (j < nonNullList2.size()) {
            Integer val2 = nonNullList2.get(j);
            if (val2 != null) {
                mergedList.add(val2);
            }
            j++;
        }

        return mergedList;
    }
}
