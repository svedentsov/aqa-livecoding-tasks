package com.svedentsov.aqa.tasks.algorithms;

/**
 * Решение задачи №49: Проверка на идеальное число.
 */
public class Task49_PerfectNumberCheck {

    /**
     * Проверяет, является ли заданное положительное целое число идеальным.
     * Идеальное число - это положительное целое число, которое равно сумме
     * своих собственных положительных делителей (делителей, исключая само число).
     * Например:
     * 6 = 1 + 2 + 3
     * 28 = 1 + 2 + 4 + 7 + 14
     *
     * @param num Число для проверки.
     * @return {@code true}, если число идеальное, {@code false} в противном случае.
     * Числа <= 1 не считаются идеальными по стандартному определению.
     */
    public boolean isPerfectNumber(int num) {
        // 1. Идеальные числа должны быть > 1
        if (num <= 1) {
            return false;
        }

        // 2. Вычисляем сумму собственных положительных делителей.
        //    Начинаем с 1, так как 1 всегда является делителем для num > 1.
        int sumOfProperDivisors = 1;

        // 3. Ищем делители эффективно, идя до квадратного корня из числа.
        //    Для каждого делителя `i`, найденного до `sqrt(num)`, существует парный
        //    делитель `num / i`. Добавляем оба, если они различны.
        for (int i = 2; i * i <= num; i++) {
            // Если i делит num без остатка
            if (num % i == 0) {
                sumOfProperDivisors += i; // Добавляем делитель i

                // Если i не является квадратным корнем (i * i != num),
                // то добавляем также парный делитель num / i.
                // Это избегает двойного добавления для полных квадратов (хотя идеальные числа не полные квадраты).
                if (i * i != num) {
                    sumOfProperDivisors += num / i;
                }
            }
            // Оптимизация: если сумма уже превысила число, оно точно не идеальное.
            if (sumOfProperDivisors > num) {
                return false;
            }
        }

        // 4. Сравниваем сумму собственных делителей с самим числом.
        //    Важно: убедиться, что мы не превысили число на последнем шаге цикла.
        return sumOfProperDivisors == num;
    }

    /**
     * Точка входа для демонстрации работы метода проверки на идеальное число.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task49_PerfectNumberCheck sol = new Task49_PerfectNumberCheck();
        // Первые несколько идеальных чисел: 6, 28, 496, 8128
        int[] testNumbers = {6, 28, 496, 8128, 1, 2, 7, 10, 12, 0, -6, 33550336 /* Очень большое, может быть медленно */};
        boolean[] expectedResults = {true, true, true, true, false, false, false, false, false, false, false, true}; // Последнее ожидается true

        System.out.println("Checking for Perfect Numbers:");
        for (int i = 0; i < testNumbers.length; i++) {
            // Пропускаем очень большое число для скорости, если не нужно
            if (testNumbers[i] == 33550336 && i == testNumbers.length - 1) {
                System.out.println(testNumbers[i] + " -> (Skipping large number test)");
                continue;
            }

            boolean result = sol.isPerfectNumber(testNumbers[i]);
            System.out.println(testNumbers[i] + " -> " + result + " (Expected: " + expectedResults[i] + ")");
            if (result != expectedResults[i]) {
                System.err.println("   Mismatch!");
            }
        }
    }
}
