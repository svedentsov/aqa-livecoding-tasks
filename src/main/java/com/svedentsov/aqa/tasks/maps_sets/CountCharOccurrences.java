package com.svedentsov.aqa.tasks.maps_sets;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Решение задачи №5: Посчитать количество вхождений символа в строке.
 * <p>
 * Описание: Написать функцию, которая подсчитывает, сколько раз каждый символ
 * встречается в строке. (Проверяет: работа со строками, Map, циклы)
 * <p>
 * Задание: Напишите метод `Map<Character, Integer> countCharacters(String str)`,
 * который возвращает Map, где ключами являются символы из строки `str`,
 * а значениями — количество их вхождений.
 * <p>
 * Пример: `countCharacters("hello world")` ->
 * `{' ': 1, 'd': 1, 'e': 1, 'h': 1, 'l': 3, 'o': 2, 'r': 1, 'w': 1}` (порядок должен быть отсортирован, если используется TreeMap).
 */
public class CountCharOccurrences {

    /**
     * Подсчитывает количество вхождений каждого символа в строке итеративно.
     * Использует TreeMap для хранения счетчиков и автоматической сортировки ключей (символов).
     * Чувствителен к регистру.
     * Сложность O(n log k) по времени из-за TreeMap (или O(n), если амортизированно), где n - длина строки, k - кол-во уник. символов.
     * Память O(k).
     *
     * @param str Входная строка. Может быть null.
     * @return Карта (TreeMap), где ключ - символ (Character), значение - количество его вхождений (Integer).
     * Возвращает пустую карту, если строка null или пуста.
     */
    public Map<Character, Integer> countCharacters(String str) {
        Map<Character, Integer> counts = new TreeMap<>();
        if (str == null || str.isEmpty()) {
            return counts; // Возвращаем пустую (отсортированную) карту
        }
        // Проходим по каждому символу строки
        for (char c : str.toCharArray()) {
            // getOrDefault(key, defaultValue) возвращает значение по ключу,
            // или defaultValue (здесь 0), если ключ отсутствует.
            // Увеличиваем счетчик для символа c.
            counts.put(c, counts.getOrDefault(c, 0) + 1);
        }
        return counts;
    }

    /**
     * Подсчитывает количество вхождений каждого символа с использованием Stream API.
     * Чувствителен к регистру. Использует TreeMap для сортировки ключей.
     *
     * @param str Входная строка. Может быть null.
     * @return Карта (TreeMap), где ключ - символ (Character), значение - количество его вхождений (Long).
     * Обратите внимание: значение имеет тип Long из-за использования Collectors.counting().
     * Возвращает пустую карту для null или пустой строки.
     */
    public Map<Character, Long> countCharactersStream(String str) {
        if (str == null || str.isEmpty()) {
            // Возвращаем типизированную пустую (отсортированную) карту
            return new TreeMap<Character, Long>();
        }
        return str.chars() // Получаем IntStream символов (кодов Unicode)
                .mapToObj(c -> (char) c) // Преобразуем IntStream в Stream<Character>
                // Группируем по символу и считаем количество (Collectors.counting() возвращает Long)
                // Собираем в TreeMap для сортировки ключей
                .collect(Collectors.groupingBy(Function.identity(),
                        TreeMap::new, // Указываем фабрику для создания TreeMap
                        Collectors.counting()));
    }
}
