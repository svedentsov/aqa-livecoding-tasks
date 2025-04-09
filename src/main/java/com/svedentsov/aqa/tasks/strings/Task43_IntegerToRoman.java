package com.svedentsov.aqa.tasks.strings;

/**
 * Решение задачи №43: Конвертация целых чисел в римские.
 */
public class Task43_IntegerToRoman {

    // Массивы для "жадного" преобразования. Значения идут по убыванию.
    // Важно включить "вычитательные" комбинации (CM, CD, XC, XL, IX, IV).
    private static final int[] VALUES = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] SYMBOLS = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    /**
     * Конвертирует целое число (в диапазоне от 1 до 3999) в его римское представление.
     * Использует "жадный" подход: итеративно вычитает из числа максимально возможное
     * значение из предопределенного списка `VALUES` и добавляет соответствующий символ
     * из `SYMBOLS` к результату.
     *
     * @param num Целое число от 1 до 3999.
     * @return Строка с римским представлением числа.
     * @throws IllegalArgumentException если число находится вне допустимого диапазона [1, 3999].
     */
    public String intToRoman(int num) {
        // 1. Проверка входного числа на допустимый диапазон
        if (num < 1 || num > 3999) {
            throw new IllegalArgumentException("Input number " + num + " must be between 1 and 3999.");
        }

        StringBuilder romanResult = new StringBuilder();
        // 2. Итерация по массиву значений (от большего к меньшему)
        for (int i = 0; i < VALUES.length; i++) {
            // 3. Пока текущее значение VALUES[i] можно вычесть из остатка числа num
            while (num >= VALUES[i]) {
                num -= VALUES[i];       // Вычитаем значение
                romanResult.append(SYMBOLS[i]); // Добавляем соответствующий римский символ
            }
            // Оптимизация: если число стало 0, можно выйти из цикла досрочно
            if (num == 0) {
                break;
            }
        }

        return romanResult.toString();
    }

    /**
     * Точка входа для демонстрации работы метода конвертации целых чисел в римские.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task43_IntegerToRoman sol = new Task43_IntegerToRoman();
        int[] nums = {3, 58, 1994, 9, 4, 40, 90, 400, 900, 1, 2025, 3999};
        String[] expectedRomans = {"III", "LVIII", "MCMXCIV", "IX", "IV", "XL", "XC", "CD", "CM", "I", "MMXXV", "MMMCMXCIX"};

        for (int i = 0; i < nums.length; i++) {
            try {
                String result = sol.intToRoman(nums[i]);
                System.out.println(nums[i] + " -> '" + result + "' (Expected: '" + expectedRomans[i] + "')");
                if (!result.equals(expectedRomans[i])) {
                    System.err.println("   Mismatch!");
                }
            } catch (IllegalArgumentException e) {
                System.err.println("Error processing " + nums[i] + ": " + e.getMessage());
            }
        }

        // Пример с невалидным входом
        try {
            System.out.print("\nProcessing 0: ");
            sol.intToRoman(0);
        } catch (IllegalArgumentException e) {
            System.err.println("Caught expected error: " + e.getMessage());
        }
        try {
            System.out.print("Processing 4000: ");
            sol.intToRoman(4000);
        } catch (IllegalArgumentException e) {
            System.err.println("Caught expected error: " + e.getMessage());
        }
    }
}
