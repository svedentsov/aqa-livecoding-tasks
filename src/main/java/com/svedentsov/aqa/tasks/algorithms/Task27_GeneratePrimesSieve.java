package com.svedentsov.aqa.tasks.algorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * Решение задачи №27: Генерация простых чисел до N (Решето Эратосфена).
 */
public class Task27_GeneratePrimesSieve {

    /**
     * Генерирует список всех простых чисел от 2 до n включительно,
     * используя алгоритм "Решето Эратосфена".
     * Алгоритм работает путем итеративного помечания как составных (не простых)
     * чисел, кратных каждому простому числу, начиная с 2.
     * Сложность O(n log log n) по времени, O(n) по памяти.
     *
     * @param n Верхняя граница (включительно). Должна быть >= 0.
     * @return Список простых чисел от 2 до n. Возвращает пустой список, если n < 2.
     */
    public List<Integer> generatePrimes(int n) {
        List<Integer> primes = new ArrayList<>();
        // Если n < 2, простых чисел в этом диапазоне нет
        if (n < 2) {
            return primes;
        }

        // Массив boolean для отметки составных чисел.
        // isComposite[i] = true означает, что число i - составное.
        // Размер n + 1, чтобы индекс i соответствовал числу i.
        boolean[] isComposite = new boolean[n + 1];
        // 0 и 1 не являются простыми, но можно не помечать, т.к. циклы начинаются с 2.

        // Проходим по числам от p = 2 до sqrt(n).
        // Если p не помечено как составное, оно простое.
        for (int p = 2; p * p <= n; p++) {
            if (!isComposite[p]) {
                // Помечаем все кратные p как составные, начиная с p*p.
                // (Оптимизация: числа меньше p*p уже были помечены меньшими простыми).
                // Шаг равен p.
                for (int multiple = p * p; multiple <= n; multiple += p) {
                    isComposite[multiple] = true;
                }
            }
        }

        // Собираем все числа от 2 до n, которые НЕ были помечены как составные.
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
        Task27_GeneratePrimesSieve sol = new Task27_GeneratePrimesSieve();
        int[] limits = {10, 20, 30, 2, 1, 0, 100};

        for (int limit : limits) {
            System.out.println("Primes up to " + limit + ": " + sol.generatePrimes(limit));
        }
        /*
        Ожидаемый вывод:
        Primes up to 10: [2, 3, 5, 7]
        Primes up to 20: [2, 3, 5, 7, 11, 13, 17, 19]
        Primes up to 30: [2, 3, 5, 7, 11, 13, 17, 19, 23, 29]
        Primes up to 2: [2]
        Primes up to 1: []
        Primes up to 0: []
        Primes up to 100: [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97]
        */
    }
}
