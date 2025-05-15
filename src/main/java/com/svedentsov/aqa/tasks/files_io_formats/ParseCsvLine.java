package com.svedentsov.aqa.tasks.files_io_formats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Решение задачи №50: Написать простой парсер CSV строки.
 * Описание: Разбить строку с запятыми-разделителями на поля, учитывая кавычки.
 * (Проверяет: работа со строками, логика)
 * Задание: Напишите метод `List<String> parseCsvLine(String csvLine)`, который
 * разбирает строку `csvLine`, представляющую одну строку CSV-файла, на
 * отдельные поля. Учитывайте, что поля могут быть заключены в двойные
 * кавычки, и внутри таких полей могут встречаться запятые. Также обрабатывайте
 * экранированные кавычки внутри поля (две кавычки подряд `""` внутри
 * кавычек означают одну кавычку).
 * Пример: `parseCsvLine("John,Doe,30")` -> `["John", "Doe", "30"]`.
 * `parseCsvLine("\"Smith, John\",\"New York, NY\",45")` -> `["Smith, John", "New York, NY", "45"]`.
 * `parseCsvLine("Field1,\"Field \"\"with\"\" quotes\",Field3")` -> `["Field1", "Field \"with\" quotes", "Field3"]`.
 */
public class ParseCsvLine {

    /**
     * Разбирает одну строку в формате CSV (Comma-Separated Values) на список полей (строк).
     * Учитывает поля, заключенные в двойные кавычки ("), внутри которых могут быть запятые.
     * Обрабатывает экранированные двойные кавычки ("") внутри закавыченных полей как
     * одну обычную кавычку (").
     * Это простая реализация конечного автомата (state machine) для разбора строки.
     *
     * @param csvLine Строка CSV для разбора. Может быть null.
     * @return Список строк, представляющих поля CSV. Возвращает пустой список для null или пустой строки.
     * @throws IllegalArgumentException если формат кавычек нарушен (например, непарная кавычка).
     */
    public List<String> parseCsvLine(String csvLine) {
        if (csvLine == null) {
            return Collections.emptyList(); // Возвращаем неизменяемый пустой список
        }
        // Пустая строка должна парситься как список с одним пустым полем,
        // если строка действительно пустая, или как пустой список, если мы не хотим такого поведения.
        // Текущая логика (добавление последнего поля в конце) даст [""].
        // Изменим, чтобы для "" возвращался пустой список, как и для null.
        if (csvLine.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> fields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean inQuotes = false; // Находимся ли внутри кавычек?
        int n = csvLine.length();

        for (int i = 0; i < n; i++) {
            char c = csvLine.charAt(i);

            if (inQuotes) {
                // ----- Состояние: Внутри кавычек -----
                if (c == '"') {
                    // Возможный конец поля или экранированная кавычка
                    if (i + 1 < n && csvLine.charAt(i + 1) == '"') {
                        // Это экранированная кавычка ""
                        currentField.append('"');
                        i++; // Пропускаем вторую кавычку
                    } else {
                        // Это закрывающая кавычка поля
                        inQuotes = false;
                        // Проверяем, что за кавычкой идет запятая или конец строки
                        if (i + 1 < n && csvLine.charAt(i + 1) != ',') {
                            throw new IllegalArgumentException("Character after closing quote must be comma or end-of-line at index " + (i + 1));
                        }
                    }
                } else {
                    // Любой другой символ внутри кавычек добавляется как есть
                    currentField.append(c);
                }
            } else {
                // ----- Состояние: Вне кавычек -----
                if (c == '"') {
                    // Начало закавыченного поля. Оно должно быть в начале текущего поля.
                    if (currentField.length() == 0) {
                        inQuotes = true;
                    } else {
                        // Кавычка не в начале незакавыченного поля - ошибка формата
                        throw new IllegalArgumentException("Unexpected quote character inside an unquoted field at index " + i);
                    }
                } else if (c == ',') {
                    // Разделитель полей
                    fields.add(currentField.toString());
                    currentField.setLength(0); // Сбрасываем для следующего поля
                } else {
                    // Обычный символ незакавыченного поля
                    currentField.append(c);
                }
            }
        } // Конец цикла for

        // Проверка незакрытой кавычки в конце
        if (inQuotes) {
            throw new IllegalArgumentException("Unclosed quote at the end of the line.");
        }

        // Добавляем последнее поле
        fields.add(currentField.toString());

        return fields;
    }
}
