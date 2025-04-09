package com.svedentsov.aqa.tasks.system_concepts;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * Решение задачи №74: Простая реализация Pub/Sub (концептуально).
 * Содержит обсуждение и очень простую реализацию в памяти.
 * В этой версии исправлено использование фабричного метода Executors.
 */
public class Task74_PubSubConcept {

    /**
     * Функциональный интерфейс, представляющий Подписчика (Subscriber).
     * Определяет действие, которое нужно выполнить при получении сообщения.
     *
     * @param <T> Тип сообщения.
     */
    @FunctionalInterface
    interface Subscriber<T> extends Consumer<T> {
        /**
         * Метод, вызываемый брокером при публикации сообщения в тему,
         * на которую подписан этот подписчик.
         *
         * @param message Полученное сообщение.
         */
        @Override
        void accept(T message);
    }

    /**
     * Простой брокер сообщений (Broker), реализующий паттерн Publish/Subscribe
     * для обмена сообщениями в памяти приложения.
     * Использует прямое создание ThreadPoolExecutor вместо фабричного метода Executors.
     *
     * @param <T> Тип сообщений, обрабатываемых брокером.
     */
    static class SimplePubSubBroker<T> { // Делаем static для main

        /**
         * Хранилище подписок.
         * Ключ: Название темы (String).
         * Значение: Потокобезопасный список подписчиков (List<Subscriber<T>>) на эту тему.
         */
        private final Map<String, List<Subscriber<T>>> subscribers = new ConcurrentHashMap<>();

        /**
         * Пул потоков для асинхронной доставки сообщений.
         */
        private final ExecutorService deliveryExecutor;
        /**
         * Флаг, включена ли асинхронная доставка.
         */
        private final boolean asyncDelivery;

        /**
         * Создает брокер с синхронной доставкой.
         */
        public SimplePubSubBroker() {
            this(false, 0); // Синхронная доставка по умолчанию
        }

        /**
         * Создает брокер.
         *
         * @param asyncDelivery  true для асинхронной доставки, false для синхронной.
         * @param threadPoolSize Размер пула потоков для асинхронной доставки (если asyncDelivery=true).
         *                       Используется фиксированный размер пула.
         */
        public SimplePubSubBroker(boolean asyncDelivery, int threadPoolSize) {
            this.asyncDelivery = asyncDelivery;
            if (asyncDelivery) {
                int coreAndMaxPoolSize = (threadPoolSize > 0) ? threadPoolSize : Runtime.getRuntime().availableProcessors();

                // Создаем фабрику потоков для именования и установки демона
                ThreadFactory customThreadFactory = r -> {
                    Thread t = Executors.defaultThreadFactory().newThread(r);
                    t.setDaemon(true); // Потоки не должны мешать завершению JVM
                    t.setName("PubSub-Delivery-" + t.getId());
                    return t;
                };

                // Прямое создание ThreadPoolExecutor вместо Executors.newFixedThreadPool
                this.deliveryExecutor = new ThreadPoolExecutor(
                        coreAndMaxPoolSize,       // corePoolSize - всегда активные потоки
                        coreAndMaxPoolSize,       // maximumPoolSize - макс. кол-во потоков (равно core для fixed)
                        0L,                       // keepAliveTime - время жизни лишних потоков (0 для fixed)
                        TimeUnit.MILLISECONDS,    // unit for keepAliveTime
                        new LinkedBlockingQueue<Runnable>(), // Очередь задач (неограниченная для fixed pool)
                        customThreadFactory       // Наша фабрика потоков
                );
                System.out.println("[Broker] Created with async delivery (pool size: " + coreAndMaxPoolSize + ")");
            } else {
                this.deliveryExecutor = null;
                System.out.println("[Broker] Created with sync delivery");
            }
        }

        /**
         * Подписывает подписчика на указанную тему.
         *
         * @param topic      Название темы (не null).
         * @param subscriber Объект подписчика (не null).
         */
        public void subscribe(String topic, Subscriber<T> subscriber) {
            Objects.requireNonNull(topic, "Topic cannot be null");
            Objects.requireNonNull(subscriber, "Subscriber cannot be null");

            // Используем CopyOnWriteArrayList для потокобезопасной итерации при publish
            subscribers.computeIfAbsent(topic, k -> new CopyOnWriteArrayList<>())
                    .add(subscriber);

            // System.out.println("Subscriber subscribed to topic: '" + topic + "'");
        }

        /**
         * Отписывает подписчика от указанной темы.
         *
         * @param topic      Название темы (не null).
         * @param subscriber Объект подписчика для отписки (не null).
         */
        public void unsubscribe(String topic, Subscriber<T> subscriber) {
            Objects.requireNonNull(topic, "Topic cannot be null");
            Objects.requireNonNull(subscriber, "Subscriber cannot be null");

            subscribers.computeIfPresent(topic, (k, topicSubscribers) -> {
                topicSubscribers.remove(subscriber);
                // System.out.println("Subscriber unsubscribed from topic: '" + topic + "'");
                return topicSubscribers.isEmpty() ? null : topicSubscribers; // Удаляем тему, если нет подписчиков
            });
        }

        /**
         * Публикует сообщение в указанную тему.
         * Доставка может быть синхронной или асинхронной.
         *
         * @param topic   Название темы (не null).
         * @param message Сообщение для публикации.
         */
        public void publish(String topic, T message) {
            Objects.requireNonNull(topic, "Topic cannot be null");

            List<Subscriber<T>> topicSubscribers = subscribers.getOrDefault(topic, Collections.emptyList());

            // System.out.println("\nPublishing to topic '" + topic + "' (" + topicSubscribers.size() + " subs): " + message);
            if (!topicSubscribers.isEmpty()) {
                // Итерация по CopyOnWriteArrayList безопасна
                for (Subscriber<T> subscriber : topicSubscribers) {
                    deliverMessage(subscriber, message, topic);
                }
            }
        }

