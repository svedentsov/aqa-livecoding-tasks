package com.svedentsov.aqa.tasks.algorithms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты для ArmstrongNumberCheck")
class ArmstrongNumberCheckTest {

    private ArmstrongNumberCheck checker;

    @BeforeEach
    void setUp() {
        checker = new ArmstrongNumberCheck();
    }

    // --- Тесты для чисел Армстронга ---
    @ParameterizedTest(name = "Число {0} ЯВЛЯЕТСЯ числом Армстронга")
    @ValueSource(ints = {
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, // Однозначные
            153, 370, 371, 407,           // Трехзначные
            1634, 8208, 9474              // Четырехзначные
            // 54748, 92727, 93084 - пятизначные
            // 548834 - шестизначный
            // И так далее... Добавим несколько известных
            , 54748, 92727, 93084, 548834
    })
    @DisplayName("Должен возвращать true для чисел Армстронга")
    void shouldReturnTrueForArmstrongNumbers(int number) {
        assertTrue(checker.isArmstrongNumber(number), number + " должно быть числом Армстронга");
    }

    // --- Тесты для чисел, не являющихся числами Армстронга ---
    @ParameterizedTest(name = "Число {0} НЕ является числом Армстронга")
    @ValueSource(ints = {
            10, 11, 99, 100, 152, 154, 369, 372, 408, // Не Армстронги < 1000
            1633, 1635, 9475, // Не Армстронги > 1000
            Integer.MAX_VALUE, // Граничное значение
            -1, -10, -153 // Отрицательные
    })
    @DisplayName("Должен возвращать false для чисел, не являющихся числами Армстронга")
    void shouldReturnFalseForNonArmstrongNumbers(int number) {
        assertFalse(checker.isArmstrongNumber(number), number + " не должно быть числом Армстронга");
    }
}
