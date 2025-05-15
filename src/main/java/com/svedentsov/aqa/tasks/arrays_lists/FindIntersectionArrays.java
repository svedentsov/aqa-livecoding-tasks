package com.svedentsov.aqa.tasks.arrays_lists;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Решение задачи №22: Найти пересечение двух массивов/списков.
 * Описание: Написать функцию, возвращающую общие элементы для двух коллекций.
 * (Проверяет: работа с коллекциями, Set, циклы)
 * Задание: Напишите метод `Set<Integer> findIntersection(int[] arr1, int[] arr2)`,
 * который возвращает множество (`Set`) уникальных элементов, присутствующих
 * в обоих массивах `arr1` и `arr2`.
 * Пример: `findIntersection(new int[]{1, 2, 2, 1}, new int[]{2, 2})` -> `{2}`.
 * `findIntersection(new int[]{4, 9, 5}, new int[]{9, 4, 9, 8, 4})` -> `{4, 9}`.
 */
public class FindIntersectionArrays {

    /**
     * Находит пересечение (общие уникальные элементы) двух массивов целых чисел.
     * Использует HashSet для эффективного поиска (O(1) в среднем).
     * Оптимизация: элементы меньшего массива помещаются в Set для экономии памяти.
     * Сложность: O(m + n) по времени, O(min(m, n)) по памяти.
     *
     * @param arr1 Первый массив целых чисел. Может быть null.
     * @param arr2 Второй массив целых чисел. Может быть null.
     * @return Множество (HashSet) целых чисел, содержащее уникальные элементы,
     * присутствующие в обоих массивах. Возвращает пустое множество, если
     * пересечения нет или хотя бы один из массивов null/пуст.
     */
    public Set<Integer> findIntersection(int[] arr1, int[] arr2) {
        // Шаг 1: Обработка null и пустых массивов
        if (arr1 == null || arr2 == null || arr1.length == 0 || arr2.length == 0) {
            return Collections.emptySet(); // Используем неизменяемое пустое множество
        }

        // Шаг 2: Оптимизация - выбираем меньший массив для создания Set
        int[] smallerArr = (arr1.length < arr2.length) ? arr1 : arr2;
        int[] largerArr = (arr1.length < arr2.length) ? arr2 : arr1;

        // Шаг 3: Помещаем элементы меньшего массива в Set
        Set<Integer> elementSet = new HashSet<>((int) (smallerArr.length / 0.75f) + 1);
        for (int num : smallerArr) {
            elementSet.add(num);
        }

        // Шаг 4: Множество для хранения результата пересечения
        Set<Integer> intersection = new HashSet<>();

        // Шаг 5: Проходим по большему массиву и ищем совпадения в Set
        for (int num : largerArr) {
            if (elementSet.contains(num)) {
                intersection.add(num);
            }
        }
        return intersection;
    }

    /**
     * Находит пересечение с использованием Stream API.
     * Может быть менее эффективен по времени и памяти для больших примитивных массивов
     * из-за boxing/unboxing и создания промежуточных коллекций.
     *
     * @param arr1 Первый массив. Может быть null.
     * @param arr2 Второй массив. Может быть null.
     * @return Множество пересекающихся элементов. Возвращает пустое множество,
     * если пересечения нет или хотя бы один из массивов null/пуст.
     */
    public Set<Integer> findIntersectionStream(int[] arr1, int[] arr2) {
        if (arr1 == null || arr2 == null || arr1.length == 0 || arr2.length == 0) {
            return Collections.emptySet();
        }
        // Преобразуем первый массив в Set<Integer>
        Set<Integer> set1 = Arrays.stream(arr1)
                .boxed() // int -> Integer
                .collect(Collectors.toSet());

        // Фильтруем второй массив: оставляем только те элементы, что есть в set1
        return Arrays.stream(arr2)
                .filter(num -> set1.contains(num)) // Оставляем общие
                .boxed() // int -> Integer
                .collect(Collectors.toSet()); // Собираем в Set
    }
}
