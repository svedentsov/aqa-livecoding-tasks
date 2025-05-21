package com.svedentsov.aqa.tasks.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты для валидации телефонного номера SimpleRegex")
class SimpleRegexTest {

    private SimpleRegex validator;

    @BeforeEach
    void setUp() {
        validator = new SimpleRegex();
    }

    static Stream<Arguments> validPhoneNumbers() {
        return Stream.of(
                Arguments.of("+7-911-123-45-67", "Классический валидный номер"),
                Arguments.of("+7-000-000-00-00", "Номер с нулями")
        );
    }

    @ParameterizedTest(name = "Валидный номер: {0} ({1})")
    @MethodSource("validPhoneNumbers")
    @DisplayName("Должен возвращать true для валидных номеров")
    void isValidPhoneNumber_validNumbers_shouldReturnTrue(String validNumber, String description) {
        assertTrue(validator.isValidPhoneNumber(validNumber), description);
    }

    static Stream<Arguments> invalidPhoneNumbers() {
        return Stream.of(
                Arguments.of("89111234567", "Неверный префикс, нет дефисов"),
                Arguments.of("+7-91-123-45-67", "Не 3 цифры в коде оператора"),
                Arguments.of("+7-911-123-45-6", "Не 2 цифры в конце (группа 1)"),
                Arguments.of("+7-911-123-4-56", "Не 2 цифры в конце (группа 2)"),
                Arguments.of("+7-911-123-45-678", "Лишняя цифра в конце"),
                Arguments.of("+7-911-123-45-6A", "Буква вместо цифры"),
                Arguments.of(" +7-911-123-45-67", "Пробел в начале"),
                Arguments.of("+7-911-123-45-67 ", "Пробел в конце"),
                Arguments.of("+79111234567", "Нет дефисов"),
                Arguments.of("+8-911-123-45-67", "Неверный код страны"),
                Arguments.of("abc", "Не телефонный номер"),
                Arguments.of("", "Пустая строка")
        );
    }

    @ParameterizedTest(name = "Невалидный номер: {0} ({1})")
    @MethodSource("invalidPhoneNumbers")
    @DisplayName("Должен возвращать false для невалидных номеров")
    void isValidPhoneNumber_invalidNumbers_shouldReturnFalse(String invalidNumber, String description) {
        assertFalse(validator.isValidPhoneNumber(invalidNumber), description);
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("Должен возвращать false для null ввода")
    void isValidPhoneNumber_nullInput_shouldReturnFalse(String nullInput) {
        // nullInput будет null из-за @NullSource
        assertFalse(validator.isValidPhoneNumber(nullInput));
    }

    @Test
    @DisplayName("Дополнительная проверка с другим валидным номером")
    void isValidPhoneNumber_anotherValidNumber_shouldReturnTrue() {
        assertTrue(validator.isValidPhoneNumber("+7-123-456-78-90"));
    }

    @Test
    @DisplayName("Дополнительная проверка с другим невалидным номером (длина блока)")
    void isValidPhoneNumber_anotherInvalidNumber_blockLength_shouldReturnFalse() {
        assertFalse(validator.isValidPhoneNumber("+7-1234-567-89-01"));
    }

    @Test
    @DisplayName("Дополнительная проверка с невалидным символом в середине")
    void isValidPhoneNumber_anotherInvalidNumber_symbol_shouldReturnFalse() {
        assertFalse(validator.isValidPhoneNumber("+7-123-A67-89-01"));
    }
}
