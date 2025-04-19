package com.svedentsov.aqa.tasks.trees;

import java.util.Objects;

/**
 * Решение задачи №95: Найти Наименьшего Общего Предка (Lowest Common Ancestor - LCA)
 * для двух узлов в Бинарном Дереве Поиска (BST) и в обычном Бинарном Дереве.
 * <p>
 * Описание: (Проверяет: рекурсия, деревья)
 * <p>
 * Задание (BST): Напишите метод `TreeNode lowestCommonAncestorBST(TreeNode root, TreeNode p, TreeNode q)`,
 * который находит LCA двух узлов `p` и `q` в бинарном дереве поиска `root`.
 * Задание (Binary Tree): Напишите метод `TreeNode lowestCommonAncestorBinaryTree(TreeNode root, TreeNode p, TreeNode q)`,
 * который находит LCA двух узлов `p` и `q` в обычном бинарном дереве `root`.
 * <p>
 * Пример: Для BST `[6, 2, 8, 0, 4, 7, 9, null, null, 3, 5]`, LCA(2, 8) -> 6. LCA(2, 4) -> 2.
 */
public class LowestCommonAncestor {

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

        // Переопределяем toString для более читаемого вывода
        @Override
        public String toString() {
            return String.valueOf(this.val);
        }
    }

    /**
     * Находит LCA двух узлов {@code p} и {@code q} в Бинарном Дереве Поиска (BST).
     * Использует свойства BST (left < root < right) для эффективного поиска за O(h),
     * где h - высота дерева. Реализация итеративная.
     *
     * @param root Корневой узел BST.
     * @param p    Первый узел (предполагается не null и существующим в дереве).
     * @param q    Второй узел (предполагается не null и существующим в дереве).
     * @return Узел, являющийся LCA для p и q, или null если root = null.
     */
    public TreeNode lowestCommonAncestorBST(TreeNode root, TreeNode p, TreeNode q) {
        Objects.requireNonNull(p, "Node p cannot be null");
        Objects.requireNonNull(q, "Node q cannot be null");

        TreeNode current = root;
        while (current != null) {
            int rootVal = current.val;
            int pVal = p.val;
            int qVal = q.val;

            // Если оба значения больше корня, LCA точно справа
            if (pVal > rootVal && qVal > rootVal) {
                current = current.right;
            }
            // Если оба значения меньше корня, LCA точно слева
            else if (pVal < rootVal && qVal < rootVal) {
                current = current.left;
            }
            // Иначе мы нашли узел, где значения p и q расходятся
            // (одно <= rootVal, другое >= rootVal), или один из узлов равен current.
            // Этот узел current и есть LCA.
            else {
                return current;
            }
        }
        return null; // Не должны дойти сюда, если p и q есть в дереве
    }

    /**
     * Находит LCA двух узлов {@code p} и {@code q} в обычном Бинарном Дереве (не BST).
     * Использует рекурсивный подход.
     * <p>
     * Сложность: O(n) по времени (в худшем случае обходим все узлы),
     * O(h) по памяти (глубина стека рекурсии, h - высота дерева).
     *
     * @param root Корневой узел дерева.
     * @param p    Первый узел.
     * @param q    Второй узел.
     * @return Узел, являющийся LCA для p и q, или null если {@code root} null
     * или если один из узлов (p или q) не найден в дереве с корнем {@code root}.
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

    /**
     * Точка входа для демонстрации работы методов поиска LCA.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        LowestCommonAncestor sol = new LowestCommonAncestor();

        // --- BST ---
        System.out.println("--- Lowest Common Ancestor in BST ---");
        TreeNode node3 = new TreeNode(3);
        TreeNode node5 = new TreeNode(5);
        TreeNode node0 = new TreeNode(0);
        TreeNode node4 = new TreeNode(4, node3, node5);
        TreeNode node2 = new TreeNode(2, node0, node4);
        TreeNode node7 = new TreeNode(7);
        TreeNode node9 = new TreeNode(9);
        TreeNode node8 = new TreeNode(8, node7, node9);
        TreeNode rootBST = new TreeNode(6, node2, node8);
        runLcaTest("BST", sol, rootBST, node2, node8, 6); // LCA(2, 8) = 6
        runLcaTest("BST", sol, rootBST, node2, node4, 2); // LCA(2, 4) = 2
        runLcaTest("BST", sol, rootBST, node0, node5, 2); // LCA(0, 5) = 2
        runLcaTest("BST", sol, rootBST, node3, node5, 4); // LCA(3, 5) = 4
        runLcaTest("BST", sol, rootBST, node7, node9, 8); // LCA(7, 9) = 8
        runLcaTest("BST", sol, rootBST, node0, node9, 6); // LCA(0, 9) = 6

        // --- Binary Tree (Не BST) ---
        System.out.println("\n--- Lowest Common Ancestor in Binary Tree ---");
        TreeNode node7_bt = new TreeNode(7);
        TreeNode node4_bt = new TreeNode(4);
        TreeNode node6_bt = new TreeNode(6);
        TreeNode node2_bt = new TreeNode(2, node7_bt, node4_bt);
        TreeNode node5_bt = new TreeNode(5, node6_bt, node2_bt);
        TreeNode node0_bt = new TreeNode(0);
        TreeNode node8_bt = new TreeNode(8);
        TreeNode node1_bt = new TreeNode(1, node0_bt, node8_bt);
        TreeNode rootBT = new TreeNode(3, node5_bt, node1_bt);
        runLcaTest("BT ", sol, rootBT, node5_bt, node1_bt, 3); // LCA(5, 1) = 3
        runLcaTest("BT ", sol, rootBT, node5_bt, node4_bt, 5); // LCA(5, 4) = 5
        runLcaTest("BT ", sol, rootBT, node6_bt, node4_bt, 5); // LCA(6, 4) = 5
        runLcaTest("BT ", sol, rootBT, node0_bt, node8_bt, 1); // LCA(0, 8) = 1
        runLcaTest("BT ", sol, rootBT, node7_bt, node4_bt, 2); // LCA(7, 4) = 2
        runLcaTest("BT ", sol, rootBT, node6_bt, node6_bt, 6); // LCA(6, 6) = 6
        TreeNode node10_bt = new TreeNode(10); // Узел не в дереве
        runLcaTest("BT ", sol, rootBT, node6_bt, node10_bt, -1); // Ожидаем null (или зависит от реализации, если один узел не найден)
    }

    /**
     * Вспомогательный метод для тестирования LCA.
     *
     * @param type        Тип дерева ("BST" или "BT").
     * @param sol         Экземпляр решателя.
     * @param root        Корень дерева.
     * @param p           Первый узел.
     * @param q           Второй узел.
     * @param expectedVal Ожидаемое значение LCA (-1 если ожидается null/ошибка).
     */
    private static void runLcaTest(String type, LowestCommonAncestor sol, TreeNode root, TreeNode p, TreeNode q, int expectedVal) {
        String pStr = (p == null ? "null" : String.valueOf(p.val));
        String qStr = (q == null ? "null" : String.valueOf(q.val));
        System.out.printf("LCA(%s, %s, %s): ", type, pStr, qStr);
        TreeNode lca = null;
        String error = null;
        try {
            if ("BST".equals(type)) {
                lca = sol.lowestCommonAncestorBST(root, p, q);
            } else {
                lca = sol.lowestCommonAncestorBinaryTree(root, p, q);
            }
        } catch (Exception e) {
            error = e.getClass().getSimpleName();
        }

        String resultStr = (lca == null ? "null" : String.valueOf(lca.val));
        String expectedStr = (expectedVal == -1 ? "null" : String.valueOf(expectedVal));

        System.out.print("Result=" + resultStr);
        if (error != null) System.out.print(" (Error: " + error + ")");
        System.out.print(" (Expected: " + expectedStr + ")");

        if (!Objects.equals(resultStr, expectedStr)) {
            System.out.print(" <- MISMATCH!");
        }
        System.out.println();
    }
}
