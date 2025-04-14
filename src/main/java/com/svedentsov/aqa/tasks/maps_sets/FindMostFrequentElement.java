package com.svedentsov.aqa.tasks.maps_sets;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Решение задачи №32: Найти самый часто встречающийся элемент в массиве.
 * <p>
 * Описание: Написать функцию для поиска элемента с максимальным числом вхождений.
 * (Проверяет: Map, циклы, сравнения)
 * <p>
 * Задание: Напишите метод `int findMostFrequentElement(int[] numbers)`, который
 * находит и возвращает элемент, который встречается в массиве `numbers` чаще всего.
 * Если таких элементов несколько, верните любой из них. Обработайте случай
 * пустого или null массива (например, выбросив исключение).
 * <p>
 * Пример: `findMostFrequentElement(new int[]{1, 3, 2, 1, 4, 1, 3})` -> `1`.
 * `findMostFrequentElement(new int[]{1, 2, 3})` -> `1` (или `2`, или `3`).
 */
public class FindMostFrequentElement {

    /**
     * Находит элемент, который встречается в массиве целых чисел чаще всего.
     * Если несколько элементов имеют одинаковую максимальную частоту,
     * возвращается первый из них, встреченный при переборе карты частот
     * (порядок перебора HashMap не гарантирован, но стабилен в рамках одного запуска).
     * Использует HashMap для подсчета частот элементов.
     * <p>
     * Сложность: O(n) по времени (один проход для подсчета, один для поиска максимума).
     * Сложность: O(k) по памяти, где k - количество уникальных элементов в массиве.
     *
     * @param numbers Массив целых чисел.
     * @return Элемент с максимальной частотой встречаемости.
     * @throws IllegalArgumentException если массив null или пуст.
     */
    public int findMostFrequentElement(int[] numbers) {
        // Проверка на недопустимый ввод
        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }

        // Шаг 1: Подсчет частот элементов
        // Карта: {число: частота}
        Map<Integer, Integer> counts = new HashMap<>();
        for (int number : numbers) {
            // counts.merge(number, 1, Integer::sum); // Альтернатива с merge
            counts.put(number, counts.getOrDefault(number, 0) + 1);
        }

        // Шаг 2: Поиск элемента с максимальной частотой
        // Инициализация переменных для отслеживания максимума
        Integer mostFrequentElement = null; // Используем Integer, т.к. Map работает с объектами
        int maxCount = -1; // Частота точно будет >= 0

        // Итерация по записям карты (парам ключ-значение)
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            int currentElement = entry.getKey();
            int currentCount = entry.getValue();

            // Если текущая частота больше максимальной найденной
            if (currentCount > maxCount) {
                maxCount = currentCount; // Обновляем максимальную частоту
                mostFrequentElement = currentElement; // Обновляем самый частый элемент
            }
            // При равенстве частот оставляем первый найденный (mostFrequentElement не меняется)
        }

        // В случае непустого массива mostFrequentElement не может остаться null
        return mostFrequentElement;
    }

    /**
     * Точка входа для демонстрации работы метода поиска самого частого элемента.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        FindMostFrequentElement sol = new FindMostFrequentElement();

        runMostFrequentTest(sol, new int[]{1, 3, 2, 1, 4, 1, 3}, "Стандартный случай 1"); // 1
        runMostFrequentTest(sol, new int[]{1, 2, 3, 2, 3, 2, 3, 3}, "Стандартный случай 2"); // 3
        runMostFrequentTest(sol, new int[]{1, 2, 3, 4, 5}, "Все уникальные"); // Любой, например 1
        runMostFrequentTest(sol, new int[]{7, 7, 7}, "Все одинаковые"); // 7
        runMostFrequentTest(sol, new int[]{5}, "Один элемент"); // 5
        runMostFrequentTest(sol, new int[]{-1, -1, 0, 0, 0, 2}, "С нулем и отрицательными"); // 0
        runMostFrequentTest(sol, new int[]{10, 20, 10, 20, 10}, "Несколько частых"); // 10
        runMostFrequentTest(sol, new int[]{1, 1, 2, 2}, "Два одинаково частых"); // 1 или 2

        // Тесты исключений
        runMostFrequentTest(sol, new int[]{}, "Пустой массив"); // Exception
        runMostFrequentTest(sol, null, "Null массив"); // Exception
    }

    /**
     * Вспомогательный метод для тестирования findMostFrequentElement.
     *
     * @param sol         Экземпляр решателя.
     * @param arr         Тестовый массив.
     * @param description Описание теста.
     */
    private static void runMostFrequentTest(FindMostFrequentElement sol, int[] arr, String description) {
        System.out.println("\n--- " + description + " ---");
        String arrString = (arr == null ? "null" : Arrays.toString(arr));
        System.out.println("Input array: " + arrString);
        try {
            int result = sol.findMostFrequentElement(arr);
            System.out.println("Most Frequent: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("Most Frequent: Error - " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Most Frequent: Unexpected Error - " + e.getMessage());
        }
    }
}
