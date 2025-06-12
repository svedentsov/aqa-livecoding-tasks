package com.svedentsov.aqa.tasks.trees;

import com.svedentsov.aqa.tasks.trees.LowestCommonAncestor.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для LowestCommonAncestor (Наименьший Общий Предок)")
class LowestCommonAncestorTest {

    private LowestCommonAncestor lcaSolver;

    @BeforeEach
    void setUp() {
        lcaSolver = new LowestCommonAncestor();
    }

    // --- Тестовые деревья ---
    static TreeNode bstRoot, node0, node2, node3, node4, node5, node6, node7, node8, node9;
    static TreeNode btRoot, btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8;

    static void setupBst() {
        node3 = new LowestCommonAncestor.TreeNode(3);
        node5 = new LowestCommonAncestor.TreeNode(5);
        node0 = new LowestCommonAncestor.TreeNode(0);
        node4 = new LowestCommonAncestor.TreeNode(4, node3, node5);
        node2 = new LowestCommonAncestor.TreeNode(2, node0, node4);
        node7 = new LowestCommonAncestor.TreeNode(7);
        node9 = new LowestCommonAncestor.TreeNode(9);
        node8 = new LowestCommonAncestor.TreeNode(8, node7, node9);
        bstRoot = new LowestCommonAncestor.TreeNode(6, node2, node8);
        node6 = bstRoot; // <-- Привязываем node6 к корневому узлу (значение 6)
    }

    static void setupBinaryTree() {
        btn7 = new LowestCommonAncestor.TreeNode(7);
        btn4 = new LowestCommonAncestor.TreeNode(4);
        btn6 = new LowestCommonAncestor.TreeNode(6);
        btn2 = new LowestCommonAncestor.TreeNode(2, btn7, btn4);
        btn5 = new LowestCommonAncestor.TreeNode(5, btn6, btn2);
        btn0 = new LowestCommonAncestor.TreeNode(0);
        btn8 = new LowestCommonAncestor.TreeNode(8);
        btn1 = new LowestCommonAncestor.TreeNode(1, btn0, btn8);
        btRoot = new LowestCommonAncestor.TreeNode(3, btn5, btn1);
        btn3 = btRoot; // <-- Привязываем btn3 к корневому узлу (значение 3)
    }


    @Nested
    @DisplayName("Тесты для lowestCommonAncestorBST (Бинарное Дерево Поиска)")
    class LowestCommonAncestorBstTests {

        @BeforeEach
        void initBst() {
            setupBst();
        }

        static Stream<Arguments> bstTestCases() {
            setupBst(); // Убедимся, что узлы инициализированы для MethodSource
            return Stream.of(
                    Arguments.of(bstRoot, node2, node8, node6, "LCA(2,8) -> 6 (root)"),
                    Arguments.of(bstRoot, node2, node4, node2, "LCA(2,4) -> 2 (p является LCA)"),
                    Arguments.of(bstRoot, node0, node5, node2, "LCA(0,5) -> 2"),
                    Arguments.of(bstRoot, node3, node5, node4, "LCA(3,5) -> 4"),
                    Arguments.of(bstRoot, node7, node9, node8, "LCA(7,9) -> 8 (q является LCA)"),
                    Arguments.of(bstRoot, node0, node9, node6, "LCA(0,9) -> 6 (root, разные ветви)"),
                    Arguments.of(bstRoot, node3, node0, node2, "LCA(3,0) -> 2"),
                    Arguments.of(bstRoot, node2, node2, node2, "LCA(p,p) -> p")
            );
        }

        @ParameterizedTest(name = "[BST] {4}")
        @MethodSource("bstTestCases")
        void testLcaBst(TreeNode root, TreeNode p, TreeNode q, TreeNode expectedLca, String description) {
            assertSame(expectedLca, lcaSolver.lowestCommonAncestorBST(root, p, q), description);
        }

        @Test
        @DisplayName("[BST] Null root должен вернуть null")
        void bst_nullRoot_returnsNull() {
            assertNull(lcaSolver.lowestCommonAncestorBST(null, new TreeNode(1), new TreeNode(2)));
        }

