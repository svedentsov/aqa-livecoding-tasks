package com.svedentsov.aqa.tasks.algorithms;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Решение задачи №86: Работа с потоками (Java Streams) - простое использование.
 * <p>
 * Описание: Отфильтровать список, преобразовать элементы, посчитать сумму
 * с помощью Stream API. (Проверяет: основы Stream API)
 * <p>
 * Задание 1: Дан `List<String> strings`. Используя Stream API, напишите код,
 * который отфильтрует строки, начинающиеся на "a" (игнорируя регистр),
 * преобразует их в верхний регистр, и соберет результат в новый `List<String>`.
 * Пример: `["apple", "banana", "apricot", "cherry", "kiwi"]` -> `["APPLE", "APRICOT"]`.
 * <p>
 * Задание 2: Дан `List<Integer> numbers`. Используя Stream API, напишите код,
 * который вычисляет сумму квадратов четных чисел в списке.
 * Пример: `[1, 2, 3, 4, 5]` -> `4 + 16 = 20`.
 */
public class JavaStreamsSimple {

    /**
     * Фильтрует список строк, оставляя те, что начинаются с "a" (игнорируя регистр),
     * преобразует их в верхний регистр и собирает в новый список.
     * Демонстрирует: `stream()`, `filter()`, `map()`, `collect()`.
     *
     * @param strings Исходный список строк. Может быть null или содержать null элементы.
     * @return Новый список отфильтрованных и преобразованных строк.
     * Возвращает пустой список, если исходный список null/пуст или не содержит подходящих строк.
     */
    public List<String> filterAndTransformStrings(List<String> strings) {
        // Обработка null входного списка
        if (strings == null) {
            return Collections.emptyList(); // Возвращаем неизменяемый пустой список
        }

        return strings.stream()             // 1. Получаем Stream<String>
                .filter(Objects::nonNull)   // 2. Убираем null строки
                // 3. Фильтруем: оставляем строки, начинающиеся на 'a' или 'A'
                .filter(s -> s.toLowerCase().startsWith("a"))
                // 4. Преобразуем каждую отфильтрованную строку в верхний регистр
                .map(String::toUpperCase)   // Эквивалентно s -> s.toUpperCase()
                // 5. Собираем результаты в новый List
                .collect(Collectors.toList());
    }

    /**
     * Вычисляет сумму квадратов четных чисел в списке Integer.
     * Демонстрирует: `stream()`, `filter()`, `mapToInt()`, `sum()`.
     *
     * @param numbers Список целых чисел. Может быть null или содержать null элементы.
     * @return Сумма квадратов четных чисел (тип long, чтобы избежать переполнения int).
     * Возвращает 0 для null/пустого списка.
     */
    public long sumSquareOfEvens(List<Integer> numbers) {
        if (numbers == null) {
            return 0L; // Возвращаем long 0
        }
        return numbers.stream()                       // Stream<Integer>
                .filter(Objects::nonNull)       // Убираем null элементы
                .filter(n -> n % 2 == 0)        // Оставляем только четные числа
                // Преобразуем в поток примитивов long, чтобы избежать переполнения при возведении в квадрат
                .mapToLong(n -> (long) n * n)    // n*n может переполнить int, поэтому приводим к long
                .sum();                         // Суммируем элементы LongStream
    }

    /**
     * Точка входа для демонстрации работы с Java Streams.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        JavaStreamsSimple sol = new JavaStreamsSimple();

        System.out.println("--- Java Streams Simple Examples ---");

        // Пример 1: Строки
        List<String> strings = Arrays.asList("Apple", "Banana", "Apricot", null, "Cherry", "Avocado", "ant", " B", "a");
        runStringStreamTest(sol, strings, "String filtering and transformation");

        // Пример 2: Числа
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, null, 8, -2, -4, Integer.MAX_VALUE); // Добавим MAX_VALUE (нечетное)
        runSumSquareTest(sol, numbers, "Sum of squares of evens");

        // Пример 3: Пустые/null списки
        runStringStreamTest(sol, new ArrayList<>(), "Empty string list");
        runStringStreamTest(sol, null, "Null string list");
        runSumSquareTest(sol, new ArrayList<>(), "Empty number list");
        runSumSquareTest(sol, null, "Null number list");
    }

    /**
     * Вспомогательный метод для тестирования filterAndTransformStrings.
     *
     * @param sol         Экземпляр решателя.
     * @param list        Исходный список строк.
     * @param description Описание теста.
     */
    private static void runStringStreamTest(JavaStreamsSimple sol, List<String> list, String description) {
        System.out.println("\n--- " + description + " ---");
        String input = (list == null ? "null" : list.toString());
        System.out.println("Input list: " + input);
        try {
            List<String> result = sol.filterAndTransformStrings(list);
            System.out.println("Result list: " + result);
        } catch (Exception e) {
            System.err.println("Result list: Error - " + e.getMessage());
        }
    }

    /**
     * Вспомогательный метод для тестирования sumSquareOfEvens.
     *
     * @param sol         Экземпляр решателя.
     * @param list        Исходный список чисел.
     * @param description Описание теста.
     */
    private static void runSumSquareTest(JavaStreamsSimple sol, List<Integer> list, String description) {
        System.out.println("\n--- " + description + " ---");
        String input = (list == null ? "null" : list.toString());
        System.out.println("Input list: " + input);
        try {
            long result = sol.sumSquareOfEvens(list);
            System.out.println("Result sum: " + result);
        } catch (Exception e) {
            System.err.println("Result sum: Error - " + e.getMessage());
        }
    }
}
