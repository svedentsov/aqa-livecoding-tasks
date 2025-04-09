package com.svedentsov.aqa.tasks.algorithms;

/**
 * Решение задачи №13: Проверка на простое число.
 */
public class Task13_PrimeNumberCheck {

    /**
     * Проверяет, является ли заданное целое число простым.
     * Простое число - это натуральное число больше 1, которое не имеет других
     * делителей, кроме 1 и самого себя.
     * Используется оптимизированный метод проверки делителей до квадратного корня из числа.
     *
     * @param number Целое число для проверки.
     * @return {@code true}, если число простое, {@code false} в противном случае.
     */
    public boolean isPrime(int number) {
        // 1. Числа <= 1 не являются простыми
        if (number <= 1) {
            return false;
        }
        // 2. Числа 2 и 3 - простые
        if (number <= 3) {
            return true;
        }
        // 3. Оптимизация: четные числа > 2 и числа, кратные 3 (кроме 3), не являются простыми
        if (number % 2 == 0 || number % 3 == 0) {
            return false;
        }

        // 4. Проверяем делители вида 6k ± 1 до sqrt(number)
        // Используем i * i <= number вместо i <= Math.sqrt(number) для избежания
        // вычисления корня и работы с double, а также для предотвращения
        // возможного переполнения i * i для больших number (используем long для i*i).
        for (long i = 5; i * i <= number; i = i + 6) {
            // Проверяем делимость на i (6k - 1) и i + 2 (6k + 1)
            if (number % i == 0 || number % (i + 2) == 0) {
                return false; // Найден делитель - число не простое
            }
        }

        // Если не нашли делителей, число простое
        return true;
    }

    /**
     * Точка входа для демонстрации работы метода проверки на простоту.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task13_PrimeNumberCheck sol = new Task13_PrimeNumberCheck();
        int[] testNumbers = {7, 10, 2, 3, 1, 0, -5, 17, 15, 97, 91, 2147483647 /* Max Int, является простым */};
        boolean[] expected = {true, false, true, true, false, false, false, true, false, true, false, true};

        for (int i = 0; i < testNumbers.length; i++) {
            boolean result = sol.isPrime(testNumbers[i]);
            System.out.println("isPrime(" + testNumbers[i] + "): " + result + " (Expected: " + expected[i] + ")");
            if (result != expected[i]) {
                System.err.println("   Mismatch!");
            }
        }
    }
}
