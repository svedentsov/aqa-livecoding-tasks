package com.svedentsov.aqa.tasks.oop_design; // или algorithms

import java.util.Objects;

/**
 * Решение задачи №79: Написать простой класс для работы с дробями.
 * Реализует неизменяемый (immutable) класс Fraction для представления рациональных дробей.
 * <p>
 * Описание: Сложение, вычитание, упрощение. (Проверяет: ООП, арифметика, НОД)
 * <p>
 * Задание: Реализуйте класс `Fraction` с полями числитель (`numerator`)
 * и знаменатель (`denominator`). Добавьте конструктор, метод `toString()`
 * (например, "2/3"), и метод `add(Fraction other)` для сложения дробей
 * с приведением к общему знаменателю и упрощением результата (используя НОД).
 * Добавлены также методы subtract, multiply, divide.
 * <p>
 * Пример: `new Fraction(1, 2).add(new Fraction(1, 3))` должен вернуть объект
 * `Fraction`, представляющий `5/6`.
 */
public class FractionClass {

    /**
     * Представляет рациональную дробь вида числитель/знаменатель.
     * Дробь автоматически упрощается при создании с использованием НОД.
     * Знак дроби хранится только в числителе, знаменатель всегда положителен.
     * Класс является неизменяемым (immutable) и реализует {@link Comparable}.
     */
    public static final class Fraction implements Comparable<Fraction> {
        private final int numerator;    // Числитель
        private final int denominator;  // Знаменатель (всегда > 0)

        public static final Fraction ZERO = new Fraction(0);
        public static final Fraction ONE = new Fraction(1);

        /**
         * Создает дробь, представляющую целое число (numerator/1).
         *
         * @param wholeNumber Целое число.
         */
        public Fraction(int wholeNumber) {
            // Проверка на переполнение не нужна, т.к. просто присваиваем
            this.numerator = wholeNumber;
            this.denominator = 1;
        }


        /**
         * Создает объект Fraction из числителя и знаменателя.
         * Выполняет проверку знаменателя на ноль и упрощение дроби.
         *
         * @param numerator   Числитель.
         * @param denominator Знаменатель. Не должен быть равен нулю.
         * @throws IllegalArgumentException если знаменатель равен нулю.
         * @throws ArithmeticException      если происходит переполнение при обработке знака Integer.MIN_VALUE.
         */
        public Fraction(int numerator, int denominator) {
            if (denominator == 0) {
                throw new IllegalArgumentException("Denominator cannot be zero.");
            }

            // Обработка знака: переносим в числитель
            if (denominator < 0) {
                // Проверка на переполнение Integer.MIN_VALUE
                if (numerator == Integer.MIN_VALUE || denominator == Integer.MIN_VALUE) {
                    throw new ArithmeticException("Overflow handling Integer.MIN_VALUE.");
                }
                numerator = -numerator;
                denominator = -denominator;
            }

            // Упрощение дроби
            if (numerator == 0) {
                this.numerator = 0;
                this.denominator = 1;
            } else {
                int commonDivisor = gcd(Math.abs(numerator), denominator);
                this.numerator = numerator / commonDivisor;
                this.denominator = denominator / commonDivisor;
            }
        }

        public int getNumerator() {
            return numerator;
        }

        public int getDenominator() {
            return denominator;
        }

        public Fraction add(Fraction other) {
            Objects.requireNonNull(other, "Other fraction cannot be null");
            long num1 = this.numerator;
            long den1 = this.denominator;
            long num2 = other.numerator;
            long den2 = other.denominator;
            // Проверка на переполнение при вычислении числителя и знаменателя
            long newNumL = Math.addExact(Math.multiplyExact(num1, den2), Math.multiplyExact(num2, den1));
            long newDenL = Math.multiplyExact(den1, den2);
            // Проверка, что результат умещается в int перед созданием новой дроби
            if (newNumL < Integer.MIN_VALUE || newNumL > Integer.MAX_VALUE || newDenL < Integer.MIN_VALUE || newDenL > Integer.MAX_VALUE) {
                throw new ArithmeticException("Integer overflow during addition.");
            }
            return new Fraction((int) newNumL, (int) newDenL);
        }

        public Fraction subtract(Fraction other) {
            Objects.requireNonNull(other, "Other fraction cannot be null");
            long num1 = this.numerator;
            long den1 = this.denominator;
            long num2 = other.numerator;
            long den2 = other.denominator;
            long newNumL = Math.subtractExact(Math.multiplyExact(num1, den2), Math.multiplyExact(num2, den1));
            long newDenL = Math.multiplyExact(den1, den2);
            if (newNumL < Integer.MIN_VALUE || newNumL > Integer.MAX_VALUE || newDenL < Integer.MIN_VALUE || newDenL > Integer.MAX_VALUE) {
                throw new ArithmeticException("Integer overflow during subtraction.");
            }
            return new Fraction((int) newNumL, (int) newDenL);
        }

        public Fraction multiply(Fraction other) {
            Objects.requireNonNull(other, "Other fraction cannot be null");
            long num1 = this.numerator;
            long den1 = this.denominator;
            long num2 = other.numerator;
            long den2 = other.denominator;
            long newNumL = Math.multiplyExact(num1, num2);
            long newDenL = Math.multiplyExact(den1, den2);
            if (newNumL < Integer.MIN_VALUE || newNumL > Integer.MAX_VALUE || newDenL < Integer.MIN_VALUE || newDenL > Integer.MAX_VALUE) {
                throw new ArithmeticException("Integer overflow during multiplication.");
            }
            return new Fraction((int) newNumL, (int) newDenL);
        }

