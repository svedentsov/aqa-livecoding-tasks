package com.svedentsov.aqa.tasks.system_concepts;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * Решение задачи №74: Простая реализация Pub/Sub (концептуально + код).
 * Описание: Обсудить базовые компоненты.
 * (Проверяет: понимание паттернов обмена сообщениями)
 * Задание: Опишите основные компоненты и логику простой системы Publish/Subscribe
 * (Издатель/Подписчик) в памяти. Как бы вы хранили подписчиков на темы?
 * Как бы издатель отправлял сообщения?
 * Пример: Обсуждение `Map<Topic, List<Subscriber>>`, методов `subscribe(topic, subscriber)`,
 * `publish(topic, message)`.
 */
public class PubSubConcept {

    // --- Функциональный интерфейс для Подписчика ---

    /**
     * Представляет Подписчика, который может получать сообщения типа T.
     * Использует стандартный {@link Consumer} для простоты.
     *
     * @param <T> Тип сообщения.
     */
    @FunctionalInterface
    interface Subscriber<T> extends Consumer<T> {
        // Метод accept(T message) унаследован от Consumer
    }

    // --- Класс Брокера ---

    /**
     * Простой брокер сообщений (Broker), реализующий паттерн Publish/Subscribe
     * для обмена сообщениями в памяти приложения. Позволяет асинхронную доставку.
     *
     * @param <T> Тип сообщений, обрабатываемых брокером.
     */
    static class SimplePubSubBroker<T> {

        /**
         * Хранилище: Тема (String) -> Список подписчиков (List<Subscriber<T>>).
         */
        private final Map<String, List<Subscriber<T>>> subscribers = new ConcurrentHashMap<>();

        /**
         * Исполнитель для асинхронной доставки.
         */
        private final ExecutorService deliveryExecutor;
        /**
         * Флаг асинхронной доставки.
         */
        private final boolean asyncDelivery;

        /**
         * Создает брокер с синхронной доставкой.
         */
        public SimplePubSubBroker() {
            this(false, 0); // По умолчанию синхронно
        }

        /**
         * Создает брокер.
         *
         * @param asyncDelivery  true для асинхронной доставки.
         * @param threadPoolSize Размер пула потоков для асинхронной доставки (если true).
         */
        public SimplePubSubBroker(boolean asyncDelivery, int threadPoolSize) {
            this.asyncDelivery = asyncDelivery;
            if (asyncDelivery) {
                int poolSize = (threadPoolSize > 0) ? threadPoolSize : Runtime.getRuntime().availableProcessors();
                // Используем фабрику для именования потоков и установки флага демона
                ThreadFactory factory = r -> {
                    Thread t = Executors.defaultThreadFactory().newThread(r);
                    t.setDaemon(true); // Потоки не мешают завершению JVM
                    t.setName("PubSub-Delivery-" + t.getId());
                    return t;
                };
                this.deliveryExecutor = Executors.newFixedThreadPool(poolSize, factory);
                System.out.println("[Broker] Async delivery enabled (Pool size: " + poolSize + ")");
            } else {
                this.deliveryExecutor = null;
                System.out.println("[Broker] Sync delivery enabled");
            }
        }

        /**
         * Подписывает подписчика на тему.
         *
         * @param topic      Тема.
         * @param subscriber Подписчик.
         */
        public void subscribe(String topic, Subscriber<T> subscriber) {
            Objects.requireNonNull(topic, "Topic cannot be null");
            Objects.requireNonNull(subscriber, "Subscriber cannot be null");
            // CopyOnWriteArrayList потокобезопасен для чтения/итерации,
            // но запись (добавление/удаление) создает новую копию (может быть дорого при частых подписках/отписках).
            // Для частых изменений лучше использовать ConcurrentHashMap<String, Set<Subscriber>>
            // с синхронизацией при добавлении/удалении подписчика в Set.
            subscribers.computeIfAbsent(topic, k -> new CopyOnWriteArrayList<>()).add(subscriber);
            // System.out.println("[Broker] Subscribed " + subscriber.getClass().getSimpleName() + " to '" + topic + "'");
        }

        /**
         * Отписывает подписчика от темы.
         *
         * @param topic      Тема.
         * @param subscriber Подписчик.
         */
        public void unsubscribe(String topic, Subscriber<T> subscriber) {
            Objects.requireNonNull(topic, "Topic cannot be null");
            Objects.requireNonNull(subscriber, "Subscriber cannot be null");
            subscribers.computeIfPresent(topic, (k, list) -> {
                list.remove(subscriber);
                // System.out.println("[Broker] Unsubscribed " + subscriber.getClass().getSimpleName() + " from '" + topic + "'");
                // Если список подписчиков стал пуст, можно удалить и сам ключ темы
                return list.isEmpty() ? null : list;
            });
        }

