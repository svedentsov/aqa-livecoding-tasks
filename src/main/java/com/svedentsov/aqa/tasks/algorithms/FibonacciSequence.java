package com.svedentsov.aqa.tasks.algorithms;

/**
 * Решение задачи №12: Числа Фибоначчи.
 * Описание: Написать функцию для генерации N-го числа Фибоначчи.
 * (Проверяет: циклы/рекурсия, арифметика)
 * Задание: Напишите метод `long fibonacci(int n)`, который возвращает `n`-ое
 * число Фибоначчи (последовательность начинается с 0, 1, 1, 2...).
 * Реализуйте итеративно. Используйте `long`.
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
     * @throws ArithmeticException      если результат переполняет тип long (для n > 92).
     */
    public long fibonacci(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Индекс Фибоначчи не может быть отрицательным: " + n);
        }
        // Базовые случаи
        if (n == 0) return 0L;
        if (n == 1) return 1L;

        long prevPrev = 0L; // F(i-2), начинается с F(0)
        long prev = 1L;     // F(i-1), начинается с F(1)
        long current;       // F(i)

        // Вычисляем числа от F(2) до F(n)
        for (int i = 2; i <= n; i++) {
            // Проверка на переполнение long ПЕРЕД сложением
            // Если prev > Long.MAX_VALUE - prevPrev, то сложение вызовет переполнение
            if (prev > Long.MAX_VALUE - prevPrev) {
                // Предоставляем информацию о максимально поддерживаемом индексе
                throw new ArithmeticException(String.format(
                        "Результат Фибоначчи для индекса %d переполняет тип long на шаге %d. Максимальный поддерживаемый индекс - 92.", n, i));
            }
            current = prevPrev + prev; // F(i) = F(i-2) + F(i-1)
            prevPrev = prev;          // Сдвигаем: F(i-2) <- F(i-1)
            prev = current;           // Сдвигаем: F(i-1) <- F(i)
        }
        // После цикла в 'prev' будет лежать F(n)
        return prev;
    }
}
