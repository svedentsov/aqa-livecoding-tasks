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
     * <p>
     * Алгоритм:
     * 1. Обработать отрицательные числа (не являются числами Армстронга).
     * 2. Обработать 0 (считается числом Армстронга).
     * 3. Определить количество цифр `n` в числе.
     * 4. Обработать однозначные числа (всегда являются числами Армстронга).
     * 5. Инициализировать `sumOfPowers = 0`.
     * 6. Использовать временную копию числа `temp = number`.
     * 7. В цикле, пока `temp > 0`:
     * a. Получить последнюю цифру `digit = temp % 10`.
     * b. Вычислить `digit^n` (с проверкой на переполнение `long`).
     * c. Проверить на переполнение суммы `sumOfPowers` перед добавлением `digit^n`.
     * d. Добавить `digit^n` к `sumOfPowers`.
     * e. Оптимизация: если `sumOfPowers > number`, выйти из цикла (результат false).
     * f. Удалить последнюю цифру из `temp` (`temp /= 10`).
     * 8. Сравнить `sumOfPowers` с исходным `number`. Если равны, то это число Армстронга.
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
        // Шаг 2: Обработка нуля
        if (number == 0) {
            return true;
        }

        // Шаг 3: Определяем количество цифр
        String numStr = Integer.toString(number);
        int nDigits = numStr.length();

        // Шаг 4: Обработка однозначных чисел
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

            // Шаг 7c & 7d: Проверяем переполнение и добавляем к сумме
            if (sumOfPowers > Long.MAX_VALUE - power) {
                // Переполнение суммы, результат точно не будет равен number
                return false;
            }
            sumOfPowers += power;

            // Шаг 7e: Оптимизация - если сумма уже превысила число
            // (Важно: сравниваем с исходным 'number', а не с 'tempNumber')
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
     * Точка входа для демонстрации работы метода проверки числа Армстронга.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        ArmstrongNumberCheck sol = new ArmstrongNumberCheck();
        int[] testNumbers = {
                153, 370, 371, 1634, 9474, 54748, // true
                1, 0, 9, // true (однозначные)
                123, 10, 9475, 1635, // false
                -153, -1 // false (отрицательные)
        };

        System.out.println("--- Checking for Armstrong Numbers ---");
        for (int num : testNumbers) {
            runArmstrongTest(sol, num);
        }

        // Тест на большое число (не Армстронга, но для проверки на переполнение)
        runArmstrongTest(sol, Integer.MAX_VALUE); // false
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
        // Базовые случаи для 0 и 1 для оптимизации и корректности (0^0 = 1)
        if (base == 0) return (exp == 0) ? 1L : 0L;
        if (base == 1) return 1L;
        if (exp == 0) return 1L;
        if (exp == 1) return base;

        long result = 1L;
        long longBase = base; // Используем long для base во избежание проблем с base*base

        while (exp > 0) {
            // Если степень нечетная, умножаем результат на текущее значение base
            if ((exp % 2) == 1) {
                // Проверка переполнения перед умножением result * longBase
                if (result > Long.MAX_VALUE / longBase) {
                    throw new ArithmeticException("Overflow calculating power");
                }
                result *= longBase;
            }
            exp /= 2; // Уменьшаем степень вдвое
            // Если степень еще > 0, возводим base в квадрат для следующей итерации
            if (exp > 0) {
                // Проверка переполнения перед умножением longBase * longBase
                if (longBase > Long.MAX_VALUE / longBase) {
                    throw new ArithmeticException("Overflow calculating power (base squaring)");
                }
                longBase *= longBase;
            }
        }
        return result;
    }

    /**
     * Вспомогательный метод для тестирования isArmstrongNumber.
     *
     * @param sol    Экземпляр решателя.
     * @param number Число для проверки.
     */
    private static void runArmstrongTest(ArmstrongNumberCheck sol, int number) {
        System.out.print("isArmstrongNumber(" + number + "): ");
        try {
            boolean result = sol.isArmstrongNumber(number);
            System.out.println(result);
            // Здесь можно добавить сравнение с ожидаемым значением, если оно известно
        } catch (Exception e) { // Ловим все исключения на всякий случай
            System.out.println("Error - " + e.getMessage());
        }
    }
}
