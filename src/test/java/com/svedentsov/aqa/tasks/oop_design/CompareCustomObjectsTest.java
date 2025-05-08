package com.svedentsov.aqa.tasks.oop_design;

import com.svedentsov.aqa.tasks.oop_design.CompareCustomObjects.Person;
import com.svedentsov.aqa.tasks.oop_design.CompareCustomObjects.PersonAgeComparator;
import com.svedentsov.aqa.tasks.oop_design.CompareCustomObjects.PersonNameComparator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для Person (Comparable и Comparators)")
class CompareCustomObjectsTest {

    private Person alice25, alice30, bob25, bob30, charlie30;

    @BeforeEach
    void setUp() {
        // Создаем набор объектов Person для тестов
        alice25 = new Person("Alice", 25);
        alice30 = new Person("Alice", 30);
        bob25 = new Person("Bob", 25);
        bob30 = new Person("Bob", 30);
        charlie30 = new Person("Charlie", 30);
    }

    // --- Тесты для Comparable (Person.compareTo) ---
    @Nested
    @DisplayName("Тесты Comparable (compareTo)")
    class ComparableTests {

        @Test
        @DisplayName("Меньший возраст -> отрицательный результат")
        void compareTo_LesserAge_ShouldReturnNegative() {
            assertTrue(alice25.compareTo(alice30) < 0);
            assertTrue(bob25.compareTo(bob30) < 0);
        }

        @Test
        @DisplayName("Больший возраст -> положительный результат")
        void compareTo_GreaterAge_ShouldReturnPositive() {
            assertTrue(alice30.compareTo(alice25) > 0);
            assertTrue(bob30.compareTo(bob25) > 0);
        }

        @Test
        @DisplayName("Одинаковый возраст, меньшее имя -> отрицательный результат")
        void compareTo_SameAgeLesserName_ShouldReturnNegative() {
            assertTrue(alice30.compareTo(bob30) < 0);
            assertTrue(alice30.compareTo(charlie30) < 0);
        }

        @Test
        @DisplayName("Одинаковый возраст, большее имя -> положительный результат")
        void compareTo_SameAgeGreaterName_ShouldReturnPositive() {
            assertTrue(bob30.compareTo(alice30) > 0);
            assertTrue(charlie30.compareTo(alice30) > 0);
            assertTrue(charlie30.compareTo(bob30) > 0);
        }

        @Test
        @DisplayName("Одинаковые объекты (или равные по equals) -> результат 0")
        void compareTo_EqualObjects_ShouldReturnZero() {
            Person alice25_copy = new Person("Alice", 25);
            assertEquals(0, alice25.compareTo(alice25_copy));
            assertEquals(0, alice25.compareTo(alice25)); // Рефлексивность
        }

        @Test
        @DisplayName("compareTo(null) должен выбросить NullPointerException")
        void compareTo_Null_ShouldThrowNullPointerException() {
            assertThrows(NullPointerException.class, () -> {
                alice25.compareTo(null);
            });
        }

        @Test
        @DisplayName("Проверка соответствия compareTo и equals")
        void compareTo_ShouldBeConsistentWithEquals() {
            Person alice25_copy = new Person("Alice", 25);
            // Если equals = true, то compareTo = 0
            assertTrue(alice25.equals(alice25_copy));
            assertEquals(0, alice25.compareTo(alice25_copy));

            // Если equals = false, то compareTo != 0
            assertFalse(alice25.equals(bob25));
            assertNotEquals(0, alice25.compareTo(bob25));
        }

        @Test
        @DisplayName("Транзитивность: a < b и b < c => a < c")
        void compareTo_Transitivity() {
            // a = alice25, b = bob25, c = alice30
            assertTrue(alice25.compareTo(bob25) < 0); // Сравнение по имени
            assertTrue(bob25.compareTo(alice30) < 0); // Сравнение по возрасту
            assertTrue(alice25.compareTo(alice30) < 0); // Ожидаем < 0

            // a = alice30, b = bob30, c = charlie30
            assertTrue(alice30.compareTo(bob30) < 0); // Сравнение по имени
            assertTrue(bob30.compareTo(charlie30) < 0); // Сравнение по имени
            assertTrue(alice30.compareTo(charlie30) < 0); // Ожидаем < 0
        }
    }

    // --- Тесты для Компараторов ---
    @Nested
    @DisplayName("Тесты Comparators")
    class ComparatorTests {

        private final PersonAgeComparator ageComparator = new PersonAgeComparator();
        private final PersonNameComparator nameComparator = new PersonNameComparator();

        @Test
        @DisplayName("PersonAgeComparator")
        void testAgeComparator() {
            assertTrue(ageComparator.compare(alice25, alice30) < 0, "Меньший возраст должен быть первым");
            assertTrue(ageComparator.compare(alice30, alice25) > 0, "Больший возраст должен быть последним");
            assertEquals(0, ageComparator.compare(alice30, bob30), "Одинаковый возраст должен давать 0");
        }

