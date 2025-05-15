package com.svedentsov.aqa.tasks.graphs_matrices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты для NumberOfIslands")
class NumberOfIslandsTest {

    private NumberOfIslands islandCounter;

    @BeforeEach
    void setUp() {
        islandCounter = new NumberOfIslands();
    }

    // Хелпер для глубокого копирования 2D массива char
    // Важно, так как методы DFS/BFS изменяют входной массив
    private char[][] copyGrid(char[][] originalGrid) {
        if (originalGrid == null) {
            return null;
        }
        if (originalGrid.length == 0) {
            return new char[0][]; // Пустой массив строк
        }
        // Если первая строка null, и метод SUT это ожидает/обрабатывает
        // if (originalGrid[0] == null && originalGrid.length > 0) {
        //     return new char[originalGrid.length][]; // Массив строк, где каждая строка null
        // }

        // Стандартное копирование для не-jagged массивов
        return Arrays.stream(originalGrid)
                .map(row -> (row == null) ? null : row.clone()) // Клонируем каждую строку
                .toArray(char[][]::new);
    }


    // --- Источник данных для тестов ---
    // Формат: исходная сетка, ожидаемое количество островов, описание для теста
    static Stream<Arguments> provideGridsAndExpectedCounts() {
        return Stream.of(
                Arguments.of(new char[][]{
                        {'1', '1', '0', '0', '0'},
                        {'1', '1', '0', '0', '0'},
                        {'0', '0', '1', '0', '0'},
                        {'0', '0', '0', '1', '1'}
                }, 3, "Пример из описания"),
                Arguments.of(new char[][]{
                        {'1', '1', '1', '1', '0'},
                        {'1', '1', '0', '1', '0'},
                        {'1', '1', '0', '0', '0'},
                        {'0', '0', '0', '0', '0'}
                }, 1, "Один большой остров"),
                Arguments.of(new char[][]{
                        {'1', '0', '1'},
                        {'0', '1', '0'},
                        {'1', '0', '1'}
                }, 5, "Шахматный порядок (5 островов)"),
                Arguments.of(new char[][]{
                        {'1', '0', '0', '1'},
                        {'0', '0', '0', '0'},
                        {'0', '0', '0', '0'},
                        {'1', '0', '0', '1'}
                }, 4, "Четыре угловых острова"),
                Arguments.of(new char[][]{
                        {'1', '1', '1'},
                        {'0', '1', '0'},
                        {'1', '1', '1'}
                }, 1, "Н-образный остров"),
                Arguments.of(new char[][]{{'1'}}, 1, "Один остров (1x1)"),
                Arguments.of(new char[][]{{'0'}}, 0, "Одна вода (1x1)"),
                Arguments.of(new char[][]{{'0', '0'}, {'0', '0'}}, 0, "Вся вода"),
                Arguments.of(new char[][]{}, 0, "Пустая сетка (0 строк)"),
                Arguments.of(new char[][]{{}}, 0, "Сетка с одной пустой строкой (0 столбцов)"),
                Arguments.of(null, 0, "Null сетка")
                // Можно добавить тест с grid[0] == null, если это валидный сценарий по условиям
                // Arguments.of(new char[1][], 0, "Сетка с null строкой") // grid[0] is null
        );
    }

    @Nested
    @DisplayName("Метод countIslandsDFS")
    class DfsTests {
        @ParameterizedTest(name = "{2} -> Ожидание: {1}")
        @MethodSource("com.svedentsov.aqa.tasks.graphs_matrices.NumberOfIslandsTest#provideGridsAndExpectedCounts")
        void shouldCountIslandsCorrectlyUsingDFS(char[][] originalGrid, int expectedCount, String description) {
            char[][] gridCopy = copyGrid(originalGrid); // Копируем, так как DFS изменяет grid
            assertEquals(expectedCount, islandCounter.countIslandsDFS(gridCopy),
                    "DFS: " + description);
        }
    }

    @Nested
    @DisplayName("Метод countIslandsBFS")
    class BfsTests {
        @ParameterizedTest(name = "{2} -> Ожидание: {1}")
        @MethodSource("com.svedentsov.aqa.tasks.graphs_matrices.NumberOfIslandsTest#provideGridsAndExpectedCounts")
        void shouldCountIslandsCorrectlyUsingBFS(char[][] originalGrid, int expectedCount, String description) {
            char[][] gridCopy = copyGrid(originalGrid); // Копируем, так как BFS изменяет grid
            assertEquals(expectedCount, islandCounter.countIslandsBFS(gridCopy),
                    "BFS: " + description);
        }
    }
}
