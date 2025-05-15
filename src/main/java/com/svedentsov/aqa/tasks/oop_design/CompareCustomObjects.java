package com.svedentsov.aqa.tasks.oop_design;

import java.util.*;

/**
 * Решение задачи №40: Сравнение объектов кастомного класса (Person).
 * Содержит класс Person, реализующий Comparable, и примеры Comparator'ов.
 * Описание: Реализовать интерфейс `Comparable` или предоставить `Comparator`
 * для кастомного класса (например, `Person` по возрасту). (Проверяет: ООП, интерфейсы)
 * Задание: Модифицируйте класс `Person` (с полями `name`, `age`), чтобы он
 * реализовывал интерфейс `Comparable<Person>`. Сравнение должно происходить
 * сначала по возрасту (по возрастанию), а при одинаковом возрасте - по имени
 * (в алфавитном порядке).
 */
public class CompareCustomObjects {

    /**
     * Класс, представляющий человека с именем и возрастом.
     * Реализует интерфейс {@link Comparable<Person>}, чтобы определить
     * "естественный" порядок сортировки объектов Person:
     * сначала по возрасту (по возрастанию), затем по имени (в алфавитном порядке).
     */
    // Класс static, чтобы его можно было инстанцировать без экземпляра CompareCustomObjects
    // Можно вынести в отдельный файл Person.java
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
    // Класс static для удобства использования
    static class PersonAgeComparator implements Comparator<Person> {
        @Override
        public int compare(Person p1, Person p2) {
            // Для большей надежности можно добавить проверки на null
            Objects.requireNonNull(p1, "Person p1 cannot be null");
            Objects.requireNonNull(p2, "Person p2 cannot be null");
            return Integer.compare(p1.getAge(), p2.getAge());
        }
    }

    /**
     * Внешний компаратор для сравнения объектов Person ТОЛЬКО по имени (алфавитный порядок).
     */
    // Класс static для удобства использования
    static class PersonNameComparator implements Comparator<Person> {
        @Override
        public int compare(Person p1, Person p2) {
            Objects.requireNonNull(p1, "Person p1 cannot be null");
            Objects.requireNonNull(p2, "Person p2 cannot be null");
            return p1.getName().compareTo(p2.getName());
        }
    }
}