        @Test
        @DisplayName("PersonNameComparator")
        void testNameComparator() {
            assertTrue(nameComparator.compare(alice30, bob30) < 0, "Имя 'Alice' должно быть раньше 'Bob'");
            assertTrue(nameComparator.compare(bob30, alice30) > 0, "Имя 'Bob' должно быть позже 'Alice'");
            assertEquals(0, nameComparator.compare(alice30, new Person("Alice", 99)), "Одинаковые имена должны давать 0");
            assertTrue(nameComparator.compare(bob30, charlie30) < 0);
        }

        @Test
        @DisplayName("Компараторы должны выбрасывать NullPointerException для null")
        void testComparatorsWithNull() {
            assertThrows(NullPointerException.class, () -> ageComparator.compare(alice25, null));
            assertThrows(NullPointerException.class, () -> ageComparator.compare(null, alice30));
            assertThrows(NullPointerException.class, () -> nameComparator.compare(alice25, null));
            assertThrows(NullPointerException.class, () -> nameComparator.compare(null, alice30));
        }
    }

    // --- Тесты сортировки списка ---
    @Nested
    @DisplayName("Тесты сортировки списка")
    class SortingTests {

        private List<Person> people;
        private List<Person> expectedSortedNatural;
        private List<Person> expectedSortedByName;
        private List<Person> expectedSortedByAgeDesc;

        @BeforeEach
        void setupSorting() {
            // Используем неупорядоченный список для теста
            people = new ArrayList<>(List.of(alice30, bob25, charlie30, bob30, alice25));

            // Ожидаемый результат естественной сортировки (Comparable: age asc, name asc)
            expectedSortedNatural = List.of(alice25, bob25, alice30, bob30, charlie30);

            // Ожидаемый результат сортировки по имени (NameComparator)
            expectedSortedByName = List.of(alice25, alice30, bob25, bob30, charlie30);

            // Ожидаемый результат сортировки по возрасту (убывание)
            expectedSortedByAgeDesc = List.of(alice30, bob30, charlie30, alice25, bob25);
        }

        @Test
        @DisplayName("Сортировка Collections.sort() (естественный порядок)")
        void testSortWithComparable() {
            Collections.sort(people); // Использует Person.compareTo
            assertEquals(expectedSortedNatural, people, "Список должен быть отсортирован по возрасту, затем по имени");
        }

        @Test
        @DisplayName("Сортировка list.sort(PersonNameComparator)")
        void testSortWithNameComparator() {
            // Ожидаемый порядок только по основному ключу (имя возрастание)
            people.sort(new PersonNameComparator());

            assertEquals(5, people.size(), "Размер списка не должен измениться");
            // Проверяем, что имена идут по возрастанию (или равны)
            for (int i = 0; i < people.size() - 1; i++) {
                assertTrue(people.get(i).getName().compareTo(people.get(i + 1).getName()) <= 0,
                        "Нарушен алфавитный порядок имен на индексе " + i);
            }
            // Проверяем, что все исходные элементы присутствуют
            List<Person> originalElements = List.of(alice30, bob25, charlie30, bob30, alice25);
            assertTrue(people.containsAll(originalElements), "Отсортированный список должен содержать все исходные элементы");
            // assertEquals(expectedSortedByName, people, "Список должен быть отсортирован по имени"); // Старый неверный assert
        }

        @Test
        @DisplayName("Сортировка list.sort(PersonAgeComparator)")
        void testSortWithAgeComparator() {
            // Сортировка только по возрасту, порядок для одинаковых возрастов не гарантирован компаратором,
            // поэтому сравниваем размеры и проверяем порядок возрастов
            people.sort(new PersonAgeComparator());
            assertEquals(5, people.size());
            assertTrue(people.get(0).getAge() <= people.get(1).getAge()); // 25 <= 25
            assertTrue(people.get(1).getAge() <= people.get(2).getAge()); // 25 <= 30
            assertTrue(people.get(2).getAge() <= people.get(3).getAge()); // 30 <= 30
            assertTrue(people.get(3).getAge() <= people.get(4).getAge()); // 30 <= 30
            // Проверяем, что все элементы на месте (содержимое)
            assertTrue(people.containsAll(List.of(alice25, bob25, alice30, bob30, charlie30)));
        }

        @Test
        @DisplayName("Сортировка по возрасту (убывание) через лямбда")
        void testSortWithLambdaAgeDesc() {
            // Ожидаемый порядок только по основному ключу (возраст убывание)
            people.sort((p1, p2) -> Integer.compare(p2.getAge(), p1.getAge())); // Сравниваем наоборот

            assertEquals(5, people.size(), "Размер списка не должен измениться");
            // Проверяем, что возраст идет по убыванию (или равен)
            for (int i = 0; i < people.size() - 1; i++) {
                assertTrue(people.get(i).getAge() >= people.get(i + 1).getAge(),
                        "Нарушен порядок убывания возраста на индексе " + i);
            }
            // Проверяем, что все исходные элементы присутствуют
            List<Person> originalElements = List.of(alice30, bob25, charlie30, bob30, alice25);
            assertTrue(people.containsAll(originalElements), "Отсортированный список должен содержать все исходные элементы");
            // assertEquals(expectedSortedByAgeDesc, people, "Список должен быть отсортирован по убыванию возраста"); // Старый неверный assert
        }
    }
}
