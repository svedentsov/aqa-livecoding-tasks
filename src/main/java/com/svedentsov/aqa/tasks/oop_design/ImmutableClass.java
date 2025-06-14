package com.svedentsov.aqa.tasks.oop_design;

import java.util.List;
import java.util.Objects;

/**
 * Решение задачи №87: Создать Immutable (неизменяемый) класс.
 * Описание: Написать класс, состояние которого нельзя изменить после создания.
 * (Проверяет: ООП, `final`)
 * Задание: Напишите класс `ImmutablePoint` с приватными `final` полями `x` и `y`
 * типа `int`. Сделайте класс `final`, предоставьте только геттеры и конструктор,
 * который инициализирует поля. Объясните, почему этот класс является неизменяемым.
 * Дополнительно показан пример с изменяемым полем (List).
 * Пример: После `ImmutablePoint p = new ImmutablePoint(1, 2);`, состояние `p`
 * изменить нельзя.
 */
public class ImmutableClass {

    // --- Пример 1: Неизменяемый Класс с Примитивными Полями ---

    /**
     * Неизменяемый класс, представляющий точку с примитивными полями `x`, `y`.
     * Принципы неизменяемости:
     * 1. Класс объявлен `final` -> нельзя унаследоваться и изменить поведение.
     * 2. Все поля `private` -> доступ только через методы класса.
     * 3. Все поля `final` -> инициализируются только один раз (в конструкторе).
     * 4. Отсутствуют сеттеры или другие методы, изменяющие состояние полей.
     * 5. Поля `x` и `y` примитивные, их значения копируются, ссылки не передаются.
     */
    public static final class ImmutablePointSimple {
        private final int x;
        private final int y;

        public ImmutablePointSimple(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public String toString() {
            return "ImmutablePointSimple(x=" + x + ", y=" + y + ')';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ImmutablePointSimple p = (ImmutablePointSimple) o;
            return x == p.x && y == p.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    // --- Пример 2: Неизменяемый Класс с Изменяемым Полем (List) ---

    /**
     * Неизменяемый класс профиля пользователя, содержащий изменяемое поле (List).
     * Дополнительные принципы неизменяемости для изменяемых полей:
     * 5. В конструкторе выполняется **защитное копирование** изменяемых объектов,
     * переданных извне. Мы создаем свою, независимую копию.
     * 6. В геттерах для изменяемых полей возвращается либо **неизменяемое
     * представление** (обертка), либо **защитная копия** внутреннего объекта,
     * чтобы внешние изменения не повлияли на внутреннее состояние.
     */
    public static final class ImmutableUserProfile {
        private final String username;           // Неизменяемый String
        private final List<String> permissions;  // Изменяемый List!

        public ImmutableUserProfile(String username, List<String> permissions) {
            this.username = Objects.requireNonNull(username);
            Objects.requireNonNull(permissions);
            // 5. Защитное копирование в конструкторе
            this.permissions = List.copyOf(permissions); // List.copyOf (Java 9+) создает неизменяемую копию
        }

        public String getUsername() {
            return username;
        }

        /**
         * Возвращает разрешения. Так как внутренний список уже неизменяемый
         * (благодаря List.copyOf), его можно вернуть напрямую.
         * Если бы внутренний список был изменяемым (например, ArrayList),
         * нужно было бы вернуть Collections.unmodifiableList(this.permissions)
         * или new ArrayList<>(this.permissions).
         *
         * @return Неизменяемый список разрешений.
         */
        public List<String> getPermissions() {
            // 6. Защита в геттере (уже обеспечена List.copyOf)
            return permissions;
        }

        @Override
        public String toString() {
            return "ImmutableUserProfile{username='" + username + "', permissions=" + permissions + '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) return false;
            ImmutableUserProfile that = (ImmutableUserProfile) o;
            return Objects.equals(username, that.username) && Objects.equals(permissions, that.permissions);
        }

        @Override
        public int hashCode() {
            return Objects.hash(username, permissions);
        }
    }
}
