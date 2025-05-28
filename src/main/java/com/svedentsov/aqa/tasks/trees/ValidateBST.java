package com.svedentsov.aqa.tasks.trees;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Решение задачи №93: Проверить, является ли дерево Бинарным Деревом Поиска (BST).
 * Описание: (Проверяет: рекурсия, деревья)
 * Задание: Напишите метод `boolean isValidBST(TreeNode root)`, который проверяет,
 * является ли данное бинарное дерево с корнем `root` корректным бинарным
 * деревом поиска (для каждого узла все значения в левом поддереве меньше
 * значения узла, а в правом - больше). Дубликаты не допускаются (значения в левом
 * поддереве должны быть строго меньше, а в правом - строго больше).
 * Пример: Дерево `[2, 1, 3]` -> `true`.
 * Дерево `[5, 1, 4, null, null, 3, 6]` -> `false`.
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
     * Рекурсивный вспомогательный метод для проверки валидности BST.
     *
     * @param node   Текущий проверяемый узел.
     * @param minVal Нижняя граница допустимого значения (исключительно).
     * @param maxVal Верхняя граница допустимого значения (исключительно).
     * @return {@code true}, если поддерево валидно, {@code false} иначе.
     */
    private boolean isValidBSTHelper(TreeNode node, long minVal, long maxVal) {
        if (node == null) {
            return true; // Базовый случай: дошли до конца ветки, это валидная часть
        }

        // Проверяем, что текущий узел вписывается в допустимые границы
        // Используем <= и >= потому что minVal/maxVal - это границы, которые узел НЕ должен достигать.
        // То есть node.val ДОЛЖЕН быть > minVal и < maxVal.
        if (node.val <= minVal || node.val >= maxVal) {
            return false;
        }

        // Рекурсивно проверяем левое поддерево (обновляя maxVal до значения текущего узла)
        // и правое поддерево (обновляя minVal до значения текущего узла)
        return isValidBSTHelper(node.left, minVal, node.val) &&
                isValidBSTHelper(node.right, node.val, maxVal);
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
        // Используем Long для previousValue, чтобы можно было начать с "минус бесконечности"
        // или использовать null и первую проверку без сравнения.
        // Использование Integer и начального null, как в оригинале, тоже корректно.
        Long previousValue = null; // Long, чтобы избежать проблем с Integer.MIN_VALUE как первым элементом
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
            previousValue = (long) current.val; // Обновляем предыдущее значение

            // Переходим к правому поддереву
            current = current.right;
        }
        // Если обход завершен без нарушений
        return true;
    }
}
