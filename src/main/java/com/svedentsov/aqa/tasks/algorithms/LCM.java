package com.svedentsov.aqa.tasks.algorithms;

/**
 * Решение задачи №37: Найти наименьшее общее кратное (НОК / LCM).
 * <p>
 * Описание: Написать функцию для нахождения НОК двух чисел.
 * (Проверяет: НОД, арифметика)
 * <p>
 * Задание: Напишите метод `long lcm(int a, int b)`, который вычисляет наименьшее
 * общее кратное двух целых чисел `a` и `b`. Можно использовать формулу
 * `lcm(a, b) = (|a * b|) / gcd(a, b)`. Обработайте нули и возможные переполнения.
 * Возвращайте `long`, т.к. НОК может легко превысить `Integer.MAX_VALUE`.
 * <p>
 * Пример: `lcm(4, 6)` -> `12`. `lcm(5, 7)` -> `35`. `lcm(0, 5)` -> `0`.
 */
public class LCM {

    /**
     * Вычисляет наименьшее общее кратное (НОК) двух целых чисел a и b.
     * Использует формулу: НОК(a, b) = (|a| / НОД(a, b)) * |b|.
     * Эта форма записи предпочтительнее, чем (|a*b|)/НОД, так как она
     * уменьшает риск переполнения на промежуточном этапе умножения.
     * Обрабатывает случаи с нулями (НОК(a, 0) = 0).
     * Использует тип long для вычислений и результата, чтобы избежать переполнения.
     *
     * @param a Первое целое число.
     * @param b Второе целое число.
     * @return Наименьшее общее кратное чисел a и b (тип long). Возвращает 0, если одно из чисел 0.
     */
    public long lcm(int a, int b) {
        // НОК с нулем всегда равен нулю по определению
        if (a == 0 || b == 0) {
            return 0L;
        }

        // Вычисляем НОД абсолютных значений
        int greatestCommonDivisor = gcd(a, b);
        // Если НОД равен 0, это значит a=0 и b=0, что уже обработано.
        // НОД не может быть 0, если хотя бы одно из чисел не 0.

        // Вычисляем НОК по формуле (|a| / НОД(a, b)) * |b|
        // Используем long для промежуточных вычислений и результата
        long valA = Math.abs((long) a);
        long valB = Math.abs((long) b);

        // Деление выполняется до умножения, чтобы уменьшить промежуточные значения
        return (valA / greatestCommonDivisor) * valB;
    }

    /**
     * Точка входа для демонстрации работы метода вычисления НОК.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        LCM sol = new LCM();
        int[][] pairs = {
                {4, 6}, {6, 4}, {5, 7}, {12, 18}, {0, 5}, {5, 0},
                {7, 7}, {-4, 6}, {-4, -6}, {1, 1}, {1, 8}, {8, 1},
                {21, 49}, {0, 0}
        };
        long[] expected = {12, 12, 35, 36, 0, 0, 7, 12, 12, 1, 8, 8, 147, 0};

        System.out.println("--- Calculating LCM (Least Common Multiple) ---");
        for (int i = 0; i < pairs.length; i++) {
            runLcmTest(sol, pairs[i][0], pairs[i][1], expected[i]);
        }

        // Пример с числами, где произведение |a*b| переполнило бы int
        int largeA = 50000;
        int largeB = 60000;
        long expectedLarge = 300000L;
        runLcmTest(sol, largeA, largeB, expectedLarge);

        // Пример с числами, где результат LCM переполняет int, но умещается в long
        int bigA = Integer.MAX_VALUE; // Простое число
        int bigB = Integer.MAX_VALUE - 1; // Четное, gcd=1
        long expectedMax = (long) bigA * bigB; // Ожидаемый результат > Integer.MAX_VALUE
        runLcmTest(sol, bigA, bigB, expectedMax);

    }

    /**
     * Вспомогательный метод для вычисления НОД (Наибольший Общий Делитель).
     * Используется итеративный алгоритм Евклида.
     * Используется методом lcm.
     *
     * @param a Первое целое число.
     * @param b Второе целое число.
     * @return НОД чисел |a| и |b|.
     */
    private int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    /**
     * Вспомогательный метод для тестирования LCM.
     *
     * @param sol      Экземпляр решателя.
     * @param a        Первое число.
     * @param b        Второе число.
     * @param expected Ожидаемый результат.
     */
    private static void runLcmTest(LCM sol, int a, int b, long expected) {
        System.out.println("\nInput: a=" + a + ", b=" + b);
        try {
            long result = sol.lcm(a, b);
            System.out.printf("  LCM = %d (Expected: %d) %s%n",
                    result, expected, (result == expected ? "" : "<- MISMATCH!"));
        } catch (ArithmeticException e) {
            // Используем Long.MAX_VALUE как маркер ожидаемого переполнения для теста
            if (expected == Long.MAX_VALUE) {
                System.out.println("  LCM = Overflow (Expected)");
            } else {
                System.err.println("  LCM = Error: " + e.getMessage() + " (Expected: " + expected + ")");
            }
        } catch (Exception e) {
            System.err.println("  LCM = Unexpected Error: " + e.getMessage());
        }
    }
}
