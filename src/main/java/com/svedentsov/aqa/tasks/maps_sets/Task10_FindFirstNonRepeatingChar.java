package com.svedentsov.aqa.tasks.maps_sets;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Решение задачи №10: Найти первый неповторяющийся символ в строке.
 */
public class Task10_FindFirstNonRepeatingChar {

    /**
     * Находит первый неповторяющийся символ в строке.
     * Использует LinkedHashMap для подсчета символов и сохранения порядка их появления.
     * Сложность O(n) по времени (два прохода по строке или один по строке и один по карте),
     * O(k) по памяти, где k - количество уникальных символов.
     *
     * @param str Входная строка. Может быть null.
     * @return Первый неповторяющийся символ или null, если такого символа нет,
     * или если строка null/пуста. Возвращается как объект Character.
     */
    public Character findFirstNonRepeatingChar(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        // Используем LinkedHashMap для сохранения порядка символов
        // Ключ - символ, Значение - количество вхождений
        Map<Character, Integer> counts = new LinkedHashMap<>();

        // Первый проход: считаем вхождения каждого символа
        for (char c : str.toCharArray()) {
            counts.put(c, counts.getOrDefault(c, 0) + 1);
        }

        // Второй проход: ищем первый символ со счетчиком 1 в карте
        // Так как карта LinkedHashMap, порядок итерации соответствует порядку добавления
        for (Map.Entry<Character, Integer> entry : counts.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey(); // Нашли первый уникальный
            }
        }

        return null; // Не найдено неповторяющихся символов
    }

    /**
     * Точка входа для демонстрации работы метода поиска первого неповторяющегося символа.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task10_FindFirstNonRepeatingChar sol = new Task10_FindFirstNonRepeatingChar();
        String s1 = "swiss";
        System.out.println("First non-repeating in '" + s1 + "': " + sol.findFirstNonRepeatingChar(s1)); // w

        String s2 = "aabbcc";
        System.out.println("First non-repeating in '" + s2 + "': " + sol.findFirstNonRepeatingChar(s2)); // null

        String s3 = "stress";
        System.out.println("First non-repeating in '" + s3 + "': " + sol.findFirstNonRepeatingChar(s3)); // t

        String s4 = "aabbccddeeffG";
        System.out.println("First non-repeating in '" + s4 + "': " + sol.findFirstNonRepeatingChar(s4)); // G

        String s5 = "a";
        System.out.println("First non-repeating in '" + s5 + "': " + sol.findFirstNonRepeatingChar(s5)); // a

        String s6 = "";
        System.out.println("First non-repeating in '" + s6 + "': " + sol.findFirstNonRepeatingChar(s6)); // null

        System.out.println("First non-repeating in null: " + sol.findFirstNonRepeatingChar(null)); // null
    }
}