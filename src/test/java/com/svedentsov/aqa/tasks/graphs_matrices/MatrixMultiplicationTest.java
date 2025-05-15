package com.svedentsov.aqa.tasks.graphs_matrices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для MatrixMultiplication")
class MatrixMultiplicationTest {

    private MatrixMultiplication multiplier;

    @BeforeEach
    void setUp() {
        multiplier = new MatrixMultiplication();
    }

    // Хелпер для сравнения 2D массивов
    private void assertMatricesEqual(int[][] expected, int[][] actual, String message) {
        assertNotNull(actual, message + " - actual matrix should not be null");
        assertEquals(expected.length, actual.length, message + " - number of rows mismatch");
        for (int i = 0; i < expected.length; i++) {
            assertArrayEquals(expected[i], actual[i], message + " - row " + i + " mismatches");
        }
    }

    // --- Источник данных для валидных умножений ---
    static Stream<Arguments> provideValidMatrices() {
        return Stream.of(
                Arguments.of( // 2x2 * 2x2
                        new int[][]{{1, 2}, {3, 4}},
                        new int[][]{{2, 0}, {1, 2}},
                        new int[][]{{4, 4}, {10, 8}},
                        "2x2 Example"
                ),
                Arguments.of( // 3x3 * 3x3
                        new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}},
                        new int[][]{{9, 8, 7}, {6, 5, 4}, {3, 2, 1}},
                        new int[][]{{30, 24, 18}, {84, 69, 54}, {138, 114, 90}},
                        "3x3 Example"
                ),
                Arguments.of( // 2x3 * 3x2 -> 2x2
                        new int[][]{{1, 2, 3}, {4, 5, 6}},
                        new int[][]{{7, 8}, {9, 10}, {11, 12}},
                        new int[][]{{58, 64}, {139, 154}},
                        "Non-Square Example (2x3 * 3x2)"
                ),
                Arguments.of( // 1x3 * 3x1 -> 1x1
                        new int[][]{{1, 2, 3}},
                        new int[][]{{4}, {5}, {6}},
                        new int[][]{{32}},
                        "1x3 * 3x1 Example"
                ),
                Arguments.of( // Умножение на единичную матрицу
                        new int[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}},
                        new int[][]{{9, 8, 7}, {6, 5, 4}, {3, 2, 1}},
                        new int[][]{{9, 8, 7}, {6, 5, 4}, {3, 2, 1}},
                        "Identity Matrix Example"
                ),
                Arguments.of( // Матрица с нулями
                        new int[][]{{1, 2}, {3, 0}},
                        new int[][]{{0, 1}, {0, 1}},
                        new int[][]{{0, 3}, {0, 3}},
                        "Matrix with zeroes"
                ),
                Arguments.of( // Матрица с отрицательными числами
                        new int[][]{{-1, 2}, {3, -4}},
                        new int[][]{{2, -0}, {-1, 2}},
                        new int[][]{{-4, 4}, {10, -8}},
                        "Matrix with negative numbers"
                )
        );
    }

    // --- Источник данных для несовместимых матриц (IllegalArgumentException) ---
    static Stream<Arguments> provideIncompatibleMatrices() {
        return Stream.of(
                Arguments.of(new int[][]{{1, 2}, {3, 4}}, new int[][]{{1}, {2}, {3}}, "2x2 * 3x1 (colsA != rowsB)"), // colsA = 2, rowsB = 3
                Arguments.of(new int[][]{{1, 2, 3}}, new int[][]{{1, 2}}, "1x3 * 1x2 (colsA != rowsB)") // colsA = 3, rowsB = 1
        );
    }

    // --- Источник данных для невалидных матриц (null, empty, non-rectangular) ---
    static Stream<Arguments> provideInvalidMatrixStructures() {
        return Stream.of(
                Arguments.of(null, new int[][]{{1}}, "Null Matrix A"),
                Arguments.of(new int[][]{{1}}, null, "Null Matrix B"),
                Arguments.of(new int[0][0], new int[][]{{1}}, "Empty Matrix A (0 rows)"), // new int[0][] also valid
                Arguments.of(new int[][]{{1}}, new int[0][0], "Empty Matrix B (0 rows)"),
                Arguments.of(new int[][]{new int[0]}, new int[][]{{1}}, "Matrix A with 0 cols"),
                Arguments.of(new int[][]{{1}}, new int[][]{new int[0]}, "Matrix B with 0 cols"),
                Arguments.of(new int[][]{{1, 2}, {3}}, new int[][]{{1},{1}}, "Non-Rectangular Matrix A"),
                Arguments.of(new int[][]{{1},{1}}, new int[][]{{1, 2}, {3}}, "Non-Rectangular Matrix B"),
                Arguments.of(new int[][]{{1,2}, null}, new int[][]{{1},{1}}, "Matrix A with null row")
        );
    }


    @Nested
    @DisplayName("Валидное умножение матриц")
    class ValidMultiplicationTests {
        @ParameterizedTest(name = "{3}")
        @MethodSource("com.svedentsov.aqa.tasks.graphs_matrices.MatrixMultiplicationTest#provideValidMatrices")
        void shouldMultiplyMatricesCorrectly(int[][] matrixA, int[][] matrixB, int[][] expectedResult, String description) {
            int[][] actualResult = multiplier.multiplyMatrices(matrixA, matrixB);
            assertMatricesEqual(expectedResult, actualResult, description);
        }
    }

    @Nested
    @DisplayName("Невалидные входные данные для умножения")
    class InvalidInputTests {
        @ParameterizedTest(name = "{2}")
        @MethodSource("com.svedentsov.aqa.tasks.graphs_matrices.MatrixMultiplicationTest#provideIncompatibleMatrices")
        void shouldThrowIllegalArgumentExceptionForIncompatibleDimensions(int[][] matrixA, int[][] matrixB, String description) {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
                multiplier.multiplyMatrices(matrixA, matrixB);
            }, description);
            assertTrue(ex.getMessage().contains("incompatible for multiplication"));
        }

        @ParameterizedTest(name = "{2}")
        @MethodSource("com.svedentsov.aqa.tasks.graphs_matrices.MatrixMultiplicationTest#provideInvalidMatrixStructures")
        void shouldThrowIllegalArgumentExceptionForInvalidMatrixStructure(int[][] matrixA, int[][] matrixB, String description) {
            assertThrows(IllegalArgumentException.class, () -> {
                multiplier.multiplyMatrices(matrixA, matrixB);
            }, description);
        }
    }

    @Nested
    @DisplayName("Проверка переполнения Integer")
    class OverflowTests {
        @Test
        @DisplayName("Должен выбросить ArithmeticException при переполнении int в результате")
        void shouldThrowArithmeticExceptionOnOverflow() {
            int[][] matrixA = {{Integer.MAX_VALUE / 2 + 10, 1}}; // 1x2
            int[][] matrixB = {{2}, {0}};                       // 2x1
            // Resulting element: (MAX_VALUE/2 + 10) * 2 + 1*0 = MAX_VALUE + 20 --> overflow for int

            ArithmeticException ex = assertThrows(ArithmeticException.class, () -> {
                multiplier.multiplyMatrices(matrixA, matrixB);
            });
            assertTrue(ex.getMessage().contains("Integer overflow"));
        }

        @Test
        @DisplayName("Не должен выбрасывать ArithmeticException, если сумма умещается в long, а затем в int")
        void shouldNotThrowWhenSumFitsLongThenInt() {
            int[][] matrixA = {{100000, 100000}}; // 1x2
            int[][] matrixB = {{10000}, {10000}};  // 2x1
            // (100000*10000) + (100000*10000) = 10^9 + 10^9 = 2*10^9. This fits in int.
            // Max int is approx 2.1 * 10^9.
            int[][] expected = {{2000000000}};

            assertDoesNotThrow(() -> {
                int[][] actual = multiplier.multiplyMatrices(matrixA, matrixB);
                assertMatricesEqual(expected, actual, "Большие числа, но сумма умещается в int");
            });
        }
    }
}
