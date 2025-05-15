package com.svedentsov.aqa.tasks.graphs_matrices;

import java.util.Arrays;

/**
 * Решение задачи №83: Морской бой - проверка попадания.
 * Описание: Дана матрица поля, проверить корректность выстрела/попадания.
 * (Проверяет: работа с 2D массивами, условия)
 * Задание: Дана 2D матрица `int[][] field`, представляющая поле для игры
 * в морской бой (0 - пусто, 1 - часть корабля). Напишите метод
 * `String checkShot(int row, int col)` который возвращает "Miss" если `field[row][col] == 0`,
 * и "Hit" если `field[row][col] == 1`. (Можно усложнить до "Sunk", если это
 * был последний целый сегмент корабля, но это выходит за рамки базовой задачи).
 * Добавлена обработка некорректных координат и состояния поля.
 * Пример: `field = {{0, 1, 0}, {0, 1, 0}, {0, 0, 0}}`.
 * `checkShot(0, 1)` -> `"Hit"`. `checkShot(0, 0)` -> `"Miss"`.
 */
public class BattleshipCheckShot {

    /**
     * Проверяет результат выстрела по координатам (row, col) на игровом поле Морского боя.
     * Определяет, был ли выстрел мимо, попаданием, повторным или невалидным.
     *
     * @param field Игровое поле, представленное 2D массивом целых чисел.
     *              Ожидаемые значения: 0 - вода, 1 - корабль.
     *              Другие значения интерпретируются как уже обстрелянные клетки.
     * @param row   Координата строки выстрела (0-based).
     * @param col   Координата столбца выстрела (0-based).
     * @return Строка, описывающая результат: "Miss", "Hit", "Already Hit/Missed",
     * "Invalid Coordinates", "Invalid Field".
     */
    public String checkShot(int[][] field, int row, int col) {
        // 1. Проверка валидности поля
        if (field == null || field.length == 0 || field[0] == null || field[0].length == 0) {
            return "Invalid Field";
        }
        // Опционально: Проверка на прямоугольность
        // int colsExpected = field[0].length;
        // for(int[] r : field) if(r == null || r.length != colsExpected) return "Invalid Field";

        int rows = field.length;
        int cols = field[0].length;

        // 2. Проверка валидности координат
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return "Invalid Coordinates";
        }

        // 3. Определение результата по значению в ячейке
        int cellValue = field[row][col];
        switch (cellValue) {
            case 0:
                return "Miss";
            case 1:
                return "Hit";
            default:
                // Любое другое значение считаем повторным/невалидным выстрелом
                return "Already Hit/Missed";
        }
    }

    /**
     * Точка входа для демонстрации работы метода проверки выстрела.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        BattleshipCheckShot sol = new BattleshipCheckShot();
        // 0-вода, 1-корабль, 2-попадание(уже), 3-промах(уже)
        int[][] gameField = {
                {3, 1, 0, 0},
                {0, 2, 0, 0},
                {0, 0, 0, 1},
                {1, 0, 0, 1}
        };

        System.out.println("--- Battleship Shot Check ---");
        printField("Game Field", gameField);

        // Тесты
        runCheckShotTest(sol, gameField, 0, 1, "Shot at (0, 1) [Hit Ship]");
        runCheckShotTest(sol, gameField, 1, 1, "Shot at (1, 1) [Already Hit]");
        runCheckShotTest(sol, gameField, 0, 0, "Shot at (0, 0) [Already Missed]");
        runCheckShotTest(sol, gameField, 2, 3, "Shot at (2, 3) [Hit Ship]");
        runCheckShotTest(sol, gameField, 3, 0, "Shot at (3, 0) [Hit Ship]");
        runCheckShotTest(sol, gameField, 2, 1, "Shot at (2, 1) [Miss Water]");

        // Крайние случаи
        System.out.println("\n--- Edge Cases ---");
        runCheckShotTest(sol, gameField, -1, 0, "Shot at (-1, 0) [Out]");
        runCheckShotTest(sol, gameField, 4, 0, "Shot at (4, 0) [Out]");
        runCheckShotTest(sol, gameField, 0, 4, "Shot at (0, 4) [Out]");

        // Невалидные поля
        runCheckShotTest(sol, null, 0, 0, "Shot at null field");
        runCheckShotTest(sol, new int[0][0], 0, 0, "Shot at empty field");
        runCheckShotTest(sol, new int[][]{{1}, null}, 1, 0, "Shot at field with null row");

        // Поле с нераспознанным значением
        int[][] invalidValueField = {{5}};
        runCheckShotTest(sol, invalidValueField, 0, 0, "Shot at invalid cell value");
    }

    /**
     * Вспомогательный метод для печати поля.
     */
    private static void printField(String title, int[][] field) {
        System.out.println("\n" + title + ":");
        if (field == null) {
            System.out.println("null");
            return;
        }
        if (field.length == 0) {
            System.out.println("[]");
            return;
        }

        for (int[] rowArr : field) {
            if (rowArr == null) {
                System.out.println("  null row");
                continue;
            }
            System.out.println("  " + Arrays.toString(rowArr));
        }
        System.out.println("--------------------");
    }

    /**
     * Вспомогательный метод для тестирования checkShot.
     *
     * @param sol         Экземпляр решателя.
     * @param field       Игровое поле.
     * @param row         Строка выстрела.
     * @param col         Столбец выстрела.
     * @param description Описание теста.
     */
    private static void runCheckShotTest(BattleshipCheckShot sol, int[][] field, int row, int col, String description) {
        System.out.print(description + ": ");
        try {
            String result = sol.checkShot(field, row, col);
            System.out.println("-> \"" + result + "\"");
        } catch (Exception e) {
            System.err.println("-> Error: " + e.getMessage());
        }
    }
}
