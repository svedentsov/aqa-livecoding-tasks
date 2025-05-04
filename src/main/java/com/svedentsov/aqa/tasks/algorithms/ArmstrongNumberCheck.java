package com.svedentsov.aqa.tasks.algorithms;

/**
 * Решение задачи №34: Проверка на число Армстронга.
 * <p>
 * Описание: Проверить, является ли число числом Армстронга (сумма его цифр,
 * возведенных в степень количества цифр, равна самому числу).
 * (Проверяет: циклы, арифметика)
 * <p>
 * Задание: Напишите метод `boolean isArmstrongNumber(int number)`, который проверяет,
 * является ли неотрицательное целое число `number` числом Армстронга.
 * <p>
 * Пример: `isArmstrongNumber(153)` -> `true` (1^3 + 5^3 + 3^3 = 1 + 125 + 27 = 153).
 * `isArmstrongNumber(370)` -> `true`. `isArmstrongNumber(123)` -> `false`.
 */
public class ArmstrongNumberCheck {

    /**
     * Проверяет, является ли заданное неотрицательное целое число числом Армстронга.
     * Число Армстронга (или нарциссическое число) - это число, которое равно сумме
     * своих цифр, возведенных в степень, равную количеству цифр в этом числе.
     * Отрицательные числа не считаются числами Армстронга.
     * <p>
     * Использует тип long для промежуточных вычислений во избежание переполнения.
     *
     * @param number Неотрицательное целое число для проверки.
     * @return {@code true}, если число является числом Армстронга, {@code false} в противном случае.
     */
    public boolean isArmstrongNumber(int number) {
        // Шаг 1: Обработка отрицательных
        if (number < 0) {
            return false;
        }
        // Шаг 2: Обработка нуля (считается числом Армстронга)
        if (number == 0) {
            return true;
        }

        // Шаг 3: Определяем количество цифр
        String numStr = Integer.toString(number);
        int nDigits = numStr.length();

        // Шаг 4: Однозначные числа (0-9) всегда являются числами Армстронга
        if (nDigits == 1) {
            return true;
        }

        // Шаг 5 & 6: Инициализация суммы и временной переменной
        long sumOfPowers = 0L;
        int tempNumber = number;

        // Шаг 7: Цикл вычисления суммы степеней
        while (tempNumber > 0) {
            int digit = tempNumber % 10;

            // Шаг 7b: Вычисляем digit^nDigits с проверкой переполнения
            long power;
            try {
                power = checkedPower(digit, nDigits);
            } catch (ArithmeticException e) {
                // Если возведение в степень переполнило long, число точно не Армстронга
                return false;
            }

            // Шаг 7c & 7d: Проверяем переполнение суммы и добавляем
            if (sumOfPowers > Long.MAX_VALUE - power) {
                // Переполнение суммы, результат точно не будет равен number
                return false;
            }
            sumOfPowers += power;

            // Шаг 7e: Оптимизация - если сумма уже превысила число
            if (sumOfPowers > number) {
                return false;
            }

            // Шаг 7f: Переходим к следующей цифре
            tempNumber /= 10;
        }

        // Шаг 8: Сравнение суммы с исходным числом
        return sumOfPowers == number;
    }

    /**
     * Вспомогательный метод для возведения неотрицательного числа base
     * в неотрицательную степень exp с проверкой на переполнение long.
     * Использует метод возведения в степень путем двоичного разложения показателя степени.
     *
     * @param base Основание (>= 0).
     * @param exp  Степень (>= 0).
     * @return base в степени exp.
     * @throws ArithmeticException если результат переполняет long.
     */
    private long checkedPower(int base, int exp) throws ArithmeticException {
        if (base == 0) return (exp == 0) ? 1L : 0L; // 0^0=1, 0^positive=0
        if (base == 1 || exp == 0) return 1L; // 1^exp=1, base^0=1
        if (exp == 1) return base; // base^1=base

        long result = 1L;
        long longBase = base; // Используем long, чтобы избежать переполнения base*base

        while (exp > 0) {
            // Если текущий бит степени установлен (нечетная степень)
            if ((exp & 1) == 1) {
                // result *= longBase; // без проверки
                if (result > Long.MAX_VALUE / longBase) {
                    throw new ArithmeticException("Overflow calculating power: result * base");
                }
                result *= longBase;
            }
            exp >>= 1; // Сдвигаем биты степени вправо (эквивалентно exp / 2)
            // Если еще есть биты для обработки, возводим базу в квадрат
            if (exp > 0) {
                // longBase *= longBase; // без проверки
                if (longBase > Math.sqrt(Long.MAX_VALUE)) { // Оптимизированная проверка перед возведением в квадрат
                    // Если longBase уже > sqrt(MAX_VALUE), то longBase*longBase точно переполнит
                    // Math.sqrt(Long.MAX_VALUE) примерно 3.037E9
                    if (exp > 0) { // Если еще остались итерации, значит точно будет переполнение
                        throw new ArithmeticException("Overflow calculating power: base squaring");
                    }
                }
                longBase *= longBase; // Здесь тоже может быть переполнение, но проверка выше его ловит
            }
        }
        return result;
    }
}