        public Fraction divide(Fraction other) {
            Objects.requireNonNull(other, "Other fraction cannot be null");
            if (other.numerator == 0) {
                throw new ArithmeticException("Division by zero fraction.");
            }
            long num1 = this.numerator;
            long den1 = this.denominator;
            long num2 = other.numerator;
            long den2 = other.denominator;
            long newNumL = Math.multiplyExact(num1, den2);
            long newDenL = Math.multiplyExact(den1, num2);
            if (newNumL < Integer.MIN_VALUE || newNumL > Integer.MAX_VALUE || newDenL < Integer.MIN_VALUE || newDenL > Integer.MAX_VALUE) {
                throw new ArithmeticException("Integer overflow during division.");
            }
            return new Fraction((int) newNumL, (int) newDenL);
        }

        public double doubleValue() {
            return (double) numerator / denominator;
        }

        @Override
        public String toString() {
            return denominator == 1 ? Integer.toString(numerator) : numerator + "/" + denominator;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Fraction fraction = (Fraction) o;
            // Сравнение упрощенных полей
            return numerator == fraction.numerator && denominator == fraction.denominator;
        }

        @Override
        public int hashCode() {
            return Objects.hash(numerator, denominator);
        }

        @Override
        public int compareTo(Fraction other) {
            Objects.requireNonNull(other, "Cannot compare to a null Fraction");
            // Сравниваем a/b и c/d через ad и bc
            long leftProduct = (long) this.numerator * other.denominator;
            long rightProduct = (long) other.numerator * this.denominator;
            return Long.compare(leftProduct, rightProduct);
        }

        // --- Приватные вспомогательные методы для Fraction ---

        /**
         * Вычисляет НОД двух неотрицательных целых чисел (алгоритм Евклида).
         */
        private static int gcd(int a, int b) {
            while (b != 0) {
                int temp = b;
                b = a % b;
                a = temp;
            }
            // a гарантированно неотрицательно, т.к. входные a и b были неотрицательны
            return a;
        }
    }

    /**
     * Точка входа для демонстрации использования класса Fraction.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        System.out.println("--- Fraction Class Demo ---");
        try {
            // Создание дробей
            System.out.println("\n[Creation & Simplification]");
            Fraction f1_2 = new Fraction(1, 2);     // 1/2
            Fraction f1_3 = new Fraction(1, 3);     // 1/3
            Fraction f2_4 = new Fraction(2, 4);     // 1/2
            Fraction f_3_6 = new Fraction(-3, 6);    // -1/2
            Fraction f_5_n2 = new Fraction(5, -2);   // -5/2
            Fraction f4_2 = new Fraction(4, 2);     // 2/1 -> 2
            Fraction f_6_n3 = new Fraction(-6, -3);  // 2/1 -> 2
            Fraction f0_5 = new Fraction(0, 5);     // 0/1 -> 0
            Fraction f7_1 = new Fraction(7);        // 7/1 -> 7

            System.out.println("f(1, 2)   -> " + f1_2);
            System.out.println("f(2, 4)   -> " + f2_4);
            System.out.println("f(-3, 6)  -> " + f_3_6);
            System.out.println("f(5, -2)  -> " + f_5_n2);
            System.out.println("f(4, 2)   -> " + f4_2);
            System.out.println("f(-6, -3) -> " + f_6_n3);
            System.out.println("f(0, 5)   -> " + f0_5);
            System.out.println("f(7)      -> " + f7_1);
            System.out.println("ZERO      -> " + Fraction.ZERO);
            System.out.println("ONE       -> " + Fraction.ONE);

            // Сравнение
            System.out.println("\n[Equality & Comparison]");
            System.out.println("f(1/2) == f(2/4)? " + f1_2.equals(f2_4)); // true
            System.out.println("f(2/1) == f(2/1)? " + f4_2.equals(f_6_n3)); // true
            System.out.println("f(1/2).compareTo(f(1/3)) -> " + f1_2.compareTo(f1_3)); // > 0
            System.out.println("f(-1/2).compareTo(f(1/2)) -> " + f_3_6.compareTo(f1_2)); // < 0

            // Арифметика
            System.out.println("\n[Arithmetic Operations]");
            System.out.println(f1_2 + " + " + f1_3 + " = " + f1_2.add(f1_3));           // 5/6
            System.out.println(f1_2 + " - " + f1_3 + " = " + f1_2.subtract(f1_3));     // 1/6
            System.out.println(f_5_n2 + " * " + f_3_6 + " = " + f_5_n2.multiply(f_3_6)); // 5/4
            System.out.println(f1_2 + " / " + f1_3 + " = " + f1_2.divide(f1_3));       // 3/2
            System.out.println(f4_2 + " / " + f1_2 + " = " + f4_2.divide(f1_2));       // 4

            // Double value
            System.out.println("\n[Double Value]");
            System.out.println("f(5/6).doubleValue() = " + new Fraction(5, 6).doubleValue()); // ~0.8333

            // Ошибки
            System.out.println("\n[Error Handling]");
            try {
                new Fraction(1, 0);
            } catch (IllegalArgumentException e) {
                System.out.println("Caught expected (div by zero): " + e.getMessage());
            }
            try {
                f1_2.divide(Fraction.ZERO);
            } catch (ArithmeticException e) {
                System.out.println("Caught expected (div by zero fraction): " + e.getMessage());
            }
            // Переполнение
            try {
                new Fraction(Integer.MAX_VALUE).add(Fraction.ONE);
            } catch (ArithmeticException e) {
                System.out.println("Caught expected (overflow): " + e.getMessage());
            }

        } catch (Exception e) { // Ловим остальные неожиданные ошибки
            System.err.println("An unexpected error occurred in main: " + e);
            e.printStackTrace();
        }
    }
}
