package com.svedentsov.aqa.tasks.graphs_matrices;

import java.util.Arrays;

/**
 * Решение задачи №83: Морской бой - проверка попадания.
 */
public class Task83_BattleshipCheckShot {

    /**
     * Проверяет результат выстрела по координатам (row, col) на игровом поле Морского боя.
     * Определяет, был ли выстрел мимо, попаданием, повторным или невалидным.
     *
     * @param field Игровое поле, представленное 2D массивом целых чисел.
     *              Предполагаемые значения для проверки:
     *              0 - пустая клетка (вода).
     *              1 - неповрежденная часть корабля.
     *              (Могут быть и другие значения, например, 2 - подбитая часть, 3 - промах,
     *              которые будут обработаны как "Already Hit/Missed").
     * @param row   Координата строки выстрела (индексация с 0).
     * @param col   Координата столбца выстрела (индексация с 0).
     * @return Строка, описывающая результат выстрела:
     * <ul>
     * <li>"Miss" - если попали в пустую клетку (0).</li>
     * <li>"Hit" - если попали в целую часть корабля (1).</li>
     * <li>"Already Hit/Missed" - если попали в уже обстрелянную клетку (не 0 и не 1, например 2 или 3).</li>
     * <li>"Invalid Coordinates" - если координаты (row, col) выходят за пределы поля.</li>
     * <li>"Invalid Field" - если само поле {@code field} невалидно (null, пустое, не прямоугольное).</li>
     * <li>"Invalid Cell Value" - если значение в ячейке не соответствует ожидаемым (0, 1, 2, 3...).
     * (В текущей реализации попадает под "Already Hit/Missed", если не 0 или 1).</li>
     * </ul>
     * Примечание: Этот метод только проверяет результат, он не обновляет состояние поля
     * и не определяет, потоплен ли корабль ("Sunk").
     */
    public String checkShot(int[][] field, int row, int col) {
        // 1. Проверка поля на null и базовую валидность
        if (field == null || field.length == 0 || field[0] == null || field[0].length == 0) {
            return "Invalid Field";
        }
        // Можно добавить проверку на прямоугольность

        int rows = field.length;
        int cols = field[0].length;

        // 2. Проверка координат на выход за пределы поля
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return "Invalid Coordinates";
        }

        // 3. Определение результата выстрела по значению ячейки
        int cellValue = field[row][col];

        switch (cellValue) {
            case 0: // Пусто (Вода)
                return "Miss";
            case 1: // Целая часть корабля
                return "Hit";
            default:
                // Если значение не 0 и не 1, считаем, что сюда уже стреляли
                // (или значение в ячейке невалидно, но обрабатываем как повторный выстрел)
                return "Already Hit/Missed";
            // Можно добавить более строгую проверку:
            // if (cellValue == 2 || cellValue == 3) return "Already Hit/Missed";
            // else return "Invalid Cell Value";
        }
    }

    /**
     * Вспомогательный метод для печати поля.
     */
    private static void printField(String title, int[][] field) {
        System.out.println(title + ":");
        if (field == null || field.length == 0 || field[0] == null || field[0].length == 0) {
            System.out.println("Empty or null field.");
            return;
        }
        for (int[] rowArr : field) {
            System.out.println(Arrays.toString(rowArr));
        }
        System.out.println("--------------------");
    }

    /**
     * Точка входа для демонстрации работы метода проверки выстрела.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task83_BattleshipCheckShot sol = new Task83_BattleshipCheckShot();
        int[][] gameField = { // 0-вода, 1-корабль, 2-попадание, 3-промах
                {3, 1, 0, 0},
                {0, 2, 0, 0},
                {0, 0, 0, 1},
                {1, 0, 0, 1}
        };

        printField("Initial Game Field", gameField);

        System.out.println("Shot at (0, 1) [Hit Ship]: " + sol.checkShot(gameField, 0, 1));
        System.out.println("Shot at (1, 1) [Already Hit]: " + sol.checkShot(gameField, 1, 1));
        System.out.println("Shot at (0, 0) [Already Missed]: " + sol.checkShot(gameField, 0, 0));
        System.out.println("Shot at (2, 3) [Hit Ship]: " + sol.checkShot(gameField, 2, 3));
        System.out.println("Shot at (3, 0) [Hit Ship]: " + sol.checkShot(gameField, 3, 0));
        System.out.println("Shot at (2, 1) [Miss Water]: " + sol.checkShot(gameField, 2, 1));

        System.out.println("\n--- Edge Cases ---");
        System.out.println("Shot at (-1, 0) [Out]: " + sol.checkShot(gameField, -1, 0));
        System.out.println("Shot at (4, 0) [Out]: " + sol.checkShot(gameField, 4, 0));
        System.out.println("Shot at (0, 4) [Out]: " + sol.checkShot(gameField, 0, 4));

        // Невалидное поле
        System.out.println("Shot at null field: " + sol.checkShot(null, 0, 0));
        System.out.println("Shot at empty field: " + sol.checkShot(new int[0][0], 0, 0));

        // Поле с невалидным значением
        int[][] invalidValueField = {{5}};
        printField("\nInvalid Value Field", invalidValueField);
        System.out.println("Shot at (0, 0) [Invalid Value]: " + sol.checkShot(invalidValueField, 0, 0)); // Попадет в default -> Already Hit/Missed
    }
}
