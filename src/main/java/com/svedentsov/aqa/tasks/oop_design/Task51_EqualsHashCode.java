package com.svedentsov.aqa.tasks.oop_design;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Решение задачи №51: Реализация equals() и hashCode().
 * Содержит класс Point с переопределенными методами.
 */
public class Task51_EqualsHashCode {

    /**
     * Класс, представляющий точку на 2D плоскости с координатами x и y.
     * Демонстрирует правильное переопределение методов equals() и hashCode()
     * в соответствии с контрактом Java:
     * 1. Рефлексивность: {@code x.equals(x)} всегда true.
     * 2. Симметричность: {@code x.equals(y)} тогда и только тогда, когда {@code y.equals(x)}.
     * 3. Транзитивность: если {@code x.equals(y)} и {@code y.equals(z)}, то {@code x.equals(z)}.
     * 4. Согласованность: повторные вызовы {@code x.equals(y)} возвращают одно и то же значение,
     * если объекты не изменялись.
     * 5. Сравнение с null: {@code x.equals(null)} всегда false.
     * 6. Контракт hashCode: если {@code x.equals(y)}, то {@code x.hashCode() == y.hashCode()}.
     * (Обратное не обязательно верно).
     */
    static class Point { // Делаем static для доступа из static main в Solution51
        private final int x;
        private final int y;

        /**
         * Создает объект Point.
         *
         * @param x Координата X.
         * @param y Координата Y.
         */
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Возвращает координату X.
         */
        public int getX() {
            return x;
        }

        /**
         * Возвращает координату Y.
         */
        public int getY() {
            return y;
        }

        @Override
        public String toString() {
            return "Point{" + "x=" + x + ", y=" + y + '}';
        }

        /**
         * Сравнивает этот объект Point с другим объектом.
         * Два объекта Point считаются равными, если их координаты x и y совпадают.
         *
         * @param o Объект для сравнения.
         * @return {@code true}, если объекты равны, {@code false} в противном случае.
         */
        @Override
        public boolean equals(Object o) {
            // 1. Проверка на идентичность ссылок (самый быстрый случай)
            if (this == o) return true;
            // 2. Проверка на null и совпадение классов
            //    Используем getClass() == o.getClass() вместо instanceof для строгого соответствия
            //    (чтобы подклассы не считались равными, если не переопределят equals).
            if (o == null || getClass() != o.getClass()) return false;
            // 3. Приведение типа (теперь безопасно)
            Point point = (Point) o;
            // 4. Сравнение всех значимых полей, участвующих в логическом равенстве
            return x == point.x && y == point.y;
        }

        /**
         * Возвращает хеш-код для объекта Point.
         * Реализация соответствует контракту hashCode. Хеш-код вычисляется
         * на основе полей, используемых в {@code equals()} (x и y).
         * Объекты, равные по equals(), должны иметь одинаковый хеш-код.
         *
         * @return Хеш-код объекта.
         */
        @Override
        public int hashCode() {
            // Используем стандартный метод Objects.hash для генерации хеш-кода.
            // Он обрабатывает null (хотя у нас примитивы) и дает хорошее распределение.
            return Objects.hash(x, y);
            /* // Ручная реализация (пример):
               int result = Integer.hashCode(x);
               result = 31 * result + Integer.hashCode(y); // 31 - часто используемое простое число
               return result;
            */
        }
    }

    /**
     * Точка входа для демонстрации работы методов equals() и hashCode() класса Point.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Point p1 = new Point(10, 20);
        Point p2 = new Point(10, 20); // Логически равен p1
        Point p3 = new Point(20, 10); // Другой
        Point p4 = p1;                // Та же ссылка

        System.out.println("p1: " + p1 + ", hashCode: " + p1.hashCode());
        System.out.println("p2: " + p2 + ", hashCode: " + p2.hashCode());
        System.out.println("p3: " + p3 + ", hashCode: " + p3.hashCode());
        System.out.println("p4: " + p4 + ", hashCode: " + p4.hashCode());

        System.out.println("\nСравнения equals():");
        System.out.println("p1.equals(p2): " + p1.equals(p2));       // true
        System.out.println("p1.equals(p3): " + p1.equals(p3));       // false
        System.out.println("p1.equals(p4): " + p1.equals(p4));       // true (рефлексивность)
        System.out.println("p1.equals(null): " + p1.equals(null));    // false
        System.out.println("p1.equals(\"10,20\"): " + p1.equals("10,20"));// false (другой класс)

        System.out.println("\nПроверка контракта hashCode:");
        System.out.println("p1.equals(p2) ? " + p1.equals(p2));
        System.out.println("p1.hashCode() == p2.hashCode() ? " + (p1.hashCode() == p2.hashCode())); // Должно быть true

        System.out.println("\nИспользование в HashMap:");
        // Корректная работа HashMap (и HashSet) зависит от правильной реализации equals/hashCode
        Map<Point, String> map = new HashMap<>();
        map.put(p1, "Точка А");
        map.put(p3, "Точка Б");

        System.out.println("map.containsKey(p1)? " + map.containsKey(p1)); // true
        System.out.println("map.containsKey(p2)? " + map.containsKey(p2)); // true (т.к. p1.equals(p2) и hashCodes равны)
        System.out.println("map.containsKey(p3)? " + map.containsKey(p3)); // true
        System.out.println("map.get(p2): " + map.get(p2)); // "Точка А" (найдет по хеш-коду и equals)
    }
}
