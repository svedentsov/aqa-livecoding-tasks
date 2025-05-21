package com.svedentsov.aqa.tasks.linked_lists;

/**
 * Решение задачи №72: Перевернуть связанный список.
 * Описание: (Проверяет: структуры данных, работа с указателями)
 * Задание: Напишите метод `ListNode reverseList(ListNode head)`, который
 * переворачивает односвязный список и возвращает новую голову списка.
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

        // Конструктор для удобства, если понадобится в тестах или других местах
        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /**
     * Переворачивает односвязный список итеративно.
     * Сложность: O(n) по времени, O(1) по дополнительной памяти.
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
     * Сложность: O(n) по времени, O(n) по памяти (из-за стека вызовов рекурсии).
     *
     * @param head Головной узел текущего подсписка для переворота. Может быть null.
     * @return Головной узел полностью перевернутого списка.
     */
    public ListNode reverseListRecursive(ListNode head) {
        // Базовый случай: если голова null или это последний узел
        if (head == null || head.next == null) {
            return head;
        }

        // Рекурсивный вызов для "хвоста" списка
        ListNode newHead = reverseListRecursive(head.next);

        // Переподключение узлов:
        // head.next - это бывший следующий узел, который теперь последний в перевернутом "хвосте".
        // Его 'next' должен указывать на текущий 'head'.
        head.next.next = head;
        // Текущий 'head' становится последним узлом в новой части перевернутого списка.
        head.next = null;

        // 'newHead' - это голова уже полностью перевернутого "хвоста",
        // которая и будет головой всего перевернутого списка.
        return newHead;
    }
}
