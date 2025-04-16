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
 * <p>
 * Описание: Найти разницу между датами, отформатировать дату.
 * (Проверяет: Java Date/Time API)
 * <p>
 * Задание: Используя `java.time` API, напишите метод, который принимает две строки
 * с датами в формате "yyyy-MM-dd" и возвращает количество дней между ними.
 * Дополнительно: методы для форматирования даты и получения дня недели.
 * <p>
 * Пример: `daysBetween("2025-04-01", "2025-04-10")` -> `9`.
 */
public class DateTimeManipulation {

    // Форматтер для стандартного ISO формата даты (потокобезопасен)
    private static final DateTimeFormatter ISO_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE; // yyyy-MM-dd

    /**
     * Вычисляет количество полных дней между двумя датами, заданными в строках
     * формата "yyyy-MM-dd".
     * Использует современный {@link LocalDate} и {@link ChronoUnit}.
     *
     * @param dateStr1 Первая дата в формате "yyyy-MM-dd".
     * @param dateStr2 Вторая дата в формате "yyyy-MM-dd".
     * @return Абсолютное количество дней между датами.
     * @throws NullPointerException   если один из аргументов null.
     * @throws DateTimeParseException если строка имеет неверный формат или невалидную дату.
     */
    public long daysBetween(String dateStr1, String dateStr2) throws DateTimeParseException {
        Objects.requireNonNull(dateStr1, "Date string 1 cannot be null.");
        Objects.requireNonNull(dateStr2, "Date string 2 cannot be null.");

        // Парсим строки в LocalDate, используя стандартный форматтер ISO_LOCAL_DATE
        // Этот метод выбросит DateTimeParseException при ошибке формата/значения.
        LocalDate date1 = LocalDate.parse(dateStr1, ISO_DATE_FORMATTER);
        LocalDate date2 = LocalDate.parse(dateStr2, ISO_DATE_FORMATTER);

        // Вычисляем количество дней между датами
        // ChronoUnit.DAYS.between(startInclusive, endExclusive)
        return Math.abs(ChronoUnit.DAYS.between(date1, date2));

        // Альтернатива с Period (менее точна для разницы только в днях, если важны часы/минуты)
        // Period period = Period.between(date1, date2);
        // return Math.abs(period.getDays()); // ОСТОРОЖНО: period.getDays() вернет только компонент дней, не общую разницу!
    }

