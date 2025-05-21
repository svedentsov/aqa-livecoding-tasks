package com.svedentsov.aqa.tasks.files_io_formats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для DateTimeManipulation")
class DateTimeManipulationTest {

    private DateTimeManipulation dateTimeManipulator;
    private static final Locale RU_LOCALE = Locale.of("ru", "RU");
    private static final Locale US_LOCALE = Locale.US;


    @BeforeEach
    void setUp() {
        dateTimeManipulator = new DateTimeManipulation();
    }

    @Nested
    @DisplayName("Тесты для метода daysBetween")
    class DaysBetweenTests {

        @ParameterizedTest(name = "Дней между \"{0}\" и \"{1}\" -> {2}")
        @CsvSource({
                "2025-04-01, 2025-04-10, 9",  // Пример из задания
                "2025-05-01, 2025-04-16, 15", // Обратный порядок
                "2024-01-01, 2025-01-01, 366", // Високосный год 2024
                "2023-01-01, 2024-01-01, 365", // Невисокосный год 2023
                "2025-04-16, 2025-04-16, 0",   // Та же дата
                "2000-02-28, 2000-03-01, 2",   // Високосный 2000, через 29 февраля
                "2100-02-28, 2100-03-01, 1"    // Невисокосный 2100 (делится на 100, но не на 400)
        })
        void daysBetween_validDates_shouldReturnCorrectDifference(String dateStr1, String dateStr2, long expectedDays) {
            assertEquals(expectedDays, dateTimeManipulator.daysBetween(dateStr1, dateStr2));
        }

        @Test
        @DisplayName("daysBetween с null аргументами должен выбросить NullPointerException")
        void daysBetween_nullInputs_shouldThrowNullPointerException() {
            assertThrows(NullPointerException.class, () -> dateTimeManipulator.daysBetween(null, "2025-01-01"));
            assertThrows(NullPointerException.class, () -> dateTimeManipulator.daysBetween("2025-01-01", null));
            assertThrows(NullPointerException.class, () -> dateTimeManipulator.daysBetween(null, null));
        }

        @ParameterizedTest(name = "Невалидная дата: \"{0}\" или \"{1}\"")
        @CsvSource({
                "2023-02-30, 2023-03-01", // Невалидный день
                "2023-13-01, 2023-01-01", // Невалидный месяц
                "invalid-date, 2023-01-01", // Неверный формат
                "2023-01-01, 01/02/2023"   // Неверный формат для второго
        })
        @DisplayName("daysBetween с невалидным форматом/датой должен выбросить DateTimeParseException")
        void daysBetween_invalidDateStrings_shouldThrowDateTimeParseException(String dateStr1, String dateStr2) {
            assertThrows(DateTimeParseException.class, () -> dateTimeManipulator.daysBetween(dateStr1, dateStr2));
        }
    }

    @Nested
    @DisplayName("Тесты для метода formatDate")
    class FormatDateTests {
        private final LocalDate testDate = LocalDate.of(2025, 7, 20); // Воскресенье

        static Stream<Arguments> formatDateParameters() {
            return Stream.of(
                    Arguments.of(LocalDate.of(2023, 10, 5), "dd/MM/yyyy", US_LOCALE, "05/10/2023"),
                    Arguments.of(LocalDate.of(2023, 10, 5), "dd.MM.yyyy", RU_LOCALE, "05.10.2023"),
                    Arguments.of(LocalDate.of(2023, 8, 15), "d MMMM yy", RU_LOCALE, "15 августа 23"),
                    Arguments.of(LocalDate.of(2023, 8, 15), "MMMM d, yyyy", US_LOCALE, "August 15, 2023"),
                    Arguments.of(LocalDate.of(2024, 1, 7), "EEEE, dd MMM yyyy", US_LOCALE, "Sunday, 07 Jan 2024"),
                    Arguments.of(LocalDate.of(2024, 1, 7), "EEEE, dd MMMM yyyy", RU_LOCALE, "воскресенье, 07 января 2024"),
                    Arguments.of(LocalDate.of(2025, 12, 25), "E, dd MMM yy", US_LOCALE, "Thu, 25 Dec 25")
            );
        }

        @ParameterizedTest(name = "formatDate({0}, \"{1}\", {2}) -> \"{3}\"")
        @MethodSource("formatDateParameters")
        void formatDate_validInputs_shouldFormatCorrectly(LocalDate date, String pattern, Locale locale, String expectedString) {
            assertEquals(expectedString, dateTimeManipulator.formatDate(date, pattern, locale));
        }

