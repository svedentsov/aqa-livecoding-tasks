package com.svedentsov.aqa.tasks.strings;

import java.util.*;

/**
 * Решение задачи №46: Найти все перестановки строки.
 * <p>
 * Описание: (Проверяет: рекурсия, работа со строками/массивами символов)
 * <p>
 * Задание: Напишите метод `List<String> findPermutations(String str)`, который
 * возвращает список всех уникальных перестановок символов строки `str`.
 * <p>
 * Пример: `findPermutations("abc")` -> `["abc", "acb", "bac", "bca", "cab", "cba"]`.
 * `findPermutations("aab")` -> `["aab", "aba", "baa"]`.
 */
public class FindStringPermutations {

    /**
     * Находит все уникальные перестановки символов строки.
     * Использует рекурсивный метод с возвратом (backtracking) и {@link HashSet} для
     * автоматического обеспечения уникальности перестановок, особенно важно,
     * если во входной строке есть повторяющиеся символы.
     *
     * @param str Исходная строка. Может быть null.
     * @return Список (ArrayList) уникальных перестановок строки.
     * Возвращает пустой список для null или пустой строки. Порядок перестановок
     * в итоговом списке зависит от HashSet и последующей сортировки в `main`.
     */
    public List<String> findPermutationsSet(String str) {
        // Используем Set для хранения уникальных перестановок
        Set<String> resultSet = new HashSet<>();
        if (str == null || str.isEmpty()) {
            return new ArrayList<>(); // Возвращаем пустой список
        }

        char[] chars = str.toCharArray();
        boolean[] used = new boolean[chars.length];
        StringBuilder currentPermutation = new StringBuilder();

        generatePermutationsRecursive(chars, used, currentPermutation, resultSet);

        return new ArrayList<>(resultSet); // Преобразуем Set в List
    }

    /**
     * Находит все уникальные перестановки строки (альтернативный метод).
     * Оптимизирован для строк с повторяющимися символами без использования Set.
     * Требует предварительной сортировки массива символов для корректной работы
     * логики пропуска дубликатов.
     *
     * @param str Исходная строка.
     * @return Список уникальных перестановок. Порядок будет лексикографическим.
     */
    public List<String> findPermutationsOptimized(String str) {
        List<String> resultList = new ArrayList<>();
        if (str == null || str.isEmpty()) {
            return resultList;
        }

        char[] chars = str.toCharArray();
        Arrays.sort(chars); // Сортировка обязательна для пропуска дубликатов
        boolean[] used = new boolean[chars.length];
        StringBuilder currentPermutation = new StringBuilder();

        generatePermutationsOptimizedRecursive(chars, used, currentPermutation, resultList);
        return resultList;
    }

    /**
     * Точка входа для демонстрации работы методов поиска перестановок.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        FindStringPermutations sol = new FindStringPermutations();
        String[] testStrings = {"abc", "aab", "a", "", null, "ABA", "112"};

        System.out.println("--- Finding All Unique Permutations ---");
        for (String str : testStrings) {
            runPermutationTest(sol, str);
        }
    }

    /**
     * Рекурсивный вспомогательный метод (backtracking) для генерации перестановок
     * с использованием Set для уникальности.
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
     * Рекурсивный вспомогательный метод для оптимизированной генерации перестановок
     * без использования Set (требует отсортированного массива `chars`).
     *
     * @param chars              Отсортированный массив символов исходной строки.
     * @param used               Массив флагов использованных символов.
     * @param currentPermutation StringBuilder для построения текущей перестановки.
     * @param resultList         Список для сбора уникальных перестановок.
     */
    private void generatePermutationsOptimizedRecursive(char[] chars, boolean[] used, StringBuilder currentPermutation, List<String> resultList) {
        if (currentPermutation.length() == chars.length) {
            resultList.add(currentPermutation.toString());
            return;
        }

        for (int i = 0; i < chars.length; i++) {
            // Условия пропуска для избежания дубликатов:
            // 1. Пропустить, если символ уже использован в текущей перестановке.
            // 2. Пропустить, если текущий символ такой же, как предыдущий (i > 0 && chars[i] == chars[i-1])
            //    И при этом предыдущий символ НЕ БЫЛ использован (!used[i-1]). Это значит, что
            //    ветка рекурсии с использованием предыдущего идентичного символа еще не завершена,
            //    и использование текущего символа создаст дубликат перестановки.
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
     * Вспомогательный метод для тестирования поиска перестановок.
     *
     * @param sol Экземпляр решателя.
     * @param str Входная строка.
     */
    private static void runPermutationTest(FindStringPermutations sol, String str) {
        String inputStr = (str == null ? "null" : "'" + str + "'");
        System.out.println("\nInput: " + inputStr);
        try {
            List<String> result1 = sol.findPermutationsSet(str);
            Collections.sort(result1); // Сортируем для сравнения и предсказуемости
            System.out.println("  Set Method (" + result1.size() + "): " + result1);
        } catch (Exception e) {
            System.err.println("  Set Method: Error - " + e.getMessage());
        }
        try {
            List<String> result2 = sol.findPermutationsOptimized(str);
            // Оптимизированный метод должен возвращать уже отсортированный результат
            System.out.println("  Opt Method (" + result2.size() + "): " + result2);
        } catch (Exception e) {
            System.err.println("  Opt Method: Error - " + e.getMessage());
        }
    }
}
