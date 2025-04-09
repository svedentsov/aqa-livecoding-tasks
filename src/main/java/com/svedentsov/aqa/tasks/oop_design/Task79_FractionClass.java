package com.svedentsov.aqa.tasks.oop_design;

import java.util.Objects;

public class Task79_FractionClass {

    /**
     * Демонстрирует использование класса Fraction.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Fraction f1_2 = new Fraction(1, 2);
        Fraction f1_3 = new Fraction(1, 3);
        Fraction f2_4 = new Fraction(2, 4); // Упростится до 1/2
        Fraction f_3_6 = new Fraction(-3, 6); // Упростится до -1/2
        Fraction f4_2 = new Fraction(4, 2); // Упростится до 2/1

        System.out.println("f(1/2) = " + f1_2);
        System.out.println("f(1/3) = " + f1_3);
        System.out.println("f(2/4) = " + f2_4);
        System.out.println("f(-3/6) = " + f_3_6);
        System.out.println("f(4/2) = " + f4_2); // Выведет "2"

        System.out.println("\nСравнение:");
        System.out.println("f(1/2) equals f(2/4)? " + f1_2.equals(f2_4)); // true
        System.out.println("f(1/2) compareTo f(1/3)? " + f1_2.compareTo(f1_3)); // > 0
        System.out.println("f(-1/2) compareTo f(1/2)? " + f_3_6.compareTo(f1_2)); // < 0

        System.out.println("\nАрифметика:");
        System.out.println("f(1/2) + f(1/3) = " + f1_2.add(f1_3));           // 5/6
        System.out.println("f(1/2) - f(1/3) = " + f1_2.subtract(f1_3));     // 1/6
        System.out.println("f(1/2) * f(1/3) = " + f1_2.multiply(f1_3));     // 1/6
        System.out.println("f(1/2) * f(-1/2) = " + f1_2.multiply(f_3_6));    // -1/4
        System.out.println("f(1/2) / f(1/3) = " + f1_2.divide(f1_3));       // 3/2
        System.out.println("f(1/2) / f(2/1) = " + f1_2.divide(new Fraction(2, 1))); // 1/4

        System.out.println("\nЗначение double:");
        System.out.println("f(1/2).doubleValue() = " + f1_2.doubleValue()); // 0.5
        System.out.println("f(1/3).doubleValue() = " + f1_3.doubleValue()); // 0.333...
        System.out.println("f(5/6).doubleValue() = " + f1_2.add(f1_3).doubleValue()); // 0.8333...


        System.out.println("\nОшибки:");
        try {
            new Fraction(1, 0);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }
        try {
            f1_2.divide(Fraction.ZERO);
        } catch (ArithmeticException e) {
            System.out.println("Caught: " + e.getMessage());
        }
        try {
            // Пример потенциального переполнения int при сложении
            Fraction large1 = new Fraction(Integer.MAX_VALUE / 2, 1);
            Fraction large2 = new Fraction(Integer.MAX_VALUE / 2 + 5, 1); // Сумма числителей > MAX_VALUE
            large1.add(large2);
        } catch (ArithmeticException e) {
            System.out.println("Caught: " + e.getMessage());
        }
    }
}

/**
 * Представляет рациональную дробь вида числитель/знаменатель.
 * Дробь автоматически упрощается при создании. Знак хранится в числителе.
 * Класс является неизменяемым (immutable).
 */
final class Fraction implements Comparable<Fraction> { // Добавим Comparable для сравнения
    private final int numerator;    // Числитель
    private final int denominator;  // Знаменатель (всегда положительный)

    // Статические константы для удобства
    public static final Fraction ZERO = new Fraction(0, 1);
    public static final Fraction ONE = new Fraction(1, 1);

    /**
     * Создает объект Fraction (дробь).
     * Дробь автоматически упрощается с использованием НОД.
     * Знак дроби хранится в числителе, знаменатель всегда положителен.
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
            numerator = -numerator;
            denominator = -denominator;
        }

        // Упрощение дроби с использованием НОД (Наибольший Общий Делитель).
        // Если числитель 0, НОД не нужен, знаменатель ставим 1.
        if (numerator == 0) {
            this.numerator = 0;
            this.denominator = 1;
        } else {
            int commonDivisor = gcd(Math.abs(numerator), denominator);
            this.numerator = numerator / commonDivisor;
            this.denominator = denominator / commonDivisor;
        }
    }

    /**
     * Возвращает числитель дроби.
     */
    public int getNumerator() {
        return numerator;
    }

    /**
     * Возвращает знаменатель дроби (всегда положительный).
     */
    public int getDenominator() {
        return denominator;
    }

