package com.svedentsov.aqa.tasks.oop_design;

import com.svedentsov.aqa.tasks.oop_design.ToStringComplex.Customer;
import com.svedentsov.aqa.tasks.oop_design.ToStringComplex.Order;
import com.svedentsov.aqa.tasks.oop_design.ToStringComplex.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для класса Order (из ToStringComplex)")
class ToStringComplexTest {

    // --- Тесты для вспомогательных классов (базовые, для уверенности) ---
    @Nested
    @DisplayName("Тесты для Customer")
    class CustomerTests {
        @Test
        void customerToString() {
            Customer customer = new Customer("Alice");
            assertEquals("Customer{name='Alice'}", customer.toString());
        }

        @Test
        void customerEqualsAndHashCode() {
            Customer c1 = new Customer("Alice");
            Customer c2 = new Customer("Alice");
            Customer c3 = new Customer("Bob");
            assertEquals(c1, c2);
            assertNotEquals(c1, c3);
            assertEquals(c1.hashCode(), c2.hashCode());
            assertNotEquals(c1.hashCode(), c3.hashCode());
        }

        @Test
        void customerConstructorValidation() {
            assertThrows(NullPointerException.class, () -> new Customer(null));
            assertThrows(IllegalArgumentException.class, () -> new Customer(""));
            assertThrows(IllegalArgumentException.class, () -> new Customer("  "));
        }
    }

    @Nested
    @DisplayName("Тесты для Product")
    class ProductTests {
        @Test
        void productToString() {
            Product product = new Product("Laptop", 1200.0);
            // String.format("%.1f"...) для консистентного вывода double
            assertEquals(String.format(Locale.ROOT, "Product{name='Laptop', price=%.1f}", 1200.0), product.toString());
        }

        @Test
        void productEqualsAndHashCode() {
            Product p1 = new Product("Laptop", 1200.0);
            Product p2 = new Product("Laptop", 1200.0);
            Product p3 = new Product("Mouse", 25.0);
            assertEquals(p1, p2);
            assertNotEquals(p1, p3);
            assertEquals(p1.hashCode(), p2.hashCode());
            assertNotEquals(p1.hashCode(), p3.hashCode());
        }

        @Test
        void productConstructorValidation() {
            assertThrows(NullPointerException.class, () -> new Product(null, 10.0));
            assertThrows(IllegalArgumentException.class, () -> new Product("", 10.0));
            assertThrows(IllegalArgumentException.class, () -> new Product("  ", 10.0));
            assertThrows(IllegalArgumentException.class, () -> new Product("Valid", -1.0));
        }
    }

    // --- Тесты для класса Order ---
    @Nested
    @DisplayName("Тесты для Order.toString()")
    class OrderToStringTests {
        private final String EOL = System.lineSeparator(); // Платформо-независимый перенос строки

        @Test
        @DisplayName("toString() для заказа с несколькими продуктами")
        void toStringWithMultipleProducts() {
            Customer customer = new Customer("Alice");
            List<Product> products = List.of(
                    new Product("Laptop", 1200.0),
                    new Product("Mouse", 25.0)
            );
            Order order = new Order(123, customer, products);

            // Ожидаемый формат на основе оригинального toString()
            String expected = "Order{" + EOL +
                    "  orderId=123," + EOL +
                    "  customer=Customer{name='Alice'}," + EOL +
                    "  products=[" + EOL +
                    "    Product{name='Laptop', price=1200.0}," + EOL + // productIndent + P1 + ",\n"
                    "    Product{name='Mouse', price=25.0}" + EOL +     // productIndent + P2 + "\n" (после Collectors.joining и финального .append("\n"))
                    "  ]" + EOL +
                    "}";
            assertEquals(expected, order.toString());
        }

        @Test
        @DisplayName("toString() для заказа с одним продуктом")
        void toStringWithSingleProduct() {
            Customer customer = new Customer("Bob");
            List<Product> products = List.of(new Product("Keyboard", 75.5));
            Order order = new Order(456, customer, products);

            String expected = "Order{" + EOL +
                    "  orderId=456," + EOL +
                    "  customer=Customer{name='Bob'}," + EOL +
                    "  products=[" + EOL +
                    "    Product{name='Keyboard', price=75.5}" + EOL + // productIndent + P1 + "\n"
                    "  ]" + EOL +
                    "}";
            assertEquals(expected, order.toString());
        }

        @Test
        @DisplayName("toString() для заказа с пустым списком продуктов")
        void toStringWithEmptyProducts() {
            Customer customer = new Customer("Charlie");
            Order order = new Order(789, customer, Collections.emptyList());

            String expected = "Order{" + EOL +
                    "  orderId=789," + EOL +
                    "  customer=Customer{name='Charlie'}," + EOL +
                    "  products=[" + EOL +
                    "    (empty)" + EOL +         // productIndent + "(empty)\n"
                    "  ]" + EOL +
                    "}";
            assertEquals(expected, order.toString());
        }
    }

