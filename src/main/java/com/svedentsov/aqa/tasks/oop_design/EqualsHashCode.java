package com.svedentsov.aqa.tasks.oop_design;

import java.util.Objects;

/**
 * Решение задачи №51: Реализация equals() и hashCode().
 * Описание: Объяснить контракт между ними.
 * (Проверяет: ООП, контракты Java)
 * Задание: Для класса `Point` с полями `int x` и `int y`, переопределите методы
 * `equals(Object o)` и `hashCode()` так, чтобы они соответствовали контракту:
 * равные объекты (`p1.x == p2.x && p1.y == p2.y`) должны иметь одинаковый `hashCode`.
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
     * Контракт equals():
     * 1. Рефлексивность: x.equals(x) == true.
     * 2. Симметричность: x.equals(y) == y.equals(x).
     * 3. Транзитивность: если x.equals(y) и y.equals(z), то x.equals(z).
     * 4. Согласованность: повторные вызовы x.equals(y) возвращают то же значение.
     * 5. Сравнение с null: x.equals(null) == false.
     * Контракт hashCode():
     * 1. Согласованность: повторные вызовы x.hashCode() возвращают то же значение.
     * 2. Связь с equals(): если x.equals(y), то x.hashCode() == y.hashCode().
     * (Обратное не обязательно верно: разные объекты могут иметь одинаковый хеш-код - коллизия).
     */
    static class Point { // Делаем static, чтобы можно было инстанцировать без объекта EqualsHashCode
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
}
