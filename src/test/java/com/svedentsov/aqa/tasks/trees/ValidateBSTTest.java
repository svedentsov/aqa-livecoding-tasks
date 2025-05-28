package com.svedentsov.aqa.tasks.trees;

import com.svedentsov.aqa.tasks.trees.ValidateBST.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты для ValidateBST (Проверка бинарного дерева поиска)")
class ValidateBSTTest {

    private ValidateBST validator;

    @BeforeEach
    void setUp() {
        validator = new ValidateBST();
    }

    // Источник данных для тестов
    static Stream<Arguments> bstTestCases() {
        return Stream.of(
                // Название теста, дерево, ожидаемый результат
                Arguments.of("Простое валидное BST [2,1,3]",
                        new TreeNode(2, new TreeNode(1), new TreeNode(3)),
                        true),
                Arguments.of("Невалидное BST [5,1,4,null,null,3,6]",
                        new TreeNode(5,
                                new TreeNode(1),
                                new TreeNode(4, new TreeNode(3), new TreeNode(6))),
                        false), // Нарушение: 3 < 4, но 3 < 5, а находится в "правой" части от 4, которая должна быть > 4. И 6 > 4, но 6 > 5, ок.
                // Ключевое нарушение: узел 3 (значение 3) находится в правом поддереве узла 4, но 3 < 4.
                // Также: узел 3 находится в правом поддереве корня 5, но 3 < 5.
                Arguments.of("Невалидное BST (дубликат слева) [2,2,3]",
                        new TreeNode(2, new TreeNode(2), new TreeNode(3)),
                        false),
                Arguments.of("Невалидное BST (дубликат справа) [2,1,2]",
                        new TreeNode(2, new TreeNode(1), new TreeNode(2)),
                        false),
                Arguments.of("Пустое дерево (null)",
                        null,
                        true),
                Arguments.of("Дерево из одного узла",
                        new TreeNode(10),
                        true),
                Arguments.of("Сложное валидное BST",
                        new TreeNode(10,
                                new TreeNode(5, new TreeNode(3), new TreeNode(7)),
                                new TreeNode(15, new TreeNode(13), new TreeNode(18))),
                        true),
                Arguments.of("Невалидное (6 < 10, но справа от 10 через 15) [10,5,[15,6,20]]",
                        new TreeNode(10,
                                new TreeNode(5),
                                new TreeNode(15, new TreeNode(6), new TreeNode(20))), // 6 < 15 (ok for left of 15), but 6 must be > 10 (not ok)
                        false),
                Arguments.of("Невалидное (12 > 10, но слева от 10 через 5) [10,[5,null,12],15]",
                        new TreeNode(10,
                                new TreeNode(5, null, new TreeNode(12)), // 12 > 5 (ok for right of 5), but 12 must be < 10 (not ok)
                                new TreeNode(15)),
                        false),
                Arguments.of("Граничное значение MAX_VALUE (один узел)",
                        new TreeNode(Integer.MAX_VALUE),
                        true),
                Arguments.of("Граничное значение MIN_VALUE (один узел)",
                        new TreeNode(Integer.MIN_VALUE),
                        true),
                Arguments.of("Валидное BST с MIN_VALUE и MAX_VALUE детьми",
                        new TreeNode(0, new TreeNode(Integer.MIN_VALUE), new TreeNode(Integer.MAX_VALUE)),
                        true),
                Arguments.of("Невалидное: MAX_VALUE как левый ребенок",
                        new TreeNode(0, new TreeNode(Integer.MAX_VALUE), null),
                        false),
                Arguments.of("Невалидное: MIN_VALUE как правый ребенок",
                        new TreeNode(0, null, new TreeNode(Integer.MIN_VALUE)),
                        false),
                Arguments.of("Цепочка вправо (валидная)",
                        new TreeNode(1, null, new TreeNode(2, null, new TreeNode(3))),
                        true),
                Arguments.of("Цепочка влево (валидная)",
                        new TreeNode(3, new TreeNode(2, new TreeNode(1), null), null),
                        true),
                Arguments.of("Цепочка вправо (невалидная)",
                        new TreeNode(1, null, new TreeNode(3, null, new TreeNode(2))), // 2 < 3, но справа
                        false)
        );
    }

    @Nested
    @DisplayName("Рекурсивный метод: isValidBSTRecursive")
    class RecursiveValidationTests {
        @ParameterizedTest(name = "{0}")
        @MethodSource("com.svedentsov.aqa.tasks.trees.ValidateBSTTest#bstTestCases")
        void testIsValidBSTRecursive(String testName, TreeNode root, boolean expected) {
            assertEquals(expected, validator.isValidBSTRecursive(root));
        }
    }

    @Nested
    @DisplayName("Итеративный метод: isValidBSTInorder")
    class InorderValidationTests {
        @ParameterizedTest(name = "{0}")
        @MethodSource("com.svedentsov.aqa.tasks.trees.ValidateBSTTest#bstTestCases")
        void testIsValidBSTInorder(String testName, TreeNode root, boolean expected) {
            assertEquals(expected, validator.isValidBSTInorder(root));
        }
    }
}
