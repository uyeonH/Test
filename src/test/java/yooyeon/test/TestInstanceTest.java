package yooyeon.test;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestInstanceTest {
    // 각 테스트는 독립적이고 순서에 영향 받으면 안된다.
    // 때에 따라 필요한 경우가 있는데 (ex. 시나리오 테스트)
    // TestInstance 설정을 하면 된다.

    int i = 0;

    @Order(1)
    @Test
    void test_1() {
        System.out.println(i++); // 0
    }

    @Order(2)
    @Test
    void test_2() {
        System.out.println(i++); // 1
    }

    @Order(3)
    @Test
    void test_3() {
        System.out.println(i++); // 2
    }

    @BeforeAll
    void beforeAll() { // static void로 작성
        System.out.println("TestInstanceTest.beforeAll");
    }

    @AfterAll
    void afterAll() {
        System.out.println("TestInstanceTest.afterAll");
    }
}
