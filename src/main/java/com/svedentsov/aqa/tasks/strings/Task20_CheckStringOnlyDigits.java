package com.svedentsov.aqa.tasks.strings;

/**
 * Решение задачи №20: Проверить, содержит ли строка только цифры.
 */
public class Task20_CheckStringOnlyDigits {

    /**
     * Проверяет, состоит ли строка только из цифр ('0'-'9').
     * Использует итерацию по символам и {@link Character#isDigit(char)}.
     *
     * @param str Строка для проверки. Может быть null.
     * @return {@code true}, если строка не null, не пуста и содержит только цифры,
     * {@code false} в противном случае.
     */
    public boolean containsOnlyDigitsManual(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            // Character.isDigit() проверяет на цифры Unicode.
            // Для строгой проверки только на ASCII '0'-'9': if (c < '0' || c > '9') return false;
            if (!Character.isDigit(c)) {
                return false; // Найден нецифровой символ
            }
        }
        return true; // Все символы - цифры
    }

    /**
     * Проверяет, состоит ли строка только из цифр ('0'-'9').
     * Использует регулярное выражение {@code ^\d+$}.
     *
     * @param str Строка для проверки. Может быть null.
     * @return {@code true}, если строка не null, не пуста и соответствует регулярному выражению,
     * {@code false} в противном случае.
     */
    public boolean containsOnlyDigitsRegex(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        // ^     - начало строки
        // \d+   - одна или более цифр
        // $     - конец строки
        return str.matches("^\\d+$");
    }

    /**
     * Точка входа для демонстрации работы методов проверки строки на цифры.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task20_CheckStringOnlyDigits sol = new Task20_CheckStringOnlyDigits();
        String[] testStrings = {"12345", "12a45", "12 45", "-123", "", null, "0", "9876543210"};
        boolean[] expectedManual = {true, false, false, false, false, false, true, true};
        boolean[] expectedRegex = {true, false, false, false, false, false, true, true};

        System.out.println("--- Manual Check ---");
        for (int i = 0; i < testStrings.length; i++) {
            boolean result = sol.containsOnlyDigitsManual(testStrings[i]);
            System.out.println("'" + testStrings[i] + "' -> " + result + " (Expected: " + expectedManual[i] + ")");
            if (result != expectedManual[i]) System.err.println("   Mismatch!");
        }


        System.out.println("\n--- Regex Check ---");
        for (int i = 0; i < testStrings.length; i++) {
            boolean result = sol.containsOnlyDigitsRegex(testStrings[i]);
            System.out.println("'" + testStrings[i] + "' -> " + result + " (Expected: " + expectedRegex[i] + ")");
            if (result != expectedRegex[i]) System.err.println("   Mismatch!");
        }
    }
}
