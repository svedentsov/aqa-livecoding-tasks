package com.svedentsov.aqa.tasks.oop_design;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Решение задачи №56: Реализация toString() для сложного объекта.
 * <p>
 * Описание: Форматированный вывод состояния объекта с вложенными объектами.
 * (Проверяет: ООП, работа со строками)
 * <p>
 * Задание: Дан класс `Order` { `int orderId; Customer customer; List<Product> products;` }.
 * Класс `Customer` { `String name;` }. Класс `Product` { `String name; double price;` }.
 * Переопределите метод `toString()` для класса `Order`, чтобы он возвращал читаемую
 * строку, включающую ID заказа, имя клиента и список продуктов (каждый на новой
 * строке с отступом).
 * <p>
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
            this.name = name;
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
            this.name = name;
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
            return "Product{name='" + name + "', price=" + price + '}';
        }

        @Override
        public boolean equals(Object o) { /* ... стандартная реализация ... */
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Product p = (Product) o;
            return Double.compare(p.price, price) == 0 && Objects.equals(name, p.name);
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
        final List<Product> products; // Используем List<Product>

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

        // Геттеры (для полноты)
        public int getOrderId() {
            return orderId;
        }

        public Customer getCustomer() {
            return customer;
        }

        public List<Product> getProducts() {
            return products;
        } // Возвращает неизменяемую копию

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
            String indent = "  "; // Отступ для полей
            String productIndent = "    "; // Отступ для продуктов

            sb.append("Order{\n"); // Начало объекта

            // Добавляем поля с отступом
            sb.append(indent).append("orderId=").append(orderId).append(",\n");
            sb.append(indent).append("customer=").append(customer).append(",\n"); // Вызовет Customer.toString()
            sb.append(indent).append("products=[\n"); // Начало списка продуктов

            // Форматируем список продуктов
            if (products.isEmpty()) {
                sb.append(productIndent).append("(empty)\n"); // Указываем, что список пуст
            } else {
                // Используем Stream API для форматирования каждого продукта
                String productsString = products.stream()
                        .filter(Objects::nonNull) // На случай, если список МОЖЕТ содержать null
                        .map(p -> productIndent + p.toString()) // Добавляем отступ перед каждым продуктом
                        .collect(Collectors.joining(",\n")); // Соединяем строки с ",\\n"
                sb.append(productsString).append("\n");
            }

            sb.append(indent).append("]\n"); // Закрывающая скобка списка
            sb.append("}"); // Закрывающая скобка объекта

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

    /**
     * Точка входа для демонстрации работы метода toString() для сложного объекта Order.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        // Создаем клиентов
        Customer alice = new Customer("Alice Smith");
        Customer bob = new Customer("Bob Johnson");

        // Создаем списки продуктов
        // Используем ArrayList для возможности добавления null (если это нужно протестировать)
        List<Product> productList1 = new ArrayList<>(List.of(
                new Product("Laptop Pro", 1250.99),
                new Product("Wireless Mouse", 29.50),
                null, // Пример null продукта в исходном списке
                new Product("Mechanical Keyboard", 85.00)
        ));
        List<Product> productList2 = List.of(); // Пустой список
        List<Product> productList3 = List.of(new Product("USB Drive", 15.00));

        // Создаем заказы
        Order order1 = new Order(20240401, alice, productList1);
        Order order2 = new Order(20240402, bob, productList2);
        Order order3 = new Order(20240403, alice, productList3);

        System.out.println("--- Complex Object toString() Demonstration ---");

        System.out.println("\n--- Order 1 ---");
        System.out.println(order1); // Вызов order1.toString()

        System.out.println("\n--- Order 2 (Empty Products) ---");
        System.out.println(order2);

        System.out.println("\n--- Order 3 ---");
        System.out.println(order3);
    }
}
