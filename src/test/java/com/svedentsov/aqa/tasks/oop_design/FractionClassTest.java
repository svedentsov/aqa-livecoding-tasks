package com.svedentsov.aqa.tasks.oop_design;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.svedentsov.aqa.tasks.oop_design.FractionClass.Fraction;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для класса Fraction")
class FractionClassTest {

    @Nested
    @DisplayName("Создание и упрощение дробей")
    class CreationAndSimplification {

        @Test
        @DisplayName("Упрощает дробь при создании (2/4 -> 1/2)")
        void shouldSimplifyOnCreation() {
            assertEquals(new Fraction(1, 2), new Fraction(2, 4));
        }

        @Test
        @DisplayName("Нормализует знак (знаменатель всегда положительный)")
        void shouldNormalizeSign() {
            Fraction f1 = new Fraction(1, -2); // 1/-2 -> -1/2
            Fraction f2 = new Fraction(-1, -2); // -1/-2 -> 1/2

            assertAll(
                    () -> assertEquals(-1, f1.getNumerator()),
                    () -> assertEquals(2, f1.getDenominator()),
                    () -> assertEquals(1, f2.getNumerator()),
                    () -> assertEquals(2, f2.getDenominator())
            );
        }

        @Test
        @DisplayName("Создает дробь из целого числа (7 -> 7/1)")
        void shouldCreateFromWholeNumber() {
            Fraction f = new Fraction(7);
            assertEquals(7, f.getNumerator());
            assertEquals(1, f.getDenominator());
        }

        @Test
        @DisplayName("Корректно обрабатывает ноль (0/5 -> 0/1)")
        void shouldHandleZeroNumerator() {
            Fraction f = new Fraction(0, 5);
            assertEquals(0, f.getNumerator());
            assertEquals(1, f.getDenominator());
            assertEquals(Fraction.ZERO, f);
        }

        @Test
        @DisplayName("Формат toString для целых и дробных чисел")
        void shouldFormatToStringCorrectly() {
            assertEquals("5/6", new Fraction(5, 6).toString());
            assertEquals("3", new Fraction(6, 2).toString());
            assertEquals("-3", new Fraction(-3, 1).toString());
        }
    }

    @Nested
    @DisplayName("Арифметические операции")
    class Arithmetic {

        private final Fraction f1_2 = new Fraction(1, 2);
        private final Fraction f1_3 = new Fraction(1, 3);

        @Test
        @DisplayName("Сложение: 1/2 + 1/3 = 5/6")
        void shouldAddFractions() {
            Fraction result = f1_2.add(f1_3);
            assertEquals(new Fraction(5, 6), result);
        }

        @Test
        @DisplayName("Вычитание: 1/2 - 1/3 = 1/6")
        void shouldSubtractFractions() {
            Fraction result = f1_2.subtract(f1_3);
            assertEquals(new Fraction(1, 6), result);
        }

        @Test
        @DisplayName("Умножение: 2/3 * 3/4 = 6/12 -> 1/2")
        void shouldMultiplyFractions() {
            Fraction f1 = new Fraction(2, 3);
            Fraction f2 = new Fraction(3, 4);
            assertEquals(new Fraction(1, 2), f1.multiply(f2));
        }

        @Test
        @DisplayName("Деление: 1/2 / 1/4 = 4/2 -> 2")
        void shouldDivideFractions() {
            Fraction f1 = new Fraction(1, 2);
            Fraction f2 = new Fraction(1, 4);
            assertEquals(new Fraction(2), f1.divide(f2));
        }
    }

    @Nested
    @DisplayName("Сравнение и равенство")
    class Comparison {
        @Test
        @DisplayName("Равенство: 1/2 равно 2/4")
        void shouldBeEqual() {
            Fraction f1 = new Fraction(1, 2);
            Fraction f2 = new Fraction(2, 4);
            assertEquals(f1, f2);
            assertEquals(f1.hashCode(), f2.hashCode());
        }

        @Test
        @DisplayName("Неравенство: 1/2 не равно 1/3")
        void shouldNotBeEqual() {
            assertNotEquals(new Fraction(1, 2), new Fraction(1, 3));
        }

        @ParameterizedTest(name = "{0} compareTo {1} should be {2}")
        @CsvSource({
                "1,2, 1,3, 1",   // 1/2 > 1/3
                "1,3, 1,2, -1",  // 1/3 < 1/2
                "-1,2, 1,2, -1", // -1/2 < 1/2
                "1,2, 2,4, 0"    // 1/2 == 2/4
        })
        void testCompareTo(int n1, int d1, int n2, int d2, int expectedSign) {
            Fraction f1 = new Fraction(n1, d1);
            Fraction f2 = new Fraction(n2, d2);
            int comparison = f1.compareTo(f2);
            // Сравниваем знаки результата
            assertEquals(Integer.signum(expectedSign), Integer.signum(comparison));
        }
    }

    @Nested
    @DisplayName("Обработка ошибок и граничные случаи")
    class ErrorHandling {
        @Test
        @DisplayName("Выбрасывает исключение при создании с нулевым знаменателем")
        void shouldThrowOnZeroDenominator() {
            assertThrows(IllegalArgumentException.class, () -> new Fraction(1, 0));
        }

        @Test
        @DisplayName("Выбрасывает исключение при делении на нулевую дробь")
        void shouldThrowOnDivideByZeroFraction() {
            Fraction f = new Fraction(1, 2);
            assertThrows(ArithmeticException.class, () -> f.divide(Fraction.ZERO));
        }

        @Test
        @DisplayName("Выбрасывает исключение при переполнении в ходе сложения")
        void shouldThrowOnAdditionOverflow() {
            Fraction max = new Fraction(Integer.MAX_VALUE);
            assertThrows(ArithmeticException.class, () -> max.add(Fraction.ONE));
        }

        @Test
        @DisplayName("Выбрасывает исключение при переполнении в ходе умножения")
        void shouldThrowOnMultiplyOverflow() {
            Fraction f = new Fraction(Integer.MAX_VALUE / 2 + 1);
            assertThrows(ArithmeticException.class, () -> f.multiply(new Fraction(2)));
        }

        @Test
        @DisplayName("Выбрасывает исключение при передаче null в арифметические методы")
        void shouldThrowOnNullArgument() {
            Fraction f = new Fraction(1, 1);
            assertThrows(NullPointerException.class, () -> f.add(null));
            assertThrows(NullPointerException.class, () -> f.subtract(null));
            assertThrows(NullPointerException.class, () -> f.multiply(null));
            assertThrows(NullPointerException.class, () -> f.divide(null));
        }
    }
}
