package com.svedentsov.aqa.tasks.graphs_matrices;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для GraphPathFinder (Поиск пути в графе)")
class GraphPathFinderTest {

    private GraphPathFinder pathFinder;
    private static Map<Integer, List<Integer>> sampleGraph;

    @BeforeAll
    static void setUpAll() {
        sampleGraph = new HashMap<>();
        sampleGraph.put(0, List.of(1, 2));
        sampleGraph.put(1, List.of(2));
        sampleGraph.put(2, List.of(0, 3)); // Cycle 0-2
        sampleGraph.put(3, List.of(3));    // Self-loop
        sampleGraph.put(4, List.of(5));    // Different component
        sampleGraph.put(5, Collections.emptyList()); // Sink node
        sampleGraph.put(6, Collections.emptyList()); // Isolated node (but present as key)
        // Node 7 is not a key in the graph
    }

    @BeforeEach
    void setUp() {
        pathFinder = new GraphPathFinder();
    }

    static Stream<Arguments> graphTestCases() {
        return Stream.of(
                // startNode, endNode, expectedResult, description
                Arguments.of(0, 3, true, "Путь 0 -> 3 существует (0-2-3)"),
                Arguments.of(3, 0, false, "Путь 3 -> 0 не существует (направленный граф)"),
                Arguments.of(0, 5, false, "Путь 0 -> 5 не существует (разные компоненты)"),
                Arguments.of(4, 5, true, "Путь 4 -> 5 существует"),
                Arguments.of(5, 4, false, "Путь 5 -> 4 не существует"),
                Arguments.of(0, 6, false, "Путь 0 -> 6 не существует (6 изолирован)"),
                Arguments.of(6, 0, false, "Путь 6 -> 0 не существует (6 изолирован)"),
                Arguments.of(0, 0, true, "Путь к себе (0 -> 0)"),
                Arguments.of(3, 3, true, "Путь к себе с петлей (3 -> 3)"),
                Arguments.of(5, 5, true, "Путь к себе для стока (5 -> 5)"),
                Arguments.of(6, 6, true, "Путь к себе для изолированного узла (6 -> 6)"),
                Arguments.of(0, 2, true, "Путь 0 -> 2 (прямой)"),
                Arguments.of(2, 0, true, "Путь 2 -> 0 (цикл)"),
                Arguments.of(1, 3, true, "Путь 1 -> 3 (1-2-3)"),
                Arguments.of(0, 7, false, "Конечный узел 7 не в графе"),
                Arguments.of(7, 0, false, "Начальный узел 7 не в графе"),
                Arguments.of(7, 7, false, "Оба узла (7) не в графе"),
                Arguments.of(8, 8, false, "Узел 8 не в графе, путь к себе"), // Node 8 is not in sampleGraph.keySet()
                Arguments.of(1, 0, true, "Путь 1 -> 0 (1-2-0)")
        );
    }

    @Nested
    @DisplayName("Метод: hasPathBFS")
    class BfsTests {
        @ParameterizedTest(name = "{3}")
        @MethodSource("com.svedentsov.aqa.tasks.graphs_matrices.GraphPathFinderTest#graphTestCases")
        void testHasPathBFS(int startNode, int endNode, boolean expected, String description) {
            assertEquals(expected, pathFinder.hasPathBFS(startNode, endNode, sampleGraph), description);
        }

        @Test
        @DisplayName("BFS: Null граф должен вернуть false")
        void bfs_nullGraph_returnsFalse() {
            assertFalse(pathFinder.hasPathBFS(0, 1, null));
        }

        @Test
        @DisplayName("BFS: Пустой граф должен корректно обрабатываться")
        void bfs_emptyGraph_handlesCorrectly() {
            Map<Integer, List<Integer>> emptyGraph = Collections.emptyMap();
            assertFalse(pathFinder.hasPathBFS(0, 1, emptyGraph), "Путь в пустом графе");
            assertFalse(pathFinder.hasPathBFS(0, 0, emptyGraph), "Путь к себе в пустом графе");
        }

        @Test
        @DisplayName("BFS: Граф с одним узлом, путь к себе")
        void bfs_singleNodeGraph_path_to_self() {
            Map<Integer, List<Integer>> singleNodeGraph = Map.of(1, Collections.emptyList());
            assertTrue(pathFinder.hasPathBFS(1, 1, singleNodeGraph));
            assertFalse(pathFinder.hasPathBFS(1, 2, singleNodeGraph)); // Target not in graph
        }
    }

    @Nested
    @DisplayName("Метод: hasPathDFS")
    class DfsTests {
        @ParameterizedTest(name = "{3}")
        @MethodSource("com.svedentsov.aqa.tasks.graphs_matrices.GraphPathFinderTest#graphTestCases")
        void testHasPathDFS(int startNode, int endNode, boolean expected, String description) {
            assertEquals(expected, pathFinder.hasPathDFS(startNode, endNode, sampleGraph), description);
        }

        @Test
        @DisplayName("DFS: Null граф должен вернуть false")
        void dfs_nullGraph_returnsFalse() {
            assertFalse(pathFinder.hasPathDFS(0, 1, null));
        }

        @Test
        @DisplayName("DFS: Пустой граф должен корректно обрабатываться")
        void dfs_emptyGraph_handlesCorrectly() {
            Map<Integer, List<Integer>> emptyGraph = Collections.emptyMap();
            assertFalse(pathFinder.hasPathDFS(0, 1, emptyGraph), "Путь в пустом графе");
            assertFalse(pathFinder.hasPathDFS(0, 0, emptyGraph), "Путь к себе в пустом графе");
        }

        @Test
        @DisplayName("DFS: Граф с одним узлом, путь к себе")
        void dfs_singleNodeGraph_path_to_self() {
            Map<Integer, List<Integer>> singleNodeGraph = Map.of(1, Collections.emptyList());
            assertTrue(pathFinder.hasPathDFS(1, 1, singleNodeGraph));
            assertFalse(pathFinder.hasPathDFS(1, 2, singleNodeGraph)); // Target not in graph
        }
    }
}
