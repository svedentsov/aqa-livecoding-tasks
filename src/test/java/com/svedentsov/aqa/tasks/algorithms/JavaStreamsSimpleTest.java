package com.svedentsov.aqa.tasks.algorithms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты для JavaStreamsSimple")
class JavaStreamsSimpleTest {

    private JavaStreamsSimple streamProcessor;

    @BeforeEach
    void setUp() {
        streamProcessor = new JavaStreamsSimple();
    }

    @Nested
    @DisplayName("Тесты для filterAndTransformStrings")
    class FilterAndTransformStringsTests {

        static Stream<Arguments> stringListTestCases() {
            return Stream.of(
                    Arguments.of(List.of("apple", "banana", "apricot", "cherry", "kiwi"), List.of("APPLE", "APRICOT"), "Пример из задания"),
                    Arguments.of(Arrays.asList("Apple", "Banana", "Apricot", null, "Cherry", "Avocado", "ant", " B", "a"), List.of("APPLE", "APRICOT", "AVOCADO", "ANT", "A"), "Смешанный регистр, null, короткие строки"),
                    Arguments.of(List.of("banana", "cherry", "kiwi"), Collections.emptyList(), "Нет строк на 'a'"),
                    Arguments.of(List.of(" Art", "  ant", "apply "), List.of(" ART", "  ANT", "APPLY "), "Строки с пробелами, начинающиеся на 'a' (пробелы сохраняются)"),
                    Arguments.of(Collections.emptyList(), Collections.emptyList(), "Пустой список"),
                    Arguments.of(Arrays.asList(null, null, null), Collections.emptyList(), "Список только из null элементов"),
                    Arguments.of(List.of("application", "Aardvark", "Amazing"), List.of("APPLICATION", "AARDVARK", "AMAZING"), "Все строки подходят")
            );
        }

        @ParameterizedTest(name = "{2}")
        @MethodSource("stringListTestCases")
        void testFilterAndTransformStrings(List<String> input, List<String> expected, String description) {
            List<String> actual = streamProcessor.filterAndTransformStrings(input);
            assertEquals(expected, actual, description);
        }

        @Test
        @DisplayName("Обработка null входного списка для строк")
        void filterAndTransformStrings_nullInput_shouldReturnEmptyList() {
            assertEquals(Collections.emptyList(), streamProcessor.filterAndTransformStrings(null));
        }
    }

    @Nested
    @DisplayName("Тесты для sumSquareOfEvens")
    class SumSquareOfEvensTests {

        static Stream<Arguments> integerListTestCases() {
            return Stream.of(
                    Arguments.of(List.of(1, 2, 3, 4, 5), 20L, "Пример из задания (1*1 нечет, 2*2=4, 3*3 нечет, 4*4=16, 5*5 нечет -> 4+16=20)"),
                    Arguments.of(Arrays.asList(1, 2, 3, 4, 5, 6, null, 8, -2, -4), 140L, "Смешанный список с null, отрицательными (2*2=4, 4*4=16, 6*6=36, 8*8=64, (-2)*(-2)=4, (-4)*(-4)=16 -> 4+16+36+64+4+16=140)"),
                    Arguments.of(List.of(1, 3, 5, 7, Integer.MAX_VALUE), 0L, "Только нечетные числа"),
                    Arguments.of(List.of(0, 2, 4), 20L, "Включая ноль (0*0=0, 2*2=4, 4*4=16 -> 0+4+16=20)"),
                    Arguments.of(Collections.emptyList(), 0L, "Пустой список"),
                    Arguments.of(Arrays.asList(null, null, null), 0L, "Список только из null элементов"),
                    Arguments.of(List.of(60000, 1), 3600000000L, "Большое четное число (проверка long)") // 60000*60000 = 3,600,000,000
            );
        }

        @ParameterizedTest(name = "{2}")
        @MethodSource("integerListTestCases")
        void testSumSquareOfEvens(List<Integer> input, long expected, String description) {
            long actual = streamProcessor.sumSquareOfEvens(input);
            assertEquals(expected, actual, description);
        }

        @Test
        @DisplayName("Обработка null входного списка для чисел")
        void sumSquareOfEvens_nullInput_shouldReturnZero() {
            assertEquals(0L, streamProcessor.sumSquareOfEvens(null));
        }
    }
}
