package com.svedentsov.aqa.tasks.arrays_lists;

import java.util.Arrays;
import java.util.Objects;

/**
 * Решение задачи №39: Повернуть массив на K элементов.
 * <p>
 * Описание: Сдвинуть элементы массива циклически влево или вправо на K позиций.
 * (Проверяет: работа с массивами, логика, возможно доп. массив)
 * <p>
 * Задание: Напишите метод `void rotateArray(int[] arr, int k)`, который циклически
 * сдвигает элементы массива `arr` вправо на `k` позиций. `k` может быть
 * больше длины массива или отрицательным. Модифицируйте массив "на месте" (in-place),
 * если возможно, или верните новый. (Представлены оба подхода).
 * <p>
 * Пример: `arr = [1, 2, 3, 4, 5], k = 2`. После `rotateArrayInPlace(arr, k)`
 * массив `arr` должен стать `[4, 5, 1, 2, 3]`.
 */
public class RotateArray {

    /**
     * Вращает массив {@code arr} вправо на {@code k} позиций с использованием
     * дополнительного массива (не in-place).
     * Создает и возвращает новый массив с повернутыми элементами.
     * <p>
     * Сложность: O(n) по времени и O(n) по памяти.
     *
     * @param arr Исходный массив. Может быть null.
     * @param k   Количество позиций для сдвига вправо. Может быть отрицательным
     *            (сдвиг влево) или больше длины массива.
     * @return Новый повернутый массив, или null если arr=null, или КОПИЯ исходного если
     * вращение не требуется (n=0, или k - кратное n), или пустой массив, если arr пуст.
     */
    public int[] rotateArrayUsingExtraSpace(int[] arr, int k) {
        if (arr == null) {
            return null; // Возвращаем null, если на вход null
        }
        int n = arr.length;
        if (n == 0) {
            // Возвращаем КОПИЮ пустого массива, чтобы соответствовать
            // контракту возврата копии при k=0
            return Arrays.copyOf(arr, 0);
        }

        // Нормализуем k, чтобы он был в диапазоне [0, n-1]
        int effectiveK = k % n;
        if (effectiveK < 0) {
            effectiveK += n; // Сдвиг влево на |k| == сдвиг вправо на n - |k|
        }

        // Если эффективный сдвиг равен 0, вращение не требуется, возвращаем копию
        if (effectiveK == 0) {
            return Arrays.copyOf(arr, n);
        }

        // Создаем новый массив для результата
        int[] rotated = new int[n];
        // Копируем элементы на новые позиции
        for (int i = 0; i < n; i++) {
            // Элемент с исходного индекса i перемещается на новый индекс (i + k) % n
            rotated[(i + effectiveK) % n] = arr[i];
        }
        return rotated; // Возвращаем новый повернутый массив
    }

    /**
     * Вращает массив {@code arr} вправо на {@code k} позиций "на месте" (in-place)
     * с использованием тройного переворота подмассивов.
     * Этот метод модифицирует переданный массив {@code arr}.
     * <p>
     * Сложность: O(n) по времени и O(1) по дополнительной памяти.
     *
     * @param arr Исходный массив для вращения. Модифицируется на месте. Не должен быть null.
     * @param k   Количество позиций для сдвига вправо.
     * @throws NullPointerException если arr равен null.
     */
    public void rotateArrayInPlace(int[] arr, int k) {
        Objects.requireNonNull(arr, "Input array cannot be null for in-place rotation.");

        int n = arr.length;
        if (n <= 1) {
            return; // Нечего вращать для массива с 0 или 1 элементом
        }

        // Нормализуем k
        int effectiveK = k % n;
        if (effectiveK < 0) {
            effectiveK += n;
        }
        if (effectiveK == 0) {
            return; // Вращение не требуется
        }

        // Выполняем тройной переворот
        reverse(arr, 0, n - 1);      // Переворот всего массива
        reverse(arr, 0, effectiveK - 1); // Переворот первой части (0 до k-1)
        reverse(arr, effectiveK, n - 1); // Переворот второй части (k до n-1)
    }

    /**
     * Вспомогательный приватный метод для переворота подмассива arr[start..end].
     * Используется методом {@code rotateArrayInPlace}.
     *
     * @param arr   Массив.
     * @param start Начальный индекс подмассива (включительно).
     * @param end   Конечный индекс подмассива (включительно).
     */
    private void reverse(int[] arr, int start, int end) {
        while (start < end) {
            // Обмен элементов
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            // Сдвиг указателей
            start++;
            end--;
        }
    }
}
