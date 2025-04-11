package com.svedentsov.aqa.tasks.oop_design; // или algorithms

import java.util.Objects;

/**
 * Решение задачи №79: Написать простой класс для работы с дробями.
 * Реализует неизменяемый (immutable) класс Fraction для представления рациональных дробей.
 */
public class Task79_FractionClass {

    /**
     * Представляет рациональную дробь вида числитель/знаменатель.
     * Дробь автоматически упрощается при создании с использованием НОД.
     * Знак дроби хранится только в числителе, знаменатель всегда положителен.
     * Класс является неизменяемым (immutable) и реализует {@link Comparable}.
     */
    public static final class Fraction implements Comparable<Fraction> { // final класс
        private final int numerator;    // Числитель (может быть отрицательным)
        private final int denominator;  // Знаменатель (всегда положительный)

        // Статические константы для часто используемых дробей
        public static final Fraction ZERO = new Fraction(0, 1);
        public static final Fraction ONE = new Fraction(1, 1);
        public static final Fraction HALF = new Fraction(1, 2);

        /**
         * Создает объект Fraction (дробь) из числителя и знаменателя.
         * Выполняет проверку знаменателя на ноль и упрощение дроби.
         *
         * @param numerator   Числитель.
         * @param denominator Знаменатель. Не должен быть равен нулю.
         * @throws IllegalArgumentException если знаменатель равен нулю.
         */
        public Fraction(int numerator, int denominator) {
            if (denominator == 0) {
                throw new IllegalArgumentException("Denominator cannot be zero.");
            }

            // Обработка знака: переносим знак в числитель, знаменатель делаем положительным.
            if (denominator < 0) {
                // Проверка на переполнение при смене знака MIN_VALUE
                if (numerator == Integer.MIN_VALUE || denominator == Integer.MIN_VALUE) {
                    throw new ArithmeticException("Integer overflow changing sign of MIN_VALUE.");
                }
                numerator = -numerator;
                denominator = -denominator;
            }

            // Упрощение дроби при создании
            if (numerator == 0) {
                // Если числитель 0, дробь равна 0/1
                this.numerator = 0;
                this.denominator = 1;
            } else {
                // Находим Наибольший Общий Делитель (НОД) абсолютного значения числителя и знаменателя.
                int commonDivisor = gcd(Math.abs(numerator), denominator);
                // Делим числитель и знаменатель на НОД для упрощения.
                this.numerator = numerator / commonDivisor;
                this.denominator = denominator / commonDivisor;
            }
        }

        /** Возвращает числитель дроби. */
        public int getNumerator() { return numerator; }
        /** Возвращает знаменатель дроби (всегда положительный, кроме случая 0/1). */
        public int getDenominator() { return denominator; }

        /**
         * Складывает эту дробь с другой дробью {@code other}.
         * Результат возвращается в виде новой, упрощенной дроби.
         * Использует {@code long} для промежуточных вычислений во избежание переполнения {@code int}.
         *
         * @param other Другая дробь для сложения (не null).
         * @return Новая дробь, представляющая сумму {@code this + other}.
         * @throws NullPointerException если other равен null.
         * @throws ArithmeticException если промежуточные или конечный результат выходят за пределы {@code int}.
         */
        public Fraction add(Fraction other) {
            Objects.requireNonNull(other, "Other fraction cannot be null");

            // Формула сложения: a/b + c/d = (ad + bc) / bd
            // Вычисляем с использованием long для предотвращения переполнения
            long num1 = this.numerator; long den1 = this.denominator;
            long num2 = other.numerator; long den2 = other.denominator;

            long newNumL = Math.addExact(Math.multiplyExact(num1, den2), Math.multiplyExact(num2, den1));
            long newDenL = Math.multiplyExact(den1, den2);

            // Проверка на выход за пределы int (хотя multiplyExact/addExact уже бросят исключение)
            if (newNumL < Integer.MIN_VALUE || newNumL > Integer.MAX_VALUE ||
                    newDenL < Integer.MIN_VALUE || newDenL > Integer.MAX_VALUE) {
                throw new ArithmeticException("Integer overflow during addition calculation.");
            }

            // Создаем новую дробь (она будет упрощена в конструкторе)
            return new Fraction((int) newNumL, (int) newDenL);
        }

