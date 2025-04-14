package com.svedentsov.aqa.tasks.algorithms;

/**
 * Решение задачи №13: Проверка на простое число.
 * <p>
 * Описание: Написать функцию, которая определяет, является ли данное число простым.
 * (Проверяет: циклы, условия, оптимизация)
 * <p>
 * Задание: Напишите метод `boolean isPrime(int number)`, который возвращает `true`,
 * если `number` является простым, и `false` иначе. Простое число - натуральное > 1,
 * делящееся только на 1 и на себя.
 * <p>
 * Пример: `isPrime(7)` -> `true`, `isPrime(10)` -> `false`, `isPrime(2)` -> `true`.
 */
public class PrimeNumberCheck {

    /**
     * Проверяет, является ли заданное целое число простым.
     * Простое число - это натуральное число больше 1, которое не имеет других
     * делителей, кроме 1 и самого себя.
     * Используется оптимизированный метод проверки делителей:
     * 1. Отбрасываются числа <= 1.
     * 2. Отбрасываются четные числа > 2.
     * 3. Отбрасываются числа > 3, кратные 3.
     * 4. Проверяются делители вида 6k ± 1 до квадратного корня из числа.
     *
     * @param number Целое число для проверки.
     * @return {@code true}, если число простое, {@code false} в противном случае.
     */
    public boolean isPrime(int number) {
        // Шаг 1: Числа <= 1 не являются простыми по определению
        if (number <= 1) {
            return false;
        }
        // Шаг 2: Числа 2 и 3 - базовые простые случаи
        if (number == 2 || number == 3) {
            return true;
        }
        // Шаг 3: Оптимизация - убираем все четные > 2 и кратные 3 (кроме 3)
        if (number % 2 == 0 || number % 3 == 0) {
            return false;
        }

        // Шаг 4: Проверяем делители вида 6k ± 1 до sqrt(number)
        // Все простые числа > 3 можно представить в виде 6k ± 1.
        // Нам нужно проверять делимость только на простые числа. Проверяя
        // i и i+2 для i=5, 11, 17... (шаг 6), мы покрываем все простые делители.
        // Используем i * i <= number вместо i <= Math.sqrt(number) для эффективности
        // и избежания работы с double. Применяем long для i*i, чтобы избежать
        // переполнения при проверке квадрата i для больших number (близких к Integer.MAX_VALUE).
        for (long i = 5; i * i <= number; i += 6) {
            // Если number делится на i (формат 6k-1) или на i+2 (формат 6k+1)
            if (number % i == 0 || number % (i + 2) == 0) {
                return false; // Найден делитель - число не простое
            }
        }
        // Если цикл завершился без нахождения делителей, число простое
        return true;
    }

    /**
     * Точка входа для демонстрации работы метода проверки на простоту.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        PrimeNumberCheck sol = new PrimeNumberCheck();

        System.out.println("--- Testing Prime Numbers ---");
        runPrimeTest(sol, -5, false);   // Не натуральное
        runPrimeTest(sol, 0, false);    // Не натуральное > 1
        runPrimeTest(sol, 1, false);    // Не > 1
        runPrimeTest(sol, 2, true);     // Базовый случай
        runPrimeTest(sol, 3, true);     // Базовый случай
        runPrimeTest(sol, 4, false);    // Четное > 2
        runPrimeTest(sol, 5, true);     // Простое (6k-1)
        runPrimeTest(sol, 6, false);    // Четное > 2
        runPrimeTest(sol, 7, true);     // Простое (6k+1)
        runPrimeTest(sol, 9, false);    // Кратное 3
        runPrimeTest(sol, 10, false);   // Четное > 2
        runPrimeTest(sol, 11, true);    // Простое (6k-1)
        runPrimeTest(sol, 13, true);    // Простое (6k+1)
        runPrimeTest(sol, 15, false);   // Кратное 3 (и 5)
        runPrimeTest(sol, 17, true);    // Простое (6k-1)
        runPrimeTest(sol, 25, false);   // Составное (5*5)
        runPrimeTest(sol, 91, false);   // Составное (7*13)
        runPrimeTest(sol, 97, true);    // Простое
        runPrimeTest(sol, 101, true);   // Простое
        runPrimeTest(sol, 103, true);   // Простое
        runPrimeTest(sol, 107, true);   // Простое
        runPrimeTest(sol, 109, true);   // Простое
        runPrimeTest(sol, 111, false);  // Составное (3*37)
        runPrimeTest(sol, 113, true);   // Простое
        runPrimeTest(sol, 2147483647, true); // Max Int (простое Мерсенна)
    }

    /**
     * Вспомогательный метод для тестирования isPrime.
     *
     * @param sol      Экземпляр решателя.
     * @param number   Число для проверки.
     * @param expected Ожидаемый результат (true - простое, false - составное/не простое).
     */
    private static void runPrimeTest(PrimeNumberCheck sol, int number, boolean expected) {
        boolean result = sol.isPrime(number);
        System.out.printf("isPrime(%d): %b (Expected: %b) %s%n",
                number, result, expected, (result == expected ? "" : "<- MISMATCH!"));
    }
}
