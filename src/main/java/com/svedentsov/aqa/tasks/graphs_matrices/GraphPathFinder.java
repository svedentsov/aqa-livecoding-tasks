package com.svedentsov.aqa.tasks.graphs_matrices;

import java.util.*;

/**
 * Решение задачи №98: Поиск пути между двумя узлами графа (BFS/DFS).
 * Описание: (Проверяет: основы графов, BFS/DFS)
 * Задание: Дан граф, представленный списком смежности
 * (`Map<Integer, List<Integer>> adjList`). Напишите метод
 * `boolean hasPath(int startNode, int endNode, Map<Integer, List<Integer>> adjList)`,
 * который проверяет, существует ли путь от `startNode` до `endNode`.
 * Используйте поиск в ширину (BFS) или в глубину (DFS).
 * Пример: Для графа `{0:[1, 2], 1:[2], 2:[0, 3], 3:[3]}`,
 * `hasPath(0, 3, graph)` -> `true`.
 * `hasPath(3, 0, graph)` -> `false`.
 */
public class GraphPathFinder {

    /**
     * Проверяет существование пути от {@code startNode} к {@code endNode} в графе,
     * используя итеративный Поиск в Ширину (BFS).
     *
     * @param startNode Начальный узел.
     * @param endNode   Целевой узел.
     * @param adjList   Список смежности графа (Ключ: узел, Значение: список соседей).
     * @return {@code true}, если путь существует, {@code false} иначе.
     */
    public boolean hasPathBFS(int startNode, int endNode, Map<Integer, List<Integer>> adjList) {
        // Валидация входа
        if (adjList == null) return false; // Или бросить исключение
        // Если узлы не существуют в графе (как ключи), пути нет (кроме случая start==end)
        if (!adjList.containsKey(startNode) || !adjList.containsKey(endNode)) {
            // Путь к себе существует, только если узел есть в графе
            return startNode == endNode && adjList.containsKey(startNode);
        }
        if (startNode == endNode) return true; // Путь к себе всегда есть

        Queue<Integer> queue = new LinkedList<>(); // Очередь для BFS
        Set<Integer> visited = new HashSet<>();    // Посещенные узлы

        queue.offer(startNode);
        visited.add(startNode);

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();

            // Проверяем соседей текущего узла
            for (int neighbor : adjList.getOrDefault(currentNode, Collections.emptyList())) {
                if (neighbor == endNode) {
                    return true; // Путь найден
                }
                // Если сосед еще не посещен, добавляем в очередь и отмечаем
                if (visited.add(neighbor)) {
                    queue.offer(neighbor);
                }
            }
        }
        // Если очередь опустела, а путь не найден
        return false;
    }

    /**
     * Проверяет существование пути от {@code startNode} к {@code endNode} в графе,
     * используя рекурсивный Поиск в Глубину (DFS).
     *
     * @param startNode Начальный узел.
     * @param endNode   Конечный узел.
     * @param adjList   Список смежности графа.
     * @return {@code true}, если путь существует, {@code false} иначе.
     */
    public boolean hasPathDFS(int startNode, int endNode, Map<Integer, List<Integer>> adjList) {
        if (adjList == null) return false;
        if (!adjList.containsKey(startNode) || !adjList.containsKey(endNode)) {
            return startNode == endNode && adjList.containsKey(startNode);
        }
        // Используем Set для отслеживания посещенных узлов в текущем пути DFS, чтобы избежать циклов
        Set<Integer> visited = new HashSet<>();
        return dfsRecursive(startNode, endNode, adjList, visited);
    }

    /**
     * Точка входа для демонстрации работы методов поиска пути в графе.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        GraphPathFinder sol = new GraphPathFinder();

        // --- Создание Графа ---
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(0, List.of(1, 2));    // 0 -> 1, 2
        graph.put(1, List.of(2));        // 1 -> 2
        graph.put(2, List.of(0, 3));    // 2 -> 0, 3 (цикл 0-2)
        graph.put(3, List.of(3));        // 3 -> 3 (петля)
        graph.put(4, List.of(5));        // 4 -> 5 (другой компонент)
        graph.put(5, Collections.emptyList()); // 5 (сток)
        graph.put(6, Collections.emptyList()); // 6 (изолированный, но есть в ключах)
        // Узел 7 не добавлен в ключи

        System.out.println("--- Graph Path Finding (BFS/DFS) ---");
        System.out.println("Graph (Adjacency List):");
        graph.forEach((node, neighbors) -> System.out.println("  " + node + " -> " + neighbors));

        // --- Тестовые Случаи ---
        int[][] testCases = {
                // start, end, expectedResult (1=true, 0=false)
                {0, 3, 1}, // true
                {3, 0, 0}, // false
                {0, 5, 0}, // false
                {4, 5, 1}, // true
                {5, 4, 0}, // false
                {0, 6, 0}, // false
                {6, 0, 0}, // false
                {0, 0, 1}, // true
                {3, 3, 1}, // true
                {5, 5, 1}, // true
                {6, 6, 1}, // true
                {0, 2, 1}, // true
                {2, 0, 1}, // true
                {1, 3, 1}, // true (1 -> 2 -> 3)
                {0, 7, 0}, // false (узел 7 не существует)
                {7, 0, 0}, // false (узел 7 не существует)
                {7, 7, 0}  // false (узел 7 не существует)
        };

        for (int[] test : testCases) {
            runPathTest(sol, graph, test[0], test[1], test[2] == 1);
        }

        // Тест с null графом
        runPathTest(sol, null, 0, 1, false);
    }

    /**
     * Рекурсивный вспомогательный метод для DFS.
     *
     * @param currentNode Текущий узел.
     * @param endNode     Целевой узел.
     * @param adjList     Список смежности.
     * @param visited     Множество посещенных узлов для текущего поиска.
     * @return {@code true}, если путь найден, {@code false} иначе.
     */
    private boolean dfsRecursive(int currentNode, int endNode, Map<Integer, List<Integer>> adjList, Set<Integer> visited) {
        // Базовый случай 1: Нашли цель
        if (currentNode == endNode) {
            return true;
        }
        // Базовый случай 2: Уже посещали этот узел в текущем пути (обнаружен цикл или повторный визит)
        if (!visited.add(currentNode)) { // add() возвращает false, если элемент уже был
            return false;
        }

        // Рекурсивно обходим соседей
        for (int neighbor : adjList.getOrDefault(currentNode, Collections.emptyList())) {
            if (dfsRecursive(neighbor, endNode, adjList, visited)) {
                return true; // Путь найден вглубь
            }
        }
        // Если ни из одного соседа путь не найден
        // Важно НЕ удалять из visited при возврате из рекурсии,
        // чтобы избежать повторного исследования уже проверенных ветвей.
        return false;
    }

    /**
     * Вспомогательный метод для тестирования поиска пути.
     *
     * @param sol      Экземпляр решателя.
     * @param graph    Список смежности.
     * @param start    Начальный узел.
     * @param end      Конечный узел.
     * @param expected Ожидаемый результат (true/false).
     */
    private static void runPathTest(GraphPathFinder sol, Map<Integer, List<Integer>> graph, int start, int end, boolean expected) {
        System.out.println("\n--- Path from " + start + " to " + end + " ---");
        String expectedStr = String.valueOf(expected);
        try {
            boolean resultBFS = sol.hasPathBFS(start, end, graph);
            System.out.printf("  Result (BFS): %-5b (Expected: %-5b) %s%n",
                    resultBFS, expected, (resultBFS == expected ? "" : "<- MISMATCH!"));
        } catch (Exception e) {
            System.err.println("  Result (BFS): Error - " + e.getMessage());
        }
        try {
            boolean resultDFS = sol.hasPathDFS(start, end, graph);
            System.out.printf("  Result (DFS): %-5b (Expected: %-5b) %s%n",
                    resultDFS, expected, (resultDFS == expected ? "" : "<- MISMATCH!"));
        } catch (Exception e) {
            System.err.println("  Result (DFS): Error - " + e.getMessage());
        }
    }
}
