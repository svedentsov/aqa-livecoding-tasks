package com.svedentsov.aqa.tasks.trees;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Решение задачи №94: Уровень узла в бинарном дереве.
 * Описание: (Проверяет: рекурсия/BFS, деревья)
 * Задание: Напишите метод `int getNodeLevel(TreeNode root, int key, int currentLevel)`,
 * который находит и возвращает уровень узла со значением `key` в бинарном дереве
 * (корень на уровне 1). Если узел не найден, верните -1.
 * (Представлены рекурсивный (DFS) и итеративный (BFS) подходы).
 * Пример: В дереве `[3, 9, 20, null, null, 15, 7]`, `getNodeLevel(root, 15, 1)` -> `3`.
 */
public class NodeLevelInTree {

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
     * Находит уровень узла с заданным значением (key) в бинарном дереве,
     * используя рекурсивный поиск в глубину (DFS).
     * Уровень корня считается равным 1.
     *
     * @param root Корневой узел дерева.
     * @param key  Значение искомого узла.
     * @return Уровень узла (начиная с 1), если найден, иначе -1.
     */
    public int getNodeLevelRecursive(TreeNode root, int key) {
        // Вспомогательный рекурсивный метод начинает поиск с уровня 1.
        return getNodeLevelRecursiveHelper(root, key, 1);
    }

    /**
     * Находит уровень узла с заданным значением (key) в бинарном дереве,
     * используя итеративный поиск в ширину (BFS).
     * Уровень корня считается равным 1.
     *
     * @param root Корневой узел дерева.
     * @param key  Значение искомого узла.
     * @return Уровень узла (начиная с 1), если он найден, иначе -1.
     */
    public int getNodeLevelBFS(TreeNode root, int key) {
        if (root == null) {
            return -1;
        }

        // Очередь для хранения пар <Узел, Уровень>
        Queue<NodeLevelPair> queue = new LinkedList<>();
        queue.offer(new NodeLevelPair(root, 1)); // Начинаем с корня (уровень 1)

        while (!queue.isEmpty()) {
            NodeLevelPair currentPair = queue.poll();
            TreeNode currentNode = currentPair.node;
            int currentLevel = currentPair.level;

            // Узел найден
            if (currentNode.val == key) {
                return currentLevel;
            }

            // Добавляем потомков в очередь
            if (currentNode.left != null) {
                queue.offer(new NodeLevelPair(currentNode.left, currentLevel + 1));
            }
            // Добавляем правого потомка в очередь (если он существует) с увеличенным уровнем
            if (currentNode.right != null) {
                queue.offer(new NodeLevelPair(currentNode.right, currentLevel + 1));
            }
        }

        // Узел не найден после обхода всего дерева
        return -1;
    }

    /**
     * Точка входа для демонстрации работы методов поиска уровня узла.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        NodeLevelInTree sol = new NodeLevelInTree();

        // Создаем тестовое дерево:
        //      3       (Уровень 1)
        //     / \
        //    9   20    (Уровень 2)
        //       /  \
        //      15   7  (Уровень 3)
        //     /
        //    10       (Уровень 4)
        TreeNode root = new TreeNode(3,
                new TreeNode(9),
                new TreeNode(20,
                        new TreeNode(15, new TreeNode(10), null),
                        new TreeNode(7)
                )
        );

        int[] keysToFind = {3, 9, 20, 15, 7, 10, 100, -5}; // Добавили 100 (несуществующий)
        //                  1, 2, 2,  3,  3,  4, -1, -1

        System.out.println("--- Finding Node Level in Binary Tree ---");

        for (int key : keysToFind) {
            runLevelTest(sol, root, key);
        }

        // Тест с пустым деревом
        System.out.println("\n--- Empty Tree Test ---");
        runLevelTest(sol, null, 5);

    }

    /**
     * Вспомогательный вложенный класс для хранения пары <Узел, Уровень> в BFS.
     */
    private static class NodeLevelPair {
        final TreeNode node;
        final int level;

        NodeLevelPair(TreeNode node, int level) {
            this.node = node;
            this.level = level;
        }
    }

    /**
     * Рекурсивный вспомогательный метод для поиска уровня узла (DFS).
     *
     * @param node         Текущий узел.
     * @param key          Искомое значение.
     * @param currentLevel Уровень текущего узла.
     * @return Уровень узла с ключом {@code key}, если найден, иначе -1.
     */
    private int getNodeLevelRecursiveHelper(TreeNode node, int key, int currentLevel) {
        // Базовый случай 1: Дошли до конца ветки, узел не найден
        if (node == null) {
            return -1;
        }
        // Базовый случай 2: Узел найден
        if (node.val == key) {
            return currentLevel;
        }

        // Рекурсивный поиск в левом поддереве
        int level = getNodeLevelRecursiveHelper(node.left, key, currentLevel + 1);
        if (level != -1) {
            return level; // Если нашли слева, возвращаем результат
        }

        // Если не нашли слева, ищем и возвращаем результат из правого поддерева
        return getNodeLevelRecursiveHelper(node.right, key, currentLevel + 1);
    }

    /**
     * Вспомогательный метод для тестирования getNodeLevel методов.
     *
     * @param sol  Экземпляр решателя.
     * @param root Корень дерева.
     * @param key  Искомое значение.
     */
    private static void runLevelTest(NodeLevelInTree sol, TreeNode root, int key) {
        System.out.println("\nSearching for key: " + key);
        try {
            int levelRec = sol.getNodeLevelRecursive(root, key); // Вызываем публичный метод
            System.out.println("  Level (Recursive DFS): " + levelRec);
        } catch (Exception e) {
            System.err.println("  Level (Recursive DFS): Error - " + e.getMessage());
        }
        try {
            int levelBFS = sol.getNodeLevelBFS(root, key);
            System.out.println("  Level (Iterative BFS): " + levelBFS);
        } catch (Exception e) {
            System.err.println("  Level (Iterative BFS): Error - " + e.getMessage());
        }
    }
}
