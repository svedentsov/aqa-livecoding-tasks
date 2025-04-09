package com.svedentsov.aqa.tasks.graphs_matrices;

import java.util.Arrays;

/**
 * Решение задачи №60: Перемножение матриц.
 */
public class Task60_MatrixMultiplication {

    /**
     * Перемножает две матрицы matrixA и matrixB (A * B).
     * Умножение возможно, если количество столбцов в matrixA равно количеству строк в matrixB.
     * Результирующая матрица C будет иметь размер rowsA x colsB.
     * Элемент C[i][j] вычисляется как скалярное произведение i-ой строки matrixA
     * и j-го столбца matrixB: C[i][j] = Σ (A[i][k] * B[k][j]) по всем k.
     *
     * @param matrixA Первая матрица (размер rowsA x colsA).
     * @param matrixB Вторая матрица (размер rowsB x colsB).
     * @return Результирующая матрица C размером rowsA x colsB.
     * @throws IllegalArgumentException если матрицы null, пустые, не прямоугольные
     *                                  или их размеры несовместимы для умножения (colsA != rowsB).
     */
    public int[][] multiplyMatrices(int[][] matrixA, int[][] matrixB) {
        // 1. Проверка на null и пустые матрицы
        if (matrixA == null || matrixB == null || matrixA.length == 0) {
            throw new IllegalArgumentException("Input matrices cannot be null or empty.");
        }
        // Проверяем первую строку на null/пустоту (если matrixA не пуста)
        if (matrixA[0] == null || matrixA[0].length == 0) {
            throw new IllegalArgumentException("First row of matrixA cannot be null or empty.");
        }
        if (matrixB.length == 0 || matrixB[0] == null || matrixB[0].length == 0) {
            throw new IllegalArgumentException("MatrixB cannot be empty or have empty rows.");
        }


        int rowsA = matrixA.length;    // Количество строк в A
        int colsA = matrixA[0].length; // Количество столбцов в A (из первой строки)
        int rowsB = matrixB.length;    // Количество строк в B
        int colsB = matrixB[0].length; // Количество столбцов в B (из первой строки)

        // 2. Проверка на прямоугольность матриц (все строки должны иметь одинаковую длину)
        for (int i = 1; i < rowsA; i++) {
            if (matrixA[i] == null || matrixA[i].length != colsA) {
                throw new IllegalArgumentException("Matrix A is not rectangular or contains null rows.");
            }
        }
        for (int i = 1; i < rowsB; i++) {
            if (matrixB[i] == null || matrixB[i].length != colsB) {
                throw new IllegalArgumentException("Matrix B is not rectangular or contains null rows.");
            }
        }

        // 3. Проверка совместимости размеров для умножения
        if (colsA != rowsB) {
            throw new IllegalArgumentException(
                    "Matrices dimensions are incompatible for multiplication: " +
                            "colsA (" + colsA + ") != rowsB (" + rowsB + ").");
        }

        // 4. Создание результирующей матрицы размером rowsA x colsB
        int[][] resultMatrix = new int[rowsA][colsB];

        // 5. Вычисление каждого элемента результирующей матрицы C[i][j]
        for (int i = 0; i < rowsA; i++) { // Итерация по строкам результата (и A)
            for (int j = 0; j < colsB; j++) { // Итерация по столбцам результата (и B)
                int sum = 0;
                // Вычисление скалярного произведения: Σ (A[i][k] * B[k][j])
                for (int k = 0; k < colsA; k++) { // Итерация по общему измерению (colsA или rowsB)
                    // Можно добавить проверку на переполнение int при умножении/сложении, если нужно
                    sum += matrixA[i][k] * matrixB[k][j];
                }
                resultMatrix[i][j] = sum; // Записываем результат в ячейку
            }
        }

        return resultMatrix;
    }

    /**
     * Вспомогательный метод для печати матрицы.
     */
    private static void printMatrix(String name, int[][] matrix) {
        System.out.println("\n" + name + ":");
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            System.out.println("Empty or null matrix.");
            return;
        }
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("----------");
    }

    /**
     * Точка входа для демонстрации работы метода умножения матриц.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task60_MatrixMultiplication sol = new Task60_MatrixMultiplication();

        System.out.println("--- 2x2 Example ---");
        int[][] matrixA = {{1, 2}, {3, 4}};
        int[][] matrixB = {{2, 0}, {1, 2}};
        printMatrix("Matrix A (2x2)", matrixA);
        printMatrix("Matrix B (2x2)", matrixB);
        try {
            int[][] result1 = sol.multiplyMatrices(matrixA, matrixB);
            printMatrix("Result A * B (2x2)", result1); // Expected: [[4, 4], [10, 8]]
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }

        System.out.println("\n--- 3x3 Example ---");
        int[][] matrixC = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[][] matrixD = {{9, 8, 7}, {6, 5, 4}, {3, 2, 1}};
        printMatrix("Matrix C (3x3)", matrixC);
        printMatrix("Matrix D (3x3)", matrixD);
        try {
            int[][] result2 = sol.multiplyMatrices(matrixC, matrixD);
            printMatrix("Result C * D (3x3)", result2); // Expected: [[30, 24, 18], [84, 69, 54], [138, 114, 90]]
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }

        System.out.println("\n--- Non-Square Example (2x3 * 3x2) -> 2x2 ---");
        int[][] matrixG = {{1, 2, 3}, {4, 5, 6}}; // 2x3
        int[][] matrixH = {{7, 8}, {9, 10}, {11, 12}}; // 3x2
        printMatrix("Matrix G (2x3)", matrixG);
        printMatrix("Matrix H (3x2)", matrixH);
        try {
            int[][] result3 = sol.multiplyMatrices(matrixG, matrixH);
            printMatrix("Result G * H (2x2)", result3); // Expected: [[58, 64], [139, 154]]
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }

        System.out.println("\n--- Incompatible Example (2x2 * 3x2) ---");
        int[][] matrixE = {{1, 2}, {3, 4}}; // 2x2
        int[][] matrixF = {{1, 0}, {2, 1}, {3, 2}}; // 3x2
        printMatrix("Matrix E (2x2)", matrixE);
        printMatrix("Matrix F (3x2)", matrixF);
        try {
            sol.multiplyMatrices(matrixE, matrixF);
        } catch (IllegalArgumentException e) {
            System.err.println("Caught Expected Error: " + e.getMessage());
        }

        System.out.println("\n--- Null/Empty Example ---");
        try {
            sol.multiplyMatrices(null, matrixB);
        } catch (IllegalArgumentException e) {
            System.err.println("Caught Expected Error (A=null): " + e.getMessage());
        }
        try {
            sol.multiplyMatrices(matrixA, new int[0][0]);
        } catch (IllegalArgumentException e) {
            System.err.println("Caught Expected Error (B=empty): " + e.getMessage());
        }

    }
}
