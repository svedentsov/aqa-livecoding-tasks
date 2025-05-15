package com.svedentsov.aqa.tasks.arrays_lists;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * Решение задачи №15: Найти отсутствующее число в последовательности.
 * Описание: Дан массив/список чисел от 1 до N с одним пропущенным числом.
 * Найти это число. (Проверяет: логика, арифметика/Set)
 * Задание: Напишите метод `int findMissingNumber(int[] numbers, int n)`,
 * который принимает массив `numbers`, содержащий уникальные числа от 1 до `n`
 * (включительно), кроме одного отсутствующего. Метод должен вернуть это
 * отсутствующее число.
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
            throw new IllegalArgumentException("Array length (" + numbers.length + ") must be n-1 (" + (n - 1) + "). Input violates assumptions.");
        }

        // Ожидаемая сумма 1 + 2 + ... + n = n * (n + 1) / 2
        long expectedSum = (long) n * (n + 1) / 2;

        // Фактическая сумма чисел в массиве
        long actualSum = IntStream.of(numbers).asLongStream().sum();

        long missing = expectedSum - actualSum;

        // Доп. проверка не обязательна, если входные данные соответствуют условиям
        // if (missing < 1 || missing > n) { ... }

        return (int) missing;
    }

    /**
     * Находит отсутствующее число в массиве [1..n] с использованием операции XOR.
     * Сложность O(n) по времени, O(1) по памяти. Безопасен от переполнения.
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
            // Можно добавить проверку num < 1 || num > n, если нужно строже
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
}
