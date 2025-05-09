package com.svedentsov.aqa.tasks.oop_design;

import com.svedentsov.aqa.tasks.oop_design.EqualsHashCode.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для класса Point")
class EqualsHashCodeTest {

    private Point p1_1, p1_2, p2_1, p_null;

    @BeforeEach
    void setUp() {
        p1_1 = new Point(1, 2);
        p1_2 = new Point(1, 2); // Логически равен p1_1
        p2_1 = new Point(2, 1); // Другая точка
        p_null = null; // Для тестов с null
    }

    @Test
    @DisplayName("Конструктор и геттеры")
    void constructorAndGettersShouldWork() {
        Point p = new Point(10, 20);
        assertAll("Проверка геттеров",
                () -> assertEquals(10, p.getX(), "getX() должен вернуть правильное значение x"),
                () -> assertEquals(20, p.getY(), "getY() должен вернуть правильное значение y")
        );
    }

    @Test
    @DisplayName("Метод toString()")
    void toStringShouldReturnCorrectFormat() {
        assertEquals("Point(x=1, y=2)", p1_1.toString(), "Формат toString() неверный");
    }

    @Nested
    @DisplayName("Контракт метода equals()")
    class EqualsContract {

        @Test
        @DisplayName("Рефлексивность: x.equals(x) == true")
        void reflexive() {
            assertTrue(p1_1.equals(p1_1), "Точка должна быть равна самой себе");
        }

        @Test
        @DisplayName("Симметричность: x.equals(y) == y.equals(x)")
        void symmetric() {
            assertTrue(p1_1.equals(p1_2), "p1_1 должен быть равен p1_2");
            assertTrue(p1_2.equals(p1_1), "p1_2 должен быть равен p1_1 (симметрия)");

            assertFalse(p1_1.equals(p2_1), "p1_1 не должен быть равен p2_1");
            assertFalse(p2_1.equals(p1_1), "p2_1 не должен быть равен p1_1 (симметрия)");
        }

        @Test
        @DisplayName("Транзитивность: x.equals(y) && y.equals(z) => x.equals(z)")
        void transitive() {
            Point p1_3 = new Point(1, 2); // z, логически равен p1_1 и p1_2
            assertTrue(p1_1.equals(p1_2), "p1_1 должен быть равен p1_2");
            assertTrue(p1_2.equals(p1_3), "p1_2 должен быть равен p1_3");
            assertTrue(p1_1.equals(p1_3), "p1_1 должен быть равен p1_3 (транзитивность)");
        }

        @Test
        @DisplayName("Согласованность: повторные вызовы x.equals(y) возвращают то же значение")
        void consistent() {
            assertTrue(p1_1.equals(p1_2));
            assertTrue(p1_1.equals(p1_2)); // Повторный вызов

            assertFalse(p1_1.equals(p2_1));
            assertFalse(p1_1.equals(p2_1)); // Повторный вызов
        }

        @Test
        @DisplayName("Сравнение с null: x.equals(null) == false")
        void nonNull() {
            assertFalse(p1_1.equals(p_null), "Сравнение с null должно возвращать false");
        }

        @Test
        @DisplayName("Сравнение с объектом другого класса должно возвращать false")
        void equalsDifferentClass() {
            assertFalse(p1_1.equals("Point(x=1, y=2)"), "Сравнение с объектом другого класса");
            assertFalse(p1_1.equals(Integer.valueOf(1)));
        }

        @Test
        @DisplayName("Равенство для идентичных полей")
        void equalsSameFields() {
            assertEquals(p1_1, p1_2);
        }

        @Test
        @DisplayName("Неравенство для разных полей")
        void equalsDifferentFields() {
            assertNotEquals(p1_1, p2_1);
        }
    }

    @Nested
    @DisplayName("Контракт метода hashCode()")
    class HashCodeContract {

        @Test
        @DisplayName("Если x.equals(y), то x.hashCode() == y.hashCode()")
        void equalsImpliesSameHashCode() {
            assertTrue(p1_1.equals(p1_2), "p1_1 должен быть равен p1_2 для этого теста");
            assertEquals(p1_1.hashCode(), p1_2.hashCode(), "Равные объекты должны иметь одинаковые хеш-коды");
        }

        @Test
        @DisplayName("Согласованность: повторные вызовы x.hashCode() возвращают то же значение")
        void consistentHashCode() {
            int initialHashCode = p1_1.hashCode();
            assertEquals(initialHashCode, p1_1.hashCode(), "Повторный вызов hashCode должен вернуть то же значение");
            assertEquals(initialHashCode, p1_1.hashCode(), "И еще один повторный вызов hashCode");
        }

        @Test
        @DisplayName("Разные объекты (вероятно) имеют разные хеш-коды")
        void unequalObjectsMayHaveDifferentHashCodes() {
            // Это не строгий тест, так как коллизии хеш-кодов возможны,
            // но для простых объектов, как Point, они маловероятны с Objects.hash()
            assertFalse(p1_1.equals(p2_1));
            assertNotEquals(p1_1.hashCode(), p2_1.hashCode(),
                    "Неравные объекты, скорее всего, будут иметь разные хеш-коды (хотя коллизии возможны)");
        }
    }

    @Nested
    @DisplayName("Поведение в коллекциях (HashMap, HashSet)")
    class CollectionBehavior {
        @Test
        @DisplayName("HashMap: get() по логически равному ключу")
        void hashMapRetrievalWithEqualKey() {
            Map<Point, String> map = new HashMap<>();
            map.put(p1_1, "Value for p1_1");
            assertEquals("Value for p1_1", map.get(p1_2),
                    "HashMap.get() должен находить значение по логически равному ключу");
        }

        @Test
        @DisplayName("HashMap: put() с логически равным ключом перезаписывает значение")
        void hashMapPutWithEqualKeyOverwrites() {
            Map<Point, String> map = new HashMap<>();
            map.put(p1_1, "Original Value");
            map.put(p1_2, "New Value"); // p1_2 equals p1_1
            assertEquals(1, map.size(), "Размер карты не должен измениться при put с равным ключом");
            assertEquals("New Value", map.get(p1_1), "Значение должно быть перезаписано");
        }

        @Test
        @DisplayName("HashSet: add() не добавляет логически равный элемент")
        void hashSetAddEqualElement() {
            Set<Point> set = new HashSet<>();
            assertTrue(set.add(p1_1), "Первое добавление p1_1 должно быть успешным");
            assertFalse(set.add(p1_2), "Повторное добавление логически равного p1_2 не должно изменить Set");
            assertEquals(1, set.size(), "Размер Set должен остаться 1");
        }

        @Test
        @DisplayName("HashSet: contains() для логически равного элемента")
        void hashSetContainsEqualElement() {
            Set<Point> set = new HashSet<>();
            set.add(p1_1);
            assertTrue(set.contains(p1_2), "HashSet.contains() должен находить логически равный элемент");
        }
    }
}
