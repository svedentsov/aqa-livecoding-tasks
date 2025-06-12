package com.svedentsov.aqa.tasks.trees;

import com.svedentsov.aqa.tasks.trees.BinaryTreeDiameter.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты для BinaryTreeDiameter (Диаметр бинарного дерева)")
class BinaryTreeDiameterTest {

    private BinaryTreeDiameter diameterCalculator;

    @BeforeEach
    void setUp() {
        diameterCalculator = new BinaryTreeDiameter();
    }

    static Stream<Arguments> treeDiameterTestCases() {
        // Пример 1: [1, [2,4,5], 3]
        TreeNode root1 = new TreeNode(1,
                new TreeNode(2, new TreeNode(4), new TreeNode(5)),
                new TreeNode(3));

        // Пример 2: [1, 2, null]
        TreeNode root2 = new TreeNode(1, new TreeNode(2), null);

        // Пример 4: [1] (только корень)
        TreeNode root4 = new TreeNode(1);

        // Пример 5: Линейное дерево вправо
        TreeNode root5 = new TreeNode(1);
        root5.right = new TreeNode(2);
        root5.right.right = new TreeNode(3);
        root5.right.right.right = new TreeNode(4);

        // Пример 6: Более сложное дерево
        TreeNode node5_6 = new TreeNode(5);
        TreeNode node6_6 = new TreeNode(6);
        TreeNode node3_6 = new TreeNode(3, node5_6, null); // 5 -> 3
        TreeNode node4_6 = new TreeNode(4, null, node6_6); // 4 -> 6
        TreeNode node2_6 = new TreeNode(2, node3_6, node4_6); // 3 -> 2 -> 4
        TreeNode root6 = new TreeNode(1, node2_6, null);      // 2 -> 1
        // Путь: 5 -> 3 -> 2 -> 4 -> 6 (4 ребра) - нет, (5-3), (3-2), (2-4), (4-6) - 4 ребра.
        // Диаметр должен быть 4.
        // (5) -> 3 -> (2) <- 4 <- (6)  -- путь (5,3,2,4,6) = 4 ребра.
        // Let's trace root6:
        // calc(1):
        //   leftH = calc(2):
        //     leftH_2 = calc(3):
        //       leftH_3 = calc(5): returns 0. maxD = max(maxD, 2-1-1=0)
        //       rightH_3 = calc(null): returns -1
        //       diam_3 = 2+0-1 = 1. maxD = max(maxD, 1)
        //       returns 1+max(0,-1) = 1. (height of 3 is 1)
        //     rightH_2 = calc(4):
        //       leftH_4 = calc(null): returns -1
        //       rightH_4 = calc(6): returns 0. maxD = max(maxD, 0)
        //       diam_4 = 2-1+0 = 1. maxD = max(maxD, 1)
        //       returns 1+max(-1,0) = 1. (height of 4 is 1)
        //     diam_2 = 2+1+1 = 4. maxD = max(maxD, 4)
        //     returns 1+max(1,1) = 2. (height of 2 is 2)
        //   rightH = calc(null): returns -1
        //   diam_1 = 2+2-1 = 3. maxD = max(4,3) = 4
        //   returns 1+max(2,-1) = 3.
        // Result: maxD = 4. The example in `main` expected 5. Let's check example path: 5 -> 3 -> 2 -> 4 -> 6. This has 4 edges.
        // Path (5,3) (3,2) (2,4) (4,6).  This is indeed 4 edges. Original comment for example 6 might be off by 1.
        // Let's use the example from LeetCode 543 which is often confused: [1,2,3,4,5]
        // This usually means:
        //    1
        //   / \
        //  2   3
        // / \
        //4   5
        // Path 4-2-1-3 is 3 edges. Path 5-2-1-3 is 3 edges. Path 4-2-5 is 2 edges. Diameter 3.
        // Path 3-1-2-4 is 3 edges.
        // Okay, `root1` is this tree. Diameter 3.

        // Tree where diameter does not pass through root
        //      1
        //     /
        //    2
        //   / \
        //  3   4
        // /     \
        //5       6
        // Path: 5-3-2-4-6 (4 edges). This is tree root6.
        TreeNode root7_left_child = new TreeNode(3, new TreeNode(5), null);
        TreeNode root7_right_child = new TreeNode(4, null, new TreeNode(6));
        TreeNode root7_node2 = new TreeNode(2, root7_left_child, root7_right_child);
        TreeNode root7 = new TreeNode(1, root7_node2, null); // This is root6 structure. Expected 4.

        // Linear tree left
        TreeNode root8 = new TreeNode(1);
        root8.left = new TreeNode(2);
        root8.left.left = new TreeNode(3);
        root8.left.left.left = new TreeNode(4);

        return Stream.of(
                Arguments.of(root1, 3, "Tree [1,[2,4,5],3] (Пример из задания)"),
                Arguments.of(root2, 1, "Tree [1,2,null] (Простое смещение)"),
                Arguments.of(null, 0, "Пустое дерево (null root)"),
                Arguments.of(root4, 0, "Дерево из одного узла [1]"),
                Arguments.of(root5, 3, "Линейное дерево вправо [1,null,[2,null,[3,null,4]]]"),
                Arguments.of(root8, 3, "Линейное дерево влево [[[[4],3],2],1]"),
                Arguments.of(root7, 4, "Диаметр не через корень (копия root6 из main)")
        );
    }

