package com.svedentsov.aqa.tasks.files_io_formats;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Решение задачи №33: Чтение файла и подсчет строк/слов.
 * Описание: Написать код, который читает текстовый файл и выводит
 * количество строк или слов. (Проверяет: основы I/O, обработка исключений)
 * Задание: Напишите метод `long countLines(String filePath)`, который читает
 * текстовый файл по пути `filePath` и возвращает количество строк в нем.
 * Обработайте возможные `IOException`. (На собеседовании могут попросить
 * использовать `try-with-resources`). Дополнительно: метод для подсчета слов.
 * Пример: Если файл `data.txt` содержит 3 строки, `countLines("data.txt")` -> `3`.
 */
public class ReadFileCount {

    /**
     * Подсчитывает количество строк в текстовом файле, используя {@link BufferedReader}.
     * Использует конструкцию try-with-resources для автоматического закрытия файла.
     *
     * @param filePath Путь к файлу в виде строки.
     * @return Количество строк в файле (тип long).
     * @throws IOException          Если возникает ошибка при доступе или чтении файла.
     * @throws NullPointerException если filePath равен null.
     */
    public long countLinesBufferedReader(String filePath) throws IOException {
        Objects.requireNonNull(filePath, "File path cannot be null."); // Проверка на null
        long lineCount = 0;
        Path path = Paths.get(filePath);
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            while (reader.readLine() != null) {
                lineCount++;
            }
        }
        return lineCount;
    }

    /**
     * Подсчитывает количество строк в текстовом файле, используя {@link Files#lines(Path)} (NIO).
     *
     * @param filePath Путь к файлу в виде строки.
     * @return Количество строк в файле (тип long).
     * @throws IOException          Если возникает ошибка при доступе или чтении файла.
     * @throws NullPointerException если filePath равен null.
     */
    public long countLinesNio(String filePath) throws IOException {
        Objects.requireNonNull(filePath, "File path cannot be null.");
        Path path = Paths.get(filePath);
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            return lines.count();
        }
    }

    /**
     * Подсчитывает количество слов в текстовом файле.
     * Словами считаются последовательности непробельных символов, разделенные
     * одним или несколькими пробельными символами (`\\s+`).
     * Пустые строки и строки, состоящие только из пробелов, игнорируются.
     *
     * @param filePath Путь к файлу.
     * @return Общее количество слов в файле (тип long).
     * @throws IOException          Если возникает ошибка при доступе или чтении файла.
     * @throws NullPointerException если filePath равен null.
     */
    public long countWordsInFile(String filePath) throws IOException {
        Objects.requireNonNull(filePath, "File path cannot be null.");
        long wordCount;
        Path path = Paths.get(filePath);
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            wordCount = lines
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    // Разделяем каждую непустую строку на слова и считаем их количество
                    .mapToLong(line -> line.split("\\s+").length)
                    .sum(); // Суммируем количество слов со всех строк
        }
        return wordCount;
    }
}
