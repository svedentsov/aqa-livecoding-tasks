package com.svedentsov.aqa.tasks.algorithms;

/**
 * Решение задачи №82: Счастливый билет.
 * Описание: Проверить, равен ли сумме первых N/2 цифр сумме последних N/2 цифр числа.
 * (Проверяет: работа со строками/числами, арифметика)
 * Задание: Напишите метод `boolean isLuckyTicket(String ticketNumber)`, который
 * проверяет, является ли строка `ticketNumber`, состоящая из четного количества
 * цифр (например, 6), "счастливым билетом" (сумма первой половины цифр равна
 * сумме второй половины).
 * Пример: `isLuckyTicket("123402")` -> `true` (1+2+3 = 6, 4+0+2 = 6).
 * `isLuckyTicket("123456")` -> `false`.
 */
public class LuckyTicket {

    /**
     * Проверяет, является ли номер билета "счастливым".
     * Счастливым считается билет, у которого сумма цифр первой половины номера
     * равна сумме цифр второй половины.
     * Требования к валидному номеру билета для проверки:
     * - Не null и не пустой.
     * - Содержит только цифры ('0'-'9').
     * - Имеет четную длину.
     *
     * @param ticketNumber Строка, представляющая номер билета.
     * @return {@code true}, если номер билета валиден и "счастливый",
     * {@code false} в противном случае (невалидный формат или суммы не равны).
     */
    public boolean isLuckyTicket(String ticketNumber) {
        // 1. Базовые проверки
        if (ticketNumber == null || ticketNumber.isEmpty()) {
            return false;
        }
        int n = ticketNumber.length();
        if (n % 2 != 0) { // Проверка на четную длину
            return false;
        }

        int halfLength = n / 2;
        int leftSum = 0;
        int rightSum = 0;

        // 2. Вычисление сумм с проверкой на цифры
        for (int i = 0; i < n; i++) {
            char c = ticketNumber.charAt(i);
            if (!Character.isDigit(c)) {
                return false; // Невалидный символ
            }
            int digitValue = c - '0'; // Преобразование char в int

            if (i < halfLength) {
                leftSum += digitValue; // Сумма первой половины
            } else {
                rightSum += digitValue; // Сумма второй половины
            }
        }

        // 3. Сравнение сумм
        return leftSum == rightSum;
    }
}
