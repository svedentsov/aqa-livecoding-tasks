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

        // Конструктор для удобства создания цепочек узлов, если нужен в тестах
        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /**
     * Определяет, содержит ли связанный список цикл, используя алгоритм
     * Флойда "Черепаха и Заяц" (два указателя: slow и fast).
     * Сложность: O(N) по времени, O(1) по памяти, где N - количество узлов.
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
}
