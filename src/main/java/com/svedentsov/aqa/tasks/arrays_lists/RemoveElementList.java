package com.svedentsov.aqa.tasks.arrays_lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Решение задачи №30: Удалить определенный элемент из списка.
 * <p>
 * Описание: Написать функцию для удаления всех вхождений заданного элемента.
 * (Проверяет: работа с коллекциями, циклы, создание нового списка)
 * <p>
 * Задание: Напишите метод `List<Integer> removeElement(List<Integer> list, int elementToRemove)`,
 * который возвращает новый список, полученный из `list` удалением всех вхождений `elementToRemove`.
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
        List<Integer> result = new ArrayList<>(); // Размер будет управляться ArrayList
        Integer elementToRemoveObj = elementToRemove; // Autoboxing

        for (Integer element : list) {
            // Objects.equals безопасно для null элементов в списке
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
     * @return Новый список без указанного элемента. Возвращает пустой список, если исходный null.
     */
    public List<Integer> removeElementStream(List<Integer> list, int elementToRemove) {
        if (list == null) {
            return Collections.emptyList();
        }
        Integer elementToRemoveObj = elementToRemove;
        return list.stream()
                .filter(element -> !Objects.equals(element, elementToRemoveObj))
                .collect(Collectors.toList());
    }

    /**
     * Удаляет все вхождения указанного элемента непосредственно ИЗ ИСХОДНОГО списка (in-place).
     * Использует {@link List#removeIf(java.util.function.Predicate)} (начиная с Java 8).
     *
     * @param list            Список, который будет модифицирован. Может быть null.
     *                        Если список не поддерживает операцию удаления (например, созданный через List.of),
     *                        будет выброшено {@link UnsupportedOperationException}.
     * @param elementToRemove Элемент (int) для удаления.
     * @throws UnsupportedOperationException если список не поддерживает удаление.
     */
    public void removeElementInPlace(List<Integer> list, int elementToRemove) {
        if (list == null) {
            return; // Ничего не делаем с null списком
        }
        Integer elementToRemoveObj = elementToRemove;
        // removeIf удалит элементы и выбросит исключение, если список неизменяемый
        list.removeIf(element -> Objects.equals(element, elementToRemoveObj));
    }
}
