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
 */
public class NodeLevelInTree {

    /**
     * Вложенный статический класс, представляющий узел бинарного дерева.
     * Сделаем его public для легкого доступа из тестов, если они в другом пакете,
     * или оставим package-private если тесты в том же пакете.
     * Для данного примера, оставим package-private (default visibility for class if no modifier).
     * В оригинале `static class TreeNode` - это package-private.
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
     * Вспомогательный вложенный класс для хранения пары <Узел, Уровень> в BFS.
     * Является деталью реализации BFS, поэтому private.
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
     * Рекурсивный вспомогательный метод для поиска уровня узла (DFS).
     */
    private int getNodeLevelRecursiveHelper(TreeNode node, int key, int currentLevel) {
        if (node == null) {
            return -1;
        }
        if (node.val == key) {
            return currentLevel;
        }

        int level = getNodeLevelRecursiveHelper(node.left, key, currentLevel + 1);
        if (level != -1) {
            return level;
        }
        return getNodeLevelRecursiveHelper(node.right, key, currentLevel + 1);
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
        return -1; // Узел не найден
    }
}
