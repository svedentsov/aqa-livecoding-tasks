package com.svedentsov.aqa.tasks.graphs_matrices; // или algorithms

import java.util.*;

/**
 * Решение задачи №98: Поиск пути между двумя узлами графа (BFS/DFS).
 */
public class Task98_GraphPathFinder {

    /**
     * Проверяет существование пути от узла {@code startNode} к узлу {@code endNode}
     * в графе, представленном списком смежности {@code adjList}.
     * Использует итеративный Поиск в Ширину (BFS), который находит путь, если он существует
     * (и обычно находит кратчайший путь по количеству ребер).
     *
     * @param startNode Начальный узел поиска пути.
     * @param endNode   Целевой узел поиска пути.
     * @param adjList   Список смежности графа. Представляет собой {@code Map}, где ключ - это
     *                  номер узла (Integer), а значение - список ({@code List<Integer>}) его соседей.
     * @return {@code true}, если путь от {@code startNode} к {@code endNode} существует,
     * {@code false} в противном случае (включая случаи, когда узлы не существуют в графе
     * или граф равен null).
     */
    public boolean hasPathBFS(int startNode, int endNode, Map<Integer, List<Integer>> adjList) {
        // 1. Проверка базовых случаев и входных данных
        if (adjList == null) {
            System.err.println("Ошибка: Список смежности не может быть null.");
            return false;
        }
        // Проверяем, существуют ли узлы как ключи в карте. Это важно, если узел
        // может быть изолированным или конечным без исходящих ребер.
        if (!adjList.containsKey(startNode) || !adjList.containsKey(endNode)) {
            System.err.println("Предупреждение: Начальный (" + startNode + ") или конечный (" + endNode + ") узел отсутствует в ключах графа.");
            // Если узла нет, пути до него/от него точно нет (кроме случая start==end)
            return startNode == endNode && adjList.containsKey(startNode); // Путь к себе есть, только если узел существует
        }
        // Если начальный и конечный узлы совпадают, путь есть.
        if (startNode == endNode) {
            return true;
        }

        // 2. Инициализация структур для BFS
        Queue<Integer> queue = new LinkedList<>(); // Очередь для узлов к посещению
        Set<Integer> visited = new HashSet<>();    // Множество для отслеживания посещенных узлов

        // 3. Начинаем BFS с начального узла
        queue.offer(startNode);
        visited.add(startNode);

        // 4. Цикл BFS пока очередь не пуста
        while (!queue.isEmpty()) {
            int currentNode = queue.poll(); // Извлекаем следующий узел из очереди

            // 5. Получаем соседей текущего узла (обрабатываем случай отсутствия соседей)
            List<Integer> neighbors = adjList.getOrDefault(currentNode, Collections.emptyList());

            // 6. Обрабатываем каждого соседа
            for (int neighbor : neighbors) {
                // 6.1 Если сосед - это искомый конечный узел, путь найден!
                if (neighbor == endNode) {
                    return true;
                }
                // 6.2 Если сосед еще не был посещен
                if (visited.add(neighbor)) { // Метод add() возвращает true, если элемент был успешно добавлен (т.е. его не было)
                    queue.offer(neighbor); // Добавляем соседа в очередь для дальнейшего обхода
                }
            }
        }

        // 7. Если очередь опустела, а конечный узел не был достигнут, значит пути нет.
        return false;
    }

    /**
     * Проверяет существование пути от узла {@code startNode} к узлу {@code endNode} в графе,
     * используя рекурсивный Поиск в Глубину (DFS).
     *
     * @param startNode Начальный узел.
     * @param endNode   Конечный узел.
     * @param adjList   Список смежности графа.
     * @return {@code true}, если путь существует, {@code false} в противном случае.
     */
    public boolean hasPathDFS(int startNode, int endNode, Map<Integer, List<Integer>> adjList) {
        if (adjList == null) return false;
        if (!adjList.containsKey(startNode) || !adjList.containsKey(endNode)) {
            System.err.println("Предупреждение: Начальный (" + startNode + ") или конечный (" + endNode + ") узел отсутствует в ключах графа.");
            return startNode == endNode && adjList.containsKey(startNode);
        }
        Set<Integer> visited = new HashSet<>(); // Множество посещенных узлов для текущего DFS
        // Запускаем рекурсивный поиск
        return dfsRecursive(startNode, endNode, adjList, visited);
    }