    @Nested
    @DisplayName("Тесты конструктора и геттеров Order")
    class OrderConstructorAndGetterTests {
        @Test
        @DisplayName("Успешное создание и проверка геттеров")
        void validOrderCreation() {
            Customer customer = new Customer("David");
            List<Product> initialProducts = List.of(new Product("Monitor", 300.0));
            Order order = new Order(101, customer, initialProducts);

            List<Product> retrievedProducts = order.getProducts();

            assertAll("Проверка полей заказа",
                    () -> assertEquals(101, order.getOrderId()),
                    () -> assertEquals(customer, order.getCustomer()),
                    () -> assertEquals(initialProducts, retrievedProducts, "Содержимое списка продуктов должно совпадать"),
                    // () -> assertNotSame(initialProducts, retrievedProducts, "Для List.of() может вернуться тот же экземпляр, если он уже неизменяемый нужного типа"),
                    () -> assertThrows(UnsupportedOperationException.class, () -> retrievedProducts.add(new Product("Test", 1.0)), "Список продуктов должен быть неизменяемым")
            );
        }

        @Test
        @DisplayName("Исключение NullPointerException для null customer")
        void constructorThrowsForNullCustomer() {
            NullPointerException ex = assertThrows(NullPointerException.class, () -> {
                new Order(1, null, Collections.emptyList());
            });
            assertEquals("Customer cannot be null", ex.getMessage());
        }

        @Test
        @DisplayName("Исключение NullPointerException для null списка продуктов")
        void constructorThrowsForNullProductList() {
            Customer customer = new Customer("Eve");
            NullPointerException ex = assertThrows(NullPointerException.class, () -> {
                new Order(1, customer, null);
            });
            assertEquals("Products list cannot be null", ex.getMessage());
        }

        @Test
        @DisplayName("Исключение NullPointerException, если список продуктов содержит null")
        void constructorThrowsForProductListWithNullElements() {
            Customer customer = new Customer("Frank");
            List<Product> productsWithNull = new ArrayList<>();
            productsWithNull.add(new Product("GoodItem", 10.0));
            productsWithNull.add(null); // Добавляем null в изменяемый список

            // List.copyOf(productsWithNull) выбросит NullPointerException
            assertThrows(NullPointerException.class, () -> {
                new Order(1, customer, productsWithNull);
            });
        }
    }

    @Nested
    @DisplayName("Тесты Order.equals() и Order.hashCode()")
    class OrderEqualsAndHashCodeTests {
        Customer c1 = new Customer("CustomerA");
        Customer c2 = new Customer("CustomerB");
        Product p1 = new Product("ProductX", 10.0);
        Product p2 = new Product("ProductY", 20.0);

        Order order1_v1 = new Order(1, c1, List.of(p1));
        Order order1_v2 = new Order(1, c1, List.of(p1)); // Равен order1_v1
        Order order2_id = new Order(2, c1, List.of(p1)); // Другой ID
        Order order3_cust = new Order(1, c2, List.of(p1)); // Другой Customer
        Order order4_prod = new Order(1, c1, List.of(p2)); // Другой Product list
        Order order5_prods = new Order(1, c1, List.of(p1, p2)); // Другой Product list (больше)


        @Test
        @DisplayName("equals должен быть рефлексивным, симметричным, транзитивным")
        void equalsContract() {
            // Рефлексивность
            assertEquals(order1_v1, order1_v1);

            // Симметричность
            assertEquals(order1_v1, order1_v2);
            assertEquals(order1_v2, order1_v1);

            // Транзитивность
            Order order1_v3 = new Order(1, c1, List.of(p1));
            assertEquals(order1_v1, order1_v2);
            assertEquals(order1_v2, order1_v3);
            assertEquals(order1_v1, order1_v3);

            // Неравенство
            assertNotEquals(order1_v1, order2_id);
            assertNotEquals(order1_v1, order3_cust);
            assertNotEquals(order1_v1, order4_prod);
            assertNotEquals(order1_v1, order5_prods);

            // Сравнение с null и другим типом
            assertNotEquals(null, order1_v1);
            assertNotEquals("some string", order1_v1);
        }

        @Test
        @DisplayName("hashCode контракт: равные объекты -> равные хеш-коды")
        void hashCodeContract() {
            assertEquals(order1_v1, order1_v2);
            assertEquals(order1_v1.hashCode(), order1_v2.hashCode());

            // Для не равных - хеш-коды могут совпадать (коллизия), но обычно разные
            if (!order1_v1.equals(order2_id)) { // Дополнительная проверка, чтобы быть уверенным
                // Мы не можем гарантировать, что они будут не равны, но для этих данных они скорее всего будут
                // assertNotEquals(order1_v1.hashCode(), order2_id.hashCode());
            }
        }
    }
}
