package com.svedentsov.aqa.tasks.arrays_lists;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для RotateArray")
class RotateArrayTest {

    private RotateArray rotator;

    @BeforeEach
    void setUp() {
        rotator = new RotateArray();
    }

    // --- Источник данных для тестов ---
    // Формат: исходный массив, k, ожидаемый массив после вращения
    // null массив обрабатывается отдельно
    static Stream<Arguments> provideArraysForRotation() {
        return Stream.of(
                Arguments.of(new int[]{1, 2, 3, 4, 5}, 2, new int[]{4, 5, 1, 2, 3}),
                Arguments.of(new int[]{1, 2, 3, 4, 5, 6, 7}, 3, new int[]{5, 6, 7, 1, 2, 3, 4}),
                Arguments.of(new int[]{1, 2, 3}, 4, new int[]{3, 1, 2}), // k > n (eff_k=1)
                Arguments.of(new int[]{1, 2, 3, 4}, -1, new int[]{2, 3, 4, 1}), // k < 0 (eff_k=3)
                Arguments.of(new int[]{1, 2, 3, 4}, -5, new int[]{2, 3, 4, 1}), // k < 0, |k| > n (eff_k=3)
                Arguments.of(new int[]{1, 2}, 0, new int[]{1, 2}), // k = 0
                Arguments.of(new int[]{1, 2}, 2, new int[]{1, 2}), // k = n (eff_k=0)
                Arguments.of(new int[]{1}, 5, new int[]{1}), // Один элемент
                Arguments.of(new int[]{}, 3, new int[]{}) // Пустой массив
        );
    }

    // --- Тесты для rotateArrayUsingExtraSpace ---
    @Nested
    @DisplayName("Метод rotateArrayUsingExtraSpace (с доп. памятью)")
    class ExtraSpaceRotationTests {

        @ParameterizedTest(name = "arr={0}, k={1} -> Ожидание: {2}")
        @MethodSource("com.svedentsov.aqa.tasks.arrays_lists.RotateArrayTest#provideArraysForRotation")
        void shouldRotateArrayCorrectlyUsingExtraSpace(int[] originalArr, int k, int[] expectedArr) {
            int[] actualRotatedArr = rotator.rotateArrayUsingExtraSpace(originalArr, k);
            assertArrayEquals(expectedArr, actualRotatedArr);
        }

        @Test
        @DisplayName("Должен возвращать null для null входа")
        void shouldReturnNullForNullInput() {
            assertNull(rotator.rotateArrayUsingExtraSpace(null, 3));
        }

        @Test
        @DisplayName("Должен возвращать копию массива, если вращение не требуется (k=0 или k=n)")
        void shouldReturnCopyWhenNoRotationNeeded() {
            int[] original = {1, 2, 3};
            int[] rotatedK0 = rotator.rotateArrayUsingExtraSpace(original, 0);
            int[] rotatedKN = rotator.rotateArrayUsingExtraSpace(original, original.length);

            assertArrayEquals(original, rotatedK0, "k=0: Массив должен остаться тем же");
            assertNotSame(original, rotatedK0, "k=0: Должна возвращаться копия, а не тот же объект");

            assertArrayEquals(original, rotatedKN, "k=n: Массив должен остаться тем же");
            assertNotSame(original, rotatedKN, "k=n: Должна возвращаться копия, а не тот же объект");

            int[] empty = {};
            int[] rotatedEmpty = rotator.rotateArrayUsingExtraSpace(empty, 5);
            assertArrayEquals(empty, rotatedEmpty, "Пустой массив должен остаться пустым");
            assertNotSame(empty, rotatedEmpty, "Должна возвращаться копия пустого массива");
        }
    }

    // --- Тесты для rotateArrayInPlace ---
    @Nested
    @DisplayName("Метод rotateArrayInPlace (на месте)")
    class InPlaceRotationTests {

        @ParameterizedTest(name = "arr={0}, k={1} -> Ожидание: {2}")
        @MethodSource("com.svedentsov.aqa.tasks.arrays_lists.RotateArrayTest#provideArraysForRotation")
        void shouldRotateArrayCorrectlyInPlace(int[] originalArr, int k, int[] expectedArr) {
            // Создаем копию, так как метод модифицирует массив
            int[] arrToRotate = Arrays.copyOf(originalArr, originalArr.length);
            rotator.rotateArrayInPlace(arrToRotate, k);
            assertArrayEquals(expectedArr, arrToRotate, "Массив должен быть повернут на месте");
        }

        @Test
        @DisplayName("Должен выбросить NullPointerException для null входа")
        void shouldThrowNullPointerExceptionForNullInput() {
            assertThrows(NullPointerException.class, () -> {
                rotator.rotateArrayInPlace(null, 3);
            });
        }

        @Test
        @DisplayName("Не должен изменять пустой массив")
        void shouldNotChangeEmptyArray() {
            int[] emptyArr = {};
            // Копия не нужна, т.к. метод просто вернется
            assertDoesNotThrow(() -> rotator.rotateArrayInPlace(emptyArr, 3));
            assertArrayEquals(new int[]{}, emptyArr); // Проверяем, что он все еще пустой
        }

        @Test
        @DisplayName("Не должен изменять массив из одного элемента")
        void shouldNotChangeSingleElementArray() {
            int[] singleElementArr = {5};
            // Копия не нужна, т.к. метод просто вернется
            assertDoesNotThrow(() -> rotator.rotateArrayInPlace(singleElementArr, 3));
            assertArrayEquals(new int[]{5}, singleElementArr); // Проверяем, что он не изменился
        }
    }
}
