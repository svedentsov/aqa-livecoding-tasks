package com.svedentsov.aqa.tasks.strings;

/**
 * Решение задачи №59: Найти индекс первого вхождения подстроки (аналог indexOf).
 * Описание: Реализовать аналог `indexOf`.
 * (Проверяет: работа со строками, циклы)
 * Задание: Напишите метод `int findSubstringIndex(String text, String pattern)`
 * без использования `String.indexOf()`, который находит индекс первого вхождения
 * строки `pattern` в строку `text`. Если `pattern` не найден, верните -1.
 * Пример: `findSubstringIndex("hello world", "world")` -> `6`.
 * `findSubstringIndex("aaaaa", "bba")` -> `-1`.
 * `findSubstringIndex("abc", "")` -> `0`.
 */
public class FindSubstringIndex {

    /**
     * Находит индекс первого вхождения строки {@code pattern} (шаблон) в строку {@code text},
     * не используя встроенный метод {@code String.indexOf()}.
     * Реализует наивный алгоритм поиска подстроки.
     * Сложность: O(n * m) в худшем случае, где n - длина text, m - длина pattern.
     *
     * @param text    Строка, в которой осуществляется поиск. Может быть null.
     * @param pattern Подстрока (шаблон), которую нужно найти. Может быть null.
     * @return Индекс первого символа первого вхождения {@code pattern} в {@code text}.
     * Возвращает 0, если {@code pattern} пуст.
     * Возвращает -1, если {@code pattern} не найден, или если {@code text} или
     * {@code pattern} равен null, или если {@code pattern} длиннее {@code text}.
     */
    public int findSubstringIndex(String text, String pattern) {
        // Шаг 1: Обработка null
        if (text == null || pattern == null) {
            return -1;
        }
        int n = text.length();
        int m = pattern.length();

        // Шаг 2: Пустой паттерн
        if (m == 0) {
            return 0;
        }
        // Шаг 3: Паттерн длиннее текста
        if (m > n) {
            return -1;
        }
        // Шаг 4: Внешний цикл по тексту
        // i <= n - m : последняя возможная стартовая позиция для паттерна
        for (int i = 0; i <= n - m; i++) {
            // Шаг 5: Внутренний цикл по паттерну (сравнение)
            int j = 0;
            while (j < m && text.charAt(i + j) == pattern.charAt(j)) {
                j++;
            }

            // Шаг 8: Проверка, найдено ли полное совпадение
            if (j == m) {
                return i; // Найдено вхождение, начиная с индекса i
            }
            // Иначе продолжаем внешний цикл (следующее значение i)
        }

        // Шаг 9: Паттерн не найден после полного прохода по тексту
        return -1;
    }
}
