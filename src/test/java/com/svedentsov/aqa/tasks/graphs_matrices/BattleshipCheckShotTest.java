package com.svedentsov.aqa.tasks.graphs_matrices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты для BattleshipCheckShot")
class BattleshipCheckShotTest {

    private BattleshipCheckShot checker;
    private int[][] standardField;

    @BeforeEach
    void setUp() {
        checker = new BattleshipCheckShot();
        // 0 - вода, 1 - корабль, 2 - уже было попадание, 3 - уже был промах
        standardField = new int[][]{
                {3, 1, 0, 0}, // Строка 0: уже промах, корабль, вода, вода
                {0, 2, 0, 0}, // Строка 1: вода, уже попадание, вода, вода
                {0, 0, 0, 1}, // Строка 2: вода, вода, вода, корабль
                {1, 0, 0, 1}  // Строка 3: корабль, вода, вода, корабль
        };
    }

    @Nested
    @DisplayName("Валидные выстрелы по стандартному полю")
    class ValidShotsOnStandardFieldTests {

        @Test
        @DisplayName("Попадание в корабль (1)")
        void checkShot_hitShip() {
            assertEquals("Hit", checker.checkShot(standardField, 0, 1)); // Корабль в (0,1)
            assertEquals("Hit", checker.checkShot(standardField, 2, 3)); // Корабль в (2,3)
            assertEquals("Hit", checker.checkShot(standardField, 3, 0)); // Корабль в (3,0)
        }

        @Test
        @DisplayName("Промах по воде (0)")
        void checkShot_missWater() {
            assertEquals("Miss", checker.checkShot(standardField, 1, 0)); // Вода в (1,0)
            assertEquals("Miss", checker.checkShot(standardField, 2, 1)); // Вода в (2,1)
        }

        @Test
        @DisplayName("Выстрел по уже обстрелянной клетке (не 0 и не 1)")
        void checkShot_alreadyShotCell() {
            assertEquals("Already Hit/Missed", checker.checkShot(standardField, 1, 1)); // Уже было попадание (2)
            assertEquals("Already Hit/Missed", checker.checkShot(standardField, 0, 0)); // Уже был промах (3)
            int[][] fieldWithOtherValue = {{5}};
            assertEquals("Already Hit/Missed", checker.checkShot(fieldWithOtherValue, 0, 0)); // Значение 5
        }
    }

    @Nested
    @DisplayName("Выстрелы с невалидными координатами")
    class InvalidCoordinatesTests {

        @ParameterizedTest(name = "Координаты ({0},{1}) -> Invalid Coordinates")
        @CsvSource({
                "-1, 0",  // строка < 0
                "0, -1",  // столбец < 0
                "4, 0",   // строка >= кол-ва строк (в standardField 4 строки, индекс 4 за границей)
                "0, 4"    // столбец >= кол-ва столбцов (в standardField 4 столбца, индекс 4 за границей)
        })
        void checkShot_outOfBoundsCoordinates_shouldReturnInvalidCoordinates(int row, int col) {
            assertEquals("Invalid Coordinates", checker.checkShot(standardField, row, col));
        }

        @Test
        @DisplayName("Координаты выходят за пределы строки (зубчатый массив)")
        void checkShot_jaggedArray_colOutOfBoundsForRow_shouldReturnInvalidCoordinates() {
            int[][] jaggedField = {{0, 1, 2}, {0, 1}}; // строка 1 короче
            // Попытка выстрела в (1,2) — столбец выходит за границу этой строки
            assertEquals("Invalid Coordinates", checker.checkShot(jaggedField, 1, 2));
        }
    }

    @Nested
    @DisplayName("Тесты с невалидным игровым полем")
    class InvalidFieldTests {

        @Test
        @DisplayName("Поле == null")
        void checkShot_nullField_shouldReturnInvalidField() {
            assertEquals("Invalid Field", checker.checkShot(null, 0, 0));
        }

        @Test
        @DisplayName("Поле без строк (field.length == 0)")
        void checkShot_emptyFieldZeroRows_shouldReturnInvalidField() {
            assertEquals("Invalid Field", checker.checkShot(new int[0][5], 0, 0));
            assertEquals("Invalid Field", checker.checkShot(new int[0][0], 0, 0));
        }

        @Test
        @DisplayName("Первая строка null (field[0] == null)")
        void checkShot_firstRowNull_shouldReturnInvalidField() {
            int[][] field = new int[1][]; // field[0] == null
            assertEquals("Invalid Field", checker.checkShot(field, 0, 0));
        }

        @Test
        @DisplayName("Первая строка пустая (field[0].length == 0)")
        void checkShot_firstRowEmpty_shouldReturnInvalidField() {
            int[][] field = {{}};
            assertEquals("Invalid Field", checker.checkShot(field, 0, 0));
            int[][] field2 = new int[1][0];
            assertEquals("Invalid Field", checker.checkShot(field2, 0, 0));
        }

        @Test
        @DisplayName("Null внутри массива, но не в первой строке")
        void checkShot_middleRowNull_shouldReturnInvalidField() {
            int[][] fieldWithNullRow = {{0, 1}, null, {0, 0}};
            assertEquals("Invalid Field", checker.checkShot(fieldWithNullRow, 1, 0)); // Попытка доступа к field[1][0]
        }
    }
}
