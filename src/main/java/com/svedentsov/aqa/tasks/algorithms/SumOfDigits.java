package com.svedentsov.aqa.tasks.algorithms;

/**
 * Решение задачи №29: Сумма цифр числа.
 * <p>
 * Описание: Написать функцию для вычисления суммы цифр целого числа.
 * (Проверяет: циклы, арифметика, остаток от деления)
 * <p>
 * Задание: Напишите метод `int sumDigits(int number)`, который вычисляет и
 * возвращает сумму цифр целого числа `number`. (Для отрицательных чисел сумма
 * цифр равна сумме цифр абсолютного значения).
 * <p>
 * Пример: `sumDigits(123)` -> `6`, `sumDigits(49)` -> `13`, `sumDigits(5)` -> `5`,
 * `sumDigits(0)` -> `0`, `sumDigits(-123)` -> `6`.
 */
public class SumOfDigits {

    /**
     * Вычисляет сумму цифр заданного целого числа.
     * Работает корректно как для положительных, так и для отрицательных чисел
     * (сумма цифр отрицательного числа равна сумме цифр его абсолютного значения).
     * Использует цикл и операции остатка от деления (%) и целочисленного деления (/).
     * <p>
     * Алгоритм:
     * 1. Взять абсолютное значение числа.
     * 2. Инициализировать сумму нулем.
     * 3. Пока число больше 0:
     * a. Добавить последнюю цифру (`число % 10`) к сумме.
     * b. Убрать последнюю цифру из числа (`число / 10`).
     * 4. Вернуть вычисленную сумму.
     *
     * @param number Целое число для вычисления суммы цифр.
     * @return Сумма цифр числа. Для 0 возвращает 0.
     */
    public int sumDigits(int number) {
        // Работаем с абсолютным значением, чтобы корректно обработать отрицательные числа
        long absNumber = Math.abs((long) number); // Используем long на случай number = Integer.MIN_VALUE
        int sum = 0;

        // Если исходное число 0, цикл не начнется, вернется sum = 0.
        while (absNumber > 0) {
            // Получаем последнюю цифру (0-9) и добавляем к сумме
            sum += (int) (absNumber % 10); // Остаток от деления на 10
            // Убираем последнюю цифру
            absNumber /= 10; // Целочисленное деление на 10
        }

        return sum;
    }

    /**
     * Точка входа для демонстрации работы метода вычисления суммы цифр.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        SumOfDigits sol = new SumOfDigits();
        int[] testNumbers = {123, 49, 5, 0, -123, 999, 100, 8, -5, Integer.MAX_VALUE, Integer.MIN_VALUE};

        System.out.println("--- Calculating Sum of Digits ---");
        for (int num : testNumbers) {
            runSumDigitsTest(sol, num);
        }
    }

    /**
     * Вспомогательный метод для тестирования sumDigits.
     *
     * @param sol    Экземпляр решателя.
     * @param number Число для теста.
     */
    private static void runSumDigitsTest(SumOfDigits sol, int number) {
        System.out.print("Sum of digits(" + number + "): ");
        try {
            int sum = sol.sumDigits(number);
            System.out.println(sum);
            // Можно добавить проверку ожидаемого значения, если заранее их рассчитать
            // int expected = calculateExpectedSum(number); // Например
            // if (sum != expected) System.err.println("   Mismatch! Expected: " + expected);
        } catch (Exception e) {
            System.out.println("Error - " + e.getMessage());
        }
    }
}
