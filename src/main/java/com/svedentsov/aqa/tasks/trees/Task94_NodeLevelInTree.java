package com.svedentsov.aqa.tasks.trees;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Решение задачи №94: Уровень узла в бинарном дереве.
 * Содержит вложенный класс TreeNode и вспомогательный класс NodeLevelPair для BFS.
 */
public class Task94_NodeLevelInTree {

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
     * Вспомогательный вложенный статический класс для хранения пары <Узел, Уровень>
     * для использования в BFS обходе.
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
     * @param root         Корневой узел дерева (или поддерева при рекурсивном вызове).
     * @param key          Значение искомого узла.
     * @param currentLevel Текущий уровень узла {@code root} (начинается с 1 для корня всего дерева).
     * @return Уровень узла со значением {@code key}, если он найден (начиная с 1).
     * Возвращает -1, если узел не найден или дерево пустое.
     */
    public int getNodeLevelRecursive(TreeNode root, int key, int currentLevel) {
        // Базовый случай 1: Если узел null, ключ не найден на этом пути.
        if (root == null) {
            return -1; // Используем -1 как индикатор "не найдено"
        }
        // Базовый случай 2: Если значение текущего узла совпадает с ключом.
        if (root.val == key) {
            return currentLevel;
        }

        // Рекурсивно ищем ключ в левом поддереве.
        // Уровень для потомков увеличивается на 1.
        int levelInLeft = getNodeLevelRecursive(root.left, key, currentLevel + 1);

        // Если ключ был найден в левом поддереве (результат не -1),
        // то возвращаем найденный уровень вверх по стеку вызовов.
        if (levelInLeft != -1) {
            return levelInLeft;
        }

        // Если ключ не найден слева, ищем его в правом поддереве.
        int levelInRight = getNodeLevelRecursive(root.right, key, currentLevel + 1);

        // Возвращаем результат поиска в правом поддереве
        // (это будет либо найденный уровень, либо -1, если не найден и там).
        return levelInRight;
    }

    /**
     * Находит уровень узла с заданным значением (key) в бинарном дереве,
     * используя итеративный поиск в ширину (BFS).
     * Уровень корня считается равным 1.
     * BFS естественно обходит дерево по уровням.
     *
     * @param root Корневой узел дерева.
     * @param key  Значение искомого узла.
     * @return Уровень узла со значением {@code key} (начиная с 1), если он найден, иначе -1.
     */
    public int getNodeLevelBFS(TreeNode root, int key) {
        if (root == null) {
            return -1; // Узел не найден в пустом дереве
        }

        // Очередь для хранения пар <Узел, Уровень>
        Queue<NodeLevelPair> queue = new LinkedList<>();

        // Начинаем обход с корня на уровне 1
        queue.offer(new NodeLevelPair(root, 1));

        while (!queue.isEmpty()) {
            // Извлекаем узел и его уровень из очереди
            NodeLevelPair currentPair = queue.poll();
            TreeNode currentNode = currentPair.node;
            int currentLevel = currentPair.level;

            // Проверяем, является ли текущий узел искомым
            if (currentNode.val == key) {
                return currentLevel; // Узел найден, возвращаем его уровень
            }

            // Добавляем левого потомка в очередь (если он существует) с увеличенным уровнем
            if (currentNode.left != null) {
                queue.offer(new NodeLevelPair(currentNode.left, currentLevel + 1));
            }
            // Добавляем правого потомка в очередь (если он существует) с увеличенным уровнем
            if (currentNode.right != null) {
                queue.offer(new NodeLevelPair(currentNode.right, currentLevel + 1));
            }
        }

        // Если очередь опустела, а узел так и не был найден
        return -1;
    }

    /**
     * Точка входа для демонстрации работы методов поиска уровня узла.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task94_NodeLevelInTree sol = new Task94_NodeLevelInTree();
        // Создадим тестовое дерево:
        //      3       (Уровень 1)
        //     / \
        //    9   20    (Уровень 2)
        //       /  \
        //      15   7  (Уровень 3)
        //     /
        //    10       (Уровень 4)
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        root.right.left.left = new TreeNode(10); // Добавим узел уровня 4

        int[] keysToFind = {3, 9, 20, 15, 7, 10, 100};
        int[] expectedLevels = {1, 2, 2, 3, 3, 4, -1};

        System.out.println("--- Recursive DFS ---");
        for (int i = 0; i < keysToFind.length; i++) {
            int key = keysToFind[i];
            int level = sol.getNodeLevelRecursive(root, key, 1); // Начинаем с уровня 1
            System.out.println("Level of node " + key + ": " + level + " (Expected: " + expectedLevels[i] + ")");
            if (level != expectedLevels[i]) System.err.println("   Mismatch!");
        }

        System.out.println("\n--- Iterative BFS ---");
        for (int i = 0; i < keysToFind.length; i++) {
            int key = keysToFind[i];
            int level = sol.getNodeLevelBFS(root, key);
            System.out.println("Level of node " + key + ": " + level + " (Expected: " + expectedLevels[i] + ")");
            if (level != expectedLevels[i]) System.err.println("   Mismatch!");
        }

        // Тесты с пустым деревом
        System.out.println("\n--- Empty Tree ---");
        System.out.println("Recursive - Level of 5 in null tree: " + sol.getNodeLevelRecursive(null, 5, 1)); // -1
        System.out.println("BFS       - Level of 5 in null tree: " + sol.getNodeLevelBFS(null, 5)); // -1
    }
}