        /**
         * Вычитает другую дробь {@code other} из этой дроби.
         * @param other Другая дробь (не null).
         * @return Новая дробь, представляющая разность {@code this - other}.
         */
        public Fraction subtract(Fraction other) {
            Objects.requireNonNull(other, "Other fraction cannot be null");
            // a/b - c/d = (ad - bc) / bd
            long num1 = this.numerator; long den1 = this.denominator;
            long num2 = other.numerator; long den2 = other.denominator;
            long newNumL = Math.subtractExact(Math.multiplyExact(num1, den2), Math.multiplyExact(num2, den1));
            long newDenL = Math.multiplyExact(den1, den2);
            if (newNumL < Integer.MIN_VALUE || newNumL > Integer.MAX_VALUE || newDenL < Integer.MIN_VALUE || newDenL > Integer.MAX_VALUE) throw new ArithmeticException("Integer overflow during subtraction.");
            return new Fraction((int) newNumL, (int) newDenL);
        }

        /**
         * Умножает эту дробь на другую дробь {@code other}.
         * @param other Другая дробь (не null).
         * @return Новая дробь, представляющая произведение {@code this * other}.
         */
        public Fraction multiply(Fraction other) {
            Objects.requireNonNull(other, "Other fraction cannot be null");
            // a/b * c/d = ac / bd
            long num1 = this.numerator; long den1 = this.denominator;
            long num2 = other.numerator; long den2 = other.denominator;
            long newNumL = Math.multiplyExact(num1, num2);
            long newDenL = Math.multiplyExact(den1, den2);
            if (newNumL < Integer.MIN_VALUE || newNumL > Integer.MAX_VALUE || newDenL < Integer.MIN_VALUE || newDenL > Integer.MAX_VALUE) throw new ArithmeticException("Integer overflow during multiplication.");
            return new Fraction((int) newNumL, (int) newDenL);
        }

        /**
         * Делит эту дробь на другую дробь {@code other}.
         * @param other Другая дробь (не null, числитель не 0).
         * @return Новая дробь, представляющая частное {@code this / other}.
         * @throws ArithmeticException если {@code other} равен нулю (деление на ноль).
         */
        public Fraction divide(Fraction other) {
            Objects.requireNonNull(other, "Other fraction cannot be null");
            if (other.numerator == 0) {
                throw new ArithmeticException("Division by zero fraction.");
            }
            // a/b / c/d = a/b * d/c = ad / bc
            long num1 = this.numerator; long den1 = this.denominator;
            long num2 = other.numerator; long den2 = other.denominator; // Знаменатель 'other' при делении станет числителем
            long newNumL = Math.multiplyExact(num1, den2);
            long newDenL = Math.multiplyExact(den1, num2); // Знаменатель 'other' при делении станет числителем
            if (newNumL < Integer.MIN_VALUE || newNumL > Integer.MAX_VALUE || newDenL < Integer.MIN_VALUE || newDenL > Integer.MAX_VALUE) throw new ArithmeticException("Integer overflow during division.");
            // Конструктор обработает знак (если newDenL стал отрицательным) и упрощение.
            return new Fraction((int) newNumL, (int) newDenL);
        }

        /** Возвращает значение дроби в виде {@code double}. Может терять точность. */
        public double doubleValue() {
            return (double) numerator / denominator;
        }


        /**
         * Возвращает строковое представление дроби в виде "числитель/знаменатель".
         * Если знаменатель равен 1 (т.е. дробь целая), возвращает только числитель.
         * @return Строковое представление дроби.
         */
        @Override
        public String toString() {
            return denominator == 1 ? Integer.toString(numerator) : numerator + "/" + denominator;
        }

        /**
         * Сравнивает эту дробь с другим объектом.
         * Две дроби равны, если их упрощенные числители и знаменатели совпадают.
         * @param o Объект для сравнения.
         * @return true, если объекты равны, иначе false.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Fraction fraction = (Fraction) o;
            // Сравниваем уже упрощенные дроби (числитель и знаменатель)
            return numerator == fraction.numerator && denominator == fraction.denominator;
        }

        /** Возвращает хеш-код для объекта Fraction, основанный на числителе и знаменателе. */
        @Override
        public int hashCode() {
            return Objects.hash(numerator, denominator);
        }

        /**
         * Сравнивает эту дробь с другой дробью для определения порядка.
         * Сравнение происходит путем приведения дробей к общему знаменателю:
         * a/b сравнивается с c/d путем сравнения a*d и b*c.
         * Используется {@code long} для промежуточных произведений.
         *
         * @param other Другая дробь для сравнения (не null).
         * @return отрицательное число, ноль или положительное число, если эта дробь
         * меньше, равна или больше другой дроби соответственно.
         * @throws NullPointerException если other равен null.
         */
        @Override
        public int compareTo(Fraction other) {
            Objects.requireNonNull(other, "Cannot compare to a null Fraction");
            // Сравниваем a/b и c/d через сравнение ad и bc
            long leftProduct = (long) this.numerator * other.denominator;
            long rightProduct = (long) other.numerator * this.denominator;
            return Long.compare(leftProduct, rightProduct);
        }

