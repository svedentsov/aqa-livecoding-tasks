package com.svedentsov.aqa.tasks.files_io_formats;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Решение задачи №33: Чтение файла и подсчет строк/слов.
 * <p>
 * Описание: Написать код, который читает текстовый файл и выводит
 * количество строк или слов. (Проверяет: основы I/O, обработка исключений)
 * <p>
 * Задание: Напишите метод `int countLines(String filePath)`, который читает
 * текстовый файл по пути `filePath` и возвращает количество строк в нем.
 * Обработайте возможные `IOException`. (На собеседовании могут попросить
 * использовать `try-with-resources`). Дополнительно: метод для подсчета слов.
 * <p>
 * Пример: Если файл `data.txt` содержит 3 строки, `countLines("data.txt")` -> `3`.
 */
public class ReadFileCount {

    /**
     * Подсчитывает количество строк в текстовом файле, используя {@link BufferedReader}.
     * Использует конструкцию try-with-resources для автоматического закрытия файла.
     * Предпочтительно указывать кодировку (здесь UTF-8).
     *
     * @param filePath Путь к файлу в виде строки.
     * @return Количество строк в файле.
     * @throws IOException          Если возникает ошибка при доступе или чтении файла
     *                              (например, файл не найден, нет прав доступа).
     * @throws NullPointerException если filePath равен null.
     */
    public long countLinesBufferedReader(String filePath) throws IOException {
        Objects.requireNonNull(filePath, "File path cannot be null."); // Проверка на null

        long lineCount = 0;
        // try-with-resources гарантирует закрытие reader
        // Используем Files.newBufferedReader для явного указания кодировки
        Path path = Paths.get(filePath);
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
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
     * Требует try-with-resources для управления ресурсами базового потока файла.
     *
     * @param filePath Путь к файлу в виде строки.
     * @return Количество строк в файле.
     * @throws IOException          Если возникает ошибка при доступе или чтении файла.
     * @throws NullPointerException если filePath равен null.
     */
    public long countLinesNio(String filePath) throws IOException {
        Objects.requireNonNull(filePath, "File path cannot be null.");
        Path path = Paths.get(filePath);
        // Files.lines(...) возвращает Stream<String>
        // try-with-resources нужен для закрытия потока
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) { // Указываем кодировку
            return lines.count(); // Метод count() терминальной операции потока
        }
    }

    /**
     * Подсчитывает количество слов в текстовом файле.
     * Словами считаются последовательности непробельных символов, разделенные
     * одним или несколькими пробельными символами (`\\s+`).
     * Пустые строки и строки, состоящие только из пробелов, игнорируются.
     *
     * @param filePath Путь к файлу.
     * @return Общее количество слов в файле.
     * @throws IOException          Если возникает ошибка при доступе или чтении файла.
     * @throws NullPointerException если filePath равен null.
     */
    public long countWordsInFile(String filePath) throws IOException {
        Objects.requireNonNull(filePath, "File path cannot be null.");
        long wordCount = 0;
        // Используем Files.lines для чтения файла построчно
        Path path = Paths.get(filePath);
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            wordCount = lines
                    .map(String::trim)           // Убираем пробелы по краям каждой строки
                    .filter(line -> !line.isEmpty()) // Пропускаем строки, ставшие пустыми после trim
                    // Разделяем каждую непустую строку на слова и считаем их количество
                    .mapToLong(line -> line.split("\\s+").length)
                    .sum(); // Суммируем количество слов со всех строк
        }
        return wordCount;
    }

    /**
     * Точка входа для демонстрации работы методов подсчета строк и слов.
     * Создает временный файл для тестов.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        ReadFileCount sol = new ReadFileCount();
        String testFilename = "temp_data_task33.txt";

        // 1. Создание временного файла
        if (!createTestFile(testFilename)) {
            return; // Прерываем выполнение, если файл не создан
        }

        // 2. Выполнение тестов
        System.out.println("--- Testing File Reading and Counting ---");
        System.out.println("Processing file: " + testFilename);
        try {
            long linesBR = sol.countLinesBufferedReader(testFilename);
            System.out.println("Line count (BufferedReader): " + linesBR); // Ожидается 5

            long linesNIO = sol.countLinesNio(testFilename);
            System.out.println("Line count (NIO Stream)  : " + linesNIO); // Ожидается 5

            long words = sol.countWordsInFile(testFilename);
            // 4 + 5 + 0 + 2 + 2 = 13
            System.out.println("Word count (NIO Stream)  : " + words); // Ожидается 13

        } catch (IOException e) {
            System.err.println("Error reading file '" + testFilename + "': " + e.getMessage());
        }

        // 3. Удаление временного файла
        deleteTestFile(testFilename);

        // 4. Тесты с некорректными путями
        testErrorCases(sol);
    }

    /**
     * Создает временный текстовый файл с заданным содержимым.
     *
     * @param filename Имя файла для создания.
     * @return true в случае успеха, false при ошибке.
     */
    private static boolean createTestFile(String filename) {
        List<String> linesToWrite = List.of(
                "Первая строка для примера.",
                "Вторая строка    с несколькими   пробелами.",
                "", // Пустая строка
                "Третья строка.",
                "И последняя."
        );
        Path path = Paths.get(filename);
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            for (String line : linesToWrite) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Created test file: " + filename);
            return true;
        } catch (IOException e) {
            System.err.println("Error creating test file '" + filename + "': " + e.getMessage());
            return false;
        }
    }

    /**
     * Удаляет указанный файл, если он существует.
     *
     * @param filename Имя файла для удаления.
     */
    private static void deleteTestFile(String filename) {
        try {
            boolean deleted = Files.deleteIfExists(Paths.get(filename));
            if (deleted) {
                System.out.println("\nDeleted test file: " + filename);
            } else {
                System.out.println("\nTest file not found for deletion: " + filename);
            }
        } catch (IOException e) {
            System.err.println("Error deleting test file '" + filename + "': " + e.getMessage());
        }
    }

    /**
     * Тестирует обработку ошибок для некорректных путей.
     *
     * @param sol Экземпляр решателя.
     */
    private static void testErrorCases(ReadFileCount sol) {
        System.out.println("\n--- Testing Error Cases ---");
        String nonExistentFile = "non_existent_file_" + System.currentTimeMillis() + ".txt";
        String nullPath = null;

        // Несуществующий файл
        try {
            System.out.print("countLinesNio(\"" + nonExistentFile + "\"): ");
            sol.countLinesNio(nonExistentFile);
        } catch (IOException e) {
            System.out.println("Caught expected error - " + e.getClass().getSimpleName());
        } catch (Exception e) {
            System.out.println("Caught unexpected error - " + e.getMessage());
        }

        // Null путь
        try {
            System.out.print("countWordsInFile(null): ");
            sol.countWordsInFile(nullPath);
        } catch (NullPointerException e) {
            System.out.println("Caught expected error - " + e.getClass().getSimpleName());
        } catch (Exception e) {
            System.out.println("Caught unexpected error - " + e.getMessage());
        }
    }
}
