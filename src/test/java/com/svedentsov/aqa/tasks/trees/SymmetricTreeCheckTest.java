package com.svedentsov.aqa.tasks.trees;

import com.svedentsov.aqa.tasks.trees.SymmetricTreeCheck.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты для SymmetricTreeCheck (Проверка симметричности дерева)")
class SymmetricTreeCheckTest {

    private SymmetricTreeCheck checker;

    @BeforeEach
    void setUp() {
        checker = new SymmetricTreeCheck();
    }

    // Источник данных для тестов
    static Stream<Arguments> symmetricTreeTestCases() {
        // Пример 1: Симметричное [1,2,2,3,4,4,3]
        TreeNode root1 = new TreeNode(1,
                new TreeNode(2, new TreeNode(3), new TreeNode(4)),
                new TreeNode(2, new TreeNode(4), new TreeNode(3)));

        // Пример 2: Несимметричное [1,2,2,null,3,null,3] -> false
        // (Левый 2: left=null, right=3. Правый 2: left=null, right=3)
        // Должно быть: Левый 2: left=N, right=3. Правый 2: left=3, right=N.
        // В примере из задачи: [1, 2, 2, null, 3, null, 3]
        // left child of root: TreeNode(2, null, new TreeNode(3))
        // right child of root: TreeNode(2, null, new TreeNode(3))
        // isMirror( (2,N,3), (2,N,3) ) ->
        //   isMirror( (2,N,3).left=N, (2,N,3).right=3 ) -> isMirror(N,3) -> false.
        // Да, это несимметрично.
        TreeNode root2 = new TreeNode(1,
                new TreeNode(2, null, new TreeNode(3)),
                new TreeNode(2, null, new TreeNode(3)));


        // Пример 3: Пустое дерево
        TreeNode root3 = null;

        // Пример 4: Один узел
        TreeNode root4 = new TreeNode(1);

        // Пример 5: Асимметрия по значению детей корня
        TreeNode root5 = new TreeNode(1, new TreeNode(2), new TreeNode(3));

        // Пример 6: Асимметрия по структуре (из main)
        // [1, [2,3,N], [2,3,N]]
        TreeNode root6 = new TreeNode(1,
                new TreeNode(2, new TreeNode(3), null),
                new TreeNode(2, new TreeNode(3), null));


        // Дополнительные случаи
        // Глубокое симметричное дерево
        TreeNode deepSymLeft = new TreeNode(2,
                new TreeNode(3, new TreeNode(5), new TreeNode(6)),
                new TreeNode(4, new TreeNode(7), new TreeNode(8)));
        TreeNode deepSymRight = new TreeNode(2,
                new TreeNode(4, new TreeNode(8), new TreeNode(7)),
                new TreeNode(3, new TreeNode(6), new TreeNode(5)));
        TreeNode root7_deepSymmetric = new TreeNode(1, deepSymLeft, deepSymRight);

        // Глубокое асимметричное дерево (структура)
        TreeNode deepAsymLeft_struct = new TreeNode(2,
                new TreeNode(3, new TreeNode(5), null), // Отличие здесь
                new TreeNode(4));
        TreeNode deepAsymRight_struct = new TreeNode(2,
                new TreeNode(4),
                new TreeNode(3, new TreeNode(5), null));// И здесь
        TreeNode root8_deepAsymmetric_structure = new TreeNode(1, deepAsymLeft_struct, deepAsymRight_struct);


        // Глубокое асимметричное дерево (значения)
        TreeNode deepAsymLeft_val = new TreeNode(2, new TreeNode(3), new TreeNode(4));
        TreeNode deepAsymRight_val = new TreeNode(2, new TreeNode(4), new TreeNode(99)); // Отличие здесь
        TreeNode root9_deepAsymmetric_value = new TreeNode(1, deepAsymLeft_val, deepAsymRight_val);


        return Stream.of(
                Arguments.of("Симметричное дерево [1,2,2,3,4,4,3]", root1, true),
                Arguments.of("Несимметричное дерево [1,2,2,N,3,N,3]", root2, false),
                Arguments.of("Пустое дерево (null)", root3, true),
                Arguments.of("Дерево из одного узла [1]", root4, true),
                Arguments.of("Асимметрия по значению детей [1,2,3]", root5, false),
                Arguments.of("Асимметрия по структуре внуков [1,[2,3,N],[2,3,N]]", root6, false),
                Arguments.of("Глубокое симметричное дерево", root7_deepSymmetric, true),
                Arguments.of("Глубокое асимметричное (структура)", root8_deepAsymmetric_structure, false),
                Arguments.of("Глубокое асимметричное (значение)", root9_deepAsymmetric_value, false),
                Arguments.of("Асимметричное: левый ребенок есть, правого нет", new TreeNode(1, new TreeNode(2), null), false),
                Arguments.of("Асимметричное: правый ребенок есть, левого нет", new TreeNode(1, null, new TreeNode(2)), false)
        );
    }

    @Nested
    @DisplayName("Рекурсивный метод: isSymmetricRecursive")
    class RecursiveTests {
        @ParameterizedTest(name = "{0}")
        @MethodSource("com.svedentsov.aqa.tasks.trees.SymmetricTreeCheckTest#symmetricTreeTestCases")
        void testIsSymmetricRecursive(String testName, TreeNode root, boolean expected) {
            assertEquals(expected, checker.isSymmetricRecursive(root), "Recursive: " + testName);
        }
    }

    @Nested
    @DisplayName("Итеративный метод: isSymmetricIterative")
    class IterativeTests {
        @ParameterizedTest(name = "{0}")
        @MethodSource("com.svedentsov.aqa.tasks.trees.SymmetricTreeCheckTest#symmetricTreeTestCases")
        void testIsSymmetricIterative(String testName, TreeNode root, boolean expected) {
            assertEquals(expected, checker.isSymmetricIterative(root), "Iterative: " + testName);
        }
    }
}
