package com.svedentsov.aqa.tasks.oop_design;

import java.util.*;

/**
 * Решение задачи №40: Сравнение объектов кастомного класса (Person).
 * Демонстрирует реализацию интерфейса Comparable для "естественного" порядка
 * и создание внешних классов Comparator для альтернативных порядков сортировки.
 * <p>
 * Описание: Реализовать интерфейс `Comparable` или предоставить `Comparator`
 * для кастомного класса (например, `Person` по возрасту). (Проверяет: ООП, интерфейсы)
 * <p>
 * Задание: Модифицируйте класс `Person` (с полями `name`, `age`), чтобы он
 * реализовывал интерфейс `Comparable<Person>`. Сравнение должно происходить
 * сначала по возрасту (по возрастанию), а при одинаковом возрасте - по имени
 * (в алфавитном порядке).
 * <p>
 * Пример: `Collections.sort(listOfPersons)` должен отсортировать список объектов `Person`
 * согласно заданным правилам.
 * `new Person("Bob", 30).compareTo(new Person("Alice", 30))` -> положительное число.
 * `new Person("Alice", 25).compareTo(new Person("Bob", 30))` -> отрицательное число.
 */
public class CompareCustomObjects {

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

        // --- Демонстрация compareTo (из Comparable) ---
        System.out.println("--- Comparing using Comparable (age ascending, then name ascending) ---");
        compareAndPrint(alice30, bob25);       // Alice(30) > Bob(25) -> 1
        compareAndPrint(bob25, alice30);       // Bob(25) < Alice(30) -> -1
        compareAndPrint(alice30, charlie30);   // Alice(30) < Charlie(30) -> -1 (по имени)
        compareAndPrint(charlie30, bob30);     // Charlie(30) > Bob(30) -> 1 (по имени)
        compareAndPrint(bob30, alice30);       // Bob(30) > Alice(30) -> 1 (по имени)
        compareAndPrint(alice30, alice30);     // Alice(30) == Alice(30) -> 0
        compareAndPrint(alice30, bob30);       // Alice(30) < Bob(30) -> -1 (по имени)

        // --- Демонстрация сортировки списка (использует Comparable) ---
        List<Person> people = new ArrayList<>(List.of(alice30, bob25, charlie30, bob30, alice25));
        System.out.println("\n--- Sorting List using Comparable (Collections.sort) ---");
        System.out.println("Original list : " + people);
        Collections.sort(people); // Использует Person.compareTo()
        System.out.println("Sorted list   : " + people);
        // Ожидаемый порядок: [Alice(25), Bob(25), Alice(30), Bob(30), Charlie(30)]

        // --- Демонстрация сортировки с использованием Comparator ---
        // Создаем копии списка для разных сортировок
        List<Person> peopleForAgeSort = new ArrayList<>(people); // Уже отсортированный список
        List<Person> peopleForNameSort = new ArrayList<>(people);
        List<Person> peopleForAgeDescSort = new ArrayList<>(people);

        System.out.println("\n--- Sorting List using Comparators (list.sort) ---");

        // Сортировка только по возрасту (возрастание)
        peopleForAgeSort.sort(new PersonAgeComparator());
        System.out.println("Sorted by Age : " + peopleForAgeSort);
        // Ожидаемый порядок: [Alice(25), Bob(25), Alice(30), Bob(30), Charlie(30)] (порядок людей с одинаковым возрастом не гарантирован)

        // Сортировка только по имени (алфавитный порядок)
        peopleForNameSort.sort(new PersonNameComparator());
        System.out.println("Sorted by Name: " + peopleForNameSort);
        // Ожидаемый порядок: [Alice(25), Alice(30), Bob(25), Bob(30), Charlie(30)]

        // Сортировка по возрасту (убывание) с использованием лямбда-выражения
        peopleForAgeDescSort.sort((p1, p2) -> Integer.compare(p2.getAge(), p1.getAge()));
        System.out.println("Sorted by Age (desc): " + peopleForAgeDescSort);
        // Ожидаемый порядок: [Alice(30), Bob(30), Charlie(30), Alice(25), Bob(25)]
    }

    /**
     * Класс, представляющий человека с именем и возрастом.
     * Реализует интерфейс {@link Comparable<Person>}, чтобы определить
     * "естественный" порядок сортировки объектов Person:
     * сначала по возрасту (по возрастанию), затем по имени (в алфавитном порядке).
     */
    static class Person implements Comparable<Person> {
        private final String name;
        private final int age;

        public Person(String name, int age) {
            this.name = Objects.requireNonNull(name, "Name cannot be null");
            if (age < 0) throw new IllegalArgumentException("Age cannot be negative: " + age);
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return name + "(" + age + ")"; // Краткая форма для вывода
            // return "Person{name='" + name + '\'' + ", age=" + age + '}';
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
         */
        @Override
        public int compareTo(Person other) {
            Objects.requireNonNull(other, "Cannot compare to a null Person");
            // 1. Сравниваем по возрасту
            int ageComparison = Integer.compare(this.age, other.age);
            // 2. Если возраст разный, возвращаем результат сравнения возрастов
            if (ageComparison != 0) {
                return ageComparison;
            }
            // 3. Если возраст одинаковый, сравниваем по имени
            return this.name.compareTo(other.name);
        }
    }

    /**
     * Внешний компаратор для сравнения объектов Person ТОЛЬКО по возрасту (по возрастанию).
     */
    static class PersonAgeComparator implements Comparator<Person> {
        @Override
        public int compare(Person p1, Person p2) {
            // Проверки на null не обязательны, если гарантируется, что список их не содержит,
            // но для надежности лучше добавить Objects.requireNonNull(p1); Objects.requireNonNull(p2);
            return Integer.compare(p1.getAge(), p2.getAge());
        }
    }

    /**
     * Внешний компаратор для сравнения объектов Person ТОЛЬКО по имени (алфавитный порядок).
     */
    static class PersonNameComparator implements Comparator<Person> {
        @Override
        public int compare(Person p1, Person p2) {
            return p1.getName().compareTo(p2.getName());
        }
    }

    /**
     * Вспомогательный метод для вывода результата сравнения двух Person.
     *
     * @param p1 Первый Person.
     * @param p2 Второй Person.
     */
    private static void compareAndPrint(Person p1, Person p2) {
        int result = p1.compareTo(p2);
        String comparison;
        if (result < 0) {
            comparison = "<";
        } else if (result > 0) {
            comparison = ">";
        } else {
            comparison = "==";
        }
        System.out.printf("  Comparing %s %s %s -> Result: %d%n", p1, comparison, p2, result);
    }
}
