package com.svedentsov.aqa.tasks.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты для FindStringPermutations")
class FindStringPermutationsTest {

    private FindStringPermutations permuter;

    @BeforeEach
    void setUp() {
        permuter = new FindStringPermutations();
    }

    // --- Источник данных для тестов ---
    // Формат: входная строка, ОЖИДАЕМЫЙ ОТСОРТИРОВАННЫЙ список уникальных перестановок
    static Stream<Arguments> provideStringsAndPermutations() {
        return Stream.of(
                Arguments.of("abc", Arrays.asList("abc", "acb", "bac", "bca", "cab", "cba")),
                Arguments.of("aab", Arrays.asList("aab", "aba", "baa")),
                Arguments.of("a", Collections.singletonList("a")),
                Arguments.of("aa", Collections.singletonList("aa")), // Дубликаты в строке
                Arguments.of("ABA", Arrays.asList("AAB", "ABA", "BAA")), // С учетом регистра, но ожидаемый отсортирован
                Arguments.of("112", Arrays.asList("112", "121", "211")),
                Arguments.of("AB", Arrays.asList("AB", "BA"))
        );
    }

    // --- Тесты для findPermutationsSet ---
    @Nested
    @DisplayName("Метод findPermutationsSet (с HashSet)")
    class SetPermutationTests {

        @ParameterizedTest(name = "Строка: \"{0}\"")
        @MethodSource("com.svedentsov.aqa.tasks.strings.FindStringPermutationsTest#provideStringsAndPermutations")
        @DisplayName("Должен находить все уникальные перестановки")
        void shouldFindUniquePermutationsUsingSet(String input, List<String> expectedSortedPermutations) {
            List<String> actualPermutations = permuter.findPermutationsSet(input);
            // Сортируем актуальный результат, так как Set не гарантирует порядок
            Collections.sort(actualPermutations);
            assertEquals(expectedSortedPermutations, actualPermutations,
                    "Списки перестановок (Set метод, после сортировки) должны совпадать");
        }

        @ParameterizedTest(name = "Вход: {0} -> Ожидание: пустой список")
        @NullAndEmptySource // Проверяет null и ""
        @DisplayName("Должен возвращать пустой список для null или пустой строки")
        void shouldReturnEmptyListForNullOrEmpty(String input) {
            List<String> actualPermutations = permuter.findPermutationsSet(input);
            assertTrue(actualPermutations.isEmpty(), "Для null/пустой строки ожидается пустой список");
        }
    }

    // --- Тесты для findPermutationsOptimized ---
    @Nested
    @DisplayName("Метод findPermutationsOptimized (без Set, сортированный)")
    class OptimizedPermutationTests {

        @ParameterizedTest(name = "Строка: \"{0}\"")
        @MethodSource("com.svedentsov.aqa.tasks.strings.FindStringPermutationsTest#provideStringsAndPermutations")
        @DisplayName("Должен находить все уникальные перестановки (в лексикографическом порядке)")
        void shouldFindUniquePermutationsOptimized(String input, List<String> expectedSortedPermutations) {
            List<String> actualPermutations = permuter.findPermutationsOptimized(input);
            // Оптимизированный метод должен возвращать уже отсортированный список
            assertEquals(expectedSortedPermutations, actualPermutations,
                    "Списки перестановок (оптимизированный метод) должны совпадать");
        }

        @ParameterizedTest(name = "Вход: {0} -> Ожидание: пустой список")
        @NullAndEmptySource // Проверяет null и ""
        @DisplayName("Должен возвращать пустой список для null или пустой строки")
        void shouldReturnEmptyListForNullOrEmpty(String input) {
            List<String> actualPermutations = permuter.findPermutationsOptimized(input);
            assertTrue(actualPermutations.isEmpty(), "Для null/пустой строки ожидается пустой список");
        }
    }
}
