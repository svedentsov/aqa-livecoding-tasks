package com.svedentsov.aqa.tasks.algorithms;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Решение задачи №86: Работа с потоками (Java Streams) - простое использование.
 * Описание: Отфильтровать список, преобразовать элементы, посчитать сумму
 * с помощью Stream API. (Проверяет: основы Stream API)
 * Задание 1: Дан `List<String> strings`. Используя Stream API, напишите код,
 * который отфильтрует строки, начинающиеся на "a" (игнорируя регистр),
 * преобразует их в верхний регистр, и соберет результат в новый `List<String>`.
 * Пример: `["apple", "banana", "apricot", "cherry", "kiwi"]` -> `["APPLE", "APRICOT"]`.
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

        return strings.stream() // 1. Получаем Stream<String>
                .filter(Objects::nonNull) // 2. Убираем null строки из потока
                // 3. Фильтруем: оставляем строки, начинающиеся на 'a' или 'A'
                .filter(s -> s.trim().toLowerCase().startsWith("a"))
                // 4. Преобразуем каждую отфильтрованную строку в верхний регистр
                .map(String::toUpperCase) // Эквивалентно s -> s.toUpperCase()
                // 5. Собираем результаты в новый List
                .collect(Collectors.toList());
    }

    /**
     * Вычисляет сумму квадратов четных чисел в списке Integer.
     * Демонстрирует: `stream()`, `filter()`, `mapToLong()`, `sum()`.
     *
     * @param numbers Список целых чисел. Может быть null или содержать null элементы.
     * @return Сумма квадратов четных чисел (тип long, чтобы избежать переполнения int).
     * Возвращает 0L для null/пустого списка или если нет четных чисел.
     */
    public long sumSquareOfEvens(List<Integer> numbers) {
        if (numbers == null) {
            return 0L; // Возвращаем long 0
        }
        return numbers.stream() // Stream<Integer>
                .filter(Objects::nonNull) // Убираем null элементы из потока
                .filter(n -> n % 2 == 0) // Оставляем только четные числа
                // Преобразуем в поток примитивов long, чтобы избежать переполнения при возведении в квадрат
                .mapToLong(n -> (long) n * n) // n*n может переполнить int, поэтому (long)n * n
                .sum(); // Суммируем элементы LongStream
    }
}
