package com.svedentsov.aqa.tasks.algorithms;

/**
 * Решение задачи №28: Проверить, является ли число степенью двойки.
 * <p>
 * Описание: Написать функцию для такой проверки.
 * (Проверяет: битовые операции/циклы)
 * <p>
 * Задание: Напишите метод `boolean isPowerOfTwo(int n)`, который возвращает `true`,
 * если положительное целое число `n` является степенью двойки (1, 2, 4, 8, 16...),
 * и `false` иначе.
 * <p>
 * Пример: `isPowerOfTwo(1)` -> `true`, `isPowerOfTwo(16)` -> `true`,
 * `isPowerOfTwo(10)` -> `false`, `isPowerOfTwo(0)` -> `false`.
 */
public class CheckPowerOfTwo {

    /**
     * Проверяет, является ли положительное целое число n степенью двойки (1, 2, 4, 8...).
     * Использует эффективный битовый трюк: `(n & (n - 1)) == 0`.
     * Числа, являющиеся степенью двойки, в двоичном представлении имеют ровно один установленный бит (например, 8 это 1000).
     * Вычитание единицы из такого числа инвертирует все биты справа от установленного бита (например, 8-1=7 это 0111).
     * Побитовое И (&) между числом и числом минус один даст 0 только для степеней двойки.
     *
     * @param n Целое число для проверки.
     * @return {@code true}, если n является положительной степенью двойки, {@code false} в противном случае.
     */
    public boolean isPowerOfTwoBitwise(int n) {
        // 1. Степени двойки должны быть положительными (n > 0).
        // 2. Применяем битовый трюк.
        return (n > 0) && ((n & (n - 1)) == 0);
    }

    /**
     * Проверяет, является ли положительное целое число n степенью двойки.
     * Использует итеративное деление на 2.
     * <p>
     * Алгоритм:
     * 1. Исключить неположительные числа (<= 0).
     * 2. Пока число `n` делится на 2 без остатка, делить его на 2.
     * 3. Если после всех делений осталось число 1, то исходное число было степенью двойки.
     *
     * @param n Целое число для проверки.
     * @return {@code true}, если n является положительной степенью двойки, {@code false} в противном случае.
     */
    public boolean isPowerOfTwoIterative(int n) {
        // Шаг 1: Исключаем 0 и отрицательные числа
        if (n <= 0) {
            return false;
        }
        // Шаг 2: Итеративное деление
        while (n % 2 == 0) {
            n /= 2;
            // Оптимизация: если n стало 0 в процессе, это не степень двойки
            // (но это невозможно при n > 0)
        }
        // Шаг 3: Проверка результата
        // Если исходное n было степенью двойки, в конце останется 1.
        return n == 1;
    }

    /**
     * Точка входа для демонстрации работы методов проверки на степень двойки.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        CheckPowerOfTwo sol = new CheckPowerOfTwo();
        int[] testNumbers = {1, 2, 3, 4, 8, 10, 16, 31, 32, 0, -1, -4, 1024, 1023, Integer.MAX_VALUE, Integer.MIN_VALUE};

        System.out.println("--- Checking for Power of Two ---");
        for (int num : testNumbers) {
            runPowerOfTwoTest(sol, num);
        }
    }

    /**
     * Вспомогательный метод для тестирования методов isPowerOfTwo.
     *
     * @param sol    Экземпляр решателя.
     * @param number Число для проверки.
     */
    private static void runPowerOfTwoTest(CheckPowerOfTwo sol, int number) {
        boolean expected = (number > 0) && ((number & (number - 1)) == 0); // Ожидаемый результат на основе битового метода
        System.out.println("\nInput: " + number);
        try {
            boolean resultBitwise = sol.isPowerOfTwoBitwise(number);
            System.out.printf("  Bitwise Check  : %-5b (Expected: %-5b) %s%n",
                    resultBitwise, expected, (resultBitwise == expected ? "" : "<- MISMATCH!"));
        } catch (Exception e) {
            System.out.println("  Bitwise Check  : Error - " + e.getMessage());
        }
        try {
            boolean resultIterative = sol.isPowerOfTwoIterative(number);
            System.out.printf("  Iterative Check: %-5b (Expected: %-5b) %s%n",
                    resultIterative, expected, (resultIterative == expected ? "" : "<- MISMATCH!"));
        } catch (Exception e) {
            System.out.println("  Iterative Check: Error - " + e.getMessage());
        }
    }
}
