package com.svedentsov.aqa.tasks.trees;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Решение задачи №97: Проверка на симметричность дерева.
 * Содержит вложенный класс TreeNode.
 */
public class Task97_SymmetricTreeCheck {

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
        // Можно добавить другие конструкторы при необходимости
    }

    /**
     * Проверяет, является ли бинарное дерево зеркально симметричным
     * относительно своего центра (корня).
     * Использует рекурсивный вспомогательный метод isMirror.
     *
     * @param root Корневой узел дерева.
     * @return {@code true}, если дерево симметрично, {@code false} в противном случае.
     * Пустое дерево считается симметричным.
     */
    public boolean isSymmetric(TreeNode root) {
        // Пустое дерево по определению симметрично.
        if (root == null) {
            return true;
        }
        // Симметричность определяется сравнением левого и правого поддеревьев
        // на зеркальность друг другу.
        return isMirror(root.left, root.right);
    }

    /**
     * Рекурсивный вспомогательный метод для проверки зеркальности двух поддеревьев.
     * Два дерева/поддерева являются зеркальными, если:
     * 1. Их корневые узлы (если существуют) имеют одинаковые значения.
     * 2. Левое поддерево первого узла зеркально правому поддереву второго узла.
     * 3. Правое поддерево первого узла зеркально левому поддереву второго узла.
     *
     * @param node1 Корень первого поддерева (может быть null).
     * @param node2 Корень второго поддерева (может быть null).
     * @return {@code true}, если поддеревья зеркальны, {@code false} иначе.
     */
    private boolean isMirror(TreeNode node1, TreeNode node2) {
        // Базовый случай 1: Если оба узла null, они симметричны.
        if (node1 == null && node2 == null) {
            return true;
        }
        // Базовый случай 2: Если ТОЛЬКО один из узлов null, они не симметричны.
        if (node1 == null || node2 == null) {
            return false;
        }

        // Условие зеркальности (рекурсивная проверка):
        return (node1.val == node2.val)              // Значения корней равны И
                && isMirror(node1.left, node2.right)  // Внешние поддеревья зеркальны И
                && isMirror(node1.right, node2.left); // Внутренние поддеревья зеркальны
    }

    /**
     * Итеративная проверка на симметричность с использованием очереди (BFS-подобный подход).
     * Сравнивает узлы на одном уровне попарно на зеркальность.
     *
     * @param root Корневой узел.
     * @return true, если дерево симметрично, false иначе.
     */
    public boolean isSymmetricIterative(TreeNode root) {
        if (root == null) return true;

        Queue<TreeNode> queue = new LinkedList<>();
        // Добавляем левый и правый узел корня (могут быть null).
        // Важно добавлять парами для зеркального сравнения.
        queue.offer(root.left);
        queue.offer(root.right);

        while (!queue.isEmpty()) {
            // Извлекаем пару узлов для сравнения.
            TreeNode node1 = queue.poll();
            TreeNode node2 = queue.poll();

            // Если оба null, они симметричны на этом уровне, продолжаем проверку дальше.
            if (node1 == null && node2 == null) continue;
            // Если только один null, или их значения не равны - дерево не симметрично.
            if (node1 == null || node2 == null || node1.val != node2.val) {
                return false;
            }

            // Добавляем потомков в очередь в зеркальном порядке для следующего уровня проверки:
            // 1. Левый потомок node1 сравнится с правым потомком node2.
            queue.offer(node1.left);
            queue.offer(node2.right);
            // 2. Правый потомок node1 сравнится с левым потомком node2.
            queue.offer(node1.right);
            queue.offer(node2.left);
            // Null потомки также добавляются в очередь, чтобы обеспечить парность сравнения.
        }
        // Если очередь пуста, значит все проверенные пары на всех уровнях были симметричны.
        return true;
    }

    /**
     * Точка входа для демонстрации работы методов проверки симметричности.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task97_SymmetricTreeCheck sol = new Task97_SymmetricTreeCheck();

        // Пример 1: Симметричное дерево
        //      1
        //     / \
        //    2   2
        //   / \ / \
        //  3  4 4  3
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(2);
        root1.left.left = new TreeNode(3);
        root1.left.right = new TreeNode(4);
        root1.right.left = new TreeNode(4);
        root1.right.right = new TreeNode(3);
        System.out.println("Tree 1 is symmetric (Recursive): " + sol.isSymmetric(root1)); // true
        System.out.println("Tree 1 is symmetric (Iterative): " + sol.isSymmetricIterative(root1)); // true


        // Пример 2: Несимметричное дерево
        //      1
        //     / \
        //    2   2
        //     \   \
        //      3   3
        TreeNode root2 = new TreeNode(1);
        root2.left = new TreeNode(2);
        root2.right = new TreeNode(2);
        root2.left.right = new TreeNode(3);
        root2.right.right = new TreeNode(3);
        System.out.println("\nTree 2 is symmetric (Recursive): " + sol.isSymmetric(root2)); // false
        System.out.println("Tree 2 is symmetric (Iterative): " + sol.isSymmetricIterative(root2)); // false


        // Пример 3: Пустое дерево
        System.out.println("\nTree 3 (null) is symmetric (Recursive): " + sol.isSymmetric(null)); // true
        System.out.println("Tree 3 (null) is symmetric (Iterative): " + sol.isSymmetricIterative(null)); // true

        // Пример 4: Дерево с одним узлом
        TreeNode root4 = new TreeNode(1);
        System.out.println("\nTree 4 ([1]) is symmetric (Recursive): " + sol.isSymmetric(root4)); // true
        System.out.println("Tree 4 ([1]) is symmetric (Iterative): " + sol.isSymmetricIterative(root4)); // true
    }
}
