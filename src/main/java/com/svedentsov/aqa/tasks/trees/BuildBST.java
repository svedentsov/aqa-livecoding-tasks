package com.svedentsov.aqa.tasks.trees;

import java.util.*;

/**
 * Решение задачи №68: Построить Бинарное Дерево Поиска (BST).
 * Описание: Вставить элементы из массива в BST.
 * (Проверяет: основы деревьев, рекурсия, ООП)
 * Задание: Реализуйте класс `TreeNode` и метод `TreeNode insertIntoBST(TreeNode root, int val)`,
 * который вставляет значение `val` в бинарное дерево поиска с корнем `root` и
 * возвращает корень обновленного дерева. Реализуйте также метод для построения
 * BST из массива.
 * Пример: `insertIntoBST(root, 5)` вставит 5 в соответствующее место дерева.
 */
public class BuildBST {

    // --- Класс Узла Дерева ---

    /**
     * Класс, представляющий узел бинарного дерева.
     */
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }

        /**
         * Печатает дерево в порядке In-order (Left, Root, Right).
         * Полезно для проверки BST, т.к. выводит отсортированные значения.
         */
        public void printInOrder() {
            System.out.print("In-order: [");
            List<String> elements = new ArrayList<>();
            printInOrderRecursive(this, elements);
            System.out.print(String.join(", ", elements));
            System.out.println("]");
        }

        /**
         * Печатает дерево по уровням (BFS - Поиск в ширину).
         * Полезно для визуализации структуры дерева.
         */
        public void printLevelOrder() {
            if (this == null) {
                System.out.println("Level Order: [] (empty tree)");
                return;
            }
            System.out.print("Level Order: [");
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(this);
            boolean first = true;

            while (!queue.isEmpty()) {
                int levelSize = queue.size(); // Обработка узлов текущего уровня
                for (int i = 0; i < levelSize; i++) {
                    TreeNode current = queue.poll();
                    if (!first) System.out.print(", ");
                    System.out.print(current.val);
                    first = false;

                    if (current.left != null) queue.offer(current.left);
                    if (current.right != null) queue.offer(current.right);
                }
            }
            System.out.println("]");
        }

        // --- Приватные вспомогательные методы для TreeNode ---

        /**
         * Рекурсивный помощник для In-order обхода.
         */
        private void printInOrderRecursive(TreeNode node, List<String> elements) {
            if (node == null) return;
            printInOrderRecursive(node.left, elements);
            elements.add(String.valueOf(node.val));
            printInOrderRecursive(node.right, elements);
        }
    }


    // --- Методы для работы с BST ---

    /**
     * Вставляет значение {@code val} в Бинарное Дерево Поиска (BST) рекурсивно.
     * Сохраняет свойства BST: left < root < right.
     * Дубликаты значений игнорируются (не вставляются).
     *
     * @param root Корневой узел текущего поддерева.
     * @param val  Значение для вставки.
     * @return Корневой узел обновленного поддерева.
     */
    public TreeNode insertIntoBST(TreeNode root, int val) {
        // Базовый случай: Найдено пустое место для вставки
        if (root == null) {
            return new TreeNode(val);
        }

        // Рекурсивный поиск места для вставки
        if (val < root.val) {
            // Идем влево
            root.left = insertIntoBST(root.left, val);
        } else if (val > root.val) {
            // Идем вправо
            root.right = insertIntoBST(root.right, val);
        }
        // Если val == root.val, ничего не делаем (игнорируем дубликат)

        // Возвращаем неизмененный указатель на узел (если не был null)
        return root;
    }

    /**
     * Строит Бинарное Дерево Поиска (BST) из массива целочисленных значений.
     * Последовательно вставляет каждое значение из массива в дерево.
     *
     * @param values Массив значений. Может быть null.
     * @return Корневой узел построенного BST или null, если массив пуст или null.
     */
    public TreeNode buildBSTFromArray(int[] values) {
        TreeNode root = null;
        if (values != null) {
            for (int val : values) {
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
        BuildBST sol = new BuildBST();

        System.out.println("--- Building and Modifying Binary Search Tree ---");

        // Тест 1: Основной пример
        int[] values1 = {4, 2, 7, 1, 3, 6, 9};
        System.out.println("\nTest 1: Build from " + Arrays.toString(values1));
        TreeNode root1 = sol.buildBSTFromArray(values1);
        if (root1 != null) {
            root1.printInOrder();
            root1.printLevelOrder();
        } else {
            System.out.println("Result: null");
        }

        // Тест 2: Вставка новых элементов
        int[] newValues = {5, 8, 0};
        System.out.println("\nTest 2: Insert values " + Arrays.toString(newValues) + " into Tree 1");
        for (int val : newValues) {
            root1 = sol.insertIntoBST(root1, val);
        }
        if (root1 != null) {
            root1.printInOrder();
            root1.printLevelOrder();
        } else {
            System.out.println("Result: null");
        }

        // Тест 3: Вставка дубликата
        System.out.println("\nTest 3: Insert duplicate value 7 into Tree 1");
        root1 = sol.insertIntoBST(root1, 7);
        if (root1 != null) {
            root1.printInOrder(); // Должен остаться таким же
            root1.printLevelOrder(); // Структура не должна измениться
        } else {
            System.out.println("Result: null");
        }

        // Тест 4: Пустой массив
        System.out.println("\nTest 4: Build from empty array");
        TreeNode rootEmpty = sol.buildBSTFromArray(new int[]{});
        if (rootEmpty != null) {
            rootEmpty.printInOrder();
        } else {
            System.out.println("Result: null");
        }

        // Тест 5: Null массив
        System.out.println("\nTest 5: Build from null array");
        TreeNode rootNull = sol.buildBSTFromArray(null);
        if (rootNull != null) {
            rootNull.printInOrder();
        } else {
            System.out.println("Result: null");
        }

        // Тест 6: Отсортированный массив (дегенерированное дерево)
        int[] valuesSorted = {1, 2, 3, 4, 5};
        System.out.println("\nTest 6: Build from sorted " + Arrays.toString(valuesSorted));
        TreeNode rootSorted = sol.buildBSTFromArray(valuesSorted);
        if (rootSorted != null) {
            rootSorted.printInOrder();
            rootSorted.printLevelOrder(); // Будет выглядеть как список
        } else {
            System.out.println("Result: null");
        }
    }
}
