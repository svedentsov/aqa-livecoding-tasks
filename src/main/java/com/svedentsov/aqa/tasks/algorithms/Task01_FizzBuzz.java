package com.svedentsov.aqa.tasks.algorithms;

/**
 * Решение задачи №1: FizzBuzz.
 */
public class Task01_FizzBuzz {

    /**
     * Выводит в консоль последовательность FizzBuzz от 1 до n.
     * Для чисел, кратных 3, выводится "Fizz".
     * Для чисел, кратных 5, выводится "Buzz".
     * Для чисел, кратных и 3, и 5, выводится "FizzBuzz".
     * В остальных случаях выводится само число.
     *
     * @param n Верхняя граница последовательности (включительно). Должно быть >= 1.
     */
    public void fizzBuzz(int n) {
        if (n < 1) {
            System.out.println("N должно быть больше или равно 1");
            return;
        }
        for (int i = 1; i <= n; i++) {
            boolean divBy3 = (i % 3 == 0);
            boolean divBy5 = (i % 5 == 0);

            if (divBy3 && divBy5) {
                System.out.println("FizzBuzz");
            } else if (divBy3) {
                System.out.println("Fizz");
            } else if (divBy5) {
                System.out.println("Buzz");
            } else {
                System.out.println(i);
            }
        }
    }

    /**
     * Точка входа для демонстрации работы метода fizzBuzz.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task01_FizzBuzz sol = new Task01_FizzBuzz();
        System.out.println("--- FizzBuzz(15) ---");
        sol.fizzBuzz(15);
        System.out.println("\n--- FizzBuzz(0) ---");
        sol.fizzBuzz(0);
    }
}
