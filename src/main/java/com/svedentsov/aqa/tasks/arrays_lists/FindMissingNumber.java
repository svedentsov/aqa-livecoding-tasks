package com.svedentsov.aqa.tasks.arrays_lists;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * Решение задачи №15: Найти отсутствующее число в последовательности.
 * <p>
 * Описание: Дан массив/список чисел от 1 до N с одним пропущенным числом.
 * Найти это число. (Проверяет: логика, арифметика/Set)
 * <p>
 * Задание: Напишите метод `int findMissingNumber(int[] numbers, int n)`,
 * который принимает массив `numbers`, содержащий уникальные числа от 1 до `n`
 * (включительно), кроме одного отсутствующего. Метод должен вернуть это
 * отсутствующее число.
 * <p>
 * Пример: `findMissingNumber(new int[]{1, 2, 4, 5}, 5)` -> `3`.
 */
public class FindMissingNumber {

    /**
     * Находит отсутствующее число в массиве [1..n] с помощью суммы арифметической прогрессии.
     * Сложность O(n) по времени, O(1) по памяти.
     * Уязвим для переполнения long при очень больших n.
     *
     * @param numbers Массив с уникальными числами от 1 до n, кроме одного. Не должен быть null.
     * @param n       Верхняя граница последовательности (максимальное ожидаемое число).
     * @return Отсутствующее число.
     * @throws IllegalArgumentException если массив null, n < 1, или длина массива не n-1.
     */
    public int findMissingNumberSum(int[] numbers, int n) {
        if (numbers == null) throw new IllegalArgumentException("Input array cannot be null.");
        if (n < 1) throw new IllegalArgumentException("Upper bound 'n' must be at least 1.");
        // Важная проверка для корректности метода суммы
        if (numbers.length != n - 1) {
            throw new IllegalArgumentException("Array length (" + numbers.length + ") must be n-1 (" + (n - 1) + ").");
        }

        // Ожидаемая сумма 1 + 2 + ... + n = n * (n + 1) / 2
        // Используем long для промежуточных вычислений, чтобы уменьшить риск переполнения
        long expectedSum = (long) n * (n + 1) / 2;

        // Фактическая сумма чисел в массиве (используем Stream API для краткости)
        long actualSum = IntStream.of(numbers).asLongStream().sum();
        // или итеративно:
        // long actualSum = 0;
        // for (int number : numbers) {
        //     actualSum += number;
        // }

        long missing = expectedSum - actualSum;

        // Дополнительная проверка: результат должен быть в диапазоне [1, n]
        if (missing < 1 || missing > n) {
            // Это не должно произойти при корректных входных данных (уникальные, 1..n, одно пропущено)
            System.err.println("Warning: Calculated missing number (" + missing + ") outside range [1.." + n + "]. Input assumption violated?");
        }

        return (int) missing;
    }

    /**
     * Находит отсутствующее число в массиве [1..n] с использованием операции XOR.
     * Сложность O(n) по времени, O(1) по памяти. Безопасен от переполнения.
     * Работает корректно, даже если длина массива не n-1 (но результат осмыслен только при n-1).
     *
     * @param numbers Массив с числами от 1 до n, кроме одного. Не должен быть null.
     * @param n       Верхняя граница последовательности.
     * @return Отсутствующее число.
     * @throws IllegalArgumentException если массив null или n < 1.
     */
    public int findMissingNumberXor(int[] numbers, int n) {
        if (numbers == null) throw new IllegalArgumentException("Input array cannot be null.");
        if (n < 1) throw new IllegalArgumentException("Upper bound 'n' must be at least 1.");

        // XOR всех чисел от 1 до n
        int xorExpected = 0;
        for (int i = 1; i <= n; i++) {
            xorExpected ^= i;
        }

        // XOR всех чисел в данном массиве
        int xorActual = 0;
        for (int number : numbers) {
            xorActual ^= number;
        }

        // XOR(1..n) ^ XOR(массив) = недостающее число
        // Пример: n=5, arr={1,2,4,5}
        // xorExpected = 1^2^3^4^5
        // xorActual   = 1^2^0^4^5
        // result = (1^2^3^4^5) ^ (1^2^0^4^5) = (1^1)^(2^2)^3^(4^4)^(5^5) = 0^0^3^0^0 = 3
        return xorExpected ^ xorActual;
    }

