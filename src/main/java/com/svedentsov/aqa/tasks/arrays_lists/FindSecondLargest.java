package com.svedentsov.aqa.tasks.arrays_lists;

/**
 * Решение задачи №19: Найти второй по величине элемент в массиве.
 * Описание: Написать функцию для поиска второго максимального элемента.
 * (Проверяет: циклы, сравнения, обработка крайних случаев)
 * Задание: Напишите метод `int findSecondLargest(int[] numbers)`, который
 * находит и возвращает второе по величине число в массиве `numbers`.
 * Если такого элемента нет (например, все элементы одинаковы или массив
 * слишком мал), верните `Integer.MIN_VALUE`.
 * (Возвращаем `Integer.MIN_VALUE` согласно заданию и примерам).
 * Пример: `findSecondLargest(new int[]{1, 5, 2, 9, 3, 9})` -> `5`.
 * `findSecondLargest(new int[]{3, 3, 3})` -> `Integer.MIN_VALUE`.
 */
public class FindSecondLargest {

    /**
     * Находит второе по величине число в массиве целых чисел за один проход.
     * Отслеживает два наибольших различных элемента: `largest` и `secondLargest`.
     * Сложность O(n) по времени, O(1) по памяти.
     *
     * @param numbers Массив целых чисел. Не должен быть null.
     * @return Второе по величине число в массиве. Возвращает {@code Integer.MIN_VALUE},
     * если второго по величине элемента нет (например, массив содержит менее 2
     * уникальных элементов).
     * @throws IllegalArgumentException если массив null или пуст.
     */
    public int findSecondLargest(int[] numbers) {
        // Проверка на null или пустой массив
        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }
        // Если в массиве только один элемент, второго по величине нет
        if (numbers.length == 1) {
            return Integer.MIN_VALUE;
        }

        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;

        // Один проход по массиву
        for (int number : numbers) {
            if (number > largest) {
                // Нашли новый максимум. Предыдущий максимум становится вторым.
                secondLargest = largest;
                largest = number;
            } else if (number > secondLargest && number < largest) {
                // Нашли число больше текущего второго, но меньше максимума.
                secondLargest = number;
            }
        }

        // Логика корректно возвращает MIN_VALUE, если второго по величине элемента
        // не было найдено (например, все элементы одинаковые, или массив состоит
        // только из largest и элементов <= MIN_VALUE).
        return secondLargest;
    }
}
