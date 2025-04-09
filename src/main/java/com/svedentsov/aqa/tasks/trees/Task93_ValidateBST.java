package com.svedentsov.aqa.tasks.trees;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Решение задачи №93: Проверить, является ли дерево Бинарным Деревом Поиска (BST).
 */
public class Task93_ValidateBST {

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
        // Можно добавить конструкторы или вспомогательные методы при необходимости
    }

    /**
     * Проверяет, является ли данное бинарное дерево корректным
     * Бинарным Деревом Поиска (BST) рекурсивно.
     * В корректном BST для ЛЮБОГО узла выполняются условия:
     * 1. Все значения в его левом поддереве СТРОГО МЕНЬШЕ значения самого узла.
     * 2. Все значения в его правом поддереве СТРОГО БОЛЬШЕ значения самого узла.
     * 3. Оба его поддерева также являются корректными BST.
     * 4. Дубликаты не допускаются.
     *
     * @param root Корневой узел дерева для проверки.
     * @return {@code true}, если дерево является валидным BST, {@code false} в противном случае.
     */
    public boolean isValidBSTRecursive(TreeNode root) {
        // Используем Long для границ, чтобы избежать проблем с Integer.MIN_VALUE/MAX_VALUE.
        return isValidBSTHelper(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    /**
     * Рекурсивный вспомогательный метод для проверки валидности BST.
     * Проверяет, что значение узла {@code node} находится в допустимом диапазоне
     * (minVal < node.val < maxVal) и рекурсивно проверяет его поддеревья.
     *
     * @param node   Текущий проверяемый узел.
     * @param minVal Нижняя граница допустимого значения (исключительно).
     * @param maxVal Верхняя граница допустимого значения (исключительно).
     * @return {@code true}, если поддерево валидно в рамках границ, {@code false} иначе.
     */
    private boolean isValidBSTHelper(TreeNode node, long minVal, long maxVal) {
        if (node == null) {
            return true; // Пустое поддерево валидно
        }

        // Проверка текущего узла
        if (node.val <= minVal || node.val >= maxVal) {
            return false; // Нарушение свойства BST
        }

        // Рекурсивная проверка левого и правого поддеревьев с суженными границами
        return isValidBSTHelper(node.left, minVal, node.val) &&
                isValidBSTHelper(node.right, node.val, maxVal);
    }

    /**
     * Проверяет, является ли дерево валидным BST с использованием
     * итеративного In-order обхода.
     * В BST In-order обход должен посещать узлы в строго возрастающем порядке.
     *
     * @param root Корневой узел.
     * @return true, если дерево валидно, false иначе.
     */
    public boolean isValidBSTInorder(TreeNode root) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        // Используем Long для previousValue, чтобы корректно сравнить с Integer.MIN_VALUE
        // в самом начале. Можно использовать и обертку `Integer prevVal = null;`
        long previousValue = Long.MIN_VALUE;
        TreeNode current = root;

        while (current != null || !stack.isEmpty()) {
            // Идем влево до конца, помещая узлы в стек
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            // Извлекаем узел из стека (следующий по inorder)
            current = stack.pop();

            // Проверяем порядок: текущее значение должно быть строго больше предыдущего
            if (current.val <= previousValue) {
                return false; // Нарушение порядка BST
            }
            previousValue = current.val; // Обновляем предыдущее значение

            // Переходим к правому поддереву
            current = current.right;
        }
        // Если обход завершен без нарушений порядка
        return true;
    }

    /**
     * Точка входа для демонстрации работы методов валидации BST.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task93_ValidateBST sol = new Task93_ValidateBST();

        // Пример 1: Валидное BST
        //      2
        //     / \
        //    1   3
        TreeNode root1 = new TreeNode(2);
        root1.left = new TreeNode(1);
        root1.right = new TreeNode(3);
        System.out.println("Tree 1 ([2,1,3]) is valid BST (Recursive): " + sol.isValidBSTRecursive(root1)); // true
        System.out.println("Tree 1 ([2,1,3]) is valid BST (Inorder):   " + sol.isValidBSTInorder(root1));   // true

        // Пример 2: Невалидное BST
        //      5
        //     / \
        //    1   4  <- Нарушение
        //       / \
        //      3   6
        TreeNode root2 = new TreeNode(5);
        root2.left = new TreeNode(1);
        root2.right = new TreeNode(4);
        root2.right.left = new TreeNode(3);
        root2.right.right = new TreeNode(6);
        System.out.println("\nTree 2 ([5,1,4,null,null,3,6]) is valid BST (Recursive): " + sol.isValidBSTRecursive(root2)); // false
        System.out.println("Tree 2 ([5,1,4,null,null,3,6]) is valid BST (Inorder):   " + sol.isValidBSTInorder(root2));   // false

        // Пример 3: Невалидное BST (дубликат)
        //      2
        //     / \
        //    2   3
        TreeNode root3 = new TreeNode(2);
        root3.left = new TreeNode(2);
        root3.right = new TreeNode(3);
        System.out.println("\nTree 3 ([2,2,3]) is valid BST (Recursive): " + sol.isValidBSTRecursive(root3)); // false
        System.out.println("Tree 3 ([2,2,3]) is valid BST (Inorder):   " + sol.isValidBSTInorder(root3));   // false

        // Пример 4: Пустое дерево
        System.out.println("\nTree 4 (null) is valid BST (Recursive): " + sol.isValidBSTRecursive(null)); // true
        System.out.println("Tree 4 (null) is valid BST (Inorder):   " + sol.isValidBSTInorder(null));   // true
    }
}
