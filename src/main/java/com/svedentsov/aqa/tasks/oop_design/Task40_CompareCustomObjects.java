package com.svedentsov.aqa.tasks.oop_design;

import java.util.*;

/**
 * Решение задачи №40: Сравнение объектов кастомного класса (Person).
 * Демонстрирует реализацию интерфейса Comparable для "естественного" порядка
 * и создание внешних классов Comparator для альтернативных порядков сортировки.
 */
public class Task40_CompareCustomObjects {

    /**
     * Класс, представляющий человека с именем и возрастом.
     * Реализует интерфейс {@link Comparable<Person>}, чтобы определить
     * "естественный" порядок сортировки объектов Person.
     * Сравнение идет сначала по возрасту (по возрастанию),
     * затем по имени (в алфавитном порядке).
     */
    static class Person implements Comparable<Person> { // Делаем static для main
        private final String name;
        private final int age;

        /**
         * Создает объект Person.
         *
         * @param name Имя человека (не null).
         * @param age  Возраст человека (не отрицательный).
         */
        public Person(String name, int age) {
            this.name = Objects.requireNonNull(name, "Name cannot be null");
            if (age < 0) throw new IllegalArgumentException("Age cannot be negative");
            this.age = age;
        }

        /**
         * Возвращает имя.
         */
        public String getName() {
            return name;
        }

        /**
         * Возвращает возраст.
         */
        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "Person{name='" + name + '\'' + ", age=" + age + '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return age == person.age && name.equals(person.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }

        /**
         * Сравнивает этот объект Person с другим объектом Person для установления порядка.
         * Реализует "естественный" порядок: сначала по возрасту (по возрастанию),
         * затем, при равенстве возрастов, по имени (в алфавитном порядке).
         *
         * @param other Другой объект Person для сравнения (не null).
         * @return отрицательное число, если этот объект меньше другого;
         * ноль, если объекты равны по критериям сравнения;
         * положительное число, если этот объект больше другого.
         * @throws NullPointerException если other равен null.
         */
        @Override
        public int compareTo(Person other) {
            Objects.requireNonNull(other, "Cannot compare to a null Person");
            // 1. Сравниваем по возрасту
            int ageComparison = Integer.compare(this.age, other.age);
            // Если возраст разный, результат сравнения определен
            if (ageComparison != 0) {
                return ageComparison;
            }
            // 2. Если возраст одинаковый, сравниваем по имени (естественный порядок строк)
            return this.name.compareTo(other.name);
        }
    }

    /**
     * Внешний компаратор ({@link Comparator}) для сравнения объектов Person
     * ТОЛЬКО по возрасту (по возрастанию).
     */
    static class PersonAgeComparator implements Comparator<Person> {
        /**
         * Сравнивает два объекта Person по возрасту.
         *
         * @param p1 Первый объект Person (не null).
         * @param p2 Второй объект Person (не null).
         * @return результат сравнения возрастов (отрицательный, 0 или положительный).
         * @throws NullPointerException если p1 или p2 равны null.
         */
        @Override
        public int compare(Person p1, Person p2) {
            Objects.requireNonNull(p1, "Person p1 cannot be null");
            Objects.requireNonNull(p2, "Person p2 cannot be null");
            // Используем Integer.compare для безопасного сравнения int
            return Integer.compare(p1.getAge(), p2.getAge());
        }
    }

    /**
     * Внешний компаратор для сравнения объектов Person ТОЛЬКО по имени (алфавитный порядок).
     */
    static class PersonNameComparator implements Comparator<Person> {
        /**
         * Сравнивает два объекта Person по имени.
         *
         * @param p1 Первый объект Person (не null).
         * @param p2 Второй объект Person (не null).
         * @return результат сравнения имен (отрицательный, 0 или положительный).
         * @throws NullPointerException если p1 или p2 равны null.
         */
        @Override
        public int compare(Person p1, Person p2) {
            Objects.requireNonNull(p1, "Person p1 cannot be null");
            Objects.requireNonNull(p2, "Person p2 cannot be null");
            // Используем compareTo для строк
            return p1.getName().compareTo(p2.getName());
        }
    }

    /**
     * Точка входа для демонстрации использования Comparable и Comparator с классом Person.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Person alice30 = new Person("Alice", 30);
        Person bob25 = new Person("Bob", 25);
        Person charlie30 = new Person("Charlie", 30);
        Person bob30 = new Person("Bob", 30);
        Person alice25 = new Person("Alice", 25);

        // --- Сравнение с использованием compareTo (из Comparable) ---
        System.out.println("--- Comparing using Comparable (age, then name) ---");
        System.out.println("alice30 vs bob25: " + Integer.signum(alice30.compareTo(bob25)));       // > 0 (+)
        System.out.println("bob25 vs alice30: " + Integer.signum(bob25.compareTo(alice30)));       // < 0 (-)
        System.out.println("alice30 vs charlie30: " + Integer.signum(alice30.compareTo(charlie30))); // < 0 (-)
        System.out.println("charlie30 vs bob30: " + Integer.signum(charlie30.compareTo(bob30)));   // > 0 (+)
        System.out.println("bob30 vs alice30: " + Integer.signum(bob30.compareTo(alice30)));       // > 0 (+)
        System.out.println("alice30 vs alice30: " + Integer.signum(alice30.compareTo(alice30)));     // = 0

        // --- Сортировка списка с использованием Comparable (Collections.sort) ---
        List<Person> people = new ArrayList<>(List.of(alice30, bob25, charlie30, bob30, alice25));
        System.out.println("\nOriginal list: " + people);
        Collections.sort(people); // Использует compareTo() из класса Person
        System.out.println("Sorted list (Comparable - age, then name): " + people);
        // Ожидаемый порядок: Alice(25), Bob(25), Alice(30), Bob(30), Charlie(30)

        // --- Сортировка с использованием Comparator (только по возрасту) ---
        List<Person> peopleForAgeSort = new ArrayList<>(List.of(alice30, bob25, charlie30, bob30, alice25));
        peopleForAgeSort.sort(new PersonAgeComparator()); // Используем внешний Comparator
        System.out.println("\nSorted list (Comparator by age): " + peopleForAgeSort);
        // Ожидаемый порядок: Alice(25), Bob(25), Alice(30), Charlie(30), Bob(30)

        // --- Сортировка с использованием Comparator (только по имени) ---
        List<Person> peopleForNameSort = new ArrayList<>(List.of(alice30, bob25, charlie30, bob30, alice25));
        peopleForNameSort.sort(new PersonNameComparator());
        System.out.println("\nSorted list (Comparator by name): " + peopleForNameSort);
        // Ожидаемый порядок: Alice(25), Alice(30), Bob(25), Bob(30), Charlie(30)

        // --- Сортировка с использованием лямбда-выражения (Java 8+) ---
        List<Person> peopleForLambdaSort = new ArrayList<>(List.of(alice30, bob25, charlie30, bob30, alice25));
        // Сортировка по убыванию возраста
        peopleForLambdaSort.sort((p1, p2) -> Integer.compare(p2.getAge(), p1.getAge()));
        System.out.println("\nSorted list (Lambda - age descending): " + peopleForLambdaSort);
        // Ожидаемый порядок: Alice(30), Charlie(30), Bob(30), Alice(25), Bob(25)
    }
}
