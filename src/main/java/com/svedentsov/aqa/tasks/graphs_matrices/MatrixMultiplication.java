package com.svedentsov.aqa.tasks.graphs_matrices;

import java.util.Arrays;

/**
 * Решение задачи №60: Перемножение матриц.
 * <p>
 * Описание: (Проверяет: вложенные циклы, работа с 2D массивами)
 * <p>
 * Задание: Напишите метод `int[][] multiplyMatrices(int[][] matrixA, int[][] matrixB)`,
 * который перемножает две матрицы (не обязательно квадратные) и возвращает
 * результирующую матрицу. Проверьте совместимость матриц для умножения.
 * <p>
 * Пример: `matrixA = {{1, 2}, {3, 4}}, matrixB = {{2, 0}, {1, 2}}`.
 * `multiplyMatrices(matrixA, matrixB)` -> `{{4, 4}, {10, 8}}`.
 */
public class MatrixMultiplication {

    /**
     * Перемножает две матрицы matrixA и matrixB (A * B).
     * Умножение возможно, если количество столбцов в matrixA равно количеству строк в matrixB.
     * Результирующая матрица C будет иметь размер rowsA x colsB.
     * Элемент C[i][j] вычисляется как скалярное произведение i-ой строки matrixA
     * и j-го столбца matrixB: C[i][j] = Σ (A[i][k] * B[k][j]) по всем k от 0 до colsA-1.
     *
     * @param matrixA Первая матрица (размер rowsA x colsA).
     * @param matrixB Вторая матрица (размер rowsB x colsB).
     * @return Результирующая матрица C размером rowsA x colsB.
     * @throws IllegalArgumentException если матрицы null, пустые, не прямоугольные
     *                                  или их размеры несовместимы для умножения (colsA != rowsB).
     */
    public int[][] multiplyMatrices(int[][] matrixA, int[][] matrixB) {
        // 1. Валидация входных матриц
        validateMatrixNotNullOrEmpty(matrixA, "Matrix A");
        validateMatrixNotNullOrEmpty(matrixB, "Matrix B");

        int rowsA = matrixA.length;
        int colsA = matrixA[0].length; // Длина первой строки
        int rowsB = matrixB.length;
        int colsB = matrixB[0].length; // Длина первой строки

        // 2. Проверка на прямоугольность
        validateMatrixIsRectangular(matrixA, colsA, "Matrix A");
        validateMatrixIsRectangular(matrixB, colsB, "Matrix B");

        // 3. Проверка совместимости размеров для умножения
        if (colsA != rowsB) {
            throw new IllegalArgumentException(
                    "Matrices dimensions are incompatible for multiplication: " +
                            "colsA (" + colsA + ") != rowsB (" + rowsB + ").");
        }

        // 4. Создание результирующей матрицы
        int[][] resultMatrix = new int[rowsA][colsB];

        // 5. Вычисление элементов результирующей матрицы
        for (int i = 0; i < rowsA; i++) { // Итерация по строкам результата (и A)
            for (int j = 0; j < colsB; j++) { // Итерация по столбцам результата (и B)
                // Вычисляем C[i][j] = Σ (A[i][k] * B[k][j])
                long sum = 0L; // Используем long для промежуточной суммы во избежание переполнения int
                for (int k = 0; k < colsA; k++) { // colsA == rowsB
                    sum += (long) matrixA[i][k] * matrixB[k][j];
                    // Проверка на переполнение результата int на каждом шаге сложения
                    if (sum > Integer.MAX_VALUE || sum < Integer.MIN_VALUE) {
                        throw new ArithmeticException("Integer overflow during matrix multiplication at result[" + i + "][" + j + "]");
                    }
                }
                resultMatrix[i][j] = (int) sum; // Записываем результат
            }
        }

        return resultMatrix;
    }