        @Test
        @DisplayName("formatDate с локалью null должен использовать локаль по умолчанию")
        void formatDate_nullLocale_shouldUseDefault() {
            LocalDate date = LocalDate.of(2023, 1, 1);
            String pattern = "MMMM";
            // Мы не можем точно предсказать результат для Locale.getDefault() в общем случае,
            // но можем проверить, что метод не падает и возвращает что-то.
            // Для более точного теста можно было бы установить Locale.setDefault() перед тестом,
            // но это может повлиять на другие тесты.
            assertDoesNotThrow(() -> {
                String formattedDate = dateTimeManipulator.formatDate(date, pattern, null);
                assertNotNull(formattedDate);
                assertFalse(formattedDate.isEmpty());
                // Примерная проверка для английской локали по умолчанию
                if (Locale.getDefault(Locale.Category.FORMAT).getLanguage().equals("en")) {
                    assertEquals("January", formattedDate);
                }
            });
        }

        @Test
        @DisplayName("formatDate с null датой или паттерном должен выбросить NullPointerException")
        void formatDate_nullDateOrPattern_shouldThrowNullPointerException() {
            assertThrows(NullPointerException.class, () -> dateTimeManipulator.formatDate(null, "yyyy-MM-dd", US_LOCALE));
            assertThrows(NullPointerException.class, () -> dateTimeManipulator.formatDate(testDate, null, US_LOCALE));
        }

        @Test
        @DisplayName("formatDate с пустым паттерном должен выбросить IllegalArgumentException")
        void formatDate_emptyPattern_shouldThrowIllegalArgumentException() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> dateTimeManipulator.formatDate(testDate, "", US_LOCALE));
            assertTrue(ex.getMessage().contains("Pattern cannot be empty"));
        }

        @Test
        @DisplayName("formatDate с невалидным паттерном должен выбросить IllegalArgumentException")
        void formatDate_invalidPattern_shouldThrowIllegalArgumentException() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> dateTimeManipulator.formatDate(testDate, "invalid-XYZ-pattern", US_LOCALE));
            assertTrue(ex.getMessage().contains("Invalid date format pattern"));
        }
    }

    @Nested
    @DisplayName("Тесты для метода getDayOfWeekName")
    class GetDayOfWeekNameTests {
        static Stream<Arguments> dayOfWeekParameters() {
            // Используем фиксированные даты, для которых день недели известен
            return Stream.of(
                    Arguments.of(LocalDate.of(2023, 10, 26), RU_LOCALE, "четверг"), // 26 Oct 2023 is Thursday
                    Arguments.of(LocalDate.of(2023, 10, 26), US_LOCALE, "Thursday"),
                    Arguments.of(LocalDate.of(2025, 5, 21), RU_LOCALE, "среда"), // Current date based on context
                    Arguments.of(LocalDate.of(2025, 5, 21), US_LOCALE, "Wednesday"),
                    Arguments.of(LocalDate.of(2025, 12, 25), RU_LOCALE, "четверг"), // Christmas 2025
                    Arguments.of(LocalDate.of(2025, 12, 25), US_LOCALE, "Thursday")
            );
        }

        @ParameterizedTest(name = "getDayOfWeekName({0}, {1}) -> \"{2}\"")
        @MethodSource("dayOfWeekParameters")
        void getDayOfWeekName_validInputs_shouldReturnCorrectName(LocalDate date, Locale locale, String expectedDayName) {
            assertEquals(expectedDayName.toLowerCase(locale), dateTimeManipulator.getDayOfWeekName(date, locale).toLowerCase(locale));
        }

        // Учитывая, что getDisplayName может возвращать разные регистры в зависимости от локали,
        // лучше привести к одному регистру перед сравнением или быть уверенным в регистре для конкретных локалей
        @Test
        @DisplayName("getDayOfWeekName для известной даты и локали (проверка регистра)")
        void getDayOfWeekName_specificCase() {
            LocalDate date = LocalDate.of(2025, 5, 21); // Среда
            assertEquals("среда", dateTimeManipulator.getDayOfWeekName(date, RU_LOCALE));
            assertEquals("Wednesday", dateTimeManipulator.getDayOfWeekName(date, US_LOCALE));
        }


        @Test
        @DisplayName("getDayOfWeekName с null датой или локалью должен выбросить NullPointerException")
        void getDayOfWeekName_nullInputs_shouldThrowNullPointerException() {
            LocalDate date = LocalDate.now();
            assertThrows(NullPointerException.class, () -> dateTimeManipulator.getDayOfWeekName(null, US_LOCALE));
            assertThrows(NullPointerException.class, () -> dateTimeManipulator.getDayOfWeekName(date, null));
        }
    }
}
