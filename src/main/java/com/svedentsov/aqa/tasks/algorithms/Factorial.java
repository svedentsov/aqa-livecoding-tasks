package com.svedentsov.aqa.tasks.algorithms;

/**
 * Решение задачи №11: Факториал числа.
 * <p>
 * Описание: Написать функцию для вычисления факториала числа
 * (итеративно или рекурсивно). (Проверяет: циклы/рекурсия, арифметика)
 * <p>
 * Задание: Напишите метод `long factorial(int n)`, который вычисляет факториал
 * неотрицательного целого числа `n`. Реализуйте итеративно.
 * <p>
 * Пример: `factorial(5)` -> `120`, `factorial(0)` -> `1`.
 */
public class Factorial {

    /**
     * Вычисляет факториал неотрицательного целого числа n итеративно.
     * Факториал n (n!) - это произведение всех положительных целых чисел до n включительно.
     * 0! = 1.
     * Использует тип long для результата, чтобы вместить большие значения факториалов
     * (до 20! включительно).
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
        // Базовые случаи: 0! = 1, 1! = 1, но цикл ниже это обработает, если n=1, поэтому отдельная проверка не нужна.
        if (n == 0) {
            return 1L;
        }

        long result = 1L;
        // Цикл от 2 до n
        for (int i = 2; i <= n; i++) {
            // Проверка на возможное переполнение ПЕРЕД умножением
            // Если result > Long.MAX_VALUE / i, то result * i точно вызовет переполнение
            if (result > Long.MAX_VALUE / i) {
                // Создаем сообщение об ошибке, включающее n и шаг i, на котором произошло переполнение
                String errorMsg = String.format("Factorial result for %d overflows long type at step %d", n, i);
                throw new ArithmeticException(errorMsg);
            }
            result *= i;
        }
        return result;
    }
}
