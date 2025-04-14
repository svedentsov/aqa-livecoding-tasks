package com.svedentsov.aqa.tasks.strings;

/**
 * Решение задачи №43: Конвертация целых чисел в римские.
 * <p>
 * Описание: Написать функцию для обратного преобразования.
 * (Проверяет: арифметика, циклы, условия, Map/массивы)
 * <p>
 * Задание: Напишите метод `String intToRoman(int num)`, который конвертирует
 * целое число `num` (от 1 до 3999) в строку с римским представлением.
 * <p>
 * Пример: `intToRoman(3)` -> `"III"`. `intToRoman(58)` -> `"LVIII"`.
 * `intToRoman(1994)` -> `"MCMXCIV"`.
 */
public class IntegerToRoman {

    // Параллельные массивы для "жадного" преобразования.
    // Значения идут по убыванию, включая "вычитательные" комбинации.
    private static final int[] VALUES = {
            1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1
    };
    private static final String[] SYMBOLS = {
            "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"
    };

    /**
     * Конвертирует целое число (в диапазоне от 1 до 3999) в его римское представление.
     * Использует "жадный" подход: итеративно вычитает из числа максимально возможное
     * значение из предопределенного списка `VALUES` и добавляет соответствующий символ
     * из `SYMBOLS` к результату.
     * <p>
     * Алгоритм:
     * 1. Проверить, что число `num` находится в диапазоне [1, 3999].
     * 2. Инициализировать пустой StringBuilder `romanResult`.
     * 3. Итерировать по массивам `VALUES` и `SYMBOLS` одновременно (индекс `i`).
     * 4. Внутренний цикл `while`: пока `num >= VALUES[i]`:
     * a. Вычесть `VALUES[i]` из `num`.
     * b. Добавить `SYMBOLS[i]` к `romanResult`.
     * 5. (Опционально) Если `num` стало 0, можно прервать внешний цикл.
     * 6. Вернуть `romanResult.toString()`.
     *
     * @param num Целое число от 1 до 3999.
     * @return Строка с римским представлением числа.
     * @throws IllegalArgumentException если число находится вне допустимого диапазона [1, 3999].
     */
    public String intToRoman(int num) {
        // Шаг 1: Валидация входа
        if (num < 1 || num > 3999) {
            throw new IllegalArgumentException("Input number " + num + " must be between 1 and 3999.");
        }

        // Шаг 2: Инициализация результата
        StringBuilder romanResult = new StringBuilder();

        // Шаг 3 & 4: Итерация и "жадное" вычитание
        for (int i = 0; i < VALUES.length; i++) {
            // Пока текущее значение можно вычесть из остатка числа
            while (num >= VALUES[i]) {
                num -= VALUES[i]; // Вычитаем
                romanResult.append(SYMBOLS[i]); // Добавляем символ
            }
            // Шаг 5: Оптимизация (если число стало 0)
            if (num == 0) {
                break;
            }
        }

        // Шаг 6: Возвращаем результат
        return romanResult.toString();
    }

    /**
     * Точка входа для демонстрации работы метода конвертации целых чисел в римские.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        IntegerToRoman sol = new IntegerToRoman();
        int[] nums = {3, 58, 1994, 9, 4, 40, 90, 400, 900, 1, 2025, 3999, 49, 888, 1515};

        System.out.println("--- Converting Integer to Roman ---");
        for (int num : nums) {
            runIntToRomanTest(sol, num);
        }

        // Тесты на ошибки
        runIntToRomanTest(sol, 0);    // Exception
        runIntToRomanTest(sol, 4000); // Exception
        runIntToRomanTest(sol, -10);  // Exception
    }

    /**
     * Вспомогательный метод для тестирования intToRoman.
     *
     * @param sol Экземпляр решателя.
     * @param num Число для конвертации.
     */
    private static void runIntToRomanTest(IntegerToRoman sol, int num) {
        System.out.print("intToRoman(" + num + ") -> ");
        try {
            String roman = sol.intToRoman(num);
            System.out.println("'" + roman + "'");
            // Можно добавить сравнение с ожидаемым значением, если оно передано
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Caught unexpected error: " + e.getMessage());
        }
    }
}
