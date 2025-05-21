package com.svedentsov.aqa.tasks.linked_lists;

import com.svedentsov.aqa.tasks.linked_lists.ReverseLinkedList.ListNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Тесты для ReverseLinkedList")
class ReverseLinkedListTest {

    private ReverseLinkedList reverser;

    @BeforeEach
    void setUp() {
        reverser = new ReverseLinkedList();
    }

    // --- Вспомогательные методы для тестов ---
    private ListNode buildList(int[] values) {
        if (values == null || values.length == 0) {
            return null;
        }
        ListNode head = new ListNode(values[0]);
        ListNode current = head;
        for (int i = 1; i < values.length; i++) {
            current.next = new ListNode(values[i]);
            current = current.next;
        }
        return head;
    }

    private List<Integer> toArrayList(ListNode head) {
        List<Integer> list = new ArrayList<>();
        ListNode current = head;
        while (current != null) {
            list.add(current.val);
            current = current.next;
        }
        return list;
    }

    @Nested
    @DisplayName("Итеративный метод: reverseListIterative")
    class IterativeReverseTests {

        @Test
        @DisplayName("Переворот пустого списка")
        void reverseEmptyListIterative() {
            ListNode head = null;
            ListNode reversedHead = reverser.reverseListIterative(head);
            assertNull(reversedHead);
        }

        @Test
        @DisplayName("Переворот списка из одного элемента")
        void reverseSingleElementListIterative() {
            ListNode head = buildList(new int[]{10});
            ListNode reversedHead = reverser.reverseListIterative(head);
            assertIterableEquals(List.of(10), toArrayList(reversedHead));
        }

        @Test
        @DisplayName("Переворот списка из двух элементов")
        void reverseTwoElementListIterative() {
            ListNode head = buildList(new int[]{1, 2});
            ListNode reversedHead = reverser.reverseListIterative(head);
            assertIterableEquals(List.of(2, 1), toArrayList(reversedHead));
        }

        @Test
        @DisplayName("Переворот списка из нескольких элементов")
        void reverseMultipleElementsListIterative() {
            ListNode head = buildList(new int[]{1, 2, 3, 4, 5});
            ListNode reversedHead = reverser.reverseListIterative(head);
            assertIterableEquals(List.of(5, 4, 3, 2, 1), toArrayList(reversedHead));
        }
    }

    @Nested
    @DisplayName("Рекурсивный метод: reverseListRecursive")
    class RecursiveReverseTests {

        @Test
        @DisplayName("Переворот пустого списка")
        void reverseEmptyListRecursive() {
            ListNode head = null;
            ListNode reversedHead = reverser.reverseListRecursive(head);
            assertNull(reversedHead);
        }

        @Test
        @DisplayName("Переворот списка из одного элемента")
        void reverseSingleElementListRecursive() {
            ListNode head = buildList(new int[]{10});
            // Копируем, так как рекурсивный метод может изменить исходный (хотя и возвращает новую голову)
            // В данном случае для одного элемента это не так критично, но для длинных списков важно
            ListNode headCopy = buildList(new int[]{10});
            ListNode reversedHead = reverser.reverseListRecursive(headCopy);
            assertIterableEquals(List.of(10), toArrayList(reversedHead));
        }

        @Test
        @DisplayName("Переворот списка из двух элементов")
        void reverseTwoElementListRecursive() {
            ListNode head = buildList(new int[]{1, 2});
            ListNode reversedHead = reverser.reverseListRecursive(head);
            assertIterableEquals(List.of(2, 1), toArrayList(reversedHead));
        }

        @Test
        @DisplayName("Переворот списка из нескольких элементов")
        void reverseMultipleElementsListRecursive() {
            ListNode head = buildList(new int[]{1, 2, 3, 4, 5});
            ListNode reversedHead = reverser.reverseListRecursive(head);
            assertIterableEquals(List.of(5, 4, 3, 2, 1), toArrayList(reversedHead));
        }
    }
}
