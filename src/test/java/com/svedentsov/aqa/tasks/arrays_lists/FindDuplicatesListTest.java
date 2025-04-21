package com.svedentsov.aqa.tasks.arrays_lists;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для FindDuplicatesList")
class FindDuplicatesListTest {

    private FindDuplicatesList finder;

    @BeforeEach
    void setUp() {
        finder = new FindDuplicatesList();
    }

    static Stream<Arguments> provideListsForDuplicateFinding() {
        return Stream.of(
                // Стандартный случай
                Arguments.of(List.of(1, 2, 3, 2, 4, 5, 1, 5), Set.of(1, 2, 5)),
                // Список без дубликатов
                Arguments.of(List.of(1, 2, 3, 4), Set.of()),
                // Список, где все элементы - дубликаты одного числа
                Arguments.of(List.of(1, 1, 1, 1), Set.of(1)),
                // Список с null значениями и дубликатами (используем Arrays.asList для изменяемого списка с null)
                Arguments.of(Arrays.asList(1, null, 2, null, 1, 2), Set.of(1, 2)),
                // Список с null значениями без дубликатов чисел
                Arguments.of(Arrays.asList(1, null, 2, null, 3), Set.of()),
                // Пустой список
                Arguments.of(Collections.emptyList(), Set.of()),
                // Список только из null
                Arguments.of(Arrays.asList(null, null, null), Set.of()),
                // Список с одним элементом
                Arguments.of(List.of(10), Set.of()),
                // Список с несколькими одинаковыми дубликатами
                Arguments.of(List.of(5, 5, 5, 5, 5), Set.of(5)),
                // Список с разными дубликатами, встречающимися много раз
                Arguments.of(List.of(1, 2, 1, 2, 1, 2, 3), Set.of(1, 2))
        );
    }

    @Nested
    @DisplayName("Тесты для метода findDuplicates (HashSet)")
    class FindDuplicatesHashSetImplTest {

        @ParameterizedTest(name = "Вход: {0}, Ожидаемые дубликаты: {1}")
        @MethodSource("com.svedentsov.aqa.tasks.arrays_lists.FindDuplicatesListTest#provideListsForDuplicateFinding")
        void shouldFindDuplicatesCorrectly(List<Integer> inputList, Set<Integer> expectedDuplicates) {
            List<Integer> actualDuplicatesList = finder.findDuplicates(inputList);
            // Преобразуем результат в Set для сравнения без учета порядка
            Set<Integer> actualDuplicatesSet = new HashSet<>(actualDuplicatesList);
            assertEquals(expectedDuplicates, actualDuplicatesSet, "Набор дубликатов должен совпадать");
        }

        @Test
        @DisplayName("Должен возвращать пустой список для null входа")
        void shouldReturnEmptyListForNullInput() {
            List<Integer> result = finder.findDuplicates(null);
            assertNotNull(result, "Результат не должен быть null");
            assertTrue(result.isEmpty(), "Список должен быть пустым для null входа");
        }
    }

    @Nested
    @DisplayName("Тесты для метода findDuplicatesStream (Stream API)")
    class FindDuplicatesStreamImplTest {

        @ParameterizedTest(name = "Вход: {0}, Ожидаемые дубликаты: {1}")
        @MethodSource("com.svedentsov.aqa.tasks.arrays_lists.FindDuplicatesListTest#provideListsForDuplicateFinding")
        void shouldFindDuplicatesCorrectlyUsingStream(List<Integer> inputList, Set<Integer> expectedDuplicates) {
            List<Integer> actualDuplicatesList = finder.findDuplicatesStream(inputList);
            // Преобразуем результат в Set для сравнения без учета порядка
            Set<Integer> actualDuplicatesSet = new HashSet<>(actualDuplicatesList);
            assertEquals(expectedDuplicates, actualDuplicatesSet, "Набор дубликатов (Stream) должен совпадать");
        }

        @Test
        @DisplayName("Должен возвращать пустой список для null входа")
        void shouldReturnEmptyListForNullInputUsingStream() {
            List<Integer> result = finder.findDuplicatesStream(null);
            assertNotNull(result, "Результат (Stream) не должен быть null");
            assertTrue(result.isEmpty(), "Список (Stream) должен быть пустым для null входа");
        }
    }
}
