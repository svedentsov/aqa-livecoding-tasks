package com.svedentsov.aqa.tasks.oop_design;

/**
 * Решение задачи №14: Поменять местами две переменные без использования третьей.
 */
public class Task14_SwapVariables {

    /**
     * Демонстрирует два способа обмена значениями двух целочисленных переменных
     * без использования временной (третьей) переменной:
     * 1. С использованием арифметических операций (+, -).
     * 2. С использованием побитовой операции XOR (^).
     * Выводит результаты в консоль.
     * Важно: Арифметический метод может привести к переполнению, если сумма/разность
     * выходит за пределы диапазона int. Метод с XOR безопасен от переполнения.
     */
    public void demonstrateSwap() {
        int a = 5;
        int b = 10;
        System.out.println("Initial: a = " + a + ", b = " + b);

        System.out.println("\n--- Using Arithmetic ---");
        // Используем более безопасную комбинацию с вычитанием,
        // хотя риск переполнения все равно остается для граничных значений.
        a = a - b; // a = 5 - 10 = -5
        b = a + b; // b = -5 + 10 = 5
        a = b - a; // a = 5 - (-5) = 10
        System.out.println("After Arithmetic: a = " + a + ", b = " + b);

        // Сброс значений для второго метода
        a = 5;
        b = 10;

        System.out.println("\n--- Using XOR ---");
        // Метод XOR: a^=b; b^=a; a^=b;
        a = a ^ b; // a = 5 ^ 10 (15)
        b = a ^ b; // b = 15 ^ 10 (5)
        a = a ^ b; // a = 15 ^ 5 (10)
        System.out.println("After XOR: a = " + a + ", b = " + b);
    }

    /**
     * Точка входа для демонстрации обмена переменных.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task14_SwapVariables sol = new Task14_SwapVariables();
        sol.demonstrateSwap();
    }
}
