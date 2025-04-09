package com.svedentsov.aqa.tasks.algorithms;

/**
 * Решение задачи №12: Числа Фибоначчи.
 */
public class Task12_FibonacciSequence {

    /**
     * Вычисляет n-ое число Фибоначчи итеративно.
     * Последовательность Фибоначчи: 0, 1, 1, 2, 3, 5, 8, ...
     * F(0) = 0, F(1) = 1, F(n) = F(n-1) + F(n-2) для n > 1.
     * Итеративный подход более эффективен по памяти, чем наивная рекурсия.
     * Используется тип long для возможности вычисления больших чисел Фибоначчи.
     *
     * @param n Индекс числа Фибоначчи (начиная с 0). Должен быть неотрицательным.
     * @return n-ое число Фибоначчи в виде long.
     * @throws IllegalArgumentException если n отрицательное.
     * @throws ArithmeticException      если результат переполняет тип long.
     */
    public long fibonacci(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Fibonacci index cannot be negative: " + n);
        }
        // Первые два числа последовательности
        if (n == 0) return 0L; // Возвращаем long
        if (n == 1) return 1L;

        long a = 0L; // F(n-2)
        long b = 1L; // F(n-1)
        long currentFib = 0L; // F(n)

        // Вычисляем числа от F(2) до F(n)
        for (int i = 2; i <= n; i++) {
            // Проверка на переполнение long перед сложением
            if (Long.MAX_VALUE - a < b) {
                throw new ArithmeticException("Fibonacci result for " + n + " overflows long type at step " + i);
            }
            currentFib = a + b; // F(i) = F(i-2) + F(i-1)
            a = b;              // Сдвигаем: F(i-2) становится F(i-1)
            b = currentFib;     // Сдвигаем: F(i-1) становится F(i)
        }
        return currentFib; // Возвращаем F(n)
    }

    /**
     * Точка входа для демонстрации работы метода вычисления чисел Фибоначчи.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task12_FibonacciSequence sol = new Task12_FibonacciSequence();
        for (int i = 0; i <= 92; i++) { // F(93) уже переполняет long
            try {
                System.out.println("Fibonacci(" + i + "): " + sol.fibonacci(i));
            } catch (ArithmeticException e) {
                System.out.println("Fibonacci(" + i + "): " + e.getMessage());
                break; // Прерываем цикл при переполнении
            }
        }

        // Пример вызова для 93 (переполнение)
        try {
            System.out.println("Fibonacci(93): " + sol.fibonacci(93));
        } catch (ArithmeticException e) {
            System.out.println("Fibonacci(93): " + e.getMessage());
        }

        // Пример вызова для отрицательного числа
        try {
            sol.fibonacci(-1);
        } catch (IllegalArgumentException e) {
            System.out.println("Error calling fibonacci(-1): " + e.getMessage());
        }
    }
}
