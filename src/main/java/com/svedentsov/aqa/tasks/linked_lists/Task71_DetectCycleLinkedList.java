package com.svedentsov.aqa.tasks.linked_lists;

/**
 * Решение задачи №71: Найти цикл в связанном списке.
 */
public class Task71_DetectCycleLinkedList {

    /**
     * Класс, представляющий узел односвязного списка.
     * (Дублируется здесь для автономности примера, в проекте должен быть один).
     */
    static class ListNode { // Делаем static для доступа из static main
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
            this.next = null;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /**
     * Определяет, содержит ли связанный список цикл, используя алгоритм
     * Флойда "Черепаха и Заяц" (два указателя: slow и fast).
     * Алгоритм:
     * - Инициализируются два указателя (`slow` и `fast`) на начало списка.
     * - На каждой итерации `slow` сдвигается на 1 узел, `fast` - на 2 узла.
     * - Если `fast` (или `fast.next`) достигает `null`, цикла нет.
     * - Если `fast` и `slow` когда-либо указывают на один и тот же узел, цикл обнаружен.
     * Сложность O(n) по времени, где n - количество узлов до начала цикла + длина цикла.
     * Сложность O(1) по памяти.
     *
     * @param head Головной узел связанного списка.
     * @return {@code true}, если список содержит цикл, {@code false} в противном случае.
     */
    public boolean hasCycle(ListNode head) {
        // Если список пуст или содержит только один узел, цикла нет.
        if (head == null || head.next == null) {
            return false;
        }

        // Инициализация указателей
        ListNode slow = head;       // Черепаха
        ListNode fast = head;       // Заяц

        // Перемещение указателей
        // Цикл продолжается, пока быстрый указатель и следующий за ним узел существуют
        while (fast != null && fast.next != null) {
            slow = slow.next;          // Медленный: шаг на 1
            fast = fast.next.next;     // Быстрый: шаг на 2

            // Проверка на встречу указателей
            if (slow == fast) {
                // Указатели встретились - цикл обнаружен
                return true;
            }
        }

        // Если цикл завершился (fast или fast.next стал null), значит, цикла нет
        return false;
    }

    /**
     * Точка входа для демонстрации работы метода определения цикла.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task71_DetectCycleLinkedList sol = new Task71_DetectCycleLinkedList();

        // Пример 1: Список с циклом
        // 3 -> 2 -> 0 -> -4 -┐
        //      ^-----------┘ (цикл к узлу 2)
        ListNode nodeNeg4 = new ListNode(-4);
        ListNode node0 = new ListNode(0, nodeNeg4);
        ListNode node2 = new ListNode(2, node0);
        ListNode head1 = new ListNode(3, node2);
        nodeNeg4.next = node2; // Создаем цикл
        System.out.println("List 1 (cycle) has cycle: " + sol.hasCycle(head1)); // true

        // Пример 2: Список без цикла
        // 1 -> 2 -> 3 -> 4 -> null
        ListNode head2 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4))));
        System.out.println("List 2 (no cycle) has cycle: " + sol.hasCycle(head2)); // false

        // Пример 3: Пустой список
        System.out.println("List 3 (empty) has cycle: " + sol.hasCycle(null)); // false

        // Пример 4: Список с одним узлом
        ListNode head4 = new ListNode(1);
        System.out.println("List 4 (single node) has cycle: " + sol.hasCycle(head4)); // false

        // Пример 5: Список с циклом на себя (один узел)
        ListNode head5 = new ListNode(1);
        head5.next = head5;
        System.out.println("List 5 (self loop) has cycle: " + sol.hasCycle(head5)); // true

        // Пример 6: Список из двух узлов с циклом
        // 1 -> 2 -┐
        // ^-----┘
        ListNode node2_6 = new ListNode(2);
        ListNode head6 = new ListNode(1, node2_6);
        node2_6.next = head6; // Цикл от 2 к 1
        System.out.println("List 6 (two node cycle) has cycle: " + sol.hasCycle(head6)); // true
    }
}
