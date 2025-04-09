package com.svedentsov.aqa.tasks.arrays_lists;

import java.util.Arrays;

/**
 * Решение задачи №39: Повернуть массив на K элементов.
 */
public class Task39_RotateArray {

    /**
     * Вращает массив {@code arr} вправо на {@code k} позиций с использованием
     * дополнительного массива (не in-place).
     * Создает и возвращает новый массив с повернутыми элементами.
     * Сложность O(n) по времени и O(n) по памяти.
     *
     * @param arr Исходный массив. Может быть null.
     * @param k   Количество позиций для сдвига вправо. Может быть отрицательным
     *            (сдвиг влево) или больше длины массива.
     * @return Новый повернутый массив, или ссылка на исходный массив, если вращение
     * не требуется (arr=null, n=0, или k - кратное n).
     */
    public int[] rotateArrayUsingExtraSpace(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return arr; // Возвращаем null или пустой массив как есть
        }
        int n = arr.length;

        // Нормализуем k:
        // 1. k % n: сдвиг на n эквивалентен отсутствию сдвига.
        // 2. Обработка отрицательного k: сдвиг влево на |k| == сдвиг вправо на n - |k|.
        k = k % n;
        if (k < 0) {
            k += n; // Делаем k положительным эквивалентом сдвига вправо
        }

        // Если k=0 после нормализации, вращение не требуется
        if (k == 0) {
            return arr; // Можно вернуть копию: return Arrays.copyOf(arr, n);
            // но по условию задачи часто ожидается ссылка на тот же, если нет изменений
        }

        // Создаем новый массив для результата
        int[] rotated = new int[n];
        // Копируем элементы на новые позиции
        for (int i = 0; i < n; i++) {
            // Элемент с индекса i перемещается на индекс (i + k) % n
            rotated[(i + k) % n] = arr[i];
        }
        return rotated; // Возвращаем новый повернутый массив
    }

    /**
     * Вращает массив {@code arr} вправо на {@code k} позиций "на месте" (in-place)
     * с использованием тройного переворота подмассивов.
     * Сложность O(n) по времени и O(1) по дополнительной памяти.
     * Модифицирует исходный массив {@code arr}.
     *
     * @param arr Исходный массив для вращения. Модифицируется на месте. Не должен быть null.
     * @param k   Количество позиций для сдвига вправо.
     * @throws NullPointerException если arr равен null.
     */
    public void rotateArrayInPlace(int[] arr, int k) {
        if (arr == null) {
            throw new NullPointerException("Input array cannot be null for in-place rotation.");
        }
        if (arr.length <= 1) {
            return; // Нечего вращать
        }
        int n = arr.length;

        // Нормализуем k (аналогично предыдущему методу)
        k = k % n;
        if (k < 0) {
            k += n;
        }
        if (k == 0) {
            return; // Вращение не требуется
        }

        // Алгоритм тройного переворота:
        // 1. Перевернуть весь массив (элементы 0..n-1).
        reverse(arr, 0, n - 1);
        // 2. Перевернуть первые k элементов (0..k-1).
        reverse(arr, 0, k - 1);
        // 3. Перевернуть оставшиеся n-k элементов (k..n-1).
        reverse(arr, k, n - 1);
    }

    /**
     * Вспомогательный приватный метод для переворота подмассива arr[start..end].
     *
     * @param arr   Массив.
     * @param start Начальный индекс подмассива (включительно).
     * @param end   Конечный индекс подмассива (включительно).
     */
    private void reverse(int[] arr, int start, int end) {
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }

    /**
     * Точка входа для демонстрации работы методов вращения массива.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task39_RotateArray sol = new Task39_RotateArray();

        System.out.println("--- Using Extra Space ---");
        int[] arr1_orig = {1, 2, 3, 4, 5};
        int k1 = 2;
        System.out.println("Original: " + Arrays.toString(arr1_orig) + ", k=" + k1);
        int[] rotated1 = sol.rotateArrayUsingExtraSpace(arr1_orig, k1);
        System.out.println("Rotated:  " + Arrays.toString(rotated1)); // [4, 5, 1, 2, 3]

        int[] arr2_orig = {1, 2, 3, 4, 5, 6, 7};
        int k2 = 3;
        System.out.println("Original: " + Arrays.toString(arr2_orig) + ", k=" + k2);
        int[] rotated2 = sol.rotateArrayUsingExtraSpace(arr2_orig, k2);
        System.out.println("Rotated:  " + Arrays.toString(rotated2)); // [5, 6, 7, 1, 2, 3, 4]

        int[] arr3_orig = {1, 2, 3};
        int k3 = 4; // k=1
        System.out.println("Original: " + Arrays.toString(arr3_orig) + ", k=" + k3);
        int[] rotated3 = sol.rotateArrayUsingExtraSpace(arr3_orig, k3);
        System.out.println("Rotated:  " + Arrays.toString(rotated3)); // [3, 1, 2]

        int[] arr4_orig = {1, 2, 3, 4};
        int k4 = -1; // k=3
        System.out.println("Original: " + Arrays.toString(arr4_orig) + ", k=" + k4);
        int[] rotated4 = sol.rotateArrayUsingExtraSpace(arr4_orig, k4);
        System.out.println("Rotated:  " + Arrays.toString(rotated4)); // [2, 3, 4, 1]


        System.out.println("\n--- In-Place Rotation ---");
        int[] arr5 = {1, 2, 3, 4, 5};
        int k5 = 2;
        System.out.println("Original: " + Arrays.toString(arr5) + ", k=" + k5);
        sol.rotateArrayInPlace(arr5, k5);
        System.out.println("Rotated:  " + Arrays.toString(arr5)); // [4, 5, 1, 2, 3]

        int[] arr6 = {1, 2, 3, 4, 5, 6, 7};
        int k6 = 3;
        System.out.println("Original: " + Arrays.toString(arr6) + ", k=" + k6);
        sol.rotateArrayInPlace(arr6, k6);
        System.out.println("Rotated:  " + Arrays.toString(arr6)); // [5, 6, 7, 1, 2, 3, 4]

        int[] arr7 = {1, 2, 3, 4};
        int k7 = -1; // k=3
        System.out.println("Original: " + Arrays.toString(arr7) + ", k=" + k7);
        sol.rotateArrayInPlace(arr7, k7);
        System.out.println("Rotated:  " + Arrays.toString(arr7)); // [2, 3, 4, 1]

        int[] arr8 = {1, 2};
        int k8 = 0; // k=0
        System.out.println("Original: " + Arrays.toString(arr8) + ", k=" + k8);
        sol.rotateArrayInPlace(arr8, k8);
        System.out.println("Rotated:  " + Arrays.toString(arr8)); // [1, 2]
    }
}
