package com.svedentsov.aqa.tasks.trees;

import com.svedentsov.aqa.tasks.trees.BuildBST.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для BuildBST")
class BuildBSTTest {

    private BuildBST bstBuilder;

    @BeforeEach
    void setUp() {
        bstBuilder = new BuildBST();
    }

    // --- Вспомогательные методы для тестов (обходы дерева) ---
    private List<Integer> getInOrderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inOrderRecursive(root, result);
        return result;
    }

    private void inOrderRecursive(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        inOrderRecursive(node.left, result);
        result.add(node.val);
        inOrderRecursive(node.right, result);
    }

    private List<Integer> getLevelOrderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            result.add(current.val);
            if (current.left != null) {
                queue.offer(current.left);
            }
            if (current.right != null) {
                queue.offer(current.right);
            }
        }
        return result;
    }

    @Nested
    @DisplayName("Тесты для метода insertIntoBST")
    class InsertIntoBSTTests {

        @Test
        @DisplayName("Вставка в пустое дерево (null root)")
        void insertIntoEmptyTree() {
            TreeNode root = bstBuilder.insertIntoBST(null, 10);
            assertNotNull(root);
            assertEquals(10, root.val);
            assertNull(root.left);
            assertNull(root.right);
        }

        @Test
        @DisplayName("Вставка меньшего значения (идет влево)")
        void insertSmallerValue() {
            TreeNode root = new TreeNode(10);
            bstBuilder.insertIntoBST(root, 5);
            assertNotNull(root.left);
            assertEquals(5, root.left.val);
            assertNull(root.right);
        }

        @Test
        @DisplayName("Вставка большего значения (идет вправо)")
        void insertLargerValue() {
            TreeNode root = new TreeNode(10);
            bstBuilder.insertIntoBST(root, 15);
            assertNotNull(root.right);
            assertEquals(15, root.right.val);
            assertNull(root.left);
        }

        @Test
        @DisplayName("Вставка дубликата (дерево не должно измениться)")
        void insertDuplicateValue() {
            TreeNode root = new TreeNode(10);
            root.left = new TreeNode(5);
            root.right = new TreeNode(15);

            List<Integer> before = getInOrderTraversal(root);
            bstBuilder.insertIntoBST(root, 10); // Дубликат
            bstBuilder.insertIntoBST(root, 5);  // Дубликат
            List<Integer> after = getInOrderTraversal(root);

            assertIterableEquals(before, after, "In-order обход не должен измениться после вставки дубликатов");
            // Проверим, что ссылки на узлы остались теми же (для дубликата 10)
            assertEquals(10, root.val); // Корень не изменился
            assertNotNull(root.left);
            assertEquals(5, root.left.val); // Левый узел не изменился
        }

        @Test
        @DisplayName("Формирование сложного дерева несколькими вставками")
        void insertMultipleValues() {
            TreeNode root = null;
            root = bstBuilder.insertIntoBST(root, 4);
            root = bstBuilder.insertIntoBST(root, 2);
            root = bstBuilder.insertIntoBST(root, 7);
            root = bstBuilder.insertIntoBST(root, 1);
            root = bstBuilder.insertIntoBST(root, 3);
            root = bstBuilder.insertIntoBST(root, 6);
            root = bstBuilder.insertIntoBST(root, 9);

            // In-order обход BST должен дать отсортированный список
            List<Integer> expectedInOrder = Arrays.asList(1, 2, 3, 4, 6, 7, 9);
            assertEquals(expectedInOrder, getInOrderTraversal(root));

            // Проверка структуры (частично)
            assertNotNull(root);
            assertEquals(4, root.val);
            assertNotNull(root.left);
            assertEquals(2, root.left.val);
            assertNotNull(root.right);
            assertEquals(7, root.right.val);
            assertNotNull(root.left.left);
            assertEquals(1, root.left.left.val);
        }
    }

    @Nested
    @DisplayName("Тесты для метода buildBSTFromArray")
    class BuildBSTFromArrayTests {

        @Test
        @DisplayName("Построение из null массива")
        void buildFromNullArray() {
            TreeNode root = bstBuilder.buildBSTFromArray(null);
            assertNull(root);
        }

        @Test
        @DisplayName("Построение из пустого массива")
        void buildFromEmptyArray() {
            TreeNode root = bstBuilder.buildBSTFromArray(new int[]{});
            assertNull(root);
        }

        @Test
        @DisplayName("Построение из массива с одним элементом")
        void buildFromSingleElementArray() {
            TreeNode root = bstBuilder.buildBSTFromArray(new int[]{10});
            assertNotNull(root);
            assertEquals(10, root.val);
            assertNull(root.left);
            assertNull(root.right);
        }

        @Test
        @DisplayName("Построение из массива [4, 2, 7, 1, 3, 6, 9]")
        void buildFromArray_example1() {
            int[] values = {4, 2, 7, 1, 3, 6, 9};
            TreeNode root = bstBuilder.buildBSTFromArray(values);
            List<Integer> expectedInOrder = Arrays.asList(1, 2, 3, 4, 6, 7, 9);
            assertEquals(expectedInOrder, getInOrderTraversal(root), "In-order обход должен дать отсортированный список");
            // Дополнительно проверим Level-order для структуры
            List<Integer> expectedLevelOrder = Arrays.asList(4, 2, 7, 1, 3, 6, 9); // Зависит от порядка вставки
            assertEquals(expectedLevelOrder, getLevelOrderTraversal(root), "Level-order обход для данного порядка вставки");
        }

        @Test
        @DisplayName("Построение из массива с дубликатами [4, 2, 7, 2, 6, 4]")
        void buildFromArray_withDuplicates() {
            int[] values = {4, 2, 7, 2, 6, 4}; // Дубликаты 2 и 4
            TreeNode root = bstBuilder.buildBSTFromArray(values);
            // Дубликаты игнорируются, поэтому в in-order обходе их не будет
            List<Integer> expectedInOrder = Arrays.asList(2, 4, 6, 7);
            assertEquals(expectedInOrder, getInOrderTraversal(root), "In-order обход с игнорированием дубликатов");
        }

        @Test
        @DisplayName("Построение из отсортированного массива [1, 2, 3, 4, 5] (дегенерированное дерево)")
        void buildFromArray_sortedInput_degenerateTree() {
            int[] values = {1, 2, 3, 4, 5};
            TreeNode root = bstBuilder.buildBSTFromArray(values);
            List<Integer> expectedInOrder = Arrays.asList(1, 2, 3, 4, 5);
            assertEquals(expectedInOrder, getInOrderTraversal(root));

            // Проверка структуры дегенерированного дерева (все узлы в правых потомках)
            assertNotNull(root);
            assertEquals(1, root.val);
            assertNull(root.left);
            assertNotNull(root.right);
            assertEquals(2, root.right.val);
            assertNull(root.right.left);
        }

        @Test
        @DisplayName("Построение из обратно отсортированного массива [5, 4, 3, 2, 1]")
        void buildFromArray_reverseSortedInput_degenerateTree() {
            int[] values = {5, 4, 3, 2, 1};
            TreeNode root = bstBuilder.buildBSTFromArray(values);
            List<Integer> expectedInOrder = Arrays.asList(1, 2, 3, 4, 5);
            assertEquals(expectedInOrder, getInOrderTraversal(root));

            // Проверка структуры дегенерированного дерева (все узлы в левых потомках)
            assertNotNull(root);
            assertEquals(5, root.val);
            assertNull(root.right);
            assertNotNull(root.left);
            assertEquals(4, root.left.val);
            assertNull(root.left.right);
        }
    }
}
