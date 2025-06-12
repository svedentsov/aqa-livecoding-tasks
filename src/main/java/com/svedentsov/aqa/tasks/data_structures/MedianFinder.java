package com.svedentsov.aqa.tasks.data_structures;

import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Решение задачи №92: Найти медиану потока чисел.
 * Реализует структуру данных для эффективного нахождения медианы в динамически пополняемом наборе чисел.
 * Описание: Обсудить использование двух куч (heaps).
 * (Проверяет: структуры данных - PriorityQueue/Heap)
 * Задание: Обсудите, как спроектировать структуру данных, которая поддерживает два метода:
 * `addNum(int num)` (добавляет число из потока данных) и `double findMedian()`
 * (возвращает медиану всех добавленных на данный момент чисел). Какую структуру
 * данных (или комбинацию) было бы эффективно использовать?
 * Пример: Обсуждение использования двух куч (min-heap и max-heap).
 * (Представлен класс MedianFinder с такой реализацией).
 */
public class MedianFinder {
    private final PriorityQueue<Integer> maxHeap; // Хранит МЕНЬШУЮ половину, вершина - максимум
    private final PriorityQueue<Integer> minHeap; // Хранит БОЛЬШУЮ половину, вершина - минимум

    /**
     * Инициализирует пустую структуру MedianFinder.
     */
    public MedianFinder() {
        // maxHeap хранит меньшую половину чисел, поэтому элементы упорядочены по убыванию (вершина - максимум)
        maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        // minHeap хранит большую половину чисел, элементы упорядочены по возрастанию (вершина - минимум)
        minHeap = new PriorityQueue<>();
    }

    /**
     * Добавляет число в структуру данных.
     * При добавлении числа поддерживается баланс куч:
     * 1. Число добавляется в maxHeap.
     * 2. Самый большой элемент из maxHeap перемещается в minHeap.
     * (Это гарантирует, что все элементы в maxHeap <= всех элементов в minHeap)
     * 3. Если minHeap стала больше maxHeap по размеру, самый маленький элемент
     * из minHeap перемещается обратно в maxHeap.
     * (Это поддерживает условие, что maxHeap.size() >= minHeap.size() и
     * maxHeap.size() - minHeap.size() <= 1)
     *
     * @param num Добавляемое число.
     */
    public void addNum(int num) {
        maxHeap.offer(num);      // Добавляем в кучу меньших чисел
        minHeap.offer(maxHeap.poll()); // Балансируем: наибольшее из меньших уходит в кучу больших

        // Поддерживаем размеры: maxHeap может быть больше minHeap не более чем на 1 элемент
        if (minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }

    /**
     * Возвращает текущую медиану всех добавленных чисел.
     *
     * @return Медиана. Если чисел нет, возвращает 0.0 (по условию изначального примера).
     * В реальных сценариях лучше выбрасывать исключение или возвращать Optional/NaN.
     */
    public double findMedian() {
        if (maxHeap.isEmpty()) {
            return 0.0; // Или: throw new IllegalStateException("No numbers added yet.");
        }

        // Если количество элементов четное, медиана - среднее двух центральных
        if (maxHeap.size() == minHeap.size()) {
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        } else {
            // Если количество элементов нечетное, медиана - вершина maxHeap
            return maxHeap.peek();
        }
    }

    /**
     * Возвращает общее количество чисел, добавленных в структуру.
     *
     * @return Общее количество чисел.
     */
    public int getSize() {
        return maxHeap.size() + minHeap.size();
    }
}
