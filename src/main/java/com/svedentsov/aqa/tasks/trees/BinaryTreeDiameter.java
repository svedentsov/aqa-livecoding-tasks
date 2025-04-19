package com.svedentsov.aqa.tasks.trees;

/**
 * Решение задачи №96: Диаметр бинарного дерева.
 * <p>
 * Описание: (Проверяет: рекурсия, деревья)
 * <p>
 * Задание: Напишите метод `int diameterOfBinaryTree(TreeNode root)`, который
 * вычисляет диаметр бинарного дерева (длину самого длинного пути между
 * любыми двумя узлами в дереве). Путь не обязательно проходит через корень.
 * Длина пути измеряется количеством ребер.
 * <p>
 * Пример: Для дерева `[1, 2, 3, 4, 5]`, диаметр равен 3
 * (путь `4 -> 2 -> 1 -> 3` или `5 -> 2 -> 1 -> 3`).
 */
public class BinaryTreeDiameter {

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

    // Поле для хранения максимального диаметра, найденного во время обхода.
    // Обнуляется перед каждым вызовом diameterOfBinaryTree.
    private int maxDiameter;

    /**
     * Вычисляет диаметр бинарного дерева (максимальное количество ребер между двумя узлами).
     *
     * @param root Корневой узел дерева.
     * @return Диаметр дерева. Возвращает 0 для пустого дерева.
     */
    public int diameterOfBinaryTree(TreeNode root) {
        maxDiameter = 0; // Сброс перед вычислением
        calculateHeightAndUpdateDiameter(root); // Запускаем рекурсию
        return maxDiameter;
    }

    /**
     * Точка входа для демонстрации вычисления диаметра дерева.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        BinaryTreeDiameter sol = new BinaryTreeDiameter();

        System.out.println("--- Calculating Binary Tree Diameter ---");

        // Пример 1
        TreeNode root1 = new TreeNode(1, new TreeNode(2, new TreeNode(4), new TreeNode(5)), new TreeNode(3));
        runDiameterTest(sol, root1, "Tree 1 [1,2,3,4,5]"); // Expected: 3

        // Пример 2
        TreeNode root2 = new TreeNode(1, new TreeNode(2), null);
        runDiameterTest(sol, root2, "Tree 2 [1,2]"); // Expected: 1

        // Пример 3: Пустое дерево
        runDiameterTest(sol, null, "Tree 3 (null)"); // Expected: 0

        // Пример 4: Только корень
        runDiameterTest(sol, new TreeNode(1), "Tree 4 [1]"); // Expected: 0

        // Пример 5: Линейное дерево
        TreeNode root5 = new TreeNode(1);
        root5.right = new TreeNode(2);
        root5.right.right = new TreeNode(3);
        root5.right.right.right = new TreeNode(4);
        runDiameterTest(sol, root5, "Tree 5 (linear)"); // Expected: 3

        // Пример 6
        TreeNode node5_6 = new TreeNode(5);
        TreeNode node6_6 = new TreeNode(6);
        TreeNode node3_6 = new TreeNode(3, node5_6, null);
        TreeNode node4_6 = new TreeNode(4, null, node6_6);
        TreeNode node2_6 = new TreeNode(2, node3_6, node4_6);
        TreeNode root6 = new TreeNode(1, node2_6, null);
        runDiameterTest(sol, root6, "Tree 6 (more complex)"); // Expected: 5 (5 -> 3 -> 2 -> 4 -> 6)
    }

    /**
     * Рекурсивно вычисляет высоту поддерева с корнем в {@code node} и попутно
     * обновляет максимальный диаметр {@code maxDiameter}.
     * Высота определяется как количество ребер на самом длинном пути от узла до листа.
     *
     * @param node Текущий узел.
     * @return Высота поддерева (-1 для null).
     */
    private int calculateHeightAndUpdateDiameter(TreeNode node) {
        // Базовый случай: высота пустого поддерева = -1
        if (node == null) {
            return -1;
        }

        // Рекурсивно находим высоту левого и правого поддеревьев
        int leftHeight = calculateHeightAndUpdateDiameter(node.left);
        int rightHeight = calculateHeightAndUpdateDiameter(node.right);

        // Диаметр пути, проходящего ЧЕРЕЗ текущий узел `node`:
        // (1 + высота левого) + (1 + высота правого) = 2 + leftHeight + rightHeight
        int diameterThroughNode = 2 + leftHeight + rightHeight;

        // Обновляем глобальный максимальный диаметр
        maxDiameter = Math.max(maxDiameter, diameterThroughNode);

        // Возвращаем высоту текущего узла
        return 1 + Math.max(leftHeight, rightHeight);
    }

    /**
     * Вспомогательный метод для тестирования diameterOfBinaryTree.
     *
     * @param sol         Экземпляр решателя.
     * @param root        Корень дерева.
     * @param description Описание теста.
     */
    private static void runDiameterTest(BinaryTreeDiameter sol, TreeNode root, String description) {
        System.out.println("\n--- " + description + " ---");
        // Печать дерева опциональна
        System.out.print("Input Tree Root: " + (root == null ? "null" : root.val));
        try {
            int diameter = sol.diameterOfBinaryTree(root);
            System.out.println(" -> Diameter: " + diameter);
            // Можно добавить сравнение с ожидаемым значением
        } catch (Exception e) {
            System.err.println(" -> Error: " + e.getMessage());
        }
    }
}
