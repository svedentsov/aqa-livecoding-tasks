package com.svedentsov.aqa.tasks.trees;

/**
 * Решение задачи №96: Диаметр бинарного дерева.
 * Описание: (Проверяет: рекурсия, деревья)
 * Задание: Напишите метод `int diameterOfBinaryTree(TreeNode root)`, который
 * вычисляет диаметр бинарного дерева (длину самого длинного пути между
 * любыми двумя узлами в дереве). Путь не обязательно проходит через корень.
 * Длина пути измеряется количеством ребер.
 * Пример: Для дерева `[1, 2, 3, 4, 5]`, диаметр равен 3
 * (путь `4 -> 2 -> 1 -> 3` или `5 -> 2 -> 1 -> 3`).
 */
public class BinaryTreeDiameter {

    /**
     * Вложенный статический класс, представляющий узел бинарного дерева.
     * Сделан public static для удобства использования в тестах.
     */
    public static class TreeNode {
        public int val;
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

    // Поле для хранения максимального диаметра, найденного во время обхода.
    // Это поле делает экземпляр класса stateful во время вызова diameterOfBinaryTree.
    // Оно сбрасывается при каждом вызове публичного метода.
    private int maxDiameter;

    /**
     * Вычисляет диаметр бинарного дерева (максимальное количество ребер между двумя узлами).
     *
     * @param root Корневой узел дерева.
     * @return Диаметр дерева. Возвращает 0 для пустого дерева или дерева из одного узла.
     */
    public int diameterOfBinaryTree(TreeNode root) {
        maxDiameter = 0; // Сброс перед каждым вычислением
        calculateHeightAndUpdateDiameter(root);
        return maxDiameter;
    }

    /**
     * Рекурсивно вычисляет высоту поддерева с корнем в {@code node} и попутно
     * обновляет максимальный диаметр {@code maxDiameter}.
     * Высота определяется как количество ребер на самом длинном пути от узла до листа.
     * Высота null-узла равна -1, высота листа равна 0.
     *
     * @param node Текущий узел.
     * @return Высота поддерева (количество ребер от узла до самого дальнего листа).
     */
    private int calculateHeightAndUpdateDiameter(TreeNode node) {
        if (node == null) {
            return -1; // Высота пустого поддерева (помогает в расчете диаметра)
        }

        int leftHeight = calculateHeightAndUpdateDiameter(node.left);
        int rightHeight = calculateHeightAndUpdateDiameter(node.right);

        // Диаметр пути, проходящего ЧЕРЕЗ текущий узел `node`:
        // это (1 + высота левого поддерева) + (1 + высота правого поддерева).
        // (1 + leftHeight) - это количество ребер от node до самого дальнего листа в левом поддереве.
        // (1 + rightHeight) - это количество ребер от node до самого дальнего листа в правом поддереве.
        // Сумма этих двух длин дает путь через node.
        int diameterThroughNode = (leftHeight + 1) + (rightHeight + 1); // Эквивалентно leftHeight + rightHeight + 2

        // Обновляем глобальный максимальный диаметр, если текущий диаметр через узел больше.
        maxDiameter = Math.max(maxDiameter, diameterThroughNode);

        // Возвращаем высоту текущего узла: 1 (ребро к ребенку) + максимальная высота из поддеревьев.
        return 1 + Math.max(leftHeight, rightHeight);
    }
}
