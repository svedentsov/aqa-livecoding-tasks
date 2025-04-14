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
 * `{' ': 1, 'd': 1, 'e': 1, 'h': 1, 'l': 3, 'o': 2, 'r': 1, 'w': 1}` (порядок может отличаться).
 */
public class CountCharOccurrences {

    /**
     * Подсчитывает количество вхождений каждого символа в строке итеративно.
     * Использует HashMap для хранения счетчиков. Порядок ключей не гарантирован.
     * Чувствителен к регистру.
     * Сложность O(n) по времени, где n - длина строки.
     * Память O(k), где k - количество уникальных символов.
     *
     * @param str Входная строка. Может быть null.
     * @return Карта (Map), где ключ - символ (Character), значение - количество его вхождений (Integer).
     * Возвращает пустую карту, если строка null или пуста.
     */
    public Map<Character, Integer> countCharacters(String str) {
        // Используем TreeMap для автоматической сортировки ключей (символов)
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
     * Чувствителен к регистру.
     *
     * @param str Входная строка. Может быть null.
     * @return Карта (TreeMap для сортировки), где ключ - символ (Character), значение - количество его вхождений (Long).
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

    /**
     * Точка входа для демонстрации работы методов подсчета символов.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        CountCharOccurrences sol = new CountCharOccurrences();

        runCountTest(sol, "hello world", "Стандартный случай (строчные)");
        // Ожидаемый результат (отсортировано): {' ':1, d:1, e:1, h:1, l:3, o:2, r:1, w:1}

        runCountTest(sol, "Programming Java", "Смешанный регистр и пробел");
        // Ожидаемый результат (отсортировано): {' ':1, J:1, P:1, a:2, g:2, i:1, m:2, n:1, o:1, r:2, v:1}

        runCountTest(sol, "", "Пустая строка");
        // Ожидаемый результат: {}

        runCountTest(sol, null, "Null строка");
        // Ожидаемый результат: {}

        runCountTest(sol, "a", "Один символ");
        // Ожидаемый результат: {a:1}

        runCountTest(sol, "aaaaa", "Все символы одинаковы");
        // Ожидаемый результат: {a:5}

        runCountTest(sol, "!@#$ %^& *()", "Специальные символы и пробелы");
        // Ожидаемый результат (отсортировано): {' ':2, !:1, #:1, $:1, %:1, &:1, (:1, ):1, *:1, @:1, ^:1}

        runCountTest(sol, "你好 世界", "Unicode символы (китайский)");
        // Ожидаемый результат (отсортировано): {' ':1, 世:1, 界:1, 你:1, 好:1}
    }

    /**
     * Вспомогательный метод для тестирования методов подсчета символов.
     *
     * @param sol         Экземпляр решателя.
     * @param str         Тестовая строка.
     * @param description Описание теста.
     */
    private static void runCountTest(CountCharOccurrences sol, String str, String description) {
        System.out.println("\n--- " + description + " ---");
        String input = (str == null ? "null" : "\"" + str + "\"");
        System.out.println("Input string: " + input);
        try {
            System.out.println("countCharacters (Iterative): " + sol.countCharacters(str));
        } catch (Exception e) {
            System.out.println("countCharacters (Iterative): Error - " + e.getMessage());
        }
        try {
            System.out.println("countCharacters (Stream):    " + sol.countCharactersStream(str));
        } catch (Exception e) {
            System.out.println("countCharacters (Stream):    Error - " + e.getMessage());
        }
    }
}
