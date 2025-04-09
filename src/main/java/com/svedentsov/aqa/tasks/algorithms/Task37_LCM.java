package com.svedentsov.aqa.tasks.algorithms;

/**
 * Решение задачи №37: Найти наименьшее общее кратное (НОК / LCM).
 */
public class Task37_LCM {

    /**
     * Вспомогательный метод для вычисления НОД (Наибольший Общий Делитель).
     * Используется итеративный алгоритм Евклида.
     *
     * @param a Первое целое число.
     * @param b Второе целое число.
     * @return НОД чисел |a| и |b|.
     */
    public int gcd(int a, int b) {
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
     * Вычисляет наименьшее общее кратное (НОК) двух целых чисел a и b.
     * Использует формулу: НОК(a, b) = (|a * b|) / НОД(a, b).
     * Обрабатывает случаи с нулями (НОК(a, 0) = 0).
     * Использует тип long для промежуточного произведения (|a * b|), чтобы избежать
     * преждевременного переполнения int.
     *
     * @param a Первое целое число.
     * @param b Второе целое число.
     * @return Наименьшее общее кратное чисел a и b. Возвращает 0, если одно из чисел 0.
     * @throws ArithmeticException если результат НОК выходит за пределы диапазона int.
     */
    public int lcm(int a, int b) {
        // НОК с нулем всегда равен нулю
        if (a == 0 || b == 0) {
            return 0;
        }

        // Вычисляем НОД абсолютных значений
        int greatestCommonDivisor = gcd(a, b);
        // Если НОД равен 0, это значит a=0 и b=0, что уже обработано выше.

        // Вычисляем произведение |a * b| с использованием long
        // Math.abs(a) * (Math.abs(b) / greatestCommonDivisor) - более безопасный способ
        // избежать переполнения на этапе умножения, но требует аккуратности с порядком.
        // Проверим (|a| / gcd) * |b|
        long valA = Math.abs((long) a);
        long valB = Math.abs((long) b);
        long result = (valA / greatestCommonDivisor) * valB;

         /* Альтернативный расчет:
         long product = valA * valB;
         long result = product / greatestCommonDivisor;
         Этот вариант более подвержен переполнению на этапе умножения.
         */


        // Проверяем, не превышает ли результат максимальное значение int
        if (result > Integer.MAX_VALUE) {
            throw new ArithmeticException("LCM result (" + result + ") exceeds Integer.MAX_VALUE for inputs " + a + " and " + b);
        }
        return (int) result;
    }

    /**
     * Точка входа для демонстрации работы метода вычисления НОК.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task37_LCM sol = new Task37_LCM();
        int[][] pairs = {
                {4, 6}, {6, 4}, {5, 7}, {12, 18}, {0, 5}, {5, 0},
                {7, 7}, {-4, 6}, {-4, -6}, {1, 1}, {1, 8}, {8, 1}
        };
        int[] expected = {12, 12, 35, 36, 0, 0, 7, 12, 12, 1, 8, 8};

        System.out.println("--- LCM Calculation ---");
        for (int i = 0; i < pairs.length; i++) {
            try {
                int result = sol.lcm(pairs[i][0], pairs[i][1]);
                System.out.println("lcm(" + pairs[i][0] + ", " + pairs[i][1] + ") = " + result + " (Expected: " + expected[i] + ")");
                if (result != expected[i]) System.err.println("   Mismatch!");
            } catch (ArithmeticException e) {
                System.err.println("Error calculating lcm(" + pairs[i][0] + ", " + pairs[i][1] + "): " + e.getMessage());
            }
        }

        // Пример с потенциальным переполнением int на промежуточном этапе
        int largeA = 50000;
        int largeB = 60000; // gcd = 10000
        System.out.println("\n--- Large Numbers ---");
        try {
            // lcm = (50000 / 10000) * 60000 = 5 * 60000 = 300000 (умещается в int)
            System.out.println("lcm(" + largeA + ", " + largeB + ") = " + sol.lcm(largeA, largeB)); // 300000
        } catch (ArithmeticException e) {
            System.err.println("Error calculating LCM for large numbers: " + e.getMessage());
        }

        // Пример с переполнением результата
        int maxA = Integer.MAX_VALUE / 2; // ~1.07 * 10^9
        int maxB = Integer.MAX_VALUE / 3 * 2; // ~1.43 * 10^9, gcd(maxA, maxB) может быть не 1
        System.out.println("\n--- Result Overflow Test ---");
        try {
            // Результат lcm будет > Integer.MAX_VALUE
            System.out.println("lcm(" + maxA + ", " + maxB + ") = " + sol.lcm(maxA, maxB));
        } catch (ArithmeticException e) {
            System.err.println("Caught expected overflow: " + e.getMessage());
        }
    }
}