        /**
         * Выполняет доставку сообщения подписчику (синхронно или асинхронно).
         * Инкапсулирует логику вызова accept и обработку ошибок.
         */
        private void deliverMessage(Subscriber<T> subscriber, T message, String topic) {
            // Задача для выполнения
            Runnable deliveryTask = () -> {
                String threadName = Thread.currentThread().getName();
                try {
                    // System.out.println("  Delivering to " + subscriber.getClass().getSimpleName() + " on " + threadName);
                    subscriber.accept(message);
                } catch (Exception e) {
                    // Логируем ошибку, не прерывая доставку другим
                    System.err.println("ERROR processing message for "
                            + subscriber.getClass().getSimpleName()
                            + " on topic '" + topic + "' in thread " + threadName
                            + ": " + e.getMessage());
                    // e.printStackTrace(); // Для детального стектрейса
                }
            };

            // Выполняем задачу синхронно или асинхронно
            if (asyncDelivery && deliveryExecutor != null) {
                try {
                    deliveryExecutor.submit(deliveryTask); // Отправляем в пул потоков
                } catch (RejectedExecutionException e) {
                    // Это может произойти, если shutdown() уже вызван
                    System.err.println("ERROR: Delivery task rejected, executor likely shut down.");
                }
            } else {
                deliveryTask.run(); // Выполняем синхронно в текущем потоке
            }
        }

        /**
         * Корректно останавливает пул потоков для асинхронной доставки.
         * Пытается сначала завершить текущие задачи, потом прерывает.
         */
        public void shutdown() {
            if (deliveryExecutor != null && !deliveryExecutor.isShutdown()) {
                System.out.println("[Broker] Shutting down delivery executor...");
                deliveryExecutor.shutdown(); // Запрещает добавление новых задач, ждет завершения текущих
                try {
                    // Ожидаем завершения задач в течение короткого времени
                    if (!deliveryExecutor.awaitTermination(2, TimeUnit.SECONDS)) {
                        System.err.println("[Broker] Executor did not terminate gracefully, forcing shutdown...");
                        deliveryExecutor.shutdownNow(); // Прерывает выполняющиеся задачи
                    } else {
                        System.out.println("[Broker] Delivery executor shut down gracefully.");
                    }
                } catch (InterruptedException e) {
                    System.err.println("[Broker] Shutdown interrupted, forcing shutdown...");
                    deliveryExecutor.shutdownNow();
                    Thread.currentThread().interrupt(); // Восстанавливаем статус прерывания
                }
            }
        }
    }

    /**
     * Точка входа для демонстрации работы простой Pub/Sub системы.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) throws InterruptedException {
        // Создаем брокер (true - асинхронная доставка, 2 - 2 потока в пуле)
        SimplePubSubBroker<String> broker = new SimplePubSubBroker<>(true, 2);

        // --- Определение Подписчиков ---
        // Лямбда-выражения реализуют интерфейс Subscriber<String>
        Subscriber<String> newsReader = msg -> System.out.println("  NewsReader [" + Thread.currentThread().getName() + "] Received: " + msg);
        Subscriber<String> weatherWatcher = msg -> System.out.println("  WeatherWatcher [" + Thread.currentThread().getName() + "] ALERT: " + msg.toUpperCase());
        // Подписчик, имитирующий долгую обработку
        Subscriber<String> slowProcessor = msg -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("  SlowProcessor [" + threadName + "] START: " + msg);
            try {
                Thread.sleep(150); // Имитация работы
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("  SlowProcessor [" + threadName + "] END: " + msg);
        };
        // Подписчик, который может выбросить исключение
        Subscriber<String> faultyProcessor = msg -> {
            System.out.println("  FaultyProcessor [" + Thread.currentThread().getName() + "] Received: " + msg);
            if (msg.contains("error")) {
                throw new RuntimeException("Simulated processing error!");
            }
        };


        // --- Подписки ---
        System.out.println("--- Subscribing ---");
        broker.subscribe("news", newsReader);
        broker.subscribe("news", slowProcessor);
        broker.subscribe("news", faultyProcessor); // Добавим "проблемного" подписчика
        broker.subscribe("weather", weatherWatcher);
        broker.subscribe("general", newsReader);
        broker.subscribe("general", weatherWatcher);


        // --- Публикация Сообщений ---
        System.out.println("\n--- Publishing Messages ---");
        broker.publish("news", "Breaking news: Market update!"); // Уйдет 3 подписчикам
        broker.publish("weather", "Expect sunshine today.");     // Уйдет 1 подписчику
        broker.publish("general", "General announcement.");      // Уйдет 2 подписчикам
        broker.publish("unknown", "This message has no subs."); // Никто не получит
        broker.publish("news", "This news might cause an error."); // faultyProcessor выбросит исключение


        // Даем время асинхронным задачам выполниться
        System.out.println("\n--- Waiting for async delivery (approx 300ms)... ---");
        Thread.sleep(300); // Увеличим время ожидания

        // --- Отписка ---
        System.out.println("\n--- Unsubscribing slowProcessor from news ---");
        broker.unsubscribe("news", slowProcessor);

        // --- Повторная Публикация ---
        System.out.println("\n--- Publishing Again ---");
        broker.publish("news", "Follow-up news report."); // Уйдет newsReader и faultyProcessor

        // Даем время асинхронным задачам выполниться
        Thread.sleep(100);


        // --- Завершение работы ---
        broker.shutdown(); // Важно остановить пул потоков
        System.out.println("\n--- Demo Finished ---");
    }
}
