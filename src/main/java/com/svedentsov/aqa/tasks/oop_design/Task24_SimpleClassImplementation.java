package com.svedentsov.aqa.tasks.oop_design;

import java.util.Objects;

/**
 * Класс-обертка для демонстрации использования класса Car.
 */
public class Task24_SimpleClassImplementation {

    /**
     * Демонстрирует создание и использование объекта Car.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        try {
            Car myCar = new Car("Toyota", "Camry", 2021);
            System.out.println("Created car: " + myCar); // Вызов toString()
            System.out.println("Make: " + myCar.getMake());
            System.out.println("Model: " + myCar.getModel());
            System.out.println("Year: " + myCar.getYear());

            Car anotherCar = new Car("Toyota", "Camry", 2021);
            Car differentCar = new Car("Honda", "Civic", 2020);

            System.out.println("\nComparing cars:");
            System.out.println("myCar.equals(anotherCar): " + myCar.equals(anotherCar)); // true
            System.out.println("myCar.equals(differentCar): " + myCar.equals(differentCar)); // false

            System.out.println("myCar.hashCode(): " + myCar.hashCode());
            System.out.println("anotherCar.hashCode(): " + anotherCar.hashCode()); // Должны быть равны
            System.out.println("differentCar.hashCode(): " + differentCar.hashCode()); // Скорее всего, другой

        } catch (NullPointerException | IllegalArgumentException e) {
            System.err.println("Error creating car: " + e.getMessage());
        }
        try {
            // Пример невалидных данных
            System.out.println("\nAttempting to create invalid car...");
            Car invalidCar = new Car("Ford", null, 1800);
        } catch (NullPointerException | IllegalArgumentException e) {
            System.err.println("Error creating invalid car: " + e.getMessage());
        }
    }

    /**
     * Представляет автомобиль с маркой, моделью и годом выпуска.
     * Этот класс демонстрирует базовые принципы ООП: инкапсуляцию (приватные поля, геттеры),
     * конструктор для инициализации объекта, а также переопределение стандартных методов
     * toString(), equals() и hashCode().
     * Поля сделаны final для демонстрации создания неизменяемого (immutable) объекта,
     * что является хорошей практикой, если состояние объекта не должно меняться после создания.
     */
    public static final class Car { // Класс final, чтобы нельзя было наследоваться и нарушить иммутабельность
        private final String make;  // Поля private final
        private final String model;
        private final int year;

        /**
         * Создает новый объект Car.
         *
         * @param make  Марка автомобиля (например, "Toyota"). Не может быть null.
         * @param model Модель автомобиля (например, "Camry"). Не может быть null.
         * @param year  Год выпуска автомобиля. Должен быть разумным (например, > 1885).
         * @throws NullPointerException     если make или model равны null.
         * @throws IllegalArgumentException если year некорректен.
         */
        public Car(String make, String model, int year) {
            // Проверка аргументов конструктора
            this.make = Objects.requireNonNull(make, "Make cannot be null");
            this.model = Objects.requireNonNull(model, "Model cannot be null");
            // Простая проверка разумности года
            int currentYear = java.time.Year.now().getValue();
            if (year < 1886 || year > currentYear + 1) { // +1 на случай машины следующего модельного года
                throw new IllegalArgumentException("Invalid year provided: " + year);
            }
            this.year = year;
        }

        /**
         * Возвращает марку автомобиля.
         *
         * @return Марка автомобиля.
         */
        public String getMake() {
            return make;
        }

        /**
         * Возвращает модель автомобиля.
         *
         * @return Модель автомобиля.
         */
        public String getModel() {
            return model;
        }

        /**
         * Возвращает год выпуска автомобиля.
         *
         * @return Год выпуска.
         */
        public int getYear() {
            return year;
        }

        /**
         * Возвращает строковое представление объекта Car.
         *
         * @return Строка вида "Car{make='...', model='...', year=...}".
         */
        @Override
        public String toString() {
            return "Car{" +
                    "make='" + make + '\'' +
                    ", model='" + model + '\'' +
                    ", year=" + year +
                    '}';
        }

        /**
         * Сравнивает этот автомобиль с другим объектом.
         *
         * @param o Объект для сравнения.
         * @return true, если объекты равны (тот же класс и все поля совпадают), иначе false.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Car car = (Car) o;
            // Сравнение всех полей, участвующих в логическом равенстве
            return year == car.year &&
                    make.equals(car.make) && // Используем equals для строк
                    model.equals(car.model);
        }

        /**
         * Возвращает хеш-код для объекта Car.
         * Контракт: если a.equals(b), то a.hashCode() == b.hashCode().
         *
         * @return Хеш-код.
         */
        @Override
        public int hashCode() {
            // Используем Objects.hash для генерации хеш-кода на основе полей,
            // участвующих в equals().
            return Objects.hash(make, model, year);
        }
    }
}
