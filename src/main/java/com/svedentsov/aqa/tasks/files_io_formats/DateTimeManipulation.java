package com.svedentsov.aqa.tasks.files_io_formats;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Objects;

/**
 * Решение задачи №78: Работа с датами и временем (используя java.time API).
 * Описание: Найти разницу между датами, отформатировать дату.
 * (Проверяет: Java Date/Time API)
 * Задание: Используя `java.time` API, напишите метод, который принимает две строки
 * с датами в формате "yyyy-MM-dd" и возвращает количество дней между ними.
 * Дополнительно: методы для форматирования даты и получения дня недели.
 * Пример: `daysBetween("2025-04-01", "2025-04-10")` -> `9`.
 */
public class DateTimeManipulation {

    // Форматтер для стандартного ISO формата даты (yyyy-MM-dd), потокобезопасен
    private static final DateTimeFormatter ISO_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    /**
     * Вычисляет количество полных дней между двумя датами, заданными в строках
     * формата "yyyy-MM-dd".
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

        LocalDate date1 = LocalDate.parse(dateStr1, ISO_DATE_FORMATTER);
        LocalDate date2 = LocalDate.parse(dateStr2, ISO_DATE_FORMATTER);

        return Math.abs(ChronoUnit.DAYS.between(date1, date2));
    }

    /**
     * Форматирует заданную дату {@link LocalDate} в строку с использованием указанного шаблона.
     *
     * @param date    Дата для форматирования (не null).
     * @param pattern Шаблон формата (например, "dd/MM/yyyy"). Не должен быть null или пустым.
     * @param locale  Локаль для форматирования. Если null, используется локаль по умолчанию.
     * @return Отформатированная строка с датой.
     * @throws IllegalArgumentException если шаблон формата {@code pattern} некорректен или пуст.
     * @throws NullPointerException     если {@code date} или {@code pattern} равны null.
     */
    public String formatDate(LocalDate date, String pattern, Locale locale) {
        Objects.requireNonNull(date, "Date cannot be null");
        Objects.requireNonNull(pattern, "Pattern cannot be null");
        if (pattern.isEmpty()) {
            throw new IllegalArgumentException("Pattern cannot be empty.");
        }
        Locale effectiveLocale = (locale == null) ? Locale.getDefault(Locale.Category.FORMAT) : locale;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, effectiveLocale);
            return date.format(formatter);
        } catch (IllegalArgumentException e) {
            // Оборачиваем, чтобы предоставить более контекстное сообщение, если нужно,
            // или просто пробрасываем оригинальное исключение.
            throw new IllegalArgumentException("Invalid date format pattern: '" + pattern + "'", e);
        }
    }

    /**
     * Возвращает название дня недели для заданной даты на указанном языке.
     *
     * @param date   Дата (не null).
     * @param locale Локаль для определения языка названия дня недели (не null).
     * @return Полное название дня недели.
     * @throws NullPointerException если {@code date} или {@code locale} равны null.
     */
    public String getDayOfWeekName(LocalDate date, Locale locale) {
        Objects.requireNonNull(date, "Date cannot be null");
        Objects.requireNonNull(locale, "Locale cannot be null");
        return date.getDayOfWeek().getDisplayName(TextStyle.FULL, locale);
    }
}
