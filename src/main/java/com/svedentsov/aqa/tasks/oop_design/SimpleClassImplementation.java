package com.svedentsov.aqa.tasks.oop_design;

import java.util.Objects;

/**
 * Класс-обертка для демонстрации использования класса Car.
 */
public class SimpleClassImplementation {

    /**
     * Точка входа. Демонстрирует создание и использование объекта Car.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        System.out.println("--- Creating and Using Car Objects ---");
        Car car1 = null;
        Car car2 = null;
        Car car3 = null;

        // Создание валидных объектов
        try {
            car1 = new Car("Toyota", "Camry", 2021);
            car2 = new Car("Toyota", "Camry", 2021); // Такой же, как car1
            car3 = new Car("Honda", "Civic", 2020);  // Другой

            System.out.println("Car 1: " + car1); // Вызов car1.toString()
            System.out.println("Car 2: " + car2);
            System.out.println("Car 3: " + car3);

            System.out.println("\n--- Accessing Fields via Getters ---");
            System.out.println("Car 1 Make: " + car1.getMake());
            System.out.println("Car 1 Model: " + car1.getModel());
            System.out.println("Car 1 Year: " + car1.getYear());

            System.out.println("\n--- Testing equals() ---");
            System.out.println("car1.equals(car2): " + car1.equals(car2)); // Ожидается true
            System.out.println("car1.equals(car3): " + car1.equals(car3)); // Ожидается false
            System.out.println("car1.equals(null): " + car1.equals(null)); // Ожидается false
            System.out.println("car1.equals(\"SomeString\"): " + car1.equals("SomeString")); // Ожидается false

            System.out.println("\n--- Testing hashCode() ---");
            System.out.println("car1.hashCode(): " + car1.hashCode());
            System.out.println("car2.hashCode(): " + car2.hashCode()); // Ожидается такой же, как у car1
            System.out.println("car3.hashCode(): " + car3.hashCode()); // Ожидается другой
            // Проверка контракта hashCode: если equals true, hashCodes должны быть равны
            if (car1.equals(car2)) {
                System.out.println("Hash code check (car1 vs car2): " + (car1.hashCode() == car2.hashCode() ? "Passed" : "Failed!"));
            }

        } catch (NullPointerException | IllegalArgumentException e) {
            System.err.println("Error during valid car creation/usage: " + e.getMessage());
        }

        // Попытка создания невалидных объектов
        System.out.println("\n--- Attempting Invalid Car Creation ---");
        try {
            System.out.print("Creating Car(null, \"Model\", 2020): ");
            new Car(null, "Model", 2020);
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println("Caught expected error - " + e.getMessage());
        }
        try {
            System.out.print("Creating Car(\"Make\", null, 2020): ");
            new Car("Make", null, 2020);
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println("Caught expected error - " + e.getMessage());
        }
        try {
            System.out.print("Creating Car(\"Make\", \"Model\", 1800): ");
            new Car("Make", "Model", 1800); // Слишком старый год
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println("Caught expected error - " + e.getMessage());
        }
        try {
            int futureYear = java.time.Year.now().getValue() + 2;
            System.out.print("Creating Car(\"Make\", \"Model\", " + futureYear + "): ");
            new Car("Make", "Model", futureYear); // Слишком будущий год
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println("Caught expected error - " + e.getMessage());
        }

    }

    /**
     * Представляет автомобиль с маркой, моделью и годом выпуска.
     * Этот класс демонстрирует базовые принципы ООП: инкапсуляцию (приватные поля, геттеры),
     * конструктор для инициализации объекта, а также переопределение стандартных методов
     * toString(), equals() и hashCode().
     * <p>
     * Сделан неизменяемым (immutable) путем объявления полей как `final` и отсутствия сеттеров.
     * Класс также объявлен `final`, чтобы предотвратить наследование и нарушение инвариантов.
     */
    public static final class Car { // Класс final для предотвращения наследования
        private final String make;  // Поля private и final для инкапсуляции и неизменяемости
        private final String model;
        private final int year;

