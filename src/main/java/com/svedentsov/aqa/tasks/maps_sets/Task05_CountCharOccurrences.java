package com.svedentsov.aqa.tasks.maps_sets;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Решение задачи №5: Посчитать количество вхождений символа в строке.
 */
public class Task05_CountCharOccurrences {

    /**
     * Подсчитывает количество вхождений каждого символа в строке.
     * Использует HashMap для хранения счетчиков.
     * Сложность O(n) по времени, где n - длина строки.
     * Память O(k), где k - количество уникальных символов.
     *
     * @param str Входная строка. Может быть null.
     * @return Карта (Map), где ключ - символ (Character), значение - количество его вхождений (Integer).
     * Возвращает пустую карту, если строка null или пуста.
     */
    public Map<Character, Integer> countCharacters(String str) {
        Map<Character, Integer> counts = new HashMap<>();
        if (str == null || str.isEmpty()) {
            return counts;
        }
        // Проходим по каждому символу строки
        for (char c : str.toCharArray()) {
            // getOrDefault(key, defaultValue) возвращает значение по ключу,
            // или defaultValue (здесь 0), если ключ отсутствует.
            counts.put(c, counts.getOrDefault(c, 0) + 1);
        }
        return counts;
    }

    /**
     * Подсчитывает количество вхождений каждого символа с использованием Stream API.
     *
     * @param str Входная строка. Может быть null.
     * @return Карта (Map), где ключ - символ (Character), значение - количество его вхождений (Long).
     * Возвращает пустую карту для null или пустой строки.
     */
    public Map<Character, Long> countCharactersStream(String str) {
        if (str == null || str.isEmpty()) {
            // Возвращаем типизированную пустую карту
            return new HashMap<Character, Long>();
        }
        return str.chars() // Получаем IntStream символов (кодов Unicode)
                .mapToObj(c -> (char) c) // Преобразуем IntStream в Stream<Character>
                // Группируем по символу и считаем количество (Collectors.counting() возвращает Long)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }


    /**
     * Точка входа для демонстрации работы методов подсчета символов.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task05_CountCharOccurrences sol = new Task05_CountCharOccurrences();
        String s1 = "hello world";
        System.out.println("countCharacters(\"" + s1 + "\"): " + sol.countCharacters(s1));
        // { =1, r=1, d=1, e=1, w=1, h=1, l=3, o=2} (порядок не гарантирован для HashMap)
        System.out.println("countCharactersStream(\"" + s1 + "\"): " + sol.countCharactersStream(s1));

        String s2 = "Programming Java"; // Учтем регистр и пробел
        System.out.println("countCharacters(\"" + s2 + "\"): " + sol.countCharacters(s2));
        // { =1, a=2, P=1, r=2, g=2, v=1, i=1, m=2, J=1, n=1, o=1}
        System.out.println("countCharactersStream(\"" + s2 + "\"): " + sol.countCharactersStream(s2));


        String s3 = "";
        System.out.println("countCharacters(\"" + s3 + "\"): " + sol.countCharacters(s3)); // {}
        System.out.println("countCharactersStream(\"" + s3 + "\"): " + sol.countCharactersStream(s3)); // {}

        System.out.println("countCharacters(null): " + sol.countCharacters(null)); // {}
        System.out.println("countCharactersStream(null): " + sol.countCharactersStream(null)); // {}
    }
}
