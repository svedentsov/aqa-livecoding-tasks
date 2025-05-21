package com.svedentsov.aqa.tasks.trees;

import java.util.*;


/**
 * Решение задачи №69: Обход бинарного дерева (In-order, Pre-order, Post-order).
 * Описание: Реализовать один из обходов.
 * (Проверяет: рекурсия, структуры данных)
 * Задание: Для данного класса `TreeNode` (с полями `val`, `left`, `right`),
 * реализуйте метод `List<Integer> inorderTraversal(TreeNode root)`, который
 * выполняет центрированный (in-order) обход дерева и возвращает список значений узлов.
 * (Дополнительно реализованы Pre-order и Post-order, итеративно и рекурсивно).
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

        // Конструкторы для удобства создания дерева в тестах (если понадобятся там)
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public List<Integer> inorderTraversalRecursive(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }

    private void inorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) return;
        inorderHelper(node.left, result);
        result.add(node.val);
        inorderHelper(node.right, result);
    }

    public List<Integer> preorderTraversalRecursive(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        preorderHelper(root, result);
        return result;
    }

    private void preorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) return;
        result.add(node.val);
        preorderHelper(node.left, result);
        preorderHelper(node.right, result);
    }

    public List<Integer> postorderTraversalRecursive(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        postorderHelper(root, result);
        return result;
    }

    private void postorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) return;
        postorderHelper(node.left, result);
        postorderHelper(node.right, result);
        result.add(node.val);
    }

    public List<Integer> inorderTraversalIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode current = root;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            result.add(current.val);
            current = current.right;
        }
        return result;
    }

    public List<Integer> preorderTraversalIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();
            result.add(current.val);
            if (current.right != null) stack.push(current.right);
            if (current.left != null) stack.push(current.left);
        }
        return result;
    }

    public List<Integer> postorderTraversalIterative(TreeNode root) {
        LinkedList<Integer> result = new LinkedList<>(); // Используем LinkedList для addFirst
        if (root == null) return result;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();
            result.addFirst(current.val); // Добавляем в начало списка
            // Сначала левый, потом правый ребенок в стек, чтобы правый обработался раньше (и попал раньше в result.addFirst)
            if (current.left != null) stack.push(current.left);
            if (current.right != null) stack.push(current.right);
        }
        return result; // LinkedList уже является List
    }
}
