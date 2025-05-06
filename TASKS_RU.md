# Задания по программированию для AQA

<a name="table-of-contents"></a>

### Основы основ (встречаются очень часто)

* [FizzBuzz](#task-1)
* [Перевернуть строку (Reverse String)](#task-2)
* [Проверка на палиндром (Palindrome Check)](#task-3)
* [Найти дубликаты в массиве/списке (Find Duplicates in Array/List)](#task-4)
* [Посчитать количество вхождений символа в строке (Count Character Occurrences)](#task-5)
* [Найти максимальный/минимальный элемент в массиве/списке (Find Max/Min in Array/List)](#task-6)
* [Удалить дубликаты из списка (Remove Duplicates from List)](#task-7)
* [Проверка на анаграммы (Anagram Check)](#task-8)
* [Сумма элементов массива/списка (Sum Array/List Elements)](#task-9)
* [Найти первый неповторяющийся символ в строке (Find First Non-Repeating Character)](#task-10)

### Стандартные задачи (встречаются часто)

* [Факториал числа (Factorial)](#task-11)
* [Числа Фибоначчи (Fibonacci Sequence)](#task-12)
* [Проверка на простое число (Prime Number Check)](#task-13)
* [Поменять местами две переменные без использования третьей (Swap Variables without Temp)](#task-14)
* [Найти отсутствующее число в последовательности (Find Missing Number)](#task-15)
* [Сортировка массива/списка (Sort Array/List - Bubble Sort)](#task-16)
* [Объединить два отсортированных массива/списка (Merge Sorted Arrays/Lists)](#task-17)
* [Посчитать количество слов в строке (Count Words in String)](#task-18)
* [Найти второй по величине элемент в массиве/списке (Find Second Largest Element)](#task-19)
* [Проверить, содержит ли строка только цифры (Check if String Contains Only Digits)](#task-20)

### Чуть сложнее или специфичнее (средняя частота)

* [Перевернуть порядок слов в предложении (Reverse Words in Sentence)](#task-21)
* [Найти пересечение двух массивов/списков (Find Intersection of Two Arrays/Lists)](#task-22)
* [Проверка сбалансированности скобок (Balanced Parentheses Check)](#task-23)
* [Реализовать простой класс (Simple Class Implementation)](#task-24)
* [Найти пару чисел в массиве, сумма которых равна заданному значению (Two Sum Problem)](#task-25)
* [Группировка элементов списка по какому-либо признаку (Group List Elements)](#task-26)
* [Сгенерировать все простые числа до N (Generate Primes up to N)](#task-27)
* [Проверить, является ли число степенью двойки (Check Power of Two)](#task-28)
* [Сумма цифр числа (Sum of Digits)](#task-29)
* [Удалить определенный элемент из массива/списка (Remove Specific Element)](#task-30)
* [Сжатие строки (String Compression)](#task-31)
* [Найти самый часто встречающийся элемент в массиве/списке (Find Most Frequent Element)](#task-32)
* [Прочитать файл и посчитать строки/слова (Read File and Count Lines/Words)](#task-33)
* [Проверка на число Армстронга (Armstrong Number Check)](#task-34)
* [Реализовать базовый Стек или Очередь (Implement Basic Stack/Queue)](#task-35)
* [Найти наибольший общий делитель (НОД / GCD)](#task-36)
* [Найти наименьшее общее кратное (НОК / LCM)](#task-37)
* [Бинарный поиск в отсортированном массиве (Binary Search)](#task-38)
* [Повернуть массив на K элементов (Rotate Array)](#task-39)
* [Сравнить два объекта кастомного класса (Compare Custom Objects)](#task-40)

### Более специализированные или сложные (реже)

* [Валидация IP-адреса (Validate IP Address)](#task-41)
* [Конвертация римских чисел в целые (Roman to Integer)](#task-42)
* [Конвертация целых чисел в римские (Integer to Roman)](#task-43)
* [Найти длину самой длинной подстроки без повторяющихся символов (Longest Substring Without Repeating Characters)](#task-44)
* [Переместить все нули в конец массива (Move Zeroes to End)](#task-45)
* [Найти все перестановки строки (Find All Permutations of a String)](#task-46)
* [Максимальная сумма подмассива (Maximum Subarray Sum - Kadane's Algorithm)](#task-47)
* [Найти K-ый по величине элемент в массиве (Find Kth Largest Element)](#task-48)
* [Проверка на идеальное число (Perfect Number Check)](#task-49)
* [Написать простой парсер CSV строки (Parse Simple CSV Line)](#task-50)
* [Реализовать `equals()` и `hashCode()` для кастомного класса](#task-51)
* [Найти общий префикс для массива строк (Longest Common Prefix)](#task-52)
* [Проверка на подпоследовательность (Is Subsequence)](#task-53)
* [Создание простого Singleton (Implement Singleton)](#task-54)
* [Подсчет островов (Number of Islands - упрощенно)](#task-55)
* [Реализовать `toString()` для сложного объекта](#task-56)
* [Сгенерировать случайную строку заданной длины (Generate Random String)](#task-57)
* [Базовая валидация формата Email (Basic Email Format Validation)](#task-58)
* [Найти индекс первого вхождения подстроки (Find Substring Index)](#task-59)
* [Перемножение матриц (Matrix Multiplication - для 2x2 или 3x3)](#task-60)

### Сложные или нетипичные для AQA live coding (очень редко)

* [Реализовать LRU Cache (Least Recently Used Cache)](#task-61)
* [Найти медиану двух отсортированных массивов (Median of Two Sorted Arrays)](#task-62)
* [Найти все комбинации элементов списка, сумма которых равна X (Combination Sum)](#task-63)
* [Редакторское расстояние (Edit Distance / Levenshtein Distance)](#task-64)
* [Задача о рюкзаке (Knapsack Problem - упрощенная версия 0/1)](#task-65)
* [Сортировка слиянием (Merge Sort)](#task-66)
* [Быстрая сортировка (Quick Sort)](#task-67)
* [Построить бинарное дерево поиска (Build Binary Search Tree)](#task-68)
* [Обход бинарного дерева (Tree Traversal - Inorder, Preorder, Postorder)](#task-69)
* [Сериализация/Десериализация простого объекта в JSON (Concept)](#task-70)
* [Найти цикл в связанном списке (Detect Cycle in Linked List)](#task-71)
* [Перевернуть связанный список (Reverse Linked List)](#task-72)
* [Реализовать Rate Limiter (концептуально)](#task-73)
* [Простая реализация Pub/Sub (концептуально)](#task-74)
* [Найти K ближайших точек к началу координат (K Closest Points to Origin)](#task-75)
* [Генерация уникальных ID (Generate Unique IDs)](#task-76)
* [Конвертация Base64 (Encode/Decode)](#task-77)
* [Работа с датами и временем (Date/Time Manipulation)](#task-78)
* [Написать простой класс для работы с дробями (Fraction Class)](#task-79)
* [Регулярное выражение для поиска/валидации (Simple Regex)](#task-80)
* [Поиск слов в матрице символов (Word Search)](#task-81)
* [Счастливый билет (Lucky Ticket)](#task-82)
* [Морской бой (Battleship - проверка попадания)](#task-83)
* [Проверка валидности судоку (Validate Sudoku - часть)](#task-84)
* [Реализация `Optional` (концептуально)](#task-85)
* [Работа с потоками (Java Streams - простое использование)](#task-86)
* [Создать Immutable класс (Create Immutable Class)](#task-87)
* [Глубокое копирование объекта (Deep Copy vs Shallow Copy)](#task-88)
* [Реализовать простой кеш в памяти (Simple In-Memory Cache)](#task-89)
* [Игра "Жизнь" (Game of Life - один шаг)](#task-90)
* [Подсчет дождевой воды (Trapping Rain Water - упрощенно)](#task-91)
* [Найти медиану потока чисел (Find Median from Data Stream - концептуально)](#task-92)
* [Проверить, является ли дерево бинарным деревом поиска (Validate BST)](#task-93)
* [Уровень узла в бинарном дереве (Level of a Node in Binary Tree)](#task-94)
* [Наименьший общий предок в BST/Binary Tree (Lowest Common Ancestor)](#task-95)
* [Диаметр бинарного дерева (Diameter of Binary Tree)](#task-96)
* [Проверка на симметричность дерева (Symmetric Tree Check)](#task-97)
* [Поиск пути между двумя узлами графа (Find Path in Graph - BFS/DFS)](#task-98)
* [Заливка (Flood Fill)](#task-99)
* [Вычисление математического выражения в строке (Evaluate Expression String - простое)](#task-100)

---

## Задания

<a name="task-1"></a>

### FizzBuzz

* **Описание:** Написать программу, которая выводит числа от 1 до N. Но для чисел, кратных 3, вывести "Fizz", для чисел,
  кратных 5 - "Buzz", а для чисел, кратных и 3, и 5 - "FizzBuzz". (Проверяет: циклы, условия)
* **Задание:** Напишите метод, например `generateFizzBuzz(int n)`, который принимает целое число n и возвращает
  последовательность FizzBuzz от 1 до n в виде списка строк (List<String>).
* **Пример:** Вызов `generateFizzBuzz(15)` должен вернуть список строк, эквивалентный: `List.of("1", "2", "Fizz", "4", "
  Buzz", "Fizz", "7", "8", "Fizz", "Buzz", "11", "Fizz", "13", "14", "FizzBuzz")`
* **Решение:**
  [`FizzBuzz.java`](src/main/java/com/svedentsov/aqa/tasks/algorithms/FizzBuzz.java),
  [`FizzBuzzTest.java`](src/test/java/com/svedentsov/aqa/tasks/algorithms/FizzBuzzTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-2"></a>

### Перевернуть строку (Reverse String)

* **Описание:** Написать функцию, которая принимает строку и возвращает её в перевернутом виде. (Проверяет: работа со
  строками, циклы/рекурсия/StringBuilder)
* **Задание:** Напишите метод `String reverseString(String str)`, который принимает строку и возвращает новую
  строку, являющуюся перевернутой версией исходной.
* **Пример:** `reverseString("hello")` -> `"olleh"`, `reverseString("Java")` -> `"avaJ"`.
* **Решение:**
  [`ReverseString.java`](src/main/java/com/svedentsov/aqa/tasks/strings/ReverseString.java),
  [`ReverseStringTest.java`](src/test/java/com/svedentsov/aqa/tasks/strings/ReverseStringTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-3"></a>

### Проверка на палиндром (Palindrome Check)

* **Описание:** Написать функцию, которая проверяет, является ли строка палиндромом (читается одинаково слева направо и
  справа налево), игнорируя регистр и не буквенно-цифровые символы. (Проверяет: работа со строками, циклы, условия)
* **Задание:** Напишите метод `boolean isPalindrome(String str)`, который возвращает `true`, если строка `str`
  является палиндромом (игнорируя регистр и не буквенно-цифровые символы), и `false` в противном случае.
* **Пример:** `isPalindrome("A man, a plan, a canal: Panama")` -> `true`, `isPalindrome("race a car")` -> `false`,
  `isPalindrome(" ")` -> `true`.
* **Решение:**
  [`PalindromeCheck.java`](src/main/java/com/svedentsov/aqa/tasks/strings/PalindromeCheck.java),
  [`PalindromeCheckTest.java`](src/test/java/com/svedentsov/aqa/tasks/strings/PalindromeCheckTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-4"></a>

### Найти дубликаты в массиве/списке (Find Duplicates in Array/List)

* **Описание:** Написать функцию, которая находит и возвращает дублирующиеся элементы в массиве или списке целых
  чисел/строк. (Проверяет: работа с коллекциями, Set/Map, циклы)
* **Задание:** Напишите метод `List<Integer> findDuplicates(List<Integer> numbers)`, который принимает список целых
  чисел и возвращает список уникальных чисел, которые встречаются в исходном списке более одного раза.
* **Пример:** `findDuplicates(List.of(1, 2, 3, 2, 4, 5, 1, 5))` -> `[1, 2, 5]` (порядок не важен, можно вернуть
  `Set`).
* **Решение:**
  [`FindDuplicatesList.java`](src/main/java/com/svedentsov/aqa/tasks/arrays_lists/FindDuplicatesList.java),
  [`FindDuplicatesListTest.java`](src/test/java/com/svedentsov/aqa/tasks/arrays_lists/FindDuplicatesListTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-5"></a>

### Посчитать количество вхождений символа в строке (Count Character Occurrences)

* **Описание:** Написать функцию, которая подсчитывает, сколько раз каждый символ встречается в строке. (Проверяет:
  работа со строками, Map, циклы)
* **Задание:** Напишите метод `Map<Character, Integer> countCharacters(String str)`, который возвращает Map, где
  ключами являются символы из строки `str`, а значениями — количество их вхождений.
* **Пример:** `countCharacters("hello world")` ->
  `{'h': 1, 'e': 1, 'l': 3, 'o': 2, ' ': 1, 'w': 1, 'r': 1, 'd': 1}`.
* **Решение:**
  [`CountCharOccurrences.java`](src/main/java/com/svedentsov/aqa/tasks/maps_sets/CountCharOccurrences.java),
  [`CountCharOccurrencesTest.java`](src/test/java/com/svedentsov/aqa/tasks/maps_sets/CountCharOccurrencesTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-6"></a>

### Найти максимальный/минимальный элемент в массиве/списке (Find Max/Min in Array/List)

* **Описание:** Написать функцию для поиска наибольшего или наименьшего числа в массиве/списке без использования
  встроенных функций `max`/`min` коллекций (иногда просят). (Проверяет: циклы, сравнения)
* **Задание:** Напишите метод `int findMax(int[] numbers)`, который находит и возвращает максимальное значение в
  массиве `numbers` без использования `Collections.max()` или `Stream.max()`. Обработайте случай пустого массива (
  например, выбросив исключение или вернув `Integer.MIN_VALUE`).
* **Пример:** `findMax(new int[]{1, 5, 2, 9, 3})` -> `9`. `findMax(new int[]{-1, -5, -2})` -> `-1`.
* **Решение:**
  [`FindMaxMinArray.java`](src/main/java/com/svedentsov/aqa/tasks/arrays_lists/FindMaxMinArray.java),
  [`FindMaxMinArrayTest.java`](src/test/java/com/svedentsov/aqa/tasks/arrays_lists/FindMaxMinArrayTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-7"></a>

### Удалить дубликаты из списка (Remove Duplicates from List)

* **Описание:** Написать функцию, которая принимает список и возвращает новый список без дубликатов. (Проверяет: работа
  с коллекциями, Set, циклы)
* **Задание:** Напишите метод `List<String> removeDuplicates(List<String> list)`, который принимает список строк и
  возвращает новый список, содержащий только уникальные строки из исходного списка, сохраняя порядок первого
  вхождения.
* **Пример:** `removeDuplicates(List.of("a", "b", "a", "c", "b"))` -> `["a", "b", "c"]`.
* **Решение:**
  [`RemoveDuplicatesList.java`](src/main/java/com/svedentsov/aqa/tasks/arrays_lists/RemoveDuplicatesList.java),
  [`RemoveDuplicatesListTest.java`](src/test/java/com/svedentsov/aqa/tasks/arrays_lists/RemoveDuplicatesListTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-8"></a>

### Проверка на анаграммы (Anagram Check)

* **Описание:** Написать функцию, которая проверяет, являются ли две строки анаграммами друг друга (состоят из одних и
  тех же символов в разном порядке). (Проверяет: работа со строками, сортировка/Map, сравнение)
* **Задание:** Напишите метод `boolean areAnagrams(String str1, String str2)`, который возвращает `true`, если
  строки `str1` и `str2` являются анаграммами (игнорируя регистр и пробелы), и `false` иначе.
* **Пример:** `areAnagrams("listen", "silent")` -> `true`, `areAnagrams("Dormitory", "dirty room")` -> `true`,
  `areAnagrams("hello", "world")` -> `false`.
* **Решение:**
  [`AnagramCheck.java`](src/main/java/com/svedentsov/aqa/tasks/strings/AnagramCheck.java),
  [`AnagramCheckTest.java`](src/test/java/com/svedentsov/aqa/tasks/strings/AnagramCheckTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-9"></a>

### Сумма элементов массива/списка (Sum Array/List Elements)

* **Описание:** Написать функцию для вычисления суммы всех числовых элементов в массиве/списке. (Проверяет: циклы,
  арифметика)
* **Задание:** Напишите метод `long sumElements(int[] numbers)`, который вычисляет и возвращает сумму всех элементов
  в массиве `numbers`.
* **Пример:** `sumElements(new int[]{1, 2, 3, 4, 5})` -> `15`. `sumElements(new int[]{})` -> `0`.
* **Решение:**
  [`SumArrayElements.java`](src/main/java/com/svedentsov/aqa/tasks/arrays_lists/SumArrayElements.java),
  [`SumArrayElementsTest.java`](src/test/java/com/svedentsov/aqa/tasks/arrays_lists/SumArrayElementsTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-10"></a>

### Найти первый неповторяющийся символ в строке (Find First Non-Repeating Character)

* **Описание:** Написать функцию, которая находит первый символ в строке, который встречается только один раз. (
  Проверяет: работа со строками, Map/массивы для счетчиков, циклы)
* **Задание:** Напишите метод `Character findFirstNonRepeatingChar(String str)`, который находит и возвращает первый
  символ в строке `str`, который встречается только один раз. Если такого символа нет, верните null или выбросите
  исключение.
* **Пример:** `findFirstNonRepeatingChar("swiss")` -> `'w'`, `findFirstNonRepeatingChar("aabbcc")` -> `null`.
* **Решение:**
  [`FindFirstNonRepeatingChar.java`](src/main/java/com/svedentsov/aqa/tasks/maps_sets/FindFirstNonRepeatingChar.java),
  [`FindFirstNonRepeatingCharTest.java`](src/test/java/com/svedentsov/aqa/tasks/maps_sets/FindFirstNonRepeatingCharTest.java)

[К оглавлению](#table-of-contents)

---
<a name="task-11"></a>

### Факториал числа (Factorial)

* **Описание:** Написать функцию для вычисления факториала числа (итеративно или рекурсивно).
  (Проверяет: циклы/рекурсия, арифметика)
* **Задание:** Напишите метод `long factorial(int n)`, который вычисляет факториал неотрицательного целого числа
  `n`. Реализуйте итеративно.
* **Пример:** `factorial(5)` -> `120`, `factorial(0)` -> `1`.
* **Решение:**
  [`Factorial.java`](src/main/java/com/svedentsov/aqa/tasks/algorithms/Factorial.java),
  [`FactorialTest.java`](src/test/java/com/svedentsov/aqa/tasks/algorithms/FactorialTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-12"></a>

### Числа Фибоначчи (Fibonacci Sequence)

* **Описание:** Написать функцию для генерации N-го числа Фибоначчи или последовательности до N-го числа (итеративно или
  рекурсивно). (Проверяет: циклы/рекурсия, арифметика)
* **Задание:** Напишите метод `int fibonacci(int n)`, который возвращает `n`-ое число Фибоначчи (последовательность
  начинается с 0, 1, 1, 2...). Реализуйте итеративно.
* **Пример:** `fibonacci(0)` -> `0`, `fibonacci(1)` -> `1`, `fibonacci(6)` -> `8`.
* **Решение:**
  [`FibonacciSequence.java`](src/main/java/com/svedentsov/aqa/tasks/algorithms/FibonacciSequence.java),
  [`FibonacciSequenceTest.java`](src/test/java/com/svedentsov/aqa/tasks/algorithms/FibonacciSequenceTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-13"></a>

### Проверка на простое число (Prime Number Check)

* **Описание:** Написать функцию, которая определяет, является ли данное число простым. (Проверяет: циклы, условия,
  оптимизация)
* **Задание:** Напишите метод `boolean isPrime(int number)`, который возвращает `true`, если `number` (положительное
  целое число > 1) является простым, и `false` иначе.
* **Пример:** `isPrime(7)` -> `true`, `isPrime(10)` -> `false`, `isPrime(2)` -> `true`.
* **Решение:**
  [`PrimeNumberCheck.java`](src/main/java/com/svedentsov/aqa/tasks/algorithms/PrimeNumberCheck.java),
  [`PrimeNumberCheckTest.java`](src/test/java/com/svedentsov/aqa/tasks/algorithms/PrimeNumberCheckTest.java)

[К оглавлению](#table-of-contents)

---
<a name="task-14"></a>

### Поменять местами две переменные без использования третьей (Swap Variables without Temp)

* **Описание:** Написать код для обмена значениями двух числовых переменных. (Проверяет: арифметика/битовые операции)
* **Задание:** Дан код: `int a = 5; int b = 10;`. Напишите код, который меняет значения `a` и `b` местами, не
  используя дополнительную переменную.
* **Пример:** После выполнения кода `a` должно стать `10`, а `b` должно стать `5`.
* **Решение:**
  [`SwapVariables.java`](src/main/java/com/svedentsov/aqa/tasks/oop_design/SwapVariables.java),
  [`SwapVariablesTest.java`](src/test/java/com/svedentsov/aqa/tasks/oop_design/SwapVariablesTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-15"></a>

### Найти отсутствующее число в последовательности (Find Missing Number)

* **Описание:** Дан массив/список чисел от 1 до N с одним пропущенным числом. Найти это число. (Проверяет: логика,
  арифметика/Set)
* **Задание:** Напишите метод `int findMissingNumber(int[] numbers, int n)`, который принимает массив `numbers`,
  содержащий уникальные числа от 1 до `n` (включительно), кроме одного отсутствующего. Метод должен вернуть это
  отсутствующее число.
* **Пример:** `findMissingNumber(new int[]{1, 2, 4, 5}, 5)` -> `3`.
* **Решение:**
  [`FindMissingNumber.java`](src/main/java/com/svedentsov/aqa/tasks/arrays_lists/FindMissingNumber.java),
  [`FindMissingNumberTest.java`](src/test/java/com/svedentsov/aqa/tasks/arrays_lists/FindMissingNumberTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-16"></a>

### Сортировка массива/списка (Sort Array/List - Bubble Sort)

* **Описание:** Реализовать простой алгоритм сортировки (пузырьком, выбором) или использовать `Collections.sort()` /
  `Arrays.sort()` и объяснить, как он работает. (Проверяет: основы алгоритмов, работа с коллекциями)
* **Задание:** Реализуйте метод `void bubbleSort(int[] arr)`, который сортирует массив `arr` по возрастанию,
  используя алгоритм пузырьковой сортировки.
* **Пример:** Исходный массив `[5, 1, 4, 2, 8]` после вызова `bubbleSort` должен стать `[1, 2, 4, 5, 8]`.
* **Решение:**
  [`BubbleSort.java`](src/main/java/com/svedentsov/aqa/tasks/sorting_searching/BubbleSort.java),
  [`BubbleSortTest.java`](src/test/java/com/svedentsov/aqa/tasks/sorting_searching/BubbleSortTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-17"></a>

### Объединить два отсортированных массива/списка (Merge Sorted Arrays/Lists)

* **Описание:** Написать функцию, которая принимает два отсортированных списка/массива и возвращает один объединенный
  отсортированный список/массив. (Проверяет: циклы, сравнения, работа с индексами)
* **Задание:** Напишите метод `List<Integer> mergeSortedLists(List<Integer> list1, List<Integer> list2)`, который
  принимает два отсортированных по возрастанию списка целых чисел и возвращает новый отсортированный список,
  содержащий все элементы из обоих исходных списков.
* **Пример:** `mergeSortedLists(List.of(1, 3, 5), List.of(2, 4, 6))` -> `[1, 2, 3, 4, 5, 6]`.
* **Решение:**
  [`MergeSortedLists.java`](src/main/java/com/svedentsov/aqa/tasks/arrays_lists/MergeSortedLists.java),
  [`MergeSortedListsTest.java`](src/test/java/com/svedentsov/aqa/tasks/arrays_lists/MergeSortedListsTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-18"></a>

### Посчитать количество слов в строке (Count Words in String)

* **Описание:** Написать функцию, которая подсчитывает количество слов в предложении. (Проверяет: работа со строками,
  `split()`)
* **Задание:** Напишите метод `int countWords(String sentence)`, который подсчитывает количество слов в строке
  `sentence`. Слова разделены одним или несколькими пробелами.
* **Пример:** `countWords("This is a sample sentence.")` -> `5`, `countWords(" Hello   world ")` -> `2`.
* **Решение:**
  [`CountWordsString.java`](src/main/java/com/svedentsov/aqa/tasks/strings/CountWordsString.java),
  [`CountWordsStringTest.java`](src/test/java/com/svedentsov/aqa/tasks/strings/CountWordsStringTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-19"></a>

### Найти второй по величине элемент в массиве/списке (Find Second Largest Element)

* **Описание:** Написать функцию для поиска второго максимального элемента. (Проверяет: циклы, сравнения, обработка
  крайних случаев)
* **Задание:** Напишите метод `int findSecondLargest(int[] numbers)`, который находит и возвращает второе по
  величине число в массиве `numbers`. Если такого элемента нет (например, все элементы одинаковы или массив слишком
  мал), верните `Integer.MIN_VALUE` или выбросите исключение.
* **Пример:** `findSecondLargest(new int[]{1, 5, 2, 9, 3, 9})` -> `5`. `findSecondLargest(new int[]{3, 3, 3})` ->
  `Integer.MIN_VALUE`.
* **Решение:**
  [`FindSecondLargest.java`](src/main/java/com/svedentsov/aqa/tasks/arrays_lists/FindSecondLargest.java),
  [`FindSecondLargestTest.java`](src/test/java/com/svedentsov/aqa/tasks/arrays_lists/FindSecondLargestTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-20"></a>

### Проверить, содержит ли строка только цифры (Check if String Contains Only Digits)

* **Описание:** Написать функцию для такой проверки. (Проверяет: работа со строками, циклы/регулярные выражения)
* **Задание:** Напишите метод `boolean containsOnlyDigits(String str)`, который возвращает `true`, если строка `str`
  состоит только из цифр (0-9), и `false` в противном случае. Пустая строка или `null` должны возвращать `false`.
* **Пример:** `containsOnlyDigits("12345")` -> `true`, `containsOnlyDigits("12a45")` -> `false`,
  `containsOnlyDigits("")` -> `false`.
* **Решение:**
  [`CheckStringOnlyDigits.java`](src/main/java/com/svedentsov/aqa/tasks/strings/CheckStringOnlyDigits.java),
  [`CheckStringOnlyDigitsTest.java`](src/test/java/com/svedentsov/aqa/tasks/strings/CheckStringOnlyDigitsTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-21"></a>

### Перевернуть порядок слов в предложении (Reverse Words in Sentence)

* **Описание:** "hello world" -> "world hello". (Проверяет: работа со строками, `split()`, `join()`/StringBuilder)
* **Задание:** Напишите метод `String reverseWords(String sentence)`, который переворачивает порядок слов в
  предложении `sentence`, сохраняя сами слова неизменными. Разделители - пробелы. Лишние пробелы в
  начале/конце/между словами должны быть убраны.
* **Пример:** `reverseWords("the sky is blue")` -> `"blue is sky the"`. `reverseWords("  hello world  ")` ->
  `"world hello"`.
* **Решение:**
  [`ReverseWordsSentence.java`](src/main/java/com/svedentsov/aqa/tasks/strings/ReverseWordsSentence.java),
  [`ReverseWordsSentenceTest.java`](src/test/java/com/svedentsov/aqa/tasks/strings/ReverseWordsSentenceTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-22"></a>

### Найти пересечение двух массивов/списков (Find Intersection of Two Arrays/Lists)

* **Описание:** Написать функцию, возвращающую общие элементы для двух коллекций. (Проверяет: работа с коллекциями, Set,
  циклы)
* **Задание:** Напишите метод `Set<Integer> findIntersection(int[] arr1, int[] arr2)`, который возвращает
  множество (`Set`) уникальных элементов, присутствующих в обоих массивах `arr1` и `arr2`.
* **Пример:** `findIntersection(new int[]{1, 2, 2, 1}, new int[]{2, 2})` -> `{2}`.
  `findIntersection(new int[]{4, 9, 5}, new int[]{9, 4, 9, 8, 4})` -> `{4, 9}`.
* **Решение:**
  [`FindIntersectionArrays.java`](src/main/java/com/svedentsov/aqa/tasks/arrays_lists/FindIntersectionArrays.java),
  [`FindIntersectionArraysTest.java`](src/test/java/com/svedentsov/aqa/tasks/arrays_lists/FindIntersectionArraysTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-23"></a>

### Проверка сбалансированности скобок (Balanced Parentheses Check)

* **Описание:** Проверить, правильно ли расставлены скобки `()`, `{}`, `[]` в строке. (Проверяет: Stack, работа со
  строками, логика)
* **Задание:** Напишите метод `boolean areBracketsBalanced(String expression)`, который проверяет, правильно ли
  сбалансированы скобки `()`, `{}`, `[]` в строке `expression`.
* **Пример:** `areBracketsBalanced("({[]})")` -> `true`. `areBracketsBalanced("([)]")` -> `false`.
  `areBracketsBalanced("{[}")` -> `false`. `areBracketsBalanced("()")` -> `true`.
* **Решение:**
  [`BalancedParenthesesCheck.java`](src/main/java/com/svedentsov/aqa/tasks/data_structures/BalancedParenthesesCheck.java),
  [`BalancedParenthesesCheckTest.java`](src/test/java/com/svedentsov/aqa/tasks/data_structures/BalancedParenthesesCheckTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-24"></a>

### Реализовать простой класс (Simple Class Implementation)

* **Описание:** Создать класс `Car` с полями, конструктором, геттерами/сеттерами и методом `toString()`. (Проверяет:
  основы ООП)
* **Задание:** Создайте класс `Car` с приватными полями `make` (String), `model` (String) и `year` (int). Реализуйте
  конструктор, принимающий все три поля, геттеры для всех полей и метод `toString()`, возвращающий строку вида
  `"Car{make='Toyota', model='Camry', year=2020}"`.
* **Пример:** `Car myCar = new Car("Toyota", "Camry", 2020); System.out.println(myCar);` должно вывести указанную
  строку. `myCar.getMake()` должно вернуть `"Toyota"`.
* **Решение:**
  [`SimpleClassImplementation.java`](src/main/java/com/svedentsov/aqa/tasks/oop_design/SimpleClassImplementation.java),
  [`SimpleClassImplementationTest.java`](src/test/java/com/svedentsov/aqa/tasks/oop_design/SimpleClassImplementationTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-25"></a>

### Найти пару чисел в массиве, сумма которых равна заданному значению (Two Sum Problem)

* **Описание:** Дан массив чисел и целевое значение. Найти два числа, которые в сумме дают это значение. (Проверяет:
  циклы, Map/Set, логика)
* **Задание:** Напишите метод `int[] findTwoSumIndices(int[] nums, int target)`, который принимает массив целых
  чисел `nums` и целевое значение `target`. Метод должен вернуть массив из двух индексов элементов, сумма которых
  равна `target`. Если такой пары нет, верните пустой массив или `null`. Предполагается, что решение единственное.
* **Пример:** `findTwoSumIndices(new int[]{2, 7, 11, 15}, 9)` -> `[0, 1]`.
  `findTwoSumIndices(new int[]{3, 2, 4}, 6)` -> `[1, 2]`.
* **Решение:**
  [`TwoSumProblem.java`](src/main/java/com/svedentsov/aqa/tasks/maps_sets/TwoSumProblem.java),
  [`TwoSumProblemTest.java`](src/test/java/com/svedentsov/aqa/tasks/maps_sets/TwoSumProblemTest.java)

[К оглавлению](#table-of-contents)

---
<a name="task-26"></a>

### Группировка элементов списка по какому-либо признаку (Group List Elements)

* **Описание:** Например, сгруппировать список строк по первой букве. (Проверяет: Map, циклы, работа с коллекциями)
* **Задание:** Напишите метод `Map<Integer, List<String>> groupStringsByLength(List<String> strings)`, который
  принимает список строк и возвращает `Map`, где ключи - это длина строки, а значения - списки строк этой длины.
* **Пример:** `groupStringsByLength(List.of("apple", "bat", "cat", "apricot", "ball"))` ->
  `{5: ["apple"], 3: ["bat", "cat"], 7: ["apricot"], 4: ["ball"]}`.
* **Решение:**
  [`GroupListElements.java`](src/main/java/com/svedentsov/aqa/tasks/maps_sets/GroupListElements.java),
  [`GroupListElementsTest.java`](src/test/java/com/svedentsov/aqa/tasks/maps_sets/GroupListElementsTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-27"></a>

### Сгенерировать все простые числа до N (Generate Primes up to N)

* **Описание:** Используя, например, Решето Эратосфена. (Проверяет: алгоритмы, массивы boolean, циклы)
* **Задание:** Напишите метод `List<Integer> generatePrimes(int n)`, который возвращает список всех простых чисел от
  2 до `n` включительно. Используйте алгоритм "Решето Эратосфена".
* **Пример:** `generatePrimes(10)` -> `[2, 3, 5, 7]`. `generatePrimes(20)` -> `[2, 3, 5, 7, 11, 13, 17, 19]`.
* **Решение:**
  [`GeneratePrimesSieve.java`](src/main/java/com/svedentsov/aqa/tasks/algorithms/GeneratePrimesSieve.java),
  [`GeneratePrimesSieveTest.java`](src/test/java/com/svedentsov/aqa/tasks/algorithms/GeneratePrimesSieveTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-28"></a>

### Проверить, является ли число степенью двойки (Check Power of Two)

* **Описание:** Написать функцию для такой проверки. (Проверяет: битовые операции/циклы)
* **Задание:** Напишите метод `boolean isPowerOfTwo(int n)`, который возвращает `true`, если положительное целое
  число `n` является степенью двойки (1, 2, 4, 8, 16...), и `false` иначе.
* **Пример:** `isPowerOfTwo(1)` -> `true`, `isPowerOfTwo(16)` -> `true`, `isPowerOfTwo(10)` -> `false`,
  `isPowerOfTwo(0)` -> `false`.
* **Решение:**
  [`CheckPowerOfTwo.java`](src/main/java/com/svedentsov/aqa/tasks/algorithms/CheckPowerOfTwo.java),
  [`CheckPowerOfTwoTest.java`](src/test/java/com/svedentsov/aqa/tasks/algorithms/CheckPowerOfTwoTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-29"></a>

### Сумма цифр числа (Sum of Digits)

* **Описание:** Написать функцию для вычисления суммы цифр целого числа. (Проверяет: циклы, арифметика, остаток от
  деления)
* **Задание:** Напишите метод `int sumDigits(int number)`, который вычисляет и возвращает сумму цифр
  неотрицательного целого числа `number`.
* **Пример:** `sumDigits(123)` -> `6`, `sumDigits(49)` -> `13`, `sumDigits(5)` -> `5`, `sumDigits(0)` -> `0`.
* **Решение:**
  [`SumOfDigits.java`](src/main/java/com/svedentsov/aqa/tasks/algorithms/SumOfDigits.java),
  [`SumOfDigitsTest.java`](src/test/java/com/svedentsov/aqa/tasks/algorithms/SumOfDigitsTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-30"></a>

### Удалить определенный элемент из массива/списка (Remove Specific Element)

* **Описание:** Написать функцию для удаления всех вхождений заданного элемента. (Проверяет: работа с коллекциями,
  циклы, создание нового списка)
* **Задание:** Напишите метод `List<Integer> removeElement(List<Integer> list, int elementToRemove)`, который
  возвращает новый список, полученный из `list` удалением всех вхождений `elementToRemove`.
* **Пример:** `removeElement(List.of(1, 2, 3, 2, 4, 2), 2)` -> `[1, 3, 4]`.
* **Решение:**
  [`RemoveElementList.java`](src/main/java/com/svedentsov/aqa/tasks/arrays_lists/RemoveElementList.java),
  [`RemoveElementListTest.java`](src/test/java/com/svedentsov/aqa/tasks/arrays_lists/RemoveElementListTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-31"></a>

### Сжатие строки (String Compression)

* **Описание:** "aabcccccaaa" -> "a2b1c5a3". Если "сжатая" строка не короче исходной, вернуть исходную. (Проверяет:
  работа со строками, StringBuilder, циклы)
* **Задание:** Напишите метод `String compressString(String str)`, который выполняет базовое сжатие строки, заменяя
  последовательности повторяющихся символов на символ и количество повторений. Если "сжатая" строка не становится
  короче исходной, метод должен вернуть исходную строку.
* **Пример:** `compressString("aabcccccaaa")` -> `"a2b1c5a3"`. `compressString("abc")` -> `"abc"`.
  `compressString("aabbcc")` -> `"a2b2c2"`.
* **Решение:**
  [`StringCompression.java`](src/main/java/com/svedentsov/aqa/tasks/strings/StringCompression.java),
  [`StringCompressionTest.java`](src/test/java/com/svedentsov/aqa/tasks/strings/StringCompressionTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-32"></a>

### Найти самый часто встречающийся элемент в массиве/списке (Find Most Frequent Element)

* **Описание:** Написать функцию для поиска элемента с максимальным числом вхождений. (Проверяет: Map, циклы, сравнения)
* **Задание:** Напишите метод `int findMostFrequentElement(int[] numbers)`, который находит и возвращает элемент,
  который встречается в массиве `numbers` чаще всего. Если таких элементов несколько, верните любой из них.
  Обработайте случай пустого массива.
* **Пример:** `findMostFrequentElement(new int[]{1, 3, 2, 1, 4, 1, 3})` -> `1`.
  `findMostFrequentElement(new int[]{1, 2, 3})` -> `1` (или `2`, или `3`).
* **Решение:**
  [`FindMostFrequentElement.java`](src/main/java/com/svedentsov/aqa/tasks/maps_sets/FindMostFrequentElement.java)
  [`FindMostFrequentElementTest.java`](src/test/java/com/svedentsov/aqa/tasks/maps_sets/FindMostFrequentElementTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-33"></a>

### Прочитать файл и посчитать строки/слова (Read File and Count Lines/Words)

* **Описание:** Написать код, который читает текстовый файл и выводит количество строк или слов. (Проверяет: основы I/O,
  обработка исключений)
* **Задание:** Напишите метод `int countLines(String filePath)`, который читает текстовый файл по пути `filePath` и
  возвращает количество строк в нем. Обработайте возможные `IOException`. (На собеседовании могут попросить
  использовать `try-with-resources`).
* **Пример:** Если файл `data.txt` содержит 3 строки, `countLines("data.txt")` -> `3`.
* **Решение:**
  [`ReadFileCount.java`](src/main/java/com/svedentsov/aqa/tasks/files_io_formats/ReadFileCount.java)
  [`ReadFileCountTest.java`](src/test/java/com/svedentsov/aqa/tasks/files_io_formats/ReadFileCountTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-34"></a>

### Проверка на число Армстронга (Armstrong Number Check)

* **Описание:** Проверить, является ли число числом Армстронга (сумма его цифр, возведенных в степень количества цифр,
  равна самому числу). (Проверяет: циклы, арифметика)
* **Задание:** Напишите метод `boolean isArmstrongNumber(int number)`, который проверяет, является ли положительное
  целое число `number` числом Армстронга (число, которое равно сумме своих цифр, возведенных в степень, равную
  количеству цифр в числе).
* **Пример:** `isArmstrongNumber(153)` -> `true` (1^3 + 5^3 + 3^3 = 1 + 125 + 27 = 153). `isArmstrongNumber(370)` ->
  `true`. `isArmstrongNumber(123)` -> `false`.
* **Решение:**
  [`ArmstrongNumberCheck.java`](src/main/java/com/svedentsov/aqa/tasks/algorithms/ArmstrongNumberCheck.java)
  [`ArmstrongNumberCheckTest.java`](src/test/java/com/svedentsov/aqa/tasks/algorithms/ArmstrongNumberCheckTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-35"></a>

### Реализовать базовый Стек или Очередь (Implement Basic Stack/Queue)

* **Описание:** Используя массив или `LinkedList`. (Проверяет: основы структур данных, ООП)
* **Задание:** Реализуйте класс `MyStack`, используя `java.util.LinkedList` или массив, который будет иметь методы
  `push(int value)`, `pop()` (возвращает и удаляет верхний элемент), `peek()` (возвращает верхний элемент без
  удаления) и `isEmpty()`.
* **Пример:** `MyStack stack = new MyStack(); stack.push(1); stack.push(2); stack.pop()` -> `2`. `stack.peek()` ->
  `1`. `stack.isEmpty()` -> `false`.
* **Решение:**
  [`ImplementStackQueue`](src/main/java/com/svedentsov/aqa/tasks/data_structures/ImplementStackQueue.java)

[К оглавлению](#table-of-contents)

---

<a name="task-36"></a>

### Найти наибольший общий делитель (НОД / GCD)

* **Описание:** Написать функцию для нахождения НОД двух чисел. (Проверяет: алгоритм Евклида, циклы/рекурсия)
* **Задание:** Напишите метод `int gcd(int a, int b)`, который вычисляет наибольший общий делитель двух
  неотрицательных целых чисел `a` и `b`, используя алгоритм Евклида.
* **Пример:** `gcd(48, 18)` -> `6`. `gcd(10, 5)` -> `5`. `gcd(7, 13)` -> `1`. `gcd(0, 5)` -> `5`.
* **Решение:**
  [`GCD.java`](src/main/java/com/svedentsov/aqa/tasks/algorithms/GCD.java)
  [`GCDTest.java`](src/test/java/com/svedentsov/aqa/tasks/algorithms/GCDTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-37"></a>

### Найти наименьшее общее кратное (НОК / LCM)

* **Описание:** Написать функцию для нахождения НОК двух чисел. (Проверяет: НОД, арифметика)
* **Задание:** Напишите метод `int lcm(int a, int b)`, который вычисляет наименьшее общее кратное двух положительных
  целых чисел `a` и `b`. Можно использовать формулу `lcm(a, b) = (|a * b|) / gcd(a, b)`.
* **Пример:** `lcm(4, 6)` -> `12`. `lcm(5, 7)` -> `35`.
* **Решение:**
  [`LCM.java`](src/main/java/com/svedentsov/aqa/tasks/algorithms/LCM.java)
  [`LCMTest.java`](src/test/java/com/svedentsov/aqa/tasks/algorithms/LCMTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-38"></a>

### Бинарный поиск в отсортированном массиве (Binary Search)

* **Описание:** Реализовать алгоритм бинарного поиска. (Проверяет: алгоритмы, работа с индексами, рекурсия/циклы)
* **Задание:** Напишите метод `int binarySearch(int[] sortedArray, int key)`, который ищет индекс элемента `key` в
  отсортированном по возрастанию массиве `sortedArray` с помощью алгоритма бинарного поиска. Если элемент не найден,
  верните -1.
* **Пример:** `binarySearch(new int[]{1, 3, 5, 7, 9}, 5)` -> `2`. `binarySearch(new int[]{1, 3, 5, 7, 9}, 6)` -> `-1`.
* **Решение:**
  [`BinarySearch.java`](src/main/java/com/svedentsov/aqa/tasks/sorting_searching/BinarySearch.java)
  [`BinarySearchTest.java`](src/test/java/com/svedentsov/aqa/tasks/sorting_searching/BinarySearchTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-39"></a>

### Повернуть массив на K элементов (Rotate Array)

* **Описание:** Сдвинуть элементы массива циклически влево или вправо на K позиций. (Проверяет: работа с массивами,
  логика, возможно доп. массив)
* **Задание:** Напишите метод `void rotateArray(int[] arr, int k)`, который циклически сдвигает элементы массива
  `arr` вправо на `k` позиций. `k` может быть больше длины массива. Модифицируйте массив "на месте" (in-place), если
  возможно, или верните новый.
* **Пример:** `arr = [1, 2, 3, 4, 5], k = 2`. После `rotateArray(arr, k)` массив `arr` должен стать
  `[4, 5, 1, 2, 3]`.
* **Решение:**
  [`RotateArray.java`](src/main/java/com/svedentsov/aqa/tasks/arrays_lists/RotateArray.java)
  [`RotateArrayTest.java`](src/test/java/com/svedentsov/aqa/tasks/arrays_lists/RotateArrayTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-40"></a>

### Сравнить два объекта кастомного класса (Compare Custom Objects)

* **Описание:** Реализовать интерфейс `Comparable` или предоставить `Comparator` для кастомного класса (например,
  `Person` по возрасту). (Проверяет: ООП, интерфейсы)
* **Задание:** Модифицируйте класс `Person` (с полями `name`, `age`), чтобы он реализовывал интерфейс
  `Comparable<Person>`. Сравнение должно происходить сначала по возрасту (по возрастанию), а при одинаковом
  возрасте - по имени (в алфавитном порядке).
* **Пример:** `Collections.sort(listOfPersons)` должен отсортировать список объектов `Person` согласно заданным
  правилам. `new Person("Bob", 30).compareTo(new Person("Alice", 30))` -> положительное число.
  `new Person("Alice", 25).compareTo(new Person("Bob", 30))` -> отрицательное число.
* **Решение:**
  [`CompareCustomObjects.java`](src/main/java/com/svedentsov/aqa/tasks/oop_design/CompareCustomObjects.java)
  [`CompareCustomObjectsTest.java`](src/test/java/com/svedentsov/aqa/tasks/oop_design/CompareCustomObjectsTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-41"></a>

### Валидация IP-адреса (Validate IP Address)

* **Описание:** Написать функцию для проверки строки на соответствие формату IPv4. (Проверяет: работа со строками,
  `split()`, условия, парсинг чисел)
* **Задание:** Напишите метод `boolean isValidIPv4(String ip)`, который проверяет, является ли строка `ip` валидным
  IPv4 адресом. Валидный адрес состоит из четырех чисел от 0 до 255, разделенных точками. Ведущие нули не
  допускаются (кроме самого числа 0).
* **Пример:** `isValidIPv4("192.168.0.1")` -> `true`. `isValidIPv4("255.255.255.255")` -> `true`.
  `isValidIPv4("0.0.0.0")` -> `true`. `isValidIPv4("256.1.1.1")` -> `false`. `isValidIPv4("192.168.0.01")` ->
  `false`. `isValidIPv4("192.168..1")` -> `false`. `isValidIPv4("abc.def.ghi.jkl")` -> `false`.
* **Решение:**
  [`ValidateIpAddress.java`](src/main/java/com/svedentsov/aqa/tasks/strings/ValidateIpAddress.java)
  [`ValidateIpAddressTest.java`](src/test/java/com/svedentsov/aqa/tasks/strings/ValidateIpAddressTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-42"></a>

### Конвертация римских чисел в целые (Roman to Integer)

* **Описание:** Написать функцию для преобразования строки с римским числом в `int`. (Проверяет: работа со строками,
  Map, логика)
* **Пример:**
    * **Задание:** Напишите метод `int romanToInt(String s)`, который конвертирует строку `s`, содержащую римское число,
      в целое число. (I=1, V=5, X=10, L=50, C=100, D=500, M=1000). Учитывайте правила вычитания (IV=4, IX=9, XL=40,
      XC=90, CD=400, CM=900).
    * **Пример:** `romanToInt("III")` -> `3`. `romanToInt("LVIII")` -> `58`. `romanToInt("MCMXCIV")` -> `1994`.
* **Решение:**
  [`RomanToInteger.java`](src/main/java/com/svedentsov/aqa/tasks/strings/RomanToInteger.java)
  [`RomanToIntegerTest.java`](src/test/java/com/svedentsov/aqa/tasks/strings/RomanToIntegerTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-43"></a>

### Конвертация целых чисел в римские (Integer to Roman)

* **Описание:** Написать функцию для обратного преобразования. (Проверяет: арифметика, циклы, условия, Map/массивы)
* **Задание:** Напишите метод `String intToRoman(int num)`, который конвертирует целое число `num` (от 1 до 3999) в
  строку с римским представлением.
* **Пример:** `intToRoman(3)` -> `"III"`. `intToRoman(58)` -> `"LVIII"`. `intToRoman(1994)` -> `"MCMXCIV"`.
* **Решение:**
  [`IntegerToRoman.java`](src/main/java/com/svedentsov/aqa/tasks/strings/IntegerToRoman.java)
  [`IntegerToRomanTest.java`](src/test/java/com/svedentsov/aqa/tasks/strings/IntegerToRomanTest.java)

[К оглавлению](#table-of-contents)

---

<a name="task-44"></a>

### Найти длину самой длинной подстроки без повторяющихся символов (Longest Substring Without Repeating Characters)

* **Описание:** (Проверяет: работа со строками, Set/Map, два указателя/sliding window)
* **Задание:** Напишите метод `int lengthOfLongestSubstring(String s)`, который находит длину самой длинной
  подстроки строки `s` без повторяющихся символов.
* **Пример:** `lengthOfLongestSubstring("abcabcbb")` -> `3` (подстрока "abc").
  `lengthOfLongestSubstring("bbbbb")` -> `1` (подстрока "b"). `lengthOfLongestSubstring("pwwkew")` -> `3` (
  подстрока "wke").
* **Решение:** [
  `LongestSubstringWithoutRepeating`](src/main/java/com/svedentsov/aqa/tasks/strings/LongestSubstringWithoutRepeating.java)

[К оглавлению](#table-of-contents)

---

<a name="task-45"></a>

### Переместить все нули в конец массива (Move Zeroes to End)

* **Описание:** Сохраняя относительный порядок ненулевых элементов. (Проверяет: работа с массивами, два указателя,
  циклы)
* **Задание:** Напишите метод `void moveZeroes(int[] nums)`, который перемещает все нули в конец массива `nums`,
  сохраняя относительный порядок ненулевых элементов. Модифицируйте массив "на месте".
* **Пример:** `nums = [0, 1, 0, 3, 12]`. После `moveZeroes(nums)` массив должен стать `[1, 3, 12, 0, 0]`.
* **Решение:** [`MoveZeroesEnd`](src/main/java/com/svedentsov/aqa/tasks/arrays_lists/MoveZeroesEnd.java)

[К оглавлению](#table-of-contents)

---

<a name="task-46"></a>

### Найти все перестановки строки (Find All Permutations of a String)

* **Описание:** (Проверяет: рекурсия, работа со строками/массивами символов)
* **Задание:** Напишите метод `List<String> findPermutations(String str)`, который возвращает список всех уникальных
  перестановок символов строки `str`.
* **Пример:** `findPermutations("abc")` -> `["abc", "acb", "bac", "bca", "cab", "cba"]`.
  `findPermutations("aab")` -> `["aab", "aba", "baa"]`.
* **Решение:** [
  `FindStringPermutations`](src/main/java/com/svedentsov/aqa/tasks/strings/FindStringPermutations.java)

[К оглавлению](#table-of-contents)

---

<a name="task-47"></a>

### Максимальная сумма подмассива (Maximum Subarray Sum - Kadane's Algorithm)

* **Описание:** Найти непрерывный подмассив с наибольшей суммой. (Проверяет: алгоритмы, циклы)
* **Задание:** Напишите метод `int maxSubArraySum(int[] nums)`, который находит максимальную сумму непрерывного
  подмассива в массиве `nums` (подмассив должен содержать хотя бы один элемент).
* **Пример:** `maxSubArraySum(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4})` -> `6` (подмассив `[4, -1, 2, 1]`).
  `maxSubArraySum(new int[]{1})` -> `1`. `maxSubArraySum(new int[]{5, 4, -1, 7, 8})` -> `23`.
* **Решение:** [`MaximumSubarraySum`](src/main/java/com/svedentsov/aqa/tasks/algorithms/MaximumSubarraySum.java)

[К оглавлению](#table-of-contents)

---

<a name="task-48"></a>

### Найти K-ый по величине элемент в массиве (Find Kth Largest Element)

* **Описание:** (Проверяет: сортировка, частичная сортировка, PriorityQueue)
* **Задание:** Напишите метод `int findKthLargest(int[] nums, int k)`, который находит `k`-ый по величине элемент в
  массиве `nums`. Обратите внимание, это `k`-ый по величине элемент в отсортированном порядке, а не `k`-ый
  уникальный элемент.
* **Пример:** `findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2)` -> `5`.
  `findKthLargest(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4)` -> `4`.
* **Решение:** [
  `KthLargestElement`](src/main/java/com/svedentsov/aqa/tasks/sorting_searching/KthLargestElement.java)

[К оглавлению](#table-of-contents)

---

<a name="task-49"></a>

### Проверка на идеальное число (Perfect Number Check)

* **Описание:** Проверить, равно ли число сумме своих собственных делителей. (Проверяет: циклы, арифметика)
* **Задание:** Напишите метод `boolean isPerfectNumber(int num)`, который проверяет, является ли положительное целое
  число `num` идеальным числом (число, равное сумме своих собственных положительных делителей, т.е. делителей,
  исключая само число).
* **Пример:** `isPerfectNumber(6)` -> `true` (1 + 2 + 3 = 6). `isPerfectNumber(28)` -> `true` (1 + 2 + 4 + 7 + 14 =
  28). `isPerfectNumber(7)` -> `false`.
* **Решение:** [`PerfectNumberCheck`](src/main/java/com/svedentsov/aqa/tasks/algorithms/PerfectNumberCheck.java)

[К оглавлению](#table-of-contents)

---

<a name="task-50"></a>

### Написать простой парсер CSV строки (Parse Simple CSV Line)

* **Описание:** Разбить строку с запятыми-разделителями на поля, учитывая кавычки. (Проверяет: работа со строками,
  логика)
* **Задание:** Напишите метод `List<String> parseCsvLine(String csvLine)`, который разбирает строку `csvLine`,
  представляющую одну строку CSV-файла, на отдельные поля. Учитывайте, что поля могут быть заключены в двойные
  кавычки, и внутри таких полей могут встречаться запятые. Также обрабатывайте экранированные кавычки внутри поля (
  две кавычки подряд `""` внутри кавычек означают одну кавычку).
* **Пример:** `parseCsvLine("John,Doe,30")` -> `["John", "Doe", "30"]`.
  `parseCsvLine("\"Smith, John\",\"New York, NY\",45")` -> `["Smith, John", "New York, NY", "45"]`.
* **Решение:** [`ParseCsvLine`](src/main/java/com/svedentsov/aqa/tasks/files_io_formats/ParseCsvLine.java)

[К оглавлению](#table-of-contents)

---
<a name="task-51"></a>

### Реализовать `equals()` и `hashCode()` для кастомного класса

* **Описание:** Объяснить контракт между ними. (Проверяет: ООП, контракты Java)
* **Задание:** Для класса `Point` с полями `int x` и `int y`, переопределите методы `equals(Object o)` и
  `hashCode()` так, чтобы они соответствовали контракту: равные объекты (`p1.x == p2.x && p1.y == p2.y`) должны
  иметь одинаковый `hashCode`.
* **Пример:** `new Point(1, 2).equals(new Point(1, 2))` -> `true`. `new Point(1, 2).equals(new Point(2, 1))` ->
  `false`. `new Point(1, 2).hashCode()` должен быть равен `new Point(1, 2).hashCode()`.
* **Решение:** [`EqualsHashCode`](src/main/java/com/svedentsov/aqa/tasks/oop_design/EqualsHashCode.java)

[К оглавлению](#table-of-contents)

---

<a name="task-52"></a>

### Найти общий префикс для массива строк (Longest Common Prefix)

* **Описание:** Найти самую длинную строку, которая является префиксом всех строк в массиве. (Проверяет: работа со
  строками, циклы)
* **Задание:** Напишите метод `String longestCommonPrefix(String[] strs)`, который находит самую длинную строку,
  являющуюся префиксом всех строк в массиве `strs`. Если общего префикса нет, верните пустую строку `""`.
* **Пример:** `longestCommonPrefix(new String[]{"flower", "flow", "flight"})` -> `"fl"`.
  `longestCommonPrefix(new String[]{"dog", "racecar", "car"})` -> `""`.
* **Решение:** [`LongestCommonPrefix`](src/main/java/com/svedentsov/aqa/tasks/strings/LongestCommonPrefix.java)

[К оглавлению](#table-of-contents)

---

<a name="task-53"></a>

### Проверка на подпоследовательность (Is Subsequence)

* **Описание:** Проверить, является ли одна строка подпоследовательностью другой (символы идут в том же порядке, но не
  обязательно подряд). (Проверяет: работа со строками, два указателя)
* **Задание:** Напишите метод `boolean isSubsequence(String s, String t)`, который возвращает `true`, если строка
  `s` является подпоследовательностью строки `t` (т.е. `s` можно получить из `t` удалением некоторых символов без
  изменения порядка оставшихся), и `false` иначе.
* **Пример:** `isSubsequence("ace", "abcde")` -> `true`. `isSubsequence("axc", "ahbgdc")` -> `false`.
* **Решение:** [`IsSubsequence`](src/main/java/com/svedentsov/aqa/tasks/strings/IsSubsequence.java)

[К оглавлению](#table-of-contents)

---

<a name="task-54"></a>

### Создание простого Singleton (Implement Singleton)

* **Описание:** Реализовать паттерн Singleton. (Проверяет: основы паттернов проектирования, статические члены,
  синхронизация - опционально)
* **Задание:** Реализуйте класс `Logger` как Singleton, используя статическое поле и приватный конструктор. Добавьте
  статический метод `getInstance()` для получения единственного экземпляра и метод `log(String message)`, который
  просто выводит сообщение в консоль.
* **Пример:** `Logger logger1 = Logger.getInstance(); Logger logger2 = Logger.getInstance(); logger1 == logger2` ->
  `true`. `logger1.log("Test message")` выводит "Test message".
* **Решение:** [`Singleton`](src/main/java/com/svedentsov/aqa/tasks/oop_design/Singleton.java)

[К оглавлению](#table-of-contents)

---

<a name="task-55"></a>

### Подсчет островов (Number of Islands - упрощенно)

* **Описание:** Дана 2D матрица 0 и 1. Посчитать количество групп из 1 (связанных по горизонтали/вертикали). (Проверяет:
  обход матриц, рекурсия/DFS/BFS - базовая концепция)
* **Задание:** Напишите метод `int countIslands(char[][] grid)`, где `grid` - это 2D массив символов, содержащий '
  1' (земля) и '0' (вода). Метод должен посчитать количество "островов". Остров окружен водой и формируется путем
  соединения смежных (горизонтально или вертикально) земель.
* **Пример:**
  `grid = {{'1','1','0','0','0'}, {'1','1','0','0','0'}, {'0','0','1','0','0'}, {'0','0','0','1','1'}}` ->
  `countIslands(grid)` должен вернуть `3`.
* **Решение:** [`NumberOfIslands`](src/main/java/com/svedentsov/aqa/tasks/graphs_matrices/NumberOfIslands.java)

[К оглавлению](#table-of-contents)

---

<a name="task-56"></a>

### Реализовать `toString()` для сложного объекта

* **Описание:** Форматированный вывод состояния объекта с вложенными объектами. (Проверяет: ООП, работа со строками)
* **Задание:** Дан класс `Order` { `int orderId; Customer customer; List<Product> products;` }. Класс `Customer` {
  `String name;` }. Класс `Product` { `String name; double price;` }. Переопределите метод `toString()` для класса
  `Order`, чтобы он возвращал читаемую строку, включающую ID заказа, имя клиента и список продуктов (каждый на новой
  строке с отступом).
* **Пример:** Вывод может выглядеть примерно так:
  `Order{orderId=123, customer=Customer{name='Alice'}, products=[\n  Product{name='Laptop', price=1200.0},\n  Product{name='Mouse', price=25.0}\n]}`.
* **Решение:** [`ToStringComplex`](src/main/java/com/svedentsov/aqa/tasks/oop_design/ToStringComplex.java)

[К оглавлению](#table-of-contents)

---

<a name="task-57"></a>

### Сгенерировать случайную строку заданной длины (Generate Random String)

* **Описание:** (Проверяет: работа с `Random`, `StringBuilder`/`char[]`)
* **Задание:** Напишите метод `String generateRandomString(int length)`, который генерирует и возвращает случайную
  строку, состоящую из букв (верхнего и нижнего регистра) и цифр, указанной длины `length`.
* **Пример:** `generateRandomString(10)` может вернуть `"aK8s2ZpQ1v"`.
* **Решение:** [`GenerateRandomString`](src/main/java/com/svedentsov/aqa/tasks/strings/GenerateRandomString.java)

[К оглавлению](#table-of-contents)

---

<a name="task-58"></a>

### Базовая валидация формата Email (Basic Email Format Validation)

* **Описание:** Очень упрощенная проверка наличия `@` и `.`. (Проверяет: работа со строками, `contains`/`indexOf`)
* **Задание:** Напишите метод `boolean isValidEmailBasic(String email)`, который выполняет очень простую проверку
  формата email: строка должна содержать один символ `@` и хотя бы одну точку `.` после символа `@`.
* **Пример:** `isValidEmailBasic("test@example.com")` -> `true`. `isValidEmailBasic("test.example.com")` -> `false`.
  `isValidEmailBasic("test@examplecom")` -> `false`. `isValidEmailBasic("test@@example.com")` -> `false`.
* **Решение:** [`BasicEmailValidation`](src/main/java/com/svedentsov/aqa/tasks/strings/BasicEmailValidation.java)

[К оглавлению](#table-of-contents)

---

<a name="task-59"></a>

### Найти индекс первого вхождения подстроки (Find Substring Index)

* **Описание:** Реализовать аналог `indexOf`. (Проверяет: работа со строками, циклы)
* **Задание:** Напишите метод `int findSubstringIndex(String text, String pattern)` без использования
  `String.indexOf()`, который находит индекс первого вхождения строки `pattern` в строку `text`. Если `pattern` не
  найден, верните -1.
* **Пример:** `findSubstringIndex("hello world", "world")` -> `6`. `findSubstringIndex("aaaaa", "bba")` -> `-1`.
  `findSubstringIndex("abc", "")` -> `0`.
* **Решение:** [`FindSubstringIndex`](src/main/java/com/svedentsov/aqa/tasks/strings/FindSubstringIndex.java)

[К оглавлению](#table-of-contents)

---

<a name="task-60"></a>

### Перемножение матриц (Matrix Multiplication - для 2x2 или 3x3)

* **Описание:** (Проверяет: вложенные циклы, работа с 2D массивами)
* **Задание:** Напишите метод `int[][] multiplyMatrices(int[][] matrixA, int[][] matrixB)`, который перемножает две
  квадратные матрицы (например, 2x2) и возвращает результирующую матрицу. Предполагается, что матрицы совместимы для
  умножения.
* **Пример:** `matrixA = {{1, 2}, {3, 4}}, matrixB = {{2, 0}, {1, 2}}`. `multiplyMatrices(matrixA, matrixB)` ->
  `{{4, 4}, {10, 8}}`.
* **Решение:** [
  `MatrixMultiplication`](src/main/java/com/svedentsov/aqa/tasks/graphs_matrices/MatrixMultiplication.java)

[К оглавлению](#table-of-contents)

---

<a name="task-61"></a>

### Реализовать LRU Cache (Least Recently Used Cache)

* **Описание:** (Проверяет: структуры данных - LinkedHashMap или комбинация Map + DoublyLinkedList)
* **Задание:** Спроектируйте и реализуйте структуру данных `LRUCache` (`Least Recently Used Cache`). Она должна
  поддерживать операции `get(key)` (возвращает значение по ключу или -1, если ключ не найден, и делает этот ключ
  самым свежим) и `put(key, value)` (добавляет/обновляет ключ-значение и делает его самым свежим). При достижении
  максимальной емкости (`capacity`), перед добавлением нового элемента, должен удаляться наименее недавно
  использовавшийся элемент (самый старый).
* **Пример:**
  `LRUCache cache = new LRUCache(2); cache.put(1, 1); cache.put(2, 2); cache.get(1); cache.put(3, 3); // Вытесняет 2 cache.get(2);` ->
  `-1`. `cache.get(1);` -> `1`. `cache.get(3);` -> `3`.
* **Решение:** [`LRUCache`](src/main/java/com/svedentsov/aqa/tasks/data_structures/LRUCache.java)

[К оглавлению](#table-of-contents)

---

<a name="task-62"></a>

### Найти медиану двух отсортированных массивов (Median of Two Sorted Arrays)

* **Описание:** (Проверяет: сложные алгоритмы, бинарный поиск)
* **Задание:** Напишите метод `double findMedianSortedArrays(int[] nums1, int[] nums2)`, который находит медиану
  двух отсортированных массивов `nums1` и `nums2`.
* **Пример:** `findMedianSortedArrays(new int[]{1, 3}, new int[]{2})` -> `2.0`.
  `findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4})` -> `2.5`.
* **Решение:** [`MedianSortedArrays`](src/main/java/com/svedentsov/aqa/tasks/algorithms/MedianSortedArrays.java)

[К оглавлению](#table-of-contents)

---
<a name="task-63"></a>

### Найти все комбинации элементов списка, сумма которых равна X (Combination Sum)

* **Описание:** Найти все уникальные комбинации чисел из массива `candidates`, сумма которых равна `target`. Одно и то
  же число может быть использовано неограниченное количество раз. (Проверяет: рекурсия, backtracking)
* **Задание:** Напишите метод `List<List<Integer>> combinationSum(int[] candidates, int target)`, который находит
  все уникальные комбинации чисел из массива `candidates`, сумма которых равна `target`. Одно и то же число может
  быть использовано неограниченное количество раз.
* **Пример:** `candidates = [2, 3, 6, 7], target = 7`. Результат: `[[2, 2, 3], [7]]`.
* **Решение:** [`CombinationSum.`](src/main/java/com/svedentsov/aqa/tasks/dp/CombinationSum.java)

[К оглавлению](#table-of-contents)

---

<a name="task-64"></a>

### Редакторское расстояние (Edit Distance / Levenshtein Distance)

* **Описание:** Найти минимальное количество операций (вставка, удаление, замена) для превращения одной строки в
  другую. (Проверяет: динамическое программирование)
* **Задание:** Напишите метод `int minEditDistance(String word1, String word2)`, который вычисляет минимальное
  количество операций (вставка, удаление, замена символа), необходимых для преобразования `word1` в `word2`.
* **Пример:** `minEditDistance("horse", "ros")` -> `3`. `minEditDistance("intention", "execution")` -> `5`.
* **Решение:** [`EditDistance`](src/main/java/com/svedentsov/aqa/tasks/dp/EditDistance.java)

[К оглавлению](#table-of-contents)

---

<a name="task-65"></a>

### Задача о рюкзаке (Knapsack Problem - упрощенная версия 0/1)

* **Описание:** Даны веса и стоимости `n` предметов и вместимость рюкзака `W`. Найдите максимальную суммарную стоимость
  предметов, которые можно поместить в рюкзак, не превысив его вместимость. (Чаще просят обсудить подход - динамическое
  программирование). (Проверяет: динамическое программирование/рекурсия)
* **Задание:** Реализуйте метод `int knapsack01(int[] weights, int[] values, int capacity)`, который решает задачу о
  рюкзаке 0/1: дан набор предметов, каждый с весом и стоимостью, и рюкзак с максимальной вместимостью. Нужно выбрать
  подмножество предметов так, чтобы их суммарный вес не превышал вместимость рюкзака, а суммарная стоимость была
  максимальной. Каждый предмет можно взять только один раз (0/1).
* **Пример:** Веса: `[10, 20, 30]`, Стоимости: `[60, 100, 120]`, Вместимость: `50`. Решение: взять предметы 2 и 3 (
  вес 20+30=50), стоимость 100+120=220.
* **Решение:** [`Knapsack01`](src/main/java/com/svedentsov/aqa/tasks/dp/Knapsack01.java)

[К оглавлению](#table-of-contents)

---

<a name="task-66"></a>

### Сортировка слиянием (Merge Sort)

* **Описание:** Реализовать алгоритм. (Проверяет: рекурсия, алгоритмы сортировки)
* **Задание:** Реализуйте метод `void mergeSort(int[] arr)`, который сортирует массив `arr` по возрастанию,
  используя рекурсивный алгоритм сортировки слиянием.
* **Пример:** Исходный `[5, 1, 4, 2, 8]` после `mergeSort` станет `[1, 2, 4, 5, 8]`.
* **Решение:** [`MergeSort`](src/main/java/com/svedentsov/aqa/tasks/sorting_searching/MergeSort.java)

[К оглавлению](#table-of-contents)

---

<a name="task-67"></a>

### Быстрая сортировка (Quick Sort)

* **Описание:** Реализовать алгоритм. (Проверяет: рекурсия, алгоритмы сортировки, выбор опорного элемента)
* **Задание:** Реализуйте метод `void quickSort(int[] arr)`, который сортирует массив `arr` по возрастанию,
  используя рекурсивный алгоритм быстрой сортировки.
* **Пример:** Исходный `[5, 1, 4, 2, 8]` после `quickSort` станет `[1, 2, 4, 5, 8]`.
* **Решение:** [`QuickSort`](src/main/java/com/svedentsov/aqa/tasks/sorting_searching/QuickSort.java)

[К оглавлению](#table-of-contents)

---

<a name="task-68"></a>

### Построить бинарное дерево поиска (Build Binary Search Tree)

* **Описание:** Вставить элементы из массива в BST. (Проверяет: основы деревьев, рекурсия, ООП)
* **Задание:** Реализуйте класс `TreeNode` и метод `TreeNode insertIntoBST(TreeNode root, int val)`, который
  вставляет значение `val` в бинарное дерево поиска с корнем `root` и возвращает корень обновленного дерева.
* **Пример:** `insertIntoBST(root, 5)` вставит 5 в соответствующее место дерева.
* **Решение:** [`BuildBST`](src/main/java/com/svedentsov/aqa/tasks/trees/BuildBST.java)

[К оглавлению](#table-of-contents)

---

<a name="task-69"></a>

### Обход бинарного дерева (Tree Traversal - Inorder, Preorder, Postorder)

* **Описание:** Реализовать один из обходов. (Проверяет: рекурсия, структуры данных)
* **Задание:** Для данного класса `TreeNode` (с полями `val`, `left`, `right`), реализуйте метод
  `List<Integer> inorderTraversal(TreeNode root)`, который выполняет центрированный (in-order) обход дерева и
  возвращает список значений узлов.
* **Пример:** Для дерева `[1, null, 2, 3]`, `inorderTraversal` вернет `[1, 3, 2]`.
* **Решение:** [`TreeTraversal`](src/main/java/com/svedentsov/aqa/tasks/trees/TreeTraversal.java)

[К оглавлению](#table-of-contents)

---

<a name="task-70"></a>

### Сериализация/Десериализация простого объекта в JSON (Concept)

* **Описание:** Обсудить, как бы это делалось с помощью библиотеки типа Jackson/Gson (без написания полного кода). (
  Проверяет: понимание форматов данных, библиотек)
* **Задание:** Объясните, как бы вы сериализовали объект класса `Person {String name; int age;}` в JSON-строку (
  например, `{"name":"John", "age":30}`) и десериализовали обратно, используя гипотетическую библиотеку (или
  упомянув Jackson/Gson).
* **Пример:** Обсуждение использования аннотаций, `ObjectMapper` и т.д.
* **Решение (Концептуальное обсуждение):** [
  `JsonConcept`](src/main/java/com/svedentsov/aqa/tasks/files_io_formats/JsonConcept.java)

[К оглавлению](#table-of-contents)

---

<a name="task-71"></a>

### Найти цикл в связанном списке (Detect Cycle in Linked List)

* **Описание:** (Проверяет: структуры данных, два указателя - slow/fast)
* **Задание:** Напишите метод `boolean hasCycle(ListNode head)`, где `ListNode` - узел связанного списка (с полями
  `val` и `next`). Метод должен вернуть `true`, если в списке есть цикл, и `false` иначе.
* **Пример:** Если узел `next` последнего элемента указывает на один из предыдущих узлов, метод вернет `true`.
* **Решение:** [
  `DetectCycleLinkedList`](src/main/java/com/svedentsov/aqa/tasks/linked_lists/DetectCycleLinkedList.java)

[К оглавлению](#table-of-contents)

---

<a name="task-72"></a>

### Перевернуть связанный список (Reverse Linked List)

* **Описание:** (Проверяет: структуры данных, работа с указателями)
* **Задание:** Напишите метод `ListNode reverseList(ListNode head)`, который переворачивает односвязный список и
  возвращает новую голову списка.
* **Пример:** Список `1 -> 2 -> 3 -> null` должен стать `3 -> 2 -> 1 -> null`.
* **Решение:** [`ReverseLinkedList`](src/main/java/com/svedentsov/aqa/tasks/linked_lists/ReverseLinkedList.java)

[К оглавлению](#table-of-contents)

---

<a name="task-73"></a>

### Реализовать Rate Limiter (концептуально)

* **Описание:** Обсудить подходы (token bucket, leaky bucket). (Проверяет: системный дизайн - очень базово)
* **Задание:** Обсудите, как бы вы реализовали простой ограничитель скорости запросов (Rate Limiter), который
  позволяет делать не более N запросов в секунду для определенного пользователя или IP-адреса. Какие структуры
  данных вы бы использовали? (Например, Token Bucket или Leaky Bucket).
* **Пример:** Обсуждение использования `Map<UserId, Timestamp/Counter>` или `Map<UserId, TokenBucketState>`.
* **Решение (Концептуальное обсуждение):** [
  `RateLimiterConcept`](src/main/java/com/svedentsov/aqa/tasks/system_concepts/RateLimiterConcept.java)

[К оглавлению](#table-of-contents)

---

<a name="task-74"></a>

### Простая реализация Pub/Sub (концептуально)

* **Описание:** Обсудить базовые компоненты. (Проверяет: понимание паттернов обмена сообщениями)
* **Задание:** Опишите основные компоненты и логику простой системы Publish/Subscribe (Издатель/Подписчик) в памяти.
  Как бы вы хранили подписчиков на темы? Как бы издатель отправлял сообщения?
* **Пример:** Обсуждение `Map<Topic, List<Subscriber>>`, методов `subscribe(topic, subscriber)`,
  `publish(topic, message)`.
* **Решение (Концептуальное обсуждение + Простой Код):** [
  `PubSubConcept`](src/main/java/com/svedentsov/aqa/tasks/system_concepts/PubSubConcept.java)

[К оглавлению](#table-of-contents)

---

<a name="task-75"></a>

### Найти K ближайших точек к началу координат (K Closest Points to Origin)

* **Описание:** (Проверяет: геометрия, PriorityQueue/сортировка)
* **Задание:** Напишите метод `int[][] kClosest(int[][] points, int k)`, где `points` - массив точек вида `[x, y]`.
  Метод должен вернуть `k` точек, наиболее близких к началу координат (0, 0).
* **Пример:** `points = [[1, 3], [-2, 2]], k = 1`. Результат: `[[-2, 2]]`.
  `points = [[3, 3], [5, -1], [-2, 4]], k = 2`. Результат: `[[3, 3], [-2, 4]]` (порядок не важен).
* **Решение:** [
  `KClosestPointsOrigin`](src/main/java/com/svedentsov/aqa/tasks/arrays_lists/KClosestPointsOrigin.java)

[К оглавлению](#table-of-contents)

---
<a name="task-76"></a>

### Генерация уникальных ID (Generate Unique IDs)

* **Описание:** Обсудить подходы (UUID, timestamp + counter). (Проверяет: общие знания)
* **Задание:** Обсудите различные подходы к генерации уникальных идентификаторов в распределенной системе или в
  рамках одного приложения. Какие плюсы и минусы у каждого подхода (например, UUID, Snowflake, Timestamp + Counter)?
* **Пример:** Сравнение вероятности коллизий, производительности, сортируемости ID.
* **Решение (Концептуальное обсуждение):** [
  `UniqueIdConcept`](src/main/java/com/svedentsov/aqa/tasks/system_concepts/UniqueIdConcept.java)

[К оглавлению](#table-of-contents)

---

<a name="task-77"></a>

### Конвертация Base64 (Encode/Decode)

* **Описание:** Использовать встроенные классы Java. (Проверяет: знание стандартной библиотеки)
* **Задание:** Напишите метод `String encodeBase64(String input)` и `String decodeBase64(String encodedInput)`,
  используя класс `java.util.Base64`, для кодирования строки в Base64 и декодирования обратно.
* **Пример:** `encodeBase64("hello")` -> `"aGVsbG8="`. `decodeBase64("aGVsbG8=")` -> `"hello"`.
* **Решение:** [`Base64`](src/main/java/com/svedentsov/aqa/tasks/files_io_formats/Base64.java)

[К оглавлению](#table-of-contents)

---

<a name="task-78"></a>

### Работа с датами и временем (Date/Time Manipulation)

* **Описание:** Найти разницу между датами, отформатировать дату. (Проверяет: Java Date/Time API)
* **Задание:** Используя `java.time` API, напишите метод, который принимает две строки с датами в формате "
  yyyy-MM-dd" и возвращает количество дней между ними.
* **Пример:** `daysBetween("2025-04-01", "2025-04-10")` -> `9`.
* **Решение:** [
  `DateTimeManipulation`](src/main/java/com/svedentsov/aqa/tasks/files_io_formats/DateTimeManipulation.java)

[К оглавлению](#table-of-contents)

---

<a name="task-79"></a>

### Написать простой класс для работы с дробями (Fraction Class)

* **Описание:** Сложение, вычитание, упрощение. (Проверяет: ООП, арифметика, НОД)
* **Задание:** Реализуйте класс `Fraction` с полями числитель (`numerator`) и знаменатель (`denominator`). Добавьте
  конструктор, метод `toString()` (например, "2/3"), и метод `add(Fraction other)` для сложения дробей с приведением
  к общему знаменателю и упрощением результата (используя НОД).
* **Пример:** `new Fraction(1, 2).add(new Fraction(1, 3))` должен вернуть объект `Fraction`, представляющий `5/6`.
* **Решение:** [`FractionClass`](src/main/java/com/svedentsov/aqa/tasks/oop_design/FractionClass.java)

[К оглавлению](#table-of-contents)

---

<a name="task-80"></a>

### Регулярное выражение для поиска/валидации (Simple Regex)

* **Описание:** Написать простое регулярное выражение (например, для поиска телефонного номера). (Проверяет: основы
  регулярных выражений)
* **Задание:** Напишите метод `boolean isValidPhoneNumber(String number)`, который использует регулярное выражение
  для проверки, соответствует ли строка `number` простому формату телефонного номера, например, `+7-XXX-XXX-XX-XX` (
  где X - цифра).
* **Пример:** `isValidPhoneNumber("+7-911-123-45-67")` -> `true`. `isValidPhoneNumber("89111234567")` -> `false`.
* **Решение:** [`SimpleRegex`](src/main/java/com/svedentsov/aqa/tasks/strings/SimpleRegex.java)

[К оглавлению](#table-of-contents)

---

<a name="task-81"></a>

### Поиск слов в матрице символов (Word Search)

* **Описание:** Найти слово в 2D сетке букв (горизонтально/вертикально). (Проверяет: обход матриц, рекурсия/DFS)
* **Задание:** Напишите метод `boolean exist(char[][] board, String word)`, который проверяет, можно ли найти слово
  `word` в 2D матрице символов `board`, двигаясь по горизонтали или вертикали к соседним клеткам. Один и тот же
  символ нельзя использовать дважды в одном слове.
* **Пример:** `board = {{'A','B','C','E'}, {'S','F','C','S'}, {'A','D','E','E'}}, word = "ABCCED"` -> `true`.
  `word = "SEE"` -> `true`. `word = "ABCB"` -> `false`.
* **Решение:** [`WordSearch`](src/main/java/com/svedentsov/aqa/tasks/graphs_matrices/WordSearch.java)

[К оглавлению](#table-of-contents)

---

<a name="task-82"></a>

### Счастливый билет (Lucky Ticket)

* **Описание:** Проверить, равен ли сумме первых N/2 цифр сумме последних N/2 цифр числа. (Проверяет: работа со
  строками/числами, арифметика)
* **Задание:** Напишите метод `boolean isLuckyTicket(String ticketNumber)`, который проверяет, является ли строка
  `ticketNumber`, состоящая из четного количества цифр (например, 6), "счастливым билетом" (сумма первой половины
  цифр равна сумме второй половины).
* **Пример:** `isLuckyTicket("123402")` -> `true` (1+2+3 = 6, 4+0+2 = 6). `isLuckyTicket("123456")` -> `false`.
* **Решение:** [`LuckyTicket`](src/main/java/com/svedentsov/aqa/tasks/algorithms/LuckyTicket.java)

[К оглавлению](#table-of-contents)

---

<a name="task-83"></a>

### Морской бой (Battleship - проверка попадания)

* **Описание:** Дана матрица поля, проверить корректность выстрела/попадания. (Проверяет: работа с 2D массивами,
  условия)
* **Задание:** Дана 2D матрица `int[][] field`, представляющая поле для игры в морской бой (0 - пусто, 1 - часть
  корабля). Напишите метод `String checkShot(int x, int y)` который возвращает "Miss" если `field[x][y] == 0`, и "
  Hit" если `field[x][y] == 1`. (Можно усложнить до "Sunk", если это был последний целый сегмент корабля, но это
  выходит за рамки базовой задачи).
* **Пример:** `field = {{0, 1, 0}, {0, 1, 0}, {0, 0, 0}}`. `checkShot(0, 1)` -> `"Hit"`. `checkShot(0, 0)` ->
  `"Miss"`.
* **Решение:** [
  `BattleshipCheckShot`](src/main/java/com/svedentsov/aqa/tasks/graphs_matrices/BattleshipCheckShot.java)

[К оглавлению](#table-of-contents)

---

<a name="task-84"></a>

### Проверка валидности судоку (Validate Sudoku - часть)

* **Описание:** Проверить одну строку/колонку/квадрат на уникальность цифр 1-9. (Проверяет: Set, работа с 2D массивами)
* **Задание:** Напишите метод `boolean isSudokuRowValid(char[][] board, int row)`, который проверяет, содержит ли
  указанная строка `row` в 9x9 поле `board` (представленном `char[][]`, где '.' - пустая клетка, '1'-'9' - цифры)
  только уникальные цифры от '1' до '9' (пустые клетки игнорируются).
* **Пример:** Если `board[row]` содержит `['5','3','.','.','7','.','.','.','.']`, метод вернет `true`. Если
  `['5','3','.','.','7','.','.','3','.']`, метод вернет `false`.
* **Решение:** [
  `ValidateSudokuPart`](src/main/java/com/svedentsov/aqa/tasks/graphs_matrices/ValidateSudokuPart.java)

[К оглавлению](#table-of-contents)

---

<a name="task-85"></a>

### Реализация `Optional` (концептуально)

* **Описание:** Объяснить идею `Optional` и как его использовать. (Проверяет: знание современных возможностей Java)
* **Задание:** Объясните своими словами, зачем в Java был введен класс `Optional<T>`. Приведите примеры
  использования `Optional.ofNullable()`, `orElse()`, `isPresent()`, `ifPresent()`, чтобы избежать
  `NullPointerException`.
* **Пример:** Показать код до и после использования `Optional` для работы с потенциально `null` объектом.
* **Решение (Концептуальное обсуждение + Демонстрация):** [
  `OptionalConcept`](src/main/java/com/svedentsov/aqa/tasks/system_concepts/OptionalConcept.java)

[К оглавлению](#table-of-contents)

---
<a name="task-86"></a>

### Работа с потоками (Java Streams - простое использование)

* **Описание:** Отфильтровать список, преобразовать элементы, посчитать сумму с помощью Stream API. (Проверяет: основы
  Stream API)
* **Задание:** Дан `List<String> strings = List.of("apple", "banana", "apricot", "cherry", "kiwi");`. Используя
  Stream API, напишите код, который отфильтрует строки, начинающиеся на "a", преобразует их в верхний регистр, и
  соберет результат в новый `List<String>`.
* **Пример:** Результат должен быть `["APPLE", "APRICOT"]`.
* **Решение:** [`JavaStreamsSimple`](src/main/java/com/svedentsov/aqa/tasks/algorithms/JavaStreamsSimple.java)

[К оглавлению](#table-of-contents)

---

<a name="task-87"></a>

### Создать Immutable класс (Create Immutable Class)

* **Описание:** Написать класс, состояние которого нельзя изменить после создания. (Проверяет: ООП, `final`)
* **Задание:** Напишите класс `ImmutablePoint` с приватными `final` полями `x` и `y` типа `int`. Сделайте класс
  `final`, предоставьте только геттеры и конструктор, который инициализирует поля. Объясните, почему этот класс
  является неизменяемым.
* **Пример:** После `ImmutablePoint p = new ImmutablePoint(1, 2);`, состояние `p` изменить нельзя.
* **Решение:** [`ImmutableClass`](src/main/java/com/svedentsov/aqa/tasks/oop_design/ImmutableClass.java)

[К оглавлению](#table-of-contents)

---

<a name="task-88"></a>

### Глубокое копирование объекта (Deep Copy vs Shallow Copy)

* **Описание:** Объяснить разницу и как реализовать (концептуально). (Проверяет: ООП, управление памятью)
* **Задание:** Объясните разницу между поверхностным (shallow) и глубоким (deep) копированием объектов в Java.
  Приведите пример класса с изменяемым полем-объектом и покажите, как реализовать для него глубокое копирование (
  например, через копирующий конструктор или метод `clone()`).
* **Пример:** Класс `UserProfile` с полем `List<String> permissions`. Показать, как при копировании `UserProfile`
  создать и новый `ArrayList` для `permissions`.
* **Решение (Концептуальное обсуждение + Демонстрация):** [
  `DeepShallowCopyConcept`](src/main/java/com/svedentsov/aqa/tasks/oop_design/DeepShallowCopyConcept.java)

[К оглавлению](#table-of-contents)

---

<a name="task-89"></a>

### Реализовать простой кеш в памяти (Simple In-Memory Cache)

* **Описание:** С использованием `Map`. (Проверяет: Map, основы кеширования)
* **Задание:** Реализуйте простой класс `SimpleCache` с использованием `HashMap`, который имеет методы
  `put(String key, Object value)` и `get(String key)`. Добавьте базовую логику очистки старых записей (например, при
  достижении определенного размера кеша удалять случайный элемент, или самый старый по времени добавления -
  потребует доп. структуру).
* **Пример:** `cache.put("user:1", userData); Object data = cache.get("user:1");`
* **Решение:** [
  `SimpleInMemoryCache`](src/main/java/com/svedentsov/aqa/tasks/data_structures/SimpleInMemoryCache.java)

[К оглавлению](#table-of-contents)

---

<a name="task-90"></a>

### Игра "Жизнь" (Game of Life - один шаг)

* **Описание:** Рассчитать следующее состояние для одной клетки или небольшой матрицы. (Проверяет: работа с 2D
  массивами, логика)
* **Задание:** Напишите метод `int[][] nextGeneration(int[][] board)`, который принимает 2D массив `board` (0 -
  мертвая клетка, 1 - живая) и вычисляет следующее состояние поля согласно правилам игры "Жизнь" Конвея (клетка
  выживает с 2-3 соседями, мертвая оживает с 3 соседями). Верните новое поле.
* **Пример:** Показать небольшой пример 3x3 поля и его состояние на следующем шаге.
* **Решение:** [`GameOfLifeStep`](src/main/java/com/svedentsov/aqa/tasks/graphs_matrices/GameOfLifeStep.java)

[К оглавлению](#table-of-contents)

---

<a name="task-91"></a>

### Подсчет дождевой воды (Trapping Rain Water - упрощенно)

* **Описание:** Найти количество воды между "столбиками" гистограммы. (Проверяет: алгоритмы, два указателя/динамика)
* **Задание:** Дан массив неотрицательных чисел `int[] height`, представляющий высоты столбцов гистограммы (ширина
  каждого столбика 1). Напишите метод `int trapRainWater(int[] height)`, который вычисляет, сколько единиц дождевой
  воды может быть удержано между столбцами после дождя.
* **Пример:** `trapRainWater(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1})` -> `6`.
* **Решение:** [`TrappingRainWater`](src/main/java/com/svedentsov/aqa/tasks/arrays_lists/TrappingRainWater.java)

[К оглавлению](#table-of-contents)

---

<a name="task-92"></a>

### Найти медиану потока чисел (Find Median from Data Stream - концептуально)

* **Описание:** Обсудить использование двух куч (heaps). (Проверяет: структуры данных - PriorityQueue/Heap)
* **Задание:** Обсудите, как спроектировать структуру данных, которая поддерживает два метода: `addNum(int num)` (
  добавляет число из потока данных) и `double findMedian()` (возвращает медиану всех добавленных на данный момент
  чисел). Какую структуру данных (или комбинацию) было бы эффективно использовать?
* **Пример:** Обсуждение использования двух куч (min-heap и max-heap).
* **Решение (Концептуальное обсуждение + Код Класса):** [
  `MedianFinderStream`](src/main/java/com/svedentsov/aqa/tasks/data_structures/MedianFinderStream.java)

[К оглавлению](#table-of-contents)

---

<a name="task-93"></a>

### Проверить, является ли дерево бинарным деревом поиска (Validate BST)

* **Описание:** (Проверяет: рекурсия, деревья)
* **Задание:** Напишите метод `boolean isValidBST(TreeNode root)`, который проверяет, является ли данное бинарное
  дерево с корнем `root` корректным бинарным деревом поиска (для каждого узла все значения в левом поддереве меньше
  значения узла, а в правом - больше).
* **Пример:** Дерево `[2, 1, 3]` -> `true`. Дерево `[5, 1, 4, null, null, 3, 6]` -> `false`.
* **Решение:** [`ValidateBST`](src/main/java/com/svedentsov/aqa/tasks/trees/ValidateBST.java)

[К оглавлению](#table-of-contents)

---

<a name="task-94"></a>

### Уровень узла в бинарном дереве (Level of a Node in Binary Tree)

* **Описание:** (Проверяет: рекурсия/BFS, деревья)
* **Задание:** Напишите метод `int getNodeLevel(TreeNode root, int key, int currentLevel)`, который находит и
  возвращает уровень узла со значением `key` в бинарном дереве (корень на уровне 1). Если узел не найден, верните
  -1.
* **Пример:** В дереве `[3, 9, 20, null, null, 15, 7]`, `getNodeLevel(root, 15, 1)` -> `3`.
* **Решение:** [`NodeLevelInTree`](src/main/java/com/svedentsov/aqa/tasks/trees/NodeLevelInTree.java)

[К оглавлению](#table-of-contents)

---

<a name="task-95"></a>

### Наименьший общий предок в BST/Binary Tree (Lowest Common Ancestor)

* **Описание:** (Проверяет: рекурсия, деревья)
* **Задание:** Напишите метод `TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q)`, который
  находит наименьшего общего предка (LCA) двух узлов `p` и `q` в бинарном дереве поиска `root`.
* **Пример:** Для BST `[6, 2, 8, 0, 4, 7, 9, null, null, 3, 5]`, LCA для узлов 2 и 8 будет 6. LCA для узлов 2 и 4
  будет 2.
* **Решение:** [`LowestCommonAncestor`](src/main/java/com/svedentsov/aqa/tasks/trees/LowestCommonAncestor.java)

[К оглавлению](#table-of-contents)

---
<a name="task-96"></a>

### Диаметр бинарного дерева (Diameter of Binary Tree)

* **Описание:** (Проверяет: рекурсия, деревья)
* **Задание:** Напишите метод `int diameterOfBinaryTree(TreeNode root)`, который вычисляет диаметр бинарного
  дерева (длину самого длинного пути между любыми двумя узлами в дереве). Путь не обязательно проходит через корень.
  Длина пути измеряется количеством ребер.
* **Пример:** Для дерева `[1, 2, 3, 4, 5]`, диаметр равен 3 (путь `4 -> 2 -> 1 -> 3` или `5 -> 2 -> 1 -> 3`).
* **Решение:** [`BinaryTreeDiameter`](src/main/java/com/svedentsov/aqa/tasks/trees/BinaryTreeDiameter.java)

[К оглавлению](#table-of-contents)

---

<a name="task-97"></a>

### Проверка на симметричность дерева (Symmetric Tree Check)

* **Описание:** (Проверяет: рекурсия, деревья)
* **Задание:** Напишите метод `boolean isSymmetric(TreeNode root)`, который проверяет, является ли бинарное дерево
  зеркально симметричным относительно своего центра.
* **Пример:** Дерево `[1, 2, 2, 3, 4, 4, 3]` -> `true`. Дерево `[1, 2, 2, null, 3, null, 3]` -> `false`.
* **Решение:** [`SymmetricTreeCheck`](src/main/java/com/svedentsov/aqa/tasks/trees/SymmetricTreeCheck.java)

[К оглавлению](#table-of-contents)

---

<a name="task-98"></a>

### Поиск пути между двумя узлами графа (Find Path in Graph - BFS/DFS)

* **Описание:** (Проверяет: основы графов, BFS/DFS)
* **Задание:** Дан граф, представленный списком смежности (`Map<Integer, List<Integer>> adjList`). Напишите метод
  `boolean hasPath(int startNode, int endNode, Map<Integer, List<Integer>> adjList)`, который проверяет, существует
  ли путь от `startNode` до `endNode`. Используйте поиск в ширину (BFS) или в глубину (DFS).
* **Пример:** Для графа `{0:[1, 2], 1:[2], 2:[0, 3], 3:[3]}`, `hasPath(0, 3, graph)` -> `true`.
  `hasPath(3, 0, graph)` -> `false`.
* **Решение:** [`GraphPathFinder`](src/main/java/com/svedentsov/aqa/tasks/graphs_matrices/GraphPathFinder.java)

[К оглавлению](#table-of-contents)

---

<a name="task-99"></a>

### Заливка (Flood Fill)

* **Описание:** Заменить цвет в связанной области матрицы. (Проверяет: рекурсия/DFS/BFS, работа с матрицами)
* **Задание:** Напишите метод `int[][] floodFill(int[][] image, int sr, int sc, int newColor)`, который выполняет "
  заливку" в 2D массиве `image`, начиная с точки (`sr`, `sc`). Все смежные клетки (4 направления), имеющие тот же
  цвет, что и стартовая клетка, должны быть перекрашены в `newColor`.
* **Пример:** `image = {{1,1,1},{1,1,0},{1,0,1}}, sr = 1, sc = 1, newColor = 2`. Результат:
  `{{2,2,2},{2,2,0},{2,0,1}}`.
* **Решение:** [`FloodFill`](src/main/java/com/svedentsov/aqa/tasks/graphs_matrices/FloodFill.java)

[К оглавлению](#table-of-contents)

---

<a name="task-100"></a>

### Вычисление математического выражения в строке (Evaluate Expression String - простое)

* **Описание:** Например, "2+3\*4". (Проверяет: Stack, парсинг строк, приоритет операций)
* **Задание:** Напишите метод `int evaluateExpression(String expression)`, который вычисляет значение простого
  арифметического выражения, заданного строкой. Выражение содержит только неотрицательные целые числа, операторы
  `+`, `-`, `*`, `/` и пробелы. Учитывайте стандартный приоритет операций (`*`, `/` перед `+`, `-`). Целочисленное
  деление должно отбрасывать дробную часть.
* **Пример:** `evaluateExpression("3 + 5 * 2")` -> `13`. `evaluateExpression(" 10 - 4 / 2 ")` -> `8`.
  `evaluateExpression("2*3+5/6*3+15")` -> `21`. (Упрощенная версия может не требовать полного парсера с учетом
  скобок).
* **Решение:** [
  `SimpleExpressionEvaluator`](src/main/java/com/svedentsov/aqa/tasks/algorithms/SimpleExpressionEvaluator.java)

[К оглавлению](#table-of-contents)
