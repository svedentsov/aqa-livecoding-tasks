package com.svedentsov.aqa.tasks.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Решение задачи №27: Генерация простых чисел до N (Решето Эратосфена).
 * Описание: Используя, например, Решето Эратосфена.
 * (Проверяет: алгоритмы, массивы boolean, циклы)
 * Задание: Напишите метод `List<Integer> generatePrimes(int n)`, который возвращает
 * список всех простых чисел от 2 до `n` включительно. Используйте алгоритм "Решето Эратосфена".
 * Пример: `generatePrimes(10)` -> `[2, 3, 5, 7]`.
 * `generatePrimes(20)` -> `[2, 3, 5, 7, 11, 13, 17, 19]`.
 */
public class GeneratePrimesSieve {

    /**
     * Генерирует список всех простых чисел от 2 до n включительно,
     * используя алгоритм "Решето Эратосфена".
     * Сложность: O(n log log n) по времени, O(n) по памяти.
     *
     * @param n Верхняя граница (включительно). Должна быть >= 0.
     * @return Список простых чисел от 2 до n. Возвращает пустой список, если n < 2.
     * @throws OutOfMemoryError если n слишком велико для выделения boolean массива.
     */
    public List<Integer> generatePrimes(int n) {
        // Ранний выход для n < 2
        if (n < 2) {
            return Collections.emptyList(); // Нет простых чисел <= 1
        }

        // Массив boolean для отметки составных чисел.
        // isComposite[i] = true означает, что число i - составное.
        boolean[] isComposite = new boolean[n + 1]; // Индексы от 0 до n

        // Проходим по числам от p = 2 до sqrt(n).
        for (int p = 2; p * p <= n; p++) {
            // Если p НЕ помечено как составное, значит p - простое число.
            if (!isComposite[p]) {
                // Помечаем все кратные p как составные, начиная с p*p.
                for (int multiple = p * p; multiple <= n; multiple += p) {
                    // Проверка на переполнение индекса (теоретически возможна при p*p > Integer.MAX_VALUE)
                    // но маловероятна, так как n ограничено размером массива/памятью.
                    // if (multiple < 0) break; // Прервать, если произошло переполнение
                    isComposite[multiple] = true;
                }
            }
        }

        // Собираем все числа от 2 до n, которые НЕ были помечены как составные.
        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (!isComposite[i]) {
                primes.add(i);
            }
        }
        return primes;
    }
}
