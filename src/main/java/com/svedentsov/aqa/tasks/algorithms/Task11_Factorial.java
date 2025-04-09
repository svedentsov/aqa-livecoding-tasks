package com.svedentsov.aqa.tasks.algorithms;

/**
 * Решение задачи №11: Факториал числа.
 */
public class Task11_Factorial {

    /**
     * Вычисляет факториал неотрицательного целого числа n итеративно.
     * Факториал n (n!) - это произведение всех положительных целых чисел до n включительно.
     * 0! = 1.
     * Использует тип long для результата, чтобы вместить большие значения факториалов.
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
        // 0! = 1, 1! = 1
        if (n <= 1) {
            return 1;
        }
        long result = 1;
        // Цикл от 2 до n
        for (int i = 2; i <= n; i++) {
            // Проверка на возможное переполнение перед умножением
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
        Task11_Factorial sol = new Task11_Factorial();
        for (int i = 0; i <= 20; i++) { // Факториал 21 уже не помещается в long
            try {
                System.out.println("Factorial(" + i + "): " + sol.factorial(i));
            } catch (ArithmeticException e) {
                System.out.println("Factorial(" + i + "): " + e.getMessage());
            }
        }

        // Пример вызова для 21 (переполнение)
        try {
            System.out.println("Factorial(21): " + sol.factorial(21));
        } catch (ArithmeticException e) {
            System.out.println("Factorial(21): " + e.getMessage());
        }

        // Пример вызова для отрицательного числа
        try {
            sol.factorial(-1);
        } catch (IllegalArgumentException e) {
            System.out.println("Error calling factorial(-1): " + e.getMessage());
        }
    }
}
