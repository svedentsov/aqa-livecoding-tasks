package com.svedentsov.aqa.tasks.system_concepts;

import java.util.UUID;

/**
 * Решение задачи №76: Генерация уникальных ID (Концептуально).
 * <p>
 * Описание: Обсудить подходы (UUID, timestamp + counter).
 * (Проверяет: общие знания)
 * <p>
 * Задание: Обсудите различные подходы к генерации уникальных идентификаторов
 * в распределенной системе или в рамках одного приложения. Какие плюсы и минусы
 * у каждого подхода (например, UUID, Snowflake, Timestamp + Counter)?
 * <p>
 * Пример: Сравнение вероятности коллизий, производительности, сортируемости ID.
 */
public class UniqueIdConcept {

    /**
     * Проводит концептуальное обсуждение различных подходов к генерации уникальных ID.
     */
    public static void discussUniqueIdGeneration() {
        System.out.println("--- Обсуждение генерации уникальных ID ---");

        System.out.println("\n[1] Контекст и Требования:");
        System.out.println("    - Уникальность: В пределах процесса, машины, кластера, глобально?");
        System.out.println("    - Сортируемость: Нужна ли сортировка ID по времени создания?");
        System.out.println("    - Производительность: Насколько быстрой должна быть генерация?");
        System.out.println("    - Длина/Формат: Есть ли ограничения (например, для URL, БД)?");
        System.out.println("    - Читаемость: Важна ли для человека?");
        System.out.println("    - Зависимости: Допустимы ли внешние сервисы (БД, координатор)?");

        System.out.println("\n[2] Основные Подходы:");

        System.out.println("\n  А) UUID (Universally Unique Identifier):");
        System.out.println("     - Стандарт (RFC 4122), 128 бит.");
        System.out.println("     - Версия 4 (случайная):");
        System.out.println("       - Плюсы: Очень низкая вероятность коллизий, не требует координации.");
        System.out.println("       - Минусы: Длинный (36 символов), не сортируется по времени, может фрагментировать индексы БД.");
        System.out.println("       - Java: `UUID.randomUUID()`");
        System.out.println("     - Версия 1 (на основе времени и MAC):");
        System.out.println("       - Плюсы: Сортируется по времени (приблизительно), глобально уникален (если MAC уникальны).");
        System.out.println("       - Минусы: Раскрывает MAC и время, требует уникального MAC.");
        System.out.println("     - Версии 6/7 (новые, упорядоченные по времени): Комбинируют временную сортируемость и случайность.");
        UUID uuid4 = UUID.randomUUID();
        System.out.println("     - Пример (v4): " + uuid4);

        System.out.println("\n  Б) База Данных (Sequences / Auto-increment):");
        System.out.println("     - Плюсы: Просто, гарантированная уникальность в рамках таблицы, компактно (int/long).");
        System.out.println("     - Минусы: Централизованно (БД - узкое место), ID доступен только *после* вставки, не глобально уникален.");

        System.out.println("\n  В) Локальный Timestamp + Counter:");
        System.out.println("     - Идея: `(timestamp << N) | (counter++ & MASK)`.");
        System.out.println("     - Плюсы: Сортируется по времени, компактный (long), быстрая генерация.");
        System.out.println("     - Минусы: Уникальность только в пределах генератора, нужна синхронизация счетчика (AtomicLong), ограничение скорости в пределах мс, чувствительность к 'прыжкам' времени.");
        long ts = System.currentTimeMillis();
        long count = System.nanoTime() % 1000; // Очень упрощенный счетчик
        long localId = (ts << 20) | count; // Примерная компоновка
        System.out.println("     - Пример ID: " + localId);


        System.out.println("\n  Г) Snowflake (Twitter):");
        System.out.println("     - Идея: 64-битный ID, уникальный в распределенной системе.");
        System.out.println("     - Структура: `| Знак(0) | Timestamp (41 бит) | Worker ID (10 бит) | Sequence (12 бит) |`");
        System.out.println("     - Плюсы: Глобально уникален (при уникальных Worker ID), сортируется по времени, компактный (long), высокая пропускная способность (4096/мс/worker).");
        System.out.println("     - Минусы: Требует уникальных Worker ID (нужен механизм их назначения, например, ZooKeeper), чувствителен к синхронизации времени (NTP).");

        System.out.println("\n  Д) Другие варианты:");
        System.out.println("     - Nano ID, KSUID: Современные альтернативы, часто комбинируют сортируемость и компактность.");
        System.out.println("     - Хеширование: Генерация ID на основе контента (менее универсально).");

        System.out.println("\n[3] Вывод:");
        System.out.println("    - Для простых веб-приложений часто достаточно Auto-increment ID из БД.");
        System.out.println("    - Для распределенных систем без строгой сортировки - UUID v4 прост и надежен.");
        System.out.println("    - Если нужна сортировка по времени и глобальная уникальность - Snowflake или новые версии UUID (v6/v7) являются хорошим выбором.");
        System.out.println("    - Выбор зависит от конкретных требований проекта.");
        System.out.println("-----------------------------------");
    }

    /**
     * Главный метод для запуска обсуждения.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        discussUniqueIdGeneration();
    }
}
