package com.svedentsov.aqa.tasks.dp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для CombinationSum")
class CombinationSumTest {

    private CombinationSum solver;

    @BeforeEach
    void setUp() {
        solver = new CombinationSum();
    }

    // Хелпер для канонизации списка списков:
    // 1. Сортирует каждый внутренний список.
    // 2. Сортирует внешний список (по первому элементу, потом по второму и т.д., потом по длине).
    // Это нужно для надежного сравнения assertEquals, так как порядок комбинаций не гарантирован.
    private Set<List<Integer>> toCanonicalSetOfLists(List<List<Integer>> listOfLists) {
        if (listOfLists == null) {
            return Collections.emptySet();
        }
        return listOfLists.stream()
                .map(list -> {
                    List<Integer> sortedList = new ArrayList<>(list);
                    Collections.sort(sortedList);
                    return sortedList;
                })
                .collect(Collectors.toSet());
    }


    static Stream<Arguments> provideCombinationSumCases() {
        return Stream.of(
                Arguments.of(new int[]{2, 3, 6, 7}, 7,
                        Set.of(List.of(2, 2, 3), List.of(7))),
                Arguments.of(new int[]{2, 3, 5}, 8,
                        Set.of(List.of(2, 2, 2, 2), List.of(2, 3, 3), List.of(3, 5))),
                Arguments.of(new int[]{2}, 1, Collections.emptySet()), // Нет решения
                Arguments.of(new int[]{1}, 3, Set.of(List.of(1, 1, 1))),
                Arguments.of(new int[]{7, 3, 2}, 7, // Неотсортированные кандидаты
                        Set.of(List.of(2, 2, 3), List.of(7))),
                Arguments.of(new int[]{8, 7, 4, 3}, 11,
                        Set.of(List.of(3, 4, 4), List.of(3, 8), List.of(4, 7))), // Ожидаемые внутренние списки должны быть отсортированы
                Arguments.of(new int[]{1,2,3}, 5,
                        Set.of(List.of(1,1,1,1,1), List.of(1,1,1,2), List.of(1,2,2), List.of(1,1,3), List.of(2,3))),
                // Граничные случаи
                Arguments.of(null, 5, Collections.emptySet()),
                Arguments.of(new int[]{}, 5, Collections.emptySet()),
                Arguments.of(new int[]{1, 2}, 0, Collections.emptySet()),
                Arguments.of(new int[]{1, 2, 5}, 5, Set.of(List.of(1,1,1,1,1), List.of(1,1,1,2), List.of(1,2,2), List.of(5)))
        );
    }

    @ParameterizedTest(name = "Кандидаты: {0}, Target: {1}")
    @MethodSource("provideCombinationSumCases")
    @DisplayName("Должен находить все уникальные комбинации суммы")
    void shouldFindAllUniqueCombinationSums(int[] candidates, int target, Set<List<Integer>> expectedCombinationsSet) {
        List<List<Integer>> actualResultRaw = solver.combinationSum(candidates, target);

        // Преобразуем актуальный результат в каноническую форму (Set отсортированных списков)
        Set<List<Integer>> actualCombinationsSet = toCanonicalSetOfLists(actualResultRaw);

        assertEquals(expectedCombinationsSet, actualCombinationsSet,
                "Множества комбинаций должны совпадать (порядок комбинаций не важен, элементы внутри комбинаций отсортированы)");
    }
}
