package com.svedentsov.aqa.tasks.trees;

import java.util.Objects; // Для проверки на null

/**
 * Решение задачи №95: Найти Наименьшего Общего Предка (Lowest Common Ancestor - LCA)
 * для двух узлов в Бинарном Дереве Поиска (BST) и в обычном Бинарном Дереве.
 * Содержит вложенный класс TreeNode.
 */
public class Task95_LowestCommonAncestor {

    /**
     * Вложенный статический класс, представляющий узел бинарного дерева.
     */
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int val) { this.val = val; }
        // Конструктор для удобства создания дерева в main
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val; this.left = left; this.right = right;
        }
    }

    /**
     * Находит Наименьшего Общего Предка (LCA) двух заданных узлов {@code p} и {@code q}
     * в Бинарном Дереве Поиска (BST) {@code root}.
     * LCA(p, q) - это самый нижний узел в дереве, который имеет и p, и q в качестве своих потомков.
     * Использует свойства BST для поиска за O(h) времени.
     *
     * @param root Корневой узел BST (не null, если p и q существуют).
     * @param p    Первый узел (не null, существует в дереве).
     * @param q    Второй узел (не null, существует в дереве).
     * @return Узел, являющийся LCA для p и q.
     */
    public TreeNode lowestCommonAncestorBST(TreeNode root, TreeNode p, TreeNode q) {
        // Проверки на null для p и q добавлены в вызывающий метод или предполагаются
        Objects.requireNonNull(p, "Node p cannot be null");
        Objects.requireNonNull(q, "Node q cannot be null");

        // Итеративная версия (эффективнее по памяти)
        TreeNode current = root;
        while (current != null) {
            int rootVal = current.val;
            int pVal = p.val;
            int qVal = q.val;

            if (pVal > rootVal && qVal > rootVal) {
                // Оба узла справа, идем вправо
                current = current.right;
            } else if (pVal < rootVal && qVal < rootVal) {
                // Оба узла слева, идем влево
                current = current.left;
            } else {
                // Нашли точку расхождения или один из узлов равен текущему - это LCA
                return current;
            }
        }
        return null; // Сюда не должны дойти, если p и q существуют в дереве с корнем root
    }

    /**
     * Находит Наименьшего Общего Предка (LCA) двух заданных узлов {@code p} и {@code q}
     * в обычном Бинарном Дереве (не обязательно BST).
     * Использует рекурсивный подход.
     * Сложность O(n) по времени, O(h) по памяти (стек рекурсии).
     *
     * @param root Корневой узел дерева.
     * @param p    Первый узел (не null, предполагается существующим в дереве).
     * @param q    Второй узел (не null, предполагается существующим в дереве).
     * @return Узел, являющийся LCA для p и q, или null если {@code root} null.
     */
    public TreeNode lowestCommonAncestorBinaryTree(TreeNode root, TreeNode p, TreeNode q) {
        Objects.requireNonNull(p, "Node p cannot be null");
        Objects.requireNonNull(q, "Node q cannot be null");

        // Базовый случай: root=null или root является одним из искомых узлов
        if (root == null || root == p || root == q) {
            return root;
        }

        // Рекурсивный поиск в левом и правом поддеревьях
        TreeNode leftResult = lowestCommonAncestorBinaryTree(root.left, p, q);
        TreeNode rightResult = lowestCommonAncestorBinaryTree(root.right, p, q);

        // Анализ результатов:
        // Если оба поддерева вернули не null, значит p и q находятся
        // в разных поддеревьях, и текущий `root` - их LCA.
        if (leftResult != null && rightResult != null) {
            return root;
        }

        // Иначе (если один из результатов null), LCA находится в том поддереве,
        // которое вернуло не null результат (или null, если оба вернули null).
        return (leftResult != null) ? leftResult : rightResult;
    }

    /**
     * Точка входа для демонстрации работы методов поиска LCA.
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task95_LowestCommonAncestor sol = new Task95_LowestCommonAncestor();

        // --- BST Example ---
        System.out.println("--- Lowest Common Ancestor in BST ---");
        // Создаем BST
        TreeNode node3 = new TreeNode(3);
        TreeNode node5 = new TreeNode(5);
        TreeNode node0 = new TreeNode(0);
        TreeNode node4 = new TreeNode(4, node3, node5); // Узел 4
        TreeNode node2 = new TreeNode(2, node0, node4); // Узел 2
        TreeNode node7 = new TreeNode(7);
        TreeNode node9 = new TreeNode(9);
        TreeNode node8 = new TreeNode(8, node7, node9); // Узел 8
        TreeNode rootBST = new TreeNode(6, node2, node8); // Узел 6 (корень)

        TreeNode lcaBST1 = sol.lowestCommonAncestorBST(rootBST, node2, node8);
        System.out.println("LCA(BST, 2, 8): " + (lcaBST1 != null ? lcaBST1.val : "null")); // Expected: 6

        TreeNode lcaBST2 = sol.lowestCommonAncestorBST(rootBST, node2, node4);
        System.out.println("LCA(BST, 2, 4): " + (lcaBST2 != null ? lcaBST2.val : "null")); // Expected: 2

        TreeNode lcaBST3 = sol.lowestCommonAncestorBST(rootBST, node0, node5);
        System.out.println("LCA(BST, 0, 5): " + (lcaBST3 != null ? lcaBST3.val : "null")); // Expected: 2

        TreeNode lcaBST4 = sol.lowestCommonAncestorBST(rootBST, node3, node5);
        System.out.println("LCA(BST, 3, 5): " + (lcaBST4 != null ? lcaBST4.val : "null")); // Expected: 4

        TreeNode lcaBST5 = sol.lowestCommonAncestorBST(rootBST, node7, node9);
        System.out.println("LCA(BST, 7, 9): " + (lcaBST5 != null ? lcaBST5.val : "null")); // Expected: 8


        // --- Binary Tree Example ---
        System.out.println("\n--- Lowest Common Ancestor in Binary Tree ---");
        // Создаем дерево (не BST)
        TreeNode node7_bt = new TreeNode(7);
        TreeNode node4_bt = new TreeNode(4);
        TreeNode node6_bt = new TreeNode(6);
        TreeNode node2_bt = new TreeNode(2, node7_bt, node4_bt);
        TreeNode node5_bt = new TreeNode(5, node6_bt, node2_bt); // Узел 5
        TreeNode node0_bt = new TreeNode(0);
        TreeNode node8_bt = new TreeNode(8);
        TreeNode node1_bt = new TreeNode(1, node0_bt, node8_bt); // Узел 1
        TreeNode rootBT = new TreeNode(3, node5_bt, node1_bt);   // Узел 3 (корень)


        TreeNode lcaBT1 = sol.lowestCommonAncestorBinaryTree(rootBT, node5_bt, node1_bt);
        System.out.println("LCA(BT, 5, 1): " + (lcaBT1 != null ? lcaBT1.val : "null")); // Expected: 3

        TreeNode lcaBT2 = sol.lowestCommonAncestorBinaryTree(rootBT, node5_bt, node4_bt);
        System.out.println("LCA(BT, 5, 4): " + (lcaBT2 != null ? lcaBT2.val : "null")); // Expected: 5

        TreeNode lcaBT3 = sol.lowestCommonAncestorBinaryTree(rootBT, node6_bt, node4_bt);
        System.out.println("LCA(BT, 6, 4): " + (lcaBT3 != null ? lcaBT3.val : "null")); // Expected: 5

        TreeNode lcaBT4 = sol.lowestCommonAncestorBinaryTree(rootBT, node0_bt, node8_bt);
        System.out.println("LCA(BT, 0, 8): " + (lcaBT4 != null ? lcaBT4.val : "null")); // Expected: 1

        TreeNode lcaBT5 = sol.lowestCommonAncestorBinaryTree(rootBT, node7_bt, node4_bt);
        System.out.println("LCA(BT, 7, 4): " + (lcaBT5 != null ? lcaBT5.val : "null")); // Expected: 2

        TreeNode lcaBT6 = sol.lowestCommonAncestorBinaryTree(rootBT, node6_bt, node6_bt); // LCA узла с самим собой
        System.out.println("LCA(BT, 6, 6): " + (lcaBT6 != null ? lcaBT6.val : "null")); // Expected: 6
    }
}
