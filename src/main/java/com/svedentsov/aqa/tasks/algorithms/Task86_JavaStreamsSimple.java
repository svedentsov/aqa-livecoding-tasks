package com.svedentsov.aqa.tasks.algorithms; // или collections

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Решение задачи №86: Работа с потоками (Java Streams) - простое использование.
 */
public class Task86_JavaStreamsSimple {

    /**
     * Фильтрует список строк, оставляя те, что начинаются с "a" (игнорируя регистр и null),
     * преобразует их в верхний регистр и собирает в новый список.
     * Демонстрирует цепочку операций Stream API: filter, map, collect.
     *
     * @param strings Исходный список строк. Может быть null или содержать null элементы.
     * @return Новый список отфильтрованных и преобразованных строк.
     * Возвращает пустой список, если исходный список null/пуст или не содержит подходящих строк.
     */
    public List<String> filterAndTransformStrings(List<String> strings) {
        // Обработка null на входе
        if (strings == null) {
            return new ArrayList<>(); // Возвращаем пустой список
        }

        return strings.stream()             // 1. Создаем Stream<String> из списка.
                .filter(Objects::nonNull)   // 2. Промежуточная операция: убираем null элементы.
                .filter(s -> s.toLowerCase().startsWith("a")) // 3. Промежуточная: фильтруем строки, начинающиеся на 'a' (игнорируя регистр).
                .map(String::toUpperCase)   // 4. Промежуточная: преобразуем каждую строку в верхний регистр.
                .collect(Collectors.toList()); // 5. Терминальная: собираем результат в новый List.
    }

    /**
     * Вычисляет сумму квадратов четных чисел в списке Integer.
     * Демонстрирует filter, mapToInt (для работы с примитивным int потоком) и sum.
     *
     * @param numbers Список целых чисел. Может быть null или содержать null элементы.
     * @return Сумма квадратов четных чисел. Возвращает 0 для null/пустого списка.
     */
    public int sumSquareOfEvens(List<Integer> numbers) {
        if (numbers == null) {
            return 0;
        }
        return numbers.stream()                       // Stream<Integer>
                .filter(Objects::nonNull)       // Оставляем только не-null числа
                .filter(n -> n % 2 == 0)        // Оставляем только четные
                .mapToInt(n -> n * n)           // Преобразуем в IntStream квадратов
                .sum();                          // Суммируем элементы IntStream
    }

    /**
     * Точка входа для демонстрации работы с Java Streams.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task86_JavaStreamsSimple sol = new Task86_JavaStreamsSimple();

        // Пример 1: Фильтрация и преобразование строк
        List<String> strings = Arrays.asList("Apple", "Banana", "Apricot", null, "Cherry", "Avocado", "ant");
        System.out.println("Original strings: " + strings);
        List<String> resultStrings = sol.filterAndTransformStrings(strings);
        System.out.println("Filtered (start with 'a', ignore case) and transformed to upper case: " + resultStrings);
        // Ожидаемый результат: [APPLE, APRICOT, AVOCADO, ANT]

        // Пример 2: Сумма квадратов четных чисел
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, null, 8, -2, -4);
        System.out.println("\nOriginal numbers: " + numbers);
        int resultSum = sol.sumSquareOfEvens(numbers);
        // 2*2 + 4*4 + 6*6 + 8*8 + (-2)*(-2) + (-4)*(-4) = 4 + 16 + 36 + 64 + 4 + 16 = 140
        System.out.println("Sum of squares of evens: " + resultSum); // 140

        // Пример с пустым списком
        List<String> emptyList = new ArrayList<>();
        System.out.println("\nOriginal empty list: " + emptyList);
        System.out.println("Filtered empty list: " + sol.filterAndTransformStrings(emptyList)); // []
        System.out.println("Sum for empty list: " + sol.sumSquareOfEvens(null)); // 0
    }
}
