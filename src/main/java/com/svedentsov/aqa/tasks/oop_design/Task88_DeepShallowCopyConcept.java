package com.svedentsov.aqa.tasks.oop_design;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Решение задачи №88: Глубокое и поверхностное копирование (Концепция и Демонстрация).
 */
public class Task88_DeepShallowCopyConcept {

    // --- Вспомогательные классы для демонстрации ---

    /**
     * Изменяемый класс адреса. Реализует Cloneable для примера clone().
     */
    static class Address implements Cloneable {
        public String city;
        public String street;

        public Address(String city, String street) {
            this.city = city;
            this.street = street;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        @Override
        public String toString() {
            return "Address{city='" + city + "', street='" + street + "'}";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Address address = (Address) o;
            return Objects.equals(city, address.city) && Objects.equals(street, address.street);
        }

        @Override
        public int hashCode() {
            return Objects.hash(city, street);
        }

        /**
         * Выполняет поверхностное копирование для Address.
         */
        @Override
        public Address clone() throws CloneNotSupportedException {
            // Для Address с полями String поверхностного копирования достаточно, т.к. String immutable.
            System.out.println("... Cloning Address (shallow is ok here)");
            return (Address) super.clone();
        }
    }

    /**
     * Пример изменяемого класса пользователя с изменяемыми полями (List<String> и Address),
     * для демонстрации разницы между поверхностным и глубоким копированием.
     * Реализует {@link Cloneable} для показа обоих типов копирования через clone().
     */
    static class MutableUser implements Cloneable {
        private String name;            // Иммутабельное поле
        private List<String> roles;     // Изменяемое поле (ссылка на список)
        private Address address;        // Изменяемое поле (ссылка на объект Address)

        /**
         * Конструктор
         */
        public MutableUser(String name, List<String> roles, Address address) {
            this.name = name;
            this.roles = roles; // Сохраняем переданную ссылку!
            this.address = address; // Сохраняем переданную ссылку!
        }

        // --- Геттеры и Сеттеры ---
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getRoles() {
            return roles;
        }

        public void setRoles(List<String> roles) {
            this.roles = roles;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        @Override
        public String toString() {
            // Выводим identityHashCode для демонстрации ссылок на объекты
            return "MutableUser{name='" + name +
                    "', roles(id=" + (roles == null ? "null" : System.identityHashCode(roles)) + ")=" + roles +
                    ", address(id=" + (address == null ? "null" : System.identityHashCode(address)) + ")=" + address + '}';
        }

        @Override
        public boolean equals(Object o) { /* ... */
            return true;
        } // Упрощено

        @Override
        public int hashCode() {
            return Objects.hash(name, roles, address);
        }


        // --- Методы Копирования ---

        /**
         * Реализует ПОВЕРХНОСТНОЕ копирование (Shallow Copy) с использованием {@code Object.clone()}.
         * Копируются только ссылки на изменяемые объекты `roles` и `address`.
         *
         * @return Поверхностная копия объекта MutableUser.
         * @throws CloneNotSupportedException если клонирование не поддерживается.
         */
        public MutableUser cloneShallow() throws CloneNotSupportedException {
            System.out.println("... Performing shallow clone using super.clone()");
            // super.clone() копирует примитивы по значению, а ссылки на объекты - по ссылке.
            return (MutableUser) super.clone();
        }

        /**
         * Реализует ГЛУБОКОЕ копирование (Deep Copy) с использованием копирующего конструктора.
         *
         * @return Глубокая копия объекта MutableUser.
         */
        public MutableUser deepCopyUsingConstructor() {
            System.out.println("... Performing deep copy using copy constructor");
            return new MutableUser(this); // Вызов копирующего конструктора
        }

        /**
         * Копирующий конструктор для ГЛУБОКОГО копирования.
         * Создает новые независимые копии для всех изменяемых полей.
         *
         * @param other Оригинальный объект MutableUser для копирования.
         */
        public MutableUser(MutableUser other) {
            System.out.println("... Inside copy constructor (deep copy)");
            this.name = other.name; // String immutable, можно копировать ссылку

            // Глубокое копирование списка: создаем новый ArrayList и копируем элементы.
            // Если бы список содержал изменяемые объекты, их тоже нужно было бы глубоко копировать.
            this.roles = (other.roles == null) ? null : new ArrayList<>(other.roles);

            // Глубокое копирование объекта Address: вызываем его метод clone() или создаем новый.
            try {
                // Пытаемся клонировать Address (предполагая, что он Cloneable)
                this.address = (other.address == null) ? null : other.address.clone();
            } catch (CloneNotSupportedException e) {
                // Если Address не Cloneable или clone() не реализован правильно,
                // копируем вручную (если известны поля).
                System.err.println("Address clone failed, copying manually.");
                this.address = (other.address == null) ? null :
                        new Address(other.address.getCity(), other.address.getStreet());
            }
        }

        /**
         * Реализует ГЛУБОКОЕ копирование (Deep Copy) через переопределение {@code clone()}.
         * Это предпочтительный способ реализации глубокого копирования через clone().
         *
         * @return Глубокая копия объекта MutableUser.
         * @throws CloneNotSupportedException если клонирование не поддерживается.
         */
        @Override
        public MutableUser clone() throws CloneNotSupportedException {
            System.out.println("... Performing deep copy using overridden clone()");
            // 1. Выполняем поверхностное копирование через super.clone()
            MutableUser cloned = (MutableUser) super.clone();
            // 2. Глубоко копируем изменяемые поля
            cloned.roles = (this.roles == null) ? null : new ArrayList<>(this.roles);
            cloned.address = (this.address == null) ? null : this.address.clone(); // Используем clone() для Address
            return cloned;
        }
    }

