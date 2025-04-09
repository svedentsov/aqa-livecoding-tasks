package com.svedentsov.aqa.tasks.system_concepts;

import java.util.Optional;

/**
 * Решение задачи №85: Реализация Optional (концептуально).
 * Содержит обсуждение и демонстрацию использования {@link Optional}.
 */
public class Task85_OptionalConcept {

    // --- Вспомогательные методы для демонстрации ---

    /**
     * Метод, который МОЖЕТ вернуть null (старый стиль).
     */
    private static String findUserNameByIdOldStyle(int id) {
        if (id == 10) return "Alice";
        if (id == 20) return "Bob";
        return null; // Пользователь не найден
    }

    /**
     * Метод, который возвращает Optional (новый стиль).
     */
    private static Optional<String> findUserNameByIdOptionalStyle(int id) {
        if (id == 10) return Optional.of("Alice"); // Гарантированно не null
        if (id == 20) return Optional.of("Bob");
        return Optional.empty(); // Явно указываем на отсутствие значения
    }

    // Метод, возвращающий Optional другого типа
    private static Optional<Integer> getUserAge(String username) {
        if ("Alice".equals(username)) return Optional.of(30);
        if ("Bob".equals(username)) return Optional.of(25);
        return Optional.empty();
    }

    /**
     * Проводит концептуальное обсуждение класса {@link Optional}
     * и демонстрирует его основные методы и паттерны использования.
     */
    public static void discussAndDemonstrateOptional() {
        System.out.println("Обсуждение и демонстрация Optional<T>:");
        System.out.println("=======================================");
        System.out.println("1. Проблема до Java 8: NullPointerException (NPE)");
        System.out.println("   - Методы могли возвращать `null` для обозначения отсутствия результата.");
        System.out.println("   - Вызов метода у `null` ссылки приводил к NPE.");
        System.out.println("   - Требовались многочисленные проверки `if (result != null) {...}`.");

        System.out.println("\n2. Optional<T> - Решение:");
        System.out.println("   - Класс-обертка (контейнер), который может содержать ОДНО не-null значение или быть пустым.");
        System.out.println("   - Цель: Сделать возможность отсутствия значения ЯВНОЙ частью API метода (в его возвращаемом типе).");
        System.out.println("   - НЕ предназначен для замены всех `null`. НЕ рекомендуется для полей класса или параметров методов.");
        System.out.println("   - Идеален для возвращаемых значений методов, где отсутствие результата - нормальный сценарий.");

        System.out.println("\n3. Создание Optional:");
        Optional<String> optWithValue = Optional.of("Значение есть"); // Ошибка если передать null
        Optional<String> optMaybeNull = Optional.ofNullable(findUserNameByIdOldStyle(5)); // Безопасно для null
        Optional<String> optEmpty = Optional.empty();                     // Явно пустой

        System.out.println("   - Optional.of(\"...\"): " + optWithValue);
        System.out.println("   - Optional.ofNullable(null): " + optMaybeNull);
        System.out.println("   - Optional.empty(): " + optEmpty);

        System.out.println("\n4. Проверка на наличие значения:");
        System.out.println("   - optWithValue.isPresent(): " + optWithValue.isPresent()); // true (устаревший стиль)
        System.out.println("   - optMaybeNull.isPresent(): " + optMaybeNull.isPresent()); // false
        System.out.println("   - optWithValue.isEmpty(): " + optWithValue.isEmpty());   // false (Java 11+)
        System.out.println("   - optEmpty.isEmpty(): " + optEmpty.isEmpty());         // true (Java 11+)

        System.out.println("\n5. Безопасное получение значения:");
        // orElse: вернуть значение или default, если пусто
        String v1 = optWithValue.orElse("Default");        // "Значение есть"
        String v2 = optEmpty.orElse("Default");            // "Default"
        System.out.println("   - optWithValue.orElse(\"Default\"): " + v1);
        System.out.println("   - optEmpty.orElse(\"Default\"): " + v2);

        // orElseGet: вернуть значение или вычислить default лениво (через Supplier)
        String v3 = optEmpty.orElseGet(() -> {
            System.out.println("      (Вычисляем default...)");
            return "Вычислено";
        }); // "Вычислено"
        System.out.println("   - optEmpty.orElseGet(...): " + v3);
        String v4 = optWithValue.orElseGet(() -> { // Supplier не будет вызван
            System.out.println("      (Это не должно быть выведено)");
            return "Вычислено";
        });
        System.out.println("   - optWithValue.orElseGet(...): " + v4); // "Значение есть"


        // orElseThrow: вернуть значение или бросить исключение
        try {
            optEmpty.orElseThrow(() -> new IllegalStateException("Значение не найдено!"));
        } catch (Exception e) {
            System.out.println("   - optEmpty.orElseThrow(): Брошено " + e.getClass().getSimpleName());
        }
        String v5 = optWithValue.orElseThrow(); // Возвращает "Значение есть"
        System.out.println("   - optWithValue.orElseThrow(): " + v5);

        // get() - НЕБЕЗОПАСНО! Использовать только после isPresent() (что анти-паттерн)
        // System.out.println(optEmpty.get()); // Бросит NoSuchElementException

        System.out.println("\n6. Выполнение действия при наличии значения:");
        // ifPresent: выполнить Consumer, если значение есть
        optWithValue.ifPresent(val -> System.out.println("   - ifPresent для optWithValue: Значение = " + val));
        optEmpty.ifPresent(val -> System.out.println("   - ifPresent для optEmpty: Это не будет вызвано"));

        // ifPresentOrElse (Java 9+): выполнить одно из двух действий
        optWithValue.ifPresentOrElse(
                val -> System.out.println("   - ifPresentOrElse для optWithValue: Есть значение: " + val),
                () -> System.out.println("   - ifPresentOrElse для optWithValue: Значения нет")
        );
        optEmpty.ifPresentOrElse(
                val -> System.out.println("   - ifPresentOrElse для optEmpty: Есть значение: " + val),
                () -> System.out.println("   - ifPresentOrElse для optEmpty: Значения нет")
        );

        System.out.println("\n7. Функциональные методы (map, flatMap, filter):");
        // map: применить функцию к значению внутри Optional, вернуть Optional с результатом
        Optional<Integer> lengthOpt = optWithValue.map(String::length); // Optional<Integer>[13]
        Optional<Integer> emptyLengthOpt = optEmpty.map(String::length); // Optional.empty
        System.out.println("   - optWithValue.map(String::length): " + lengthOpt);
        System.out.println("   - optEmpty.map(String::length): " + emptyLengthOpt);

        // filter: вернуть Optional с тем же значением, если оно удовлетворяет предикату, иначе empty
        Optional<String> filteredOpt = optWithValue.filter(s -> s.contains("есть")); // Optional[Значение есть]
        Optional<String> filteredOpt2 = optWithValue.filter(s -> s.length() < 5); // Optional.empty
        System.out.println("   - optWithValue.filter(s -> s.contains(\"есть\")): " + filteredOpt);
        System.out.println("   - optWithValue.filter(s -> s.length() < 5): " + filteredOpt2);

        // flatMap: применить функцию, которая сама возвращает Optional, и "развернуть" результат
        Optional<Integer> ageOpt = findUserNameByIdOptionalStyle(10) // Optional["Alice"]
                .flatMap(Task85_OptionalConcept::getUserAge); // Применяем getUserAge -> Optional[30]
        System.out.println("   - flatMap для ID 10: " + ageOpt); // Optional[30]
        Optional<Integer> ageOpt2 = findUserNameByIdOptionalStyle(5) // Optional.empty
                .flatMap(Task85_OptionalConcept::getUserAge); // flatMap не вызывается
        System.out.println("   - flatMap для ID 5: " + ageOpt2); // Optional.empty


        System.out.println("\n8. Итог: Optional помогает писать более чистый и безопасный код, явно обрабатывая отсутствие значений.");
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
}