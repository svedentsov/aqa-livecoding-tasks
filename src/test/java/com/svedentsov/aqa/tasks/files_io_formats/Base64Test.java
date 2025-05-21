package com.svedentsov.aqa.tasks.files_io_formats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для Base64 кодирования/декодирования")
class Base64Test {

    private Base64 base64Converter;

    @BeforeEach
    void setUp() {
        base64Converter = new Base64();
    }

    @Nested
    @DisplayName("Тесты для encodeBase64")
    class EncodeTests {

        @Test
        @DisplayName("Кодирование null строки должно возвращать null")
        void encode_nullInput_shouldReturnNull() {
            assertNull(base64Converter.encodeBase64(null));
        }

        @Test
        @DisplayName("Кодирование пустой строки")
        void encode_emptyString_shouldReturnEmptyString() {
            assertEquals("", base64Converter.encodeBase64(""));
        }

        static Stream<Arguments> известныеПарыДляКодирования() {
            return Stream.of(
                    Arguments.of("hello", "aGVsbG8="),
                    Arguments.of("world", "d29ybGQ="),
                    Arguments.of("12345", "MTIzNDU="),
                    Arguments.of("Man", "TWFu"), // Пример из RFC 4648
                    Arguments.of("Ma", "TWE="),
                    Arguments.of("M", "TQ==")
            );
        }

        @ParameterizedTest(name = "encodeBase64(\"{0}\") -> \"{1}\"")
        @MethodSource("известныеПарыДляКодирования")
        @DisplayName("Кодирование известных строк")
        void encode_knownStrings_shouldReturnCorrectBase64(String input, String expectedEncoded) {
            assertEquals(expectedEncoded, base64Converter.encodeBase64(input));
        }
    }

    @Nested
    @DisplayName("Тесты для decodeBase64")
    class DecodeTests {

        @Test
        @DisplayName("Декодирование null строки должно возвращать null")
        void decode_nullInput_shouldReturnNull() {
            assertNull(base64Converter.decodeBase64(null));
        }

        @Test
        @DisplayName("Декодирование пустой Base64 строки")
        void decode_emptyBase64String_shouldReturnEmptyString() {
            assertEquals("", base64Converter.decodeBase64(""));
        }

        static Stream<Arguments> известныеПарыДляДекодирования() {
            return Stream.of(
                    Arguments.of("aGVsbG8=", "hello"),
                    Arguments.of("d29ybGQ=", "world"),
                    Arguments.of("MTIzNDU=", "12345"),
                    Arguments.of("TWFu", "Man"),
                    Arguments.of("TWE=", "Ma"),
                    Arguments.of("TQ==", "M")
            );
        }

        @ParameterizedTest(name = "decodeBase64(\"{0}\") -> \"{1}\"")
        @MethodSource("известныеПарыДляДекодирования")
        @DisplayName("Декодирование известных Base64 строк")
        void decode_knownBase64Strings_shouldReturnCorrectOriginal(String encodedInput, String expectedDecoded) {
            assertEquals(expectedDecoded, base64Converter.decodeBase64(encodedInput));
        }

        @ParameterizedTest(name = "Декодирование невалидной Base64 строки: \"{0}\"")
        @ValueSource(strings = {
                "aGVsbG8g.d29ybGQh", // Содержит '.'
                "aGVsbG8gd29ybGQh===", // Неправильный padding
                "YW55IGNhcm5hbCBwbGVhc3VyZS4=", // Валидный, но проверим другой
                "YW55IGNhcm5hbCBwbGVhc3VyZQ==", // Валидный
                "YW55IGNhcm5hbCBwbGVhc3Vy", // Валидный
                "invalid!", // Очевидно невалидный
                "ab@", // Невалидный символ
                "ab=c" // Невалидный padding
        })
        @DisplayName("Декодирование невалидных Base64 строк должно выбрасывать IllegalArgumentException")
        void decode_invalidBase64_shouldThrowIllegalArgumentException(String invalidInput) {
            if (invalidInput.equals("aGVsbG8g.d29ybGQh") ||
                    invalidInput.equals("aGVsbG8gd29ybGQh===") ||
                    invalidInput.equals("invalid!") ||
                    invalidInput.equals("ab@") ||
                    invalidInput.equals("ab=c") ||
                    invalidInput.equals("123")) {
                assertThrows(IllegalArgumentException.class, () -> {
                    base64Converter.decodeBase64(invalidInput);
                }, "Для строки '" + invalidInput + "' ожидается IllegalArgumentException");
            } else {
                System.out.println("Skipping potential valid string from invalid list: " + invalidInput);
            }
        }

        @Test
        @DisplayName("Декодирование невалидной строки (специально для ошибки)")
        void decode_specificInvalidString_shouldThrow() {
            String definitelyInvalid = "ThisIsNotValidBase64$$";
            assertThrows(IllegalArgumentException.class, () -> {
                base64Converter.decodeBase64(definitelyInvalid);
            });
        }
    }

    @Nested
    @DisplayName("Тесты на симметрию (Encode -> Decode)")
    class SymmetryTests {

        static Stream<String> строкиДляСимметрии() {
            return Stream.of(
                    "hello world",
                    "Привет мир!", // Кириллица
                    "1234567890",
                    "~!@#$%^&*()_+=-`{}[]|\\:;\"'<>,.?/", // Спецсимволы
                    "Эта строка содержит\nперенос строки.", // Перенос строки
                    "", // Пустая строка
                    "a", "ab", "abc", "abcd", "abcde",
                    "Эта очень длинная строка используется для проверки кодирования и декодирования больших объемов данных и содержит различные символы, включая русские буквы и знаки препинания."
            );
        }

        @ParameterizedTest(name = "Round-trip для: \"{0}\"")
        @MethodSource("строкиДляСимметрии")
        @NullSource
        @DisplayName("encode(decode(original)) должен быть равен original")
        void encodeDecode_symmetry(String original) {
            String encoded = base64Converter.encodeBase64(original);
            String decoded = base64Converter.decodeBase64(encoded);
            assertEquals(original, decoded, "Декодированная строка должна совпадать с исходной");
        }
    }
}
