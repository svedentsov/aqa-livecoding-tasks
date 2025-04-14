package com.svedentsov.aqa.tasks.arrays_lists;

import java.util.Arrays;

/**
 * Решение задачи №45: Переместить все нули в конец массива.
 * <p>
 * Описание: Сохраняя относительный порядок ненулевых элементов.
 * (Проверяет: работа с массивами, два указателя, циклы)
 * <p>
 * Задание: Напишите метод `void moveZeroes(int[] nums)`, который перемещает
 * все нули в конец массива `nums`, сохраняя относительный порядок ненулевых
 * элементов. Модифицируйте массив "на месте".
 * <p>
 * Пример: `nums = [0, 1, 0, 3, 12]`. После `moveZeroes(nums)` массив
 * должен стать `[1, 3, 12, 0, 0]`.
 */
public class MoveZeroesEnd {

    /**
     * Перемещает все нули в конец массива {@code nums}, сохраняя относительный
     * порядок ненулевых элементов. Модификация происходит "на месте" (in-place).
     * Использует подход с одним указателем (`insertPos` или `nonZeroIndex`),
     * который отслеживает позицию для вставки следующего ненулевого элемента.
     * <p>
     * Сложность: O(n) по времени (один проход по массиву), O(1) по памяти.
     * <p>
     * Алгоритм:
     * 1. Инициализировать указатель `insertPos = 0`.
     * 2. Итерировать по массиву `nums` с индексом `i` от 0 до `n-1`.
     * 3. Если `nums[i]` не равен 0:
     * a. Записать `nums[i]` в позицию `nums[insertPos]`.
     * b. Увеличить `insertPos`.
     * 4. После первого прохода все ненулевые элементы находятся в начале массива
     * (до индекса `insertPos`), сохраняя свой относительный порядок.
     * 5. Итерировать от `insertPos` до конца массива (`n-1`).
     * 6. Записать 0 в каждую из этих позиций (`nums[i] = 0`).
     *
     * @param nums Массив для модификации. Может быть null. Если null или длина <= 1,
     *             метод ничего не делает.
     */
    public void moveZeroes(int[] nums) {
        // Проверка на null и тривиальные случаи
        if (nums == null || nums.length <= 1) {
            return;
        }

        int n = nums.length;
        int insertPos = 0; // Указатель на место для следующего ненулевого элемента

        // Шаг 1-3: Перемещаем ненулевые элементы в начало
        for (int i = 0; i < n; i++) {
            if (nums[i] != 0) {
                // Если insertPos == i, это значит, что пока не было нулей
                // или они идут подряд в конце. Запись nums[i] в nums[insertPos]
                // просто перезапишет элемент самим собой.
                // Если insertPos < i, значит, были пропущены нули, и мы
                // перезаписываем ноль (или предыдущий ненулевой, если swap)
                // текущим ненулевым элементом.
                nums[insertPos] = nums[i];
                insertPos++; // Сдвигаем позицию для вставки
            }
        }

        // Шаг 5-6: Заполняем оставшуюся часть массива нулями
        while (insertPos < n) {
            nums[insertPos] = 0;
            insertPos++;
        }
    }

    /*
    // Альтернативная реализация с обменом (swap) - может быть чуть менее эффективна
    // из-за лишних записей нулей.
    public void moveZeroesSwap(int[] nums) {
        if (nums == null || nums.length <= 1) return;
        int nonZeroIndex = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                // Обмен nums[i] с nums[nonZeroIndex]
                int temp = nums[nonZeroIndex];
                nums[nonZeroIndex] = nums[i];
                nums[i] = temp; // Ставим 0 (или предыдущий ненулевой) на место i
                nonZeroIndex++;
            }
        }
    }
    */

    /**
     * Точка входа для демонстрации работы метода перемещения нулей.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        MoveZeroesEnd sol = new MoveZeroesEnd();

        System.out.println("--- Moving Zeroes to End (In-Place) ---");

        runMoveZeroesTest(sol, new int[]{0, 1, 0, 3, 12}, "Стандартный случай");
        // Ожидается: [1, 3, 12, 0, 0]

        runMoveZeroesTest(sol, new int[]{0, 0, 1}, "Нули в начале");
        // Ожидается: [1, 0, 0]

        runMoveZeroesTest(sol, new int[]{1, 2, 3}, "Нет нулей");
        // Ожидается: [1, 2, 3]

        runMoveZeroesTest(sol, new int[]{0}, "Один ноль");
        // Ожидается: [0]

        runMoveZeroesTest(sol, new int[]{1}, "Один ненулевой");
        // Ожидается: [1]

        runMoveZeroesTest(sol, new int[]{1, 0, 2, 0, 0, 3}, "Смешанный случай");
        // Ожидается: [1, 2, 3, 0, 0, 0]

        runMoveZeroesTest(sol, new int[]{0, 0, 0, 0}, "Только нули");
        // Ожидается: [0, 0, 0, 0]

        runMoveZeroesTest(sol, new int[]{1, 0, 1, 0, 1}, "Чередующиеся");
        // Ожидается: [1, 1, 1, 0, 0]

        runMoveZeroesTest(sol, new int[]{}, "Пустой массив");
        // Ожидается: []

        runMoveZeroesTest(sol, null, "Null массив");
        // Ожидается: null
    }

    /**
     * Вспомогательный метод для тестирования moveZeroes.
     *
     * @param sol         Экземпляр решателя.
     * @param nums        Массив для теста (будет изменен!).
     * @param description Описание теста.
     */
    private static void runMoveZeroesTest(MoveZeroesEnd sol, int[] nums, String description) {
        System.out.println("\n--- " + description + " ---");
        String originalString = (nums == null ? "null" : Arrays.toString(nums));
        System.out.println("Original: " + originalString);
        try {
            // Создаем копию для вывода ожидаемого результата, если нужно
            // int[] expected = calculateExpected(nums);
            sol.moveZeroes(nums); // Модифицируем массив
            String movedString = (nums == null ? "null" : Arrays.toString(nums));
            System.out.println("Moved:    " + movedString);
            // if (!Arrays.equals(nums, expected)) System.err.println("   Mismatch!");
        } catch (Exception e) {
            System.err.println("Moved:    Error - " + e.getMessage());
        }
    }
}
