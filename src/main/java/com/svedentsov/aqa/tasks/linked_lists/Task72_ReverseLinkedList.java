package com.svedentsov.aqa.tasks.linked_lists;

/**
 * Решение задачи №72: Перевернуть связанный список.
 */
public class Task72_ReverseLinkedList {

    /**
     * Класс узла односвязного списка (копия).
     */
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /**
     * Переворачивает односвязный список итеративно.
     * Проходит по списку, изменяя указатель `next` каждого узла так,
     * чтобы он указывал на предыдущий узел (`prev`).
     * Сложность O(n) по времени, O(1) по дополнительной памяти.
     *
     * @param head Головной узел исходного списка. Может быть null.
     * @return Головной узел перевернутого списка (бывший хвост исходного списка).
     * Возвращает null, если исходный список был null.
     */
    public ListNode reverseListIterative(ListNode head) {
        ListNode prev = null;     // Указатель на предыдущий узел, станет новым 'next'
        ListNode current = head;  // Текущий узел для обработки
        // ListNode nextTemp;        // Не обязательна, можно обойтись

        // Идем по списку, пока не обработаем все узлы (current не станет null)
        while (current != null) {
            // 1. Сохраняем ссылку на следующий узел в исходном списке
            ListNode nextNode = current.next;
            // 2. Перенаправляем ссылку текущего узла на предыдущий узел
            current.next = prev;
            // 3. Сдвигаем указатели для следующей итерации:
            //    'prev' теперь указывает на обработанный 'current'
            prev = current;
            //    'current' переходит к следующему узлу исходного списка
            current = nextNode;
        }
        // Когда current == null, prev указывает на последний узел исходного списка,
        // который стал головой перевернутого.
        return prev;
    }

    /**
     * Переворачивает односвязный список рекурсивно.
     * Рекурсивно переворачивает "хвост" списка (все узлы после head),
     * а затем перенаправляет указатели, чтобы последний узел хвоста
     * указывал на head, а next у head стал null.
     * Сложность O(n) по времени, O(n) по памяти из-за стека вызовов рекурсии.
     *
     * @param head Головной узел текущего подсписка для переворота. Может быть null.
     * @return Головной узел полностью перевернутого списка.
     */
    public ListNode reverseListRecursive(ListNode head) {
        // Базовый случай рекурсии:
        // Если список пуст (head == null) или содержит один узел (head.next == null),
        // то он уже "перевернут", и head является новой головой.
        if (head == null || head.next == null) {
            return head;
        }

        // Рекурсивно переворачиваем оставшуюся часть списка ("хвост"), начиная со следующего узла.
        // `newHead` будет указывать на голову уже перевернутого хвоста (бывший последний узел всего списка).
        ListNode newHead = reverseListRecursive(head.next);

        // На шаге возврата из рекурсии:
        // `head` - это текущий узел.
        // `head.next` - это узел, который был СЛЕДУЮЩИМ за head в исходном списке,
        //              а теперь он ПОСЛЕДНИЙ узел в перевернутом хвосте (`newHead`... -> `head.next`).
        // Нам нужно, чтобы `head.next` (последний узел хвоста) указывал обратно на `head`.
        head.next.next = head;

        // Текущий узел `head` должен стать последним узлом в полностью перевернутом списке,
        // поэтому его указатель `next` должен стать null.
        head.next = null;

        // Возвращаем `newHead`, которая была получена из самого глубокого вызова рекурсии
        // и является головой всего перевернутого списка.
        return newHead;
    }

    /**
     * Вспомогательный метод для печати списка.
     */
    private static void printList(String prefix, ListNode head) {
        System.out.print(prefix);
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }

    /**
     * Точка входа для демонстрации работы методов переворота списка.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task72_ReverseLinkedList sol = new Task72_ReverseLinkedList();

        // --- Тест 1 ---
        ListNode head1 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        printList("Original List 1:      ", head1);
        // Создаем копию для рекурсивного метода
        ListNode head1_copy = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));

        ListNode reversedHead1_iter = sol.reverseListIterative(head1);
        printList("Reversed (Iterative): ", reversedHead1_iter); // 5 -> 4 -> 3 -> 2 -> 1 -> null

        ListNode reversedHead1_rec = sol.reverseListRecursive(head1_copy);
        printList("Reversed (Recursive): ", reversedHead1_rec); // 5 -> 4 -> 3 -> 2 -> 1 -> null

        // --- Тест 2 ---
        ListNode head2 = new ListNode(10);
        printList("\nOriginal List 2:      ", head2);
        ListNode reversedHead2 = sol.reverseListIterative(head2);
        printList("Reversed (Iterative): ", reversedHead2); // 10 -> null

        // --- Тест 3 ---
        ListNode head3 = null;
        printList("\nOriginal List 3:      ", head3);
        ListNode reversedHead3 = sol.reverseListIterative(head3);
        printList("Reversed (Iterative): ", reversedHead3); // null

    }
}
