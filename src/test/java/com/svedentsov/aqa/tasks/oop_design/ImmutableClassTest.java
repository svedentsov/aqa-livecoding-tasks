package com.svedentsov.aqa.tasks.oop_design;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для проверки неизменяемости (immutability) классов.
 */
class ImmutableClassTest {

    @Test
    @DisplayName("ImmutablePointSimple: должен сохранять состояние после создания")
    void testImmutablePointSimpleStateIsUnchanged() {
        // Создаем экземпляр с начальными значениями
        ImmutableClass.ImmutablePointSimple point = new ImmutableClass.ImmutablePointSimple(10, 20);

        // Проверяем, что геттеры возвращают правильные значения
        // assertAll позволяет сгруппировать несколько проверок. Тест провалится, если хотя бы одна из них не пройдет.
        assertAll("Проверка состояния ImmutablePointSimple",
                () -> assertEquals(10, point.getX(), "Координата X должна быть 10"),
                () -> assertEquals(20, point.getY(), "Координата Y должна быть 20")
        );
        // Попытка изменить состояние напрямую невозможна из-за final полей и отсутствия сеттеров (проверяется на этапе компиляции).
    }

    @Test
    @DisplayName("ImmutableUserProfile: должен быть невосприимчив к изменению исходного списка")
    void testUserProfileIsUnaffectedByOriginalListModification() {
        // 1. Создаем изменяемый список
        List<String> initialPermissions = new ArrayList<>();
        initialPermissions.add("READ");
        initialPermissions.add("WRITE");

        // 2. Создаем неизменяемый объект, передавая ему этот список
        ImmutableClass.ImmutableUserProfile userProfile = new ImmutableClass.ImmutableUserProfile("testUser", initialPermissions);

        // 3. Изменяем исходный список ПОСЛЕ создания объекта
        initialPermissions.add("DELETE");

        // 4. Проверяем, что состояние объекта не изменилось
        List<String> expectedPermissions = List.of("READ", "WRITE");
        assertEquals(expectedPermissions, userProfile.getPermissions(),
                "Внутренний список не должен изменяться после модификации внешнего списка (защитное копирование).");
        assertNotEquals(initialPermissions, userProfile.getPermissions(),
                "Внутренний список должен быть отдельной копией, а не ссылкой на исходный.");
    }

    @Test
    @DisplayName("ImmutableUserProfile: геттер должен возвращать неизменяемый список")
    void testUserProfileGetterReturnsUnmodifiableList() {
        // 1. Создаем объект с какими-либо данными
        ImmutableClass.ImmutableUserProfile userProfile = new ImmutableClass.ImmutableUserProfile(
                "testUser",
                List.of("READ_ONLY")
        );

        // 2. Получаем список из объекта
        List<String> permissionsFromGetter = userProfile.getPermissions();

        // 3. Проверяем, что любая попытка изменить этот список приводит к ошибке
        assertThrows(UnsupportedOperationException.class, () -> {
            permissionsFromGetter.add("WRITE_ACCESS");
        }, "Попытка модификации списка из геттера должна вызывать UnsupportedOperationException.");
    }
}
