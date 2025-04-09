package com.svedentsov.aqa.tasks.strings;

import java.util.*;

/**
 * Решение задачи №46: Найти все перестановки строки.
 */
public class Task46_FindStringPermutations {

    /**
     * Находит все уникальные перестановки символов строки.
     * Использует рекурсивный метод с возвратом (backtracking) и {@link HashSet} для
     * автоматического обеспечения уникальности перестановок, если во входной
     * строке есть повторяющиеся символы.
     *
     * @param str Исходная строка. Может быть null.
     * @return Список (List) уникальных перестановок строки.
     * Возвращает пустой список для null или пустой строки. Порядок перестановок не гарантирован.
     */
    public List<String> findPermutationsSet(String str) {
        // Используем Set для хранения уникальных перестановок
        Set<String> resultSet = new HashSet<>();
        // Обработка null и пустой строки
        if (str == null || str.isEmpty()) {
            return new ArrayList<>(resultSet); // Возвращаем пустой список
        }

        char[] chars = str.toCharArray();
        boolean[] used = new boolean[chars.length]; // Отслеживание использованных символов
        StringBuilder currentPermutation = new StringBuilder(); // Построение перестановки

        // Запуск рекурсивной генерации
        generatePermutationsRecursive(chars, used, currentPermutation, resultSet);

        return new ArrayList<>(resultSet); // Преобразуем Set в List
    }

    /**
     * Рекурсивный вспомогательный метод (backtracking) для генерации перестановок.
     *
     * @param chars              Массив символов исходной строки.
     * @param used               Массив флагов использованных символов для текущей ветки рекурсии.
     * @param currentPermutation StringBuilder для построения текущей перестановки.
     * @param resultSet          Множество для сбора уникальных готовых перестановок.
     */
    private void generatePermutationsRecursive(char[] chars, boolean[] used, StringBuilder currentPermutation, Set<String> resultSet) {
        // Базовый случай: длина текущей перестановки равна длине исходной строки
        if (currentPermutation.length() == chars.length) {
            resultSet.add(currentPermutation.toString()); // Добавляем готовую перестановку
            return; // Завершаем эту ветку рекурсии
        }

        // Пробуем добавить каждый НЕиспользованный символ на следующую позицию
        for (int i = 0; i < chars.length; i++) {
            if (!used[i]) { // Если символ с индексом i еще не использован
                // Выбираем: помечаем как использованный и добавляем к строке
                used[i] = true;
                currentPermutation.append(chars[i]);

                // Рекурсия: генерируем оставшуюся часть перестановки
                generatePermutationsRecursive(chars, used, currentPermutation, resultSet);

                // Отменяем выбор (backtrack): снимаем пометку и удаляем символ из строки
                used[i] = false;
                currentPermutation.deleteCharAt(currentPermutation.length() - 1);
            }
        }
    }

    /**
     * Находит все уникальные перестановки строки (альтернативный метод).
     * Оптимизирован для строк с повторяющимися символами без использования Set.
     * Требует предварительной сортировки массива символов.
     *
     * @param str Исходная строка.
     * @return Список уникальных перестановок. Порядок может быть лексикографическим, если входная строка отсортирована.
     */
    public List<String> findPermutationsOptimized(String str) {
        List<String> resultList = new ArrayList<>();
        if (str == null || str.isEmpty()) return resultList;

        char[] chars = str.toCharArray();
        Arrays.sort(chars); // Сортировка обязательна для этой оптимизации
        boolean[] used = new boolean[chars.length];
        StringBuilder currentPermutation = new StringBuilder();

        generatePermutationsOptimizedRecursive(chars, used, currentPermutation, resultList);
        return resultList;
    }

    /**
     * Рекурсивный вспомогательный метод для оптимизированной генерации.
     */
    private void generatePermutationsOptimizedRecursive(char[] chars, boolean[] used, StringBuilder currentPermutation, List<String> resultList) {
        if (currentPermutation.length() == chars.length) {
            resultList.add(currentPermutation.toString());
            return;
        }

        for (int i = 0; i < chars.length; i++) {
            // Условия пропуска для избежания дубликатов:
            if (used[i] || (i > 0 && chars[i] == chars[i - 1] && !used[i - 1])) {
                continue;
            }
            // Выбираем
            used[i] = true;
            currentPermutation.append(chars[i]);
            // Рекурсия
            generatePermutationsOptimizedRecursive(chars, used, currentPermutation, resultList);
            // Отменяем выбор
            used[i] = false;
            currentPermutation.deleteCharAt(currentPermutation.length() - 1);
        }
    }

    /**
     * Точка входа для демонстрации работы методов поиска перестановок.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task46_FindStringPermutations sol = new Task46_FindStringPermutations();
        String[] testStrings = {"abc", "aab", "a", "", null, "ABA"};

        for (String str : testStrings) {
            System.out.println("\nPermutations of '" + str + "':");

            List<String> result1 = sol.findPermutationsSet(str);
            // Сортируем для консистентного вывода
            Collections.sort(result1);
            System.out.println("  Set Method (" + result1.size() + "): " + result1);

            List<String> result2 = sol.findPermutationsOptimized(str);
            // Результат Optimized уже может быть отсортирован лексикографически
            System.out.println("  Opt Method (" + result2.size() + "): " + result2);
        }
        /* Примерный вывод:
        Permutations of 'abc':
          Set Method (6): [abc, acb, bac, bca, cab, cba]
          Opt Method (6): [abc, acb, bac, bca, cab, cba]

        Permutations of 'aab':
          Set Method (3): [aab, aba, baa]
          Opt Method (3): [aab, aba, baa]

        Permutations of 'a':
          Set Method (1): [a]
          Opt Method (1): [a]

        Permutations of '':
          Set Method (0): []
          Opt Method (0): []

        Permutations of 'null':
          Set Method (0): []
          Opt Method (0): []

        Permutations of 'ABA':
          Set Method (3): [AAB, ABA, BAA]
          Opt Method (3): [AAB, ABA, BAA]
        */
    }
}
