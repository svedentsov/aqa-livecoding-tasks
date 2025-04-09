package com.svedentsov.aqa.tasks.files_io_formats; // или algorithms

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Objects;

/**
 * Решение задачи №78: Работа с датами и временем (используя java.time API).
 * Демонстрирует парсинг дат из строк, вычисление разницы между датами и форматирование дат в строки.
 */
public class Task78_DateTimeManipulation {

    // Определяем форматтер для стандартного формата ISO (yyyy-MM-dd) как константу
    // Это рекомендуется для часто используемых форматтеров.
    private static final DateTimeFormatter ISO_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE; // yyyy-MM-dd

    /**
     * Вычисляет количество полных дней между двумя датами, заданными в строках
     * формата "yyyy-MM-dd".
     * Использует современный {@link LocalDate} и {@link ChronoUnit}.
     *
     * @param dateStr1 Первая дата в формате "yyyy-MM-dd".
     * @param dateStr2 Вторая дата в формате "yyyy-MM-dd".
     * @return Абсолютное количество дней между датами.
     * @throws IllegalArgumentException если одна из строк null или имеет неверный формат.
     * @throws DateTimeParseException   если строка имеет верный формат, но невалидную дату (например, "2023-02-30").
     */
    public long daysBetween(String dateStr1, String dateStr2) {
        // Проверка входных аргументов на null
        Objects.requireNonNull(dateStr1, "Date string 1 cannot be null.");
        Objects.requireNonNull(dateStr2, "Date string 2 cannot be null.");

        try {
            // 1. Парсим строки в объекты LocalDate с использованием стандартного форматтера
            LocalDate date1 = LocalDate.parse(dateStr1, ISO_DATE_FORMATTER);
            LocalDate date2 = LocalDate.parse(dateStr2, ISO_DATE_FORMATTER);

            // 2. Вычисляем количество дней между датами с помощью ChronoUnit.DAYS.between.
            //    Метод between(startInclusive, endExclusive) считает полные единицы между датами.
            //    Результат может быть отрицательным, если date1 > date2, поэтому берем модуль.
            return Math.abs(ChronoUnit.DAYS.between(date1, date2));
        } catch (DateTimeParseException e) {
            // Перехватываем ошибку парсинга (неверный формат или невалидная дата)
            // и выбрасываем более описательное исключение.
            throw new IllegalArgumentException("Invalid date format or value (expected yyyy-MM-dd): " + e.getMessage(), e);
        }
    }

    /**
     * Форматирует заданную дату {@link LocalDate} в строку с использованием указанного шаблона.
     *
     * @param date    Дата для форматирования (не null).
     * @param pattern Шаблон формата (например, "dd/MM/yyyy", "dd MMMM yyyy", "E, dd MMM yy").
     *                Не должен быть null или пустым.
     * @return Отформатированная строка с датой.
     * @throws IllegalArgumentException если шаблон формата {@code pattern} некорректен.
     * @throws NullPointerException     если {@code date} или {@code pattern} равны null.
     */
    public String formatDate(LocalDate date, String pattern) {
        Objects.requireNonNull(date, "Date cannot be null");
        Objects.requireNonNull(pattern, "Pattern cannot be null");
        if (pattern.isEmpty()) {
            throw new IllegalArgumentException("Pattern cannot be empty.");
        }
        try {
            // Создаем форматтер на основе заданного пользователем шаблона
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            // Форматируем дату с использованием созданного форматтера
            return date.format(formatter);
        } catch (IllegalArgumentException e) {
            // Перехватываем ошибку невалидного шаблона
            throw new IllegalArgumentException("Invalid date format pattern: '" + pattern + "'", e);
        }
    }

    /**
     * Возвращает название дня недели для заданной даты на русском языке.
     *
     * @param date Дата (не null).
     * @return Полное название дня недели на русском (например, "Понедельник").
     * @throws NullPointerException если {@code date} равно null.
     */
    public String getDayOfWeekNameRussian(LocalDate date) {
        Objects.requireNonNull(date, "Date cannot be null");
        // Указываем русскую локаль для получения названий на русском языке
        Locale russianLocale = Locale.of("ru", "RU"); // Можно просто new Locale("ru")
        // Получаем день недели (DayOfWeek enum), затем его полное текстовое представление для локали
        return date.getDayOfWeek().getDisplayName(TextStyle.FULL, russianLocale);
    }

    /**
     * Точка входа для демонстрации работы методов работы с датами.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task78_DateTimeManipulation sol = new Task78_DateTimeManipulation();

        System.out.println("--- Вычисление разницы в днях ---");
        try {
            String d1 = "2024-04-08"; // Примерная текущая дата
            String d2 = "2024-05-01"; // Следующий месяц
            System.out.printf("Дней между %s и %s: %d\n", d1, d2, sol.daysBetween(d1, d2)); // 23

            String d3 = "2025-01-01"; // Следующий год
            String d4 = "2024-01-01";
            System.out.printf("Дней между %s и %s: %d\n", d3, d4, sol.daysBetween(d3, d4)); // 366 (2024 високосный)

            String d5 = "2023-03-10";
            String d6 = "2023-02-25";
            System.out.printf("Дней между %s и %s: %d\n", d5, d6, sol.daysBetween(d5, d6)); // 13

        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка вычисления разницы: " + e.getMessage());
        }

        System.out.println("\n--- Форматирование дат ---");
        LocalDate today = LocalDate.now(); // Берем текущую дату системы
        LocalDate futureDate = LocalDate.of(2025, 12, 31);
        try {
            System.out.println("Текущая дата (стандарт): " + today); // yyyy-MM-dd
            System.out.println("Текущая дата (dd/MM/yyyy): " + sol.formatDate(today, "dd/MM/yyyy"));
            // Для русского названия месяца нужна локаль в форматтере, но можно и так:
            System.out.println("Текущая дата (dd MMMM yyyy): " + sol.formatDate(today, "dd MMMM yyyy"));
            System.out.println("Дата (" + futureDate + ") (E, dd MMM yy): " + sol.formatDate(futureDate, "E, dd MMM yy")); // E - день недели сокр.

        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка форматирования: " + e.getMessage());
        }

        System.out.println("\n--- Название дня недели ---");
        System.out.println("День недели для " + today + ": " + sol.getDayOfWeekNameRussian(today));
        System.out.println("День недели для " + futureDate + ": " + sol.getDayOfWeekNameRussian(futureDate));


        // --- Примеры ошибок ---
        System.out.println("\n--- Обработка ошибок ---");
        try {
            sol.daysBetween("2023-02-30", "2023-03-01"); // Невалидная дата
        } catch (IllegalArgumentException e) {
            System.err.println("Ожидаемая ошибка (невалидная дата): " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
        try {
            sol.daysBetween("03/04/2024", "2024-04-05"); // Неверный формат
        } catch (IllegalArgumentException e) {
            System.err.println("Ожидаемая ошибка (неверный формат): " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
        try {
            sol.formatDate(today, "невалидный-паттерн"); // Невалидный шаблон
        } catch (IllegalArgumentException e) {
            System.err.println("Ожидаемая ошибка (невалидный паттерн): " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }
}
