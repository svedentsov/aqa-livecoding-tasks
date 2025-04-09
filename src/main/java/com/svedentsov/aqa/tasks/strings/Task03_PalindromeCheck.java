package com.svedentsov.aqa.tasks.strings;

/**
 * Решение задачи №3: Проверка на палиндром.
 */
public class Task03_PalindromeCheck {

    /**
     * Проверяет, является ли строка палиндромом.
     * Игнорируются регистр и все символы, кроме букв и цифр.
     *
     * @param str Входная строка для проверки. Может быть null.
     * @return {@code true}, если строка является палиндромом, {@code false} в противном случае.
     * Пустая строка или строка только из не буквенно-цифровых символов считается палиндромом.
     * Null считается не палиндромом.
     */
    public boolean isPalindrome(String str) {
        if (str == null) {
            return false;
        }
        // 1. Очистка строки: убираем не буквенно-цифровые символы и приводим к нижнему регистру
        StringBuilder cleaned = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                cleaned.append(Character.toLowerCase(c));
            }
        }
        String cleanedStr = cleaned.toString();

        // 2. Проверка палиндрома для очищенной строки
        int left = 0;
        int right = cleanedStr.length() - 1;
        while (left < right) {
            if (cleanedStr.charAt(left) != cleanedStr.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    /**
     * Точка входа для демонстрации работы метода isPalindrome.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task03_PalindromeCheck sol = new Task03_PalindromeCheck();
        System.out.println("'A man, a plan, a canal: Panama' is palindrome: " + sol.isPalindrome("A man, a plan, a canal: Panama")); // true
        System.out.println("'race a car' is palindrome: " + sol.isPalindrome("race a car")); // false
        System.out.println("' ' is palindrome: " + sol.isPalindrome(" ")); // true
        System.out.println("',.' is palindrome: " + sol.isPalindrome(",.")); // true
        System.out.println("null is palindrome: " + sol.isPalindrome(null)); // false
        System.out.println("'level' is palindrome: " + sol.isPalindrome("level")); // true
    }
}
