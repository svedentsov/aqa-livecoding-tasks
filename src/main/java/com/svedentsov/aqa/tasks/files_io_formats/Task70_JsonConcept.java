package com.svedentsov.aqa.tasks.files_io_formats; // или system_concepts

import java.util.List;

/**
 * Решение задачи №70: Сериализация/Десериализация в JSON (Концептуально).
 * Содержит обсуждение процесса и упоминание библиотек.
 */
public class Task70_JsonConcept {

    // Пример класса для демонстрации
    static class PersonForJson {
        public String name;
        public int age;
        public List<String> hobbies;
        public boolean active;

        // Конструктор по умолчанию (часто нужен библиотекам)
        public PersonForJson() {
        }

        public PersonForJson(String name, int age, List<String> hobbies, boolean active) {
            this.name = name;
            this.age = age;
            this.hobbies = hobbies;
            this.active = active;
        }

        @Override
        public String toString() {
            return "PersonForJson{name='" + name + "', age=" + age + ", hobbies=" + hobbies + ", active=" + active + '}';
        }
        // equals и hashCode опущены для краткости концепта
    }

    /**
     * Проводит концептуальное обсуждение процесса сериализации Java-объекта в JSON
     * и десериализации JSON обратно в Java-объект с упоминанием библиотек Jackson и Gson.
     */
    public static void discussJsonSerialization() {
        System.out.println("Обсуждение сериализации/десериализации в JSON:");
        System.out.println("===============================================");
        System.out.println("1. ЧТО ЭТО?");
        System.out.println("   - Сериализация: Процесс преобразования состояния объекта Java в формат JSON (текстовая строка).");
        System.out.println("   - Десериализация: Обратный процесс - создание объекта Java из строки JSON.");

        System.out.println("\n2. ЗАЧЕМ ЭТО НУЖНО?");
        System.out.println("   - Передача данных: Между клиентом (браузер, моб. приложение) и сервером (API).");
        System.out.println("   - Хранение данных: Сохранение конфигураций или состояния в файлы.");
        System.out.println("   - Межсервисное взаимодействие: Обмен данными между микросервисами.");
        System.out.println("   - Читаемость: JSON легко читается как человеком, так и машиной.");

        System.out.println("\n3. ФОРМАТ JSON (JavaScript Object Notation):");
        System.out.println("   - Объекты: Пары \"ключ\": значение, заключенные в фигурные скобки `{}`. Ключи - строки.");
        System.out.println("   - Массивы: Упорядоченные списки значений, заключенные в квадратные скобки `[]`.");
        System.out.println("   - Значения: Строки (в двойных кавычках), числа, булевы (`true`/`false`), `null`, другие объекты или массивы.");
        System.out.println("   - Пример: `{\"name\":\"Alice\", \"age\":30, \"hobbies\":[\"Reading\", \"Hiking\"], \"active\":true}`");

        System.out.println("\n4. ПОПУЛЯРНЫЕ БИБЛИОТЕКИ В JAVA:");
        System.out.println("   - Jackson (com.fasterxml.jackson.core): Самая популярная, мощная, гибкая, стандарт для Spring.");
        System.out.println("     - Основной класс: `ObjectMapper`.");
        System.out.println("   - Gson (com.google.gson): Библиотека от Google, проста в использовании, хорошо работает \"из коробки\".");
        System.out.println("     - Основной класс: `Gson` (часто создается через `GsonBuilder`).");
        System.out.println("   - JSON-B (javax.json.bind / jakarta.json.bind): Стандарт Jakarta EE, основан на аннотациях.");
        System.out.println("     - Основной класс: `Jsonb` (создается через `JsonbBuilder`).");
        System.out.println("   - (Устаревшие/Менее популярные): org.json, JSON-P и др.");

        System.out.println("\n5. ОСНОВНЫЕ ОПЕРАЦИИ (НА ПРИМЕРЕ JACKSON):");
        System.out.println("   А) Сериализация (Java Object -> JSON String):");
        System.out.println("      `ObjectMapper mapper = new ObjectMapper();`");
        System.out.println("      `PersonForJson person = new PersonForJson(\"Bob\", 25, List.of(\"Gaming\"), true);`");
        System.out.println("      `String jsonString = mapper.writeValueAsString(person);`");
        System.out.println("      // jsonString будет примерно: '{\"name\":\"Bob\",\"age\":25,\"hobbies\":[\"Gaming\"],\"active\":true}'");

        System.out.println("\n   Б) Десериализация (JSON String -> Java Object):");
        System.out.println("      `String inputJson = \"{\\\"name\\\":\\\"Charlie\\\",\\\"age\\\":35,\\\"hobbies\\\":[],\\\"active\\\":false}\";`");
        System.out.println("      `PersonForJson personObj = mapper.readValue(inputJson, PersonForJson.class);`");
        System.out.println("      // personObj будет содержать соответствующие значения.");
        System.out.println("      // Требование: Класс PersonForJson должен иметь конструктор по умолчанию (или аннотированный конструктор).");


        System.out.println("\n6. ВАЖНЫЕ АСПЕКТЫ И НАСТРОЙКИ:");
        System.out.println("   - Имена полей: Если имена в Java и JSON не совпадают -> Аннотации (@JsonProperty, @SerializedName).");
        System.out.println("   - Игнорирование полей: @JsonIgnore, `transient`, @Expose.");
        System.out.println("   - Даты/Время: Требуют настройки формата (@JsonFormat, setDateFormat). Стандартно часто используется ISO 8601 или timestamp (мс).");
        System.out.println("   - Обработка `null`: Включать ли `null` поля в JSON (по умолчанию да), или игнорировать.");
        System.out.println("   - Конструкторы: Необходимость конструктора по умолчанию или использование аннотаций (@JsonCreator).");
        System.out.println("   - Вложенные объекты/Коллекции: Обычно обрабатываются автоматически.");
        System.out.println("   - Полиморфизм: Обработка наследования (требует аннотаций: @JsonTypeInfo, @JsonSubTypes).");
        System.out.println("   - Обработка ошибок: `JsonProcessingException` (Jackson), `JsonSyntaxException` (Gson).");
        System.out.println("===============================================");
    }

    /**
     * Главный метод для запуска обсуждения.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        discussJsonSerialization();

        // Демонстрация создания объекта для сериализации
        PersonForJson person = new PersonForJson("Alice", 30, List.of("Reading", "Hiking"), true);
        System.out.println("\nПример объекта Java: " + person);
        System.out.println("Примерный ожидаемый JSON:\n" +
                "{\"name\":\"Alice\",\"age\":30,\"hobbies\":[\"Reading\",\"Hiking\"],\"active\":true}");
    }
}
