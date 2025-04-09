package com.svedentsov.aqa.tasks.files_io_formats;

import java.util.ArrayList;
import java.util.List;

/**
 * Решение задачи №50: Написать простой парсер CSV строки.
 */
public class Task50_ParseCsvLine {

    /**
     * Разбирает одну строку в формате CSV (Comma-Separated Values) на список полей (строк).
     * Учитывает поля, заключенные в двойные кавычки ("), внутри которых могут быть запятые.
     * Обрабатывает экранированные двойные кавычки ("") внутри закавыченных полей как
     * одну обычную кавычку (").
     * Это простая реализация, которая может не покрывать все крайние случаи стандарта RFC 4180
     * (например, переносы строк внутри полей), но демонстрирует основную логику.
     *
     * @param csvLine Строка CSV для разбора. Может быть null.
     * @return Список строк, представляющих поля CSV. Возвращает пустой список для null или пустой строки.
     * @throws IllegalArgumentException если формат кавычек нарушен (например, непарная кавычка в конце).
     */
    public List<String> parseCsvLine(String csvLine) {
        List<String> fields = new ArrayList<>();
        if (csvLine == null || csvLine.isEmpty()) {
            return fields; // Возвращаем пустой список для null или пустой строки
        }

        StringBuilder currentField = new StringBuilder(); // Для накопления символов текущего поля
        boolean inQuotes = false; // Флаг: находимся ли мы внутри двойных кавычек
        int i = 0;
        int n = csvLine.length();

        while (i < n) {
            char c = csvLine.charAt(i);

            if (inQuotes) {
                // --- Находимся ВНУТРИ кавычек ---
                if (c == '"') {
                    // Встретили кавычку внутри кавычек. Проверяем следующую.
                    if (i + 1 < n && csvLine.charAt(i + 1) == '"') {
                        // Это экранированная кавычка (""). Добавляем одну кавычку в поле.
                        currentField.append('"');
                        i++; // Пропускаем вторую кавычку из пары "".
                    } else {
                        // Это закрывающая кавычка для поля. Выходим из режима кавычек.
                        inQuotes = false;
                        // Сама закрывающая кавычка в поле не добавляется.
                    }
                } else {
                    // Любой другой символ внутри кавычек добавляется как есть.
                    currentField.append(c);
                }
            } else {
                // --- Находимся ВНЕ кавычек ---
                if (c == '"') {
                    // Встретили открывающую кавычку.
                    // По стандарту, она должна быть в начале поля (после запятой или в начале строки).
                    // Если currentField не пуст, это может быть кавычка внутри незакавыченного поля
                    // (что формально не по стандарту, но некоторые парсеры допускают).
                    // Здесь мы будем считать, что кавычка может начинать поле, только если currentField пуст.
                    if (currentField.length() == 0) {
                        inQuotes = true; // Входим в режим кавычек.
                        // Сама открывающая кавычка не добавляется.
                    } else {
                        // Кавычка в середине незакавыченного поля - добавляем как обычный символ.
                        currentField.append(c);
                        // Или можно бросить исключение о неверном формате.
                        // throw new IllegalArgumentException("Quote found inside unquoted field at index " + i);
                    }
                } else if (c == ',') {
                    // Запятая вне кавычек - это разделитель полей.
                    fields.add(currentField.toString()); // Добавляем собранное поле в список.
                    currentField.setLength(0);           // Очищаем StringBuilder для следующего поля.
                } else {
                    // Любой другой символ вне кавычек добавляется в текущее поле.
                    currentField.append(c);
                }
            }
            i++; // Переходим к следующему символу строки
        } // конец while

        // Проверка на незакрытую кавычку в самом конце строки
        if (inQuotes) {
            throw new IllegalArgumentException("CSV line ends with an unclosed quote.");
        }

        // Добавляем последнее собранное поле в список после окончания строки
        fields.add(currentField.toString());

        return fields;
    }

    /**
     * Точка входа для демонстрации работы парсера CSV строки.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task50_ParseCsvLine sol = new Task50_ParseCsvLine();
        String[] testLines = {
                "John,Doe,30",                          // Простое
                "\"Smith, John\",\"New York, NY\",45",      // Запятые внутри кавычек
                "Field1,\"Field \"\"with\"\" quotes\",Field3", // Экранированные кавычки
                "one,two,,four",                      // Пустое поле
                "a,b,\"c\"",                           // Последнее поле в кавычках
                "a,b,",                               // Пустое последнее поле
                "",                                   // Пустая строка
                "\"\"",                               // Пустое поле в кавычках
                "single",                             // Одно поле
                "\"quoted, field\"",                  // Одно поле в кавычках с запятой
                "field1,\"unclosed quote",            // Невалидная: незакрытая кавычка
                "field1, \" a \" ,field3 "            // Пробелы вокруг полей (сохраняются)
        };
        List<List<String>> expectedResults = List.of(
                List.of("John", "Doe", "30"),
                List.of("Smith, John", "New York, NY", "45"),
                List.of("Field1", "Field \"with\" quotes", "Field3"),
                List.of("one", "two", "", "four"),
                List.of("a", "b", "c"),
                List.of("a", "b", ""),
                List.of(""), // Пустая строка парсится как одно пустое поле
                List.of(""), // "" парсится как одно пустое поле
                List.of("single"),
                List.of("quoted, field"),
                null, // Ожидаем исключение
                List.of("field1", " a ", "field3 ") // Пробелы сохраняются, если не делать trim() полей
        );


        for (int i = 0; i < testLines.length; i++) {
            String line = testLines[i];
            List<String> expected = (expectedResults.get(i) == null) ? null : expectedResults.get(i);
            System.out.println("\nParsing: '" + line + "'");
            try {
                List<String> result = sol.parseCsvLine(line);
                System.out.println("Result:   " + result);
                if (expected != null) {
                    boolean match = result.equals(expected);
                    System.out.println("Expected: " + expected);
                    if (!match) System.err.println("   MISMATCH!");
                } else {
                    System.err.println("   MISMATCH! Expected exception, but got result.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Caught exception: " + e.getMessage());
                if (expected != null) {
                    System.err.println("   MISMATCH! Expected result, but got exception.");
                } else {
                    System.out.println("   (Expected exception caught successfully)");
                }
            } catch (Exception e) {
                System.err.println("   Caught UNEXPECTED exception: " + e);
            }
        }
    }
}
