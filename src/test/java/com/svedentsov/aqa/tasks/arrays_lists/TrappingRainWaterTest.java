package com.svedentsov.aqa.tasks.arrays_lists;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты для TrappingRainWater (Подсчет дождевой воды)")
class TrappingRainWaterTest {

    private TrappingRainWater solver;

    @BeforeEach
    void setUp() {
        solver = new TrappingRainWater();
    }

    static Stream<Arguments> rainWaterTestCases() {
        return Stream.of(
                Arguments.of(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}, 6, "Пример из задания"),
                Arguments.of(new int[]{4, 2, 0, 3, 2, 5}, 9, "Впадины разной глубины"),
                Arguments.of(new int[]{4, 2, 3}, 1, "Простая впадина"),
                Arguments.of(new int[]{1, 0, 1}, 1, "Симметричная впадина U"),
                Arguments.of(new int[]{3, 0, 0, 2, 0, 4}, 10, "Несколько впадин"),
                Arguments.of(new int[]{1, 2, 3, 4, 5}, 0, "Возрастающая последовательность"),
                Arguments.of(new int[]{5, 4, 3, 2, 1}, 0, "Убывающая последовательность"),
                Arguments.of(new int[]{2, 0, 2}, 2, "Простой U-образный случай"),
                Arguments.of(new int[]{}, 0, "Пустой массив"),
                Arguments.of(new int[]{5}, 0, "Один элемент"),
                Arguments.of(new int[]{5, 5}, 0, "Два элемента одинаковой высоты"),
                Arguments.of(new int[]{5, 8}, 0, "Два элемента разной высоты"),
                Arguments.of(new int[]{5, 5, 1, 7, 1, 1, 5, 2, 7, 6}, 23, "Более сложный случай"),
                Arguments.of(null, 0, "Null массив")
        );
    }

    @Nested
    @DisplayName("Метод: trapRainWaterTwoPointers (Два указателя)")
    class TwoPointersSolutionTests {
        @ParameterizedTest(name = "{2}")
        @MethodSource("com.svedentsov.aqa.tasks.arrays_lists.TrappingRainWaterTest#rainWaterTestCases")
        void testTrapRainWaterTwoPointers(int[] height, int expectedWater, String description) {
            assertEquals(expectedWater, solver.trapRainWaterTwoPointers(height), description);
        }
    }

    @Nested
    @DisplayName("Метод: trapRainWaterExtraSpace (Дополнительное пространство)")
    class ExtraSpaceSolutionTests {
        @ParameterizedTest(name = "{2}")
        @MethodSource("com.svedentsov.aqa.tasks.arrays_lists.TrappingRainWaterTest#rainWaterTestCases")
        void testTrapRainWaterExtraSpace(int[] height, int expectedWater, String description) {
            assertEquals(expectedWater, solver.trapRainWaterExtraSpace(height), description);
        }
    }
}
