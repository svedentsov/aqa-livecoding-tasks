package com.svedentsov.aqa.tasks.arrays_lists;

import java.util.List;
import java.util.Objects;

/**
 * Решение задачи №9: Сумма элементов массива/списка.
 * <p>
 * Описание: Написать функцию для вычисления суммы всех числовых элементов
 * в массиве/списке. (Проверяет: циклы, арифметика)
 * <p>
 * Задание: Напишите метод `long sumElements(int[] numbers)`, который вычисляет
 * и возвращает сумму всех элементов в массиве `numbers`. Используйте `long` для
 * результата, чтобы избежать переполнения.
 * <p>
 * Пример: `sumElements(new int[]{1, 2, 3, 4, 5})` -> `15`. `sumElements(new int[]{})` -> `0`.
 */
public class SumArrayElements {

    /**
     * Вычисляет сумму элементов массива целых чисел итеративно.
     * Использует цикл for-each.
     *
     * @param numbers Массив целых чисел. Может быть null.
     * @return Сумма элементов массива (тип long). Возвращает 0L, если массив null или пуст.
     */
    public long sumElements(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return 0L; // Возвращаем 0 типа long
        }
        long sum = 0L; // Используем long для суммы, инициализируем нулем long
        for (int number : numbers) {
            sum += number; // Сложение int и long дает long
        }
        return sum;
    }

    /**
     * Вычисляет сумму элементов списка целых чисел итеративно.
     * Обрабатывает null элементы в списке, игнорируя их.
     *
     * @param numbers Список Integer. Может быть null.
     * @return Сумма элементов списка (тип long). Возвращает 0L, если список null или пуст.
     */
    public long sumElementsListIterative(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return 0L;
        }
        long sum = 0L;
        for (Integer number : numbers) {
            if (number != null) { // Проверяем на null перед суммированием
                sum += number; // Автоматическое unboxing Integer -> int
            }
        }
        return sum;
    }

    /**
     * Вычисляет сумму элементов списка целых чисел с использованием Stream API.
     * Игнорирует null элементы в списке.
     *
     * @param numbers Список Integer. Может быть null.
     * @return Сумма элементов списка (тип long). Возвращает 0L, если список null или пуст.
     */
    public long sumElementsListStream(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return 0L;
        }
        return numbers.stream()
                .filter(Objects::nonNull) // Игнорируем null элементы в потоке
                .mapToLong(Integer::longValue) // Преобразуем Integer в long (безопаснее чем mapToInt)
                .sum(); // Встроенный метод sum() для LongStream
    }
}