        // Константы для валидации года
        private static final int MIN_YEAR = 1886; // Год первого автомобиля Бенца

        /**
         * Создает новый неизменяемый объект Car.
         *
         * @param make  Марка автомобиля (например, "Toyota"). Не может быть null или пустой.
         * @param model Модель автомобиля (например, "Camry"). Не может быть null или пустой.
         * @param year  Год выпуска автомобиля. Должен быть разумным (например, между MIN_YEAR и текущим годом + 1).
         * @throws NullPointerException     если make или model равны null.
         * @throws IllegalArgumentException если make или model пустые, или если year некорректен.
         */
        public Car(String make, String model, int year) {
            // Проверка аргументов конструктора с использованием Objects.requireNonNull и дополнительных проверок
            this.make = Objects.requireNonNull(make, "Make cannot be null");
            this.model = Objects.requireNonNull(model, "Model cannot be null");

            if (make.trim().isEmpty()) throw new IllegalArgumentException("Make cannot be empty or blank.");
            if (model.trim().isEmpty()) throw new IllegalArgumentException("Model cannot be empty or blank.");

            // Проверка разумности года
            int currentYear = java.time.Year.now().getValue();
            if (year < MIN_YEAR || year > currentYear + 1) { // +1 допускает машины следующего модельного года
                throw new IllegalArgumentException("Invalid year provided: " + year +
                        ". Year must be between " + MIN_YEAR + " and " + (currentYear + 1));
            }
            this.year = year;
        }

        // --- Геттеры ---

        /**
         * @return Марка автомобиля.
         */
        public String getMake() {
            return make;
        }

        /**
         * @return Модель автомобиля.
         */
        public String getModel() {
            return model;
        }

        /**
         * @return Год выпуска автомобиля.
         */
        public int getYear() {
            return year;
        }

        // --- Переопределенные методы Object ---

        /**
         * Возвращает строковое представление объекта Car.
         *
         * @return Строка вида "Car{make='...', model='...', year=...}".
         */
        @Override
        public String toString() {
            // Используем String.format для чуть более чистого форматирования
            return String.format("Car{make='%s', model='%s', year=%d}", make, model, year);
            // Альтернатива:
            // return "Car{" +
            //         "make='" + make + '\'' +
            //         ", model='" + model + '\'' +
            //         ", year=" + year +
            //         '}';
        }

        /**
         * Сравнивает этот автомобиль с другим объектом на равенство.
         * Два автомобиля считаются равными, если они одного класса (Car)
         * и имеют одинаковые значения полей make, model и year.
         *
         * @param o Объект для сравнения.
         * @return true, если объекты равны, иначе false.
         */
        @Override
        public boolean equals(Object o) {
            // 1. Проверка на идентичность ссылок (оптимизация)
            if (this == o) return true;
            // 2. Проверка на null и совпадение классов
            if (o == null || getClass() != o.getClass()) return false;
            // 3. Приведение типа и сравнение полей
            Car otherCar = (Car) o;
            return this.year == otherCar.year &&
                    this.make.equals(otherCar.make) && // Используем equals для объектов (строк)
                    this.model.equals(otherCar.model);
        }

        /**
         * Возвращает хеш-код для объекта Car.
         * Хеш-код вычисляется на основе полей, используемых в методе `equals` (make, model, year).
         * Соблюдает контракт: если `a.equals(b)`, то `a.hashCode() == b.hashCode()`.
         *
         * @return Целочисленный хеш-код.
         */
        @Override
        public int hashCode() {
            // Используем утилиту Objects.hash для генерации хеш-кода на основе полей.
            // Это рекомендуемый способ для избежания ошибок при вычислении хеш-кода.
            return Objects.hash(make, model, year);
            // Альтернативный ручной расчет (менее предпочтителен):
            // int result = make.hashCode();
            // result = 31 * result + model.hashCode();
            // result = 31 * result + Integer.hashCode(year); // или просто + year
            // return result;
        }
    }
}
