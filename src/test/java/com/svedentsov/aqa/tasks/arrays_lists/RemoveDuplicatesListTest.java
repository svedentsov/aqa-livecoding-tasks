package com.svedentsov.aqa.tasks.arrays_lists;

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

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для RemoveDuplicatesList")
class RemoveDuplicatesListTest {

    private RemoveDuplicatesList remover;

    @BeforeEach
    void setUp() {
        remover = new RemoveDuplicatesList();
    }

    static Stream<Arguments> provideListsForDuplicateRemoval() {
        // Используем Arrays.asList для списков с null
        return Stream.of(
                Arguments.of(List.of("a", "b", "a", "c", "b", "a"), List.of("a", "b", "c")),
                Arguments.of(List.of("java", "python", "java", "c++", "python"), List.of("java", "python", "c++")),
                Arguments.of(List.of("a", "a", "a"), List.of("a")),
                Arguments.of(Collections.emptyList(), Collections.emptyList()), // Пустой список
                Arguments.of(Arrays.asList("apple", null, "banana", "apple", null), Arrays.asList("apple", null, "banana")), // Список с null
                Arguments.of(List.of("Case", "case", "CASE"), List.of("Case", "case", "CASE")), // Разный регистр
                Arguments.of(List.of(" lead ", "lead", " lead "), List.of(" lead ", "lead")), // С пробелами
                Arguments.of(List.of("a"), List.of("a")), // Один элемент
                Arguments.of(Arrays.asList(null, null, null), Collections.singletonList(null)), // Только null элементы
                Arguments.of(Arrays.asList(null, "a", null, "a"), Arrays.asList(null, "a")) // Null и другие элементы
        );
    }

    @Nested
    @DisplayName("Тесты для метода removeDuplicates (LinkedHashSet)")
    class RemoveDuplicatesSetTest {

        @ParameterizedTest(name = "Вход: {0}, Ожидание: {1}")
        @MethodSource("com.svedentsov.aqa.tasks.arrays_lists.RemoveDuplicatesListTest#provideListsForDuplicateRemoval")
        void shouldRemoveDuplicatesAndPreserveOrder(List<String> inputList, List<String> expectedList) {
            List<String> actualList = remover.removeDuplicates(inputList);
            assertEquals(expectedList, actualList, "Список после удаления дубликатов должен совпадать и сохранять порядок");
        }

        @Test // Используем @Test для null, так как @MethodSource не передает null напрямую
        @DisplayName("Должен возвращать пустой список для null входа")
        void shouldReturnEmptyListForNullInput() {
            List<String> result = remover.removeDuplicates(null);
            assertNotNull(result, "Результат не должен быть null");
            assertTrue(result.isEmpty(), "Список должен быть пустым для null входа");
        }
    }

    @Nested
    @DisplayName("Тесты для метода removeDuplicatesStream (Stream API)")
    class RemoveDuplicatesStreamTest {

        @ParameterizedTest(name = "Вход: {0}, Ожидание: {1}")
        @MethodSource("com.svedentsov.aqa.tasks.arrays_lists.RemoveDuplicatesListTest#provideListsForDuplicateRemoval")
        void shouldRemoveDuplicatesAndPreserveOrderUsingStream(List<String> inputList, List<String> expectedList) {
            List<String> actualList = remover.removeDuplicatesStream(inputList);
            assertEquals(expectedList, actualList, "Список после удаления дубликатов (Stream) должен совпадать и сохранять порядок");
        }

        @Test
        @DisplayName("Должен возвращать пустой список для null входа")
        void shouldReturnEmptyListForNullInputUsingStream() {
            List<String> result = remover.removeDuplicatesStream(null);
            assertNotNull(result, "Результат (Stream) не должен быть null");
            assertTrue(result.isEmpty(), "Список (Stream) должен быть пустым для null входа");
        }
    }
}
