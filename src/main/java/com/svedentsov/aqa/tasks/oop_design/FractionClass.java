package com.svedentsov.aqa.tasks.oop_design;

import java.util.Objects;

/**
 * Решение задачи №79: Написать простой класс для работы с дробями.
 * Реализует неизменяемый (immutable) класс Fraction для представления рациональных дробей.
 * Описание: Сложение, вычитание, упрощение. (Проверяет: ООП, арифметика, НОД)
 * Задание: Реализуйте класс `Fraction` с полями числитель (`numerator`)
 * и знаменатель (`denominator`). Добавьте конструктор, метод `toString()`
 * (например, "2/3"), и метод `add(Fraction other)` для сложения дробей
 * с приведением к общему знаменателю и упрощением результата (используя НОД).
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
            long newNumL = Math.addExact(Math.multiplyExact((long) this.numerator, other.denominator), Math.multiplyExact((long) other.numerator, this.denominator));
            long newDenL = Math.multiplyExact((long) this.denominator, other.denominator);
            if (newNumL < Integer.MIN_VALUE || newNumL > Integer.MAX_VALUE || newDenL < Integer.MIN_VALUE || newDenL > Integer.MAX_VALUE) {
                throw new ArithmeticException("Integer overflow during addition.");
            }
            return new Fraction((int) newNumL, (int) newDenL);
        }

        public Fraction subtract(Fraction other) {
            Objects.requireNonNull(other, "Other fraction cannot be null");
            long newNumL = Math.subtractExact(Math.multiplyExact((long) this.numerator, other.denominator), Math.multiplyExact((long) other.numerator, this.denominator));
            long newDenL = Math.multiplyExact((long) this.denominator, other.denominator);
            if (newNumL < Integer.MIN_VALUE || newNumL > Integer.MAX_VALUE || newDenL < Integer.MIN_VALUE || newDenL > Integer.MAX_VALUE) {
                throw new ArithmeticException("Integer overflow during subtraction.");
            }
            return new Fraction((int) newNumL, (int) newDenL);
        }

        public Fraction multiply(Fraction other) {
            Objects.requireNonNull(other, "Other fraction cannot be null");
            long newNumL = Math.multiplyExact((long) this.numerator, other.numerator);
            long newDenL = Math.multiplyExact((long) this.denominator, other.denominator);
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
            long newNumL = Math.multiplyExact((long) this.numerator, other.denominator);
            long newDenL = Math.multiplyExact((long) this.denominator, other.numerator);
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
}
