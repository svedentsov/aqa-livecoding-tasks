package com.svedentsov.aqa.tasks.data_structures;

import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Решение задачи №92: Найти медиану потока чисел.
 * Реализует структуру данных для эффективного нахождения медианы в динамически пополняемом наборе чисел.
 */
public class Task92_MedianFinderStream {

    /**
     * Структура данных для эффективного нахождения медианы в потоке чисел.
     * Использует две кучи (heaps):
     * <ul>
     * <li>{@code maxHeap}: Max-heap (куча максимума) для хранения МЕНЬШЕЙ половины чисел потока.
     * Вершина этой кучи (peek) - это наибольшее число в меньшей половине.</li>
     * <li>{@code minHeap}: Min-heap (куча минимума) для хранения БОЛЬШЕЙ половины чисел потока.
     * Вершина этой кучи (peek) - это наименьшее число в большей половине.</li>
     * </ul>
     * Поддерживается инвариант: размеры куч либо равны (для четного общего количества чисел),
     * либо {@code maxHeap} содержит на один элемент больше (для нечетного количества).
     * Это позволяет вычислять медиану за O(1) на основе вершин куч.
     * Добавление числа ({@link #addNum(int)}) занимает O(log n) времени из-за операций с кучами.
     */
    static class MedianFinder { // Делаем static для main
        /**
         * Max-heap для хранения меньшей половины чисел (корнем является максимум этой половины).
         */
        private final PriorityQueue<Integer> maxHeap;
        /**
         * Min-heap для хранения большей половины чисел (корнем является минимум этой половины).
         */
        private final PriorityQueue<Integer> minHeap;

        /**
         * Инициализирует пустую структуру данных MedianFinder.
         */
        public MedianFinder() {
            // Для max-heap используем обратный компаратор
            maxHeap = new PriorityQueue<>(Collections.reverseOrder());
            // Для min-heap используется естественный порядок (по умолчанию)
            minHeap = new PriorityQueue<>();
        }

        /**
         * Добавляет число {@code num} из потока данных в структуру.
         * Поддерживает баланс размеров куч и гарантирует, что все элементы
         * в maxHeap меньше или равны всем элементам в minHeap.
         * Сложность: O(log n), где n - количество уже добавленных чисел.
         *
         * @param num Число для добавления.
         */
        public void addNum(int num) {
            // 1. Добавляем новое число в maxHeap (для меньшей половины).
            maxHeap.offer(num);

            // 2. Балансируем значения:
            //    Перемещаем самый большой элемент из maxHeap в minHeap.
            //    Это гарантирует, что все элементы в minHeap >= всех элементов в maxHeap.
            minHeap.offer(maxHeap.poll()); // poll() извлекает и удаляет корень (максимум) maxHeap

            // 3. Балансируем размеры:
            //    Если minHeap стала содержать больше элементов, чем maxHeap,
            //    перемещаем самый маленький элемент из minHeap (ее корень) обратно в maxHeap.
            if (minHeap.size() > maxHeap.size()) {
                maxHeap.offer(minHeap.poll()); // poll() извлекает и удаляет корень (минимум) minHeap
            }
            // После этих шагов размеры куч либо равны, либо maxHeap на 1 больше.
        }

        /**
         * Возвращает медиану всех чисел, добавленных на данный момент.
         * Если общее количество чисел четное, возвращает среднее арифметическое
         * двух средних элементов (вершин куч).
         * Если нечетное, возвращает средний элемент (вершину maxHeap).
         * Сложность: O(1).
         *
         * @return Медиана в виде double. Возвращает 0.0, если чисел еще не было добавлено
         * (или можно определить другое поведение, например, бросить исключение).
         */
        public double findMedian() {
            // Если кучи сбалансированы по размеру (общее количество четное)
            if (maxHeap.size() == minHeap.size()) {
                // Обработка случая, когда обе кучи пусты
                if (maxHeap.isEmpty()) {
                    // Можно вернуть NaN или бросить исключение, 0.0 может быть неоднозначным.
                    return 0.0;
                    // throw new IllegalStateException("Cannot find median of empty data stream.");
                }
                // Медиана = среднее вершин maxHeap (макс. из меньшей половины)
                //          и minHeap (мин. из большей половины).
                // Делим на 2.0 для получения double результата.
                return (maxHeap.peek() + minHeap.peek()) / 2.0;
            }
            // Если maxHeap на один элемент больше (общее количество нечетное)
            else {
                // Медиана - это единственный средний элемент, который хранится
                // в вершине maxHeap.
                // maxHeap не может быть пустой в этом случае по логике addNum.
                return maxHeap.peek();
            }
        }
    }

