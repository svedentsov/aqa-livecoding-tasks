package com.svedentsov.aqa.tasks.oop_design;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Решение задачи №56: Реализация toString() для сложного объекта.
 * Описание: Форматированный вывод состояния объекта с вложенными объектами.
 * (Проверяет: ООП, работа со строками)
 * Задание: Дан класс `Order` { `int orderId; Customer customer; List<Product> products;` }.
 * Класс `Customer` { `String name;` }. Класс `Product` { `String name; double price;` }.
 * Переопределите метод `toString()` для класса `Order`, чтобы он возвращал читаемую
 * строку, включающую ID заказа, имя клиента и список продуктов (каждый на новой
 * строке с отступом).
 * Пример: Вывод может выглядеть примерно так:
 * `Order{orderId=123, customer=Customer{name='Alice'}, products=[\n  Product{name='Laptop', price=1200.0},\n  Product{name='Mouse', price=25.0}\n]}`.
 */
public class ToStringComplex {

    // --- Вспомогательные классы ---

    /**
     * Класс Customer.
     */
    static class Customer {
        String name;

        public Customer(String name) {
            this.name = Objects.requireNonNull(name, "Customer name cannot be null");
            if (name.trim().isEmpty()) {
                throw new IllegalArgumentException("Customer name cannot be empty or blank.");
            }
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Customer{name='" + name + "'}";
        }

        @Override
        public boolean equals(Object o) { /* ... стандартная реализация ... */
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            return Objects.equals(name, ((Customer) o).name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    /**
     * Класс Product.
     */
    static class Product {
        String name;
        double price;

        public Product(String name, double price) {
            this.name = Objects.requireNonNull(name, "Product name cannot be null");
            if (name.trim().isEmpty()) {
                throw new IllegalArgumentException("Product name cannot be empty or blank.");
            }
            if (price < 0) {
                throw new IllegalArgumentException("Product price cannot be negative.");
            }
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        @Override
        public String toString() {
            // Используем Locale.ROOT для консистентного вывода double (с точкой как разделителем)
            return String.format(Locale.ROOT, "Product{name='%s', price=%.1f}", name, price);
        }

        @Override
        public boolean equals(Object o) { /* ... стандартная реализация ... */
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Product product = (Product) o;
            return Double.compare(product.price, price) == 0 && name.equals(product.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, price);
        }
    }

    // --- Основной класс Order ---

    /**
     * Класс, представляющий заказ с ID, клиентом и списком продуктов.
     * Демонстрирует переопределение toString() для читаемого вывода,
     * включая форматирование вложенных объектов и коллекций с отступами.
     */
    static class Order {
        final int orderId;
        final Customer customer;
        final List<Product> products;

        /**
         * Создает объект Order.
         *
         * @param orderId  ID заказа.
         * @param customer Клиент (не null).
         * @param products Список продуктов (не null, будет скопирован).
         */
        public Order(int orderId, Customer customer, List<Product> products) {
            this.orderId = orderId;
            // Проверка на null для обязательных полей
            this.customer = Objects.requireNonNull(customer, "Customer cannot be null");
            Objects.requireNonNull(products, "Products list cannot be null");
            // Создаем неизменяемую копию списка для защиты внутреннего состояния
            this.products = List.copyOf(products);
        }

        public int getOrderId() {
            return orderId;
        }

        public Customer getCustomer() {
            return customer;
        }

        public List<Product> getProducts() {
            return products;
        }

        /**
         * Возвращает форматированное строковое представление объекта Order.
         * Включает ID заказа, информацию о клиенте (используя его toString())
         * и список продуктов, где каждый продукт выводится на новой строке с отступом.
         *
         * @return Читаемая многострочная строка, представляющая заказ.
         */
        @Override
        public String toString() {
            // Используем StringBuilder для эффективного построения строки
            StringBuilder sb = new StringBuilder();
            String indent = "  ";
            String productIndent = "    ";
            String EOL = System.lineSeparator();

            sb.append("Order{").append(EOL);
            sb.append(indent).append("orderId=").append(orderId).append(",").append(EOL);
            sb.append(indent).append("customer=").append(customer).append(",").append(EOL);
            sb.append(indent).append("products=[").append(EOL);

            if (products.isEmpty()) {
                sb.append(productIndent).append("(empty)").append(EOL);
            } else {
                String productsString = products.stream()
                        .map(p -> productIndent + p.toString())
                        .collect(Collectors.joining("," + EOL));
                sb.append(productsString).append(EOL);
            }
            sb.append(indent).append("]").append(EOL);
            sb.append("}");
            return sb.toString();
        }

        // equals() и hashCode() для полноты класса
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Order order = (Order) o;
            return orderId == order.orderId &&
                    customer.equals(order.customer) &&
                    products.equals(order.products); // Сравнение списков
        }

        @Override
        public int hashCode() {
            return Objects.hash(orderId, customer, products);
        }
    }
}
