package com.svedentsov.aqa.tasks.trees;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import com.svedentsov.aqa.tasks.trees.TreeTraversal.TreeNode; // Импорт вложенного класса

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для TreeTraversal")
class TreeTraversalTest {

    private TreeTraversal traverser;

    // Тестовые деревья
    private TreeNode emptyTree;
    private TreeNode singleNodeTree; // 10
    private TreeNode exampleTree;    //     4 / \ 2   7 / \ / \ 1  3 6  9
    private TreeNode leetCodeTree;   //     1 \ 2 / 3  (inorder: 1,3,2)
    private TreeNode leftSkewedTree; //     3 / 2 / 1
    private TreeNode rightSkewedTree;//     1 \ 2 \ 3


    @BeforeEach
    void setUp() {
        traverser = new TreeTraversal();

        emptyTree = null;
        singleNodeTree = new TreeNode(10);

        exampleTree = new TreeNode(4,
                new TreeNode(2, new TreeNode(1), new TreeNode(3)),
                new TreeNode(7, new TreeNode(6), new TreeNode(9))
        );

        // LeetCode example: [1,null,2,3] -> 1 (root), right=2, 2.left=3
        leetCodeTree = new TreeNode(1,
                null,
                new TreeNode(2, new TreeNode(3), null)
        );

        leftSkewedTree = new TreeNode(3);
        leftSkewedTree.left = new TreeNode(2);
        leftSkewedTree.left.left = new TreeNode(1);

        rightSkewedTree = new TreeNode(1);
        rightSkewedTree.right = new TreeNode(2);
        rightSkewedTree.right.right = new TreeNode(3);
    }

    @Nested
    @DisplayName("In-order Traversal Tests")
    class InOrderTests {
        @Test
        @DisplayName("Рекурсивный In-order")
        void inorderRecursive() {
            assertIterableEquals(Collections.emptyList(), traverser.inorderTraversalRecursive(emptyTree));
            assertIterableEquals(List.of(10), traverser.inorderTraversalRecursive(singleNodeTree));
            assertIterableEquals(List.of(1, 2, 3, 4, 6, 7, 9), traverser.inorderTraversalRecursive(exampleTree));
            assertIterableEquals(List.of(1, 3, 2), traverser.inorderTraversalRecursive(leetCodeTree));
            assertIterableEquals(List.of(1, 2, 3), traverser.inorderTraversalRecursive(leftSkewedTree));
            assertIterableEquals(List.of(1, 2, 3), traverser.inorderTraversalRecursive(rightSkewedTree));
        }

        @Test
        @DisplayName("Итеративный In-order")
        void inorderIterative() {
            assertIterableEquals(Collections.emptyList(), traverser.inorderTraversalIterative(emptyTree));
            assertIterableEquals(List.of(10), traverser.inorderTraversalIterative(singleNodeTree));
            assertIterableEquals(List.of(1, 2, 3, 4, 6, 7, 9), traverser.inorderTraversalIterative(exampleTree));
            assertIterableEquals(List.of(1, 3, 2), traverser.inorderTraversalIterative(leetCodeTree));
            assertIterableEquals(List.of(1, 2, 3), traverser.inorderTraversalIterative(leftSkewedTree));
            assertIterableEquals(List.of(1, 2, 3), traverser.inorderTraversalIterative(rightSkewedTree));
        }
    }

    @Nested
    @DisplayName("Pre-order Traversal Tests")
    class PreOrderTests {
        @Test
        @DisplayName("Рекурсивный Pre-order")
        void preorderRecursive() {
            assertIterableEquals(Collections.emptyList(), traverser.preorderTraversalRecursive(emptyTree));
            assertIterableEquals(List.of(10), traverser.preorderTraversalRecursive(singleNodeTree));
            assertIterableEquals(List.of(4, 2, 1, 3, 7, 6, 9), traverser.preorderTraversalRecursive(exampleTree));
            assertIterableEquals(List.of(1, 2, 3), traverser.preorderTraversalRecursive(leetCodeTree));
            assertIterableEquals(List.of(3, 2, 1), traverser.preorderTraversalRecursive(leftSkewedTree));
            assertIterableEquals(List.of(1, 2, 3), traverser.preorderTraversalRecursive(rightSkewedTree));
        }

        @Test
        @DisplayName("Итеративный Pre-order")
        void preorderIterative() {
            assertIterableEquals(Collections.emptyList(), traverser.preorderTraversalIterative(emptyTree));
            assertIterableEquals(List.of(10), traverser.preorderTraversalIterative(singleNodeTree));
            assertIterableEquals(List.of(4, 2, 1, 3, 7, 6, 9), traverser.preorderTraversalIterative(exampleTree));
            assertIterableEquals(List.of(1, 2, 3), traverser.preorderTraversalIterative(leetCodeTree));
            assertIterableEquals(List.of(3, 2, 1), traverser.preorderTraversalIterative(leftSkewedTree));
            assertIterableEquals(List.of(1, 2, 3), traverser.preorderTraversalIterative(rightSkewedTree));
        }
    }

    @Nested
    @DisplayName("Post-order Traversal Tests")
    class PostOrderTests {
        @Test
        @DisplayName("Рекурсивный Post-order")
        void postorderRecursive() {
            assertIterableEquals(Collections.emptyList(), traverser.postorderTraversalRecursive(emptyTree));
            assertIterableEquals(List.of(10), traverser.postorderTraversalRecursive(singleNodeTree));
            assertIterableEquals(List.of(1, 3, 2, 6, 9, 7, 4), traverser.postorderTraversalRecursive(exampleTree));
            assertIterableEquals(List.of(3, 2, 1), traverser.postorderTraversalRecursive(leetCodeTree));
            assertIterableEquals(List.of(1, 2, 3), traverser.postorderTraversalRecursive(leftSkewedTree));
            assertIterableEquals(List.of(3, 2, 1), traverser.postorderTraversalRecursive(rightSkewedTree));
        }

        @Test
        @DisplayName("Итеративный Post-order")
        void postorderIterative() {
            assertIterableEquals(Collections.emptyList(), traverser.postorderTraversalIterative(emptyTree));
            assertIterableEquals(List.of(10), traverser.postorderTraversalIterative(singleNodeTree));
            assertIterableEquals(List.of(1, 3, 2, 6, 9, 7, 4), traverser.postorderTraversalIterative(exampleTree));
            assertIterableEquals(List.of(3, 2, 1), traverser.postorderTraversalIterative(leetCodeTree));
            assertIterableEquals(List.of(1, 2, 3), traverser.postorderTraversalIterative(leftSkewedTree));
            assertIterableEquals(List.of(3, 2, 1), traverser.postorderTraversalIterative(rightSkewedTree));
        }
    }
}