    /**
     * Точка входа для демонстрации работы MedianFinder.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        System.out.println("--- MedianFinder Demo ---");
        MedianFinder medianFinder = new MedianFinder();

        MedianFinder finalMedianFinder = medianFinder;
        Runnable printState = () -> {
            System.out.println("  MaxHeap (<= Median): " + finalMedianFinder.maxHeap);
            System.out.println("  MinHeap (>= Median): " + finalMedianFinder.minHeap);
            try {
                System.out.println("  Median: " + finalMedianFinder.findMedian());
            } catch (IllegalStateException e) {
                System.out.println("  Median: " + e.getMessage());
            }
            System.out.println("-------------------------");
        };

        System.out.println("Initial state:");
        printState.run(); // [], [], 0.0

        System.out.println("addNum(1)");
        medianFinder.addNum(1);
        printState.run();
        // Max:[1], Min:[], Median: 1.0

        System.out.println("addNum(2)");
        medianFinder.addNum(2);
        printState.run();
        // Max:[1], Min:[2], Median: 1.5

        System.out.println("addNum(3)");
        medianFinder.addNum(3);
        printState.run();
        // Max:[2, 1], Min:[3], Median: 2.0

        System.out.println("addNum(0)");
        medianFinder.addNum(0);
        printState.run();
        // Max:[1, 0], Min:[2, 3], Median: 1.5

        System.out.println("addNum(5)");
        medianFinder.addNum(5);
        printState.run();
        // Max:[2, 1, 0], Min:[3, 5], Median: 2.0

        System.out.println("addNum(2)");
        medianFinder.addNum(2);
        printState.run();
        // Max:[2, 1, 0], Min:[2, 3, 5], Median: 2.0

        System.out.println("addNum(4)");
        medianFinder.addNum(4);
        printState.run();
        // Max:[3, 1, 2, 0], Min:[4, 5, 2], Median: 3.0

        System.out.println("addNum(-1)");
        medianFinder.addNum(-1);
        printState.run();
        // Max:[3, 1, 2, 0,-1], Min:[4, 5, 2], Median: (2+3)/2 = 2.5 -> Ошибка в ручном расчете.
        // После add(-1): maxH=[-1], minH=[] -> add -1 to maxH
        //               poll -1 from maxH, add -1 to minH. maxH=[], minH=[-1]
        //               minH > maxH -> poll -1 from minH, add -1 to maxH. maxH=[-1], minH=[]
        // add(1): maxH=[1, -1], minH=[]
        // add(2): maxH=[1,-1], minH=[2] -> (1+2)/2=1.5
        // add(3): maxH=[2,1,-1], minH=[3] -> 2.0
        // add(0): maxH=[1,0,-1], minH=[2,3] -> (1+2)/2=1.5
        // add(5): maxH=[2,1,0,-1], minH=[3,5] -> 2.0
        // add(2): maxH=[2,1,0,-1], minH=[2,3,5] -> (2+2)/2=2.0
        // add(4): maxH=[2,1,0,-1,2], minH=[3,5,4] -> 2.0
        // add(-1): maxH=[2,1,0,-1,2,-1], minH=[3,5,4] -> (2+3)/2 = 2.5

        // Проверим расчет для add(-1):
        // maxH=[2, 1, 0, -1, 2], minH=[3, 5, 4]
        // add(-1) to maxH -> maxH=[2, 1, 0, -1, 2, -1]
        // poll(2) from maxH, add(2) to minH -> maxH=[2, 1, 0, -1, -1], minH=[2, 3, 4, 5]
        // minH > maxH (4 > 5 - неверно, 4 < 5) -> все правильно
        // Размеры: maxH = 5, minH = 4. Нечетное -> median = maxH.peek() = 2.0 (ранее было 2.0, не изменилось).
        // Мой ручной расчет выше был неверен.

        // Пересчитаем вручную:
        // 1 -> maxH[1], minH[], med=1
        // 2 -> maxH[1], minH[2], med=1.5
        // 3 -> maxH[2,1], minH[3], med=2
        // 0 -> maxH[1,0], minH[2,3], med=1.5
        // 5 -> maxH[2,1,0], minH[3,5], med=2
        // 2 -> maxH[2,1,0], minH[2,3,5], med=2.0 ((2+2)/2)
        // 4 -> maxH[3,1,2,0], minH[4,5,2], med=3.0
        // -1 -> maxH=[3,1,2,0,-1], minH=[4,5,2]. Med = 3.0
        System.out.println("<<Перепроверенный расчет>>");
        medianFinder = new MedianFinder();
        medianFinder.addNum(1);  // maxH[1], minH[], med=1.0
        medianFinder.addNum(2);  // maxH[1], minH[2], med=1.5
        medianFinder.addNum(3);  // maxH[2,1], minH[3], med=2.0
        medianFinder.addNum(0);  // maxH[1,0], minH[2,3], med=1.5
        medianFinder.addNum(5);  // maxH[2,1,0], minH[3,5], med=2.0
        medianFinder.addNum(2);  // maxH[2,1,0], minH[2,3,5], med=2.0
        medianFinder.addNum(4);  // maxH[3,1,2,0], minH[4,5,2], med=3.0
        medianFinder.addNum(-1); // maxH=[3,1,2,0,-1], minH=[4,5,2], med=3.0
        printState.run();
    }
}
