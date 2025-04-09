package com.svedentsov.aqa.tasks.arrays_lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Решение задачи №30: Удалить определенный элемент из списка.
 */
public class Task30_RemoveElementList {

    /**
     * Создает НОВЫЙ список, исключая все вхождения указанного элемента из исходного списка.
     * Не модифицирует исходный список.
     * Использует цикл for-each.
     *
     * @param list            Исходный список целых чисел. Может быть null или содержать null.
     * @param elementToRemove Элемент (int), который нужно удалить.
     * @return Новый список без указанного элемента. Возвращает пустой список, если исходный null.
     */
    public List<Integer> removeElementNewList(List<Integer> list, int elementToRemove) {
        if (list == null) {
            return new ArrayList<>();
        }
        List<Integer> result = new ArrayList<>();
        // Автоупаковка int в Integer для сравнения через Objects.equals
        Integer elementToRemoveObj = elementToRemove;

        for (Integer element : list) {
            // Objects.equals корректно обрабатывает null элементы в списке
            if (!Objects.equals(element, elementToRemoveObj)) {
                result.add(element);
            }
        }
        return result;
    }

    /**
     * Создает НОВЫЙ список без указанного элемента с использованием Stream API.
     * Не модифицирует исходный список.
     *
     * @param list            Исходный список. Может быть null или содержать null.
     * @param elementToRemove Элемент (int) для удаления.
     * @return Новый список без указанного элемента.
     */
    public List<Integer> removeElementStream(List<Integer> list, int elementToRemove) {
        if (list == null) {
            return new ArrayList<>();
        }
        Integer elementToRemoveObj = elementToRemove;
        return list.stream()
                // Используем Objects.equals для безопасного сравнения (учитывает null)
                .filter(element -> !Objects.equals(element, elementToRemoveObj))
                .collect(Collectors.toList());
    }

    /**
     * Удаляет все вхождения указанного элемента непосредственно ИЗ ИСХОДНОГО списка (in-place).
     * Использует {@link List#removeIf(java.util.function.Predicate)} (начиная с Java 8),
     * что является рекомендуемым и безопасным способом модификации списка во время итерации.
     *
     * @param list            Список, который будет модифицирован. Может быть null.
     * @param elementToRemove Элемент (int) для удаления.
     */
    public void removeElementInPlace(List<Integer> list, int elementToRemove) {
        if (list == null) {
            return; // Ничего не делаем с null списком
        }
        Integer elementToRemoveObj = elementToRemove;
        // removeIf удаляет все элементы, для которых предикат возвращает true
        list.removeIf(element -> Objects.equals(element, elementToRemoveObj));
    }

    /**
     * Точка входа для демонстрации работы методов удаления элемента.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task30_RemoveElementList sol = new Task30_RemoveElementList();
        List<Integer> originalList = new ArrayList<>(Arrays.asList(1, 2, 3, 2, 4, 2, null, 2, 5));
        int toRemove = 2;

        System.out.println("Original List: " + originalList);

        // Метод 1: Создание нового списка
        List<Integer> newList = sol.removeElementNewList(originalList, toRemove);
        System.out.println("New List (Method 1 - New List): " + newList); // [1, 3, 4, null, 5]
        System.out.println("Original list after Method 1: " + originalList); // Не должен измениться: [1, 2, 3, 2, 4, 2, null, 2, 5]

        // Метод 2: Stream API
        List<Integer> streamList = sol.removeElementStream(originalList, toRemove);
        System.out.println("New List (Method 2 - Stream): " + streamList); // [1, 3, 4, null, 5]

        // Метод 3: In-place удаление
        // Создаем копию для теста in-place, чтобы не влиять на другие тесты
        List<Integer> listToModify = new ArrayList<>(originalList);
        System.out.println("\nList to modify (before in-place): " + listToModify);
        sol.removeElementInPlace(listToModify, toRemove);
        System.out.println("List to modify (after in-place):  " + listToModify); // [1, 3, 4, null, 5]

        // Пример с удалением null (невозможно для int параметра)
        // Если бы метод принимал Integer, можно было бы удалить null:
        // List<Integer> listWithNulls = new ArrayList<>(Arrays.asList(1, null, 2, null, 1));
        // sol.removeElementInPlace(listWithNulls, null); // Удалит null
        // System.out.println("List after removing nulls: " + listWithNulls); // [1, 2, 1]

        // Пример с пустым списком
        List<Integer> emptyList = new ArrayList<>();
        System.out.println("\nRemoving " + toRemove + " from empty list:");
        System.out.println(" New list: " + sol.removeElementNewList(emptyList, toRemove)); // []
        sol.removeElementInPlace(emptyList, toRemove);
        System.out.println(" In-place: " + emptyList); // []
    }
}
