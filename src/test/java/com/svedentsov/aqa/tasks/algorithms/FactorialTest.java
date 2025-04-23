package com.svedentsov.aqa.tasks.algorithms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для класса Factorial")
class FactorialTest {

    private Factorial factorialCalculator;

    @BeforeEach
    void setUp() {
        factorialCalculator = new Factorial();
    }

    @ParameterizedTest(name = "Факториал({0}) должен быть {1}")
    @CsvSource({
            "0, 1",
            "1, 1",
            "2, 2",
            "3, 6",
            "5, 120",
            "10, 3628800",
            "15, 1307674368000",
            "20, 2432902008176640000" // Максимальное значение для long
    })
    @DisplayName("Проверка факториала для валидных неотрицательных чисел (до 20)")
    void shouldCalculateFactorialForValidNonNegativeInput(int n, long expectedFactorial) {
        assertEquals(expectedFactorial, factorialCalculator.factorial(n));
    }

    @ParameterizedTest(name = "Факториал({0}) должен выбросить IllegalArgumentException")
    @ValueSource(ints = {-1, -5, -10}) // Несколько отрицательных значений
    @DisplayName("Проверка исключения IllegalArgumentException для отрицательных чисел")
    void shouldThrowIllegalArgumentExceptionForNegativeInput(int negativeNumber) {
        // Проверяем, что исключение выбрасывается
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            factorialCalculator.factorial(negativeNumber);
        });

        // Проверяем сообщение в исключении
        assertTrue(exception.getMessage().contains("Factorial is not defined for negative numbers"),
                "Сообщение об ошибке должно указывать на недопустимость отрицательных чисел");
        assertTrue(exception.getMessage().contains(String.valueOf(negativeNumber)),
                "Сообщение об ошибке должно содержать переданное отрицательное число");
    }

    @ParameterizedTest(name = "Факториал({0}) должен выбросить ArithmeticException (переполнение long)")
    @ValueSource(ints = {21, 25}) // Числа, факториал которых точно не помещается в long
    @DisplayName("Проверка исключения ArithmeticException при переполнении long")
    void shouldThrowArithmeticExceptionForOverflow(int numberCausingOverflow) {
        // Проверяем, что исключение выбрасывается
        ArithmeticException exception = assertThrows(ArithmeticException.class, () -> {
            factorialCalculator.factorial(numberCausingOverflow);
        });

        // Проверяем сообщение в исключении
        assertTrue(exception.getMessage().contains("overflows long type"),
                "Сообщение об ошибке должно указывать на переполнение long");
        assertTrue(exception.getMessage().contains(String.valueOf(numberCausingOverflow)),
                "Сообщение об ошибке должно содержать число, вызвавшее переполнение");

    }

    @Test
    @DisplayName("Проверка, что факториал(20) не вызывает исключение переполнения")
    void factorialOf20ShouldNotThrowOverflowException() {
        assertDoesNotThrow(() -> {
            factorialCalculator.factorial(20);
        }, "Вычисление факториала 20 не должно приводить к ArithmeticException");
    }
}
