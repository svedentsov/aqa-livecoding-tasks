package com.svedentsov.aqa.tasks.trees;

import java.util.*;

/**
 * Решение задачи №69: Обход бинарного дерева (In-order, Pre-order, Post-order).
 * <p>
 * Описание: Реализовать один из обходов.
 * (Проверяет: рекурсия, структуры данных)
 * <p>
 * Задание: Для данного класса `TreeNode` (с полями `val`, `left`, `right`),
 * реализуйте метод `List<Integer> inorderTraversal(TreeNode root)`, который
 * выполняет центрированный (in-order) обход дерева и возвращает список значений узлов.
 * (Дополнительно реализованы Pre-order и Post-order, итеративно и рекурсивно).
 * <p>
 * Пример: Для дерева `[1, null, 2, 3]`, `inorderTraversal` вернет `[1, 3, 2]`.
 */
public class TreeTraversal {

    /**
     * Вложенный статический класс, представляющий узел бинарного дерева.
     */
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }

        // Конструкторы для удобства создания дерева в main
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // ========================================
    // === Методы обхода (Public API) ========
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
        inorderHelper(root, result); // Вызов приватного рекурсивного помощника
        return result;
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
     * Выполняет центрированный (In-order) обход бинарного дерева итеративно
     * с использованием стека.
     *
     * @param root Корневой узел.
     * @return Список значений узлов в In-order порядке.
     */
    public List<Integer> inorderTraversalIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode current = root;

        while (current != null || !stack.isEmpty()) {
            while (current != null) { // Идем влево
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();   // Посещаем узел
            result.add(current.val);
            current = current.right; // Идем вправо
        }
        return result;
    }

    /**
     * Выполняет прямой (Pre-order) обход бинарного дерева итеративно
     * с использованием стека.
     *
     * @param root Корневой узел.
     * @return Список значений узлов в Pre-order порядке.
     */
    public List<Integer> preorderTraversalIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();
            result.add(current.val); // Посещаем корень
            // Сначала правый, потом левый (чтобы левый был обработан первым)
            if (current.right != null) stack.push(current.right);
            if (current.left != null) stack.push(current.left);
        }
        return result;
    }

    /**
     * Выполняет обратный (Post-order) обход бинарного дерева итеративно.
     * Использует модифицированный Pre-order и добавление в начало списка.
     *
     * @param root Корневой узел.
     * @return Список значений узлов в Post-order порядке.
     */
    public List<Integer> postorderTraversalIterative(TreeNode root) {
        LinkedList<Integer> result = new LinkedList<>(); // Для addFirst() O(1)
        if (root == null) return result;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();
            result.addFirst(current.val); // Добавляем корень В НАЧАЛО
            // Сначала левый, потом правый (чтобы правый был обработан первым)
            if (current.left != null) stack.push(current.left);
            if (current.right != null) stack.push(current.right);
        }
        return result;
    }

    /**
     * Точка входа для демонстрации различных обходов дерева.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        TreeTraversal sol = new TreeTraversal();

        // Создаем тестовое дерево:
        //      4
        //     / \
        //    2   7
        //   / \ / \
        //  1  3 6  9
        TreeNode root = new TreeNode(4,
                new TreeNode(2, new TreeNode(1), new TreeNode(3)),
                new TreeNode(7, new TreeNode(6), new TreeNode(9))
        );

        System.out.println("--- Binary Tree Traversals ---");
        System.out.println("Tree Structure (approximate):");
        System.out.println("      4");
        System.out.println("     / \\");
        System.out.println("    2   7");
        System.out.println("   / \\ / \\");
        System.out.println("  1  3 6  9");

        System.out.println("\n--- Recursive Methods ---");
        runTraversalTest(sol, root, "Inorder (Recursive)", sol::inorderTraversalRecursive);
        runTraversalTest(sol, root, "Preorder (Recursive)", sol::preorderTraversalRecursive);
        runTraversalTest(sol, root, "Postorder (Recursive)", sol::postorderTraversalRecursive);

        System.out.println("\n--- Iterative Methods ---");
        runTraversalTest(sol, root, "Inorder (Iterative)", sol::inorderTraversalIterative);
        runTraversalTest(sol, root, "Preorder (Iterative)", sol::preorderTraversalIterative);
        runTraversalTest(sol, root, "Postorder (Iterative)", sol::postorderTraversalIterative);

        // Тест на пустом дереве
        System.out.println("\n--- Empty Tree ---");
        runTraversalTest(sol, null, "Inorder (Recursive)", sol::inorderTraversalRecursive);
        runTraversalTest(sol, null, "Inorder (Iterative)", sol::inorderTraversalIterative);
    }

    /**
     * Вспомогательный рекурсивный метод для In-order обхода.
     */
    private void inorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) return;
        inorderHelper(node.left, result);
        result.add(node.val);
        inorderHelper(node.right, result);
    }

    /**
     * Вспомогательный рекурсивный метод для Pre-order обхода.
     */
    private void preorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) return;
        result.add(node.val);
        preorderHelper(node.left, result);
        preorderHelper(node.right, result);
    }

    /**
     * Вспомогательный рекурсивный метод для Post-order обхода.
     */
    private void postorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) return;
        postorderHelper(node.left, result);
        postorderHelper(node.right, result);
        result.add(node.val);
    }

    /**
     * Вспомогательный метод для запуска и вывода результатов тестов обхода.
     * Использует функциональный интерфейс для передачи метода обхода.
     */
    @FunctionalInterface
    interface TraversalFunction {
        List<Integer> apply(TreeNode root);
    }

    private static void runTraversalTest(TreeTraversal sol, TreeNode root, String description, TraversalFunction traversalMethod) {
        System.out.print(String.format("%-25s: ", description));
        try {
            List<Integer> result = traversalMethod.apply(root);
            System.out.println(result);
        } catch (Exception e) {
            System.err.println("Error - " + e.getMessage());
        }
    }
}
