package com.svedentsov.aqa.tasks.data_structures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты для MedianFinder (Медиана потока чисел)")
class MedianFinderTest {

    private MedianFinder medianFinder;
    private static final double DELTA = 1e-5; // Дельта для сравнения double

    @BeforeEach
    void setUp() {
        medianFinder = new MedianFinder();
    }

    @Test
    @DisplayName("findMedian на пустом MedianFinder должен вернуть 0.0")
    void findMedian_emptyFinder_returnsZero() {
        assertEquals(0.0, medianFinder.findMedian(), DELTA);
        assertEquals(0, medianFinder.getSize());
    }

    @Test
    @DisplayName("После добавления одного числа, медиана равна этому числу")
    void addNum_singleElement_medianIsElement() {
        medianFinder.addNum(5);
        assertEquals(5.0, medianFinder.findMedian(), DELTA);
        assertEquals(1, medianFinder.getSize());
    }

    @Test
    @DisplayName("После добавления двух чисел, медиана - их среднее")
    void addNum_twoElements_medianIsAverage() {
        medianFinder.addNum(1);
        medianFinder.addNum(2);
        assertEquals(1.5, medianFinder.findMedian(), DELTA);
        assertEquals(2, medianFinder.getSize());

        medianFinder = new MedianFinder(); // Reset
        medianFinder.addNum(2);
        medianFinder.addNum(1);
        assertEquals(1.5, medianFinder.findMedian(), DELTA);
    }

    @Test
    @DisplayName("Последовательность из задания main: 1, 2, 3")
    void addNum_sequence_1_2_3() {
        medianFinder.addNum(1); // maxHeap: [1], minHeap: [], median: 1
        assertEquals(1.0, medianFinder.findMedian(), DELTA);
        assertEquals(1, medianFinder.getSize());

        medianFinder.addNum(2); // maxHeap: [1], minHeap: [2], median: 1.5
        assertEquals(1.5, medianFinder.findMedian(), DELTA);
        assertEquals(2, medianFinder.getSize());

        medianFinder.addNum(3); // maxHeap: [2,1], minHeap: [3], median: 2 (maxHeap after rebalance: [2], minHeap:[3])
        // 1. add 3 -> maxH: [3,1], minH:[] -> poll 3
        // 2. minH: [3], maxH:[1] -> poll 3 from maxH, offer to minH
        // 3. minH: [3], maxH:[1] -> offer 3 to minH
        //    maxH: [1], minH: [3]
        // 4. minH.size > maxH.size (1 > 0, should be maxH:[1], minH[3])
        // Let's trace addNum(3) to existing {1,2} (maxH:[1], minH:[2])
        // 1. addNum(3): maxH.offer(3) -> maxH becomes [3,1] (top is 3)
        // 2. minH.offer(maxH.poll()) -> maxH.poll() is 3. maxH is [1]. minH.offer(3) -> minH is [2,3] (top is 2)
        // 3. if (minH.size() > maxH.size()) -> (2 > 1) is true
        // 4. maxH.offer(minH.poll()) -> minH.poll() is 2. minH is [3]. maxH.offer(2) -> maxH is [2,1] (top is 2)
        // State: maxH: [2,1] (peek 2), minH: [3] (peek 3). Size: 3. Median: maxH.peek() = 2. Correct.
        assertEquals(2.0, medianFinder.findMedian(), DELTA);
        assertEquals(3, medianFinder.getSize());
    }


    static Stream<Arguments> sequenceTestCases() {
        return Stream.of(
                Arguments.of(new int[]{1, 2, 3, 0, 5, 2, 4, -1, 6, 6}, new double[]{1.0, 1.5, 2.0, 1.5, 2.0, 2.0, 2.0, 2.0, 2.0, 2.5}, "Первая последовательность из main"),
                Arguments.of(new int[]{6, 10, 2, 6, 5, 0, 6, 3, 1, 0, 0}, new double[]{6.0, 8.0, 6.0, 6.0, 6.0, 5.5, 6.0, 5.5, 5.0, 4.0, 3.0}, "Вторая последовательность из main"),
                Arguments.of(new int[]{3, 1, 2}, new double[]{3.0, 2.0, 2.0}, "Простая последовательность 3,1,2"),
                Arguments.of(new int[]{-1, -2, -3, -4, -5}, new double[]{-1.0, -1.5, -2.0, -2.5, -3.0}, "Отрицательные числа (убывающие)"),
                Arguments.of(new int[]{1, 1, 1, 1}, new double[]{1.0, 1.0, 1.0, 1.0}, "Дубликаты")
        );
    }

    @ParameterizedTest(name = "{2}")
    @MethodSource("sequenceTestCases")
    @DisplayName("Проверка медианы после добавления последовательности чисел")
    void addNum_sequences_medianUpdatesCorrectly(int[] numbersToAdd, double[] expectedMedians, String description) {
        assertEquals(numbersToAdd.length, expectedMedians.length, "Длины массивов чисел и ожидаемых медиан должны совпадать");
        for (int i = 0; i < numbersToAdd.length; i++) {
            medianFinder.addNum(numbersToAdd[i]);
            // System.out.println("Added " + numbersToAdd[i] + ", MaxHeap: " + medianFinder.maxHeap + ", MinHeap: " + medianFinder.minHeap + ", Median: " + medianFinder.findMedian());
            assertEquals(expectedMedians[i], medianFinder.findMedian(), DELTA,
                    "Медиана после добавления " + numbersToAdd[i] + " (шаг " + (i + 1) + ")");
            assertEquals(i + 1, medianFinder.getSize(), "Размер после добавления " + numbersToAdd[i]);
        }
    }
}