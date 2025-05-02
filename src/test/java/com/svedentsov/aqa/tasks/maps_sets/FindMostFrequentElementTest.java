package com.svedentsov.aqa.tasks.maps_sets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для FindMostFrequentElement")
class FindMostFrequentElementTest {

    private FindMostFrequentElement finder;

    @BeforeEach
    void setUp() {
        finder = new FindMostFrequentElement();
    }

    // --- Источник данных для тестов ---
    // Формат: массив nums, МНОЖЕСТВО возможных ожидаемых результатов (Set<Integer>)
    // Это нужно для случаев, когда несколько элементов имеют одинаковую макс. частоту
    static Stream<Arguments> provideArraysForFrequencyTest() {
        return Stream.of(
                // Уникальный самый частый элемент
                Arguments.of(new int[]{1, 3, 2, 1, 4, 1, 3}, Set.of(1)),
                Arguments.of(new int[]{1, 2, 3, 2, 3, 2, 3, 3}, Set.of(3)),
                Arguments.of(new int[]{7, 7, 7}, Set.of(7)), // Все одинаковые
                Arguments.of(new int[]{5}, Set.of(5)),       // Один элемент
                Arguments.of(new int[]{-1, -1, 0, 0, 0, 2}, Set.of(0)), // С нулем и отрицательными
                Arguments.of(new int[]{10, 20, 10, 20, 10}, Set.of(10)),

                // Несколько элементов с одинаковой максимальной частотой
                Arguments.of(new int[]{1, 2, 3, 4, 5}, Set.of(1, 2, 3, 4, 5)), // Все уникальные (частота 1)
                Arguments.of(new int[]{1, 1, 2, 2}, Set.of(1, 2)),          // Два одинаково частых
                Arguments.of(new int[]{1, 1, 2, 2, 3}, Set.of(1, 2))           // Два частых, один редкий
        );
    }

    // --- Параметризованный тест для findMostFrequentElement ---
    @ParameterizedTest(name = "Массив: {0} -> Ожидание: один из {1}")
    @MethodSource("provideArraysForFrequencyTest")
    @DisplayName("Должен находить один из самых часто встречающихся элементов")
    void shouldFindMostFrequentElement(int[] numbers, Set<Integer> expectedPossibleResults) {
        int actualResult = finder.findMostFrequentElement(numbers);
        // Проверяем, что возвращенный результат входит в множество ожидаемых
        assertTrue(expectedPossibleResults.contains(actualResult),
                "Результат " + actualResult + " не входит в ожидаемое множество " + expectedPossibleResults);
    }

    // --- Тесты для некорректных входов (null или пустой массив) ---
    @ParameterizedTest(name = "Вход: {0} -> Ожидание: IllegalArgumentException")
    @NullAndEmptySource // Проверяет null и new int[]{}
    @DisplayName("Должен выбросить IllegalArgumentException для null или пустого массива")
    void shouldThrowIllegalArgumentExceptionForNullOrEmptyArray(int[] numbers) {
        String inputType = (numbers == null) ? "null" : "пустой массив";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            finder.findMostFrequentElement(numbers);
        });
        assertEquals("Input array cannot be null or empty.", exception.getMessage(),
                "Неверное сообщение об ошибке для " + inputType);
    }
}
