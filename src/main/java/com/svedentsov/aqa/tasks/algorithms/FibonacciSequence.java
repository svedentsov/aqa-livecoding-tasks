package com.svedentsov.aqa.tasks.algorithms;

/**
 * Решение задачи №12: Числа Фибоначчи.
 * <p>
 * Описание: Написать функцию для генерации N-го числа Фибоначчи или
 * последовательности до N-го числа (итеративно или рекурсивно).
 * (Проверяет: циклы/рекурсия, арифметика)
 * <p>
 * Задание: Напишите метод `long fibonacci(int n)`, который возвращает `n`-ое
 * число Фибоначчи (последовательность начинается с 0, 1, 1, 2...).
 * Реализуйте итеративно. Используйте `long`.
 * <p>
 * Пример: `fibonacci(0)` -> `0`, `fibonacci(1)` -> `1`, `fibonacci(6)` -> `8`.
 */
public class FibonacciSequence {

    /**
     * Вычисляет n-ое число Фибоначчи итеративно.
     * Последовательность Фибоначчи: 0, 1, 1, 2, 3, 5, 8, ...
     * F(0) = 0, F(1) = 1, F(n) = F(n-1) + F(n-2) для n > 1.
     * Итеративный подход эффективен по времени (O(n)) и памяти (O(1)).
     * Используется тип long для возможности вычисления больших чисел Фибоначчи (до F(92)).
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
        // Базовые случаи
        if (n == 0) return 0L;
        if (n == 1) return 1L;

        long prevPrev = 0L; // F(i-2), начинается с F(0)
        long prev = 1L;     // F(i-1), начинается с F(1)
        long current = 0L;  // F(i)

        // Вычисляем числа от F(2) до F(n)
        for (int i = 2; i <= n; i++) {
            // Проверка на переполнение long ПЕРЕД сложением
            // Если prev > Long.MAX_VALUE - prevPrev, то сложение вызовет переполнение
            if (prev > Long.MAX_VALUE - prevPrev) {
                throw new ArithmeticException("Fibonacci result for index " + n + " overflows long type at step " + i);
            }
            current = prevPrev + prev; // F(i) = F(i-2) + F(i-1)
            prevPrev = prev;          // Сдвигаем: F(i-2) <- F(i-1)
            prev = current;           // Сдвигаем: F(i-1) <- F(i)
        }
        // После цикла в 'prev' будет лежать F(n)
        // (Потому что current вычисляется на последней итерации i=n, и присваивается в prev)
        // Если n=0 или n=1, цикл не выполняется, возвращаются базовые случаи.
        // Если n > 1, цикл выполняется, и в конце prev содержит F(n).
        return prev; // Возвращаем F(n)
    }

    /**
     * Точка входа для демонстрации работы метода вычисления чисел Фибоначчи.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        FibonacciSequence sol = new FibonacciSequence();

        runFibonacciTest(sol, 0);   // 0
        runFibonacciTest(sol, 1);   // 1
        runFibonacciTest(sol, 2);   // 1
        runFibonacciTest(sol, 6);   // 8
        runFibonacciTest(sol, 10);  // 55
        runFibonacciTest(sol, 20);  // 6765
        runFibonacciTest(sol, 90);  // 2880067194370816120
        runFibonacciTest(sol, 92);  // 7540113804746346429 (Максимальный для long)
        runFibonacciTest(sol, 93);  // Error - overflows long type
        runFibonacciTest(sol, -1);  // Error - Fibonacci index cannot be negative
        runFibonacciTest(sol, -10); // Error - Fibonacci index cannot be negative
    }

    /**
     * Вспомогательный метод для тестирования чисел Фибоначчи.
     *
     * @param sol Экземпляр решателя.
     * @param n   Индекс числа Фибоначчи.
     */
    private static void runFibonacciTest(FibonacciSequence sol, int n) {
        System.out.print("Fibonacci(" + n + "): ");
        try {
            System.out.println(sol.fibonacci(n));
        } catch (IllegalArgumentException | ArithmeticException e) {
            System.out.println("Error - " + e.getMessage());
        }
    }
}