        /**
         * Публикует сообщение в тему. Уведомляет всех подписчиков этой темы.
         *
         * @param topic   Тема.
         * @param message Сообщение.
         */
        public void publish(String topic, T message) {
            Objects.requireNonNull(topic, "Topic cannot be null");
            // Получаем список подписчиков (или пустой список, если темы нет)
            List<Subscriber<T>> topicSubscribers = subscribers.getOrDefault(topic, Collections.emptyList());

            // System.out.println("[Broker] Publishing to '" + topic + "' (" + topicSubscribers.size() + " subs): " + message);
            if (!topicSubscribers.isEmpty()) {
                // Итерация по CopyOnWriteArrayList безопасна при одновременной модификации (отписки)
                for (Subscriber<T> subscriber : topicSubscribers) {
                    deliverMessage(subscriber, message, topic); // Доставка сообщения
                }
            }
        }

        /**
         * Завершает работу пула потоков (если он был создан).
         */
        public void shutdown() {
            if (deliveryExecutor != null && !deliveryExecutor.isShutdown()) {
                System.out.println("[Broker] Shutting down executor service...");
                deliveryExecutor.shutdown(); // Инициирует мягкое завершение
                try {
                    // Ожидаем завершения задач
                    if (!deliveryExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                        System.err.println("[Broker] Executor did not terminate in time.");
                        deliveryExecutor.shutdownNow(); // Принудительное завершение
                    } else {
                        System.out.println("[Broker] Executor shut down.");
                    }
                } catch (InterruptedException e) {
                    deliveryExecutor.shutdownNow();
                    Thread.currentThread().interrupt();
                }
            }
        }

        // --- Точка входа ---

        /**
         * Точка входа для демонстрации работы простой Pub/Sub системы.
         *
         * @param args Аргументы командной строки (не используются).
         */
        public static void main(String[] args) {
            discussPubSubConcept(); // Сначала выводим обсуждение
            runPubSubDemo(); // Затем запускаем демо с кодом
        }

        /**
         * Выполняет доставку сообщения подписчику (синхронно или асинхронно).
         * Обрабатывает ошибки доставки для конкретного подписчика.
         */
        private void deliverMessage(Subscriber<T> subscriber, T message, String topic) {
            Runnable deliveryTask = () -> {
                try {
                    // Вызов метода подписчика
                    subscriber.accept(message);
                } catch (Exception e) {
                    // Логируем ошибку, чтобы не прерывать доставку другим
                    System.err.println("ERROR delivering to subscriber for topic '" + topic + "' : " + e.getMessage());
                }
            };

            if (asyncDelivery && deliveryExecutor != null && !deliveryExecutor.isShutdown()) {
                try {
                    deliveryExecutor.submit(deliveryTask); // Асинхронно
                } catch (RejectedExecutionException e) {
                    System.err.println("ERROR: Delivery task rejected (executor shutdown?).");
                }
            } else {
                deliveryTask.run(); // Синхронно
            }
        }

