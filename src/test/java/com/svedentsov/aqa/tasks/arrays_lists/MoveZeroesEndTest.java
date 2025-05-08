package com.svedentsov.aqa.tasks.arrays_lists;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("Тесты для MoveZeroesEnd")
class MoveZeroesEndTest {

    private MoveZeroesEnd mover;

    @BeforeEach
    void setUp() {
        mover = new MoveZeroesEnd();
    }

    // --- Источник данных для тестов ---
    // Формат: исходный массив, ожидаемый массив после перемещения нулей
    static Stream<Arguments> provideArraysForMovingZeroes() {
        return Stream.of(
                Arguments.of(new int[]{0, 1, 0, 3, 12}, new int[]{1, 3, 12, 0, 0}), // Стандартный случай
                Arguments.of(new int[]{0, 0, 1}, new int[]{1, 0, 0}),          // Нули в начале
                Arguments.of(new int[]{1, 2, 3}, new int[]{1, 2, 3}),          // Нет нулей
                Arguments.of(new int[]{1, 0, 2, 0, 0, 3}, new int[]{1, 2, 3, 0, 0, 0}), // Смешанный случай
                Arguments.of(new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}),       // Только нули
                Arguments.of(new int[]{1, 0, 1, 0, 1}, new int[]{1, 1, 1, 0, 0})  // Чередующиеся
        );
    }

    // --- Параметризованный тест для moveZeroes ---
    @ParameterizedTest(name = "Массив: {0} -> Ожидание: {1}")
    @MethodSource("provideArraysForMovingZeroes")
    @DisplayName("Должен перемещать нули в конец, сохраняя порядок остальных")
    void shouldMoveZeroesToEnd(int[] originalArray, int[] expectedArray) {
        // Создаем копию, так как метод модифицирует массив на месте
        int[] arrayToModify = Arrays.copyOf(originalArray, originalArray.length);
        mover.moveZeroes(arrayToModify);
        assertArrayEquals(expectedArray, arrayToModify, "Массив после перемещения нулей не совпадает с ожидаемым");
    }

    // --- Тесты для граничных и особых случаев ---
    @Test
    @DisplayName("Не должен изменять пустой массив")
    void shouldNotChangeEmptyArray() {
        int[] emptyArray = {};
        int[] expectedArray = {};
        mover.moveZeroes(emptyArray); // Метод должен просто вернуться
        assertArrayEquals(expectedArray, emptyArray, "Пустой массив не должен изменяться");
    }

    @Test
    @DisplayName("Не должен изменять массив из одного ненулевого элемента")
    void shouldNotChangeSingleNonZeroElementArray() {
        int[] singleElementArray = {5};
        int[] expectedArray = {5};
        mover.moveZeroes(singleElementArray);
        assertArrayEquals(expectedArray, singleElementArray, "Массив из одного ненулевого элемента не должен изменяться");
    }

    @Test
    @DisplayName("Не должен изменять массив из одного нулевого элемента")
    void shouldNotChangeSingleZeroElementArray() {
        int[] singleElementArray = {0};
        int[] expectedArray = {0};
        mover.moveZeroes(singleElementArray);
        assertArrayEquals(expectedArray, singleElementArray, "Массив из одного нулевого элемента не должен изменяться");
    }

    @Test
    @DisplayName("Не должен выбрасывать исключение для null входа")
    void shouldNotThrowExceptionForNullInput() {
        // Метод внутри проверяет на null и возвращается, поэтому исключения быть не должно
        assertDoesNotThrow(() -> {
            mover.moveZeroes(null);
        }, "Вызов moveZeroes с null не должен приводить к исключению");
    }
}
