package com.svedentsov.aqa.tasks.strings;

/**
 * Решение задачи №41: Валидация IP-адреса (IPv4).
 * <p>
 * Описание: Написать функцию для проверки строки на соответствие формату IPv4.
 * (Проверяет: работа со строками, `split()`, условия, парсинг чисел)
 * <p>
 * Задание: Напишите метод `boolean isValidIPv4(String ip)`, который проверяет,
 * является ли строка `ip` валидным IPv4 адресом. Валидный адрес состоит из
 * четырех чисел от 0 до 255, разделенных точками. Ведущие нули не допускаются
 * (кроме самого числа 0).
 * <p>
 * Пример: `isValidIPv4("192.168.0.1")` -> `true`.
 * `isValidIPv4("255.255.255.255")` -> `true`.
 * `isValidIPv4("0.0.0.0")` -> `true`.
 * `isValidIPv4("256.1.1.1")` -> `false`.
 * `isValidIPv4("192.168.0.01")` -> `false`.
 * `isValidIPv4("192.168..1")` -> `false`.
 * `isValidIPv4("abc.def.ghi.jkl")` -> `false`.
 */
public class ValidateIpAddress {

    /**
     * Проверяет, является ли строка валидным IPv4 адресом.
     * Валидный IPv4 адрес состоит из четырех чисел (октетов) от 0 до 255,
     * разделенных точками. Ведущие нули не допускаются, кроме самого числа "0".
     * Использует разбор строки и проверку частей.
     * <p>
     * Алгоритм:
     * 1. Проверить на null.
     * 2. Разделить строку по точкам (`split("\\.", -1)`). Лимит -1 важен.
     * 3. Проверить, что получилось ровно 4 части.
     * 4. Для каждой части:
     * a. Проверить на пустоту.
     * b. Проверить на длину > 3.
     * c. Проверить на ведущие нули (длина > 1 и первый символ '0').
     * d. Попытаться распарсить как int. Если не удалось (NumberFormatException) -> false.
     * e. Проверить, что число в диапазоне [0, 255].
     * 5. Если все проверки пройдены, вернуть true.
     *
     * @param ip Строка для проверки. Может быть null.
     * @return {@code true}, если строка является валидным IPv4 адресом, {@code false} в противном случае.
     */
    public boolean isValidIPv4(String ip) {
        // Шаг 1: Проверка на null
        if (ip == null) {
            return false;
        }

        // Шаг 2: Разделение по точкам
        // Используем "\\." т.к. "." - спецсимвол в regex.
        // Лимит -1 гарантирует, что конечные пустые строки не будут отброшены (например, "1.2.3.").
        String[] parts = ip.split("\\.", -1);

        // Шаг 3: Проверка количества частей
        if (parts.length != 4) {
            return false;
        }

        // Шаг 4: Проверка каждой части
        for (String part : parts) {
            // Шаг 4a, 4b: Проверка на пустоту и длину
            if (part.isEmpty() || part.length() > 3) {
                return false;
            }

            // Шаг 4c: Проверка на ведущие нули (кроме единственного нуля "0")
            if (part.length() > 1 && part.charAt(0) == '0') {
                return false;
            }

            // Шаг 4d, 4e: Парсинг и проверка диапазона
            try {
                int num = Integer.parseInt(part);
                // Проверка диапазона [0, 255]
                if (num < 0 || num > 255) {
                    return false;
                }
                // Проверка на нецифровые символы неявно происходит при parseInt
            } catch (NumberFormatException e) {
                // Если parseInt бросил исключение (например, для "abc") -> невалидно
                return false;
            }
        }

        // Шаг 5: Если все проверки пройдены
        return true;
    }

    /*
    // --- Альтернатива с регулярным выражением (сложная и потенциально неполная) ---
    // Этот regex не обрабатывает ведущие нули так же строго, как ручной парсер.
    // "01.02.03.04" будет считаться валидным этим regex.
    // Для полной эквивалентности regex будет еще сложнее.
    private static final String IPV4_OCTET_REGEX = "([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])";
    private static final Pattern IPV4_PATTERN = Pattern.compile(
            "^" + IPV4_OCTET_REGEX + "\\." + IPV4_OCTET_REGEX + "\\." +
                  IPV4_OCTET_REGEX + "\\." + IPV4_OCTET_REGEX + "$"
    );

    public boolean isValidIPv4Regex(String ip) {
        if (ip == null) {
            return false;
        }
        return IPV4_PATTERN.matcher(ip).matches();
    }
    */

    /**
     * Точка входа для демонстрации работы метода валидации IPv4.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        ValidateIpAddress sol = new ValidateIpAddress();
        String[] testIPs = {
                "192.168.0.1",    // true
                "255.255.255.255", // true
                "0.0.0.0",       // true
                "1.1.1.1",       // true
                "10.0.42.100",   // true
                "256.1.1.1",     // false (октент > 255)
                "192.168.0.256", // false (октент > 255)
                "192.168.0.01",  // false (ведущий ноль)
                "01.02.03.04",   // false (ведущие нули)
                "192.168..1",    // false (пустой октент)
                "1.2.3.4.",      // false (4 части, но последняя пустая из-за конечной точки)
                ".1.2.3.4",      // false (пустой первый октент)
                "abc.def.ghi.jkl",// false (не числа)
                "192.168.0",     // false (3 части)
                "1.2.3.4.5",     // false (5 частей)
                "192.168.0.1 ",  // false (содержит пробел, split может работать некорректно или часть будет невалидна) - зависит от точной обработки пробелов. Наш метод вернет false, т.к. "1 " не распарсится.
                "-1.0.0.0",      // false (отрицательное число)
                "1.2.3.0",       // true (одиночный ноль валиден)
                null,            // false
                ""               // false
        };

        System.out.println("--- IPv4 Validation (String Parsing) ---");
        for (String ip : testIPs) {
            runIpTest(sol, ip);
        }
    }

    /**
     * Вспомогательный метод для тестирования валидации IP.
     *
     * @param sol Экземпляр решателя.
     * @param ip  IP адрес для проверки.
     */
    private static void runIpTest(ValidateIpAddress sol, String ip) {
        String input = (ip == null ? "null" : "'" + ip + "'");
        try {
            boolean isValid = sol.isValidIPv4(ip);
            System.out.printf("isValidIPv4(%s) -> %b%n", input, isValid);
            // Здесь можно добавить сравнение с ожидаемым значением, если они переданы
        } catch (Exception e) {
            System.err.printf("Error processing %s: %s%n", input, e.getMessage());
        }
    }
}
