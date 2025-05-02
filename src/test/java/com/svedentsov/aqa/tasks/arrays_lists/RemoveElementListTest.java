package com.svedentsov.aqa.tasks.arrays_lists;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для RemoveElementList")
class RemoveElementListTest {

    private RemoveElementList remover;

    @BeforeEach
    void setUp() {
        remover = new RemoveElementList();
    }

    // --- Источник данных для тестов (возвращающих новый список) ---
    // Формат: исходный список, элемент для удаления, ожидаемый список
    static Stream<Arguments> provideListsForNewListRemoval() {
        // Используем Arrays.asList для списков с null
        return Stream.of(
                Arguments.of(Arrays.asList(1, 2, 3, 2, 4, 2, null, 2, 5), 2, Arrays.asList(1, 3, 4, null, 5)), // Стандартный случай
                Arguments.of(Arrays.asList(1, 1, 1, 1), 1, Collections.emptyList()), // Удаление всех
                Arguments.of(Arrays.asList(1, 2, 3), 4, Arrays.asList(1, 2, 3)), // Элемент отсутствует
                Arguments.of(Collections.emptyList(), 5, Collections.emptyList()), // Пустой список
                Arguments.of(Arrays.asList(1, 2, null, 3), 0, Arrays.asList(1, 2, null, 3)) // Удаляем элемент, которого нет
        );
    }

    // --- Тесты для методов, возвращающих новый список ---
    @Nested
    @DisplayName("Методы removeElementNewList / removeElementStream (возвращают новый список)")
    class NewListRemovalTests {

        @ParameterizedTest(name = "Iterative: Вход: {0}, Удалить: {1} -> Ожидание: {2}")
        @MethodSource("com.svedentsov.aqa.tasks.arrays_lists.RemoveElementListTest#provideListsForNewListRemoval")
        void shouldRemoveElementAndReturnNewListIterative(List<Integer> inputList, int elementToRemove, List<Integer> expectedList) {
            List<Integer> actualList = remover.removeElementNewList(inputList, elementToRemove);
            assertEquals(expectedList, actualList, "Итеративный метод вернул неверный список");
        }

        @ParameterizedTest(name = "Stream: Вход: {0}, Удалить: {1} -> Ожидание: {2}")
        @MethodSource("com.svedentsov.aqa.tasks.arrays_lists.RemoveElementListTest#provideListsForNewListRemoval")
        void shouldRemoveElementAndReturnNewListStream(List<Integer> inputList, int elementToRemove, List<Integer> expectedList) {
            List<Integer> actualList = remover.removeElementStream(inputList, elementToRemove);
            assertEquals(expectedList, actualList, "Stream метод вернул неверный список");
        }

        @Test
        @DisplayName("Должны возвращать пустой список для null входа")
        void shouldReturnEmptyListForNullInput() {
            assertTrue(remover.removeElementNewList(null, 1).isEmpty(), "Итеративный метод должен вернуть пустой список для null");
            assertTrue(remover.removeElementStream(null, 1).isEmpty(), "Stream метод должен вернуть пустой список для null");
            assertEquals(Collections.emptyList(), remover.removeElementNewList(null, 1)); // Проверка типа
            assertEquals(Collections.emptyList(), remover.removeElementStream(null, 1));
        }
    }

    // --- Тесты для метода removeElementInPlace (модифицирует исходный список) ---
    @Nested
    @DisplayName("Метод removeElementInPlace (модифицирует список)")
    class InPlaceRemovalTests {

        // Используем тот же источник данных, но будем создавать изменяемые копии
        @ParameterizedTest(name = "In-Place: Вход: {0}, Удалить: {1} -> Ожидание: {2}")
        @MethodSource("com.svedentsov.aqa.tasks.arrays_lists.RemoveElementListTest#provideListsForNewListRemoval")
        void shouldRemoveElementInPlaceCorrectly(List<Integer> originalList, int elementToRemove, List<Integer> expectedList) {
            // Создаем изменяемую копию для теста in-place
            List<Integer> listToModify = new ArrayList<>(originalList);
            remover.removeElementInPlace(listToModify, elementToRemove);
            assertEquals(expectedList, listToModify, "Список не был корректно изменен in-place");
        }

        @Test
        @DisplayName("Не должен выбрасывать исключение для null входа")
        void shouldNotThrowExceptionForNullInput() {
            assertDoesNotThrow(() -> remover.removeElementInPlace(null, 1),
                    "Вызов in-place с null списком не должен приводить к исключению");
        }

        @Test
        @DisplayName("Должен выбросить UnsupportedOperationException для неизменяемого списка")
        void shouldThrowUnsupportedOperationExceptionForUnmodifiableList() {
            List<Integer> unmodifiableList = List.of(1, 2, 2, 3); // Неизменяемый список
            assertThrows(UnsupportedOperationException.class, () -> {
                remover.removeElementInPlace(unmodifiableList, 2);
            }, "Должно быть выброшено исключение при попытке изменить неизменяемый список");
        }
    }
}
