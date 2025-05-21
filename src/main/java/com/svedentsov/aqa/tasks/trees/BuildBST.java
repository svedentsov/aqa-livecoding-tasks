package com.svedentsov.aqa.tasks.trees;

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
    }

    /**
     * Вставляет значение {@code val} в Бинарное Дерево Поиска (BST) рекурсивно.
     * Сохраняет свойства BST: left.val < root.val < right.val.
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
        // Возвращаем неизмененный (или новый, если root был null) указатель на узел
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
}
