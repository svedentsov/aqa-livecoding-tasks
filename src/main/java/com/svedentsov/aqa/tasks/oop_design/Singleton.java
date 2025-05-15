package com.svedentsov.aqa.tasks.oop_design;

import java.io.ObjectStreamException;
import java.io.Serializable;

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

    /**
     * Реализация Singleton с использованием Eager Initialization (Нетерпеливая инициализация).
     * Экземпляр создается заранее при загрузке класса.
     * Просто, потокобезопасно.
     */
    static class LoggerEager implements Serializable, Cloneable {
        private static final LoggerEager INSTANCE = new LoggerEager();
        // Флаг для защиты от рефлексии, если конструктор вызывается после инициализации INSTANCE
        private static boolean instanceCreated = false;


        private LoggerEager() {
            // Защита от рефлексии. Если INSTANCE уже существует (а для eager это так при попытке создать второй),
            // или если флаг instanceCreated уже установлен (для первой попытки создать через рефлексию)
            if (instanceCreated) { // Эта проверка остановит попытку создать второй экземпляр через рефлексию
                throw new IllegalStateException("Singleton (Eager) already constructed. Use getInstance().");
            }
            instanceCreated = true; // Устанавливаем флаг при создании ЕДИНСТВЕННОГО экземпляра
            // System.out.println("[Constructor] LoggerEager instance created."); // Убрано для чистых тестов
        }

        public static LoggerEager getInstance() {
            return INSTANCE;
        }

        public void log(String message) {
            System.out.println("[LOG Eager #" + System.identityHashCode(this) + "] " + message);
        }

        @Override
        protected final Object clone() throws CloneNotSupportedException {
            throw new CloneNotSupportedException("Singleton cannot be cloned.");
        }

        // Защита от создания нового объекта при десериализации
        protected Object readResolve() throws ObjectStreamException {
            System.out.println("[readResolve] Returning existing INSTANCE.");
            return INSTANCE; // Всегда возвращаем существующий экземпляр
        }
    }

    /**
     * Реализация Singleton с использованием Lazy Initialization (Ленивая инициализация)
     * и Double-Checked Locking (DCL) для потокобезопасности.
     * Экземпляр создается только при первом вызове getInstance().
     */
    static class LoggerLazyDCL implements Serializable, Cloneable {
        private static volatile LoggerLazyDCL instance; // volatile важно для DCL
        // Флаг, чтобы конструктор был вызван только один раз, даже через рефлексию.
        private static boolean isInstanceCreated = false;


        private LoggerLazyDCL() {
            // Защита от рефлексии
            // Если кто-то пытается вызвать конструктор напрямую, когда isInstanceCreated уже true
            synchronized (LoggerLazyDCL.class) { // Синхронизация для безопасного изменения isInstanceCreated
                if (isInstanceCreated) {
                    throw new IllegalStateException("Singleton (LazyDCL) already constructed. Use getInstance().");
                }
                isInstanceCreated = true; // Устанавливаем флаг ПЕРЕД выходом из конструктора
            }
            // Это сообщение используется в тестах для проверки однократного вызова конструктора
            System.out.println("[Constructor] LoggerLazyDCL instance created.");
        }

        public static LoggerLazyDCL getInstance() {
            LoggerLazyDCL localInstance = instance; // Оптимизация: читаем volatile поле один раз
            if (localInstance == null) {
                // Блокировка на классе, чтобы инициализация произошла только один раз
                synchronized (LoggerLazyDCL.class) {
                    localInstance = instance; // Повторная проверка внутри блокировки
                    if (localInstance == null) {
                        // isInstanceCreated будет true внутри конструктора, но здесь он еще не вызван
                        instance = localInstance = new LoggerLazyDCL();
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
            throw new CloneNotSupportedException("Singleton (LazyDCL) cannot be cloned.");
        }

        // Защита от создания нового объекта при десериализации
        protected Object readResolve() throws ObjectStreamException {
            return getInstance(); // Возвращаем существующий/созданный через getInstance() экземпляр
        }
    }
}
