package com.svedentsov.aqa.tasks.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты для ValidateIpAddress")
class ValidateIpAddressTest {

    private ValidateIpAddress validator;

    @BeforeEach
    void setUp() {
        validator = new ValidateIpAddress();
    }

    // --- Тесты для валидных IPv4 адресов ---
    @ParameterizedTest(name = "IPv4: \"{0}\" -> Ожидание: true")
    @ValueSource(strings = {
            "192.168.0.1",
            "255.255.255.255",
            "0.0.0.0",
            "1.1.1.1",
            "10.0.42.100",
            "127.0.0.1",
            "1.2.3.0" // Одиночный ноль в октете валиден
    })
    @DisplayName("Должен возвращать true для валидных IPv4 адресов")
    void shouldReturnTrueForValidIpAddresses(String validIp) {
        assertTrue(validator.isValidIPv4(validIp), "IP адрес \"" + validIp + "\" должен быть валидным");
    }

    // --- Тесты для невалидных IPv4 адресов ---
    @ParameterizedTest(name = "IPv4: \"{0}\" -> Ожидание: false")
    @ValueSource(strings = {
            // Октет вне диапазона
            "256.1.1.1",
            "192.168.0.256",
            "-1.0.0.0", // Отрицательное число
            // Ведущие нули
            "192.168.0.01",
            "01.02.03.04",
            "192.001.000.1",
            // Неправильное количество частей
            "192.168.0",     // 3 части
            "1.2.3.4.5",     // 5 частей
            // Пустые октеты
            "192.168..1",
            "1.2.3.4.",      // Конечная точка
            ".1.2.3.4",      // Начальная точка
            "1..2.3",
            // Нечисловые символы
            "abc.def.ghi.jkl",
            "192.168.0.a",
            // Слишком длинные октеты
            "1234.1.1.1",
            "1.1234.1.1",
            // С пробелами
            "192.168.0.1 ", // Пробел в конце
            " 192.168.0.1", // Пробел в начале
            "192.168 .0.1", // Пробел внутри
            // Другие невалидные форматы
            "192.168.0.-1"
    })
    @DisplayName("Должен возвращать false для невалидных IPv4 адресов")
    void shouldReturnFalseForInvalidIpAddresses(String invalidIp) {
        assertFalse(validator.isValidIPv4(invalidIp), "IP адрес \"" + invalidIp + "\" должен быть невалидным");
    }

    // --- Тесты для null, пустой строки и строки только из пробелов ---
    @ParameterizedTest(name = "Вход: {0} -> Ожидание: false")
    @NullAndEmptySource // Проверяет null и ""
    @ValueSource(strings = {" ", "   ", "\t"}) // Добавляем строки только из пробелов
    @DisplayName("Должен возвращать false для null, пустых и содержащих только пробелы строк")
    void shouldReturnFalseForNullEmptyOrWhitespaceOnly(String input) {
        assertFalse(validator.isValidIPv4(input), "Для null/пустых/пробельных строк ожидается false");
    }
}
