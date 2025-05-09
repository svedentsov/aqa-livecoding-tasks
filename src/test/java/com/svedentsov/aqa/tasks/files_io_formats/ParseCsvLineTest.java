package com.svedentsov.aqa.tasks.files_io_formats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Тесты для ParseCsvLine")
class ParseCsvLineTest {

    private ParseCsvLine parser;

    @BeforeEach
    void setUp() {
        parser = new ParseCsvLine();
    }

    // --- Источник данных для валидных CSV строк ---
    static Stream<Arguments> provideValidCsvLines() {
        return Stream.of(
                Arguments.of("John,Doe,30", List.of("John", "Doe", "30")),
                Arguments.of("\"Smith, John\",\"New York, NY\",45", List.of("Smith, John", "New York, NY", "45")),
                Arguments.of("Field1,\"Field \"\"with\"\" quotes\",Field3", List.of("Field1", "Field \"with\" quotes", "Field3")),
                Arguments.of("one,two,,four", List.of("one", "two", "", "four")), // Пустое поле
                Arguments.of("\"one\",\"two\",\"\",\"four\"", List.of("one", "two", "", "four")), // Закавыченное пустое поле
                Arguments.of("a,b,\"c\"", List.of("a", "b", "c")), // Последнее поле закавычено
                Arguments.of("a,b,", List.of("a", "b", "")), // Пустое поле в конце
                Arguments.of(",a,b", List.of("", "a", "b")), // Пустое поле в начале
                Arguments.of("\"\"", List.of("")), // Одно закавыченное пустое поле
                Arguments.of("single", List.of("single")), // Одно поле
                Arguments.of("\"quoted, field\"", List.of("quoted, field")), // Одно закавыченное поле с запятой
                Arguments.of("  leading space, field2 ", List.of("  leading space", " field2 ")), // Пробелы являются частью незакавыченных полей
                Arguments.of("\"  leading and trailing  \",\" field with spaces \"", List.of("  leading and trailing  ", " field with spaces ")), // Пробелы внутри кавычек
                Arguments.of("\"\"\"\"", List.of("\"")), // Поле, состоящее из одной кавычки
                Arguments.of("\"a\"\"b\"", List.of("a\"b")), // Поле с экранированной кавычкой внутри
                Arguments.of(",,", List.of("", "", "")), // Три пустых поля
                Arguments.of(" , spacefield , ", List.of(" ", " spacefield ", " ")) // Поля с пробелами
        );
    }

    // --- Источник данных для невалидных CSV строк ---
    static Stream<String> provideInvalidCsvLines() {
        return Stream.of(
                "field1,\"unclosed quote", // Незакрытая кавычка
                "field1,\"quote\"between,field3",  // Символ после закрывающей кавычки (не запятая)
                "\"unclosed \"\" quote",           // Незакрытая кавычка с экранированной внутри
                "part\"iallyquoted,field2",        // Кавычка внутри незакавыченного поля (не в начале)
                "\"field1\"unexpected\"field2\"",  // Неожиданный символ после полностью закавыченного поля (до запятой)
                "\"field1\"  , field2"             // Пробелы после закрывающей кавычки перед запятой
        );
    }

    @Nested
    @DisplayName("Разбор валидных CSV строк")
    class ValidCsvLinesTests {
        @ParameterizedTest(name = "Вход: \"{0}\" -> {1}")
        @MethodSource("com.svedentsov.aqa.tasks.files_io_formats.ParseCsvLineTest#provideValidCsvLines")
        void shouldParseValidCsvLinesCorrectly(String csvLine, List<String> expectedFields) {
            List<String> actualFields = parser.parseCsvLine(csvLine);
            assertEquals(expectedFields, actualFields);
        }
    }

    @Nested
    @DisplayName("Разбор невалидных CSV строк")
    class InvalidCsvLinesTests {
        @ParameterizedTest(name = "Невалидный вход: \"{0}\" -> Ожидание: IllegalArgumentException")
        @MethodSource("com.svedentsov.aqa.tasks.files_io_formats.ParseCsvLineTest#provideInvalidCsvLines")
        void shouldThrowIllegalArgumentExceptionForInvalidLines(String invalidCsvLine) {
            assertThrows(IllegalArgumentException.class, () -> {
                parser.parseCsvLine(invalidCsvLine);
            });
        }
    }

    @Nested
    @DisplayName("Граничные случаи и пустые значения")
    class EdgeCaseTests {
        @ParameterizedTest(name = "Вход: {0} -> Ожидание: пустой список")
        @NullAndEmptySource
            // Для null и ""
        void shouldReturnEmptyListForNullOrEmptyInput(String csvLine) {
            List<String> actualFields = parser.parseCsvLine(csvLine);
            assertEquals(Collections.emptyList(), actualFields, "Для null или пустой строки должен возвращаться пустой список");
        }

        @Test
        @DisplayName("Строка из одного пробела -> список с одним полем-пробелом")
        void lineWithOnlySpaceShouldResultInOneFieldWithSpace() {
            assertEquals(List.of(" "), parser.parseCsvLine(" "), "Строка из одного пробела");
        }

        @Test
        @DisplayName("Строка из одной запятой -> список с двумя пустыми полями")
        void lineWithOnlyCommaShouldResultInTwoEmptyFields() {
            assertEquals(List.of("", ""), parser.parseCsvLine(","), "Строка из одной запятой");
        }

        @Test
        @DisplayName("Только кавычки \"abc\",\"def\" -> список из [abc, def]")
        void onlyQuotedFields() {
            assertEquals(List.of("abc", "def"), parser.parseCsvLine("\"abc\",\"def\""));
        }

        @Test
        @DisplayName("Экранированные кавычки в начале и конце поля: \"\"\"quoted\"\"\" -> [\"quoted\"]")
        void escapedQuotesAtStartAndEndOfField() {
            assertEquals(List.of("\"quoted\""), parser.parseCsvLine("\"\"\"quoted\"\"\""));
        }
    }
}
