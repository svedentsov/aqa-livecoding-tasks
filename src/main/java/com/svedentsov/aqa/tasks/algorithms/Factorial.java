package com.svedentsov.aqa.tasks.algorithms;

/**
 * Решение задачи №11: Факториал числа.
 * <p>
 * Описание: Написать функцию для вычисления факториала числа
 * (итеративно или рекурсивно). (Проверяет: циклы/рекурсия, арифметика)
 * <p>
 * Задание: Напишите метод `long factorial(int n)`, который вычисляет факториал
 * неотрицательного целого числа `n`. Реализуйте итеративно.
 * <p>
 * Пример: `factorial(5)` -> `120`, `factorial(0)` -> `1`.
 */
public class Factorial {

    /**
     * Вычисляет факториал неотрицательного целого числа n итеративно.
     * Факториал n (n!) - это произведение всех положительных целых чисел до n включительно.
     * 0! = 1.
     * Использует тип long для результата, чтобы вместить большие значения факториалов
     * (до 20! включительно).
     *
     * @param n Неотрицательное целое число.
     * @return Факториал числа n (n!).
     * @throws IllegalArgumentException если n отрицательное.
     * @throws ArithmeticException      если результат вычисления факториала переполняет тип long.
     */
    public long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers: " + n);
        }
        // Базовые случаи: 0! = 1, 1! = 1
        if (n == 0) {
            return 1L;
        }

        long result = 1L;
        // Цикл от 2 до n
        for (int i = 2; i <= n; i++) {
            // Проверка на возможное переполнение ПЕРЕД умножением
            // Если result > Long.MAX_VALUE / i, то result * i точно вызовет переполнение
            if (result > Long.MAX_VALUE / i) {
                throw new ArithmeticException("Factorial result for " + n + " overflows long type at step " + i);
            }
            result *= i;
        }
        return result;
    }

    /**
     * Точка входа для демонстрации работы метода вычисления факториала.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Factorial sol = new Factorial();

        runFactorialTest(sol, 0);   // 1
        runFactorialTest(sol, 1);   // 1
        runFactorialTest(sol, 2);   // 2
        runFactorialTest(sol, 5);   // 120
        runFactorialTest(sol, 10);  // 3628800
        runFactorialTest(sol, 20);  // 2432902008176640000 (Максимальный для long)
        runFactorialTest(sol, 21);  // Error - overflows long type
        runFactorialTest(sol, -1);  // Error - Factorial is not defined for negative numbers
        runFactorialTest(sol, -5);  // Error - Factorial is not defined for negative numbers
    }

    /**
     * Вспомогательный метод для тестирования факториала.
     *
     * @param sol Экземпляр решателя.
     * @param n   Число для вычисления факториала.
     */
    private static void runFactorialTest(Factorial sol, int n) {
        System.out.print("Factorial(" + n + "): ");
        try {
            System.out.println(sol.factorial(n));
        } catch (IllegalArgumentException | ArithmeticException e) {
            System.out.println("Error - " + e.getMessage());
        }
    }
}
