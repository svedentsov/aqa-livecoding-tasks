package com.svedentsov.aqa.tasks.files_io_formats;

import java.util.List;
import java.util.Objects;

/**
 * Решение задачи №70: Сериализация/Десериализация в JSON (Концептуально).
 * Описание: Обсудить, как бы это делалось с помощью библиотеки типа Jackson/Gson
 * (без написания полного кода). (Проверяет: понимание форматов данных, библиотек)
 * Задание: Объясните, как бы вы сериализовали объект класса
 * `Person {String name; int age;}` в JSON-строку
 * (например, `{"name":"John", "age":30}`) и десериализовали обратно,
 * используя гипотетическую библиотеку (или упомянув Jackson/Gson).
 * Пример: Обсуждение использования аннотаций, `ObjectMapper` и т.д.
 */
public class JsonConcept {

    /**
     * Пример простого POJO (Plain Old Java Object) для демонстрации.
     * Поля сделаны публичными для простоты примера, в реальных задачах
     * они должны быть приватными с геттерами/сеттерами (или использоваться Lombok).
     * Конструктор по умолчанию важен для многих библиотек десериализации.
     */
    static class PersonForJson {
        public String name;
        public int age;
        public List<String> hobbies; // Пример поля с коллекцией
        public boolean isActive; // Пример boolean поля
        // transient поле будет проигнорировано большинством библиотек по умолчанию
        public transient String internalNotes = "Some notes";

        // Конструктор по умолчанию (часто обязателен)
        public PersonForJson() {
        }

        public PersonForJson(String name, int age, List<String> hobbies, boolean active) {
            this.name = name;
            this.age = age;
            this.hobbies = hobbies;
            this.isActive = active;
        }

        @Override
        public String toString() {
            return "PersonForJson{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", hobbies=" + hobbies +
                    ", isActive=" + isActive +
                    '}';
        }

        // equals и hashCode важны, если объекты используются в коллекциях (Set, Map)
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PersonForJson that = (PersonForJson) o;
            return age == that.age && isActive == that.isActive && Objects.equals(name, that.name) && Objects.equals(hobbies, that.hobbies);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age, hobbies, isActive);
        }
    }

    /**
     * Проводит концептуальное обсуждение процесса сериализации Java-объекта в JSON
     * и десериализации JSON обратно в Java-объект с упоминанием библиотек Jackson и Gson.
     */
    public static void discussJsonSerialization() {
        System.out.println("--- Концептуальное Обсуждение Сериализации/Десериализации в JSON ---");

        System.out.println("\n[1] Цель:");
        System.out.println("  - Сериализация: Преобразовать объект Java (например, Person) в текстовую строку JSON.");
        System.out.println("                  Пример: `new Person(\"John\", 30)` -> `{\"name\":\"John\",\"age\":30}`");
        System.out.println("  - Десериализация: Восстановить объект Java из строки JSON.");
        System.out.println("                  Пример: `{\"name\":\"Jane\",\"age\":25}` -> объект Person с name=\"Jane\", age=25");

        System.out.println("\n[2] Применение:");
        System.out.println("  - Веб-сервисы (REST API): Обмен данными между клиентом и сервером.");
        System.out.println("  - Конфигурационные файлы: Хранение настроек.");
        System.out.println("  - Хранение данных: Простые сценарии хранения в файлах.");
        System.out.println("  - Взаимодействие систем: Обмен данными между различными приложениями/сервисами.");

        System.out.println("\n[3] Популярные Библиотеки Java:");
        System.out.println("  - Jackson (com.fasterxml.jackson.core): Наиболее распространенная, мощная, быстрая.");
        System.out.println("    - Основной класс: `ObjectMapper`.");
        System.out.println("  - Gson (com.google.gson): От Google, простая в использовании.");
        System.out.println("    - Основной класс: `Gson` (часто через `GsonBuilder`).");
        System.out.println("  - JSON-B (jakarta.json.bind): Стандарт Jakarta EE.");
        System.out.println("    - Основной класс: `Jsonb`.");

        System.out.println("\n[4] Процесс (на примере Jackson):");

        System.out.println("  А) Сериализация (Объект -> JSON):");
        System.out.println("     1. Создать экземпляр `ObjectMapper`: `ObjectMapper mapper = new ObjectMapper();`");
        System.out.println("     2. Создать объект для сериализации: `PersonForJson person = new PersonForJson(...);`");
        System.out.println("     3. Вызвать метод `writeValueAsString()`: `String json = mapper.writeValueAsString(person);`");
        System.out.println("     4. (Опционально) Настроить ObjectMapper (например, для красивого вывода): `mapper.enable(SerializationFeature.INDENT_OUTPUT);`");

        System.out.println("\n  Б) Десериализация (JSON -> Объект):");
        System.out.println("     1. Иметь строку JSON: `String jsonInput = \"{\\\"name\\\":\\\"Alice\\\",...}\";`");
        System.out.println("     2. Создать `ObjectMapper`: `ObjectMapper mapper = new ObjectMapper();`");
        System.out.println("     3. Вызвать `readValue()`: `PersonForJson person = mapper.readValue(jsonInput, PersonForJson.class);`");
        System.out.println("     4. Важно: Класс `PersonForJson` ДОЛЖЕН иметь конструктор по умолчанию (без аргументов),");
        System.out.println("        либо конструктор, аннотированный `@JsonCreator` с аннотациями `@JsonProperty` для параметров.");
        System.out.println("     5. (Опционально) Настроить обработку неизвестных полей: `mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);`");

        System.out.println("\n[5] Ключевые моменты и Настройки:");
        System.out.println("  - Соответствие полей: По умолчанию имена полей Java должны совпадать с ключами JSON.");
        System.out.println("    - Для переименования: Аннотации `@JsonProperty(\"json_key_name\")` (Jackson), `@SerializedName(\"json_key_name\")` (Gson).");
        System.out.println("  - Игнорирование полей: Модификатор `transient`, аннотации `@JsonIgnore` (Jackson), `@Expose` (Gson с настройкой).");
        System.out.println("  - Формат дат: Часто требует настройки (`@JsonFormat`, `setDateFormat` у ObjectMapper, кастомные (де)сериализаторы).");
        System.out.println("  - Обработка Null: Настройка включения/исключения null-полей (`mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);`).");
        System.out.println("  - Вложенные объекты/Коллекции: Обычно обрабатываются автоматически, если типы известны.");
        System.out.println("  - Конструкторы/Фабричные методы: `@JsonCreator`, `@JsonValue`, `@JsonProperty` для параметров.");
        System.out.println("---------------------------------------------------");
    }

    /**
     * Главный метод для запуска обсуждения.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        discussJsonSerialization();

        // Пример объекта для наглядности
        PersonForJson person = new PersonForJson("Example Person", 42, List.of("Java", "JSON"), true);
        System.out.println("\nПример объекта: " + person);
        System.out.println("Примерный JSON (без красивого вывода): {\"name\":\"Example Person\",\"age\":42,\"hobbies\":[\"Java\",\"JSON\"],\"active\":true}");
        System.out.println("Примерный JSON (с красивым выводом):");
        System.out.println("{");
        System.out.println("  \"name\" : \"Example Person\",");
        System.out.println("  \"age\" : 42,");
        System.out.println("  \"hobbies\" : [ \"Java\", \"JSON\" ],");
        System.out.println("  \"active\" : true");
        System.out.println("}");
    }
}
