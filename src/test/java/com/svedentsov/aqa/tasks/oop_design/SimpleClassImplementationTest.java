package com.svedentsov.aqa.tasks.oop_design;

import com.svedentsov.aqa.tasks.oop_design.SimpleClassImplementation.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Year;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для класса Car")
class SimpleClassImplementationTest {

    private final String VALID_MAKE = "Toyota";
    private final String VALID_MODEL = "Camry";
    private final int VALID_YEAR = 2021;
    // Make MIN_YEAR static final if it wasn't already, to be accessible in static method easily
    private static final int MIN_YEAR = 1886; // Должно совпадать с константой в Car

    private Car validCar;

    @BeforeEach
    void setUp() {
        validCar = new Car(VALID_MAKE, VALID_MODEL, VALID_YEAR);
    }

    // --- Тесты Конструктора ---
    @Nested
    @DisplayName("Тесты конструктора")
    class ConstructorTests {

        // ... (other constructor tests remain the same) ...

        // --- Static method to provide invalid years ---
        static Stream<Integer> provideInvalidYears() {
            int currentYear = Year.now().getValue();
            return Stream.of(
                    MIN_YEAR - 1, // e.g., 1885
                    1800,
                    0,
                    -10,
                    currentYear + 2 // Year in the future
            );
        }

        @ParameterizedTest(name = "Невалидный год: {0} -> IllegalArgumentException")
        // Use @MethodSource instead of @ValueSource
        @MethodSource("provideInvalidYears")
        @DisplayName("Исключение IllegalArgumentException для невалидного года")
        void shouldThrowIllegalArgumentExceptionForInvalidYear(int invalidYear) {
            int currentYear = Year.now().getValue(); // Recalculate for assertion message if needed
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                new Car(VALID_MAKE, VALID_MODEL, invalidYear);
            });
            assertTrue(exception.getMessage().contains("Invalid year provided"), "Сообщение должно указывать на невалидный год");
            assertTrue(exception.getMessage().contains(String.valueOf(invalidYear)), "Сообщение должно содержать невалидный год");
            assertTrue(exception.getMessage().contains(String.valueOf(MIN_YEAR)), "Сообщение должно содержать MIN_YEAR");
            assertTrue(exception.getMessage().contains(String.valueOf(currentYear + 1)), "Сообщение должно содержать максимальный допустимый год");
        }

        @Test
        @DisplayName("Успешное создание с граничными годами")
        void shouldCreateCarWithBoundaryYears() {
            int currentYear = Year.now().getValue(); // Need current year for boundary check
            assertDoesNotThrow(() -> new Car(VALID_MAKE, VALID_MODEL, MIN_YEAR));
            assertDoesNotThrow(() -> new Car(VALID_MAKE, VALID_MODEL, currentYear));
            assertDoesNotThrow(() -> new Car(VALID_MAKE, VALID_MODEL, currentYear + 1));
        }
    }
}
