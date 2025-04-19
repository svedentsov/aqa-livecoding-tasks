package com.svedentsov.aqa.tasks.algorithms;

/**
 * Решение задачи №82: Счастливый билет.
 * <p>
 * Описание: Проверить, равен ли сумме первых N/2 цифр сумме последних N/2 цифр числа.
 * (Проверяет: работа со строками/числами, арифметика)
 * <p>
 * Задание: Напишите метод `boolean isLuckyTicket(String ticketNumber)`, который
 * проверяет, является ли строка `ticketNumber`, состоящая из четного количества
 * цифр (например, 6), "счастливым билетом" (сумма первой половины цифр равна
 * сумме второй половины).
 * <p>
 * Пример: `isLuckyTicket("123402")` -> `true` (1+2+3 = 6, 4+0+2 = 6).
 * `isLuckyTicket("123456")` -> `false`.
 */
public class LuckyTicket {

    /**
     * Проверяет, является ли номер билета "счастливым".
     * Счастливым считается билет, у которого сумма цифр первой половины номера
     * равна сумме цифр второй половины.
     * <p>
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

    /**
     * Точка входа для демонстрации работы метода проверки счастливого билета.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        LuckyTicket sol = new LuckyTicket();
        String[] tickets = {
                "123402", // true (6 == 6)
                "123456", // false (6 != 15)
                "000000", // true (0 == 0)
                "11",     // true (1 == 1)
                "1010",   // true (1 == 1)
                "55",     // true (5 == 5)
                "987888", // true (24 == 24)
                "123",    // false (odd length)
                "1a3402", // false (non-digit)
                "12 34",  // false (non-digit)
                "",       // false (empty)
                null      // false (null)
        };

        System.out.println("--- Lucky Ticket Check ---");
        for (String ticket : tickets) {
            runLuckyTicketTest(sol, ticket);
        }
    }

    /**
     * Вспомогательный метод для тестирования isLuckyTicket.
     *
     * @param sol    Экземпляр решателя.
     * @param ticket Номер билета для проверки.
     */
    private static void runLuckyTicketTest(LuckyTicket sol, String ticket) {
        String input = (ticket == null ? "null" : "'" + ticket + "'");
        try {
            boolean result = sol.isLuckyTicket(ticket);
            System.out.println("isLuckyTicket(" + input + ") -> " + result);
            // Можно добавить сравнение с ожидаемым результатом, если он передан
        } catch (Exception e) {
            System.err.println("Error processing " + input + ": " + e.getMessage());
        }
    }
}
