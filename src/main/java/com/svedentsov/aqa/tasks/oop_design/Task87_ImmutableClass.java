package com.svedentsov.aqa.tasks.oop_design;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Решение задачи №87: Создать Immutable (неизменяемый) класс.
 * Содержит два примера: один с примитивными полями, другой с изменяемым полем List.
 */
public class Task87_ImmutableClass {

    /**
     * Пример неизменяемого (immutable) класса, представляющего точку с примитивными полями.
     * Неизменяемость достигается за счет:
     * 1. Объявления класса как {@code final} (предотвращает наследование).
     * 2. Объявления всех полей как {@code private final} (инициализация только в конструкторе).
     * 3. Отсутствия сеттеров или методов, изменяющих состояние.
     * 4. Так как поля `x` и `y` примитивные, защитное копирование не требуется.
     */
    public static final class ImmutablePointSimple { // 1. Класс final
        private final int x;       // 2. Поля private final
        private final int y;

        /**
         * Конструктор для инициализации final полей.
         *
         * @param x Координата X.
         * @param y Координата Y.
         */
        public ImmutablePointSimple(int x, int y) { // 3. Инициализация в конструкторе
            this.x = x;
            this.y = y;
        }

        /**
         * Возвращает координату X.
         *
         * @return координата X.
         */
        public int getX() {
            return x;
        } // 4. Только геттеры

        /**
         * Возвращает координату Y.
         *
         * @return координата Y.
         */
        public int getY() {
            return y;
        } // 4. Только геттеры

        @Override
        public String toString() {
            return "ImmutablePointSimple{x=" + x + ", y=" + y + '}';
        }

        @Override
        public boolean equals(Object o) { /*...*/
            return true;
        } // Упрощено

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    /**
     * Пример неизменяемого класса, содержащего ссылку на изменяемый объект (List).
     * Демонстрирует необходимость защитного копирования изменяемых полей.
     * Правила неизменяемости:
     * 1. Класс объявлен `final`.
     * 2. Все поля `private final`.
     * 3. Инициализация всех полей в конструкторе.
     * 4. Нет сеттеров/модифицирующих методов.
     * 5. **Защитное копирование** изменяемых полей при получении их в конструкторе.
     * 6. **Защитное копирование** или возврат неизменяемого представления изменяемых полей в геттерах.
     */
    public static final class ImmutableUserProfile {
        private final String username;              // String - иммутабельный
        private final List<String> permissions;     // List - изменяемый!

        /**
         * Конструктор. Выполняет защитное копирование списка permissions.
         *
         * @param username    Имя пользователя (не null).
         * @param permissions Список разрешений (не null, содержимое копируется).
         */
        public ImmutableUserProfile(String username, List<String> permissions) {
            this.username = Objects.requireNonNull(username);
            Objects.requireNonNull(permissions);

            // 5. ЗАЩИТНОЕ КОПИРОВАНИЕ В КОНСТРУКТОРЕ:
            // Создаем новую, независимую копию переданного списка.
            // List.copyOf (Java 9+) создает неизменяемую копию.
            this.permissions = List.copyOf(permissions);
            // Альтернатива (до Java 9 или если нужна изменяемая внутренняя копия):
            // this.permissions = new ArrayList<>(permissions);
            // Если бы permissions был `final List`, то нужна была бы обертка:
            // this.permissions = Collections.unmodifiableList(new ArrayList<>(permissions));
        }

        /**
         * Возвращает имя пользователя.
         */
        public String getUsername() {
            return username;
        }

        /**
         * Возвращает НЕИЗМЕНЯЕМОЕ представление списка разрешений.
         * Предотвращает модификацию внутреннего состояния через полученный список.
         *
         * @return Неизменяемый список разрешений.
         */
        public List<String> getPermissions() {
            // 6. ЗАЩИТА В ГЕТТЕРЕ:
            // Так как List.copyOf уже создал неизменяемый список, можно вернуть его напрямую.
            return permissions;
            // Если бы this.permissions был изменяемым ArrayList, нужно было бы вернуть
            // НЕИЗМЕНЯЕМОЕ ПРЕДСТАВЛЕНИЕ или КОПИЮ:
            // return Collections.unmodifiableList(this.permissions); // Представление
            // или
            // return new ArrayList<>(this.permissions); // Копия
        }

        @Override
        public String toString() {
            return "ImmutableUserProfile{username='" + username + "', permissions=" + permissions + '}';
        }

        @Override
        public boolean equals(Object o) { /*...*/
            return true;
        } // Упрощено

        @Override
        public int hashCode() {
            return Objects.hash(username, permissions);
        }
    }

    /**
     * Точка входа для демонстрации использования неизменяемых классов.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        System.out.println("--- ImmutablePointSimple (Примитивные поля) ---");
        ImmutablePointSimple p1 = new ImmutablePointSimple(5, -10);
        System.out.println("Point p1: " + p1);
        // Попытка изменить поля приведет к ошибке компиляции.

        System.out.println("\n--- ImmutableUserProfile (С изменяемым полем List) ---");
        // Создаем исходный ИЗМЕНЯЕМЫЙ список
        List<String> roles = new ArrayList<>();
        roles.add("VIEWER");
        roles.add("EDITOR");
        System.out.println("Original mutable list 'roles': " + roles);

        // Создаем неизменяемый профиль, передавая изменяемый список
        ImmutableUserProfile user = new ImmutableUserProfile("testuser", roles);
        System.out.println("Immutable profile 'user' created: " + user);

        // 1. Проверяем, что изменение ИСХОДНОГО списка не влияет на объект user
        System.out.println("\nModifying the original 'roles' list...");
        roles.add("ADMIN"); // Модифицируем исходный список
        System.out.println("Original mutable list 'roles' now: " + roles);
        System.out.println("Immutable profile 'user' (unchanged): " + user); // Должен остаться [VIEWER, EDITOR]

        // 2. Проверяем, что список, полученный из геттера, нельзя изменить
        System.out.println("\nTrying to modify list obtained from user.getPermissions()...");
        List<String> permissionsFromGetter = user.getPermissions();
        try {
            // Эта операция должна выбросить UnsupportedOperationException,
            // так как List.copyOf() или Collections.unmodifiableList() вернули неизменяемый список.
            permissionsFromGetter.add("SUPER_ADMIN");
            System.out.println("   Modification SUCCEEDED (!!! This is WRONG for immutable getter !!!)");
        } catch (UnsupportedOperationException e) {
            System.out.println("   Caught expected UnsupportedOperationException: Cannot modify the returned list.");
        }
        System.out.println("Immutable profile 'user' after trying to modify via getter: " + user); // Должен остаться [VIEWER, EDITOR]

        System.out.println("\nВывод: Правильно реализованный неизменяемый класс защищает свое внутреннее состояние от внешних изменений.");
    }
}
