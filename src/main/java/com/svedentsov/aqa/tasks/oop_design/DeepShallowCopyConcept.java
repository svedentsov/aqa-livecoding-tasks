package com.svedentsov.aqa.tasks.oop_design;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Решение задачи №88: Глубокое и поверхностное копирование (Концепция и Демонстрация).
 * <p>
 * Описание: Объяснить разницу и как реализовать (концептуально).
 * (Проверяет: ООП, управление памятью)
 * <p>
 * Задание: Объясните разницу между поверхностным (shallow) и глубоким (deep)
 * копированием объектов в Java. Приведите пример класса с изменяемым
 * полем-объектом и покажите, как реализовать для него глубокое копирование
 * (например, через копирующий конструктор или метод `clone()`).
 * <p>
 * Пример: Класс `UserProfile` с полем `List<String> permissions`. Показать, как
 * при копировании `UserProfile` создать и новый `ArrayList` для `permissions`.
 */
public class DeepShallowCopyConcept {

    /**
     * Изменяемый класс адреса (реализует Cloneable для примера)
     */
    static class Address implements Cloneable {
        public String city;
        public String street;

        public Address(String city, String street) {
            this.city = city;
            this.street = street;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        @Override
        public String toString() {
            return "Address{'" + city + "', '" + street + "'}";
        }

        @Override
        public boolean equals(Object o) { /*...*/
            return true;
        } // Упрощено

        @Override
        public int hashCode() {
            return Objects.hash(city, street);
        }

        /**
         * Поверхностное копирование (для Address достаточно, т.к. String immutable)
         */
        @Override
        public Address clone() throws CloneNotSupportedException {
            // System.out.println("..Cloning Address (shallow OK)");
            return (Address) super.clone();
        }
    }

    /**
     * Изменяемый класс пользователя с изменяемыми полями (для демонстрации)
     */
    static class MutableUser implements Cloneable {
        public String name;            // Immutable
        public List<String> roles;     // Mutable (List object)
        public Address address;        // Mutable (Address object)

        public MutableUser(String name, List<String> roles, Address address) {
            this.name = name;
            this.roles = roles;     // Сохраняет ссылку
            this.address = address; // Сохраняет ссылку
        }

        // --- Копирующий конструктор (для Deep Copy) ---
        public MutableUser(MutableUser other) {
            System.out.println("... MutableUser Copy Constructor (Deep Copy)");
            this.name = other.name;
            // Глубокое копирование списка строк
            this.roles = (other.roles == null) ? null : new ArrayList<>(other.roles);
            // Глубокое копирование адреса (вызываем clone или создаем новый)
            try {
                this.address = (other.address == null) ? null : other.address.clone();
            } catch (CloneNotSupportedException e) { // Fallback если Address не Cloneable
                this.address = (other.address == null) ? null : new Address(other.address.city, other.address.street);
                System.err.println("Warning: Address clone failed, manual copy used.");
            }
        }

        // --- Shallow Copy (через стандартный clone) ---
        public MutableUser cloneShallow() throws CloneNotSupportedException {
            System.out.println("... MutableUser cloneShallow() using super.clone()");
            return (MutableUser) super.clone();
        }

        // --- Deep Copy (через переопределенный clone) ---
        @Override
        public MutableUser clone() throws CloneNotSupportedException {
            System.out.println("... MutableUser overridden clone() (Deep Copy)");
            // 1. Вызываем super.clone() для поверхностной копии
            MutableUser cloned = (MutableUser) super.clone();
            // 2. Глубоко копируем изменяемые поля
            cloned.roles = (this.roles == null) ? null : new ArrayList<>(this.roles);
            cloned.address = (this.address == null) ? null : this.address.clone(); // Используем clone адреса
            return cloned;
        }

        @Override
        public String toString() {
            return String.format("User[name=%s, roles(id=%s)=%s, address(id=%s)=%s]",
                    name,
                    (roles == null ? "null" : System.identityHashCode(roles)), roles,
                    (address == null ? "null" : System.identityHashCode(address)), address);
        }
    }

