package com.svedentsov.aqa.tasks.arrays_lists;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Решение задачи №15: Найти отсутствующее число в последовательности.
 */
public class Task15_FindMissingNumber {

    /**
     * Находит отсутствующее число в массиве, который должен содержать
     * все числа от 1 до n, кроме одного.
     * Метод использует формулу суммы арифметической прогрессии.
     * Сложность O(n) по времени, O(1) по памяти.
     * Может произойти переполнение long при вычислении суммы, если n очень большое.
     *
     * @param numbers Массив с уникальными числами от 1 до n, кроме одного. Не должен быть null.
     * @param n       Верхняя граница последовательности (максимальное ожидаемое число).
     * @return Отсутствующее число.
     * @throws IllegalArgumentException если массив null или если n некорректно (например, n < 1).
     */
    public int findMissingNumberSum(int[] numbers, int n) {
        if (numbers == null) {
            throw new IllegalArgumentException("Input array cannot be null.");
        }
        if (n < 1) {
            throw new IllegalArgumentException("Upper bound 'n' must be at least 1.");
        }
        // Длина массива должна быть n-1
        if (numbers.length != n - 1) {
            System.err.println("Warning: Array length (" + numbers.length + ") does not match expected length (n-1 = " + (n - 1) + "). Result might be incorrect.");
            // Можно бросить исключение или продолжить, но результат не гарантирован
            // throw new IllegalArgumentException("Array length mismatch.");
        }

        // Ожидаемая сумма всех чисел от 1 до n: n * (n + 1) / 2
        long expectedSum = (long) n * (n + 1) / 2;

        // Фактическая сумма чисел в массиве
        long actualSum = 0;
        for (int number : numbers) {
            actualSum += number;
        }

        // Разница между ожидаемой и фактической суммой - это пропущенное число
        long missing = expectedSum - actualSum;

        // Проверка, что результат в допустимом диапазоне (1..n)
        if (missing < 1 || missing > n) {
            // Это может произойти, если входные данные некорректны (дубликаты, числа вне диапазона)
            System.err.println("Warning: Calculated missing number (" + missing + ") is outside the expected range [1, " + n + "]. Input data might be invalid.");
            // Возвращаем как есть или бросаем исключение
        }

        return (int) missing;
    }

    /**
     * Находит отсутствующее число в массиве с использованием операции XOR.
     * XOR всех чисел от 1 до n, а затем XOR результата со всеми числами в массиве.
     * Сложность O(n) по времени, O(1) по памяти. Безопасен от переполнения.
     *
     * @param numbers Массив с уникальными числами от 1 до n, кроме одного. Не должен быть null.
     * @param n       Верхняя граница последовательности.
     * @return Отсутствующее число.
     * @throws IllegalArgumentException если массив null или n < 1.
     */
    public int findMissingNumberXor(int[] numbers, int n) {
        if (numbers == null) {
            throw new IllegalArgumentException("Input array cannot be null.");
        }
        if (n < 1) {
            throw new IllegalArgumentException("Upper bound 'n' must be at least 1.");
        }
        // Проверка длины не обязательна для XOR, но может помочь выявить неверные входные данные
        if (numbers.length != n - 1) {
            System.err.println("Warning: Array length (" + numbers.length + ") does not match expected length (n-1 = " + (n - 1) + ").");
        }

        // XOR всех чисел от 1 до n
        int xorAllExpected = 0;
        for (int i = 1; i <= n; i++) {
            xorAllExpected ^= i;
        }

        // XOR всех чисел в массиве
        int xorActual = 0;
        for (int number : numbers) {
            xorActual ^= number;
        }

        // Результат XOR двух "сумм" - это пропущенное число
        return xorAllExpected ^ xorActual;
    }

    /**
     * Находит отсутствующее число с использованием множества (Set).
     * Менее эффективно по памяти (O(n)), чем предыдущие методы.
     *
     * @param numbers Массив чисел.
     * @param n       Верхняя граница.
     * @return Отсутствующее число.
     * @throws IllegalArgumentException если массив null, n < 1 или отсутствующее число не найдено.
     */
    public int findMissingNumberSet(int[] numbers, int n) {
        if (numbers == null) {
            throw new IllegalArgumentException("Input array cannot be null.");
        }
        if (n < 1) {
            throw new IllegalArgumentException("Upper bound 'n' must be at least 1.");
        }

        Set<Integer> numSet = new HashSet<>();
        for (int num : numbers) {
            numSet.add(num);
        }

        // Проверяем, какое число от 1 до n отсутствует в сете
        for (int i = 1; i <= n; i++) {
            if (!numSet.contains(i)) {
                return i;
            }
        }
        // Если все числа от 1 до n присутствуют (или n=0 и массив пуст),
        // то входные данные некорректны согласно условию задачи.
        throw new IllegalArgumentException("No missing number found in the range [1, " + n + "] or invalid input.");
    }

    /**
     * Точка входа для демонстрации работы методов поиска пропущенного числа.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task15_FindMissingNumber sol = new Task15_FindMissingNumber();
        int[] arr1 = {1, 2, 4, 5};
        int n1 = 5;
        System.out.println("Missing in " + Arrays.toString(arr1) + " (n=" + n1 + ")");
        System.out.println("  Sum method: " + sol.findMissingNumberSum(arr1, n1)); // 3
        System.out.println("  XOR method: " + sol.findMissingNumberXor(arr1, n1)); // 3
        System.out.println("  Set method: " + sol.findMissingNumberSet(arr1, n1)); // 3

        int[] arr2 = {3, 1, 2, 5}; // Порядок не важен
        int n2 = 5;
        System.out.println("\nMissing in " + Arrays.toString(arr2) + " (n=" + n2 + ")");
        System.out.println("  Sum method: " + sol.findMissingNumberSum(arr2, n2)); // 4
        System.out.println("  XOR method: " + sol.findMissingNumberXor(arr2, n2)); // 4
        System.out.println("  Set method: " + sol.findMissingNumberSet(arr2, n2)); // 4

        int[] arr3 = {1};
        int n3 = 2;
        System.out.println("\nMissing in " + Arrays.toString(arr3) + " (n=" + n3 + ")");
        System.out.println("  Sum method: " + sol.findMissingNumberSum(arr3, n3)); // 2
        System.out.println("  XOR method: " + sol.findMissingNumberXor(arr3, n3)); // 2
        System.out.println("  Set method: " + sol.findMissingNumberSet(arr3, n3)); // 2

        int[] arr4 = {}; // n=1, пропущено 1
        int n4 = 1;
        System.out.println("\nMissing in " + Arrays.toString(arr4) + " (n=" + n4 + ")");
        System.out.println("  Sum method: " + sol.findMissingNumberSum(arr4, n4)); // 1
        System.out.println("  XOR method: " + sol.findMissingNumberXor(arr4, n4)); // 1
        System.out.println("  Set method: " + sol.findMissingNumberSet(arr4, n4)); // 1
    }
}
