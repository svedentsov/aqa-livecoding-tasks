package com.svedentsov.aqa.tasks.trees;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Решение задачи №93: Проверить, является ли дерево Бинарным Деревом Поиска (BST).
 * Описание: (Проверяет: рекурсия, деревья)
 * Задание: Напишите метод `boolean isValidBST(TreeNode root)`, который проверяет,
 * является ли данное бинарное дерево с корнем `root` корректным бинарным
 * деревом поиска (для каждого узла все значения в левом поддереве меньше
 * значения узла, а в правом - больше). Дубликаты не допускаются.
 * Пример: Дерево `[2, 1, 3]` -> `true`.
 * Дерево `[5, 1, 4, null, null, 3, 6]` -> `false` (4 < 5, но 3 < 4 находится справа от 4).
 */
public class ValidateBST {

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
     * Проверяет, является ли данное бинарное дерево корректным BST рекурсивно.
     * Использует вспомогательный метод с передачей допустимых границ (min, max)
     * для значения каждого узла.
     *
     * @param root Корневой узел дерева для проверки.
     * @return {@code true}, если дерево является валидным BST, {@code false} в противном случае.
     */
    public boolean isValidBSTRecursive(TreeNode root) {
        // Используем Long.MIN_VALUE и Long.MAX_VALUE как начальные границы,
        // чтобы корректно обрабатывать узлы со значениями Integer.MIN_VALUE/MAX_VALUE.
        return isValidBSTHelper(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    /**
     * Проверяет, является ли дерево валидным BST с использованием итеративного In-order обхода.
     * В корректном BST In-order обход должен давать строго возрастающую последовательность.
     *
     * @param root Корневой узел.
     * @return true, если дерево валидно, false иначе.
     */
    public boolean isValidBSTInorder(TreeNode root) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        // Используем обертку Integer для previousValue, чтобы можно было начать с null.
        Integer previousValue = null;
        TreeNode current = root;

        while (current != null || !stack.isEmpty()) {
            // Идем влево до конца
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            // Посещаем узел (извлекаем из стека)
            current = stack.pop();

            // Проверяем порядок: текущее значение должно быть > предыдущего
            // Сравнение `previousValue != null && current.val <= previousValue` корректно.
            if (previousValue != null && current.val <= previousValue) {
                return false; // Нарушение порядка BST
            }
            previousValue = current.val; // Обновляем предыдущее значение

            // Переходим к правому поддереву
            current = current.right;
        }
        // Если обход завершен без нарушений
        return true;
    }

    /**
     * Точка входа для демонстрации работы методов валидации BST.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        ValidateBST sol = new ValidateBST();

        System.out.println("--- Validating Binary Search Trees ---");

        // Пример 1: Валидное BST
        TreeNode root1 = new TreeNode(2, new TreeNode(1), new TreeNode(3));
        runValidationTest(sol, root1, "Tree 1 [2,1,3] (Valid)"); // true

        // Пример 2: Невалидное BST
        TreeNode root2 = new TreeNode(5, new TreeNode(1), new TreeNode(4, new TreeNode(3), new TreeNode(6)));
        runValidationTest(sol, root2, "Tree 2 [5,1,4,null,null,3,6] (Invalid)"); // false

        // Пример 3: Невалидное (дубликат)
        TreeNode root3 = new TreeNode(2, new TreeNode(2), new TreeNode(3));
        runValidationTest(sol, root3, "Tree 3 [2,2,3] (Invalid - duplicate)"); // false

        // Пример 4: Пустое дерево
        runValidationTest(sol, null, "Tree 4 (null) (Valid)"); // true

        // Пример 5: Сложное валидное дерево
        TreeNode root5 = new TreeNode(10,
                new TreeNode(5, new TreeNode(3), new TreeNode(7)),
                new TreeNode(15, new TreeNode(13), new TreeNode(18)));
        runValidationTest(sol, root5, "Tree 5 (Valid complex)"); // true

        // Пример 6: Невалидное (нарушение справа)
        TreeNode root6 = new TreeNode(10,
                new TreeNode(5),
                new TreeNode(15, new TreeNode(6), new TreeNode(20))); // 6 < 10, но справа
        runValidationTest(sol, root6, "Tree 6 (Invalid - right subtree violation)"); // false

        // Пример 7: Невалидное (нарушение слева)
        TreeNode root7 = new TreeNode(10,
                new TreeNode(5, null, new TreeNode(12)), // 12 > 10, но слева
                new TreeNode(15));
        runValidationTest(sol, root7, "Tree 7 (Invalid - left subtree violation)"); // false

        // Пример 8: Граничные значения Integer
        TreeNode root8 = new TreeNode(Integer.MAX_VALUE);
        runValidationTest(sol, root8, "Tree 8 [MAX_VALUE] (Valid)"); // true
        TreeNode root9 = new TreeNode(Integer.MIN_VALUE);
        runValidationTest(sol, root9, "Tree 9 [MIN_VALUE] (Valid)"); // true
        TreeNode root10 = new TreeNode(0, new TreeNode(Integer.MIN_VALUE), new TreeNode(Integer.MAX_VALUE));
        runValidationTest(sol, root10, "Tree 10 [0, MIN, MAX] (Valid)"); // true
    }

    /**
     * Рекурсивный вспомогательный метод для проверки валидности BST.
     *
     * @param node   Текущий проверяемый узел.
     * @param minVal Нижняя граница допустимого значения (исключительно).
     * @param maxVal Верхняя граница допустимого значения (исключительно).
     * @return {@code true}, если поддерево валидно, {@code false} иначе.
     */
    private boolean isValidBSTHelper(TreeNode node, long minVal, long maxVal) {
        if (node == null) {
            return true; // Базовый случай: дошли до конца ветки
        }

        // Проверяем, что текущий узел вписывается в допустимые границы
        if (node.val <= minVal || node.val >= maxVal) {
            return false;
        }

        // Рекурсивно проверяем левое поддерево (обновляя maxVal)
        // и правое поддерево (обновляя minVal)
        return isValidBSTHelper(node.left, minVal, node.val) &&
                isValidBSTHelper(node.right, node.val, maxVal);
    }

    /**
     * Вспомогательный метод для тестирования методов валидации BST.
     *
     * @param sol         Экземпляр решателя.
     * @param root        Корень дерева.
     * @param description Описание теста.
     */
    private static void runValidationTest(ValidateBST sol, TreeNode root, String description) {
        System.out.println("\n--- " + description + " ---");
        // Печать дерева (если нужно) может быть сложной для невалидных деревьев
        // if (root != null) { root.printLevelOrder(); } else { System.out.println("Input: null"); }
        try {
            boolean resultRec = sol.isValidBSTRecursive(root);
            System.out.println("  Result (Recursive): " + resultRec);
        } catch (Exception e) {
            System.err.println("  Result (Recursive): Error - " + e.getMessage());
        }
        try {
            boolean resultIter = sol.isValidBSTInorder(root);
            System.out.println("  Result (Inorder)  : " + resultIter);
        } catch (Exception e) {
            System.err.println("  Result (Inorder)  : Error - " + e.getMessage());
        }
    }
}
