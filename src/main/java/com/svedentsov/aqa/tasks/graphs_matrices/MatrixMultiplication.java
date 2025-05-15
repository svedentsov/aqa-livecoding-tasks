package com.svedentsov.aqa.tasks.graphs_matrices;

/**
 * Решение задачи №60: Перемножение матриц.
 * Описание: (Проверяет: вложенные циклы, работа с 2D массивами)
 * Задание: Напишите метод `int[][] multiplyMatrices(int[][] matrixA, int[][] matrixB)`,
 * который перемножает две матрицы (не обязательно квадратные) и возвращает
 * результирующую матрицу. Проверьте совместимость матриц для умножения.
 * Пример: `matrixA = {{1, 2}, {3, 4}}, matrixB = {{2, 0}, {1, 2}}`.
 * `multiplyMatrices(matrixA, matrixB)` -> `{{4, 4}, {10, 8}}`.
 */
public class MatrixMultiplication {

    /**
     * Перемножает две матрицы matrixA и matrixB (A * B).
     *
     * @param matrixA Первая матрица (размер rowsA x colsA).
     * @param matrixB Вторая матрица (размер rowsB x colsB).
     * @return Результирующая матрица C размером rowsA x colsB.
     * @throws IllegalArgumentException если матрицы null, пустые, не прямоугольные
     *                                  или их размеры несовместимы для умножения (colsA != rowsB).
     * @throws ArithmeticException      если происходит переполнение int при вычислении элемента результирующей матрицы.
     */
    public int[][] multiplyMatrices(int[][] matrixA, int[][] matrixB) {
        validateMatrixNotNullOrEmpty(matrixA, "Matrix A");
        validateMatrixNotNullOrEmpty(matrixB, "Matrix B");

        int rowsA = matrixA.length;
        int colsA = matrixA[0].length;
        int rowsB = matrixB.length;
        int colsB = matrixB[0].length;

        validateMatrixIsRectangular(matrixA, colsA, "Matrix A");
        validateMatrixIsRectangular(matrixB, colsB, "Matrix B");

        if (colsA != rowsB) {
            throw new IllegalArgumentException(
                    "Matrices dimensions are incompatible for multiplication: " +
                            "colsA (" + colsA + ") != rowsB (" + rowsB + ").");
        }

        int[][] resultMatrix = new int[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                long sum = 0L; // Используем long для промежуточной суммы
                for (int k = 0; k < colsA; k++) { // colsA == rowsB
                    // Умножение двух int может переполнить long, если оба близки к sqrt(Long.MAX_VALUE)
                    // но здесь мы умножаем int*int, результат может быть > Integer.MAX_VALUE, но должен помещаться в long.
                    // Затем суммируем. Проблема может быть, если sum сама по себе переполняет long.
                    // Однако, так как элементы int, и их количество ограничено int, sum вряд ли переполнит long.
                    // Основная проверка - на то, что итоговая sum помещается в int.
                    sum += (long) matrixA[i][k] * matrixB[k][j];
                }
                // Проверка на переполнение результата перед присвоением в int ячейку
                if (sum > Integer.MAX_VALUE || sum < Integer.MIN_VALUE) {
                    throw new ArithmeticException("Integer overflow during matrix multiplication for result[" + i + "][" + j + "], sum: " + sum);
                }
                resultMatrix[i][j] = (int) sum;
            }
        }
        return resultMatrix;
    }

    /**
     * Проверяет, что матрица не null, не пуста и первая строка не null/пуста.
     */
    private void validateMatrixNotNullOrEmpty(int[][] matrix, String matrixName) {
        if (matrix == null) {
            throw new IllegalArgumentException(matrixName + " cannot be null.");
        }
        if (matrix.length == 0) {
            throw new IllegalArgumentException(matrixName + " cannot be empty (0 rows).");
        }
        if (matrix[0] == null) { // Проверка на случай new int[x][], где строки не инициализированы
            throw new IllegalArgumentException("First row of " + matrixName + " cannot be null.");
        }
        if (matrix[0].length == 0) {
            throw new IllegalArgumentException(matrixName + " cannot have 0 columns (first row is empty).");
        }
    }

    /**
     * Проверяет, что все строки матрицы имеют одинаковую длину (т.е. матрица прямоугольная).
     */
    private void validateMatrixIsRectangular(int[][] matrix, int expectedCols, String matrixName) {
        for (int i = 0; i < matrix.length; i++) { // Начинаем с i=0 для проверки matrix[0] тоже (хотя его длина уже взята)
            if (matrix[i] == null) {
                throw new IllegalArgumentException(matrixName + " contains a null row at index " + i);
            }
            if (matrix[i].length != expectedCols) {
                throw new IllegalArgumentException(matrixName + " is not rectangular. Row " + i + " has "
                        + matrix[i].length + " columns, expected " + expectedCols + ".");
            }
        }
    }
}
