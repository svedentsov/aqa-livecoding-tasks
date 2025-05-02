package com.svedentsov.aqa.tasks.maps_sets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты для GroupListElements")
class GroupListElementsTest {

    private GroupListElements grouper;

    @BeforeEach
    void setUp() {
        grouper = new GroupListElements();
    }

    // --- Хелпер для сортировки списков в Map ---
    // Нужен для сравнения карт независимо от порядка элементов в списках-значениях
    private Map<Integer, List<String>> sortMapValueLists(Map<Integer, List<String>> map) {
        map.forEach((key, value) -> Collections.sort(value));
        return map;
    }

    // --- Источник данных для тестов ---
    // Формат: входной список, ожидаемая карта (с отсортированными списками-значениями)
    static Stream<Arguments> provideListsForGrouping() {
        // Ожидаемые карты создаем как TreeMap и сразу сортируем внутренние списки
        Map<Integer, List<String>> expected1 = new TreeMap<>();
        expected1.put(1, Collections.singletonList("a")); // Используем singletonList для неизменяемости
        expected1.put(2, Collections.singletonList("zz"));
        expected1.put(3, Arrays.asList("bat", "cat", "dog"));
        Collections.sort(expected1.get(3));
        expected1.put(4, Arrays.asList("ball", "kiwi"));
        Collections.sort(expected1.get(4));
        expected1.put(5, Collections.singletonList("apple"));
        expected1.put(7, Collections.singletonList("apricot"));

        Map<Integer, List<String>> expected2 = new TreeMap<>();
        expected2.put(3, Arrays.asList("one", "two"));
        Collections.sort(expected2.get(3));
        expected2.put(4, Arrays.asList("four", "five"));
        Collections.sort(expected2.get(4));
        expected2.put(5, Collections.singletonList("three"));

        Map<Integer, List<String>> expected3 = new TreeMap<>();
        expected3.put(1, Arrays.asList("a", "b", "c"));
        Collections.sort(expected3.get(1));

        Map<Integer, List<String>> expected4 = new TreeMap<>();
        expected4.put(4, Arrays.asList("aaaa", "bbbb"));
        Collections.sort(expected4.get(4));

        return Stream.of(
                Arguments.of(Arrays.asList("apple", "bat", "cat", "apricot", "ball", null, "dog", "kiwi", "a", "zz"), expected1),
                Arguments.of(List.of("one", "two", "three", "four", "five"), expected2),
                Arguments.of(List.of("a", "b", "c"), expected3),
                Arguments.of(List.of("aaaa", "bbbb"), expected4),
                Arguments.of(Arrays.asList(null, null), new TreeMap<>()) // Только null
        );
    }

    // --- Тесты для groupStringsByLength (итеративный) ---
    @Nested
    @DisplayName("Метод groupStringsByLength (итеративный)")
    class IterativeGroupingTests {

        @ParameterizedTest(name = "Вход: {0}")
        @MethodSource("com.svedentsov.aqa.tasks.maps_sets.GroupListElementsTest#provideListsForGrouping")
        @DisplayName("Должен корректно группировать строки по длине")
        void shouldGroupStringsByLength(List<String> inputList, Map<Integer, List<String>> expectedMap) {
            Map<Integer, List<String>> actualMap = grouper.groupStringsByLength(inputList);
            // Сортируем списки в актуальном результате перед сравнением
            assertEquals(expectedMap, sortMapValueLists(actualMap));
        }

        @ParameterizedTest(name = "Вход: {0} -> Ожидание: пустая карта")
        @NullAndEmptySource // Проверяет null и Collections.emptyList()
        @DisplayName("Должен возвращать пустую карту для null или пустого списка")
        void shouldReturnEmptyMapForNullOrEmptyList(List<String> inputList) {
            Map<Integer, List<String>> actualMap = grouper.groupStringsByLength(inputList);
            assertTrue(actualMap.isEmpty(), "Карта должна быть пустой для null/пустого списка");
            assertTrue(actualMap instanceof TreeMap, "Должен возвращаться TreeMap"); // Проверяем тип
        }
    }

    // --- Тесты для groupStringsByLengthStream (Stream API) ---
    @Nested
    @DisplayName("Метод groupStringsByLengthStream (Stream API)")
    class StreamGroupingTests {

        @ParameterizedTest(name = "Вход: {0}")
        @MethodSource("com.svedentsov.aqa.tasks.maps_sets.GroupListElementsTest#provideListsForGrouping")
        @DisplayName("Должен корректно группировать строки по длине")
        void shouldGroupStringsByLengthStream(List<String> inputList, Map<Integer, List<String>> expectedMap) {
            Map<Integer, List<String>> actualMap = grouper.groupStringsByLengthStream(inputList);
            // Сортируем списки в актуальном результате перед сравнением
            assertEquals(expectedMap, sortMapValueLists(actualMap));
        }

        @ParameterizedTest(name = "Вход: {0} -> Ожидание: пустая карта")
        @NullAndEmptySource // Проверяет null и Collections.emptyList()
        @DisplayName("Должен возвращать пустую карту для null или пустого списка")
        void shouldReturnEmptyMapForNullOrEmptyList(List<String> inputList) {
            Map<Integer, List<String>> actualMap = grouper.groupStringsByLengthStream(inputList);
            assertTrue(actualMap.isEmpty(), "Карта (Stream) должна быть пустой для null/пустого списка");
            assertTrue(actualMap instanceof TreeMap, "Должен возвращаться TreeMap (Stream)"); // Проверяем тип
        }
    }
}