    /**
     * Складывает эту дробь с другой дробью.
     * Результат возвращается в виде новой упрощенной дроби.
     * Использует long для промежуточных вычислений во избежание переполнения int.
     *
     * @param other Другая дробь для сложения. Не может быть null.
     * @return Новая дробь, представляющая сумму.
     * @throws ArithmeticException если промежуточные или конечные числитель/знаменатель выходят за пределы int.
     */
    public Fraction add(Fraction other) {
        Objects.requireNonNull(other, "Other fraction cannot be null");

        // Вычисление с использованием long для промежуточных шагов
        // Новый числитель: a*d + b*c
        long newNumL = (long) this.numerator * other.denominator + (long) other.numerator * this.denominator;
        // Новый знаменатель: b*d
        long newDenL = (long) this.denominator * other.denominator;

        // Проверка на переполнение перед созданием новой дроби
        if (newNumL < Integer.MIN_VALUE || newNumL > Integer.MAX_VALUE ||
                newDenL < Integer.MIN_VALUE || newDenL > Integer.MAX_VALUE) {
            throw new ArithmeticException("Integer overflow during addition.");
        }


        // Создание новой дроби (она будет упрощена в конструкторе)
        return new Fraction((int) newNumL, (int) newDenL);
    }

    // --- Можно добавить другие арифметические операции ---

    /**
     * Вычитает другую дробь из этой.
     */
    public Fraction subtract(Fraction other) {
        Objects.requireNonNull(other, "Other fraction cannot be null");
        long newNumL = (long) this.numerator * other.denominator - (long) other.numerator * this.denominator;
        long newDenL = (long) this.denominator * other.denominator;
        if (newNumL < Integer.MIN_VALUE || newNumL > Integer.MAX_VALUE || newDenL < Integer.MIN_VALUE || newDenL > Integer.MAX_VALUE)
            throw new ArithmeticException("Integer overflow during subtraction.");
        return new Fraction((int) newNumL, (int) newDenL);
    }

    /**
     * Умножает эту дробь на другую.
     */
    public Fraction multiply(Fraction other) {
        Objects.requireNonNull(other, "Other fraction cannot be null");
        long newNumL = (long) this.numerator * other.numerator;
        long newDenL = (long) this.denominator * other.denominator;
        if (newNumL < Integer.MIN_VALUE || newNumL > Integer.MAX_VALUE || newDenL < Integer.MIN_VALUE || newDenL > Integer.MAX_VALUE)
            throw new ArithmeticException("Integer overflow during multiplication.");
        return new Fraction((int) newNumL, (int) newDenL);
    }

    /**
     * Делит эту дробь на другую (умножает на обратную).
     */
    public Fraction divide(Fraction other) {
        Objects.requireNonNull(other, "Other fraction cannot be null");
        if (other.numerator == 0) throw new ArithmeticException("Division by zero fraction.");
        // Деление = умножение на обратную дробь (other.denominator / other.numerator)
        long newNumL = (long) this.numerator * other.denominator;
        long newDenL = (long) this.denominator * other.numerator;
        if (newNumL < Integer.MIN_VALUE || newNumL > Integer.MAX_VALUE || newDenL < Integer.MIN_VALUE || newDenL > Integer.MAX_VALUE)
            throw new ArithmeticException("Integer overflow during division.");
        // Конструктор обработает знак и упрощение
        return new Fraction((int) newNumL, (int) newDenL);
    }

    /**
     * Возвращает значение дроби в виде double.
     */
    public double doubleValue() {
        return (double) numerator / denominator;
    }


    /**
     * Возвращает строковое представление дроби в виде "числитель/знаменатель".
     * Если знаменатель равен 1, возвращает только числитель.
     *
     * @return Строковое представление дроби.
     */
    @Override
    public String toString() {
        return denominator == 1 ? Integer.toString(numerator) : numerator + "/" + denominator;
    }

    /**
     * Сравнивает эту дробь с другим объектом.
     * Две дроби равны, если их упрощенные числители и знаменатели совпадают.
     *
     * @param o Объект для сравнения.
     * @return true, если объекты равны, иначе false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fraction fraction = (Fraction) o;
        // Сравнение уже упрощенных дробей
        return numerator == fraction.numerator && denominator == fraction.denominator;
    }

    /**
     * Возвращает хеш-код для объекта Fraction.
     */
    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator);
    }

    /**
     * Сравнивает эту дробь с другой дробью.
     *
     * @param other Другая дробь.
     * @return отрицательное число, ноль или положительное число, если эта дробь
     * меньше, равна или больше другой дроби соответственно.
     */
    @Override
    public int compareTo(Fraction other) {
        Objects.requireNonNull(other, "Cannot compare to a null Fraction");
        // Сравниваем путем приведения к общему знаменателю: a/b ? c/d -> ad ? bc
        long left = (long) this.numerator * other.denominator;
        long right = (long) other.numerator * this.denominator;
        return Long.compare(left, right);
    }

    /**
     * Вспомогательный метод для вычисления НОД (Наибольший Общий Делитель).
     * Используется итеративный алгоритм Евклида.
     *
     * @param a Первое число (должно быть неотрицательным).
     * @param b Второе число (должно быть положительным).
     * @return НОД чисел a и b.
     */
    private static int gcd(int a, int b) {
        // Гарантируем, что a >= 0, b > 0 для алгоритма
        a = Math.abs(a);
        b = Math.abs(b);
        if (b == 0) return a; // НОД(a, 0) = a

        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
