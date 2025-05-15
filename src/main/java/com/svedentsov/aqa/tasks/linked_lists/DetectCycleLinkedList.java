package com.svedentsov.aqa.tasks.linked_lists;

/**
 * Решение задачи №71: Найти цикл в связанном списке.
 * Описание: (Проверяет: структуры данных, два указателя - slow/fast)
 * Задание: Напишите метод `boolean hasCycle(ListNode head)`, где `ListNode` - узел
 * связанного списка (с полями `val` и `next`). Метод должен вернуть `true`,
 * если в списке есть цикл, и `false` иначе.
 * Пример: Если узел `next` последнего элемента указывает на один из предыдущих
 * узлов, метод вернет `true`.
 */
public class DetectCycleLinkedList {

    /**
     * Класс, представляющий узел односвязного списка.
     */
    static class ListNode {
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
     * Сложность: O(N) по времени, O(1) по памяти, где N - количество узлов.
     * Алгоритм:
     * - Инициализируются два указателя (`slow`, `fast`) на `head`.
     * - В цикле: `slow` двигается на 1 шаг, `fast` на 2 шага.
     * - Если `fast` достигает `null` или `fast.next` равен `null`, цикла нет.
     * - Если `slow` и `fast` встречаются (`slow == fast`), цикл есть.
     *
     * @param head Головной узел связанного списка.
     * @return {@code true}, если список содержит цикл, {@code false} в противном случае.
     */
    public boolean hasCycle(ListNode head) {
        // Список пуст или имеет один узел -> нет цикла
        if (head == null || head.next == null) {
            return false;
        }

        ListNode slow = head;
        ListNode fast = head;

        // Цикл продолжается, пока fast может сделать два шага вперед
        while (fast != null && fast.next != null) {
            slow = slow.next;      // Шаг черепахи
            fast = fast.next.next; // Шаг зайца

            // Если указатели встретились
            if (slow == fast) {
                return true; // Цикл найден
            }
        }

        // Если fast дошел до конца списка (null), цикла нет
        return false;
    }

    /**
     * Точка входа для демонстрации работы метода определения цикла.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        DetectCycleLinkedList sol = new DetectCycleLinkedList();

        System.out.println("--- Linked List Cycle Detection ---");

        // Пример 1: Список с циклом
        ListNode nodeNeg4_1 = new ListNode(-4);
        ListNode node0_1 = new ListNode(0, nodeNeg4_1);
        ListNode node2_1 = new ListNode(2, node0_1);
        ListNode head1 = new ListNode(3, node2_1);
        nodeNeg4_1.next = node2_1; // Цикл к узлу 2
        runCycleTest(sol, head1, "List 1 (cycle to node 2)"); // Expected: true

        // Пример 2: Список без цикла
        ListNode head2 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4))));
        runCycleTest(sol, head2, "List 2 (no cycle)"); // Expected: false

        // Пример 3: Пустой список
        runCycleTest(sol, null, "List 3 (empty)"); // Expected: false

        // Пример 4: Список с одним узлом
        ListNode head4 = new ListNode(1);
        runCycleTest(sol, head4, "List 4 (single node)"); // Expected: false

        // Пример 5: Список с циклом на себя (один узел)
        ListNode head5 = new ListNode(1);
        head5.next = head5;
        runCycleTest(sol, head5, "List 5 (self loop)"); // Expected: true

        // Пример 6: Список из двух узлов с циклом
        ListNode node2_6 = new ListNode(2);
        ListNode head6 = new ListNode(1, node2_6);
        node2_6.next = head6; // Цикл от 2 к 1
        runCycleTest(sol, head6, "List 6 (two node cycle)"); // Expected: true

        // Пример 7: Длинный список без цикла
        ListNode current = new ListNode(0);
        ListNode head7 = current;
        for (int i = 1; i < 100; i++) {
            current.next = new ListNode(i);
            current = current.next;
        }
        runCycleTest(sol, head7, "List 7 (long, no cycle)"); // Expected: false
    }

    /**
     * Вспомогательный метод для тестирования hasCycle.
     *
     * @param sol         Экземпляр решателя.
     * @param head        Голова списка.
     * @param description Описание теста.
     */
    private static void runCycleTest(DetectCycleLinkedList sol, ListNode head, String description) {
        System.out.println("\n--- " + description + " ---");
        // Печать списка невозможна безопасно, если есть цикл
        // printList("Input list", head); // Нельзя использовать
        try {
            boolean result = sol.hasCycle(head);
            System.out.println("Result (hasCycle): " + result);
        } catch (Exception e) {
            System.err.println("Result (hasCycle): Error - " + e.getMessage());
        }
    }
}
