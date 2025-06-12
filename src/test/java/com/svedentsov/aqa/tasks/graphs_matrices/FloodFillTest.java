package com.svedentsov.aqa.tasks.graphs_matrices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для FloodFill (Заливка области)")
class FloodFillTest {

    private FloodFill floodFiller;

    @BeforeEach
    void setUp() {
        floodFiller = new FloodFill();
    }

    // Вспомогательный метод для глубокого копирования 2D массива int
    private int[][] deepCopy(int[][] original) {
        if (original == null) return null;
        return Arrays.stream(original).map(int[]::clone).toArray(int[][]::new);
    }

    // Вспомогательный метод для сравнения 2D массивов с информативным выводом
    private void assertImageEquals(int[][] expected, int[][] actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || actual == null) {
            fail(message + "\nExpected: " + Arrays.deepToString(expected) + "\nActual  : " + Arrays.deepToString(actual));
        }
        if (expected.length == 0 && actual.length == 0) return; // Оба new int[0][0] или new int[x][0]

        assertTrue(Arrays.deepEquals(expected, actual),
                message + "\nExpected:\n" + imageToString(expected) +
                        "Actual  :\n" + imageToString(actual));
    }

    private String imageToString(int[][] image) {
        if (image == null) return "null";
        if (image.length == 0) return "[] (0 rows)";
        if (image[0] == null && image.length > 0) return "[null row(s)]";
        if (image[0] != null && image[0].length == 0) return "[] (0 cols)";

        StringBuilder sb = new StringBuilder();
        for (int[] row : image) {
            if (row == null) sb.append("  null\n");
            else sb.append("  ").append(Arrays.toString(row)).append("\n");
        }
        return sb.toString().trim();
    }


    static Stream<Arguments> floodFillTestCases() {
        return Stream.of(
                Arguments.of("Пример 1: Стандартная заливка",
                        new int[][]{{1, 1, 1}, {1, 1, 0}, {1, 0, 1}}, 1, 1, 2,
                        new int[][]{{2, 2, 2}, {2, 2, 0}, {2, 0, 1}}),
                Arguments.of("Пример 2: Новый цвет совпадает с исходным",
                        new int[][]{{0, 0, 0}, {0, 1, 1}, {0, 0, 0}}, 1, 1, 1,
                        new int[][]{{0, 0, 0}, {0, 1, 1}, {0, 0, 0}}), // Без изменений
                Arguments.of("Пример 3: Заливка всего изображения",
                        new int[][]{{0, 0, 0}, {0, 0, 0}}, 0, 0, 5,
                        new int[][]{{5, 5, 5}, {5, 5, 5}}),
                Arguments.of("Пример 4: Заливка не затрагивает другую область",
                        new int[][]{{1, 0, 1}, {0, 1, 0}, {1, 0, 1}}, 1, 1, 9,
                        new int[][]{{1, 0, 1}, {0, 9, 0}, {1, 0, 1}}),
                Arguments.of("Заливка с края",
                        new int[][]{{1, 1, 1}, {1, 0, 1}, {1, 1, 1}}, 0, 0, 5,
                        new int[][]{{5, 5, 5}, {5, 0, 5}, {5, 5, 5}}),
                Arguments.of("Заливка одной точки",
                        new int[][]{{1}}, 0, 0, 2,
                        new int[][]{{2}}),
                Arguments.of("Точка для заливки уже нового цвета",
                        new int[][]{{1, 2, 1}, {1, 2, 1}}, 0, 1, 2,
                        new int[][]{{1, 2, 1}, {1, 2, 1}}),
                Arguments.of("Изображение 1xN",
                        new int[][]{{1, 1, 0, 1, 1}}, 0, 0, 2,
                        new int[][]{{2, 2, 0, 1, 1}}),
                Arguments.of("Изображение Nx1",
                        new int[][]{{1}, {1}, {0}, {1}, {1}}, 0, 0, 2,
                        new int[][]{{2}, {2}, {0}, {1}, {1}})
        );
    }

    @Nested
    @DisplayName("Метод: floodFillRecursiveDFS")
    class DfsTests {
        @ParameterizedTest(name = "{0}")
        @MethodSource("com.svedentsov.aqa.tasks.graphs_matrices.FloodFillTest#floodFillTestCases")
        void testFloodFillRecursiveDFS(String desc, int[][] image, int sr, int sc, int newColor, int[][] expected) {
            int[][] imageCopy = deepCopy(image); // Копируем, т.к. метод изменяет на месте
            int[][] result = floodFiller.floodFillRecursiveDFS(imageCopy, sr, sc, newColor);
            assertImageEquals(expected, result, desc + " (DFS)");
        }

        @Test
        @DisplayName("DFS: Null изображение вызывает NullPointerException")
        void dfs_nullImage_throwsNullPointerException() {
            assertThrows(NullPointerException.class, () -> floodFiller.floodFillRecursiveDFS(null, 0, 0, 1));
        }

        @Test
        @DisplayName("DFS: Пустое изображение (0 строк) возвращается без изменений")
        void dfs_emptyImage0Rows_returnsAsIs() {
            int[][] emptyImage = new int[0][5];
            int[][] result = floodFiller.floodFillRecursiveDFS(emptyImage, 0, 0, 1); // Координаты здесь невалидны, но метод вернет image
            assertSame(emptyImage, result); // Ожидаем тот же пустой массив
            assertEquals(0, result.length);
        }

        @Test
        @DisplayName("DFS: Пустое изображение (0 столбцов) возвращается без изменений")
        void dfs_emptyImage0Cols_returnsAsIs() {
            int[][] emptyImage = new int[3][0];
            int[][] result = floodFiller.floodFillRecursiveDFS(emptyImage, 0, 0, 1);
            assertSame(emptyImage, result);
            if (result.length > 0) assertEquals(0, result[0].length);
            else assertEquals(0, result.length);
        }

        @Test
        @DisplayName("DFS: Координаты за пределами (строка) вызывают IndexOutOfBoundsException")
        void dfs_startRowOutOfBounds_throwsIndexOutOfBoundsException() {
            int[][] image = {{1}};
            assertThrows(IndexOutOfBoundsException.class, () -> floodFiller.floodFillRecursiveDFS(image, 1, 0, 2));
            assertThrows(IndexOutOfBoundsException.class, () -> floodFiller.floodFillRecursiveDFS(image, -1, 0, 2));
        }

        @Test
        @DisplayName("DFS: Координаты за пределами (столбец) вызывают IndexOutOfBoundsException")
        void dfs_startColOutOfBounds_throwsIndexOutOfBoundsException() {
            int[][] image = {{1}};
            assertThrows(IndexOutOfBoundsException.class, () -> floodFiller.floodFillRecursiveDFS(image, 0, 1, 2));
            assertThrows(IndexOutOfBoundsException.class, () -> floodFiller.floodFillRecursiveDFS(image, 0, -1, 2));
        }
    }

    @Nested
    @DisplayName("Метод: floodFillIterativeBFS")
    class BfsTests {
        @ParameterizedTest(name = "{0}")
        @MethodSource("com.svedentsov.aqa.tasks.graphs_matrices.FloodFillTest#floodFillTestCases")
        void testFloodFillIterativeBFS(String desc, int[][] image, int sr, int sc, int newColor, int[][] expected) {
            int[][] imageCopy = deepCopy(image); // Копируем, т.к. метод изменяет на месте
            int[][] result = floodFiller.floodFillIterativeBFS(imageCopy, sr, sc, newColor);
            assertImageEquals(expected, result, desc + " (BFS)");
        }

        @Test
        @DisplayName("BFS: Null изображение вызывает NullPointerException")
        void bfs_nullImage_throwsNullPointerException() {
            assertThrows(NullPointerException.class, () -> floodFiller.floodFillIterativeBFS(null, 0, 0, 1));
        }

        @Test
        @DisplayName("BFS: Пустое изображение (0 строк) возвращается без изменений")
        void bfs_emptyImage0Rows_returnsAsIs() {
            int[][] emptyImage = new int[0][5];
            int[][] result = floodFiller.floodFillIterativeBFS(emptyImage, 0, 0, 1);
            assertSame(emptyImage, result);
            assertEquals(0, result.length);
        }

        @Test
        @DisplayName("BFS: Пустое изображение (0 столбцов) возвращается без изменений")
        void bfs_emptyImage0Cols_returnsAsIs() {
            int[][] emptyImage = new int[3][0];
            int[][] result = floodFiller.floodFillIterativeBFS(emptyImage, 0, 0, 1);
            assertSame(emptyImage, result);
            if (result.length > 0) assertEquals(0, result[0].length);
            else assertEquals(0, result.length);
        }

        @Test
        @DisplayName("BFS: Координаты за пределами (строка) вызывают IndexOutOfBoundsException")
        void bfs_startRowOutOfBounds_throwsIndexOutOfBoundsException() {
            int[][] image = {{1}};
            assertThrows(IndexOutOfBoundsException.class, () -> floodFiller.floodFillIterativeBFS(image, 1, 0, 2));
            assertThrows(IndexOutOfBoundsException.class, () -> floodFiller.floodFillIterativeBFS(image, -1, 0, 2));
        }

        @Test
        @DisplayName("BFS: Координаты за пределами (столбец) вызывают IndexOutOfBoundsException")
        void bfs_startColOutOfBounds_throwsIndexOutOfBoundsException() {
            int[][] image = {{1}};
            assertThrows(IndexOutOfBoundsException.class, () -> floodFiller.floodFillIterativeBFS(image, 0, 1, 2));
            assertThrows(IndexOutOfBoundsException.class, () -> floodFiller.floodFillIterativeBFS(image, 0, -1, 2));
        }
    }
}
