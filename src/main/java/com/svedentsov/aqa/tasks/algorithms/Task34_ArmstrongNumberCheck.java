package com.svedentsov.aqa.tasks.algorithms;

/**
 * Решение задачи №34: Проверка на число Армстронга.
 */
public class Task34_ArmstrongNumberCheck {

    /**
     * Проверяет, является ли заданное неотрицательное целое число числом Армстронга.
     * Число Армстронга (или нарциссическое число) - это число, которое равно сумме
     * своих цифр, возведенных в степень, равную количеству цифр в этом числе.
     * Например, 153 (3 цифры) = 1^3 + 5^3 + 3^3 = 1 + 125 + 27 = 153.
     * 1634 (4 цифры) = 1^4 + 6^4 + 3^4 + 4^4 = 1 + 1296 + 81 + 256 = 1634.
     * Использует тип long для промежуточных вычислений во избежание переполнения.
     *
     * @param number Неотрицательное целое число для проверки.
     * @return {@code true}, если число является числом Армстронга, {@code false} в противном случае.
     */
    public boolean isArmstrongNumber(int number) {
        // Обработка отрицательных чисел (они не считаются числами Армстронга)
        if (number < 0) {
            return false;
        }
        // 0 считается числом Армстронга (0^1 = 0)
        if (number == 0) return true;


        // 1. Определяем количество цифр (степень)
        // Можно через логарифм: int n = (int) Math.floor(Math.log10(number)) + 1;
        // Но преобразование в строку надежнее для 0 и проще.
        String numStr = Integer.toString(number);
        int n = numStr.length();

        // Если n=1, любое однозначное число является числом Армстронга (digit^1 = digit)
        if (n == 1) return true;

        long sumOfPowers = 0; // Используем long для суммы
        int temp = number; // Временная переменная для извлечения цифр

        // 2. Вычисляем сумму степеней цифр
        while (temp > 0) {
            int digit = temp % 10;

            // Вычисляем digit^n. Используем свой метод или Math.pow.
            // Math.pow возвращает double, что может привести к неточностям.
            // Свой метод безопаснее от неточностей, но нужно следить за переполнением long.
            long power;
            try {
                power = checkedPower(digit, n);
            } catch (ArithmeticException e) {
                // Если возведение в степень переполнило long, число точно не Армстронга
                return false;
            }


            // Проверяем на переполнение суммы ДО сложения
            if (Long.MAX_VALUE - power < sumOfPowers) {
                // Переполнение суммы, число не может быть равно сумме
                return false;
            }
            sumOfPowers += power;

            // Оптимизация: если промежуточная сумма уже больше исходного числа,
            // дальнейшие вычисления бессмысленны.
            if (sumOfPowers > number) {
                return false;
            }

            temp /= 10; // Переходим к следующей цифре
        }

        // 3. Сравниваем вычисленную сумму с исходным числом
        return sumOfPowers == number;
    }

    /**
     * Вспомогательный метод для возведения неотрицательного числа base
     * в неотрицательную степень exp с проверкой на переполнение long.
     *
     * @param base Основание (>= 0).
     * @param exp  Степень (>= 0).
     * @return base в степени exp.
     * @throws ArithmeticException если результат переполняет long.
     */
    private long checkedPower(int base, int exp) throws ArithmeticException {
        long result = 1L;
        // Упрощение для 0 и 1
        if (base == 0) return (exp == 0) ? 1L : 0L; // 0^0 = 1, 0^exp = 0 for exp > 0
        if (base == 1) return 1L;

        for (int i = 0; i < exp; i++) {
            // Проверка на переполнение перед умножением
            if (result > Long.MAX_VALUE / base) {
                throw new ArithmeticException("Overflow calculating power " + base + "^" + exp);
            }
            result *= base;
        }
        return result;
    }

    /**
     * Точка входа для демонстрации работы метода проверки числа Армстронга.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task34_ArmstrongNumberCheck sol = new Task34_ArmstrongNumberCheck();
        int[] testNumbers = {153, 370, 371, 1634, 9474, 1, 0, 9, 123, 10, 9475, -153};
        boolean[] expected = {true, true, true, true, true, true, true, true, false, false, false, false};

        for (int i = 0; i < testNumbers.length; i++) {
            boolean result = sol.isArmstrongNumber(testNumbers[i]);
            System.out.println("isArmstrongNumber(" + testNumbers[i] + "): " + result + " (Expected: " + expected[i] + ")");
            if (result != expected[i]) System.err.println("   Mismatch!");
        }

        // Тест на возможное переполнение (зависит от реализации checkedPower)
        // 9^10 > Integer.MAX_VALUE, но < Long.MAX_VALUE
        // 3^39 > Long.MAX_VALUE
        System.out.println("isArmstrongNumber(999999999): " + sol.isArmstrongNumber(999999999)); // false (сумма будет большой, но не равна числу)
        System.out.println("isArmstrongNumber(Integer.MAX_VALUE): " + sol.isArmstrongNumber(Integer.MAX_VALUE)); // false
    }
}
