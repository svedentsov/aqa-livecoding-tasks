package com.svedentsov.aqa.tasks.arrays_lists; // Или maps_sets

import java.util.*;
import java.util.stream.Collectors;

/**
 * Решение задачи №22: Найти пересечение двух массивов/списков.
 * <p>
 * Описание: Написать функцию, возвращающую общие элементы для двух коллекций.
 * (Проверяет: работа с коллекциями, Set, циклы)
 * <p>
 * Задание: Напишите метод `Set<Integer> findIntersection(int[] arr1, int[] arr2)`,
 * который возвращает множество (`Set`) уникальных элементов, присутствующих
 * в обоих массивах `arr1` и `arr2`.
 * <p>
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
        // Указываем начальную емкость, чтобы уменьшить количество рехеширований
        Set<Integer> elementSet = new HashSet<>((int) (smallerArr.length / 0.75f) + 1);
        for (int num : smallerArr) {
            elementSet.add(num);
        }

        // Шаг 4: Множество для хранения результата пересечения
        Set<Integer> intersection = new HashSet<>();

        // Шаг 5: Проходим по большему массиву и ищем совпадения в Set
        for (int num : largerArr) {
            // contains() работает за O(1) в среднем
            if (elementSet.contains(num)) {
                intersection.add(num); // Добавляем общий элемент в результат (Set сам обрабатывает дубликаты)
            }
        }
        return intersection;
    }

    /**
     * Находит пересечение с использованием Stream API.
     * Может быть менее эффективен по времени и памяти для больших примитивных массивов
     * из-за boxing/unboxing и создания промежуточных коллекций.
     *
     * @param arr1 Первый массив.
     * @param arr2 Второй массив.
     * @return Множество пересекающихся элементов.
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

    /**
     * Точка входа для демонстрации работы методов поиска пересечения.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        FindIntersectionArrays sol = new FindIntersectionArrays();

        runIntersectionTest(sol, new int[]{1, 2, 2, 1}, new int[]{2, 2}, "Пример 1"); // [2]
        runIntersectionTest(sol, new int[]{4, 9, 5}, new int[]{9, 4, 9, 8, 4}, "Пример 2"); // [4, 9]
        runIntersectionTest(sol, new int[]{1, 2, 3}, new int[]{4, 5, 6}, "Нет пересечения"); // []
        runIntersectionTest(sol, new int[]{}, new int[]{1, 2}, "Первый пустой"); // []
        runIntersectionTest(sol, new int[]{1, 2}, new int[]{}, "Второй пустой"); // []
        runIntersectionTest(sol, new int[]{}, new int[]{}, "Оба пустые"); // []
        runIntersectionTest(sol, null, new int[]{1, 2}, "Первый null"); // []
        runIntersectionTest(sol, new int[]{1, 2}, null, "Второй null"); // []
        runIntersectionTest(sol, null, null, "Оба null"); // []
        runIntersectionTest(sol, new int[]{1, 2, 3, 4}, new int[]{2, 4, 6, 8}, "Частичное пересечение"); // [2, 4]
        runIntersectionTest(sol, new int[]{-1, 0, 1}, new int[]{0, 1, 2, -1}, "С нулем и отрицательными"); // [-1, 0, 1]
        runIntersectionTest(sol, new int[]{5, 5, 5}, new int[]{5, 0}, "Дубликаты в одном"); // [5]
    }

    /**
     * Вспомогательный метод для тестирования поиска пересечения.
     *
     * @param sol         Экземпляр решателя.
     * @param arr1        Первый массив.
     * @param arr2        Второй массив.
     * @param description Описание теста.
     */
    private static void runIntersectionTest(FindIntersectionArrays sol, int[] arr1, int[] arr2, String description) {
        System.out.println("\n--- " + description + " ---");
        String a1Str = (arr1 == null ? "null" : Arrays.toString(arr1));
        String a2Str = (arr2 == null ? "null" : Arrays.toString(arr2));
        System.out.println("Array 1: " + a1Str);
        System.out.println("Array 2: " + a2Str);
        try {
            // Используем TreeSet для вывода отсортированного результата
            Set<Integer> resultManual = new TreeSet<>(sol.findIntersection(arr1, arr2));
            System.out.println("  Intersection (Set):    " + resultManual);
        } catch (Exception e) {
            System.out.println("  Intersection (Set):    Error - " + e.getMessage());
        }
        try {
            Set<Integer> resultStream = new TreeSet<>(sol.findIntersectionStream(arr1, arr2));
            System.out.println("  Intersection (Stream): " + resultStream);
        } catch (Exception e) {
            System.out.println("  Intersection (Stream): Error - " + e.getMessage());
        }
    }
}