    /**
     * Рекурсивный вспомогательный метод для DFS.
     *
     * @param currentNode Текущий узел, с которого продолжаем поиск.
     * @param endNode     Целевой узел.
     * @param adjList     Список смежности.
     * @param visited     Множество посещенных узлов (чтобы избежать циклов).
     * @return {@code true}, если путь до {@code endNode} найден из {@code currentNode}, {@code false} иначе.
     */
    private boolean dfsRecursive(int currentNode, int endNode, Map<Integer, List<Integer>> adjList, Set<Integer> visited) {
        // Базовый случай 1: Если текущий узел - это искомый конечный узел, путь найден.
        if (currentNode == endNode) {
            return true;
        }

        // Отмечаем текущий узел как посещенный в рамках текущего пути поиска
        visited.add(currentNode);

        // Получаем соседей текущего узла
        List<Integer> neighbors = adjList.getOrDefault(currentNode, Collections.emptyList());

        // Рекурсивно обходим всех НЕПОСЕЩЕННЫХ соседей
        for (int neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                // Если рекурсивный вызов для соседа нашел путь, то возвращаем true вверх по стеку вызовов
                if (dfsRecursive(neighbor, endNode, adjList, visited)) {
                    return true;
                }
            }
        }

        // Если прошли всех соседей текущего узла и ни один из путей не привел к endNode,
        // то из этой точки путь не найден. Возвращаем false.
        // Важно: Не удаляем из visited при возврате, чтобы не зациклиться в графах с циклами.
        return false;
    }

    /**
     * Точка входа для демонстрации работы методов поиска пути в графе.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task98_GraphPathFinder sol = new Task98_GraphPathFinder();

        // Создаем граф (представление списком смежности)
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(0, Arrays.asList(1, 2));    // 0 -> 1, 0 -> 2
        graph.put(1, Arrays.asList(2));        // 1 -> 2
        graph.put(2, Arrays.asList(0, 3));    // 2 -> 0, 2 -> 3 (цикл 0-2)
        graph.put(3, Arrays.asList(3));        // 3 -> 3 (петля)
        graph.put(4, Arrays.asList(5));        // 4 -> 5 (другой компонент связности)
        graph.put(5, Collections.emptyList()); // 5 - сток
        graph.put(6, Collections.emptyList()); // 6 - изолированный узел (есть в ключах)

        System.out.println("Граф (Список смежности): " + graph);


        int[][] testCases = {{0, 3}, {3, 0}, {0, 5}, {4, 5}, {5, 4}, {0, 6}, {6, 0}, {0, 0}, {3, 3}, {5, 5}, {6, 6}};
        boolean[] expectedResults = {true, false, false, true, false, false, false, true, true, true, true};

        System.out.println("\n--- BFS Path Finding ---");
        for (int i = 0; i < testCases.length; i++) {
            int start = testCases[i][0];
            int end = testCases[i][1];
            boolean result = sol.hasPathBFS(start, end, graph);
            System.out.println("Path from " + start + " to " + end + "? " + result + " (Expected: " + expectedResults[i] + ")");
            if (result != expectedResults[i]) System.err.println("   Mismatch!");
        }


        System.out.println("\n--- DFS Path Finding ---");
        for (int i = 0; i < testCases.length; i++) {
            int start = testCases[i][0];
            int end = testCases[i][1];
            boolean result = sol.hasPathDFS(start, end, graph);
            System.out.println("Path from " + start + " to " + end + "? " + result + " (Expected: " + expectedResults[i] + ")");
            if (result != expectedResults[i]) System.err.println("   Mismatch!");
        }

        // Тесты с отсутствующими узлами
        System.out.println("\n--- Missing Node Tests ---");
        System.out.println("Path from 0 to 10 (BFS)? " + sol.hasPathBFS(0, 10, graph)); // false
        System.out.println("Path from 10 to 0 (DFS)? " + sol.hasPathDFS(10, 0, graph)); // false
        System.out.println("Path from 10 to 10 (BFS)? " + sol.hasPathBFS(10, 10, graph)); // false
    }
}
