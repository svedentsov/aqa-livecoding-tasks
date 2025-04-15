package com.svedentsov.aqa.tasks.oop_design;

import java.io.*;
import java.lang.reflect.Constructor;

/**
 * Решение задачи №54: Реализация паттерна Singleton.
 * Содержит два варианта реализации: Eager Initialization и Lazy Initialization с DCL.
 * <p>
 * Описание: Реализовать паттерн Singleton.
 * (Проверяет: основы паттернов проектирования, статические члены, синхронизация - опционально)
 * <p>
 * Задание: Реализуйте класс `Logger` как Singleton, используя статическое поле и
 * приватный конструктор. Добавьте статический метод `getInstance()` для получения
 * единственного экземпляра и метод `log(String message)`, который просто выводит
 * сообщение в консоль.
 * <p>
 * Пример: `Logger logger1 = Logger.getInstance(); Logger logger2 = Logger.getInstance();`
 * `logger1 == logger2` -> `true`. `logger1.log("Test message")` выводит "Test message".
 */
public class Singleton {

    // --- Вариант 1: Eager Initialization ---

    /**
     * Реализация Singleton с использованием Eager Initialization (Нетерпеливая инициализация).
     * Экземпляр создается заранее при загрузке класса.
     * Просто, потокобезопасно. Подходит, если экземпляр нужен всегда или его создание дешево.
     * Добавлены меры защиты от создания через Reflection, Cloning, Deserialization.
     */
    static class LoggerEager implements Serializable, Cloneable {
        // 1. Единственный экземпляр создается сразу
        private static final LoggerEager INSTANCE = new LoggerEager();

        // 2. Приватный конструктор
        private LoggerEager() {
            // Защита от рефлексии (частичная)
            if (INSTANCE != null) {
                throw new IllegalStateException("Singleton already constructed. Use getInstance().");
            }
            System.out.println("[Constructor] LoggerEager instance created.");
        }

        /**
         * @return Единственный экземпляр LoggerEager.
         */
        public static LoggerEager getInstance() {
            return INSTANCE;
        }

        /**
         * Логирует сообщение.
         */
        public void log(String message) {
            System.out.println("[LOG Eager #" + System.identityHashCode(this) + "] " + message);
        }

        // 3. Защита от клонирования
        @Override
        protected final Object clone() throws CloneNotSupportedException {
            throw new CloneNotSupportedException("Singleton cannot be cloned.");
        }

        // 4. Защита от создания нового объекта при десериализации
        protected Object readResolve() throws ObjectStreamException {
            System.out.println("[readResolve] Returning existing INSTANCE.");
            return INSTANCE; // Всегда возвращаем существующий экземпляр
        }
    }

    // --- Вариант 2: Lazy Initialization + Double-Checked Locking (DCL) ---

    /**
     * Реализация Singleton с использованием Lazy Initialization (Ленивая инициализация)
     * и Double-Checked Locking (DCL) для потокобезопасности.
     * Экземпляр создается только при первом вызове getInstance().
     * Требует `volatile` и `synchronized`. Подходит для ресурсоемких объектов.
     */
    static class LoggerLazyDCL implements Serializable, Cloneable {
        // 1. volatile поле для экземпляра
        private static volatile LoggerLazyDCL instance; // Инициализируется как null

        // 2. Приватный конструктор
        private LoggerLazyDCL() {
            // Защита от рефлексии
            if (instance != null) {
                throw new IllegalStateException("Singleton already constructed. Use getInstance().");
            }
            System.out.println("[Constructor] LoggerLazyDCL instance created.");
        }

        /**
         * Возвращает единственный экземпляр LoggerLazyDCL (потокобезопасно).
         *
         * @return Экземпляр LoggerLazyDCL.
         */
        public static LoggerLazyDCL getInstance() {
            // Первая проверка (без блокировки) - оптимизация
            LoggerLazyDCL localInstance = instance; // Читаем volatile поле один раз
            if (localInstance == null) {
                // Блокировка на классе, чтобы инициализация произошла только один раз
                synchronized (LoggerLazyDCL.class) {
                    localInstance = instance; // Повторная проверка внутри блокировки
                    if (localInstance == null) {
                        instance = localInstance = new LoggerLazyDCL(); // Создаем и присваиваем
                    }
                }
            }
            return localInstance;
        }

        /**
         * Логирует сообщение.
         */
        public void log(String message) {
            System.out.println("[LOG LazyDCL #" + System.identityHashCode(this) + "] " + message);
        }

        // Защита от клонирования и десериализации
        @Override
        protected final Object clone() throws CloneNotSupportedException {
            throw new CloneNotSupportedException("...");
        }

        protected Object readResolve() throws ObjectStreamException {
            return getInstance(); // Возвращаем существующий/созданный экземпляр
        }
    }

