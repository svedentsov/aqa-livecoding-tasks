package com.svedentsov.aqa.tasks.arrays_lists;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Решение задачи №7: Удалить дубликаты из списка.
 * Описание: Написать функцию, которая принимает список и возвращает новый список
 * без дубликатов. (Проверяет: работа с коллекциями, Set, циклы)
 * Задание: Напишите метод `List<String> removeDuplicates(List<String> list)`,
 * который принимает список строк и возвращает новый список, содержащий только
 * уникальные строки из исходного списка, сохраняя порядок первого вхождения.
 * Пример: `removeDuplicates(List.of("a", "b", "a", "c", "b"))` -> `["a", "b", "c"]`.
 */
public class RemoveDuplicatesList {

    /**
     * Удаляет дубликаты из списка строк, сохраняя порядок первого вхождения.
     * Использует LinkedHashSet для эффективного отслеживания уникальных элементов
     * и сохранения порядка. Null элементы в исходном списке будут сохранены как
     * уникальные (если встречаются).
     * Сложность O(n) по времени и O(n) по памяти.
     *
     * @param list Исходный список строк. Может быть null или содержать null.
     * @return Новый список с уникальными строками в порядке их первого появления
     * в исходном списке. Возвращает пустой список, если исходный был null.
     */
    public List<String> removeDuplicates(List<String> list) {
        if (list == null) {
            return new ArrayList<>(); // Возвращаем пустой список для null входа
        }
        // LinkedHashSet автоматически обрабатывает дубликаты (включая null)
        // и сохраняет порядок вставки.
        Set<String> uniqueSet = new LinkedHashSet<>(list);
        // Создаем новый список из уникальных элементов сета
        return new ArrayList<>(uniqueSet);
    }

    /**
     * Удаляет дубликаты из списка строк с использованием Stream API (distinct()).
     * Порядок элементов сохраняется. Null элементы также обрабатываются корректно
     * методом distinct().
     *
     * @param list Исходный список строк. Может быть null или содержать null.
     * @return Новый список с уникальными строками. Возвращает пустой список для null входа.
     */
    public List<String> removeDuplicatesStream(List<String> list) {
        if (list == null) {
            return new ArrayList<>();
        }
        return list.stream()
                // .filter(Objects::nonNull) // Раскомментировать, если null нужно полностью исключить
                .distinct() // Удаляет дубликаты, корректно обрабатывает null
                .collect(Collectors.toList()); // Собирает в новый список
    }
}
