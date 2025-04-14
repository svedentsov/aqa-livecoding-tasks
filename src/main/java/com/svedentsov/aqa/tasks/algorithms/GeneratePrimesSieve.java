package com.svedentsov.aqa.tasks.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Решение задачи №27: Генерация простых чисел до N (Решето Эратосфена).
 * <p>
 * Описание: Используя, например, Решето Эратосфена.
 * (Проверяет: алгоритмы, массивы boolean, циклы)
 * <p>
 * Задание: Напишите метод `List<Integer> generatePrimes(int n)`, который возвращает
 * список всех простых чисел от 2 до `n` включительно. Используйте алгоритм "Решето Эратосфена".
 * <p>
 * Пример: `generatePrimes(10)` -> `[2, 3, 5, 7]`.
 * `generatePrimes(20)` -> `[2, 3, 5, 7, 11, 13, 17, 19]`.
 */
public class GeneratePrimesSieve {

    /**
     * Генерирует список всех простых чисел от 2 до n включительно,
     * используя алгоритм "Решето Эратосфена".
     * Алгоритм работает путем итеративного помечания как составных (не простых)
     * чисел, кратных каждому простому числу, начиная с 2.
     * <p>
     * Сложность: O(n log log n) по времени, O(n) по памяти.
     *
     * @param n Верхняя граница (включительно). Должна быть >= 0.
     * @return Список простых чисел от 2 до n. Возвращает пустой список, если n < 2.
     * @throws IllegalArgumentException если n отрицательное (хотя можно просто вернуть пустой список).
     * @throws OutOfMemoryError         если n слишком велико для выделения boolean массива.
     */
    public List<Integer> generatePrimes(int n) {
        // Ранний выход для n < 2
        if (n < 2) {
            // Можно также бросить исключение, если требуется обрабатывать только n >= 2
            // if (n < 0) throw new IllegalArgumentException("Input n cannot be negative.");
            return Collections.emptyList(); // Нет простых чисел <= 1
        }

        // Массив boolean для отметки составных чисел.
        // isComposite[i] = true означает, что число i - составное.
        // Размер n + 1, чтобы индекс i соответствовал числу i.
        // Изначально все false (т.е. все числа считаются простыми).
        boolean[] isComposite = new boolean[n + 1];
        // 0 и 1 не являются простыми, но их не нужно обрабатывать, т.к. циклы начинаются с 2.

        // Проходим по числам от p = 2 до sqrt(n).
        // Если p*p > n, то дальнейшая проверка не нужна, т.к. все
        // оставшиеся непомеченные числа будут простыми.
        for (int p = 2; p * p <= n; p++) {
            // Если p НЕ помечено как составное, значит p - простое число.
            if (!isComposite[p]) {
                // Помечаем все кратные p как составные, начиная с p*p.
                // Числа меньше p*p (например, 2*p, 3*p...) уже были бы помечены
                // меньшими простыми множителями (2, 3...).
                for (int multiple = p * p; multiple <= n; multiple += p) {
                    isComposite[multiple] = true;
                }
            }
        }

        // Собираем все числа от 2 до n, которые НЕ были помечены как составные.
        // Оцениваем примерный размер списка для оптимизации (pi(n) ~ n/ln(n))
        List<Integer> primes = new ArrayList<>((int) (n / Math.log(Math.max(2, n))) + 1);
        for (int i = 2; i <= n; i++) {
            if (!isComposite[i]) {
                primes.add(i);
            }
        }
        return primes;
    }

    /**
     * Точка входа для демонстрации работы Решета Эратосфена.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        GeneratePrimesSieve sol = new GeneratePrimesSieve();
        int[] limits = {10, 20, 30, 2, 1, 0, -5, 100, 3};

        System.out.println("--- Generating Primes using Sieve of Eratosthenes ---");
        for (int limit : limits) {
            runGeneratePrimesTest(sol, limit);
        }
    }

    /**
     * Вспомогательный метод для тестирования генерации простых чисел.
     *
     * @param sol   Экземпляр решателя.
     * @param limit Верхняя граница n.
     */
    private static void runGeneratePrimesTest(GeneratePrimesSieve sol, int limit) {
        System.out.print("Primes up to " + limit + ": ");
        try {
            List<Integer> primes = sol.generatePrimes(limit);
            System.out.println(primes);
        } catch (IllegalArgumentException | OutOfMemoryError e) {
            System.out.println("Error - " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected Error - " + e.getMessage());
        }
    }
}
