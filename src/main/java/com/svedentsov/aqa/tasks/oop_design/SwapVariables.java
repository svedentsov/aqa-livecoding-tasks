package com.svedentsov.aqa.tasks.oop_design;

/**
 * Решение задачи №14: Поменять местами две переменные без использования третьей.
 * <p>
 * Описание: Написать код для обмена значениями двух числовых переменных.
 * (Проверяет: арифметика/битовые операции)
 * <p>
 * Задание: Дан код: `int a = 5; int b = 10;`. Напишите код, который меняет
 * значения `a` и `b` местами, не используя дополнительную переменную.
 * <p>
 * Пример: После выполнения кода `a` должно стать `10`, а `b` должно стать `5`.
 */
public class SwapVariables {

    /**
     * Меняет местами значения двух целочисленных переменных с использованием
     * арифметических операций.
     * Внимание: Может вызвать переполнение для граничных значений int.
     *
     * @param a Первое число (передается по значению, поэтому для демонстрации
     *          нужно использовать массив или объект-обертку, либо поля класса).
     *          Здесь для простоты демонстрации просто выводим шаги.
     * @param b Второе число.
     */
    public void swapUsingArithmetic(int a, int b) {
        System.out.println("--- Using Arithmetic ---");
        System.out.println("Initial       : a = " + a + ", b = " + b);
        try {
            // Эта последовательность (+, -, -) чуть безопаснее чем (+, -, +)
            // но все равно уязвима для переполнения на первом шаге.
            a = a + b; // Шаг 1: a = a + b (Риск переполнения)
            b = a - b; // Шаг 2: b = (a_old + b_old) - b_old = a_old
            a = a - b; // Шаг 3: a = (a_old + b_old) - a_old = b_old
            System.out.println("Step 1 (a+b)  : a = " + a + ", b = " + b); // b еще старое
            System.out.println("Step 2 (a-b)  : a = " + a + ", b = " + b); // b уже новое
            System.out.println("Final         : a = " + a + ", b = " + b); // a тоже новое
        } catch (ArithmeticException e) {
            System.out.println("Error: Arithmetic overflow occurred during swap.");
        }
    }

    /**
     * Меняет местами значения двух целочисленных переменных с использованием
     * побитовой операции XOR (^).
     * Этот метод безопасен от переполнения.
     *
     * @param a Первое число.
     * @param b Второе число.
     */
    public void swapUsingXor(int a, int b) {
        System.out.println("--- Using XOR ---");
        System.out.println("Initial       : a = " + a + ", b = " + b);
        a = a ^ b; // Шаг 1: a = a XOR b
        b = a ^ b; // Шаг 2: b = (a XOR b) XOR b = a XOR (b XOR b) = a XOR 0 = a (старое a)
        a = a ^ b; // Шаг 3: a = (a XOR b) XOR a_old = (a_old XOR b_old) XOR a_old = b_old XOR (a_old XOR a_old) = b_old XOR 0 = b_old
        System.out.println("Step 1 (a^b)  : a = " + a + ", b = " + b); // b еще старое
        System.out.println("Step 2 (a^b)  : a = " + a + ", b = " + b); // b уже новое
        System.out.println("Final         : a = " + a + ", b = " + b); // a тоже новое
    }

    /**
     * Точка входа для демонстрации обмена переменных.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        SwapVariables sol = new SwapVariables();

        System.out.println("Test Case 1: Positive Numbers");
        sol.swapUsingArithmetic(5, 10);
        sol.swapUsingXor(5, 10);

        System.out.println("\nTest Case 2: One Negative, One Positive");
        sol.swapUsingArithmetic(-7, 15);
        sol.swapUsingXor(-7, 15);

        System.out.println("\nTest Case 3: Both Negative");
        sol.swapUsingArithmetic(-3, -9);
        sol.swapUsingXor(-3, -9);

        System.out.println("\nTest Case 4: Zero Involved");
        sol.swapUsingArithmetic(0, 100);
        sol.swapUsingXor(0, 100);

        System.out.println("\nTest Case 5: Same Numbers");
        sol.swapUsingArithmetic(42, 42);
        sol.swapUsingXor(42, 42);

        System.out.println("\nTest Case 6: Boundary Values (Potential Arithmetic Overflow)");
        // Арифметика скорее всего переполнится
        sol.swapUsingArithmetic(Integer.MAX_VALUE, 1);
        // XOR должен работать корректно
        sol.swapUsingXor(Integer.MAX_VALUE, 1);

        System.out.println("\nTest Case 7: Opposite Boundary Values");
        // Арифметика может переполниться или дать неожиданный результат
        sol.swapUsingArithmetic(Integer.MAX_VALUE, Integer.MIN_VALUE);
        // XOR должен работать корректно
        sol.swapUsingXor(Integer.MAX_VALUE, Integer.MIN_VALUE);
    }
}