    /**
     * Точка входа для демонстрации работы метода умножения матриц.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        MatrixMultiplication sol = new MatrixMultiplication();

        System.out.println("--- Matrix Multiplication ---");

        runMatrixMultiplicationTest(sol,
                new int[][]{{1, 2}, {3, 4}},        // A (2x2)
                new int[][]{{2, 0}, {1, 2}},        // B (2x2)
                "2x2 Example"
        ); // Expected: [[4, 4], [10, 8]]

        runMatrixMultiplicationTest(sol,
                new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}, // C (3x3)
                new int[][]{{9, 8, 7}, {6, 5, 4}, {3, 2, 1}}, // D (3x3)
                "3x3 Example"
        ); // Expected: [[30, 24, 18], [84, 69, 54], [138, 114, 90]]

        runMatrixMultiplicationTest(sol,
                new int[][]{{1, 2, 3}, {4, 5, 6}},           // G (2x3)
                new int[][]{{7, 8}, {9, 10}, {11, 12}},       // H (3x2)
                "Non-Square Example (2x3 * 3x2)"
        ); // Expected: [[58, 64], [139, 154]]

        runMatrixMultiplicationTest(sol,
                new int[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}}, // I (3x3)
                new int[][]{{9, 8, 7}, {6, 5, 4}, {3, 2, 1}}, // D (3x3)
                "Identity Matrix Example (I * D)"
        ); // Expected: [[9, 8, 7], [6, 5, 4], [3, 2, 1]]

        // --- Тесты на ошибки ---
        runMatrixMultiplicationTest(sol,
                new int[][]{{1, 2}, {3, 4}},                  // E (2x2)
                new int[][]{{1, 0}, {2, 1}, {3, 2}},          // F (3x2)
                "Incompatible Example (2x2 * 3x2)"
        ); // Expected: IllegalArgumentException

        runMatrixMultiplicationTest(sol, null, new int[][]{{1}}, "Null Matrix A"); // Expected: IllegalArgumentException
        runMatrixMultiplicationTest(sol, new int[][]{{1}}, null, "Null Matrix B"); // Expected: IllegalArgumentException
        runMatrixMultiplicationTest(sol, new int[][]{}, new int[][]{{1}}, "Empty Matrix A"); // Expected: IllegalArgumentException
        runMatrixMultiplicationTest(sol, new int[][]{{1, 2}, {3}}, new int[][]{{1}}, "Non-Rectangular Matrix A"); // Expected: IllegalArgumentException
        runMatrixMultiplicationTest(sol, new int[][]{{Integer.MAX_VALUE, 1}}, new int[][]{{1}, {2}}, "Potential Overflow Example"); // Expected: ArithmeticException or Correct Result
    }

    /**
     * Проверяет, что матрица не null, не пуста и первая строка не null/пуста.
     */
    private void validateMatrixNotNullOrEmpty(int[][] matrix, String matrixName) {
        if (matrix == null) {
            throw new IllegalArgumentException(matrixName + " cannot be null.");
        }
        if (matrix.length == 0) {
            throw new IllegalArgumentException(matrixName + " cannot be empty.");
        }
        if (matrix[0] == null) {
            throw new IllegalArgumentException("First row of " + matrixName + " cannot be null.");
        }
        if (matrix[0].length == 0) {
            throw new IllegalArgumentException("First row of " + matrixName + " cannot be empty.");
        }
    }

    /**
     * Проверяет, что все строки матрицы имеют одинаковую длину.
     */
    private void validateMatrixIsRectangular(int[][] matrix, int expectedCols, String matrixName) {
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i] == null || matrix[i].length != expectedCols) {
                throw new IllegalArgumentException(matrixName + " is not rectangular or contains null rows.");
            }
        }
    }

    /**
     * Вспомогательный метод для печати матрицы.
     */
    private static void printMatrix(String name, int[][] matrix) {
        System.out.println(name + ":");
        if (matrix == null) {
            System.out.println("null");
            return;
        }
        if (matrix.length == 0) {
            System.out.println("[]");
            return;
        }

        for (int[] row : matrix) {
            System.out.println("  " + Arrays.toString(row));
        }
    }

    /**
     * Вспомогательный метод для тестирования умножения матриц.
     *
     * @param sol         Экземпляр решателя.
     * @param matrixA     Первая матрица.
     * @param matrixB     Вторая матрица.
     * @param description Описание теста.
     */
    private static void runMatrixMultiplicationTest(MatrixMultiplication sol, int[][] matrixA, int[][] matrixB, String description) {
        System.out.println("\n--- " + description + " ---");
        printMatrix("Matrix A", matrixA);
        printMatrix("Matrix B", matrixB);
        System.out.print("Result A * B: ");
        try {
            int[][] result = sol.multiplyMatrices(matrixA, matrixB);
            System.out.println(); // Перенос строки после "Result: "
            printMatrix("", result); // Печатаем результат
        } catch (IllegalArgumentException | ArithmeticException e) {
            System.out.println("Error - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected Error - " + e.getMessage());
            e.printStackTrace(); // Для отладки
        }
    }
}
