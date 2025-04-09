package com.svedentsov.aqa.tasks.strings;

/**
 * Решение задачи №53: Проверка на подпоследовательность.
 */
public class Task53_IsSubsequence {

    /**
     * Проверяет, является ли строка {@code s} подпоследовательностью строки {@code t}.
     * Строка {@code s} является подпоследовательностью {@code t}, если {@code s} может
     * быть получена из {@code t} удалением нуля или более символов {@code t} без
     * изменения порядка оставшихся символов.
     * Использует метод двух указателей.
     * Сложность O(|t|) по времени, O(1) по памяти.
     *
     * @param s Строка, которая проверяется (потенциальная подпоследовательность). Может быть null.
     * @param t Строка, в которой ищется подпоследовательность {@code s}. Может быть null.
     * @return {@code true}, если {@code s} является подпоследовательностью {@code t},
     * {@code false} в противном случае (включая случаи, когда {@code s} или {@code t} равны null).
     */
    public boolean isSubsequence(String s, String t) {
        // Обработка null: если любая строка null, считаем не подпоследовательностью.
        if (s == null || t == null) {
            return false;
        }

        int sLen = s.length();
        int tLen = t.length();

        // Пустая строка s является подпоследовательностью любой строки t (включая пустую t).
        if (sLen == 0) {
            return true;
        }
        // Если t пуста, а s нет, то s не может быть подпоследовательностью.
        if (tLen == 0) {
            return false;
        }
        // Если s длиннее t, она не может быть подпоследовательностью.
        if (sLen > tLen) {
            return false;
        }


        int sPointer = 0; // Указатель для строки s (подпоследовательность)
        int tPointer = 0; // Указатель для строки t (основная строка)

        // Идем по основной строке t
        while (tPointer < tLen) {
            // Если символы на текущих позициях указателей совпадают
            if (s.charAt(sPointer) == t.charAt(tPointer)) {
                // Двигаем указатель подпоследовательности s
                sPointer++;
                // Если мы нашли все символы из s (sPointer достиг конца s),
                // то s является подпоследовательностью.
                if (sPointer == sLen) {
                    return true;
                }
            }
            // Всегда двигаем указатель основной строки t
            tPointer++;
        }

        // Если мы дошли до конца строки t, но не нашли все символы s (sPointer < sLen),
        // то s не является подпоследовательностью.
        // Это можно проверить и так: return sPointer == sLen;
        return false;
    }

    /**
     * Точка входа для демонстрации работы метода проверки подпоследовательности.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task53_IsSubsequence sol = new Task53_IsSubsequence();
        String[] s = {"ace", "axc", "", "abc", "b", "aaaaaa", null, "abc"};
        String[] t = {"abcde", "ahbgdc", "abcde", "", "abc", "bbaaaa", "abc", null};
        boolean[] expected = {true, false, true, false, true, false, false, false};

        for (int i = 0; i < s.length; i++) {
            boolean result = sol.isSubsequence(s[i], t[i]);
            System.out.println("isSubsequence('" + s[i] + "', '" + t[i] + "') -> " + result + " (Expected: " + expected[i] + ")");
            if (result != expected[i]) System.err.println("   Mismatch!");
        }
    }
}
