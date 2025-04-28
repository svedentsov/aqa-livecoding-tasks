package com.svedentsov.aqa.tasks.arrays_lists;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Тесты для FindSecondLargest")
class FindSecondLargestTest {

    private FindSecondLargest finder;

    @BeforeEach
    void setUp() {
        finder = new FindSecondLargest();
    }

    // --- Источник данных для тестов ---
    // Формат: массив int[], ожидаемый результат (второй по величине или MIN_VALUE)
    static Stream<Arguments> provideArraysForSecondLargest() {
        return Stream.of(
                Arguments.of(new int[]{1, 5, 2, 9, 3, 9}, 5), // Стандартный случай
                Arguments.of(new int[]{10, 2, 8, 10, 5}, 8), // Дубликат максимума
                Arguments.of(new int[]{3, 3, 3}, Integer.MIN_VALUE), // Все одинаковые
                Arguments.of(new int[]{5, 1}, 1), // Два разных элемента
                Arguments.of(new int[]{1, 5}, 1), // Два разных элемента (другой порядок)
                Arguments.of(new int[]{5, 5}, Integer.MIN_VALUE), // Два одинаковых элемента
                Arguments.of(new int[]{Integer.MAX_VALUE, 1, Integer.MAX_VALUE}, 1), // Граничные значения 1
                Arguments.of(new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE}, Integer.MIN_VALUE), // Все MIN_VALUE
                Arguments.of(new int[]{1, Integer.MIN_VALUE}, Integer.MIN_VALUE), // Один элемент и MIN_VALUE
                Arguments.of(new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE}, Integer.MIN_VALUE), // MAX и MIN
                Arguments.of(new int[]{1, 2, Integer.MIN_VALUE}, 1), // Три элемента с MIN_VALUE
                Arguments.of(new int[]{0, -1, Integer.MIN_VALUE}, -1), // С нулем и MIN_VALUE
                Arguments.of(new int[]{-1, -2, -3}, -2), // Все отрицательные
                Arguments.of(new int[]{-3, -2, -1}, -2), // Все отрицательные (отсорт.)
                Arguments.of(new int[]{100, 200, 50}, 100), // Еще стандартный
                Arguments.of(new int[]{10, 10, 9}, 9) // Максимум дублируется, есть меньший
        );
    }

    // --- Параметризованный тест для валидных массивов (>= 1 элемента) ---
    @ParameterizedTest(name = "Массив: {0} -> Ожидание: {1}")
    @MethodSource("provideArraysForSecondLargest")
    @DisplayName("Должен находить второй по величине элемент или возвращать MIN_VALUE")
    void shouldFindSecondLargestCorrectly(int[] numbers, int expectedResult) {
        assertEquals(expectedResult, finder.findSecondLargest(numbers));
    }

    // --- Тест для массива из одного элемента ---
    @Test
    @DisplayName("Должен возвращать MIN_VALUE для массива из одного элемента")
    void shouldReturnMinValueForSingleElementArray() {
        assertEquals(Integer.MIN_VALUE, finder.findSecondLargest(new int[]{5}),
                "Для массива с одним элементом ожидается Integer.MIN_VALUE");
    }

    // --- Тесты для некорректных входов (null или пустой массив) ---
    @ParameterizedTest(name = "Вход: {0} -> Ожидание: IllegalArgumentException")
    @NullAndEmptySource // Проверяет null и new int[]{}
    @DisplayName("Должен выбросить IllegalArgumentException для null или пустого массива")
    void shouldThrowIllegalArgumentExceptionForNullOrEmptyArray(int[] numbers) {
        String inputType = (numbers == null) ? "null" : "пустой массив";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            finder.findSecondLargest(numbers);
        });
        assertEquals("Input array cannot be null or empty.", exception.getMessage(),
                "Неверное сообщение об ошибке для " + inputType);
    }
}
