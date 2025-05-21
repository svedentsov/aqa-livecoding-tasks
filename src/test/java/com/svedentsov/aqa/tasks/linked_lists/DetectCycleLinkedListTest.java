package com.svedentsov.aqa.tasks.linked_lists;

import com.svedentsov.aqa.tasks.linked_lists.DetectCycleLinkedList.ListNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты для DetectCycleLinkedList")
class DetectCycleLinkedListTest {

    private DetectCycleLinkedList cycleDetector;

    @BeforeEach
    void setUp() {
        cycleDetector = new DetectCycleLinkedList();
    }

    @Nested
    @DisplayName("Тесты на наличие цикла")
    class CyclePresentTests {

        @Test
        @DisplayName("Список с циклом (пример 1 из main)")
        void hasCycle_whenCyclePresent_example1_shouldReturnTrue() {
            ListNode nodeNeg4 = new ListNode(-4);
            ListNode node0 = new ListNode(0, nodeNeg4);
            ListNode node2 = new ListNode(2, node0);
            ListNode head = new ListNode(3, node2);
            nodeNeg4.next = node2; // Цикл: -4 -> 2
            assertTrue(cycleDetector.hasCycle(head), "Должен обнаружить цикл");
        }

        @Test
        @DisplayName("Список с циклом на себя (один узел)")
        void hasCycle_whenSingleNodeSelfLoop_shouldReturnTrue() {
            ListNode head = new ListNode(1);
            head.next = head; // Цикл на себя
            assertTrue(cycleDetector.hasCycle(head), "Должен обнаружить цикл на себя");
        }

        @Test
        @DisplayName("Список из двух узлов с циклом")
        void hasCycle_whenTwoNodeCycle_shouldReturnTrue() {
            ListNode node2 = new ListNode(2);
            ListNode head = new ListNode(1, node2);
            node2.next = head; // Цикл: 2 -> 1
            assertTrue(cycleDetector.hasCycle(head), "Должен обнаружить цикл из двух узлов");
        }

        @Test
        @DisplayName("Список с циклом, начинающимся не с головы")
        void hasCycle_whenCycleStartsMidList_shouldReturnTrue() {
            ListNode node5 = new ListNode(5);
            ListNode node4 = new ListNode(4, node5);
            ListNode node3 = new ListNode(3, node4);
            ListNode node2 = new ListNode(2, node3);
            ListNode head = new ListNode(1, node2);
            node5.next = node3; // Цикл: 5 -> 3
            assertTrue(cycleDetector.hasCycle(head), "Должен обнаружить цикл, начинающийся в середине списка");
        }
    }

    @Nested
    @DisplayName("Тесты на отсутствие цикла")
    class NoCycleTests {

        @Test
        @DisplayName("Список без цикла (пример 2 из main)")
        void hasCycle_whenNoCycle_example2_shouldReturnFalse() {
            ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4))));
            assertFalse(cycleDetector.hasCycle(head), "Не должен обнаруживать цикл");
        }

        @Test
        @DisplayName("Пустой список")
        void hasCycle_whenListIsEmpty_shouldReturnFalse() {
            assertFalse(cycleDetector.hasCycle(null), "Пустой список не должен иметь цикла");
        }

        @Test
        @DisplayName("Список с одним узлом (без цикла на себя)")
        void hasCycle_whenSingleNodeNoLoop_shouldReturnFalse() {
            ListNode head = new ListNode(1);
            assertFalse(cycleDetector.hasCycle(head), "Список из одного узла (без самозацикливания) не должен иметь цикла");
        }

        @Test
        @DisplayName("Список из двух узлов без цикла")
        void hasCycle_whenTwoNodesNoLoop_shouldReturnFalse() {
            ListNode head = new ListNode(1, new ListNode(2));
            assertFalse(cycleDetector.hasCycle(head), "Список из двух узлов без цикла не должен иметь цикла");
        }

        @Test
        @DisplayName("Длинный список без цикла (пример 7 из main)")
        void hasCycle_whenLongListNoCycle_shouldReturnFalse() {
            ListNode current = new ListNode(0);
            ListNode head = current;
            for (int i = 1; i < 100; i++) {
                current.next = new ListNode(i);
                current = current.next;
            }
            assertFalse(cycleDetector.hasCycle(head), "Длинный список без цикла не должен иметь цикла");
        }
    }
}
