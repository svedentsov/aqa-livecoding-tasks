package com.svedentsov.aqa.tasks.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для BasicEmailValidation")
class BasicEmailValidationTest {

    private BasicEmailValidation validator;

    @BeforeEach
    void setUp() {
        validator = new BasicEmailValidation();
    }

    @Nested
    @DisplayName("Метод isValidEmailBasicIndexOf")
    class IndexOfMethodTests {

        @ParameterizedTest(name = "Валидный Email (IndexOf): \"{0}\" -> true")
        @ValueSource(strings = {
                "test@example.com",
                "user.name@domain.co.uk", // Пройдет, т.к. последняя точка не в конце
                "firstname.lastname@example.org",
                "email@subdomain.example.com",
                "user+tag@example.net",     // + не проверяется indexOf методом
                "a@b.cde",                  // TLD .cde
                "simple@domain.info"
        })
        void shouldReturnTrueForValidEmailsUsingIndexOf(String validEmail) {
            assertTrue(validator.isValidEmailBasicIndexOf(validEmail), "Email '" + validEmail + "' должен быть валидным по indexOf-правилам");
        }

        @ParameterizedTest(name = "Невалидный Email (IndexOf): \"{0}\" -> false")
        @ValueSource(strings = {
                "test.example.com",    // нет @
                "test@examplecom",     // нет точки после @
                "test@.com",           // точка сразу после @
                "@example.com",        // @ в начале
                "test@",               // @ в конце
                "test@example.",       // точка в конце
                "test@@example.com",   // две @
                " test@example.com",   // пробел в начале
                "test@example.com ",   // пробел в конце
                "a@b.c",               // TLD из одного символа (точка последняя значащая)
                "user@localhost",      // нет точки в доменной части для TLD
                "user@.d.com",         // точка сразу после @
                "plainaddress",        // просто текст
                "email.example.com",   // нет @
                "email@example@example.com" // две @
        })
        void shouldReturnFalseForInvalidEmailsUsingIndexOf(String invalidEmail) {
            assertFalse(validator.isValidEmailBasicIndexOf(invalidEmail), "Email '" + invalidEmail + "' должен быть невалидным по indexOf-правилам");
        }

        @ParameterizedTest(name = "Null/Empty/Whitespace Email (IndexOf): \"{0}\" -> false")
        @NullAndEmptySource
        @ValueSource(strings = {" ", "   "}) // строки только из пробелов
        void shouldReturnFalseForNullEmptyOrWhitespaceOnlyUsingIndexOf(String email) {
            assertFalse(validator.isValidEmailBasicIndexOf(email), "Email '" + email + "' (null/empty/whitespace) должен быть невалидным");
        }
    }

    @Nested
    @DisplayName("Метод isValidEmailBasicRegex")
    class RegexMethodTests {

        @ParameterizedTest(name = "Валидный Email (Regex): \"{0}\" -> true")
        @ValueSource(strings = {
                "test@example.com",
                "user.name@domain.co.uk",
                "firstname.lastname@example.org",
                "email@subdomain.example.com",
                "user+tag@example.net",
                "a@b.cde",
                " test@example.com ", // Regex-метод делает trim
                "user@hyphen-domain.com",
                "test@example.museum" // Длинный TLD
        })
        void shouldReturnTrueForValidEmailsUsingRegex(String validEmail) {
            assertTrue(validator.isValidEmailBasicRegex(validEmail), "Email '" + validEmail + "' должен быть валидным по regex-правилам");
        }

        @ParameterizedTest(name = "Невалидный Email (Regex): \"{0}\" -> false")
        @ValueSource(strings = {
                "test.example.com",    // нет @
                "test@examplecom",     // нет точки перед TLD
                "test@.com",           // домен начинается с точки
                "@example.com",        // локальная часть пуста (по шаблону может пройти, если не пуста) - зависит от интерпретации +
                // текущий шаблон требует хотя бы один символ до @
                "test@",               // доменная часть пуста
                "test@example.",       // TLD пуст
                "test@@example.com",   // две @ (не соответствует шаблону)
                "a@b.c",               // TLD < 2 символов
                "user@localhost",      // нет TLD из 2+ символов
                "user@.d.com",         // домен начинается с точки
                "plainaddress",
                "email.example.com",
                "#@%^%#$@#$@#.com",   // невалидные символы в локальной части по стандарту, но часть проходит по regex `._%+-`
                "email@example@example.com", // две @
                "test@example..com",   // две точки в домене
                "test@example.c_m"     // _ в TLD (невалидно по regex [a-zA-Z]{2,})
        })
        void shouldReturnFalseForInvalidEmailsUsingRegex(String invalidEmail) {
            assertFalse(validator.isValidEmailBasicRegex(invalidEmail), "Email '" + invalidEmail + "' должен быть невалидным по regex-правилам");
        }

        @ParameterizedTest(name = "Null/Empty/Whitespace Email (Regex): \"{0}\" -> false")
        @NullAndEmptySource // null и ""
        @ValueSource(strings = {" ", "   "}) // строки только из пробелов (после trim станут пустыми)
        void shouldReturnFalseForNullEmptyOrWhitespaceOnlyUsingRegex(String email) {
            assertFalse(validator.isValidEmailBasicRegex(email), "Email '" + email + "' (null/empty/whitespace) должен быть невалидным");
        }
    }
}
