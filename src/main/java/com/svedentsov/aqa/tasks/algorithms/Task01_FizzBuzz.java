package com.svedentsov.aqa.tasks.algorithms;

/**
 * Решение задачи №1: FizzBuzz.
 * <p>
 * Описание: Написать программу, которая выводит числа от 1 до N.
 * Но для чисел, кратных 3, вывести "Fizz", для чисел, кратных 5 - "Buzz",
 * а для чисел, кратных и 3, и 5 - "FizzBuzz". (Проверяет: циклы, условия)
 * <p>
 * Задание: Напишите метод `fizzBuzz(int n)`, который выводит в консоль числа от 1 до `n`.
 * Для чисел, кратных 3, выведите "Fizz" вместо числа.
 * Для чисел, кратных 5, выведите "Buzz".
 * Для чисел, кратных и 3, и 5, выведите "FizzBuzz".
 * <p>
 * Пример: `fizzBuzz(15)` должно вывести: 1, 2, Fizz, 4, Buzz, Fizz, 7, 8, Fizz, Buzz, 11, Fizz, 13, 14, FizzBuzz
 */
public class Task01_FizzBuzz {

    /**
     * Выводит в консоль последовательность FizzBuzz от 1 до n.
     * Для чисел, кратных 3, выводится "Fizz".
     * Для чисел, кратных 5, выводится "Buzz".
     * Для чисел, кратных и 3, и 5 (т.е. кратных 15), выводится "FizzBuzz".
     * В остальных случаях выводится само число.
     * Каждое значение выводится на новой строке.
     *
     * @param n Верхняя граница последовательности (включительно). Должно быть >= 1.
     *          Если n < 1, выводится сообщение об ошибке.
     */
    public void fizzBuzz(int n) {
        if (n < 1) {
            System.out.println("N должно быть больше или равно 1");
            return;
        }
        // Использование StringBuilder для более эффективного построения строки (хотя для вывода в консоль это менее критично)
        // StringBuilder output = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            boolean divBy3 = (i % 3 == 0);
            boolean divBy5 = (i % 5 == 0);

            if (divBy3 && divBy5) {
                System.out.println("FizzBuzz");
                // output.append("FizzBuzz");
            } else if (divBy3) {
                System.out.println("Fizz");
                // output.append("Fizz");
            } else if (divBy5) {
                System.out.println("Buzz");
                // output.append("Buzz");
            } else {
                System.out.println(i);
                // output.append(i);
            }
            // Если нужно выводить через запятую:
            // if (i < n) {
            //     output.append(", ");
            // }
        }
        // System.out.println(output.toString()); // Для вывода одной строкой
    }

    /**
     * Точка входа для демонстрации работы метода fizzBuzz с различными примерами.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task01_FizzBuzz sol = new Task01_FizzBuzz();

        System.out.println("--- FizzBuzz(15) ---");
        sol.fizzBuzz(15); // Стандартный пример

        System.out.println("\n--- FizzBuzz(0) ---");
        sol.fizzBuzz(0); // Пример с некорректным вводом (n < 1)

        System.out.println("\n--- FizzBuzz(1) ---");
        sol.fizzBuzz(1); // Пример с минимальным корректным вводом

        System.out.println("\n--- FizzBuzz(5) ---");
        sol.fizzBuzz(5); // Пример, включающий Fizz и Buzz

        System.out.println("\n--- FizzBuzz(30) ---");
        sol.fizzBuzz(30); // Пример с большим количеством Fizz, Buzz и FizzBuzz
    }
}
