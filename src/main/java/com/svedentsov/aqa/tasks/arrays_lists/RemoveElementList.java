package com.svedentsov.aqa.tasks.arrays_lists;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Решение задачи №30: Удалить определенный элемент из списка.
 * <p>
 * Описание: Написать функцию для удаления всех вхождений заданного элемента.
 * (Проверяет: работа с коллекциями, циклы, создание нового списка)
 * <p>
 * Задание: Напишите метод `List<Integer> removeElement(List<Integer> list, int elementToRemove)`,
 * который возвращает новый список, полученный из `list` удалением всех вхождений `elementToRemove`.
 * Также может быть полезен метод, удаляющий элементы "на месте".
 * <p>
 * Пример: `removeElement(List.of(1, 2, 3, 2, 4, 2), 2)` -> `[1, 3, 4]`.
 */
public class RemoveElementList {

    /**
     * Создает НОВЫЙ список, исключая все вхождения указанного элемента из исходного списка.
     * Не модифицирует исходный список.
     * Использует цикл for-each для итерации и создает новый список для результата.
     *
     * @param list            Исходный список целых чисел. Может быть null или содержать null.
     * @param elementToRemove Элемент (int), который нужно удалить.
     * @return Новый список без указанного элемента. Возвращает пустой список, если исходный null.
     */
    public List<Integer> removeElementNewList(List<Integer> list, int elementToRemove) {
        if (list == null) {
            return Collections.emptyList(); // Возвращаем неизменяемый пустой список
        }
        // Оцениваем размер результирующего списка для оптимизации ArrayList
        List<Integer> result = new ArrayList<>(list.size());
        // Автоупаковка int в Integer для последующего сравнения через Objects.equals
        Integer elementToRemoveObj = elementToRemove;

        for (Integer element : list) {
            // Objects.equals(a, b) безопасен, если 'element' равен null.
            // Он вернет false, если element == null и elementToRemoveObj != null.
            // Он вернет true, если оба null (но elementToRemoveObj не может быть null здесь).
            if (!Objects.equals(element, elementToRemoveObj)) {
                result.add(element); // Добавляем элемент, если он не равен удаляемому
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
     * @return Новый список без указанного элемента. Возвращает пустой список, если исходный null.
     */
    public List<Integer> removeElementStream(List<Integer> list, int elementToRemove) {
        if (list == null) {
            return Collections.emptyList();
        }
        Integer elementToRemoveObj = elementToRemove;
        return list.stream()
                // filter оставляет только те элементы, для которых предикат истинен
                .filter(element -> !Objects.equals(element, elementToRemoveObj))
                // collect собирает отфильтрованные элементы в новый список
                .collect(Collectors.toList());
    }

    /**
     * Удаляет все вхождения указанного элемента непосредственно ИЗ ИСХОДНОГО списка (in-place).
     * Использует {@link List#removeIf(java.util.function.Predicate)} (начиная с Java 8).
     * Это эффективный и безопасный способ модификации списка во время итерации.
     *
     * @param list            Список, который будет модифицирован. Может быть null.
     *                        Если список не поддерживает операцию удаления (например, созданный через List.of),
     *                        будет выброшено {@link UnsupportedOperationException}.
     * @param elementToRemove Элемент (int) для удаления.
     */
    public void removeElementInPlace(List<Integer> list, int elementToRemove) {
        if (list == null) {
            return; // Ничего не делаем с null списком
        }
        Integer elementToRemoveObj = elementToRemove;
        // removeIf удаляет все элементы, для которых предикат (лямбда-выражение) возвращает true
        try {
            list.removeIf(element -> Objects.equals(element, elementToRemoveObj));
        } catch (UnsupportedOperationException e) {
            System.err.println("Warning: Could not modify list in-place. Input list might be unmodifiable.");
            // Можно либо проигнорировать, либо перебросить исключение
        }
    }

    /**
     * Точка входа для демонстрации работы методов удаления элемента.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        RemoveElementList sol = new RemoveElementList();

        // Используем ArrayList для демонстрации in-place удаления
        List<Integer> list1 = new ArrayList<>(Arrays.asList(1, 2, 3, 2, 4, 2, null, 2, 5));
        runRemoveTest(sol, list1, 2, "Стандартный случай с null");
        // Ожидается: Новый список: [1, 3, 4, null, 5], In-place: [1, 3, 4, null, 5]

        List<Integer> list2 = new ArrayList<>(Arrays.asList(1, 1, 1, 1));
        runRemoveTest(sol, list2, 1, "Удаление всех элементов");
        // Ожидается: Новый список: [], In-place: []

        List<Integer> list3 = new ArrayList<>(Arrays.asList(1, 2, 3));
        runRemoveTest(sol, list3, 4, "Элемент для удаления отсутствует");
        // Ожидается: Новый список: [1, 2, 3], In-place: [1, 2, 3]

        List<Integer> list4 = new ArrayList<>();
        runRemoveTest(sol, list4, 5, "Пустой список");
        // Ожидается: Новый список: [], In-place: []

        List<Integer> list5 = null;
        runRemoveTest(sol, list5, 5, "Null список");
        // Ожидается: Новый список: [], In-place: (ничего не делает)

        // Пример с неизменяемым списком для in-place
        List<Integer> unmodifiableList = List.of(1, 2, 2, 3);
        System.out.println("\n--- Attempting in-place removal from unmodifiable list ---");
        System.out.println("Input list: " + unmodifiableList);
        System.out.print("In-place result: ");
        sol.removeElementInPlace(unmodifiableList, 2); // Выведет Warning или бросит Exception
        System.out.println("List after attempt: " + unmodifiableList); // Останется [1, 2, 2, 3]
    }

    /**
     * Вспомогательный метод для тестирования удаления элементов.
     *
     * @param sol          Экземпляр решателя.
     * @param originalList Исходный список (может быть изменен методом in-place!).
     * @param toRemove     Элемент для удаления.
     * @param description  Описание теста.
     */
    private static void runRemoveTest(RemoveElementList sol, List<Integer> originalList, int toRemove, String description) {
        System.out.println("\n--- " + description + " ---");
        String listString = (originalList == null ? "null" : new ArrayList<>(originalList).toString()); // Копия для вывода
        System.out.println("Original List : " + listString);
        System.out.println("Element to remove: " + toRemove);

        // Тест создания нового списка (итеративно)
        try {
            List<Integer> newList1 = sol.removeElementNewList(originalList, toRemove);
            System.out.println("New List (Iter) : " + newList1);
        } catch (Exception e) {
            System.out.println("New List (Iter) : Error - " + e.getMessage());
        }

        // Тест создания нового списка (Stream)
        try {
            List<Integer> newList2 = sol.removeElementStream(originalList, toRemove);
            System.out.println("New List (Stream): " + newList2);
        } catch (Exception e) {
            System.out.println("New List (Stream): Error - " + e.getMessage());
        }

        // Тест удаления на месте (in-place)
        // Создаем КОПИЮ исходного списка СПЕЦИАЛЬНО для этого теста,
        // т.к. метод removeElementInPlace модифицирует переданный список.
        List<Integer> listForInPlace = (originalList == null) ? null : new ArrayList<>(originalList);
        System.out.print("In-place Result : ");
        try {
            sol.removeElementInPlace(listForInPlace, toRemove);
            System.out.println((listForInPlace == null ? "null" : listForInPlace.toString()));
        } catch (Exception e) {
            System.out.println("Error - " + e.getMessage());
        }
    }
}
