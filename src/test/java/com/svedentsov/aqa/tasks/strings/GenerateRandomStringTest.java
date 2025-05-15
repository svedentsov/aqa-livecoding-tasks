package com.svedentsov.aqa.tasks.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для GenerateRandomString")
class GenerateRandomStringTest {

    private GenerateRandomString generator;
    private static final String ALLOWED_CHARACTERS_FOR_TEST = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz" +
            "0123456789";

    @BeforeEach
    void setUp() {
        generator = new GenerateRandomString();
    }

    // --- Тесты на корректность длины генерируемой строки ---
    @ParameterizedTest(name = "Длина строки {0}")
    @ValueSource(ints = {1, 5, 10, 20, 62, 100})
    @DisplayName("Должен генерировать строку указанной длины")
    void shouldGenerateStringOfSpecifiedLength(int length) {
        String randomStr = generator.generateRandomString(length);
        assertEquals(length, randomStr.length(), "Длина сгенерированной строки не совпадает с ожидаемой");
    }

    @Test
    @DisplayName("Должен возвращать пустую строку для длины 0")
    void shouldReturnEmptyStringForLengthZero() {
        assertEquals("", generator.generateRandomString(0), "Для длины 0 должна возвращаться пустая строка");
    }

    // --- Тест на корректность используемых символов ---
    @ParameterizedTest(name = "Проверка символов для строки длины {0}")
    @ValueSource(ints = {10, 50}) // Тестируем на строках разной длины
    @DisplayName("Сгенерированная строка должна состоять только из разрешенных символов")
    void generatedStringShouldContainOnlyAllowedCharacters(int length) {
        String randomStr = generator.generateRandomString(length);
        for (char c : randomStr.toCharArray()) {
            assertTrue(ALLOWED_CHARACTERS_FOR_TEST.indexOf(c) != -1,
                    "Символ '" + c + "' не принадлежит к разрешенному набору");
        }
    }

    // --- Тест на выбрасывание исключения для отрицательной длины ---
    @ParameterizedTest(name = "Длина {0} должна вызвать IllegalArgumentException")
    @ValueSource(ints = {-1, -5, -100})
    @DisplayName("Должен выбросить IllegalArgumentException для отрицательной длины")
    void shouldThrowIllegalArgumentExceptionForNegativeLength(int negativeLength) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            generator.generateRandomString(negativeLength);
        });
        assertTrue(exception.getMessage().contains("Length cannot be negative"),
                "Сообщение об ошибке должно указывать на недопустимость отрицательной длины");
    }

    // --- Качественный тест на случайность (вероятностный) ---
    // Повторяем тест несколько раз, чтобы увеличить вероятность обнаружения проблем
    @RepeatedTest(5) // Запустим 5 раз
    @DisplayName("Две сгенерированные строки (длина > 1) обычно должны отличаться")
    void generatedStringsShouldGenerallyBeDifferent() {
        int length = 15; // Достаточная длина для минимизации случайных совпадений
        String str1 = generator.generateRandomString(length);
        String str2 = generator.generateRandomString(length);
        // Этот тест может ИНОГДА падать из-за природы случайности, но для длинных строк это маловероятно
        // Для production тестов случайности нужны более сложные статистические подходы
        // Для длины 1 вероятность совпадения выше
        assertNotEquals(str1, str2,
                "Две последовательно сгенерированные строки не должны быть идентичны (вероятностно)." +
                        " str1='" + str1 + "', str2='" + str2 + "'");
    }
}
