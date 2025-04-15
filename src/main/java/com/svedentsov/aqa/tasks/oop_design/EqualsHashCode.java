package com.svedentsov.aqa.tasks.oop_design;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Решение задачи №51: Реализация equals() и hashCode().
 * <p>
 * Описание: Объяснить контракт между ними.
 * (Проверяет: ООП, контракты Java)
 * <p>
 * Задание: Для класса `Point` с полями `int x` и `int y`, переопределите методы
 * `equals(Object o)` и `hashCode()` так, чтобы они соответствовали контракту:
 * равные объекты (`p1.x == p2.x && p1.y == p2.y`) должны иметь одинаковый `hashCode`.
 * <p>
 * Пример: `new Point(1, 2).equals(new Point(1, 2))` -> `true`.
 * `new Point(1, 2).equals(new Point(2, 1))` -> `false`.
 * `new Point(1, 2).hashCode()` должен быть равен `new Point(1, 2).hashCode()`.
 */
public class EqualsHashCode {

    // --- Класс Point ---

    /**
     * Класс, представляющий точку на 2D плоскости с координатами x и y.
     * Демонстрирует правильное переопределение методов equals() и hashCode()
     * в соответствии с контрактом Java.
     * <p>
     * Контракт equals():
     * 1. Рефлексивность: x.equals(x) == true.
     * 2. Симметричность: x.equals(y) == y.equals(x).
     * 3. Транзитивность: если x.equals(y) и y.equals(z), то x.equals(z).
     * 4. Согласованность: повторные вызовы x.equals(y) возвращают то же значение.
     * 5. Сравнение с null: x.equals(null) == false.
     * <p>
     * Контракт hashCode():
     * 1. Согласованность: повторные вызовы x.hashCode() возвращают то же значение.
     * 2. Связь с equals(): если x.equals(y), то x.hashCode() == y.hashCode().
     * (Обратное не обязательно верно: разные объекты могут иметь одинаковый хеш-код - коллизия).
     */
    static class Point { // Делаем static для доступа из static main
        private final int x;
        private final int y;

        /**
         * Создает объект Point.
         */
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * @return Координата X.
         */
        public int getX() {
            return x;
        }

        /**
         * @return Координата Y.
         */
        public int getY() {
            return y;
        }

        @Override
        public String toString() {
            return "Point(x=" + x + ", y=" + y + ')';
        }

        /**
         * Сравнивает этот объект Point с другим объектом.
         * Равны, если `o` это Point и координаты x и y совпадают.
         */
        @Override
        public boolean equals(Object o) {
            // 1. Проверка ссылок
            if (this == o) return true;
            // 2. Проверка на null и совпадение классов
            if (o == null || getClass() != o.getClass()) return false;
            // 3. Приведение типа
            Point otherPoint = (Point) o;
            // 4. Сравнение полей
            return this.x == otherPoint.x && this.y == otherPoint.y;
        }

        /**
         * Возвращает хеш-код для объекта Point.
         * Основан на полях, используемых в equals() (x и y).
         */
        @Override
        public int hashCode() {
            // Используем утилиту Objects.hash для простоты и хорошего распределения
            return Objects.hash(x, y);
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

        System.out.println("--- Demonstrating equals() and hashCode() for Point ---");

        System.out.println("Points:");
        System.out.println("p1: " + p1 + ", hashCode: " + p1.hashCode());
        System.out.println("p2: " + p2 + ", hashCode: " + p2.hashCode());
        System.out.println("p3: " + p3 + ", hashCode: " + p3.hashCode());
        System.out.println("p4: " + p4 + ", hashCode: " + p4.hashCode());

        System.out.println("\n--- equals() Comparisons ---");
        System.out.println("p1.equals(p2): " + p1.equals(p2));       // Expected: true
        System.out.println("p2.equals(p1): " + p2.equals(p1));       // Expected: true (Symmetry)
        System.out.println("p1.equals(p3): " + p1.equals(p3));       // Expected: false
        System.out.println("p1.equals(p4): " + p1.equals(p4));       // Expected: true (Reflexivity/Identity)
        System.out.println("p1.equals(null): " + p1.equals(null));    // Expected: false
        System.out.println("p1.equals(\"Point\"): " + p1.equals("Point"));// Expected: false (Different class)

        System.out.println("\n--- hashCode() Contract Check ---");
        boolean equalsCheck = p1.equals(p2);
        boolean hashCodeCheck = p1.hashCode() == p2.hashCode();
        System.out.println("p1.equals(p2) : " + equalsCheck);
        System.out.println("p1.hashCode() == p2.hashCode() : " + hashCodeCheck);
        if (equalsCheck && !hashCodeCheck) {
            System.err.println("   hashCode() contract VIOLATED!");
        } else {
            System.out.println("   hashCode() contract holds.");
        }
        System.out.println("p1.equals(p3) : " + p1.equals(p3));
        System.out.println("p1.hashCode() == p3.hashCode() : " + (p1.hashCode() == p3.hashCode())); // Likely false (collisions possible)


        System.out.println("\n--- Usage in HashMap ---");
        Map<Point, String> pointMap = new HashMap<>();
        pointMap.put(p1, "Point 1 Value");
        pointMap.put(p3, "Point 3 Value");
        System.out.println("Map size: " + pointMap.size()); // Expected: 2

        // Проверяем доступ по логически равному ключу (p2)
        System.out.println("pointMap.containsKey(p1)? " + pointMap.containsKey(p1)); // Expected: true
        System.out.println("pointMap.containsKey(p2)? " + pointMap.containsKey(p2)); // Expected: true
        System.out.println("pointMap.get(p2): " + pointMap.get(p2)); // Expected: Point 1 Value

        // Добавляем p2 - он должен перезаписать p1, т.к. они равны
        pointMap.put(p2, "Point 2 Value (Overwrites P1)");
        System.out.println("Map size after putting p2: " + pointMap.size()); // Expected: 2
        System.out.println("pointMap.get(p1): " + pointMap.get(p1)); // Expected: Point 2 Value (Overwrites P1)
    }
}