    /**
     * Точка входа для обсуждения и демонстрации разницы между
     * поверхностным (Shallow Copy) и глубоким (Deep Copy) копированием.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        System.out.println("Обсуждение Shallow vs Deep Copy:");
        System.out.println("================================");
        System.out.println("Shallow Copy (Поверхностное):");
        System.out.println("  - Копия объекта верхнего уровня создается.");
        System.out.println("  - Поля-примитивы копируются по значению.");
        System.out.println("  - Поля-ссылки на другие объекты копируются ПО ССЫЛКЕ.");
        System.out.println("  - => Оригинал и копия совместно используют вложенные объекты.");
        System.out.println("  - => Изменение вложенного объекта через копию затронет и оригинал!");
        System.out.println("  - Реализация: `Object.clone()` по умолчанию, простое присваивание ссылок.");

        System.out.println("\nDeep Copy (Глубокое):");
        System.out.println("  - Копия объекта верхнего уровня создается.");
        System.out.println("  - Поля-примитивы копируются по значению.");
        System.out.println("  - Для полей-ссылок на объекты создаются НОВЫЕ КОПИИ этих объектов (рекурсивно).");
        System.out.println("  - => Оригинал и копия полностью независимы.");
        System.out.println("  - => Изменение вложенного объекта в копии НЕ влияет на оригинал.");
        System.out.println("  - Реализация: Копирующий конструктор, переопределенный `clone()` с глубоким копированием полей, библиотеки (например, через сериализацию).");
        System.out.println("================================");

        // --- Создаем оригинал ---
        List<String> originalRoles = new ArrayList<>(List.of("USER"));
        Address originalAddress = new Address("Moscow", "Arbat St.");
        MutableUser userOriginal = new MutableUser("Alice", originalRoles, originalAddress);
        System.out.println("\nOriginal User: " + userOriginal);

        // --- Демонстрация Поверхностного копирования ---
        System.out.println("\n--- Shallow Copy Demo (using cloneShallow) ---");
        try {
            MutableUser userShallow = userOriginal.cloneShallow();
            System.out.println("Shallow Copy:  " + userShallow);
            // Проверяем ссылки на изменяемые объекты
            System.out.println("  userOriginal.roles == userShallow.roles ? "
                    + (userOriginal.getRoles() == userShallow.getRoles()));     // true
            System.out.println("  userOriginal.address == userShallow.address ? "
                    + (userOriginal.getAddress() == userShallow.getAddress())); // true

            System.out.println("\n  Modifying roles list via shallow copy...");
            userShallow.getRoles().add("GUEST");
            System.out.println("  Modifying address city via shallow copy...");
            userShallow.getAddress().setCity("Tver");

            System.out.println("  Shallow Copy after modification: " + userShallow);
            System.out.println("  Original User after modification:  " + userOriginal); // Оригинал изменился!

        } catch (CloneNotSupportedException e) {
            System.err.println("Shallow clone failed: " + e.getMessage());
        }

        // --- Восстановление оригинала ---
        userOriginal.getRoles().remove("GUEST"); // Убираем изменения
        userOriginal.getAddress().setCity("Moscow");
        System.out.println("\nOriginal User restored: " + userOriginal);


        // --- Демонстрация Глубокого копирования (через конструктор) ---
        System.out.println("\n--- Deep Copy Demo (using Copy Constructor) ---");
        MutableUser userDeepCopy1 = userOriginal.deepCopyUsingConstructor();
        System.out.println("Deep Copy 1:   " + userDeepCopy1);
        // Проверяем ссылки на изменяемые объекты
        System.out.println("  userOriginal.roles == userDeepCopy1.roles ? "
                + (userOriginal.getRoles() == userDeepCopy1.getRoles()));     // false
        System.out.println("  userOriginal.address == userDeepCopy1.address ? "
                + (userOriginal.getAddress() == userDeepCopy1.getAddress())); // false

        System.out.println("\n  Modifying roles list via deep copy 1...");
        userDeepCopy1.getRoles().add("ADMIN");
        System.out.println("  Modifying address city via deep copy 1...");
        userDeepCopy1.getAddress().setCity("Kazan");

        System.out.println("  Deep Copy 1 after modification: " + userDeepCopy1);
        System.out.println("  Original User after modification: " + userOriginal); // Оригинал НЕ изменился!


        // --- Демонстрация Глубокого копирования (через переопределенный clone) ---
        System.out.println("\n--- Deep Copy Demo (using overridden clone) ---");
        try {
            MutableUser userDeepCopy2 = userOriginal.clone(); // Вызывает наш глубокий clone()
            System.out.println("Deep Copy 2:   " + userDeepCopy2);
            System.out.println("  userOriginal.roles == userDeepCopy2.roles ? "
                    + (userOriginal.getRoles() == userDeepCopy2.getRoles()));     // false
            System.out.println("  userOriginal.address == userDeepCopy2.address ? "
                    + (userOriginal.getAddress() == userDeepCopy2.getAddress())); // false

            System.out.println("\n  Modifying roles list via deep copy 2...");
            userDeepCopy2.getRoles().add("VIEWER");
            System.out.println("  Modifying address street via deep copy 2...");
            userDeepCopy2.getAddress().setStreet("Mira Ave.");

            System.out.println("  Deep Copy 2 after modification: " + userDeepCopy2);
            System.out.println("  Original User after modification: " + userOriginal); // Оригинал НЕ изменился!
        } catch (CloneNotSupportedException e) {
            System.err.println("Deep clone failed: " + e.getMessage());
        }
    }
}
