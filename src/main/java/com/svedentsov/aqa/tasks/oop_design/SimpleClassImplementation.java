package com.svedentsov.aqa.tasks.oop_design;

import java.util.Objects;

/**
 * Класс-обертка для класса Car.
 */
public class SimpleClassImplementation {

    /**
     * Представляет автомобиль с маркой, моделью и годом выпуска.
     * Этот класс демонстрирует базовые принципы ООП: инкапсуляцию (приватные поля, геттеры),
     * конструктор для инициализации объекта, а также переопределение стандартных методов
     * toString(), equals() и hashCode().
     * Сделан неизменяемым (immutable) путем объявления полей как `final` и отсутствия сеттеров.
     * Класс также объявлен `final`, чтобы предотвратить наследование и нарушение инвариантов.
     */
    public static final class Car { // Класс final для предотвращения наследования
        private final String make;  // Поля private и final для инкапсуляции и неизменяемости
        private final String model;
        private final int year;

        // Константы для валидации года
        // Год первого автомобиля Бенца
        private static final int MIN_YEAR = 1886;

        /**
         * Создает новый неизменяемый объект Car.
         *
         * @param make  Марка автомобиля (например, "Toyota"). Не может быть null или пустой/содержать только пробелы.
         * @param model Модель автомобиля (например, "Camry"). Не может быть null или пустой/содержать только пробелы.
         * @param year  Год выпуска автомобиля. Должен быть разумным (например, между MIN_YEAR и текущим годом + 1).
         * @throws NullPointerException     если make или model равны null.
         * @throws IllegalArgumentException если make или model пустые/содержат только пробелы, или если year некорректен.
         */
        public Car(String make, String model, int year) {
            // Проверка аргументов конструктора
            this.make = Objects.requireNonNull(make, "Make cannot be null");
            this.model = Objects.requireNonNull(model, "Model cannot be null");

            if (make.trim().isEmpty()) throw new IllegalArgumentException("Make cannot be empty or blank.");
            if (model.trim().isEmpty()) throw new IllegalArgumentException("Model cannot be empty or blank.");

            // Проверка разумности года
            // Используем java.time для получения текущего года
            int currentYear = java.time.Year.now().getValue();
            if (year < MIN_YEAR || year > currentYear + 1) { // +1 допускает машины следующего модельного года
                throw new IllegalArgumentException("Invalid year provided: " + year +
                        ". Year must be between " + MIN_YEAR + " and " + (currentYear + 1));
            }
            this.year = year;
        }

        // --- Геттеры ---

        public String getMake() {
            return make;
        }

        public String getModel() {
            return model;
        }

        public int getYear() {
            return year;
        }

        // --- Переопределенные методы Object ---

        @Override
        public String toString() {
            return String.format("Car{make='%s', model='%s', year=%d}", make, model, year);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Car otherCar = (Car) o;
            // Сравнение полей. Порядок не важен, но обычно начинают с примитивов.
            return this.year == otherCar.year &&
                    this.make.equals(otherCar.make) &&
                    this.model.equals(otherCar.model);
        }

        @Override
        public int hashCode() {
            return Objects.hash(make, model, year);
        }
    }
}
