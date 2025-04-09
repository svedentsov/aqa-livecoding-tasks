package com.svedentsov.aqa.tasks.algorithms;

/**
 * Решение задачи №82: Счастливый билет.
 */
public class Task82_LuckyTicket {

    /**
     * Проверяет, является ли номер билета "счастливым".
     * Счастливым считается билет, у которого сумма цифр первой половины номера
     * равна сумме цифр второй половины.
     * Номер билета должен состоять из четного количества цифр.
     *
     * @param ticketNumber Строка, представляющая номер билета. Предполагается,
     *                     что она может содержать только цифры, но метод выполняет проверку.
     *                     Может быть null.
     * @return {@code true}, если номер билета валиден (не null, не пуст, четная длина,
     * только цифры) и является "счастливым", {@code false} в противном случае.
     */
    public boolean isLuckyTicket(String ticketNumber) {
        // 1. Проверка на null, пустую строку
        if (ticketNumber == null || ticketNumber.isEmpty()) {
            // System.err.println("Ticket number is null or empty.");
            return false;
        }
        // 2. Проверка на четную длину
        int n = ticketNumber.length();
        if (n % 2 != 0) {
            // System.err.println("Ticket number has odd length: " + n);
            return false;
        }

        int halfLength = n / 2;
        int leftSum = 0;
        int rightSum = 0;

        // 3. Итерация по строке, проверка на цифры и вычисление сумм
        for (int i = 0; i < n; i++) {
            char c = ticketNumber.charAt(i);
            // Проверка, является ли символ цифрой
            if (!Character.isDigit(c)) {
                // System.err.println("Ticket number contains non-digit character: '" + c + "'");
                return false; // Невалидный символ
            }
            // Преобразование символа в числовое значение цифры
            int digitValue = c - '0'; // Эффективнее, чем Character.getNumericValue(c) для ASCII цифр

            // Добавление цифры к соответствующей сумме
            if (i < halfLength) {
                // Первая половина (индексы от 0 до halfLength - 1)
                leftSum += digitValue;
            } else {
                // Вторая половина (индексы от halfLength до n - 1)
                rightSum += digitValue;
            }
        }

        // 4. Сравнение сумм первой и второй половин
        return leftSum == rightSum;
    }

    /**
     * Точка входа для демонстрации работы метода проверки счастливого билета.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task82_LuckyTicket sol = new Task82_LuckyTicket();
        String[] tickets = {
                "123402", // true (6 == 6)
                "123456", // false (6 != 15)
                "000000", // true (0 == 0)
                "11",     // true (1 == 1)
                "1010",   // true (1 == 1)
                "55",     // true (5 == 5)
                "987888", // false (24 != 24) - нет, 24 == 24 -> true
                "123",    // false (нечетная длина)
                "1a3402", // false (не цифра)
                "12 34",  // false (не цифра - пробел)
                "",       // false (пустой)
                null      // false
        };
        boolean[] expected = {true, false, true, true, true, true, true, false, false, false, false, false};


        for (int i = 0; i < tickets.length; i++) {
            boolean result = sol.isLuckyTicket(tickets[i]);
            System.out.println("Ticket '" + tickets[i] + "' is lucky: " + result + " (Expected: " + expected[i] + ")");
            if (result != expected[i]) System.err.println("   Mismatch!");
        }
    }
}
