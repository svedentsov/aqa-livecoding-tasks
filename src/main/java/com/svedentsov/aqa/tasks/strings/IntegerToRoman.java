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
}
