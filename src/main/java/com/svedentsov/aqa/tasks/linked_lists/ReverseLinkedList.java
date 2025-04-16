package com.svedentsov.aqa.tasks.linked_lists;

/**
 * Решение задачи №72: Перевернуть связанный список.
 * <p>
 * Описание: (Проверяет: структуры данных, работа с указателями)
 * <p>
 * Задание: Напишите метод `ListNode reverseList(ListNode head)`, который
 * переворачивает односвязный список и возвращает новую голову списка.
 * <p>
 * Пример: Список `1 -> 2 -> 3 -> null` должен стать `3 -> 2 -> 1 -> null`.
 */
public class ReverseLinkedList {

    /**
     * Класс узла односвязного списка.
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
     * <p>
     * Сложность: O(n) по времени, O(1) по дополнительной памяти.
     * <p>
     * Алгоритм:
     * 1. Инициализировать `prev = null`, `current = head`.
     * 2. В цикле `while (current != null)`:
     * a. Сохранить `next = current.next`.
     * b. Перенаправить `current.next = prev`.
     * c. Сдвинуть `prev = current`.
     * d. Сдвинуть `current = next`.
     * 3. Вернуть `prev` (новую голову).
     *
     * @param head Головной узел исходного списка. Может быть null.
     * @return Головной узел перевернутого списка.
     */
    public ListNode reverseListIterative(ListNode head) {
        ListNode prev = null;
        ListNode current = head;

        while (current != null) {
            ListNode nextNode = current.next; // Сохраняем следующий
            current.next = prev;      // Переворачиваем указатель
            prev = current;           // Сдвигаем prev
            current = nextNode;       // Сдвигаем current
        }
        // prev теперь указывает на новую голову
        return prev;
    }

    /**
     * Переворачивает односвязный список рекурсивно.
     * <p>
     * Сложность: O(n) по времени, O(n) по памяти (из-за стека вызовов рекурсии).
     * <p>
     * Алгоритм:
     * 1. Базовый случай: если `head` null или `head.next` null, вернуть `head`.
     * 2. Рекурсивно вызвать `reverseListRecursive(head.next)`, получить `newHead`.
     * 3. Перенаправить указатель `head.next.next = head`.
     * 4. Установить `head.next = null`.
     * 5. Вернуть `newHead`.
     *
     * @param head Головной узел текущего подсписка для переворота. Может быть null.
     * @return Головной узел полностью перевернутого списка.
     */
    public ListNode reverseListRecursive(ListNode head) {
        // Базовый случай
        if (head == null || head.next == null) {
            return head;
        }

        // Рекурсивный вызов для хвоста
        ListNode newHead = reverseListRecursive(head.next);

        // Переподключение узлов
        head.next.next = head; // Следующий узел теперь указывает на текущий
        head.next = null;      // Текущий узел становится последним

        // Возвращаем голову перевернутого хвоста
        return newHead;
    }

    /**
     * Точка входа для демонстрации работы методов переворота списка.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        ReverseLinkedList sol = new ReverseLinkedList();

        System.out.println("--- Reversing Linked List ---");

        // Тест 1: Обычный список
        ListNode head1 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        runReverseListTest(sol, head1, "List 1: Normal");
        // Ожидаемый результат: 5 -> 4 -> 3 -> 2 -> 1 -> null

        // Тест 2: Список из одного элемента
        ListNode head2 = new ListNode(10);
        runReverseListTest(sol, head2, "List 2: Single element");
        // Ожидаемый результат: 10 -> null

        // Тест 3: Пустой список
        ListNode head3 = null;
        runReverseListTest(sol, head3, "List 3: Empty list");
        // Ожидаемый результат: null

        // Тест 4: Список из двух элементов
        ListNode head4 = new ListNode(1, new ListNode(2));
        runReverseListTest(sol, head4, "List 4: Two elements");
        // Ожидаемый результат: 2 -> 1 -> null
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
     * Вспомогательный метод для создания копии списка (нужен для тестов).
     */
    private static ListNode copyList(ListNode head) {
        if (head == null) return null;
        ListNode dummy = new ListNode(0);
        ListNode currentCopy = dummy;
        ListNode currentOriginal = head;
        while (currentOriginal != null) {
            currentCopy.next = new ListNode(currentOriginal.val);
            currentCopy = currentCopy.next;
            currentOriginal = currentOriginal.next;
        }
        return dummy.next;
    }

    /**
     * Вспомогательный метод для тестирования методов переворота списка.
     *
     * @param sol         Экземпляр решателя.
     * @param head        Голова исходного списка.
     * @param description Описание теста.
     */
    private static void runReverseListTest(ReverseLinkedList sol, ListNode head, String description) {
        System.out.println("\n--- " + description + " ---");
        printList("Original        : ", head);

        // Тест итеративного метода (на копии)
        ListNode headCopyIter = copyList(head);
        try {
            ListNode reversedIter = sol.reverseListIterative(headCopyIter);
            printList("Reversed (Iter) : ", reversedIter);
        } catch (Exception e) {
            System.err.println("Reversed (Iter) : Error - " + e.getMessage());
        }

        // Тест рекурсивного метода (на копии)
        ListNode headCopyRec = copyList(head);
        try {
            ListNode reversedRec = sol.reverseListRecursive(headCopyRec);
            printList("Reversed (Rec)  : ", reversedRec);
        } catch (Exception e) {
            System.err.println("Reversed (Rec)  : Error - " + e.getMessage());
        }
    }
}