        /**
         * Выводит концептуальное обсуждение Pub/Sub.
         */
        private static void discussPubSubConcept() {
            System.out.println("--- Концептуальное Обсуждение Publish/Subscribe ---");
            System.out.println("[1] Идея:");
            System.out.println("    - Слабая связанность: Издатели (Publishers) не знают о Подписчиках (Subscribers), и наоборот.");
            System.out.println("    - Посредник (Брокер): Центральный компонент, который управляет подписками и маршрутизирует сообщения.");
            System.out.println("    - Темы (Topics): Категории сообщений. Издатели публикуют в тему, подписчики подписываются на тему.");

            System.out.println("\n[2] Основные Компоненты:");
            System.out.println("    - Издатель (Publisher): Отправляет сообщения в Брокер, указывая тему.");
            System.out.println("    - Подписчик (Subscriber): Подписывается на интересующие его темы у Брокера.");
            System.out.println("                         Реализует логику обработки полученных сообщений (часто через callback или интерфейс).");
            System.out.println("    - Брокер (Broker/Message Bus/Event Channel):");
            System.out.println("        - Хранит подписки (например, `Map<String topic, List<Subscriber>>`).");
            System.out.println("        - Принимает сообщения от издателей.");
            System.out.println("        - Находит всех подписчиков на тему полученного сообщения.");
            System.out.println("        - Доставляет сообщение каждому подписчику.");
            System.out.println("    - Сообщение (Message/Event): Данные, передаваемые от издателя к подписчикам.");
            System.out.println("    - Тема (Topic/Channel): Именованный канал для категоризации сообщений.");

            System.out.println("\n[3] Процесс:");
            System.out.println("    1. Подписчик A вызывает `broker.subscribe(\"news\", subscriberA_handler)`.");
            System.out.println("    2. Подписчик B вызывает `broker.subscribe(\"news\", subscriberB_handler)`.");
            System.out.println("    3. Подписчик C вызывает `broker.subscribe(\"weather\", subscriberC_handler)`.");
            System.out.println("    4. Издатель X вызывает `broker.publish(\"news\", message1)`.");
            System.out.println("    5. Брокер находит подписчиков A и B для темы \"news\".");
            System.out.println("    6. Брокер доставляет `message1` подписчикам A и B (вызывает их хендлеры).");
            System.out.println("    7. Издатель Y вызывает `broker.publish(\"weather\", message2)`.");
            System.out.println("    8. Брокер находит подписчика C для темы \"weather\".");
            System.out.println("    9. Брокер доставляет `message2` подписчику C.");

            System.out.println("\n[4] Реализация в Памяти (In-Memory):");
            System.out.println("    - Хранение подписок: `Map<String, List<Subscriber>>` (или `Set<Subscriber>` для уникальности).");
            System.out.println("      - Важно: Выбрать потокобезопасную реализацию (`ConcurrentHashMap`, `CopyOnWriteArrayList`/`Set` или внешняя синхронизация),");
            System.out.println("        если доступ к подпискам и публикация могут происходить из разных потоков.");
            System.out.println("    - Доставка сообщений:");
            System.out.println("        - Синхронная: `publish` вызывает методы подписчиков прямо в своем потоке.");
            System.out.println("        - Асинхронная: `publish` ставит задачи доставки в очередь (`BlockingQueue`) и использует пул потоков (`ExecutorService`) для их выполнения.");
            System.out.println("          -> Повышает отзывчивость издателя, изолирует подписчиков друг от друга.");

            System.out.println("\n[5] Преимущества Pub/Sub:");
            System.out.println("    - Слабая связанность / Декомпозиция системы.");
            System.out.println("    - Масштабируемость (можно добавлять издателей/подписчиков независимо).");
            System.out.println("    - Гибкость (легко менять компоненты).");

            System.out.println("\n[6] Недостатки / Сложности:");
            System.out.println("    - Гарантии доставки (нужны механизмы подтверждения/повторов в сложных системах).");
            System.out.println("    - Порядок сообщений (обычно не гарантируется между разными издателями).");
            System.out.println("    - Управление состоянием брокера.");
            System.out.println("    - Отладка (сложнее отследить поток данных).");
            System.out.println("---------------------------------------------------");
        }

        /**
         * Запускает демонстрацию работы кода Pub/Sub брокера.
         */
        private static void runPubSubDemo() {
            System.out.println("\n--- Simple Pub/Sub Code Demonstration ---");
            SimplePubSubBroker<String> broker = new SimplePubSubBroker<>(true, 2); // Асинхронно

            // Подписчики
            Subscriber<String> sub1 = msg -> System.out.println("  SUB1 [" + Thread.currentThread().getName() + "] got: " + msg);
            Subscriber<String> sub2 = msg -> System.out.println("  SUB2 [" + Thread.currentThread().getName() + "] got: " + msg.toUpperCase());
            Subscriber<String> sub3 = msg -> {
                System.out.println("  SUB3 [" + Thread.currentThread().getName() + "] start processing: " + msg);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("  SUB3 [" + Thread.currentThread().getName() + "] end processing.");
            };

            // Подписки
            System.out.println("Subscribing...");
            broker.subscribe("topicA", sub1);
            broker.subscribe("topicA", sub3);
            broker.subscribe("topicB", sub2);

            // Публикация
            System.out.println("\nPublishing...");
            broker.publish("topicA", "Hello A!"); // Уйдет sub1, sub3
            broker.publish("topicB", "Message for B"); // Уйдет sub2
            broker.publish("topicC", "Nobody home"); // Никто не получит

            // Ждем немного для асинхронной доставки
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Отписка и повторная публикация
            System.out.println("\nUnsubscribing SUB3 from topicA...");
            broker.unsubscribe("topicA", sub3);
            broker.publish("topicA", "Hello A again!"); // Уйдет только sub1

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            broker.shutdown(); // Завершаем работу брокера
        }
    }
}
