package com.svedentsov.aqa.tasks.files_io_formats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Тесты для ReadFileCount")
class ReadFileCountTest {

    private ReadFileCount fileCounter;

    // JUnit 5 предоставит временную директорию для тестов
    // Она будет автоматически создана до и удалена после тестов класса
    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        fileCounter = new ReadFileCount();
    }

    // --- Хелпер для создания тестового файла ---
    private Path createTestFile(String filename, List<String> lines) throws IOException {
        Path filePath = tempDir.resolve(filename);
        Files.write(filePath, lines, StandardCharsets.UTF_8);
        return filePath;
    }

    // --- Тесты для подсчета строк ---
    @Nested
    @DisplayName("Подсчет строк (countLines)")
    class LineCountingTests {

        @Test
        @DisplayName("Файл с несколькими строками, включая пустые")
        void shouldCountLinesInMultiLineFile() throws IOException {
            List<String> lines = Arrays.asList(
                    "Line 1",
                    "Line 2 with text",
                    "", // Пустая строка
                    "Line 4",
                    "    ", // Строка с пробелами
                    "Line 6"
            );
            Path testFile = createTestFile("multiLine.txt", lines);
            long expectedLines = lines.size(); // Ожидаем 6 строк

            assertEquals(expectedLines, fileCounter.countLinesBufferedReader(testFile.toString()), "BufferedReader count mismatch");
            assertEquals(expectedLines, fileCounter.countLinesNio(testFile.toString()), "NIO count mismatch");
        }

        @Test
        @DisplayName("Пустой файл")
        void shouldCountZeroLinesInEmptyFile() throws IOException {
            Path testFile = createTestFile("empty.txt", Collections.emptyList());
            long expectedLines = 0;

            assertEquals(expectedLines, fileCounter.countLinesBufferedReader(testFile.toString()), "BufferedReader count mismatch for empty file");
            assertEquals(expectedLines, fileCounter.countLinesNio(testFile.toString()), "NIO count mismatch for empty file");
        }

        @Test
        @DisplayName("Файл с одной строкой")
        void shouldCountOneLineInSingleLineFile() throws IOException {
            Path testFile = createTestFile("singleLine.txt", Collections.singletonList("Just one line"));
            long expectedLines = 1;

            assertEquals(expectedLines, fileCounter.countLinesBufferedReader(testFile.toString()), "BufferedReader count mismatch for single line file");
            assertEquals(expectedLines, fileCounter.countLinesNio(testFile.toString()), "NIO count mismatch for single line file");
        }

        @Test
        @DisplayName("Исключение для несуществующего файла")
        void shouldThrowIOExceptionForNonExistentFile() {
            String nonExistentPath = tempDir.resolve("non_existent.txt").toString();
            assertThrows(NoSuchFileException.class, () -> fileCounter.countLinesBufferedReader(nonExistentPath), "BufferedReader should throw for non-existent file");
            assertThrows(NoSuchFileException.class, () -> fileCounter.countLinesNio(nonExistentPath), "NIO should throw for non-existent file");
        }

        @Test
        @DisplayName("Исключение для null пути")
        void shouldThrowNullPointerExceptionForNullPath() {
            assertThrows(NullPointerException.class, () -> fileCounter.countLinesBufferedReader(null), "BufferedReader should throw for null path");
            assertThrows(NullPointerException.class, () -> fileCounter.countLinesNio(null), "NIO should throw for null path");
        }
    }


    // --- Тесты для подсчета слов ---
    @Nested
    @DisplayName("Подсчет слов (countWordsInFile)")
    class WordCountingTests {

        @Test
        @DisplayName("Файл с разным количеством слов и пробелов")
        void shouldCountWordsCorrectly() throws IOException {
            List<String> lines = Arrays.asList(
                    "Первая строка для примера.", // 4 слова
                    "Вторая строка    с несколькими   пробелами.", // 5 слов
                    "", // 0 слов
                    "Третья строка.", // 2 слова
                    "И последняя." // 2 слова
                    // Пустая строка в конце файла при записи через Files.write не создается, если последняя строка не пустая
            );
            Path testFile = createTestFile("wordsTest.txt", lines);
            long expectedWords = 4 + 5 + 0 + 2 + 2; // = 13

            assertEquals(expectedWords, fileCounter.countWordsInFile(testFile.toString()));
        }

        @Test
        @DisplayName("Пустой файл")
        void shouldCountZeroWordsInEmptyFile() throws IOException {
            Path testFile = createTestFile("emptyWords.txt", Collections.emptyList());
            assertEquals(0, fileCounter.countWordsInFile(testFile.toString()));
        }

        @Test
        @DisplayName("Файл только с пробелами и пустыми строками")
        void shouldCountZeroWordsInWhitespaceOnlyFile() throws IOException {
            List<String> lines = Arrays.asList("   ", "\t", "", " \n ");
            Path testFile = createTestFile("whitespaceOnly.txt", lines);
            assertEquals(0, fileCounter.countWordsInFile(testFile.toString()));
        }

        @Test
        @DisplayName("Исключение для несуществующего файла")
        void shouldThrowIOExceptionForNonExistentFile() {
            String nonExistentPath = tempDir.resolve("non_existent_words.txt").toString();
            assertThrows(NoSuchFileException.class, () -> fileCounter.countWordsInFile(nonExistentPath));
        }

        @Test
        @DisplayName("Исключение для null пути")
        void shouldThrowNullPointerExceptionForNullPath() {
            assertThrows(NullPointerException.class, () -> fileCounter.countWordsInFile(null));
        }
    }
}