    /**
     * Точка входа для демонстрации различных реализаций Singleton.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        System.out.println("--- Singleton Pattern Demonstration ---");

        // Eager Initialization
        System.out.println("\nTesting Eager Initialization:");
        // Сообщение конструктора должно было появиться при загрузке класса (выше)
        LoggerEager eager1 = LoggerEager.getInstance();
        LoggerEager eager2 = LoggerEager.getInstance();
        System.out.println("Eager instance 1 hash: " + System.identityHashCode(eager1));
        System.out.println("Eager instance 2 hash: " + System.identityHashCode(eager2));
        System.out.println("eager1 == eager2 : " + (eager1 == eager2));
        eager1.log("Eager message 1.");
        eager2.log("Eager message 2.");

        // Lazy Initialization (DCL)
        System.out.println("\nTesting Lazy Initialization (DCL):");
        System.out.println("Accessing first time (should create instance)...");
        LoggerLazyDCL lazy1 = LoggerLazyDCL.getInstance(); // Сообщение конструктора здесь
        System.out.println("Accessing second time...");
        LoggerLazyDCL lazy2 = LoggerLazyDCL.getInstance(); // Конструктор не вызывается
        System.out.println("Lazy instance 1 hash: " + System.identityHashCode(lazy1));
        System.out.println("Lazy instance 2 hash: " + System.identityHashCode(lazy2));
        System.out.println("lazy1 == lazy2 : " + (lazy1 == lazy2));
        lazy1.log("Lazy message 1.");
        lazy2.log("Lazy message 2.");

        // Демонстрация многопоточного доступа к Lazy DCL
        demonstrateMultithreadedLazyAccess();

        // Демонстрация защиты от рефлексии
        demonstrateReflectionAttack();

        // Демонстрация защиты от десериализации (упрощенная)
        demonstrateSerialization(eager1);
    }

    /**
     * Демонстрирует многопоточный доступ к Lazy DCL Singleton.
     */
    private static void demonstrateMultithreadedLazyAccess() {
        System.out.println("\n--- Multithreaded Lazy DCL Access Simulation ---");
        Runnable task = () -> {
            LoggerLazyDCL instance = LoggerLazyDCL.getInstance();
            System.out.println("Thread " + Thread.currentThread().getId() + " got instance hash: " + System.identityHashCode(instance));
        };
        // Запускаем несколько потоков, которые пытаются получить экземпляр
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        Thread t3 = new Thread(task);
        t1.start();
        t2.start();
        t3.start();
        try { // Ждем завершения потоков
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread interrupted during join.");
        }
        // Ожидаем увидеть сообщение конструктора LoggerLazyDCL только один раз
        // и одинаковые хеш-коды экземпляров во всех потоках.
    }

    /**
     * Демонстрирует попытку нарушить Singleton с помощью рефлексии.
     */
    private static void demonstrateReflectionAttack() {
        System.out.println("\n--- Reflection Attack Simulation ---");
        LoggerEager initialEager = LoggerEager.getInstance();
        LoggerLazyDCL initialLazy = LoggerLazyDCL.getInstance(); // Убедимся, что ленивый создан

        // Атака на Eager
        try {
            Constructor<LoggerEager> constructor = LoggerEager.class.getDeclaredConstructor();
            constructor.setAccessible(true); // Делаем приватный конструктор доступным
            LoggerEager reflectionEager = constructor.newInstance(); // Пытаемся создать новый экземпляр
            System.out.println("Reflection Eager Instance created!");
            System.out.println("initialEager == reflectionEager : " + (initialEager == reflectionEager));
            System.out.println("Hashes: " + System.identityHashCode(initialEager) + " vs " + System.identityHashCode(reflectionEager));
        } catch (Exception e) {
            // Ожидаем ошибку из-за защиты в конструкторе
            System.out.println("Reflection attack on Eager failed (expected): " + e.getCause());
        }

        // Атака на Lazy DCL
        try {
            Constructor<LoggerLazyDCL> constructor = LoggerLazyDCL.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            LoggerLazyDCL reflectionLazy = constructor.newInstance();
            System.out.println("Reflection Lazy Instance created!");
            System.out.println("initialLazy == reflectionLazy : " + (initialLazy == reflectionLazy));
            System.out.println("Hashes: " + System.identityHashCode(initialLazy) + " vs " + System.identityHashCode(reflectionLazy));
        } catch (Exception e) {
            // Ожидаем ошибку из-за защиты в конструкторе
            System.out.println("Reflection attack on Lazy failed (expected): " + e.getCause());
        }
    }

    /**
     * Демонстрирует (упрощенно) защиту от создания нового объекта при десериализации.
     */
    private static void demonstrateSerialization(LoggerEager instance) {
        System.out.println("\n--- Serialization/Deserialization Simulation ---");
        try {
            // Сериализация
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(instance);
            oos.close();
            byte[] bytes = bos.toByteArray();

            // Десериализация
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            LoggerEager deserializedInstance = (LoggerEager) ois.readObject();
            ois.close();

            System.out.println("Initial instance hash    : " + System.identityHashCode(instance));
            System.out.println("Deserialized instance hash: " + System.identityHashCode(deserializedInstance));
            System.out.println("instance == deserializedInstance : " + (instance == deserializedInstance));
            // Ожидаем true благодаря readResolve()

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Serialization/Deserialization failed: " + e.getMessage());
        }
    }
}
