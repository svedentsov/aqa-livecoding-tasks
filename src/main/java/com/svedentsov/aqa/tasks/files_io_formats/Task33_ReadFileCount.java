package com.svedentsov.aqa.tasks.files_io_formats;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * Решение задачи №33: Чтение файла и подсчет строк/слов.
 */
public class Task33_ReadFileCount {

    /**
     * Подсчитывает количество строк в текстовом файле, используя {@link BufferedReader}.
     * Использует конструкцию try-with-resources для автоматического закрытия файла.
     *
     * @param filePath Путь к файлу в виде строки.
     * @return Количество строк в файле.
     * @throws IOException          Если возникает ошибка при доступе или чтении файла
     *                              (например, файл не найден, нет прав доступа).
     * @throws NullPointerException если filePath равен null.
     */
    public long countLinesBufferedReader(String filePath) throws IOException {
        if (filePath == null) {
            throw new NullPointerException("File path cannot be null.");
        }
        long lineCount = 0;
        // try-with-resources гарантирует, что reader будет закрыт
        // FileReader использует кодировку по умолчанию, что может быть не всегда хорошо.
        // Лучше использовать Files.newBufferedReader(Paths.get(filePath), StandardCharsets.UTF_8)
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            // readLine() читает одну строку, возвращает null в конце файла
            while (reader.readLine() != null) {
                lineCount++;
            }
        } // reader.close() вызывается здесь автоматически
        return lineCount;
    }

    /**
     * Подсчитывает количество строк в текстовом файле, используя {@link Files#lines(Path)} (NIO).
     * Более современный и лаконичный подход с использованием Stream API.
     * Также использует try-with-resources для управления ресурсами потока.
     *
     * @param filePath Путь к файлу в виде строки.
     * @return Количество строк в файле.
     * @throws IOException          Если возникает ошибка при доступе или чтении файла.
     * @throws NullPointerException если filePath равен null.
     */
    public long countLinesNio(String filePath) throws IOException {
        if (filePath == null) {
            throw new NullPointerException("File path cannot be null.");
        }
        Path path = Paths.get(filePath);
        // Files.lines(...) возвращает Stream<String>
        // try-with-resources нужен для закрытия базового файлового потока
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) { // Указываем кодировку
            return lines.count(); // Метод count() терминальной операции потока
        }
    }

    /**
     * Подсчитывает количество слов в текстовом файле.
     * Читает файл построчно и разбивает каждую непустую строку на слова по пробельным символам.
     *
     * @param filePath Путь к файлу.
     * @return Общее количество слов в файле.
     * @throws IOException          Если возникает ошибка при доступе или чтении файла.
     * @throws NullPointerException если filePath равен null.
     */
    public long countWordsInFile(String filePath) throws IOException {
        if (filePath == null) {
            throw new NullPointerException("File path cannot be null.");
        }
        long wordCount = 0;
        // Используем Files.lines для более лаконичного чтения
        try (Stream<String> lines = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            wordCount = lines
                    .map(String::trim)           // Убираем пробелы по краям
                    .filter(line -> !line.isEmpty()) // Пропускаем пустые строки
                    // Разделяем на слова и считаем их количество в каждой строке
                    .mapToLong(line -> line.split("\\s+").length)
                    .sum(); // Суммируем количество слов со всех строк
        }
        return wordCount;
         /* // Альтернатива с BufferedReader
         try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath), StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                 String trimmedLine = line.trim();
                 if (!trimmedLine.isEmpty()) {
                     wordCount += trimmedLine.split("\\s+").length;
                 }
            }
        }
        return wordCount;
         */
    }

    /**
     * Точка входа для демонстрации работы методов подсчета строк и слов.
     * Создает временный файл для тестов.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task33_ReadFileCount sol = new Task33_ReadFileCount();
        String testFilename = "temp_data_task33.txt";

        // Создаем временный файл с тестовыми данными
        try {
            List<String> linesToWrite = List.of(
                    "Первая строка для примера.",
                    "Вторая строка    с несколькими   пробелами.",
                    "", // Пустая строка
                    "Третья строка.",
                    "И последняя."
            );
            Files.write(Paths.get(testFilename), linesToWrite, StandardCharsets.UTF_8);
            System.out.println("Создан тестовый файл: " + testFilename);
        } catch (IOException e) {
            System.err.println("Ошибка создания тестового файла: " + e.getMessage());
            return; // Прекращаем выполнение
        }

        // Выполняем подсчеты
        try {
            System.out.println("\n--- Подсчет строк ---");
            long linesBR = sol.countLinesBufferedReader(testFilename);
            System.out.println("Строки (BufferedReader): " + linesBR); // Ожидается 5

            long linesNIO = sol.countLinesNio(testFilename);
            System.out.println("Строки (NIO): " + linesNIO);         // Ожидается 5

            System.out.println("\n--- Подсчет слов ---");
            long words = sol.countWordsInFile(testFilename);
            // 4 + 5 + 0 + 2 + 2 = 13
            System.out.println("Слова в файле: " + words);        // Ожидается 13

        } catch (IOException e) {
            System.err.println("Ошибка чтения файла '" + testFilename + "': " + e.getMessage());
        } finally {
            // Удаляем временный файл
            try {
                Files.deleteIfExists(Paths.get(testFilename));
                System.out.println("\nУдален тестовый файл: " + testFilename);
            } catch (IOException e) {
                System.err.println("Ошибка удаления тестового файла: " + e.getMessage());
            }
        }

        // Пример с несуществующим файлом
        try {
            sol.countLinesNio("non_existent_file_12345.txt");
        } catch (IOException e) {
            System.err.println("\nОжидаемая ошибка для несуществующего файла: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
        // Пример с null путем
        try {
            sol.countLinesNio(null);
        } catch (NullPointerException | IOException e) {
            System.err.println("Ожидаемая ошибка для null пути: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }
}
