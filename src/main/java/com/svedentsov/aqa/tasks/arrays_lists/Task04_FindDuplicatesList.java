package com.svedentsov.aqa.tasks.arrays_lists;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Решение задачи №4: Найти дубликаты в списке.
 * <p>
 * Описание: Написать функцию, которая находит и возвращает дублирующиеся элементы
 * в массиве или списке целых чисел/строк. (Проверяет: работа с коллекциями, Set/Map, циклы)
 * <p>
 * Задание: Напишите метод `List<Integer> findDuplicates(List<Integer> numbers)`,
 * который принимает список целых чисел и возвращает список уникальных чисел,
 * которые встречаются в исходном списке более одного раза.
 * <p>
 * Пример: `findDuplicates(List.of(1, 2, 3, 2, 4, 5, 1, 5))` -> `[1, 2, 5]`
 * (порядок не важен, можно вернуть Set).
 */
public class Task04_FindDuplicatesList {

    /**
     * Находит уникальные дублирующиеся элементы в списке целых чисел.
     * Использует два множества: одно для отслеживания всех встреченных уникальных чисел,
     * другое для хранения уникальных чисел, которые были встречены повторно (дубликатов).
     * Игнорирует null элементы при поиске дубликатов.
     * <p>
     * Сложность: O(n) по времени (каждый элемент обрабатывается один раз).
     * Сложность: O(k) по памяти, где k - количество уникальных элементов в списке (для `seen`),
     * плюс O(d), где d - количество уникальных дубликатов (для `duplicates`). В худшем случае O(n).
     *
     * @param numbers Список целых чисел для проверки. Может быть null или содержать null.
     * @return Новый список, содержащий уникальные дубликаты (не включая null).
     * Если дубликатов нет или входной список null/пуст, возвращает пустой список.
     * Порядок элементов в возвращаемом списке не гарантирован (зависит от HashSet).
     */
    public List<Integer> findDuplicates(List<Integer> numbers) {
        // Обработка граничных случаев: null или пустой список
        if (numbers == null || numbers.isEmpty()) {
            return new ArrayList<>(); // Возвращаем пустой список
        }
        // Множество для отслеживания уже встреченных чисел
        Set<Integer> seen = new HashSet<>();
        // Множество для сбора уникальных дубликатов (чтобы одно и то же число-дубликат не попало в результат много раз)
        Set<Integer> duplicates = new HashSet<>();
        for (Integer number : numbers) {
            // Игнорируем null элементы при поиске дубликатов чисел
            if (number == null) {
                continue;
            }
            // Метод add() у Set возвращает false, если элемент УЖЕ существует в множестве.
            // Это значит, что мы встретили этот 'number' как минимум во второй раз.
            if (!seen.add(number)) {
                // Если элемент уже был в 'seen', значит это дубликат. Добавляем его в 'duplicates'.
                // HashSet сам позаботится об уникальности добавляемых дубликатов.
                duplicates.add(number);
            }
        }
        // Преобразуем множество уникальных дубликатов в список для возврата
        return new ArrayList<>(duplicates);
    }

    /**
     * Находит уникальные дублирующиеся элементы с использованием Stream API и группировки.
     * Игнорирует null элементы.
     * <p>
     * Сложность: Зависит от реализации `groupingBy` и `counting`. Обычно требует
     * прохода по списку и создания промежуточной карты. Может быть сравнима с O(n)
     * по времени, но с потенциально большими накладными расходами и использованием памяти
     * для создания карты (O(k) по памяти, где k - кол-во уник. элементов).
     *
     * @param numbers Список целых чисел для проверки. Может быть null или содержать null.
     * @return Новый список уникальных дубликатов (не включая null).
     * Порядок элементов в возвращаемом списке не гарантирован.
     */
    public List<Integer> findDuplicatesStream(List<Integer> numbers) {
        // Обработка граничных случаев
        if (numbers == null || numbers.isEmpty()) {
            return new ArrayList<>();
        }
        return numbers.stream() // Создаем поток из списка
                .filter(Objects::nonNull) // Фильтруем (удаляем) null элементы из потока
                // Группируем элементы потока по их значению (Function.identity())
                // и для каждой группы считаем количество элементов (Collectors.counting()).
                // Результат: Map<Integer, Long>, где ключ - число, значение - сколько раз оно встретилось.
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                // Получаем набор записей (ключ-значение) из карты
                .entrySet().stream() // Создаем поток из набора записей
                // Фильтруем записи: оставляем только те, у которых значение (количество) > 1
                .filter(entry -> entry.getValue() > 1)
                // Из оставшихся записей извлекаем только ключ (само число-дубликат)
                .map(Map.Entry::getKey)
                // Собираем ключи (числа-дубликаты) в новый список
                .collect(Collectors.toList());
    }

    /**
     * Точка входа для демонстрации работы методов поиска дубликатов.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task04_FindDuplicatesList sol = new Task04_FindDuplicatesList();
        // 1. Стандартный случай с несколькими дубликатами. Ожидаемый результат: [1, 2, 5]
        List<Integer> list1 = List.of(1, 2, 3, 2, 4, 5, 1, 5);
        System.out.println("findDuplicates(" + list1 + "): " + sol.findDuplicates(list1));
        System.out.println("findDuplicatesStream(" + list1 + "): " + sol.findDuplicatesStream(list1));

        // 2. Список без дубликатов. Ожидаемый результат: []
        List<Integer> list2 = List.of(1, 2, 3, 4);
        System.out.println("findDuplicates(" + list2 + "): " + sol.findDuplicates(list2)); // []
        System.out.println("findDuplicatesStream(" + list2 + "): " + sol.findDuplicatesStream(list2)); // []

        // 3. Список, где все элементы - дубликаты одного числа. Ожидаемый результат: [1]
        List<Integer> list3 = List.of(1, 1, 1, 1);
        System.out.println("findDuplicates(" + list3 + "): " + sol.findDuplicates(list3)); // [1]
        System.out.println("findDuplicatesStream(" + list3 + "): " + sol.findDuplicatesStream(list3)); // [1]

        // 4. Список с null значениями и дубликатами. Ожидаемый результат: [1, 2]
        List<Integer> list4 = new ArrayList<>();
        list4.add(1);
        list4.add(null);
        list4.add(2);
        list4.add(null); // Несколько null
        list4.add(1);    // Дубликат 1
        list4.add(2);    // Дубликат 2
        System.out.println("findDuplicates(" + list4 + "): " + sol.findDuplicates(list4)); // [1, 2]
        System.out.println("findDuplicatesStream(" + list4 + "): " + sol.findDuplicatesStream(list4)); // [1, 2]
    }
}
