package com.svedentsov.aqa.tasks.maps_sets;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Решение задачи №32: Найти самый часто встречающийся элемент в массиве.
 */
public class Task32_FindMostFrequentElement {

    /**
     * Находит элемент, который встречается в массиве целых чисел чаще всего.
     * Если несколько элементов имеют одинаковую максимальную частоту,
     * возвращается первый из них, встреченный при переборе карты частот
     * (порядок перебора HashMap не гарантирован).
     * Использует HashMap для подсчета частот элементов.
     * Сложность O(n) по времени (один проход для подсчета, один для поиска максимума),
     * O(k) по памяти, где k - количество уникальных элементов в массиве.
     *
     * @param numbers Массив целых чисел.
     * @return Элемент с максимальной частотой встречаемости.
     * @throws IllegalArgumentException если массив null или пуст.
     */
    public int findMostFrequentElement(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }

        // Карта для подсчета частот: {число: частота}
        Map<Integer, Integer> counts = new HashMap<>();
        // Проход 1: Считаем частоты
        for (int number : numbers) {
            counts.put(number, counts.getOrDefault(number, 0) + 1);
        }

        // Проход 2: Ищем элемент с максимальной частотой
        // Инициализируем переменные. Можно взять первый элемент массива
        // как начальное приближение, но безопаснее пройти по карте.
        Integer mostFrequentElement = null; // Используем Integer для обработки случая пустого counts (невозможно при проверке numbers.length > 0)
        int maxCount = -1; // Инициализируем частоту значением, которое точно будет меньше любой реальной частоты

        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            int currentElement = entry.getKey();
            int currentCount = entry.getValue();

            // Если нашли элемент с большей частотой
            if (currentCount > maxCount) {
                maxCount = currentCount;
                mostFrequentElement = currentElement;
            }
            // Если частоты равны, mostFrequentElement не меняется (возвращаем первый встреченный)
        }

        // mostFrequentElement не может быть null, так как массив не пуст
        return mostFrequentElement;
    }

    /**
     * Точка входа для демонстрации работы метода поиска самого частого элемента.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task32_FindMostFrequentElement sol = new Task32_FindMostFrequentElement();

        int[][] testArrays = {
                {1, 3, 2, 1, 4, 1, 3},       // 1 (freq 3)
                {1, 2, 3, 2, 3, 2, 3, 3},    // 3 (freq 4)
                {1, 2, 3, 4, 5},            // 1 (или любой другой, freq 1)
                {7, 7, 7},                  // 7 (freq 3)
                {5},                        // 5 (freq 1)
                {-1, -1, 0, 0, 0, 2},       // 0 (freq 3)
                {10, 20, 10, 20, 10}        // 10 (freq 3)
        };
        int[] expected = {1, 3, 1, 7, 5, 0, 10}; // Ожидаемый результат (для arr3 может быть любой)

        for (int i = 0; i < testArrays.length; i++) {
            try {
                int result = sol.findMostFrequentElement(testArrays[i]);
                System.out.println("Most frequent in " + Arrays.toString(testArrays[i]) + ": " + result);
                // Для случая равных частот, сравниваем с ожидаемым или проверяем вхождение
                if (expected[i] != 1 && result != expected[i] && testArrays[i].length > 1 && !(testArrays[i].length == 5 && i == 2)) { // Костыль для arr3
                    System.err.println("   Mismatch! Expected (one of): " + expected[i]);
                } else if (testArrays[i].length == 5 && i == 2) {
                    System.out.println("   (Expected any element)"); // Для arr3
                }

            } catch (IllegalArgumentException e) {
                System.err.println("Error processing " + Arrays.toString(testArrays[i]) + ": " + e.getMessage());
            }
        }

        // Тест на пустом массиве
        try {
            sol.findMostFrequentElement(new int[]{});
        } catch (IllegalArgumentException e) {
            System.out.println("\nError on empty array: " + e.getMessage());
        }
        // Тест на null массиве
        try {
            sol.findMostFrequentElement(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Error on null array: " + e.getMessage());
        }
    }
}