        @Test
        @DisplayName("[BST] Null p или q должен выбросить NullPointerException")
        void bst_nullPOrQ_throwsNullPointerException() {
            setupBst();
            assertThrows(NullPointerException.class, () -> lcaSolver.lowestCommonAncestorBST(bstRoot, null, node2));
            assertThrows(NullPointerException.class, () -> lcaSolver.lowestCommonAncestorBST(bstRoot, node2, null));
        }
    }

    @Nested
    @DisplayName("Тесты для lowestCommonAncestorBinaryTree (Обычное Бинарное Дерево)")
    class LowestCommonAncestorBinaryTreeTests {

        @BeforeEach
        void initBt() {
            setupBinaryTree();
        }

        static Stream<Arguments> binaryTreeTestCases() {
            setupBinaryTree(); // Убедимся, что узлы инициализированы
            return Stream.of(
                    Arguments.of(btRoot, btn5, btn1, btn3, "LCA(5,1) -> 3 (root)"),
                    Arguments.of(btRoot, btn5, btn4, btn5, "LCA(5,4) -> 5 (p является LCA)"),
                    Arguments.of(btRoot, btn6, btn4, btn5, "LCA(6,4) -> 5"),
                    Arguments.of(btRoot, btn0, btn8, btn1, "LCA(0,8) -> 1"),
                    Arguments.of(btRoot, btn7, btn4, btn2, "LCA(7,4) -> 2"),
                    Arguments.of(btRoot, btn6, btn6, btn6, "LCA(p,p) -> p"),
                    Arguments.of(btRoot, btn7, btn8, btn3, "LCA(7,8) -> 3 (root, глубоко разные ветви)")
            );
        }

        @ParameterizedTest(name = "[BT] {4}")
        @MethodSource("binaryTreeTestCases")
        void testLcaBinaryTree(TreeNode root, TreeNode p, TreeNode q, TreeNode expectedLca, String description) {
            assertSame(expectedLca, lcaSolver.lowestCommonAncestorBinaryTree(root, p, q), description);
        }

        @Test
        @DisplayName("[BT] Null root должен вернуть null")
        void bt_nullRoot_returnsNull() {
            assertNull(lcaSolver.lowestCommonAncestorBinaryTree(null, new TreeNode(1), new TreeNode(2)));
        }

        @Test
        @DisplayName("[BT] Null p или q должен выбросить NullPointerException")
        void bt_nullPOrQ_throwsNullPointerException() {
            setupBinaryTree();
            assertThrows(NullPointerException.class, () -> lcaSolver.lowestCommonAncestorBinaryTree(btRoot, null, btn2));
            assertThrows(NullPointerException.class, () -> lcaSolver.lowestCommonAncestorBinaryTree(btRoot, btn2, null));
        }

        @Test
        @DisplayName("[BT] Один из узлов не в дереве, должен вернуть другой узел, если он есть")
        void bt_oneNodeNotInTree_returnsTheOtherNode() {
            setupBinaryTree();
            TreeNode nodeNotInTree = new LowestCommonAncestor.TreeNode(100);
            // Ожидаем, что вернет btn6, так как он есть в дереве
            assertSame(btn6, lcaSolver.lowestCommonAncestorBinaryTree(btRoot, btn6, nodeNotInTree), "LCA(btn6, notInTree) -> btn6");
            // Ожидаем, что вернет btn4, так как он есть в дереве
            assertSame(btn4, lcaSolver.lowestCommonAncestorBinaryTree(btRoot, nodeNotInTree, btn4), "LCA(notInTree, btn4) -> btn4");
        }

        @Test
        @DisplayName("[BT] Оба узла не в дереве, должен вернуть null")
        void bt_bothNodesNotInTree_returnsNull() {
            setupBinaryTree();
            TreeNode nodeNotInTree1 = new LowestCommonAncestor.TreeNode(100);
            TreeNode nodeNotInTree2 = new LowestCommonAncestor.TreeNode(200);
            assertNull(lcaSolver.lowestCommonAncestorBinaryTree(btRoot, nodeNotInTree1, nodeNotInTree2), "LCA(notInTree1, notInTree2) -> null");
        }
    }
}
