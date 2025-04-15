package com.svedentsov.aqa.tasks.strings;

/**
 * Решение задачи №53: Проверка на подпоследовательность.
 * <p>
 * Описание: Проверить, является ли одна строка подпоследовательностью другой
 * (символы идут в том же порядке, но не обязательно подряд).
 * (Проверяет: работа со строками, два указателя)
 * <p>
 * Задание: Напишите метод `boolean isSubsequence(String s, String t)`, который
 * возвращает `true`, если строка `s` является подпоследовательностью строки `t`
 * (т.е. `s` можно получить из `t` удалением некоторых символов без изменения
 * порядка оставшихся), и `false` иначе.
 * <p>
 * Пример: `isSubsequence("ace", "abcde")` -> `true`.
 * `isSubsequence("axc", "ahbgdc")` -> `false`.
 */
public class IsSubsequence {

    /**
     * Проверяет, является ли строка {@code s} подпоследовательностью строки {@code t}.
     * Использует метод двух указателей.
     * <p>
     * Сложность: O(|t|) по времени, O(1) по памяти.
     * <p>
     * Алгоритм:
     * 1. Обработать null: если `s` или `t` null, вернуть false.
     * 2. Обработать пустую `s`: пустая строка всегда подпоследовательность, вернуть true.
     * 3. Инициализировать два указателя: `sPointer = 0` для `s`, `tPointer = 0` для `t`.
     * 4. В цикле `while (tPointer < t.length())`:
     * a. Если `sPointer == s.length()`, значит все символы `s` найдены, вернуть true.
     * b. Если `s.charAt(sPointer) == t.charAt(tPointer)`, значит найден очередной символ
     * подпоследовательности, инкрементировать `sPointer`.
     * c. Всегда инкрементировать `tPointer`, чтобы двигаться по основной строке `t`.
     * 5. Если цикл завершился, а `sPointer` не достиг конца `s`, вернуть false.
     *
     * @param s Строка, которая проверяется (потенциальная подпоследовательность). Может быть null.
     * @param t Строка, в которой ищется подпоследовательность {@code s}. Может быть null.
     * @return {@code true}, если {@code s} является подпоследовательностью {@code t},
     * {@code false} в противном случае.
     */
    public boolean isSubsequence(String s, String t) {
        // Шаг 1: Обработка null
        if (s == null || t == null) {
            return false;
        }

        int sLen = s.length();
        int tLen = t.length();

        // Шаг 2: Обработка пустой строки s
        if (sLen == 0) {
            return true;
        }
        // Если t пуста (и s не пуста), то не подпоследовательность
        if (tLen == 0) {
            return false;
        }
        // Если s длиннее t, не может быть подпоследовательностью
        if (sLen > tLen) {
            return false;
        }

        // Шаг 3: Инициализация указателей
        int sPointer = 0;
        int tPointer = 0;

        // Шаг 4: Итерация по строке t
        while (tPointer < tLen) {
            // Шаг 4b: Если символы совпадают, двигаем указатель s
            if (s.charAt(sPointer) == t.charAt(tPointer)) {
                sPointer++;
                // Шаг 4a: Если все символы s найдены, выходим
                if (sPointer == sLen) {
                    return true;
                }
            }
            // Шаг 4c: Всегда двигаем указатель t
            tPointer++;
        }

        // Шаг 5: Если дошли до конца t, но не нашли все символы s
        // Это равносильно проверке return sPointer == sLen; в конце
        return sPointer == sLen;
    }

    /**
     * Точка входа для демонстрации работы метода проверки подпоследовательности.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        IsSubsequence sol = new IsSubsequence();
        // Тестовые случаи: s, t, expected
        Object[][] testCases = {
                {"ace", "abcde", true},        // true
                {"axc", "ahbgdc", false},      // false
                {"", "abcde", true},           // true (пустая s)
                {"abc", "", false},            // false (пустая t)
                {"b", "abc", true},            // true
                {"aaaaaa", "bbaaaa", false},   // false
                {"abc", "abc", true},          // true (равные строки)
                {"z", "abc", false},           // false
                {"", "", true},                // true (обе пустые)
                {null, "abc", false},          // false (s=null)
                {"abc", null, false},          // false (t=null)
                {null, null, false},           // false (оба null)
                {"too long", "short", false}   // false (s длиннее t)
        };

        System.out.println("--- Checking for Subsequence ---");
        for (Object[] testCase : testCases) {
            runSubsequenceTest(sol, (String) testCase[0], (String) testCase[1], (boolean) testCase[2]);
        }
    }

    /**
     * Вспомогательный метод для тестирования isSubsequence.
     *
     * @param sol      Экземпляр решателя.
     * @param s        Потенциальная подпоследовательность.
     * @param t        Основная строка.
     * @param expected Ожидаемый результат.
     */
    private static void runSubsequenceTest(IsSubsequence sol, String s, String t, boolean expected) {
        String sStr = (s == null ? "null" : "'" + s + "'");
        String tStr = (t == null ? "null" : "'" + t + "'");
        try {
            boolean result = sol.isSubsequence(s, t);
            System.out.printf("isSubsequence(%s, %s) -> %-5b (Expected: %-5b) %s%n",
                    sStr, tStr, result, expected, (result == expected ? "" : "<- MISMATCH!"));
        } catch (Exception e) {
            System.err.printf("Error processing isSubsequence(%s, %s): %s%n", sStr, tStr, e.getMessage());
        }
    }
}
