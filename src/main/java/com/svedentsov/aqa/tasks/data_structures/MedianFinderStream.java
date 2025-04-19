package com.svedentsov.aqa.tasks.data_structures;

import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Решение задачи №92: Найти медиану потока чисел.
 * Реализует структуру данных для эффективного нахождения медианы в динамически пополняемом наборе чисел.
 * <p>
 * Описание: Обсудить использование двух куч (heaps).
 * (Проверяет: структуры данных - PriorityQueue/Heap)
 * <p>
 * Задание: Обсудите, как спроектировать структуру данных, которая поддерживает два метода:
 * `addNum(int num)` (добавляет число из потока данных) и `double findMedian()`
 * (возвращает медиану всех добавленных на данный момент чисел). Какую структуру
 * данных (или комбинацию) было бы эффективно использовать?
 * <p>
 * Пример: Обсуждение использования двух куч (min-heap и max-heap).
 * (Представлен класс MedianFinder с такой реализацией).
 */
public class MedianFinderStream {

    /**
     * Структура данных для эффективного нахождения медианы в потоке чисел.
     * Использует две кучи (PriorityQueue):
     * - `maxHeap` (для меньшей половины, с обратным порядком)
     * - `minHeap` (для большей половины, с естественным порядком)
     * Поддерживает баланс размеров: `maxHeap.size() == minHeap.size()` или
     * `maxHeap.size() == minHeap.size() + 1`.
     * Медиана вычисляется за O(1), добавление за O(log n).
     */
    static class MedianFinder {
        private final PriorityQueue<Integer> maxHeap; // Хранит МЕНЬШУЮ половину, вершина - максимум
        private final PriorityQueue<Integer> minHeap; // Хранит БОЛЬШУЮ половину, вершина - минимум

        /**
         * Инициализирует пустую структуру.
         */
        public MedianFinder() {
            maxHeap = new PriorityQueue<>(Collections.reverseOrder()); // Max-heap
            minHeap = new PriorityQueue<>();                           // Min-heap
        }

        /**
         * Добавляет число в структуру, поддерживая баланс куч.
         * Сложность: O(log n).
         *
         * @param num Добавляемое число.
         */
        public void addNum(int num) {
            // 1. Добавляем в maxHeap
            maxHeap.offer(num);
            // 2. Балансируем значения: переносим макс. элемент из maxHeap в minHeap
            minHeap.offer(maxHeap.poll());
            // 3. Балансируем размеры: если minHeap стала больше, переносим мин. элемент обратно
            if (minHeap.size() > maxHeap.size()) {
                maxHeap.offer(minHeap.poll());
            }
        }

        /**
         * Возвращает текущую медиану.
         * Сложность: O(1).
         *
         * @return Медиана (double). Возвращает 0.0 для пустого потока.
         */
        public double findMedian() {
            if (maxHeap.isEmpty()) {
                // Или бросить исключение, или вернуть NaN
                return 0.0;
            }
            // Если размеры равны (четное число элементов), медиана - среднее вершин
            if (maxHeap.size() == minHeap.size()) {
                // Используем .peek() для получения значения без удаления
                return (maxHeap.peek() + minHeap.peek()) / 2.0;
            } else {
                // Если maxHeap больше (нечетное число элементов), медиана - вершина maxHeap
                return maxHeap.peek();
            }
        }

        // Дополнительные методы (не требуются по заданию, но полезны)
        public int getSize() {
            return maxHeap.size() + minHeap.size();
        }

        public String getMaxHeapState() {
            return maxHeap.toString();
        }

        public String getMinHeapState() {
            return minHeap.toString();
        }
    }

    /**
     * Точка входа для демонстрации работы MedianFinder.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        System.out.println("--- Median Finder Demo (Using Two Heaps) ---");
        MedianFinder medianFinder = new MedianFinder();

        // Последовательность добавления чисел и вывода состояния/медианы
        addAndPrint(medianFinder, 1);
        addAndPrint(medianFinder, 2);
        addAndPrint(medianFinder, 3);
        addAndPrint(medianFinder, 0);
        addAndPrint(medianFinder, 5);
        addAndPrint(medianFinder, 2);
        addAndPrint(medianFinder, 4);
        addAndPrint(medianFinder, -1);
        addAndPrint(medianFinder, 6);
        addAndPrint(medianFinder, 6); // Добавим дубликат

        System.out.println("\n--- Another Sequence ---");
        medianFinder = new MedianFinder(); // Новый экземпляр
        addAndPrint(medianFinder, 6);
        addAndPrint(medianFinder, 10);
        addAndPrint(medianFinder, 2);
        addAndPrint(medianFinder, 6);
        addAndPrint(medianFinder, 5);
        addAndPrint(medianFinder, 0);
        addAndPrint(medianFinder, 6);
        addAndPrint(medianFinder, 3);
        addAndPrint(medianFinder, 1);
        addAndPrint(medianFinder, 0);
        addAndPrint(medianFinder, 0);
    }

    /**
     * Вспомогательный метод для добавления числа и печати состояния MedianFinder.
     *
     * @param finder Экземпляр MedianFinder.
     * @param num    Число для добавления.
     */
    private static void addAndPrint(MedianFinder finder, int num) {
        System.out.println("\naddNum(" + num + ")");
        finder.addNum(num);
        System.out.println("  Size: " + finder.getSize());
        System.out.println("  MaxHeap (<= Median): " + finder.getMaxHeapState());
        System.out.println("  MinHeap (>= Median): " + finder.getMinHeapState());
        try {
            System.out.println("  Median: " + finder.findMedian());
        } catch (IllegalStateException e) { // На случай, если findMedian бросает исключение для пустого
            System.out.println("  Median: " + e.getMessage());
        }
        System.out.println("  -------------------------");
    }
}
