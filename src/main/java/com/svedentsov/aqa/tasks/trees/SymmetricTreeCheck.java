package com.svedentsov.aqa.tasks.trees;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Решение задачи №97: Проверка на симметричность дерева.
 * <p>
 * Описание: (Проверяет: рекурсия, деревья)
 * <p>
 * Задание: Напишите метод `boolean isSymmetric(TreeNode root)`, который проверяет,
 * является ли бинарное дерево зеркально симметричным относительно своего центра.
 * <p>
 * Пример: Дерево `[1, 2, 2, 3, 4, 4, 3]` -> `true`.
 * Дерево `[1, 2, 2, null, 3, null, 3]` -> `false`.
 */
public class SymmetricTreeCheck {

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

        TreeNode(int val, TreeNode left, TreeNode right) {
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
            // Извлекаем пару узлов для сравнения
            TreeNode node1 = queue.poll();
            TreeNode node2 = queue.poll();

            // Случаи, когда пара симметрична (оба null)
            if (node1 == null && node2 == null) continue;
            // Случаи, когда пара не симметрична (один null, или значения не равны)
            if (node1 == null || node2 == null || node1.val != node2.val) {
                return false;
            }

            // Добавляем потомков в очередь в зеркальном порядке
            queue.offer(node1.left);  // Левый от первого
            queue.offer(node2.right); // Правый от второго
            queue.offer(node1.right); // Правый от первого
            queue.offer(node2.left);  // Левый от второго
        }
        // Если очередь опустела без нарушений -> симметрично
        return true;
    }

    /**
     * Точка входа для демонстрации работы методов проверки симметричности.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        SymmetricTreeCheck sol = new SymmetricTreeCheck();

        System.out.println("--- Checking for Symmetric Trees ---");

        // Пример 1: Симметричное
        TreeNode root1 = new TreeNode(1,
                new TreeNode(2, new TreeNode(3), new TreeNode(4)),
                new TreeNode(2, new TreeNode(4), new TreeNode(3)));
        runSymmetricTest(sol, root1, "Tree 1 (Symmetric)"); // true

        // Пример 2: Несимметричное
        TreeNode root2 = new TreeNode(1,
                new TreeNode(2, null, new TreeNode(3)),
                new TreeNode(2, null, new TreeNode(3)));
        runSymmetricTest(sol, root2, "Tree 2 (Asymmetric)"); // false

        // Пример 3: Пустое
        runSymmetricTest(sol, null, "Tree 3 (null)"); // true

        // Пример 4: Один узел
        runSymmetricTest(sol, new TreeNode(1), "Tree 4 (Single node)"); // true

        // Пример 5: Асимметрия по значению
        TreeNode root5 = new TreeNode(1, new TreeNode(2), new TreeNode(3));
        runSymmetricTest(sol, root5, "Tree 5 (Asymmetric values)"); // false

        // Пример 6: Асимметрия по структуре
        TreeNode root6 = new TreeNode(1, new TreeNode(2, new TreeNode(3), null), new TreeNode(2, new TreeNode(3), null));
        runSymmetricTest(sol, root6, "Tree 6 (Asymmetric structure)"); // false
    }

    /**
     * Рекурсивный вспомогательный метод для проверки зеркальности двух поддеревьев.
     *
     * @param node1 Корень первого поддерева.
     * @param node2 Корень второго поддерева.
     * @return {@code true}, если поддеревья зеркальны, {@code false} иначе.
     */
    private boolean isMirror(TreeNode node1, TreeNode node2) {
        // Оба null -> симметричны
        if (node1 == null && node2 == null) return true;
        // Только один null -> не симметричны
        if (node1 == null || node2 == null) return false;
        // Значения разные -> не симметричны
        if (node1.val != node2.val) return false;

        // Рекурсивно проверяем внешние и внутренние поддеревья
        return isMirror(node1.left, node2.right) && isMirror(node1.right, node2.left);
    }

    /**
     * Вспомогательный метод для тестирования isSymmetric.
     *
     * @param sol         Экземпляр решателя.
     * @param root        Корень дерева.
     * @param description Описание теста.
     */
    private static void runSymmetricTest(SymmetricTreeCheck sol, TreeNode root, String description) {
        System.out.println("\n--- " + description + " ---");
        // Печать дерева для визуализации может быть сложной, пропустим
        System.out.println("Input root value: " + (root == null ? "null" : root.val));
        try {
            boolean resultRec = sol.isSymmetricRecursive(root);
            System.out.println("  Result (Recursive): " + resultRec);
        } catch (Exception e) {
            System.err.println("  Result (Recursive): Error - " + e.getMessage());
        }
        try {
            boolean resultIter = sol.isSymmetricIterative(root);
            System.out.println("  Result (Iterative): " + resultIter);
        } catch (Exception e) {
            System.err.println("  Result (Iterative): Error - " + e.getMessage());
        }
    }
}