    @ParameterizedTest(name = "{2}")
    @MethodSource("treeDiameterTestCases")
    @DisplayName("Проверка вычисления диаметра для различных деревьев")
    void testDiameterOfBinaryTree(TreeNode root, int expectedDiameter, String description) {
        assertEquals(expectedDiameter, diameterCalculator.diameterOfBinaryTree(root), description);
    }

    @Test
    @DisplayName("Дерево: корень с двумя детьми-листьями")
    void tree_rootWithTwoLeafChildren() {
        //   1
        //  / \
        // 2   3
        TreeNode root = new TreeNode(1, new TreeNode(2), new TreeNode(3));
        assertEquals(2, diameterCalculator.diameterOfBinaryTree(root), "Диаметр для 1-[2,3] должен быть 2");
    }

    @Test
    @DisplayName("Дерево: палка из трех узлов, изгиб посередине")
    void tree_threeNodeStick_bentInMiddle() {
        //   1
        //  /
        // 2
        //  \
        //   3
        // Path: 1-2-3, 2 ребра
        TreeNode root = new TreeNode(1, new TreeNode(2, null, new TreeNode(3)), null);
        assertEquals(2, diameterCalculator.diameterOfBinaryTree(root));
    }

    @Test
    @DisplayName("Более глубокое дерево, где диаметр через корень")
    void deeperTree_diameterThroughRoot() {
        //        1
        //       / \
        //      2   3
        //     /     \
        //    4       5
        //   /         \
        //  6           7
        // Path: 6-4-2-1-3-5-7 (6 ребер)
        TreeNode n6 = new TreeNode(6);
        TreeNode n4 = new TreeNode(4, n6, null);
        TreeNode n2 = new TreeNode(2, n4, null);
        TreeNode n7 = new TreeNode(7);
        TreeNode n5 = new TreeNode(5, null, n7);
        TreeNode n3 = new TreeNode(3, null, n5);
        TreeNode root = new TreeNode(1, n2, n3);
        assertEquals(6, diameterCalculator.diameterOfBinaryTree(root));
    }

    @Test
    @DisplayName("Дерево, где самый длинный путь не проходит через корень")
    void tree_diameterNotThroughRoot() {
        //      1
        //       \
        //        2
        //       / \
        //      3   4
        //     / \   \
        //    5   6   7
        // Path: 5-3-6 (2 ребра). Path: 5-3-2-4-7 (4 ребра). Path: 6-3-2-4-7 (4 ребра).
        // Path: 5-3-2-4 (3). Path: 6-3-2-4 (3).
        // Subtree at 2: left_path 5-3-2 (2 edges), right_path 2-4-7 (2 edges). Diameter through 2 = 2+2 = 4.
        // Height of 3 is 1 (path 3-5 or 3-6). Height of 4 is 1 (path 4-7).
        // Height of 2 is 1 + max(h(3), h(4)) = 1 + max(1,1) = 2.
        // Diameter through 1: (1+h(null)) + (1+h(2)) = (1-1) + (1+2) = 0 + 3 = 3.
        // Max diameter should be 4.
        TreeNode n5 = new TreeNode(5);
        TreeNode n6 = new TreeNode(6);
        TreeNode n7 = new TreeNode(7);
        TreeNode n3 = new TreeNode(3, n5, n6);
        TreeNode n4 = new TreeNode(4, null, n7);
        TreeNode n2 = new TreeNode(2, n3, n4);
        TreeNode root = new TreeNode(1, null, n2);
        assertEquals(4, diameterCalculator.diameterOfBinaryTree(root));
    }
}
