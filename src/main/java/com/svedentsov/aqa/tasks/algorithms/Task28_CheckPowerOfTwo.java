package com.svedentsov.aqa.tasks.algorithms;

/**
 * Решение задачи №28: Проверить, является ли число степенью двойки.
 */
public class Task28_CheckPowerOfTwo {

    /**
     * Проверяет, является ли положительное целое число n степенью двойки (1, 2, 4, 8...).
     * Использует эффективный битовый трюк: (n & (n - 1)) == 0 для n > 0.
     *
     * @param n Целое число для проверки.
     * @return {@code true}, если n является положительной степенью двойки, {@code false} в противном случае.
     */
    public boolean isPowerOfTwoBitwise(int n) {
        // 1. Исключаем 0 и отрицательные числа.
        // 2. Проверяем битовый трюк: степени двойки имеют только один установленный бит.
        //    n-1 инвертирует все биты справа от этого бита. Побитовое И даст 0.
        return (n > 0) && ((n & (n - 1)) == 0);
    }

    /**
     * Проверяет, является ли положительное целое число n степенью двойки.
     * Использует итеративное деление на 2.
     *
     * @param n Целое число для проверки.
     * @return {@code true}, если n является положительной степенью двойки, {@code false} в противном случае.
     */
    public boolean isPowerOfTwoIterative(int n) {
        if (n <= 0) {
            return false;
        }
        // Делим на 2, пока число четное
        while (n % 2 == 0) {
            n /= 2;
        }
        // Если в результате осталась 1, значит, исходное число было степенью двойки
        return n == 1;
    }

    /**
     * Точка входа для демонстрации работы методов проверки на степень двойки.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task28_CheckPowerOfTwo sol = new Task28_CheckPowerOfTwo();
        int[] testNumbers = {1, 2, 3, 4, 8, 10, 16, 31, 32, 0, -4, 1024, 1023};
        boolean[] expected = {true, true, false, true, true, false, true, false, true, false, false, true, false};

        System.out.println("--- Bitwise Method ---");
        for (int i = 0; i < testNumbers.length; i++) {
            boolean result = sol.isPowerOfTwoBitwise(testNumbers[i]);
            System.out.println("isPowerOfTwoBitwise(" + testNumbers[i] + "): " + result + " (Expected: " + expected[i] + ")");
            if (result != expected[i]) System.err.println("   Mismatch!");
        }

        System.out.println("\n--- Iterative Method ---");
        for (int i = 0; i < testNumbers.length; i++) {
            boolean result = sol.isPowerOfTwoIterative(testNumbers[i]);
            System.out.println("isPowerOfTwoIterative(" + testNumbers[i] + "): " + result + " (Expected: " + expected[i] + ")");
            if (result != expected[i]) System.err.println("   Mismatch!");
        }
    }
}
