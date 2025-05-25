package com.svedentsov.aqa.tasks.algorithms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для проверки \"счастливого билета\" 🎫")
class LuckyTicketTest {

    private LuckyTicket luckyTicketChecker;

    @BeforeEach
    void setUp() {
        luckyTicketChecker = new LuckyTicket();
    }

    static Stream<Arguments> luckyTicketTestCases() {
        return Stream.of(
                Arguments.of("123402", true, "Классический счастливый билет (6 == 6)"),
                Arguments.of("000000", true, "Все нули (0 == 0)"),
                Arguments.of("11", true, "Две цифры, счастливый (1 == 1)"),
                Arguments.of("1010", true, "Четыре цифры, счастливый (1 == 1)"),
                Arguments.of("55", true, "Две одинаковые цифры (5 == 5)"),
                Arguments.of("987888", true, "Шесть цифр, счастливый (24 == 24)"),
                Arguments.of("123456", false, "Несчастливый билет (6 != 15)"),
                Arguments.of("10", false, "Две цифры, несчастливый (1 != 0)"),
                Arguments.of("000001", false, "Почти все нули, несчастливый (0 != 1)")
        );
    }

    @ParameterizedTest(name = "{2}: isLuckyTicket(\"{0}\") -> {1}")
    @MethodSource("luckyTicketTestCases")
    @DisplayName("Проверка валидных счастливых и несчастливых билетов")
    void testLuckyAndUnluckyTickets(String ticketNumber, boolean expected, String description) {
        assertEquals(expected, luckyTicketChecker.isLuckyTicket(ticketNumber), description);
    }

    @ParameterizedTest(name = "Невалидный формат (нечетная длина): \"{0}\" -> false")
    @ValueSource(strings = {"123", "0", "98765"})
    @DisplayName("Должен возвращать false для номеров с нечетной длиной")
    void isLuckyTicket_oddLength_shouldReturnFalse(String ticketNumber) {
        assertFalse(luckyTicketChecker.isLuckyTicket(ticketNumber), "Нечетная длина");
    }

    @ParameterizedTest(name = "Невалидный формат (не цифры): \"{0}\" -> false")
    @ValueSource(strings = {"1a3402", "12 34", "12-34", "+12345"})
    @DisplayName("Должен возвращать false для номеров с нецифровыми символами")
    void isLuckyTicket_nonDigitCharacters_shouldReturnFalse(String ticketNumber) {
        assertFalse(luckyTicketChecker.isLuckyTicket(ticketNumber), "Содержит не цифры");
    }

    @ParameterizedTest(name = "Невалидный формат (null или пустой): \"{0}\" -> false")
    @NullAndEmptySource
    @DisplayName("Должен возвращать false для null или пустой строки")
    void isLuckyTicket_nullOrEmpty_shouldReturnFalse(String ticketNumber) {
        // ticketNumber будет null, затем "" из-за @NullAndEmptySource
        assertFalse(luckyTicketChecker.isLuckyTicket(ticketNumber), "Null или пустая строка");
    }
}
