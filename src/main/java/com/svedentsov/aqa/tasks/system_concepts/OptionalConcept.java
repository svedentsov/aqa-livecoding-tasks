package com.svedentsov.aqa.tasks.system_concepts;

import java.util.Optional;

/**
 * Решение задачи №85: Реализация Optional (концептуально).
 * <p>
 * Описание: Объяснить идею `Optional` и как его использовать.
 * (Проверяет: знание современных возможностей Java)
 * <p>
 * Задание: Объясните своими словами, зачем в Java был введен класс `Optional<T>`.
 * Приведите примеры использования `Optional.ofNullable()`, `orElse()`, `isPresent()`,
 * `ifPresent()`, `map()`, `flatMap()`, чтобы избежать `NullPointerException`.
 * <p>
 * Пример: Показать код до и после использования `Optional` для работы
 * с потенциально `null` объектом.
 */
public class OptionalConcept {

    /**
     * Проводит концептуальное обсуждение класса {@link Optional}
     * и демонстрирует его основные методы и паттерны использования.
     */
    public static void discussAndDemonstrateOptional() {
        System.out.println("--- Обсуждение и Демонстрация Optional<T> ---");

        System.out.println("\n[1] Проблема до Java 8: NullPointerException (NPE)");
        System.out.println("    - `null` использовался для обозначения отсутствия значения.");
        System.out.println("    - Вызов метода на `null` ссылке -> NPE.");
        System.out.println("    - Необходимость частых проверок `if (x != null) ...`.");

        System.out.println("\n[2] Optional<T> - Решение:");
        System.out.println("    - Класс-контейнер: может содержать одно НЕ-null значение или быть пустым.");
        System.out.println("    - Цель: Явно выразить в сигнатуре метода возможность отсутствия результата.");
        System.out.println("    - Применение: Идеально для возвращаемых значений методов.");
        System.out.println("    - НЕ рекомендуется: Для полей класса, параметров методов, коллекций (есть лучшие способы).");

        System.out.println("\n[3] Создание Optional:");
        Optional<String> nonEmpty = Optional.of("Data");        // Ошибка, если передать null
        Optional<String> nullableVal = Optional.ofNullable(findUserNameByIdOldStyle(20)); // "Bob" -> Optional["Bob"]
        Optional<String> nullableNull = Optional.ofNullable(findUserNameByIdOldStyle(5)); // null -> Optional.empty
        Optional<String> empty = Optional.empty();             // Явно пустой

        System.out.println("    Optional.of(\"Data\")          : " + nonEmpty);
        System.out.println("    Optional.ofNullable(\"Bob\") : " + nullableVal);
        System.out.println("    Optional.ofNullable(null)  : " + nullableNull);
        System.out.println("    Optional.empty()         : " + empty);
        System.out.println("    ---");

        System.out.println("\n[4] Проверка наличия значения:");
        System.out.println("    nonEmpty.isPresent() : " + nonEmpty.isPresent()); // true (устаревший стиль)
        System.out.println("    empty.isPresent()    : " + empty.isPresent());    // false
        System.out.println("    nonEmpty.isEmpty()   : " + nonEmpty.isEmpty());   // false (с Java 11+)
        System.out.println("    empty.isEmpty()      : " + empty.isEmpty());      // true (с Java 11+)
        System.out.println("    ---");

        System.out.println("\n[5] Получение значения (безопасные способы):");
        // orElse(defaultValue): Вернуть значение или default, если пусто.
        String name1 = nullableNull.orElse("Unknown User");
        System.out.println("    nullableNull.orElse(\"Unknown\") : " + name1); // Unknown User
        String name2 = nullableVal.orElse("Unknown User");
        System.out.println("    nullableVal.orElse(\"Unknown\")  : " + name2); // Bob

        // orElseGet(supplier): Вернуть значение или ВЫЧИСЛИТЬ default (лениво).
        String name3 = nullableNull.orElseGet(() -> {
            System.out.println("      (Вычисляем default имя...)");
            return generateDefaultName();
        });
        System.out.println("    nullableNull.orElseGet(...)    : " + name3); // DefaultName
        String name4 = nullableVal.orElseGet(OptionalConcept::generateDefaultName); // Supplier не вызовется
        System.out.println("    nullableVal.orElseGet(...)     : " + name4); // Bob

        // orElseThrow(): Вернуть значение или бросить исключение (по умолчанию NoSuchElementException).
        try {
            empty.orElseThrow();
        } catch (Exception e) {
            System.out.println("    empty.orElseThrow()          : Caught " + e.getClass().getSimpleName());
        }
        String name5 = nonEmpty.orElseThrow();
        System.out.println("    nonEmpty.orElseThrow()         : " + name5); // Data

        // orElseThrow(exceptionSupplier): Вернуть значение или бросить указанное исключение.
        try {
            empty.orElseThrow(() -> new IllegalArgumentException("Value not found!"));
        } catch (Exception e) {
            System.out.println("    empty.orElseThrow(Supplier)  : Caught " + e.getClass().getSimpleName());
        }
        System.out.println("    ---");

        System.out.println("\n[6] Выполнение действия при наличии значения:");
        // ifPresent(consumer): Выполнить действие, если значение есть.
        nonEmpty.ifPresent(val -> System.out.println("    nonEmpty.ifPresent(): Value is " + val));
        empty.ifPresent(val -> System.out.println("    empty.ifPresent(): This won't print"));

        // ifPresentOrElse(consumer, emptyAction): Выполнить одно из двух действий. (Java 9+)
        nullableVal.ifPresentOrElse(
                val -> System.out.println("    nullableVal.ifPresentOrElse(): User found: " + val),
                () -> System.out.println("    nullableVal.ifPresentOrElse(): User not found")
        );
        nullableNull.ifPresentOrElse(
                val -> System.out.println("    nullableNull.ifPresentOrElse(): User found: " + val),
                () -> System.out.println("    nullableNull.ifPresentOrElse(): User not found")
        );
        System.out.println("    ---");

        System.out.println("\n[7] Трансформация значения (map, flatMap):");
        // map(function): Применить функцию к значению, если оно есть, вернуть Optional с результатом.
        Optional<Integer> nameLengthOpt = nullableVal.map(String::length);
        System.out.println("    nullableVal.map(String::length): " + nameLengthOpt); // Optional[3]
        Optional<Integer> emptyMapOpt = empty.map(String::length);
        System.out.println("    empty.map(String::length)      : " + emptyMapOpt); // Optional.empty

        // flatMap(functionReturningOptional): Применить функцию, возвращающую Optional, и "развернуть".
        Optional<Integer> ageOpt = findUserNameByIdOptionalStyle(10) // -> Optional["Alice"]
                .flatMap(OptionalConcept::getUserAge); // -> Optional[30]
        System.out.println("    flatMap user 10 age            : " + ageOpt); // Optional[30]
        Optional<Integer> ageOptNotFound = findUserNameByIdOptionalStyle(5) // -> Optional.empty
                .flatMap(OptionalConcept::getUserAge); // -> Optional.empty
        System.out.println("    flatMap user 5 age             : " + ageOptNotFound); // Optional.empty

        // Пример цепочки вызовов
        String userInfo = findUserNameByIdOptionalStyle(20) // Optional["Bob"]
                .flatMap(OptionalConcept::getUserAge) // Optional[25]
                .map(age -> "Bob's age is " + age) // Optional["Bob's age is 25"]
                .orElse("User or age not found"); // Получаем строку
        System.out.println("    Chained call for user 20       : " + userInfo);

        System.out.println("\n[8] Вывод: Optional - это инструмент для явной обработки возможного отсутствия значения, улучшающий читаемость и безопасность кода по сравнению с возвратом null.");
        System.out.println("=======================================");
    }

    /**
     * Главный метод для запуска обсуждения и демонстрации Optional.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        discussAndDemonstrateOptional();
    }

    /**
     * Метод, который МОЖЕТ вернуть null (старый стиль).
     */
    private static String findUserNameByIdOldStyle(int id) {
        if (id == 10) return "Alice";
        if (id == 20) return "Bob";
        return null;
    }

    /**
     * Метод, который возвращает Optional (новый стиль).
     */
    private static Optional<String> findUserNameByIdOptionalStyle(int id) {
        if (id == 10) return Optional.of("Alice");
        if (id == 20) return Optional.of("Bob");
        return Optional.empty();
    }

    /**
     * Метод, возвращающий Optional другого типа.
     */
    private static Optional<Integer> getUserAge(String username) {
        if ("Alice".equals(username)) return Optional.of(30);
        if ("Bob".equals(username)) return Optional.of(25);
        return Optional.empty();
    }

    /**
     * Вспомогательный метод для orElseGet.
     */
    private static String generateDefaultName() {
        return "DefaultName";
    }
}
