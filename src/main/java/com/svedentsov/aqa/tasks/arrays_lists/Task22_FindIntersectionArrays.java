package com.svedentsov.aqa.tasks.arrays_lists; // Или maps_sets

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Решение задачи №22: Найти пересечение двух массивов/списков.
 */
public class Task22_FindIntersectionArrays {

    /**
     * Находит пересечение (общие уникальные элементы) двух массивов целых чисел.
     * Использует HashSet для эффективного поиска (O(1) в среднем).
     * Сложность O(m + n) по времени, O(m) или O(min(m,n)) по памяти (если помещать меньший массив в Set).
     *
     * @param arr1 Первый массив целых чисел. Может быть null.
     * @param arr2 Второй массив целых чисел. Может быть null.
     * @return Множество (Set) целых чисел, содержащее уникальные элементы,
     * присутствующие в обоих массивах. Возвращает пустое множество, если
     * пересечения нет или один из массивов null/пуст.
     */
    public Set<Integer> findIntersection(int[] arr1, int[] arr2) {
        // Обработка null и пустых массивов
        if (arr1 == null || arr2 == null || arr1.length == 0 || arr2.length == 0) {
            return new HashSet<>();
        }

        // Оптимизация: помещаем элементы меньшего массива в Set для экономии памяти
        int[] smallerArr = (arr1.length < arr2.length) ? arr1 : arr2;
        int[] largerArr = (arr1.length < arr2.length) ? arr2 : arr1;

        // Помещаем все элементы меньшего массива в Set
        Set<Integer> elementSet = new HashSet<>();
        for (int num : smallerArr) {
            elementSet.add(num);
        }

        // Множество для хранения результата пересечения
        Set<Integer> intersection = new HashSet<>();

        // Проходим по большему массиву и проверяем наличие его элементов в set
        for (int num : largerArr) {
            if (elementSet.contains(num)) {
                intersection.add(num); // Добавляем общий элемент в результат
            }
        }
        return intersection;
    }

    /**
     * Находит пересечение с использованием Stream API.
     * Менее эффективен, чем метод с Set, если массивы большие.
     *
     * @param arr1 Первый массив.
     * @param arr2 Второй массив.
     * @return Множество пересекающихся элементов.
     */
    public Set<Integer> findIntersectionStream(int[] arr1, int[] arr2) {
        if (arr1 == null || arr2 == null || arr1.length == 0 || arr2.length == 0) {
            return new HashSet<>();
        }
        // Преобразуем первый массив в Set
        Set<Integer> set1 = Arrays.stream(arr1).boxed().collect(Collectors.toSet());

        // Фильтруем второй массив, оставляя только те элементы, что есть в set1
        return Arrays.stream(arr2)
                .filter(num -> set1.contains(num)) // Оставляем только общие
                .boxed() // Преобразуем IntStream в Stream<Integer>
                .collect(Collectors.toSet()); // Собираем в Set (уникальность гарантирована)
    }

    /**
     * Точка входа для демонстрации работы методов поиска пересечения.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task22_FindIntersectionArrays sol = new Task22_FindIntersectionArrays();

        int[] a1 = {1, 2, 2, 1};
        int[] b1 = {2, 2};
        System.out.println("Intersection of " + Arrays.toString(a1) + " and " + Arrays.toString(b1) + ":");
        System.out.println("  Set Method: " + sol.findIntersection(a1, b1)); // [2]
        System.out.println("  Stream Method: " + sol.findIntersectionStream(a1, b1)); // [2]

        int[] a2 = {4, 9, 5};
        int[] b2 = {9, 4, 9, 8, 4};
        System.out.println("\nIntersection of " + Arrays.toString(a2) + " and " + Arrays.toString(b2) + ":");
        // Порядок элементов в Set не гарантирован
        System.out.println("  Set Method: " + sol.findIntersection(a2, b2)); // [4, 9]
        System.out.println("  Stream Method: " + sol.findIntersectionStream(a2, b2)); // [4, 9]

        int[] a3 = {1, 2, 3};
        int[] b3 = {4, 5, 6};
        System.out.println("\nIntersection of " + Arrays.toString(a3) + " and " + Arrays.toString(b3) + ":");
        System.out.println("  Set Method: " + sol.findIntersection(a3, b3)); // []
        System.out.println("  Stream Method: " + sol.findIntersectionStream(a3, b3)); // []

        int[] a4 = {};
        int[] b4 = {1, 2};
        System.out.println("\nIntersection of " + Arrays.toString(a4) + " and " + Arrays.toString(b4) + ":");
        System.out.println("  Set Method: " + sol.findIntersection(a4, b4)); // []
        System.out.println("  Stream Method: " + sol.findIntersectionStream(a4, b4)); // []

        System.out.println("\nIntersection of null and " + Arrays.toString(b4) + ":");
        System.out.println("  Set Method: " + sol.findIntersection(null, b4)); // []
        System.out.println("  Stream Method: " + sol.findIntersectionStream(null, b4)); // []

    }
}
