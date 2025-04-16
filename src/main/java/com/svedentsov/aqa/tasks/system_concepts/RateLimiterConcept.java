package com.svedentsov.aqa.tasks.system_concepts;

/**
 * Решение задачи №73: Реализация Rate Limiter (концептуально).
 * <p>
 * Описание: Обсудить подходы (token bucket, leaky bucket).
 * (Проверяет: системный дизайн - очень базово)
 * <p>
 * Задание: Обсудите, как бы вы реализовали простой ограничитель скорости запросов
 * (Rate Limiter), который позволяет делать не более N запросов в секунду для
 * определенного пользователя или IP-адреса. Какие структуры данных вы бы
 * использовали? (Например, Token Bucket или Leaky Bucket).
 * <p>
 * Пример: Обсуждение использования `Map<UserId, Timestamp/Counter>` или
 * `Map<UserId, TokenBucketState>`.
 */
public class RateLimiterConcept {

    /**
     * Проводит концептуальное обсуждение реализации Rate Limiter (ограничителя скорости).
     * Описывает цель, причины использования и основные алгоритмы.
     */
    public static void discussRateLimiter() {
        System.out.println("--- Обсуждение Rate Limiter (Ограничитель Скорости) ---");

        System.out.println("\n[1] Цель:");
        System.out.println("    Контроль частоты запросов к API или ресурсу (N запросов / T времени).");
        System.out.println("    Применение: глобально, per-user, per-IP, per-API-key и т.д.");

        System.out.println("\n[2] Причины использования:");
        System.out.println("    - Защита от перегрузки сервера (CPU, память, сеть, БД).");
        System.out.println("    - Предотвращение злоупотреблений (DoS/DDoS, brute-force, scraping).");
        System.out.println("    - Обеспечение справедливого распределения ресурсов.");
        System.out.println("    - Управление затратами на сторонние API.");
        System.out.println("    - Монетизация (разные лимиты для разных планов).");

        System.out.println("\n[3] Основные Алгоритмы:");

        System.out.println("\n  А) Token Bucket (Ведро с токенами):");
        System.out.println("     - Концепция: Ведро емкостью `C`, токены добавляются со скоростью `R` (токенов/сек).");
        System.out.println("       Запрос требует 1 токен. Если токен есть - запрос проходит, токен убирается.");
        System.out.println("       Если токенов нет - запрос отклоняется/ставится в очередь.");
        System.out.println("     - Плюсы: Позволяет кратковременные всплески (bursts) до `C`. Прост.");
        System.out.println("     - Минусы: Требует хранения состояния (кол-во токенов, время последнего пополнения).");
        System.out.println("     - Данные (per-key): `{ long lastRefillTimestamp, double currentTokens }`");

        System.out.println("\n  Б) Leaky Bucket (Дырявое ведро):");
        System.out.println("     - Концепция: Запросы попадают в очередь фикс. размера (`C`). Обрабатываются с пост. скоростью (`R`).");
        System.out.println("       Если очередь полна - новые запросы отбрасываются.");
        System.out.println("     - Плюсы: Гарантирует плавную, постоянную скорость обработки. Сглаживает всплески.");
        System.out.println("     - Минусы: Всплески могут быть отброшены. Увеличивает задержку.");
        System.out.println("     - Данные (per-key): `{ Queue requestQueue, long lastLeakTimestamp }` (или счетчик + время).");

        System.out.println("\n  В) Fixed Window Counter (Счетчик в фиксированном окне):");
        System.out.println("     - Концепция: Время делится на интервалы (`T`, н-р, 1 минута). Считаем запросы в текущем интервале.");
        System.out.println("       Если счетчик < `N`, запрос проходит, счетчик++, иначе - отказ.");
        System.out.println("     - Плюсы: Очень прост, требует мало памяти (счетчик + время начала окна). Легко реализовать в Redis (INCR + EXPIRE).");
        System.out.println("     - Минусы: Проблема 'граничного всплеска' (burst на стыке окон может пропустить 2*N запросов).");
        System.out.println("     - Данные (per-key): `{ long windowStartTimestamp, AtomicInteger counter }`.");

        System.out.println("\n  Г) Sliding Window Log (Лог в скользящем окне):");
        System.out.println("     - Концепция: Храним временные метки всех запросов за последний период `T`.");
        System.out.println("       При новом запросе: удаляем метки старше `T`, считаем оставшиеся. Если < `N`, добавляем метку, пропускаем.");
        System.out.println("     - Плюсы: Наиболее точный. Нет проблемы граничного всплеска.");
        System.out.println("     - Минусы: Требует много памяти для хранения меток.");
        System.out.println("     - Данные (per-key): `{ SortedSet<Timestamp> requestTimestamps }` (или в Redis Sorted Set).");

        System.out.println("\n  Д) Sliding Window Counter (Счетчик в скользящем окне):");
        System.out.println("     - Концепция: Компромисс. Хранит счетчики для текущего и предыдущего окон. Оценка запросов в скользящем окне.");
        System.out.println("     - Плюсы: Точнее Fixed Window, требует меньше памяти, чем Sliding Log.");
        System.out.println("     - Минусы: Реализация сложнее Fixed Window, аппроксимация.");
        System.out.println("     - Данные (per-key): `{ long prevWindowTimestamp, int prevCount, long currentWindowTimestamp, int currentCount }`.");

        System.out.println("\n[4] Хранение Состояния (для распределенных систем):");
        System.out.println("    - Ключ: Обычно UserId, IP-адрес, ApiKey.");
        System.out.println("    - Значение: Состояние выбранного алгоритма (токены/время, счетчики/время, лог меток).");
        System.out.println("    - Где хранить: Часто используются быстрые In-Memory хранилища типа Redis или Memcached.");
        System.out.println("      - Преимущества: Атомарные операции (INCR, DECR), TTL (автоматическое удаление старых данных).");

        System.out.println("\n[5] Выбор Алгоритма:");
        System.out.println("    - Зависит от требований: Допуск всплесков? Плавность? Точность? Ресурсы?");
        System.out.println("    - Token Bucket - часто хороший баланс.");
        System.out.println("    - Fixed Window - прост, но уязвим на границах.");
        System.out.println("    - Sliding Window Log - самый точный, но ресурсоемкий.");
        System.out.println("---------------------------------------------------");
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
