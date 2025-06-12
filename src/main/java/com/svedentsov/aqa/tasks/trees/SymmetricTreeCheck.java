package com.svedentsov.aqa.tasks.trees;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Решение задачи №97: Проверка на симметричность дерева.
 * Описание: (Проверяет: рекурсия, деревья)
 * Задание: Напишите метод `boolean isSymmetric(TreeNode root)`, который проверяет,
 * является ли бинарное дерево зеркально симметричным относительно своего центра.
 * Пример: Дерево `[1, 2, 2, 3, 4, 4, 3]` -> `true`.
 * Дерево `[1, 2, 2, null, 3, null, 3]` -> `false`.
 */
public class SymmetricTreeCheck {

    /**
     * Вложенный статический класс, представляющий узел бинарного дерева.
     * Сделан public static для удобства использования в тестах.
     */
    public static class TreeNode {
        public int val; // public для простоты создания деревьев в тестах
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Проверяет, является ли бинарное дерево зеркально симметричным (рекурсивный подход).
     *
     * @param root Корневой узел дерева.
     * @return {@code true}, если дерево симметрично, {@code false} в противном случае.
     */
    public boolean isSymmetricRecursive(TreeNode root) {
        if (root == null) {
            return true; // Пустое дерево симметрично
        }
        // Проверяем зеркальность левого и правого поддеревьев
        return isMirror(root.left, root.right);
    }

    /**
     * Рекурсивный вспомогательный метод для проверки зеркальности двух поддеревьев.
     */
    private boolean isMirror(TreeNode node1, TreeNode node2) {
        if (node1 == null && node2 == null) return true;
        if (node1 == null || node2 == null) return false;
        if (node1.val != node2.val) return false;

        return isMirror(node1.left, node2.right) && isMirror(node1.right, node2.left);
    }

    /**
     * Проверяет, является ли бинарное дерево зеркально симметричным (итеративный подход).
     * Использует очередь для попарного сравнения узлов на симметричных позициях.
     *
     * @param root Корневой узел.
     * @return true, если дерево симметрично, false иначе.
     */
    public boolean isSymmetricIterative(TreeNode root) {
        if (root == null) return true;

        Queue<TreeNode> queue = new LinkedList<>();
        // Добавляем левый и правый узлы корня (могут быть null)
        queue.offer(root.left);
        queue.offer(root.right);

        while (!queue.isEmpty()) {
            TreeNode node1 = queue.poll();
            TreeNode node2 = queue.poll();

            // Случаи, когда пара симметрична (оба null)
            if (node1 == null && node2 == null) continue;
            // Случаи, когда пара не симметрична (один null, или значения не равны)
            if (node1 == null || node2 == null || node1.val != node2.val) {
                return false;
            }

            // Добавляем потомков в очередь в зеркальном порядке для сравнения
            queue.offer(node1.left);  // Внешний левый
            queue.offer(node2.right); // Внешний правый
            queue.offer(node1.right); // Внутренний левый
            queue.offer(node2.left);  // Внутренний правый
        }
        return true;
    }
}
