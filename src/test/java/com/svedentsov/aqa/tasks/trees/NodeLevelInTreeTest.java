package com.svedentsov.aqa.tasks.trees;

import com.svedentsov.aqa.tasks.trees.NodeLevelInTree.TreeNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты для NodeLevelInTree (Уровень узла в бинарном дереве)")
class NodeLevelInTreeTest {

    private NodeLevelInTree levelFinder;
    private static TreeNode sampleRoot;
    private static TreeNode singleNodeTree;
    private static TreeNode rightSkewedTree;
    private static TreeNode leftSkewedTree;


    @BeforeAll
    static void setUpAll() {
        // Создаем тестовое дерево:
        //      3       (Уровень 1)
        //     / \
        //    9   20    (Уровень 2)
        //       /  \
        //      15   7  (Уровень 3)
        //     /
        //    10       (Уровень 4)
        sampleRoot = new TreeNode(3,
                new TreeNode(9),
                new TreeNode(20,
                        new TreeNode(15, new TreeNode(10), null),
                        new TreeNode(7)
                )
        );
        singleNodeTree = new TreeNode(42);
        // 1 -> 2 -> 3
        rightSkewedTree = new TreeNode(1, null, new TreeNode(2, null, new TreeNode(3)));
        // 3 -> 2 -> 1
        leftSkewedTree = new TreeNode(3, new TreeNode(2, new TreeNode(1), null), null);
    }


    @BeforeEach
    void setUp() {
        levelFinder = new NodeLevelInTree();
    }

    // Источник данных для тестов
    static Stream<Arguments> treeTestCases() {
        return Stream.of(
                // TreeNode root, int keyToFind, int expectedLevel, String description
                Arguments.of(sampleRoot, 3, 1, "Корень дерева"),
                Arguments.of(sampleRoot, 9, 2, "Левый ребенок корня"),
                Arguments.of(sampleRoot, 20, 2, "Правый ребенок корня"),
                Arguments.of(sampleRoot, 15, 3, "Узел на уровне 3 (левый ребенок 20)"),
                Arguments.of(sampleRoot, 7, 3, "Узел на уровне 3 (правый ребенок 20)"),
                Arguments.of(sampleRoot, 10, 4, "Узел на уровне 4 (самый глубокий)"),
                Arguments.of(sampleRoot, 100, -1, "Несуществующий узел"),
                Arguments.of(sampleRoot, -5, -1, "Другой несуществующий узел"),
                Arguments.of(null, 5, -1, "Пустое дерево (null root)"),
                Arguments.of(singleNodeTree, 42, 1, "Дерево из одного узла, ключ найден"),
                Arguments.of(singleNodeTree, 10, -1, "Дерево из одного узла, ключ не найден"),
                Arguments.of(rightSkewedTree, 1, 1, "Правосторонняя цепочка, ключ 1 (корень)"),
                Arguments.of(rightSkewedTree, 2, 2, "Правосторонняя цепочка, ключ 2"),
                Arguments.of(rightSkewedTree, 3, 3, "Правосторонняя цепочка, ключ 3 (лист)"),
                Arguments.of(rightSkewedTree, 4, -1, "Правосторонняя цепочка, ключ не найден"),
                Arguments.of(leftSkewedTree, 3, 1, "Левосторонняя цепочка, ключ 3 (корень)"),
                Arguments.of(leftSkewedTree, 2, 2, "Левосторонняя цепочка, ключ 2"),
                Arguments.of(leftSkewedTree, 1, 3, "Левосторонняя цепочка, ключ 1 (лист)"),
                Arguments.of(leftSkewedTree, 0, -1, "Левосторонняя цепочка, ключ не найден")
        );
    }

    @Nested
    @DisplayName("Рекурсивный метод: getNodeLevelRecursive")
    class RecursiveMethodTests {
        @ParameterizedTest(name = "{3}: ключ={1}, ожидаемый уровень={2}")
        @MethodSource("com.svedentsov.aqa.tasks.trees.NodeLevelInTreeTest#treeTestCases")
        void testGetNodeLevelRecursive(TreeNode root, int key, int expectedLevel, String description) {
            assertEquals(expectedLevel, levelFinder.getNodeLevelRecursive(root, key), description);
        }
    }

    @Nested
    @DisplayName("Итеративный метод (BFS): getNodeLevelBFS")
    class BfsMethodTests {
        @ParameterizedTest(name = "{3}: ключ={1}, ожидаемый уровень={2}")
        @MethodSource("com.svedentsov.aqa.tasks.trees.NodeLevelInTreeTest#treeTestCases")
        void testGetNodeLevelBFS(TreeNode root, int key, int expectedLevel, String description) {
            assertEquals(expectedLevel, levelFinder.getNodeLevelBFS(root, key), description);
        }
    }
}
