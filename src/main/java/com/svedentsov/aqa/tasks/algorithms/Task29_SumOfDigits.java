package com.svedentsov.aqa.tasks.algorithms;

/**
 * Решение задачи №29: Сумма цифр числа.
 */
public class Task29_SumOfDigits {

    /**
     * Вычисляет сумму цифр заданного целого числа.
     * Работает корректно как для положительных, так и для отрицательных чисел
     * (сумма цифр отрицательного числа равна сумме цифр его абсолютного значения).
     * Использует цикл и операции остатка от деления (%) и целочисленного деления (/).
     *
     * @param number Целое число для вычисления суммы цифр.
     * @return Сумма цифр числа. Для 0 возвращает 0.
     */
    public int sumDigits(int number) {
        // Работаем с абсолютным значением числа
        int absNumber = Math.abs(number);
        int sum = 0;

        // Пока в числе есть цифры (число > 0)
        while (absNumber > 0) {
            sum += absNumber % 10; // Добавляем последнюю цифру к сумме
            absNumber /= 10;      // Убираем последнюю цифру из числа
        }

        // Если исходное число было 0, цикл не выполнится, sum останется 0.
        return sum;
    }

    /**
     * Точка входа для демонстрации работы метода вычисления суммы цифр.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task29_SumOfDigits sol = new Task29_SumOfDigits();
        int[] testNumbers = {123, 49, 5, 0, -123, 999, 100, 8};
        int[] expectedSums = {6, 13, 5, 0, 6, 27, 1, 8};

        for (int i = 0; i < testNumbers.length; i++) {
            int result = sol.sumDigits(testNumbers[i]);
            System.out.println("Sum of digits(" + testNumbers[i] + "): " + result + " (Expected: " + expectedSums[i] + ")");
            if (result != expectedSums[i]) System.err.println("   Mismatch!");
        }
    }
}
