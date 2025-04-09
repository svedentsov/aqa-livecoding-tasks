package com.svedentsov.aqa.tasks.strings;

/**
 * Решение задачи №59: Найти индекс первого вхождения подстроки (аналог indexOf).
 */
public class Task59_FindSubstringIndex {

    /**
     * Находит индекс первого вхождения строки {@code pattern} (шаблон) в строку {@code text},
     * не используя встроенный метод {@code String.indexOf()}.
     * Реализует наивный алгоритм поиска подстроки путем сравнения символов.
     * Сложность O(n * m) в худшем случае, где n - длина text, m - длина pattern.
     * Существуют более эффективные алгоритмы (КМП, Бойера-Мура), но они сложнее.
     *
     * @param text    Строка, в которой осуществляется поиск. Может быть null.
     * @param pattern Подстрока (шаблон), которую нужно найти. Может быть null.
     * @return Индекс первого символа первого вхождения {@code pattern} в {@code text}.
     * Возвращает 0, если {@code pattern} пуст (поведение как у {@code String.indexOf()}).
     * Возвращает -1, если {@code pattern} не найден, или если {@code text} или {@code pattern} равен null,
     * или если {@code pattern} длиннее {@code text}.
     */
    public int findSubstringIndex(String text, String pattern) {
        // 1. Обработка null аргументов
        if (text == null || pattern == null) {
            return -1;
        }

        int n = text.length();
        int m = pattern.length();

        // 2. Если паттерн пуст, он "находится" в начале любой строки
        if (m == 0) {
            return 0;
        }
        // 3. Если паттерн длиннее текста, он не может быть найден
        if (m > n) {
            return -1;
        }

        // 4. Итерация по возможным начальным позициям паттерна в тексте.
        //    Цикл идет от i=0 до n-m включительно.
        for (int i = 0; i <= n - m; i++) {
            // 5. Сравнение символов паттерна с подстрокой текста, начинающейся с i.
            int j = 0; // Индекс для паттерна
            // Сравниваем символы, пока не дойдем до конца паттерна (j < m)
            // и пока символы совпадают.
            while (j < m && text.charAt(i + j) == pattern.charAt(j)) {
                j++;
            }

            // 6. Проверка результата сравнения.
            //    Если j достиг длины паттерна (j == m), значит, все символы совпали.
            if (j == m) {
                return i; // Найдено вхождение, возвращаем начальный индекс i.
            }
            // Если цикл while прервался до того, как j достиг m, значит было несовпадение.
            // Продолжаем внешний цикл for для следующей стартовой позиции i.
        }

        // 7. Если внешний цикл завершен, а вхождение не найдено.
        return -1;
    }

    /**
     * Точка входа для демонстрации работы метода поиска подстроки.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task59_FindSubstringIndex sol = new Task59_FindSubstringIndex();
        String[][] testCases = {
                {"hello world, welcome to the world!", "world"}, // 6
                {"hello world", "o w"},                          // 4
                {"aaaaa", "bba"},                               // -1
                {"abc", ""},                                    // 0
                {"abc", "abc"},                                 // 0
                {"abc", "d"},                                   // -1
                {"mississippi", "issi"},                        // 1
                {"mississippi", "issip"},                       // 4
                {"abc", "abcd"},                                // -1
                {"ababab", "aba"},                              // 0
                {"ababab", "bab"},                              // 1
                {null, "a"},                                    // -1
                {"a", null},                                    // -1
                {"", ""}                                        // 0
        };
        int[] expected = {6, 4, -1, 0, 0, -1, 1, 4, -1, 0, 1, -1, -1, 0};

        for (int i = 0; i < testCases.length; i++) {
            String text = testCases[i][0];
            String pattern = testCases[i][1];
            int result = sol.findSubstringIndex(text, pattern);
            System.out.println("Index of '" + pattern + "' in '" + text + "': " + result + " (Expected: " + expected[i] + ")");
            if (result != expected[i]) System.err.println("   Mismatch!");
        }
    }
}
