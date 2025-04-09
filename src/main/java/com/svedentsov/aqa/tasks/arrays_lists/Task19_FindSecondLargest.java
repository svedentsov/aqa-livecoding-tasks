package com.svedentsov.aqa.tasks.arrays_lists;

import java.util.Arrays;

/**
 * Решение задачи №19: Найти второй по величине элемент в массиве.
 */
public class Task19_FindSecondLargest {

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
        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }
        // Если в массиве только один элемент, второго по величине нет.
        if (numbers.length == 1) {
            return Integer.MIN_VALUE; // Согласно условию
        }

        // Инициализируем largest и secondLargest
        // Можно инициализировать первыми двумя элементами (если они различны)
        // или использовать Integer.MIN_VALUE. Использование MIN_VALUE проще,
        // но требует аккуратной обработки граничных случаев.
        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;
        boolean foundMinValue = false; // Флаг на случай, если MIN_VALUE есть в массиве

        // Ищем первый largest
        for (int number : numbers) {
            if (number > largest) {
                largest = number;
            }
            if (number == Integer.MIN_VALUE) {
                foundMinValue = true;
            }
        }

        // Ищем secondLargest, который меньше largest
        for (int number : numbers) {
            if (number > secondLargest && number < largest) {
                secondLargest = number;
            }
        }

        // Обработка случая, когда все элементы одинаковы (или только largest и MIN_VALUE)
        // Если secondLargest так и не обновился и остался MIN_VALUE,
        // нужно проверить, был ли MIN_VALUE действительно вторым по величине,
        // или второго по величине просто не было.
        if (secondLargest == Integer.MIN_VALUE) {
            // Если largest тоже MIN_VALUE, значит все элементы были MIN_VALUE
            if (largest == Integer.MIN_VALUE) {
                return Integer.MIN_VALUE;
            }
            // Если largest НЕ MIN_VALUE, но secondLargest = MIN_VALUE,
            // проверяем, был ли MIN_VALUE в массиве И был ли он единственным другим значением.
            if (foundMinValue) {
                int countNonMinValues = 0;
                for (int number : numbers) {
                    if (number != Integer.MIN_VALUE) {
                        countNonMinValues++;
                    }
                }
                // Если кроме MIN_VALUE был только один уникальный largest, то MIN_VALUE - второй.
                // Но если count == 1 (только largest), второго нет.
                // Если count > 1, но все они равны largest, то второго нет.
                int uniqueCount = 0;
                for (int number : numbers) {
                    if (number != largest && number != Integer.MIN_VALUE) {
                        uniqueCount++;
                        break; // Нашли другое значение
                    }
                }
                if (uniqueCount == 0) { // Только largest и MIN_VALUE
                    // Если countNonMinValues > 0, то MIN_VALUE и есть второй по величине
                    if (countNonMinValues > 0) return Integer.MIN_VALUE;
                    else return Integer.MIN_VALUE; // Все были MIN_VALUE
                }
                // Если же был MIN_VALUE и еще какие-то числа, отличные от largest,
                // то secondLargest должен был обновиться. Раз не обновился, значит
                // все остальные числа были < MIN_VALUE (невозможно) или равны largest.
                // => Второго по величине нет.
                else return Integer.MIN_VALUE;

            } else {
                // Если MIN_VALUE не было, но secondLargest остался MIN_VALUE,
                // значит все элементы были равны largest.
                return Integer.MIN_VALUE;
            }
        }

        return secondLargest;
    }

    /**
     * Точка входа для демонстрации работы метода поиска второго по величине элемента.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task19_FindSecondLargest sol = new Task19_FindSecondLargest();
        System.out.println(Arrays.toString(new int[]{1, 5, 2, 9, 3, 9}) + " -> " + sol.findSecondLargest(new int[]{1, 5, 2, 9, 3, 9})); // 5
        System.out.println(Arrays.toString(new int[]{10, 2, 8, 10, 5}) + " -> " + sol.findSecondLargest(new int[]{10, 2, 8, 10, 5})); // 8
        System.out.println(Arrays.toString(new int[]{3, 3, 3}) + " -> " + sol.findSecondLargest(new int[]{3, 3, 3})); // -2147483648
        System.out.println(Arrays.toString(new int[]{5, 1}) + " -> " + sol.findSecondLargest(new int[]{5, 1})); // 1
        System.out.println(Arrays.toString(new int[]{5, 5}) + " -> " + sol.findSecondLargest(new int[]{5, 5})); // -2147483648
        System.out.println(Arrays.toString(new int[]{Integer.MAX_VALUE, 1, Integer.MAX_VALUE}) + " -> " + sol.findSecondLargest(new int[]{Integer.MAX_VALUE, 1, Integer.MAX_VALUE})); // 1
        System.out.println(Arrays.toString(new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE}) + " -> " + sol.findSecondLargest(new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE})); // -2147483648
        System.out.println(Arrays.toString(new int[]{1, Integer.MIN_VALUE}) + " -> " + sol.findSecondLargest(new int[]{1, Integer.MIN_VALUE})); // -2147483648
        System.out.println(Arrays.toString(new int[]{1, 2, Integer.MIN_VALUE}) + " -> " + sol.findSecondLargest(new int[]{1, 2, Integer.MIN_VALUE})); // 1
        System.out.println(Arrays.toString(new int[]{-1, -2, -3}) + " -> " + sol.findSecondLargest(new int[]{-1, -2, -3})); // -2

        try {
            System.out.println(Arrays.toString(new int[]{5}) + " -> " + sol.findSecondLargest(new int[]{5})); // -2147483648 (т.к. length < 2)
            sol.findSecondLargest(new int[]{}); // Выбросит исключение
        } catch (IllegalArgumentException e) {
            System.out.println("Error for empty array: " + e.getMessage());
        }
        try {
            sol.findSecondLargest(null); // Выбросит исключение
        } catch (IllegalArgumentException e) {
            System.out.println("Error for null array: " + e.getMessage());
        }
    }
}
