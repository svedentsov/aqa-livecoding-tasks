package com.svedentsov.aqa.tasks.oop_design;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Решение задачи №56: Реализация toString() для сложного объекта.
 */
public class Task56_ToStringComplex {

    /**
     * Вспомогательный класс Customer.
     */
    static class Customer { // Делаем static для доступа из static main
        String name;

        /**
         * Конструктор Customer. @param name Имя клиента.
         */
        public Customer(String name) {
            this.name = name;
        }

        /**
         * Возвращает имя клиента.
         */
        public String getName() {
            return name;
        }

        /**
         * Стандартный toString для Customer.
         */
        @Override
        public String toString() {
            return "Customer{name='" + name + "'}";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Customer customer = (Customer) o;
            return Objects.equals(name, customer.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    /**
     * Вспомогательный класс Product.
     */
    static class Product { // Делаем static для доступа из static main
        String name;
        double price;

        /**
         * Конструктор Product. @param name Название продукта. @param price Цена продукта.
         */
        public Product(String name, double price) {
            this.name = name;
            this.price = price;
        }

        /**
         * Возвращает название продукта.
         */
        public String getName() {
            return name;
        }

        /**
         * Возвращает цену продукта.
         */
        public double getPrice() {
            return price;
        }

        /**
         * Стандартный toString для Product.
         */
        @Override
        public String toString() {
            return "Product{name='" + name + "', price=" + price + '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Product product = (Product) o;
            return Double.compare(product.price, price) == 0 && Objects.equals(name, product.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, price);
        }
    }

    /**
     * Класс, представляющий заказ с ID, клиентом и списком продуктов.
     * Демонстрирует переопределение toString() для читаемого вывода,
     * включая форматирование вложенных объектов и коллекций с отступами.
     */
    static class Order { // Делаем static для доступа из static main
        final int orderId;
        final Customer customer;
        final List<Product> products;

        /**
         * Создает объект Order.
         *
         * @param orderId  ID заказа.
         * @param customer Клиент (не null).
         * @param products Список продуктов (не null).
         */
        public Order(int orderId, Customer customer, List<Product> products) {
            this.orderId = orderId;
            this.customer = Objects.requireNonNull(customer, "Customer cannot be null");
            // Создаем копию списка для защиты от внешних модификаций (если Order должен быть immutable)
            this.products = List.copyOf(Objects.requireNonNull(products, "Products list cannot be null"));
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
            // Используем StringBuilder для эффективности конкатенации строк
            StringBuilder sb = new StringBuilder();
            sb.append("Order{\n"); // Начало объекта и перенос строки
            sb.append("  orderId=").append(orderId).append(",\n"); // ID с отступом
            sb.append("  customer=").append(customer).append(",\n"); // Клиент с отступом (вызов customer.toString())
            sb.append("  products=[\n"); // Начало списка продуктов

            // Форматируем список продуктов с отступами
            if (products.isEmpty()) {
                // sb.append("\n"); // Можно оставить пустую строку внутри скобок
            } else {
                String productsString = products.stream()
                        .filter(Objects::nonNull) // Пропускаем null продукты, если они возможны
                        .map(p -> "    " + p.toString()) // Добавляем больший отступ для элементов списка
                        .collect(Collectors.joining(",\n")); // Соединяем через запятую и перенос строки
                sb.append(productsString).append("\n");
            }

            sb.append("  ]\n"); // Закрывающая скобка списка
            sb.append("}"); // Закрывающая скобка объекта
            return sb.toString();
        }

        // equals() и hashCode() для полноты класса
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Order order = (Order) o;
            return orderId == order.orderId && customer.equals(order.customer) && products.equals(order.products);
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
        Customer alice = new Customer("Alice Smith");
        Customer bob = new Customer("Bob Johnson");

        List<Product> productList1 = List.of(
                new Product("Laptop Pro", 1250.99),
                new Product("Wireless Mouse", 29.50),
                new Product("Mechanical Keyboard", 85.00)
        );
        Order order1 = new Order(20240401, alice, productList1);

        List<Product> productList2 = List.of(); // Пустой список продуктов
        Order order2 = new Order(20240402, bob, productList2);

        List<Product> productList3 = List.of(new Product("USB Drive", 15.00));
        Order order3 = new Order(20240403, alice, productList3);


        System.out.println("--- Order 1 ---");
        System.out.println(order1); // Вызов order1.toString()

        System.out.println("\n--- Order 2 (Empty Products) ---");
        System.out.println(order2);

        System.out.println("\n--- Order 3 ---");
        System.out.println(order3);
    }
}