    /**
     * Находит отсутствующее число в массиве [1..n] с использованием множества (Set).
     * Сложность O(n) по времени и O(n) по памяти.
     *
     * @param numbers Массив чисел. Не должен быть null.
     * @param n       Верхняя граница.
     * @return Отсутствующее число.
     * @throws IllegalArgumentException если массив null, n < 1, или отсутствующее число не найдено
     *                                  (т.е. все числа от 1 до n присутствуют, что нарушает условие).
     */
    public int findMissingNumberSet(int[] numbers, int n) {
        if (numbers == null) throw new IllegalArgumentException("Input array cannot be null.");
        if (n < 1) throw new IllegalArgumentException("Upper bound 'n' must be at least 1.");

        // Добавляем все числа из массива в Set для быстрой проверки наличия
        Set<Integer> numSet = new HashSet<>();
        for (int num : numbers) {
            numSet.add(num);
        }

        // Проверяем, какое число от 1 до n отсутствует в сете
        for (int i = 1; i <= n; i++) {
            if (!numSet.contains(i)) {
                return i; // Нашли отсутствующее
            }
        }

        // Если цикл завершился, значит, все числа от 1 до n были в массиве,
        // что противоречит условию задачи (одно число должно отсутствовать).
        throw new IllegalArgumentException("No missing number found in range [1.." + n + "]. Input violates constraints.");
    }

    /**
     * Точка входа для демонстрации работы методов поиска пропущенного числа.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        FindMissingNumber sol = new FindMissingNumber();

        runMissingNumberTest(sol, new int[]{1, 2, 4, 5}, 5, "Стандартный случай (пропущено 3)"); // 3
        runMissingNumberTest(sol, new int[]{3, 1, 2, 5}, 5, "Другой порядок (пропущено 4)"); // 4
        runMissingNumberTest(sol, new int[]{1}, 2, "Малый n (пропущено 2)"); // 2
        runMissingNumberTest(sol, new int[]{}, 1, "n=1 (пропущено 1)"); // 1
        runMissingNumberTest(sol, new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10}, 10, "Пропущено 1"); // 1
        runMissingNumberTest(sol, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, 10, "Пропущено n (10)"); // 10
        runMissingNumberTest(sol, new int[]{1, 3, 4, 5, 6, 2, 8, 7, 10}, 10, "Большой n (пропущено 9)"); // 9

        // Тесты некорректных входных данных
        runMissingNumberTest(sol, null, 5, "Null массив"); // Error
        runMissingNumberTest(sol, new int[]{1, 2, 3}, 0, "n < 1"); // Error
        runMissingNumberTest(sol, new int[]{1, 2, 3}, 3, "Неверная длина массива (Sum/Set методы)"); // Sum: Error, XOR: 0 (неверно), Set: Error
        runMissingNumberTest(sol, new int[]{1, 2, 4, 4}, 5, "Массив с дубликатами (результат неопределен)"); // Результаты могут быть неверными
        runMissingNumberTest(sol, new int[]{1, 2, 6, 5}, 5, "Число вне диапазона (результат неопределен)"); // Результаты могут быть неверными
    }

    /**
     * Вспомогательный метод для тестирования методов поиска пропущенного числа.
     *
     * @param sol         Экземпляр решателя.
     * @param arr         Тестовый массив.
     * @param n           Верхняя граница последовательности.
     * @param description Описание теста.
     */
    private static void runMissingNumberTest(FindMissingNumber sol, int[] arr, int n, String description) {
        System.out.println("\n--- " + description + " ---");
        String arrString = (arr == null ? "null" : Arrays.toString(arr));
        System.out.println("Input array: " + arrString + ", n = " + n);
        try {
            System.out.println("  Sum method: " + sol.findMissingNumberSum(arr, n));
        } catch (IllegalArgumentException e) {
            System.out.println("  Sum method: Error - " + e.getMessage());
        } catch (Exception e) {
            System.out.println("  Sum method: Unexpected Error - " + e.getMessage());
        }
        try {
            System.out.println("  XOR method: " + sol.findMissingNumberXor(arr, n));
        } catch (IllegalArgumentException e) {
            System.out.println("  XOR method: Error - " + e.getMessage());
        } catch (Exception e) {
            System.out.println("  XOR method: Unexpected Error - " + e.getMessage());
        }
        try {
            System.out.println("  Set method: " + sol.findMissingNumberSet(arr, n));
        } catch (IllegalArgumentException e) {
            System.out.println("  Set method: Error - " + e.getMessage());
        } catch (Exception e) {
            System.out.println("  Set method: Unexpected Error - " + e.getMessage());
        }
    }
}