        /**
         * Вспомогательный статический метод для вычисления НОД (Наибольший Общий Делитель)
         * двух неотрицательных целых чисел с использованием итеративного алгоритма Евклида.
         * @param a Первое неотрицательное число.
         * @param b Второе неотрицательное число.
         * @return НОД чисел a и b.
         */
        private static int gcd(int a, int b) {
            // Алгоритм Евклида работает и для отрицательных, но мы уже обработали знак
            // a = Math.abs(a); // В конструкторе уже сделано
            // b = Math.abs(b);
            if (b == 0) return a; // НОД(a, 0) = a
            while (b != 0) {
                int temp = b;
                b = a % b;
                a = temp;
            }
            return a; // Возвращаем последний ненулевой остаток
        }
    }

    /**
     * Точка входа для демонстрации использования класса Fraction.
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        System.out.println("--- Fraction Class Demo ---");
        try {
            Fraction f1_2 = new Fraction(1, 2);
            Fraction f1_3 = new Fraction(1, 3);
            Fraction f2_4 = new Fraction(2, 4); // Упростится до 1/2
            Fraction f_3_6 = new Fraction(-3, 6); // Упростится до -1/2
            Fraction f_5_n2 = new Fraction(5, -2); // Упростится до -5/2
            Fraction f4_2 = new Fraction(4, 2); // Упростится до 2/1
            Fraction f_6_n3 = new Fraction(-6, -3); // Упростится до 2/1

            System.out.println("f(1/2) = " + f1_2);
            System.out.println("f(1/3) = " + f1_3);
            System.out.println("f(2/4) = " + f2_4);
            System.out.println("f(-3/6) = " + f_3_6);
            System.out.println("f(5/-2) = " + f_5_n2);
            System.out.println("f(4/2) = " + f4_2);     // Выведет "2"
            System.out.println("f(-6/-3) = " + f_6_n3);   // Выведет "2"
            System.out.println("ZERO = " + Fraction.ZERO); // Выведет "0"

            System.out.println("\nСравнение:");
            System.out.println("f(1/2) equals f(2/4)? " + f1_2.equals(f2_4)); // true
            System.out.println("f(4/2) equals f(-6/-3)? " + f4_2.equals(f_6_n3)); // true
            System.out.println("f(1/2) equals f(1/3)? " + f1_2.equals(f1_3)); // false
            System.out.println("f(1/2).compareTo(f(1/3)) = " + f1_2.compareTo(f1_3)); // > 0
            System.out.println("f(-1/2).compareTo(f(1/2)) = " + f_3_6.compareTo(f1_2)); // < 0
            System.out.println("f(2/1).compareTo(f(2/1)) = " + f4_2.compareTo(f_6_n3)); // = 0

            System.out.println("\nАрифметика:");
            System.out.println("f(1/2) + f(1/3) = " + f1_2.add(f1_3));           // 5/6
            System.out.println("f(1/2) - f(1/3) = " + f1_2.subtract(f1_3));     // 1/6
            System.out.println("f(1/3) - f(1/2) = " + f1_3.subtract(f1_2));     // -1/6
            System.out.println("f(1/2) * f(1/3) = " + f1_2.multiply(f1_3));     // 1/6
            System.out.println("f(1/2) * f(-1/2) = " + f1_2.multiply(f_3_6));   // -1/4
            System.out.println("f(-1/2) * f(-5/2) = " + f_3_6.multiply(f_5_n2)); // 5/4
            System.out.println("f(1/2) / f(1/3) = " + f1_2.divide(f1_3));       // 3/2
            System.out.println("f(5/6) / f(1/6) = " + f1_2.add(f1_3).divide(f1_2.subtract(f1_3))); // 5/1 -> 5
            System.out.println("f(2/1) / f(1/2) = " + f4_2.divide(f1_2));       // 4/1 -> 4

            System.out.println("\nЗначение double:");
            System.out.println("f(1/2).doubleValue() = " + f1_2.doubleValue()); // 0.5
            System.out.println("f(1/3).doubleValue() = " + f1_3.doubleValue()); // 0.333...
            System.out.println("f(5/6).doubleValue() = " + f1_2.add(f1_3).doubleValue()); // 0.8333...
            System.out.println("f(-5/2).doubleValue() = " + f_5_n2.doubleValue()); // -2.5

            System.out.println("\nОшибки:");
            try { new Fraction(1, 0); }
            catch (IllegalArgumentException e) { System.out.println("Caught expected: " + e.getMessage()); }
            try { f1_2.divide(Fraction.ZERO); }
            catch (ArithmeticException e) { System.out.println("Caught expected: " + e.getMessage()); }

        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e);
            e.printStackTrace();
        }
    }
}
