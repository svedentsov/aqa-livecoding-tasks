package com.svedentsov.aqa.tasks.trees;

import java.util.*;

/**
 * Решение задачи №69: Обход бинарного дерева (In-order, Pre-order, Post-order).
 * Предоставляет рекурсивные и итеративные реализации для всех трех типов обхода.
 */
public class Task69_TreeTraversal {

    /**
     * Вложенный статический класс, представляющий узел бинарного дерева.
     * Сделан static, чтобы к нему можно было обращаться из статического метода main
     * и использовать без создания экземпляра внешнего класса Task93_ValidateBST.
     */
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    // ========================================
    // === РЕКУРСИВНЫЕ МЕТОДЫ ОБХОДА ========
    // ========================================

    /**
     * Выполняет центрированный (In-order) обход бинарного дерева рекурсивно.
     * Порядок: Левое поддерево -> Корень -> Правое поддерево.
     *
     * @param root Корневой узел дерева.
     * @return Список значений узлов в порядке In-order обхода.
     */
    public List<Integer> inorderTraversalRecursive(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }

    /**
     * Вспомогательный рекурсивный метод для In-order обхода.
     */
    private void inorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) return;
        inorderHelper(node.left, result);  // 1. Лево
        result.add(node.val);            // 2. Корень
        inorderHelper(node.right, result); // 3. Право
    }

    /**
     * Выполняет прямой (Pre-order) обход бинарного дерева рекурсивно.
     * Порядок: Корень -> Левое поддерево -> Правое поддерево.
     *
     * @param root Корневой узел дерева.
     * @return Список значений узлов в порядке Pre-order обхода.
     */
    public List<Integer> preorderTraversalRecursive(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        preorderHelper(root, result);
        return result;
    }

    /**
     * Вспомогательный рекурсивный метод для Pre-order обхода.
     */
    private void preorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) return;
        result.add(node.val);             // 1. Корень
        preorderHelper(node.left, result);  // 2. Лево
        preorderHelper(node.right, result); // 3. Право
    }

    /**
     * Выполняет обратный (Post-order) обход бинарного дерева рекурсивно.
     * Порядок: Левое поддерево -> Правое поддерево -> Корень.
     *
     * @param root Корневой узел дерева.
     * @return Список значений узлов в порядке Post-order обхода.
     */
    public List<Integer> postorderTraversalRecursive(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        postorderHelper(root, result);
        return result;
    }

    /**
     * Вспомогательный рекурсивный метод для Post-order обхода.
     */
    private void postorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) return;
        postorderHelper(node.left, result);  // 1. Лево
        postorderHelper(node.right, result); // 2. Право
        result.add(node.val);            // 3. Корень
    }

    // =========================================
    // === ИТЕРАТИВНЫЕ МЕТОДЫ ОБХОДА =========
    // =========================================

    /**
     * Выполняет центрированный (In-order) обход бинарного дерева итеративно
     * с использованием стека ({@link Deque}).
     *
     * @param root Корневой узел.
     * @return Список значений узлов в In-order порядке.
     */
    public List<Integer> inorderTraversalIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        // Используем Deque как стек (ArrayDeque - эффективная реализация)
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode current = root; // Начинаем с корня

        // Цикл продолжается, пока есть узлы для посещения (current != null)
        // или пока стек не пуст (есть узлы, к которым нужно вернуться)
        while (current != null || !stack.isEmpty()) {
            // 1. Идем максимально влево от текущего узла,
            //    добавляя все пройденные узлы в стек.
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            // 2. Когда дошли до самого левого узла (current стал null),
            //    извлекаем последний узел из стека. Это следующий узел в In-order порядке.
            current = stack.pop();
            result.add(current.val); // 3. Посещаем узел (добавляем в результат).
            // 4. Переходим к правому поддереву этого узла.
            //    На следующей итерации цикла мы начнем спуск влево от этого правого потомка.
            current = current.right;
        }
        return result;
    }

    /**
     * Выполняет прямой (Pre-order) обход бинарного дерева итеративно
     * с использованием стека ({@link Deque}).
     *
     * @param root Корневой узел.
     * @return Список значений узлов в Pre-order порядке.
     */
    public List<Integer> preorderTraversalIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result; // Обработка пустого дерева

        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root); // Начинаем с корня

        while (!stack.isEmpty()) {
            // 1. Извлекаем узел из стека.
            TreeNode current = stack.pop();
            // 2. Посещаем узел (добавляем в результат).
            result.add(current.val);

            // 3. Добавляем правого потомка в стек ПЕРВЫМ (если он есть).
            //    Он будет извлечен и обработан ПОСЛЕ левого потомка.
            if (current.right != null) {
                stack.push(current.right);
            }
            // 4. Добавляем левого потомка в стек ПОСЛЕДНИМ (если он есть).
            //    Он будет извлечен и обработан ПЕРЕД правым потомком.
            if (current.left != null) {
                stack.push(current.left);
            }
            // Порядок добавления (сначала правый, потом левый) обеспечивает
            // порядок извлечения и обработки: Корень -> Левый -> Правый.
        }
        return result;
    }

    /**
     * Выполняет обратный (Post-order) обход бинарного дерева итеративно.
     * Использует два стека или модифицированный Pre-order обход (Root -> Right -> Left)
     * с последующим реверсом результата или добавлением в начало LinkedList.
     * Этот вариант использует модифицированный Pre-order и LinkedList.addFirst().
     *
     * @param root Корневой узел.
     * @return Список значений узлов в Post-order порядке.
     */
    public List<Integer> postorderTraversalIterative(TreeNode root) {
        // Используем LinkedList для эффективного добавления в начало (addFirst).
        LinkedList<Integer> result = new LinkedList<>();
        if (root == null) return result;

        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();
            // Добавляем значение узла в НАЧАЛО результирующего списка.
            result.addFirst(current.val);

            // Сначала добавляем левого потомка в стек (он будет обработан позже).
            if (current.left != null) {
                stack.push(current.left);
            }
            // Затем добавляем правого потомка (он будет обработан раньше левого).
            if (current.right != null) {
                stack.push(current.right);
            }
            // Порядок извлечения и добавления в начало (addFirst) имитирует
            // последовательность Post-order: Left -> Right -> Root.
        }
        return result; // LinkedList уже содержит элементы в нужном порядке.
    }

    /**
     * Точка входа для демонстрации различных обходов дерева.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task69_TreeTraversal sol = new Task69_TreeTraversal();
        // Создадим тестовое дерево:
        //      4
        //     / \
        //    2   7
        //   / \ / \
        //  1  3 6  9
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(7);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(9);

        System.out.println("--- Recursive Traversals ---");
        System.out.println("Inorder:   " + sol.inorderTraversalRecursive(root));   // [1, 2, 3, 4, 6, 7, 9]
        System.out.println("Preorder:  " + sol.preorderTraversalRecursive(root)); // [4, 2, 1, 3, 7, 6, 9]
        System.out.println("Postorder: " + sol.postorderTraversalRecursive(root));// [1, 3, 2, 6, 9, 7, 4]

        System.out.println("\n--- Iterative Traversals ---");
        System.out.println("Inorder:   " + sol.inorderTraversalIterative(root));   // [1, 2, 3, 4, 6, 7, 9]
        System.out.println("Preorder:  " + sol.preorderTraversalIterative(root)); // [4, 2, 1, 3, 7, 6, 9]
        System.out.println("Postorder: " + sol.postorderTraversalIterative(root));// [1, 3, 2, 6, 9, 7, 4]
    }
}