    /**
     * Форматирует заданную дату {@link LocalDate} в строку с использованием указанного шаблона.
     *
     * @param date    Дата для форматирования (не null).
     * @param pattern Шаблон формата (например, "dd/MM/yyyy", "dd MMMM yyyy", "E, dd MMM yy").
     *                Не должен быть null или пустым.
     * @param locale  Локаль для форматирования (например, для названий месяцев/дней).
     *                Если null, используется локаль по умолчанию.
     * @return Отформатированная строка с датой.
     * @throws IllegalArgumentException если шаблон формата {@code pattern} некорректен.
     * @throws NullPointerException     если {@code date} или {@code pattern} равны null.
     */
    public String formatDate(LocalDate date, String pattern, Locale locale) {
        Objects.requireNonNull(date, "Date cannot be null");
        Objects.requireNonNull(pattern, "Pattern cannot be null");
        if (pattern.isEmpty()) {
            throw new IllegalArgumentException("Pattern cannot be empty.");
        }
        // Используем локаль по умолчанию, если не указана
        Locale effectiveLocale = (locale == null) ? Locale.getDefault() : locale;

        try {
            // Создаем форматтер с указанным шаблоном и локалью
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, effectiveLocale);
            return date.format(formatter);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid date format pattern: '" + pattern + "'", e);
        }
    }

    /**
     * Возвращает название дня недели для заданной даты на указанном языке.
     *
     * @param date   Дата (не null).
     * @param locale Локаль для определения языка названия дня недели (не null).
     * @return Полное название дня недели (например, "Понедельник", "Monday").
     * @throws NullPointerException если {@code date} или {@code locale} равны null.
     */
    public String getDayOfWeekName(LocalDate date, Locale locale) {
        Objects.requireNonNull(date, "Date cannot be null");
        Objects.requireNonNull(locale, "Locale cannot be null");
        // Получаем DayOfWeek enum и его текстовое представление
        return date.getDayOfWeek().getDisplayName(TextStyle.FULL, locale);
    }

    /**
     * Точка входа для демонстрации работы методов работы с датами.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        DateTimeManipulation sol = new DateTimeManipulation();
        Locale ruLocale = Locale.of("ru", "RU");
        Locale usLocale = Locale.US;

        System.out.println("--- Date/Time Manipulation (java.time API) ---");

        // --- Вычисление разницы ---
        System.out.println("\n[Calculating Days Between]");
        runDaysBetweenTest(sol, "2025-04-16", "2025-05-01", "Normal case");
        runDaysBetweenTest(sol, "2024-01-01", "2025-01-01", "Leap year"); // 366
        runDaysBetweenTest(sol, "2025-03-10", "2025-02-25", "Reverse order");
        runDaysBetweenTest(sol, "2025-04-16", "2025-04-16", "Same date");
        runDaysBetweenTest(sol, "2023-02-30", "2023-03-01", "Invalid date 1"); // Exception
        runDaysBetweenTest(sol, "2024-04-05", "05/04/2024", "Invalid format 2"); // Exception
        runDaysBetweenTest(sol, null, "2024-04-05", "Null date 1"); // Exception

        // --- Форматирование ---
        System.out.println("\n[Formatting Dates]");
        LocalDate date1 = LocalDate.now(); // Сегодняшняя дата
        LocalDate date2 = LocalDate.of(2025, 12, 25); // Рождество 2025

        runFormatDateTest(sol, date1, "dd/MM/yyyy", ruLocale, "Today (dd/MM/yyyy RU)");
        runFormatDateTest(sol, date1, "EEEE, d MMMM yyyy", ruLocale, "Today (Full RU)");
        runFormatDateTest(sol, date2, "MMMM d, yyyy", usLocale, "Christmas (MMMM d, yyyy US)");
        runFormatDateTest(sol, date2, "E dd.MM.yy", usLocale, "Christmas (E dd.MM.yy US)");
        runFormatDateTest(sol, date1, "yyyyMMdd", null, "Today (yyyyMMdd default locale)");
        runFormatDateTest(sol, date1, "invalid-pattern", ruLocale, "Invalid pattern"); // Exception

        // --- День недели ---
        System.out.println("\n[Getting Day of Week]");
        runDayOfWeekTest(sol, date1, ruLocale, "Today (RU)");
        runDayOfWeekTest(sol, date1, usLocale, "Today (US)");
        runDayOfWeekTest(sol, date2, ruLocale, "Christmas 2025 (RU)");
        runDayOfWeekTest(sol, date2, usLocale, "Christmas 2025 (US)");
        runDayOfWeekTest(sol, null, ruLocale, "Null date"); // Exception
    }

    /**
     * Вспомогательный метод для тестирования daysBetween.
     */
    private static void runDaysBetweenTest(DateTimeManipulation sol, String d1, String d2, String desc) {
        String s1 = (d1 == null ? "null" : "'" + d1 + "'");
        String s2 = (d2 == null ? "null" : "'" + d2 + "'");
        System.out.print(String.format("%-20s: daysBetween(%s, %s) -> ", desc, s1, s2));
        try {
            long days = sol.daysBetween(d1, d2);
            System.out.println(days);
        } catch (NullPointerException | DateTimeParseException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getClass().getSimpleName() + " (" + e.getMessage() + ")");
        } catch (Exception e) {
            System.err.println("Unexpected Error - " + e.getMessage());
        }
    }

    /**
     * Вспомогательный метод для тестирования formatDate.
     */
    private static void runFormatDateTest(DateTimeManipulation sol, LocalDate date, String pattern, Locale locale, String desc) {
        String dateStr = (date == null ? "null" : date.toString());
        String localeStr = (locale == null ? "default" : locale.toString());
        System.out.print(String.format("%-30s: formatDate(%s, \"%s\", %s) -> ", desc, dateStr, pattern, localeStr));
        try {
            String formatted = sol.formatDate(date, pattern, locale);
            System.out.println("'" + formatted + "'");
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getClass().getSimpleName() + " (" + e.getMessage() + ")");
        } catch (Exception e) {
            System.err.println("Unexpected Error - " + e.getMessage());
        }
    }

    /**
     * Вспомогательный метод для тестирования getDayOfWeekNameRussian.
     */
    private static void runDayOfWeekTest(DateTimeManipulation sol, LocalDate date, Locale locale, String desc) {
        String dateStr = (date == null ? "null" : date.toString());
        String localeStr = (locale == null ? "default" : locale.toString());
        System.out.print(String.format("%-25s: getDayOfWeekName(%s, %s) -> ", desc, dateStr, localeStr));
        try {
            String dayName = sol.getDayOfWeekName(date, locale);
            System.out.println("'" + dayName + "'");
        } catch (NullPointerException e) {
            System.out.println("Error: " + e.getClass().getSimpleName() + " (" + e.getMessage() + ")");
        } catch (Exception e) {
            System.err.println("Unexpected Error - " + e.getMessage());
        }
    }
}
