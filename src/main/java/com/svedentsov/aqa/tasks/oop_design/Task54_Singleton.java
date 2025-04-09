package com.svedentsov.aqa.tasks.oop_design;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Constructor;

/**
 * Решение задачи №54: Реализация паттерна Singleton.
 * Содержит два варианта реализации: Eager Initialization и Lazy Initialization с DCL.
 */
public class Task54_Singleton {

    /**
     * Реализация Singleton с использованием Eager Initialization (Нетерпеливая инициализация).
     * Экземпляр создается заранее при загрузке класса статической инициализацией.
     * Этот подход прост и потокобезопасен по умолчанию в Java.
     * Подходит, когда создание объекта не слишком ресурсоемко или экземпляр нужен всегда.
     */
    static class LoggerEager implements Serializable, Cloneable { // Добавим защиту
        // 1. Статическое final поле для хранения единственного экземпляра.
        // Инициализируется сразу при загрузке класса.
        private static final LoggerEager INSTANCE = new LoggerEager();

        // Счетчик для демонстрации единственности создания
        private static int instanceCounter = 0;

        // 2. Приватный конструктор для предотвращения создания извне.
        private LoggerEager() {
            instanceCounter++;
            // Защита от создания через рефлексию (может быть обойдена, но усложняет)
            if (INSTANCE != null) {
                throw new IllegalStateException("Singleton instance already exists. Use getInstance().");
            }
            System.out.println(">>> LoggerEager instance #" + instanceCounter + " created. <<<");
        }

        /**
         * Возвращает единственный экземпляр класса LoggerEager.
         *
         * @return Экземпляр LoggerEager.
         */
        public static LoggerEager getInstance() {
            return INSTANCE;
        }

        /**
         * Метод для логирования (пример).
         */
        public void log(String message) {
            System.out.println("[LOG Eager " + System.identityHashCode(this) + "] " + message);
        }

        /**
         * Предотвращение создания копий через клонирование.
         */
        @Override
        protected final Object clone() throws CloneNotSupportedException {
            // Можно просто выбросить исключение
            throw new CloneNotSupportedException("Singleton cannot be cloned.");
            // Или вернуть тот же самый экземпляр:
            // return INSTANCE;
        }

        /**
         * Предотвращение создания новых объектов при десериализации.
         */
        protected Object readResolve() throws ObjectStreamException {
            System.out.println("ReadResolve called, returning INSTANCE");
            return INSTANCE;
        }
    }

    /**
     * Реализация Singleton с использованием Lazy Initialization (Ленивая инициализация)
     * и Double-Checked Locking (DCL) для потокобезопасности.
     * Экземпляр создается только при первом вызове getInstance().
     * Требует использования `volatile` и `synchronized`.
     * Подходит, когда создание объекта ресурсоемко и не всегда необходимо.
     * Примечание: В современных версиях Java (5+) DCL с volatile считается безопасным.
     */
    static class LoggerLazyDCL implements Serializable, Cloneable {
        // 1. volatile статическое поле. volatile гарантирует видимость изменений
        //    между потоками и предотвращает проблемы переупорядочивания инструкций.
        private static volatile LoggerLazyDCL INSTANCE;

        private static int instanceCounter = 0;


        // 2. Приватный конструктор.
        private LoggerLazyDCL() {
            instanceCounter++;
            // Защита от рефлексии (частичная)
            if (INSTANCE != null) {
                throw new IllegalStateException("Singleton instance already exists. Use getInstance().");
            }
            System.out.println(">>> LoggerLazyDCL instance #" + instanceCounter + " created. <<<");
        }

        /**
         * Возвращает единственный экземпляр класса LoggerLazyDCL.
         * Использует Double-Checked Locking для потокобезопасной ленивой инициализации.
         *
         * @return Единственный экземпляр LoggerLazyDCL.
         */
        public static LoggerLazyDCL getInstance() {
            // Первая проверка без блокировки (оптимизация для большинства случаев)
            if (INSTANCE == null) {
                // Блокировка на мониторе класса, чтобы только один поток
                // мог войти в критическую секцию инициализации.
                synchronized (LoggerLazyDCL.class) {
                    // Вторая проверка внутри блокировки (важно!)
                    // На случай, если другой поток уже создал экземпляр,
                    // пока текущий поток ожидал входа в synchronized блок.
                    if (INSTANCE == null) {
                        INSTANCE = new LoggerLazyDCL(); // Создание экземпляра
                    }
                }
            }
            return INSTANCE;
        }

        /**
         * Метод для логирования (пример).
         */
        public void log(String message) {
            System.out.println("[LOG LazyDCL " + System.identityHashCode(this) + "] " + message);
        }

        // Защита от клонирования и десериализации
        @Override
        protected final Object clone() throws CloneNotSupportedException {
            throw new CloneNotSupportedException("...");
        }

        protected Object readResolve() throws ObjectStreamException {
            return getInstance();
        }
    }

    /**
     * Точка входа для демонстрации различных реализаций Singleton.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        System.out.println("--- Eager Initialization Demo ---");
        // Класс загружен, экземпляр уже создан (сообщение должно было появиться раньше)
        LoggerEager eager1 = LoggerEager.getInstance();
        LoggerEager eager2 = LoggerEager.getInstance();
        System.out.println("Eager instances the same? " + (eager1 == eager2));
        eager1.log("Message 1");
        eager2.log("Message 2");

        System.out.println("\n--- Lazy Initialization (DCL) Demo ---");
        // Экземпляр еще не создан
        System.out.println("Getting first lazy instance...");
        LoggerLazyDCL lazy1 = LoggerLazyDCL.getInstance(); // <- Создание произойдет здесь
        System.out.println("Getting second lazy instance...");
        LoggerLazyDCL lazy2 = LoggerLazyDCL.getInstance(); // <- Вернется существующий
        System.out.println("Lazy instances the same? " + (lazy1 == lazy2));
        lazy1.log("Message 3");
        lazy2.log("Message 4");

        // Демонстрация многопоточности для Lazy DCL (упрощенно)
        System.out.println("\n--- Multithreaded Lazy DCL Access ---");
        Runnable task = () -> {
            LoggerLazyDCL instance = LoggerLazyDCL.getInstance();
            // instance.log("Accessed by thread " + Thread.currentThread().getName());
            System.out.println("Thread " + Thread.currentThread().getId() + " got instance: " + System.identityHashCode(instance));
        };
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // Сообщение о создании должно появиться только один раз, хеш-коды должны совпасть.

        // Демонстрация защиты от рефлексии (может выбросить исключение)
        System.out.println("\n--- Reflection Attack Demo ---");
        try {
            Constructor<LoggerEager> constructor = LoggerEager.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            LoggerEager reflectionInstance = constructor.newInstance();
            System.out.println("Reflection instance created? " + (eager1 != reflectionInstance));
            System.out.println("HashCodes: " + System.identityHashCode(eager1) + " vs " + System.identityHashCode(reflectionInstance));
        } catch (Exception e) {
            System.out.println("Reflection attack failed (as expected for Eager): " + e.getCause());
        }
        try { // DCL тоже уязвим без доп. проверок в конструкторе
            Constructor<LoggerLazyDCL> constructor = LoggerLazyDCL.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            LoggerLazyDCL reflectionInstance = constructor.newInstance();
        } catch (Exception e) {
            System.out.println("Reflection attack failed (as expected for Lazy): " + e.getCause());
        }

    }
}
