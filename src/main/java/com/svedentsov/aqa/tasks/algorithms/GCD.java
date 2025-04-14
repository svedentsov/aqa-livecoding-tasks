package com.svedentsov.aqa.tasks.algorithms;

/**
 * Решение задачи №36: Найти наибольший общий делитель (НОД / GCD).
 * <p>
 * Описание: Написать функцию для нахождения НОД двух чисел.
 * (Проверяет: алгоритм Евклида, циклы/рекурсия)
 * <p>
 * Задание: Напишите метод `int gcd(int a, int b)`, который вычисляет
 * наибольший общий делитель двух целых чисел `a` и `b`.
 * (Обычно предполагается неотрицательность, но алгоритм работает и для отрицательных).
 * <p>
 * Пример: `gcd(48, 18)` -> `6`. `gcd(10, 5)` -> `5`. `gcd(7, 13)` -> `1`.
 * `gcd(0, 5)` -> `5`. `gcd(0, 0)` -> `0`.
 */
public class GCD {

    /**
     * Вычисляет наибольший общий делитель (НОД) двух целых чисел
     * с использованием рекурсивной версии алгоритма Евклида.
     * Алгоритм основан на принципе: НОД(a, b) = НОД(b, a % b), при этом НОД(a, 0) = |a|.
     * Работает корректно для положительных, отрицательных чисел и нуля.
     * НОД(0, 0) обрабатывается как 0.
     *
     * @param a Первое целое число.
     * @param b Второе целое число.
     * @return Наибольший общий делитель чисел |a| и |b|.
     */
    public int gcdRecursive(int a, int b) {
        // Используем абсолютные значения для корректной работы алгоритма %
        a = Math.abs(a);
        b = Math.abs(b);

        // Базовый случай рекурсии: НОД(a, 0) = a
        if (b == 0) {
            return a; // Если a тоже 0, вернет 0, что принято для НОД(0,0)
        }
        // Рекурсивный вызов с b и остатком от деления a на b
        return gcdRecursive(b, a % b);
    }

    /**
     * Вычисляет наибольший общий делитель (НОД) двух целых чисел
     * с использованием итеративной версии алгоритма Евклида.
     * Этот метод обычно предпочтительнее рекурсивного из-за отсутствия
     * накладных расходов на вызовы функций и риска переполнения стека
     * для очень больших (хотя и маловероятных для int) входных данных.
     *
     * @param a Первое целое число.
     * @param b Второе целое число.
     * @return Наибольший общий делитель чисел |a| и |b|.
     */
    public int gcdIterative(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);

        // Итеративный алгоритм Евклида
        while (b != 0) {
            int temp = b; // Сохраняем b, т.к. оно понадобится как новое 'a'
            b = a % b;    // Новое 'b' - это остаток
            a = temp;     // Новое 'a' - это старое 'b'
        }
        // Когда b становится 0, значение 'a' содержит НОД
        return a;
    }

    /**
     * Точка входа для демонстрации работы методов вычисления НОД.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        GCD sol = new GCD();
        int[][] pairs = {
                {48, 18}, {18, 48}, {10, 5}, {5, 10}, {7, 13}, {13, 7},
                {0, 5}, {5, 0}, {-48, 18}, {-48, -18}, {0, 0}, {21, 49},
                {1, 1}, {1, 5}, {100, 75}, {Integer.MAX_VALUE, 1} // gcd(large, 1) = 1
        };

        System.out.println("--- Calculating GCD (Greatest Common Divisor) ---");
        for (int[] pair : pairs) {
            runGcdTest(sol, pair[0], pair[1]);
        }
    }

    /**
     * Вспомогательный метод для тестирования методов GCD.
     *
     * @param sol Экземпляр решателя.
     * @param a   Первое число.
     * @param b   Второе число.
     */
    private static void runGcdTest(GCD sol, int a, int b) {
        System.out.println("\nInput: a=" + a + ", b=" + b);
        try {
            int resultRecursive = sol.gcdRecursive(a, b);
            System.out.println("  GCD (Recursive): " + resultRecursive);
            // Проверка: gcdIterative должен дать тот же результат
            int resultIterative = sol.gcdIterative(a, b);
            if (resultIterative != resultRecursive) {
                System.err.println("  MISMATCH! Iterative result: " + resultIterative);
            } else {
                System.out.println("  GCD (Iterative): " + resultIterative);
            }
        } catch (Exception e) {
            System.err.println("  Error calculating GCD: " + e.getMessage());
        }
    }
}
