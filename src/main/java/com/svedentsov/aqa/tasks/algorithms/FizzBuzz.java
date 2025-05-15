package com.svedentsov.aqa.tasks.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Решение задачи №1: FizzBuzz.
 * Описание: Генерирует последовательность строк от 1 до N.
 * Для чисел, кратных 3, строка заменяется на "Fizz", для чисел, кратных 5 - на "Buzz", а для чисел, кратных и 3, и 5 - на "FizzBuzz".
 * Пример: `generateFizzBuzz(15)` должен вернуть список строк, эквивалентный: `List.of("1", "2", "Fizz", "4",
 * "Buzz", "Fizz", "7", "8", "Fizz", "Buzz", "11", "Fizz", "13", "14", "FizzBuzz")`.
 */
public class FizzBuzz {

    /**
     * Генерирует последовательность FizzBuzz от 1 до n.
     *
     * @param n Верхняя граница последовательности (включительно). Должно быть >= 1.
     * @return Список строк (List<String>), представляющий последовательность FizzBuzz.
     * Возвращает пустой список, если n < 1.
     */
    public List<String> generateFizzBuzz(int n) {
        if (n < 1) {
            return Collections.emptyList(); // Возвращаем пустой список для некорректного ввода
        }

        List<String> result = new ArrayList<>(n); // Задаем начальную емкость для эффективности
        for (int i = 1; i <= n; i++) {
            result.add(getFizzBuzzValue(i));
        }
        return result;
    }

    /**
     * Возвращает строковое представление FizzBuzz для одного числа.
     *
     * @param number Число для проверки.
     * @return "FizzBuzz", "Fizz", "Buzz" или строковое представление числа.
     */
    private String getFizzBuzzValue(int number) {
        boolean divisibleBy3 = (number % 3 == 0);
        boolean divisibleBy5 = (number % 5 == 0);

        if (divisibleBy3 && divisibleBy5) {
            return "FizzBuzz";
        } else if (divisibleBy3) {
            return "Fizz";
        } else if (divisibleBy5) {
            return "Buzz";
        } else {
            return String.valueOf(number); // Преобразуем число в строку
        }
    }
}