    /**
     * Точка входа для обсуждения и демонстрации копирования.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        // --- Концептуальное Обсуждение ---
        discussShallowVsDeepCopy();

        // --- Демонстрация ---
        System.out.println("\n--- Shallow vs Deep Copy Demonstration ---");

        // Создаем оригинал
        List<String> originalRoles = new ArrayList<>(List.of("USER", "REPORTER"));
        Address originalAddress = new Address("London", "Baker St.");
        MutableUser userOriginal = new MutableUser("Sherlock", originalRoles, originalAddress);
        System.out.println("\nOriginal User     : " + userOriginal);

        // 1. Поверхностное копирование
        try {
            MutableUser userShallow = userOriginal.cloneShallow();
            System.out.println("Shallow Copy      : " + userShallow);
            System.out.println("  References equal? roles=" + (userOriginal.roles == userShallow.roles) +
                    ", address=" + (userOriginal.address == userShallow.address)); // true, true

            // Модифицируем через КОПИЮ
            userShallow.roles.add("DETECTIVE");
            userShallow.address.setStreet("Fleet St.");
            System.out.println("  Shallow after mod : " + userShallow);
            System.out.println("  Original after mod: " + userOriginal); // Оригинал ИЗМЕНИЛСЯ!
        } catch (CloneNotSupportedException e) {
            System.err.println("Shallow clone failed: " + e);
        }

        // Восстанавливаем оригинал (для следующих тестов)
        userOriginal.roles.remove("DETECTIVE");
        userOriginal.address.setStreet("Baker St.");
        System.out.println("\nOriginal Restored : " + userOriginal);

        // 2. Глубокое копирование (через конструктор)
        MutableUser userDeep1 = new MutableUser(userOriginal); // Используем копирующий конструктор
        System.out.println("Deep Copy (Constr): " + userDeep1);
        System.out.println("  References equal? roles=" + (userOriginal.roles == userDeep1.roles) +
                ", address=" + (userOriginal.address == userDeep1.address)); // false, false

        // Модифицируем через КОПИЮ
        userDeep1.roles.add("CONSULTANT");
        userDeep1.address.setCity("Paris");
        System.out.println("  Deep Copy 1 after mod: " + userDeep1);
        System.out.println("  Original after mod : " + userOriginal); // Оригинал НЕ изменился!

        // 3. Глубокое копирование (через переопределенный clone)
        try {
            MutableUser userDeep2 = userOriginal.clone(); // Используем наш глубокий clone()
            System.out.println("\nDeep Copy (clone) : " + userDeep2);
            System.out.println("  References equal? roles=" + (userOriginal.roles == userDeep2.roles) +
                    ", address=" + (userOriginal.address == userDeep2.address)); // false, false

            // Модифицируем через КОПИЮ
            userDeep2.roles.remove("USER");
            userDeep2.address.setStreet("Another St.");
            System.out.println("  Deep Copy 2 after mod: " + userDeep2);
            System.out.println("  Original after mod : " + userOriginal); // Оригинал НЕ изменился!
        } catch (CloneNotSupportedException e) {
            System.err.println("Deep clone failed: " + e);
        }
    }

    /**
     * Выводит концептуальное объяснение разницы между Shallow и Deep Copy.
     */
    private static void discussShallowVsDeepCopy() {
        System.out.println("--- Обсуждение Shallow vs Deep Copy ---");
        System.out.println("\nShallow Copy (Поверхностное):");
        System.out.println("  - Копирует только сам объект.");
        System.out.println("  - Поля-примитивы копируются по значению.");
        System.out.println("  - Поля-ссылки копируются как ссылки (указывают на те же объекты).");
        System.out.println("  - Последствие: Изменение вложенного объекта через копию влияет на оригинал.");
        System.out.println("  - Пример: `Object.clone()` по умолчанию.");

        System.out.println("\nDeep Copy (Глубокое):");
        System.out.println("  - Копирует объект и рекурсивно копирует ВСЕ объекты, на которые он ссылается.");
        System.out.println("  - Оригинал и копия полностью независимы.");
        System.out.println("  - Последствие: Изменение вложенного объекта в копии НЕ влияет на оригинал.");
        System.out.println("  - Пример: Копирующий конструктор, переопределенный `clone()`, сериализация/десериализация, спец. библиотеки.");

        System.out.println("\nКогда что использовать?");
        System.out.println("  - Shallow: Если объект содержит только примитивы или неизменяемые (immutable) объекты (String, Integer, etc.), или если совместное использование вложенных объектов допустимо/желательно.");
        System.out.println("  - Deep: Если нужно создать полностью независимую копию объекта с изменяемым состоянием, чтобы изменения в копии не затрагивали оригинал.");
        System.out.println("------------------------------------");
    }
}
