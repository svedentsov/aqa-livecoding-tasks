package com.svedentsov.aqa.tasks.system_concepts;

/**
 * Решение задачи №73: Реализация Rate Limiter (концептуально).
 * Содержит обсуждение различных алгоритмов.
 */
public class Task73_RateLimiterConcept {

    /**
     * Проводит концептуальное обсуждение реализации Rate Limiter (ограничителя скорости).
     * Описывает цель, причины использования и основные алгоритмы.
     */
    public static void discussRateLimiter() {
        System.out.println("Обсуждение Rate Limiter (Ограничитель Скорости):");
        System.out.println("================================================");
        System.out.println("1. ЦЕЛЬ:");
        System.out.println("   - Контролировать и ограничивать частоту запросов к определенному ресурсу или API.");
        System.out.println("   - Обычно определяется как 'не более N запросов за период T' (например, 100 запросов в минуту).");
        System.out.println("   - Может применяться глобально, на пользователя, на IP-адрес, на ключ API и т.д.");

        System.out.println("\n2. ЗАЧЕМ НУЖЕН?");
        System.out.println("   - Защита от перегрузки: Предотвращает исчерпание ресурсов сервера (CPU, память, сеть, БД).");
        System.out.println("   - Предотвращение злоупотреблений: Защита от DoS/DDoS атак, brute-force, скрейпинга.");
        System.out.println("   - Справедливое использование: Гарантирует, что один пользователь не монополизирует ресурсы.");
        System.out.println("   - Управление затратами: Ограничение вызовов платных сторонних API.");
        System.out.println("   - Монетизация: Предоставление разных уровней обслуживания с разными лимитами.");

        System.out.println("\n3. ОСНОВНЫЕ АЛГОРИТМЫ РЕАЛИЗАЦИИ:");

        System.out.println("\n   А) Token Bucket (Ведро с токенами):");
        System.out.println("      - Как работает: Ведро имеет емкость (`capacity`). Токены добавляются с пост. скоростью (`refillRate`). Запрос 'потребляет' токен. Если токенов нет - запрос отклоняется.");
        System.out.println("      - Плюсы: Позволяет краткосрочные всплески (bursts) до `capacity`. Средняя скорость ограничена `refillRate`. Относительно прост.");
        System.out.println("      - Минусы: Реализация требует отслеживания времени последнего пополнения.");
        System.out.println("      - Структура данных (концепт): `Map<Key, {long lastRefillTime, double currentTokens}>`.");

        System.out.println("\n   Б) Leaky Bucket (Дырявое ведро):");
        System.out.println("      - Как работает: Запросы поступают в очередь (ведро) фикс. размера (`bucketSize`). Обрабатываются из очереди с пост. скоростью (`leakRate`). Если очередь полна - запрос отбрасывается.");
        System.out.println("      - Плюсы: Гарантирует постоянную исходящую скорость, сглаживает всплески.");
        System.out.println("      - Минусы: Всплески отбрасываются, даже если средняя скорость ниже лимита. Задержка обработки.");
        System.out.println("      - Структура данных (концепт): `Map<Key, Queue<Request>>` или `Map<Key, {int queueSize, long lastLeakTime}>`.");

        System.out.println("\n   В) Fixed Window Counter (Счетчик в фиксированном окне):");
        System.out.println("      - Как работает: Время делится на интервалы (`windowSize`, н-р, 60 сек). Считаются запросы в каждом интервале. Если счетчик < `limit`, запрос проходит, счетчик ++, иначе - отказ.");
        System.out.println("      - Плюсы: Очень прост в реализации (атомарный счетчик + временная метка окна).");
        System.out.println("      - Минусы: Проблема 'граничного всплеска' - возможен всплеск 2*`limit` на стыке двух окон.");
        System.out.println("      - Структура данных (концепт): `Map<Key, {long windowStartTimestamp, AtomicInteger counter}>` (часто в Redis/Memcached с TTL).");

        System.out.println("\n   Г) Sliding Window Log (Лог в скользящем окне):");
        System.out.println("      - Как работает: Хранятся временные метки всех запросов за последний период (`windowSize`). При запросе удаляются старые метки, считается количество оставшихся. Если < `limit`, запрос проходит, метка добавляется.");
        System.out.println("      - Плюсы: Наиболее точный подсчет в скользящем окне.");
        System.out.println("      - Минусы: Требует много памяти/ресурсов для хранения и обработки меток.");
        System.out.println("      - Структура данных (концепт): `Map<Key, SortedSet<Timestamp>>` или `Map<Key, Deque<Timestamp>>`.");

        System.out.println("\n   Д) Sliding Window Counter (Счетчик в скользящем окне):");
        System.out.println("      - Как работает: Компромисс. Хранит счетчики для текущего и предыдущего окон. Оценивает количество запросов в 'скользящем' окне на основе этих двух счетчиков и текущего времени внутри окна.");
        System.out.println("      - Плюсы: Баланс между точностью Sliding Log и эффективностью Fixed Window.");
        System.out.println("      - Минусы: Реализация сложнее Fixed Window, менее точен, чем Sliding Log.");
        System.out.println("      - Структура данных (концепт): `Map<Key, {long prevWindowStart, int prevCount, long currentWindowStart, int currentCount}>`.");

        System.out.println("\n4. МЕСТО РЕАЛИЗАЦИИ В СИСТЕМЕ:");
        System.out.println("   - API Gateway / Load Balancer / Reverse Proxy (Nginx, HAProxy, Kong, Apigee): Централизованный контроль на входе.");
        System.out.println("   - Middleware / Filter / Interceptor (в коде приложения): Контроль на уровне конкретного сервиса/эндпоинта.");
        System.out.println("   - Отдельный Rate Limiter Service: Выделенный сервис для управления лимитами.");

        System.out.println("\n5. ХРАНЕНИЕ СОСТОЯНИЯ (в распределенных системах):");
        System.out.println("   - In-Memory хранилища (Redis, Memcached): Быстро, часто используется для счетчиков Fixed/Sliding Window.");
        System.out.println("   - Базы данных: Менее подходят для высокочастотных обновлений счетчиков.");

        System.out.println("\n6. ВЫБОР АЛГОРИТМА:");
        System.out.println("   - Зависит от требований: допустимы ли всплески, важна ли постоянная скорость, простота реализации, требования к точности и ресурсам.");
        System.out.println("   - Token Bucket часто является хорошим компромиссом.");
        System.out.println("================================================");
    }

    /**
     * Главный метод для запуска обсуждения.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        discussRateLimiter();
    }
}
