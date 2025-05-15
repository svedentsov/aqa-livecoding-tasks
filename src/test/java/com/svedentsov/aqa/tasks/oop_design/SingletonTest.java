package com.svedentsov.aqa.tasks.oop_design;

import org.junit.jupiter.api.*;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для паттерна Singleton")
class SingletonTest {

    // Для перехвата вывода в консоль
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent)); // Перенаправляем System.out
        outContent.reset(); // Очищаем буфер перед каждым тестом
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut); // Восстанавливаем System.out
        // Сбрасываем статические поля LoggerLazyDCL для чистоты тестов
        resetLazyDCLSingleton();
    }

    // Метод для сброса состояния LoggerLazyDCL через рефлексию
    private static void resetLazyDCLSingleton() {
        try {
            Field instanceField = Singleton.LoggerLazyDCL.class.getDeclaredField("instance");
            instanceField.setAccessible(true);
            instanceField.set(null, null);

            Field createdFlagField = Singleton.LoggerLazyDCL.class.getDeclaredField("isInstanceCreated");
            createdFlagField.setAccessible(true);
            createdFlagField.set(null, false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // В тестах это не должно падать, если имена полей верны
            e.printStackTrace();
        }
    }

    // Вспомогательный метод для подсчета строк
    private long countOccurrences(String text, String patternString) {
        if (text == null || patternString == null || patternString.isEmpty()) {
            return 0;
        }
        Pattern pattern = Pattern.compile(Pattern.quote(patternString)); // Экранируем спецсимволы в паттерне
        Matcher matcher = pattern.matcher(text);
        long count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }


    @Nested
    @DisplayName("Тесты для LoggerEager (Eager Initialization)")
    class LoggerEagerTests {
        private Singleton.LoggerEager logger1;
        private Singleton.LoggerEager logger2;

        @BeforeEach
        void setUp() {
            outContent.reset(); // Убедимся, что буфер чист
            // Eager instance создается при загрузке класса
            // Сбросим флаг создания для теста рефлексии, если он был установлен другим тестом
            try {
                Field createdFlagField = Singleton.LoggerEager.class.getDeclaredField("instanceCreated");
                createdFlagField.setAccessible(true);
                // Сбрасываем флаг только если он уже true, чтобы имитировать "чистую" загрузку класса
                // для первого теста рефлексии. Это немного грязно.
                // Но для Eager, INSTANCE всегда != null после загрузки класса.
                // Проблема в том, что флаг instanceCreated один на все тесты.
                // Для Eager, защита от рефлексии больше полагается на то, что INSTANCE уже не null.
                // Оставим как есть, защита от рефлексии для Eager всегда будет активна после первой инициализации.
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

            logger1 = Singleton.LoggerEager.getInstance();
            logger2 = Singleton.LoggerEager.getInstance();
        }

        @Test
        @DisplayName("getInstance() должен всегда возвращать один и тот же экземпляр")
        void getInstance_shouldReturnSameInstance() {
            assertSame(logger1, logger2, "getInstance() должен возвращать тот же самый экземпляр");
        }

        @Test
        @DisplayName("log() должен выводить сообщение в консоль")
        void log_shouldPrintMessage() {
            String testMessage = "Eager test log message";
            logger1.log(testMessage);
            String output = outContent.toString();
            assertTrue(output.contains(testMessage), "Вывод должен содержать сообщение лога: " + testMessage);
            assertTrue(output.contains("[LOG Eager"), "Вывод должен содержать префикс логгера");
        }

        @Test
        @DisplayName("clone() должен выбрасывать CloneNotSupportedException")
        void clone_shouldThrowCloneNotSupportedException() {
            // Пытаемся вызвать protected метод clone через рефлексию
            try {
                Method cloneMethod = Singleton.LoggerEager.class.getDeclaredMethod("clone");
                cloneMethod.setAccessible(true); // Делаем protected метод доступным
                cloneMethod.invoke(logger1);
                fail("CloneNotSupportedException должна была быть выброшена");
            } catch (InvocationTargetException e) {
                // Ожидаем, что причиной будет CloneNotSupportedException
                assertTrue(e.getCause() instanceof CloneNotSupportedException,
                        "Причина исключения должна быть CloneNotSupportedException");
            } catch (NoSuchMethodException | IllegalAccessException e) {
                fail("Не удалось протестировать метод clone через рефлексию: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("Десериализация должна возвращать тот же самый экземпляр")
        void deserialization_shouldReturnSameInstance() throws IOException, ClassNotFoundException {
            // Сериализация
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(logger1);
            oos.close();
            byte[] bytes = bos.toByteArray();

            // Десериализация
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            Singleton.LoggerEager deserializedLogger = (Singleton.LoggerEager) ois.readObject();
            ois.close();

            assertSame(logger1, deserializedLogger, "Десериализованный экземпляр должен быть тем же самым (readResolve)");
        }
    }


    @Nested
    @DisplayName("Тесты для LoggerLazyDCL (Lazy Initialization with DCL)")
    class LoggerLazyDCLTests {

        @BeforeEach
        void setUp() {
            resetLazyDCLSingleton(); // Важно сбрасывать перед каждым тестом для ленивой инициализации
            outContent.reset();
        }

        @Test
        @DisplayName("getInstance() должен возвращать один и тот же экземпляр")
        void getInstance_shouldReturnSameInstance() {
            Singleton.LoggerLazyDCL logger1 = Singleton.LoggerLazyDCL.getInstance();
            Singleton.LoggerLazyDCL logger2 = Singleton.LoggerLazyDCL.getInstance();
            assertSame(logger1, logger2, "getInstance() должен возвращать тот же самый экземпляр");
        }

        @Test
        @DisplayName("Конструктор должен вызываться только один раз (ленивая инициализация)")
        void constructor_shouldBeCalledOnlyOnce() {
            Singleton.LoggerLazyDCL.getInstance(); // Первый вызов, конструктор вызывается
            long countFirst = countOccurrences(outContent.toString(), "[Constructor] LoggerLazyDCL instance created.");

            outContent.reset(); // Очищаем перед вторым вызовом

            Singleton.LoggerLazyDCL.getInstance(); // Второй вызов, конструктор НЕ должен вызываться
            long countSecond = countOccurrences(outContent.toString(), "[Constructor] LoggerLazyDCL instance created.");

            assertEquals(1, countFirst, "Конструктор должен быть вызван при первом getInstance()");
            assertEquals(0, countSecond, "Конструктор НЕ должен быть вызван при последующих getInstance()");
        }


        @Test
        @DisplayName("log() должен выводить сообщение в консоль")
        void log_shouldPrintMessage() {
            Singleton.LoggerLazyDCL logger = Singleton.LoggerLazyDCL.getInstance();
            String testMessage = "LazyDCL test log message";
            logger.log(testMessage);
            String output = outContent.toString();
            assertTrue(output.contains(testMessage), "Вывод должен содержать сообщение лога");
            assertTrue(output.contains("[LOG LazyDCL"), "Вывод должен содержать префикс логгера");
        }

        @Test
        @DisplayName("clone() должен выбрасывать CloneNotSupportedException")
        void clone_shouldThrowCloneNotSupportedException() {
            Singleton.LoggerLazyDCL logger = Singleton.LoggerLazyDCL.getInstance();
            try {
                Method cloneMethod = Singleton.LoggerLazyDCL.class.getDeclaredMethod("clone");
                cloneMethod.setAccessible(true);
                cloneMethod.invoke(logger);
                fail("CloneNotSupportedException должна была быть выброшена");
            } catch (InvocationTargetException e) {
                assertTrue(e.getCause() instanceof CloneNotSupportedException,
                        "Причина исключения должна быть CloneNotSupportedException");
            } catch (NoSuchMethodException | IllegalAccessException e) {
                fail("Не удалось протестировать метод clone через рефлексию: " + e.getMessage());
            }
        }

        @Test
        @DisplayName("Десериализация должна возвращать тот же самый экземпляр")
        void deserialization_shouldReturnSameInstance() throws IOException, ClassNotFoundException {
            Singleton.LoggerLazyDCL logger1 = Singleton.LoggerLazyDCL.getInstance(); // Убедимся, что экземпляр создан

            // Сериализация
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(logger1);
            oos.close();
            byte[] bytes = bos.toByteArray();

            // Десериализация
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            Singleton.LoggerLazyDCL deserializedLogger = (Singleton.LoggerLazyDCL) ois.readObject();
            ois.close();

            assertSame(logger1, deserializedLogger, "Десериализованный экземпляр должен быть тем же самым (readResolve)");
        }


        @Test
        @DisplayName("Защита от создания через рефлексию должна работать")
        void reflectionProtection_shouldThrowExceptionOnSecondAttempt() {
            // Первая попытка создания через рефлексию (если getInstance() еще не вызывался)
            // должна пройти, но установит isInstanceCreated = true
            Constructor<Singleton.LoggerLazyDCL> constructor;
            try {
                constructor = Singleton.LoggerLazyDCL.class.getDeclaredConstructor();
                constructor.setAccessible(true);
                constructor.newInstance(); // Первый "нелегитимный" экземпляр (если instance еще null)
            } catch (InvocationTargetException e) {
                // Это может произойти, если другой тест УЖЕ создал экземпляр и не был сделан reset.
                // Наш resetLazyDCLSingleton() должен это предотвращать.
                // Если исключение здесь, значит, isInstanceCreated уже true.
                assertTrue(e.getCause() instanceof IllegalStateException,
                        "Если это первый вызов конструктора в JVM, он должен пройти, иначе IllegalStateException");
                // Если первый вызов конструктора прошел успешно, то isInstanceCreated теперь true
            } catch (Exception e) {
                fail("Неожиданное исключение при первой попытке рефлексии: " + e);
                return; // Выходим, если не удалось даже первую попытку сделать
            }


            // Вторая попытка создания через рефлексию должна провалиться
            try {
                Constructor<Singleton.LoggerLazyDCL> ctorForSecondAttempt = Singleton.LoggerLazyDCL.class.getDeclaredConstructor();
                ctorForSecondAttempt.setAccessible(true);
                ctorForSecondAttempt.newInstance();
                fail("Должно было быть выброшено исключение при второй попытке создания через рефлексию");
            } catch (InvocationTargetException e) {
                assertTrue(e.getCause() instanceof IllegalStateException,
                        "Причина исключения должна быть IllegalStateException");
                assertTrue(e.getCause().getMessage().contains("Singleton (LazyDCL) already constructed"),
                        "Сообщение об ошибке должно соответствовать 'already constructed'");
            } catch (Exception e) {
                fail("Неожиданное исключение при второй атаке рефлексией: " + e);
            }
        }


        @Test
        @DisplayName("getInstance() должен быть потокобезопасным и создавать экземпляр один раз")
        void getInstance_shouldBeThreadSafeAndCreateOnce() throws InterruptedException {
            int numThreads = 100;
            ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
            CountDownLatch allThreadsReady = new CountDownLatch(numThreads); // Для синхронизации старта
            CountDownLatch allThreadsDone = new CountDownLatch(numThreads);  // Для ожидания завершения
            Set<Singleton.LoggerLazyDCL> instances = Collections.synchronizedSet(new HashSet<>());

            for (int i = 0; i < numThreads; i++) {
                executorService.submit(() -> {
                    allThreadsReady.countDown(); // Сигнализируем о готовности
                    try {
                        allThreadsReady.await(); // Ждем, пока все потоки будут готовы
                        Singleton.LoggerLazyDCL instance = Singleton.LoggerLazyDCL.getInstance();
                        instances.add(instance);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        allThreadsDone.countDown(); // Сигнализируем о завершении
                    }
                });
            }

            assertTrue(allThreadsDone.await(10, TimeUnit.SECONDS), "Потоки должны завершиться вовремя");
            executorService.shutdownNow();

            assertEquals(1, instances.size(), "Все потоки должны получить один и тот же экземпляр");

            // Проверяем, что сообщение конструктора появилось только один раз
            long constructorCallCount = countOccurrences(outContent.toString(), "[Constructor] LoggerLazyDCL instance created.");
            assertEquals(1, constructorCallCount, "Конструктор должен быть вызван только один раз в многопоточной среде");
        }
    }
}
