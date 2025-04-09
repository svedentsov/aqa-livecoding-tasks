package com.svedentsov.aqa.tasks.trees;

/**
 * Решение задачи №96: Диаметр бинарного дерева.
 * Содержит вложенный класс TreeNode.
 */
public class Task96_BinaryTreeDiameter {

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
    }

    // Переменная для хранения максимального диаметра, найденного во время обхода.
    // Используется как поле класса, сбрасывается перед каждым вызовом основного метода.
    // Альтернатива: передавать объект-контейнер (например, int[1]) в рекурсию.
    private int maxDiameter;

    /**
     * Вычисляет диаметр бинарного дерева.
     * Диаметр - это длина (количество ребер) самого длинного пути между
     * любыми двумя узлами в дереве. Этот путь может проходить или не проходить через корень.
     * Алгоритм:
     * Использует рекурсивный метод для вычисления высоты каждого поддерева.
     * Во время вычисления высоты, для каждого узла также вычисляется
     * "диаметр через этот узел", который равен сумме высот его левого и правого
     * поддеревьев + 2 (если считать по ребрам) или +1 (если считать по узлам).
     * Максимальный из всех таких "диаметров через узел" и есть итоговый диаметр дерева.
     *
     * @param root Корневой узел дерева.
     * @return Диаметр дерева (максимальное количество ребер на самом длинном пути).
     * Возвращает 0 для пустого дерева или дерева с одним узлом.
     */
    public int diameterOfBinaryTree(TreeNode root) {
        // Сбрасываем максимальный диаметр перед началом вычислений для нового дерева
        maxDiameter = 0;
        // Запускаем рекурсивное вычисление высоты, которое попутно обновит maxDiameter
        calculateHeightAndUpdateDiameter(root);
        // Возвращаем найденный максимальный диаметр
        return maxDiameter;
    }

    /**
     * Рекурсивно вычисляет высоту поддерева с корнем в {@code node}.
     * Высота узла - это количество ребер на самом длинном пути от него до листового узла.
     * Высота null-узла = -1. Высота листа = 0.
     * ПОБОЧНЫЙ ЭФФЕКТ: Вычисляет диаметр, проходящий через {@code node},
     * и обновляет глобальный максимум {@code maxDiameter}, если найден больший диаметр.
     *
     * @param node Текущий узел.
     * @return Высота поддерева с корнем в {@code node}.
     */
    private int calculateHeightAndUpdateDiameter(TreeNode node) {
        // Базовый случай: высота пустого поддерева равна -1 (нет ребер)
        if (node == null) {
            return -1;
        }

        // Рекурсивно вычисляем высоту левого и правого поддеревьев
        int leftHeight = calculateHeightAndUpdateDiameter(node.left);
        int rightHeight = calculateHeightAndUpdateDiameter(node.right);

        // Вычисляем диаметр пути, который проходит через ТЕКУЩИЙ узел `node`.
        // Он равен сумме длин самых длинных путей в левом и правом поддеревьях,
        // идущих от `node`. Длина пути = высота + 1 ребро.
        // currentDiameter = (длина левого пути) + (длина правого пути)
        //                 = (leftHeight + 1) + (rightHeight + 1)
        int currentDiameter = leftHeight + rightHeight + 2;

        // Обновляем максимальный диаметр, найденный во всем дереве до сих пор
        maxDiameter = Math.max(maxDiameter, currentDiameter);

        // Возвращаем высоту текущего узла:
        // это максимальная из высот его поддеревьев + 1 (ребро к текущему узлу)
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * Точка входа для демонстрации вычисления диаметра дерева.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task96_BinaryTreeDiameter sol = new Task96_BinaryTreeDiameter();

        // Дерево 1:
        //      1
        //     / \
        //    2   3
        //   / \
        //  4   5
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(3);
        root1.left.left = new TreeNode(4);
        root1.left.right = new TreeNode(5);
        System.out.println("Diameter of Tree 1 ([1,2,3,4,5]): " + sol.diameterOfBinaryTree(root1)); // 3 (путь 4-2-1-3 или 5-2-1-3)

        // Дерево 2:
        //      1
        //     /
        //    2
        TreeNode root2 = new TreeNode(1);
        root2.left = new TreeNode(2);
        System.out.println("Diameter of Tree 2 ([1,2]): " + sol.diameterOfBinaryTree(root2)); // 1 (путь 2-1)

        // Дерево 3: пустое
        System.out.println("Diameter of Tree 3 (null): " + sol.diameterOfBinaryTree(null)); // 0

        // Дерево 4: только корень
        TreeNode root4 = new TreeNode(1);
        System.out.println("Diameter of Tree 4 ([1]): " + sol.diameterOfBinaryTree(root4)); // 0

        // Дерево 5: Линейное дерево
        // 1 -> 2 -> 3 -> 4 (все правые потомки)
        TreeNode root5 = new TreeNode(1);
        root5.right = new TreeNode(2);
        root5.right.right = new TreeNode(3);
        root5.right.right.right = new TreeNode(4);
        System.out.println("Diameter of Tree 5 (linear): " + sol.diameterOfBinaryTree(root5)); // 3 (путь 1-2-3-4)

        // Дерево 6:
        //       1
        //      /
        //     2
        //    / \
        //   3   4
        //  /     \
        // 5       6
        TreeNode root6 = new TreeNode(1);
        root6.left = new TreeNode(2);
        root6.left.left = new TreeNode(3);
        root6.left.right = new TreeNode(4);
        root6.left.left.left = new TreeNode(5);
        root6.left.right.right = new TreeNode(6);
        System.out.println("Diameter of Tree 6: " + sol.diameterOfBinaryTree(root6)); // 5 (путь 5-3-2-4-6)

    }
}
