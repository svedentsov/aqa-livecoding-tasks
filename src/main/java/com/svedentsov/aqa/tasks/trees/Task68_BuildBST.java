package com.svedentsov.aqa.tasks.trees;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Решение задачи №68: Построить Бинарное Дерево Поиска (BST).
 * Содержит класс узла TreeNode и методы для вставки и построения дерева из массива.
 */
public class Task68_BuildBST {

    /**
     * Класс, представляющий узел бинарного дерева.
     * Содержит значение и ссылки на левого и правого потомков.
     */
    static class TreeNode { // Делаем static для доступа из static main
        int val;      // Значение узла
        TreeNode left;  // Ссылка на левого потомка
        TreeNode right; // Ссылка на правого потомка

        /**
         * Конструктор узла.
         *
         * @param val Значение узла.
         */
        TreeNode(int val) {
            this.val = val;
            this.left = null; // Инициализируем потомков как null
            this.right = null;
        }

        // --- Вспомогательные методы для печати дерева (для демонстрации) ---

        /**
         * Печатает дерево в порядке In-order (Left, Root, Right).
         */
        public void printInOrder() {
            printInOrderRecursive(this);
            System.out.println();
        }

        private void printInOrderRecursive(TreeNode node) {
            if (node == null) return;
            printInOrderRecursive(node.left);
            System.out.print(node.val + " ");
            printInOrderRecursive(node.right);
        }

        /**
         * Печатает дерево по уровням (BFS).
         */
        public void printLevelOrder() {
            if (this == null) {
                System.out.println("Tree is empty.");
                return;
            }
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(this);
            System.out.print("Level Order: [");
            boolean first = true;
            while (!queue.isEmpty()) {
                TreeNode current = queue.poll();
                if (!first) System.out.print(", ");
                System.out.print(current.val);
                first = false;
                if (current.left != null) queue.offer(current.left);
                if (current.right != null) queue.offer(current.right);
            }
            System.out.println("]");
        }
    }

    /**
     * Вставляет значение {@code val} в Бинарное Дерево Поиска (BST).
     * Если дерево пустое (root == null), создает новый узел со значением {@code val}
     * и возвращает его как новый корень.
     * В противном случае, рекурсивно находит подходящее место для вставки,
     * сохраняя свойства BST:
     * - Все значения в левом поддереве узла строго меньше значения узла.
     * - Все значения в правом поддереве узла строго больше значения узла.
     * Дубликаты значений игнорируются (не вставляются).
     *
     * @param root Корневой узел текущего поддерева (изначально корень всего дерева).
     * @param val  Значение для вставки.
     * @return Корневой узел обновленного поддерева (или всего дерева). Обычно это тот же {@code root},
     * кроме случая вставки в пустое дерево.
     */
    public TreeNode insertIntoBST(TreeNode root, int val) {
        // Базовый случай: если достигли пустого места (null), создаем и возвращаем новый узел.
        if (root == null) {
            return new TreeNode(val);
        }

        // Рекурсивный спуск по дереву в зависимости от значения val:
        if (val < root.val) {
            // Если значение меньше корня, идем в левое поддерево.
            // Результат рекурсивной вставки присваиваем левому потомку.
            root.left = insertIntoBST(root.left, val);
        } else if (val > root.val) {
            // Если значение больше корня, идем в правое поддерево.
            root.right = insertIntoBST(root.right, val);
        }
        // Если val == root.val, то дубликат игнорируется. Ничего не делаем.

        // Возвращаем текущий узел (root). Его потомки могли обновиться в рекурсивных вызовах.
        return root;
    }

    /**
     * Строит Бинарное Дерево Поиска (BST) из массива целочисленных значений.
     * Последовательно вставляет каждое значение из массива в дерево, начиная с пустого.
     * Порядок элементов в массиве влияет на структуру (но не на inorder обход) результирующего дерева.
     *
     * @param values Массив значений для построения дерева. Может быть null.
     * @return Корневой узел построенного BST или null, если массив пуст или null.
     */
    public TreeNode buildBSTFromArray(int[] values) {
        TreeNode root = null; // Начинаем с пустого дерева
        if (values != null) {
            for (int val : values) {
                // Последовательно вставляем каждое значение
                root = insertIntoBST(root, val);
            }
        }
        return root;
    }

    /**
     * Точка входа для демонстрации построения и вставки в BST.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task68_BuildBST sol = new Task68_BuildBST();
        int[] values = {4, 2, 7, 1, 3, 6, 9}; // Массив для построения дерева
        System.out.println("Building BST from: " + Arrays.toString(values));

        // Строим дерево из массива
        TreeNode root = sol.buildBSTFromArray(values);

        System.out.println("\nBST Structure:");
        System.out.print(" In-order (sorted): ");
        if (root != null) root.printInOrder();
        else System.out.println("empty");
        System.out.print(" Level-order: ");
        if (root != null) root.printLevelOrder();
        else System.out.println("empty");


        // Вставляем новые элементы
        int[] newValues = {5, 8, 0};
        System.out.println("\nInserting values: " + Arrays.toString(newValues));
        for (int val : newValues) {
            System.out.println(" Inserting " + val + "...");
            root = sol.insertIntoBST(root, val); // Вставляем и обновляем корень (хотя он не изменится)
        }

        System.out.println("\nBST Structure after insertions:");
        System.out.print(" In-order (sorted): ");
        if (root != null) root.printInOrder();
        else System.out.println("empty"); // 0 1 2 3 4 5 6 7 8 9
        System.out.print(" Level-order: ");
        if (root != null) root.printLevelOrder();
        else System.out.println("empty");


        // Вставка дубликата
        int duplicateValue = 7;
        System.out.println("\nInserting duplicate " + duplicateValue + "...");
        root = sol.insertIntoBST(root, duplicateValue);
        System.out.println("\nBST Structure after inserting duplicate (should be unchanged):");
        System.out.print(" In-order (sorted): ");
        if (root != null) root.printInOrder();
        else System.out.println("empty"); // 0 1 2 3 4 5 6 7 8 9
    }
}
