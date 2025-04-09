package com.svedentsov.aqa.tasks.strings;

/**
 * Решение задачи №41: Валидация IP-адреса (IPv4).
 */
public class Task41_ValidateIpAddress {

    /**
     * Проверяет, является ли строка валидным IPv4 адресом.
     * Валидный IPv4 адрес состоит из четырех чисел (октетов) от 0 до 255,
     * разделенных точками. Ведущие нули не допускаются, кроме самого числа "0".
     * Использует разбор строки и проверку частей.
     *
     * @param ip Строка для проверки. Может быть null.
     * @return {@code true}, если строка является валидным IPv4 адресом, {@code false} в противном случае.
     */
    public boolean isValidIPv4(String ip) {
        // 1. Проверка на null
        if (ip == null) {
            return false;
        }

        // 2. Разделение строки по точкам. Используем "\\." для экранирования точки.
        //    Лимит -1 важен, чтобы пустые части в конце (например, "1.2.3.") не игнорировались.
        String[] parts = ip.split("\\.", -1);

        // 3. Проверка количества частей (должно быть ровно 4).
        if (parts.length != 4) {
            return false;
        }

        // 4. Проверка каждой части (октета).
        for (String part : parts) {
            // 4.1. Проверка на пустоту или длину > 3 (например, "1..2" или "1.1000.2.3").
            if (part.isEmpty() || part.length() > 3) {
                return false;
            }

            // 4.2. Проверка на ведущие нули. "0" валидно, "01", "001" - невалидны.
            if (part.length() > 1 && part.charAt(0) == '0') {
                return false;
            }

            // 4.3. Попытка парсинга числа и проверка диапазона [0, 255].
            try {
                int num = Integer.parseInt(part);
                // Проверяем диапазон и неявно проверяем на наличие нецифровых символов (parseInt выбросит исключение).
                if (num < 0 || num > 255) {
                    return false;
                }
            } catch (NumberFormatException e) {
                // Если parseInt не смог распарсить (например, "abc"), это невалидный IP.
                return false;
            }
        }

        // Если все проверки для всех 4 частей пройдены, IP адрес валиден.
        return true;
    }

    // --- Альтернатива с регулярным выражением (менее читаемая, но возможная) ---
    /*
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
        Task41_ValidateIpAddress sol = new Task41_ValidateIpAddress();
        String[] testIPs = {
                "192.168.0.1",    // true
                "255.255.255.255", // true
                "0.0.0.0",       // true
                "1.1.1.1",       // true
                "10.0.42.100",   // true
                "256.1.1.1",     // false
                "192.168.0.256", // false
                "192.168.0.01",  // false
                "01.02.03.04",   // false
                "192.168..1",    // false
                "1.2.3.4.",      // false
                ".1.2.3.4",      // false
                "abc.def.ghi.jkl",// false
                "192.168.0",     // false
                "1.2.3.4.5",     // false
                "192.168.0.1 ",  // false
                "-1.0.0.0",      // false
                "1.2.3.0",       // true
                null,            // false
                ""               // false
        };

        System.out.println("--- IPv4 Validation (String Parsing) ---");
        for (String ip : testIPs) {
            System.out.println("'" + ip + "' is valid IPv4: " + sol.isValidIPv4(ip));
        }

        /* // Тестирование Regex версии
         System.out.println("\n--- IPv4 Validation (Regex) ---");
         for (String ip : testIPs) {
             // Regex не обрабатывает ведущие нули так же строго, как ручной парсер
             // Например, "01.02.03.04" будет true по этому Regex.
             // Regex нужно доработать для полной эквивалентности.
             System.out.println("'" + ip + "' is valid IPv4 (Regex): " + sol.isValidIPv4Regex(ip));
         }
        */
    }
}
