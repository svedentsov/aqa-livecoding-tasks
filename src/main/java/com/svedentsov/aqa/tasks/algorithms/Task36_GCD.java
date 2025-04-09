package com.svedentsov.aqa.tasks.algorithms;

/**
 * Решение задачи №36: Найти наибольший общий делитель (НОД / GCD).
 */
public class Task36_GCD {

    /**
     * Вычисляет наибольший общий делитель (НОД) двух целых чисел
     * с использованием рекурсивной версии алгоритма Евклида.
     * Алгоритм основан на принципе: НОД(a, b) = НОД(b, a % b), при этом НОД(a, 0) = |a|.
     * Работает корректно для положительных, отрицательных чисел и нуля.
     * НОД(0, 0) возвращает 0.
     *
     * @param a Первое целое число.
     * @param b Второе целое число.
     * @return Наибольший общий делитель чисел |a| и |b|.
     */
    public int gcdRecursive(int a, int b) {
        // Берем абсолютные значения, так как НОД определяется для неотрицательных чисел
        a = Math.abs(a);
        b = Math.abs(b);

        // Базовый случай рекурсии: НОД(a, 0) = a
        if (b == 0) {
            return a;
        }
        // Рекурсивный вызов с b и остатком от деления a на b
        return gcdRecursive(b, a % b);
    }

    /**
     * Вычисляет наибольший общий делитель (НОД) двух целых чисел
     * с использованием итеративной версии алгоритма Евклида.
     * Этот метод обычно предпочтительнее рекурсивного из-за отсутствия
     * накладных расходов на вызовы функций и риска переполнения стека.
     *
     * @param a Первое целое число.
     * @param b Второе целое число.
     * @return Наибольший общий делитель чисел |a| и |b|.
     */
    public int gcdIterative(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        // Цикл продолжается, пока b не станет 0
        while (b != 0) {
            int temp = b; // Сохраняем текущее значение b
            b = a % b;    // Новое b - это остаток от деления a на старое b
            a = temp;     // Новое a - это старое значение b
        }
        // Когда b становится 0, значение a является НОД
        return a;
    }

    /**
     * Точка входа для демонстрации работы методов вычисления НОД.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task36_GCD sol = new Task36_GCD();
        int[][] pairs = {
                {48, 18}, {18, 48}, {10, 5}, {7, 13}, {0, 5},
                {5, 0}, {-48, 18}, {-48, -18}, {0, 0}, {21, 49}
        };
        int[] expected = {6, 6, 5, 1, 5, 5, 6, 6, 0, 7};

        System.out.println("--- Recursive GCD ---");
        for (int i = 0; i < pairs.length; i++) {
            int result = sol.gcdRecursive(pairs[i][0], pairs[i][1]);
            System.out.println("gcd(" + pairs[i][0] + ", " + pairs[i][1] + ") = " + result + " (Expected: " + expected[i] + ")");
            if (result != expected[i]) System.err.println("   Mismatch!");
        }

        System.out.println("\n--- Iterative GCD ---");
        for (int i = 0; i < pairs.length; i++) {
            int result = sol.gcdIterative(pairs[i][0], pairs[i][1]);
            System.out.println("gcd(" + pairs[i][0] + ", " + pairs[i][1] + ") = " + result + " (Expected: " + expected[i] + ")");
            if (result != expected[i]) System.err.println("   Mismatch!");
        }
    }
}
