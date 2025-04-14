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
     * @return Новый повернутый массив, или ссылка на исходный массив, если вращение
     * не требуется (arr=null, n=0, или k - кратное n), или пустой массив, если arr пуст.
     */
    public int[] rotateArrayUsingExtraSpace(int[] arr, int k) {
        if (arr == null) {
            return null; // Возвращаем null, если на вход null
        }
        int n = arr.length;
        if (n == 0) {
            return arr; // Возвращаем пустой массив как есть
        }

        // Нормализуем k, чтобы он был в диапазоне [0, n-1]
        int effectiveK = k % n;
        if (effectiveK < 0) {
            effectiveK += n; // Сдвиг влево на |k| == сдвиг вправо на n - |k|
        }

        // Если эффективный сдвиг равен 0, вращение не требуется
        if (effectiveK == 0) {
            // Возвращаем КОПИЮ, чтобы не возвращать тот же самый объект
            // если k было, например, n.
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
     * <p>
     * Алгоритм:
     * 1. Нормализовать `k` до `effectiveK` в диапазоне `[0, n-1]`.
     * 2. Если `effectiveK == 0`, ничего не делать.
     * 3. Перевернуть весь массив (элементы `0` до `n-1`).
     * 4. Перевернуть первые `effectiveK` элементов (от `0` до `effectiveK-1`).
     * 5. Перевернуть оставшиеся `n - effectiveK` элементов (от `effectiveK` до `n-1`).
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
     * Точка входа для демонстрации работы методов вращения массива.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        RotateArray sol = new RotateArray();

        // Тесты для метода с доп. пространством
        System.out.println("--- Rotation Using Extra Space ---");
        runRotateTest(sol, new int[]{1, 2, 3, 4, 5}, 2, false);       // [4, 5, 1, 2, 3]
        runRotateTest(sol, new int[]{1, 2, 3, 4, 5, 6, 7}, 3, false); // [5, 6, 7, 1, 2, 3, 4]
        runRotateTest(sol, new int[]{1, 2, 3}, 4, false);             // [3, 1, 2] (k=1)
        runRotateTest(sol, new int[]{1, 2, 3, 4}, -1, false);         // [2, 3, 4, 1] (k=3)
        runRotateTest(sol, new int[]{1, 2}, 0, false);                // [1, 2]
        runRotateTest(sol, new int[]{1, 2}, 2, false);                // [1, 2] (k=0)
        runRotateTest(sol, new int[]{1}, 5, false);                   // [1]
        runRotateTest(sol, new int[]{}, 3, false);                    // []
        runRotateTest(sol, null, 3, false);                       // null

        // Тесты для метода in-place
        System.out.println("\n--- Rotation In-Place ---");
        runRotateTest(sol, new int[]{1, 2, 3, 4, 5}, 2, true);      // [4, 5, 1, 2, 3]
        runRotateTest(sol, new int[]{1, 2, 3, 4, 5, 6, 7}, 3, true);  // [5, 6, 7, 1, 2, 3, 4]
        runRotateTest(sol, new int[]{1, 2, 3}, 4, true);          // [3, 1, 2] (k=1)
        runRotateTest(sol, new int[]{1, 2, 3, 4}, -1, true);      // [2, 3, 4, 1] (k=3)
        runRotateTest(sol, new int[]{1, 2}, 0, true);          // [1, 2]
        runRotateTest(sol, new int[]{1, 2}, 2, true);          // [1, 2] (k=0)
        runRotateTest(sol, new int[]{1}, 5, true);             // [1]
        runRotateTest(sol, new int[]{}, 3, true);              // []
        runRotateTest(sol, null, 3, true);                 // Exception
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
        // Проверка границ (необязательно, но полезно)
        if (arr == null || start < 0 || end >= arr.length || start >= end) {
            return;
        }
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

    /**
     * Вспомогательный метод для тестирования вращения массива.
     *
     * @param sol     Экземпляр решателя.
     * @param arr     Исходный массив.
     * @param k       Количество позиций сдвига.
     * @param inPlace Использовать ли in-place метод.
     */
    private static void runRotateTest(RotateArray sol, int[] arr, int k, boolean inPlace) {
        String methodType = inPlace ? "In-Place" : "Extra Space";
        System.out.println("\n-- Test (" + methodType + ") --");
        String originalString = (arr == null ? "null" : Arrays.toString(arr));
        System.out.println("Original: " + originalString + ", k=" + k);

        if (inPlace) {
            // Для in-place создаем копию, чтобы исходный массив (если он из main)
            // не изменялся для других тестов в рамках одного запуска main.
            int[] arrCopy = (arr == null) ? null : Arrays.copyOf(arr, arr.length);
            System.out.print("Rotated:  ");
            try {
                sol.rotateArrayInPlace(arrCopy, k);
                System.out.println(Arrays.toString(arrCopy));
            } catch (NullPointerException e) {
                System.out.println("Error - " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected Error - " + e.getMessage());
            }
        } else {
            System.out.print("Rotated:  ");
            try {
                int[] rotated = sol.rotateArrayUsingExtraSpace(arr, k);
                System.out.println(rotated == null ? "null" : Arrays.toString(rotated));
            } catch (Exception e) {
                System.out.println("Unexpected Error - " + e.getMessage());
            }
        }
    }
}
