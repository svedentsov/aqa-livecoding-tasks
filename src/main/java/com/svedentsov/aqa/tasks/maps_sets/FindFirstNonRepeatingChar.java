package com.svedentsov.aqa.tasks.maps_sets;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Решение задачи №10: Найти первый неповторяющийся символ в строке.
 * <p>
 * Описание: Написать функцию, которая находит первый символ в строке,
 * который встречается только один раз. (Проверяет: работа со строками,
 * Map/массивы для счетчиков, циклы)
 * <p>
 * Задание: Напишите метод `Character findFirstNonRepeatingChar(String str)`,
 * который находит и возвращает первый символ в строке `str`, который встречается
 * только один раз. Если такого символа нет, верните null или выбросите исключение.
 * <p>
 * Пример: `findFirstNonRepeatingChar("swiss")` -> `'w'`,
 * `findFirstNonRepeatingChar("aabbcc")` -> `null`.
 */
public class FindFirstNonRepeatingChar {

    /**
     * Находит первый неповторяющийся символ в строке.
     * Использует LinkedHashMap для подсчета частоты символов и сохранения порядка
     * их первого появления в строке. Метод чувствителен к регистру.
     * Сложность O(n) по времени (два прохода: один по строке, один по карте в худшем случае).
     * Сложность O(k) по памяти, где k - количество уникальных символов в строке
     * (размер алфавита).
     *
     * @param str Входная строка. Может быть null.
     * @return Первый неповторяющийся символ (как объект Character) или null,
     * если такого символа нет, или если строка null/пуста.
     */
    public Character findFirstNonRepeatingChar(String str) {
        // Обработка null или пустой строки
        if (str == null || str.isEmpty()) {
            return null;
        }
        // Используем LinkedHashMap для сохранения порядка символов так,
        // как они впервые появляются в строке.
        // Ключ - символ (Character), Значение - количество вхождений (Integer).
        Map<Character, Integer> counts = new LinkedHashMap<>();

        // Первый проход: подсчитываем частоту каждого символа
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            counts.put(c, counts.getOrDefault(c, 0) + 1);
        }

        // Второй проход: итерируем по записям карты (в порядке вставки)
        // и ищем первую запись со значением (количеством) равным 1.
        for (Map.Entry<Character, Integer> entry : counts.entrySet()) {
            if (entry.getValue() == 1) {
                // Найден первый символ, который встретился только один раз.
                return entry.getKey();
            }
        }

        // Если цикл завершился, значит, неповторяющихся символов не найдено.
        return null;
    }

    /**
     * Точка входа для демонстрации работы метода поиска первого неповторяющегося символа.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        FindFirstNonRepeatingChar sol = new FindFirstNonRepeatingChar();

        runNonRepeatingTest(sol, "swiss", "Стандартный случай 1"); // 'w'
        runNonRepeatingTest(sol, "aabbcc", "Все символы повторяются"); // null
        runNonRepeatingTest(sol, "stress", "Стандартный случай 2"); // 't'
        runNonRepeatingTest(sol, "aabbccddeeffG", "Символ в конце"); // 'G'
        runNonRepeatingTest(sol, "a", "Один символ"); // 'a'
        runNonRepeatingTest(sol, "", "Пустая строка"); // null
        runNonRepeatingTest(sol, null, "Null строка"); // null
        runNonRepeatingTest(sol, "Go Google", "С пробелами и регистром"); // ' ' (пробел - первый уникальный)
        runNonRepeatingTest(sol, "loveleetcode", "Другой пример"); // 'v'
        runNonRepeatingTest(sol, "aabbccddee", "Нет уникальных"); // null
        runNonRepeatingTest(sol, "zabcdeabcde", "Первый символ уникален"); // 'z'
        runNonRepeatingTest(sol, "112233455", "Строка с цифрами"); // '4'
    }

    /**
     * Вспомогательный метод для тестирования findFirstNonRepeatingChar.
     *
     * @param sol         Экземпляр решателя.
     * @param str         Тестовая строка.
     * @param description Описание теста.
     */
    private static void runNonRepeatingTest(FindFirstNonRepeatingChar sol, String str, String description) {
        System.out.println("\n--- " + description + " ---");
        String input = (str == null ? "null" : "\"" + str + "\"");
        System.out.println("Input string: " + input);
        try {
            Character result = sol.findFirstNonRepeatingChar(str);
            // Выводим результат в кавычках, если это символ, иначе null
            System.out.println("First non-repeating: " + (result == null ? "null" : "'" + result + "'"));
        } catch (Exception e) {
            System.out.println("First non-repeating: Error - " + e.getMessage());
        }
    }
}
