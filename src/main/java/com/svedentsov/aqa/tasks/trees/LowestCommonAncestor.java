package com.svedentsov.aqa.tasks.trees;

import java.util.Objects;

/**
 * Решение задачи №95: Найти Наименьшего Общего Предка (Lowest Common Ancestor - LCA)
 * для двух узлов в Бинарном Дереве Поиска (BST) и в обычном Бинарном Дереве.
 * Описание: (Проверяет: рекурсия, деревья)
 * Задание (BST): Напишите метод `TreeNode lowestCommonAncestorBST(TreeNode root, TreeNode p, TreeNode q)`,
 * который находит LCA двух узлов `p` и `q` в бинарном дереве поиска `root`.
 * Задание (Binary Tree): Напишите метод `TreeNode lowestCommonAncestorBinaryTree(TreeNode root, TreeNode p, TreeNode q)`,
 * который находит LCA двух узлов `p` и `q` в обычном бинарном дереве `root`.
 * Пример: Для BST `[6, 2, 8, 0, 4, 7, 9, null, null, 3, 5]`, LCA(2, 8) -> 6. LCA(2, 4) -> 2.
 */
public class LowestCommonAncestor {

    /**
     * Вложенный статический класс, представляющий узел бинарного дерева.
     * Сделан public static для легкого доступа из тестов.
     */
    public static class TreeNode {
        public int val; // Сделаем public для простоты создания деревьев в тестах
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

        @Override
        public String toString() {
            return String.valueOf(this.val);
        }

        // Для удобства тестирования, чтобы можно было сравнивать узлы по значению в некоторых случаях,
        // но для LCA важно ссылочное равенство.
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TreeNode treeNode = (TreeNode) o;
            return val == treeNode.val; // Упрощенное equals для тестовых нужд, если нужно сравнить значения.
            // В реальных LCA тестах будем сравнивать ссылки.
        }

        @Override
        public int hashCode() {
            return Objects.hash(val);
        }
    }

    /**
     * Находит LCA двух узлов {@code p} и {@code q} в Бинарном Дереве Поиска (BST).
     * Итеративная реализация.
     * Предполагается, что p и q не null и присутствуют в дереве.
     *
     * @param root Корневой узел BST.
     * @param p    Первый узел.
     * @param q    Второй узел.
     * @return Узел, являющийся LCA для p и q. Возвращает null, если root равен null.
     */
    public TreeNode lowestCommonAncestorBST(TreeNode root, TreeNode p, TreeNode q) {
        Objects.requireNonNull(p, "Node p cannot be null");
        Objects.requireNonNull(q, "Node q cannot be null");

        TreeNode current = root;
        while (current != null) {
            // Если оба значения p и q больше значения текущего узла, LCA в правом поддереве
            if (p.val > current.val && q.val > current.val) {
                current = current.right;
            }
            // Если оба значения p и q меньше значения текущего узла, LCA в левом поддереве
            else if (p.val < current.val && q.val < current.val) {
                current = current.left;
            }
            // Иначе, текущий узел current является точкой расхождения или одним из узлов p/q.
            // Это и есть LCA.
            else {
                return current;
            }
        }
        return null; // Должно быть достигнуто только если root изначально null,
        // или если p/q не найдены (хотя метод предполагает их наличие)
    }

    /**
     * Находит LCA двух узлов {@code p} и {@code q} в обычном Бинарном Дереве (не BST).
     * Рекурсивная реализация.
     * Если один из узлов p или q не найден в дереве с корнем root,
     * и другой узел найден, то будет возвращен найденный узел.
     * Если оба не найдены, вернет null.
     *
     * @param root Корневой узел дерева.
     * @param p    Первый узел.
     * @param q    Второй узел.
     * @return Узел, являющийся LCA для p и q.
     */
    public TreeNode lowestCommonAncestorBinaryTree(TreeNode root, TreeNode p, TreeNode q) {
        Objects.requireNonNull(p, "Node p cannot be null");
        Objects.requireNonNull(q, "Node q cannot be null");

        // Базовый случай 1: Дошли до конца ветки (null)
        if (root == null) {
            return null;
        }
        // Базовый случай 2: Нашли один из искомых узлов (p или q)
        // Этот узел может быть LCA, если второй узел находится в его поддереве.
        if (root == p || root == q) {
            return root;
        }

        // Рекурсивно ищем p и q в левом и правом поддеревьях
        TreeNode leftLCA = lowestCommonAncestorBinaryTree(root.left, p, q);
        TreeNode rightLCA = lowestCommonAncestorBinaryTree(root.right, p, q);

        // Анализируем результаты рекурсии:
        // Если оба поддерева вернули не null, значит p и q находятся в разных
        // поддеревьях текущего узла `root`. Следовательно, `root` и есть их LCA.
        if (leftLCA != null && rightLCA != null) {
            return root;
        }

        // Если p и q найдены только в одном из поддеревьев (левом или правом),
        // то LCA для них уже был найден глубже в рекурсии и возвращен как
        // `leftLCA` или `rightLCA`. Возвращаем этот найденный LCA вверх по стеку.
        // Если ни в одном поддереве ничего не найдено (оба null), возвращаем null.
        return (leftLCA != null) ? leftLCA : rightLCA;
    }
}
