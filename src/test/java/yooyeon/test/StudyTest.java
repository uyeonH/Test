package yooyeon.test;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;

import java.time.Duration;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Tag("integration")
class StudyTest {

    @Test
    @SlowTest
    void tagSlow() {

    }

    @Test
    @FastTest
    void tagFast() {

    }

    @Test
    @Tag("feature2")
    void tag() {
        // Intellij Edit Configuration 에서 태그 필터링 가능
    }


    @Test
    @EnabledOnOs({OS.MAC, OS.WINDOWS})
    @DisabledOnOs(OS.LINUX)
    @EnabledOnJre({JRE.JAVA_8, JRE.JAVA_9, JRE.JAVA_10, JRE.JAVA_11})
    void condition2() {
        // OS 환경 제한
        // JRE 버전 제한
    }

    @Test
        //@EnabledIfEnvironmentVariable(named = "TEST_ENV_STRING", matches = "Test")
    void condition() {
        // 조건을 만족하는 경우 테스트를 진행하고 싶음

        // 시스템 환경변수에 TEST_ENV_STRING: Test인 경우우
        String test_env = System.getenv("TEST_ENV_STRING");

        System.out.println("test_env = " + test_env);
        assumeTrue("Test".equalsIgnoreCase(test_env));

        assumingThat("Test".equalsIgnoreCase(test_env), () -> {
            Study actual = new Study(10);
            assertThat(actual.getLimit()).isGreaterThan(0);
        });

    }

    @Test
    void exception() {
        // 특정 예외가 발생하는지 확인
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        String message = exception.getMessage();
        assertEquals("limit은 0보다 커야함", message);
    }

    @Test
    void time_out() {

        /*
        assertTimeout(Duration.ofMillis(100),()->
        {
            new Study(10);
            Thread.sleep(1000);
        });
        */

        // 실행이 완료될 때까지 기다리지 않고 100ms를 넘으면 실패하게 하고싶다면
        // 단, ThreadLocal을 사용하는 코드에서는 사용하지 말것
        assertTimeoutPreemptively(Duration.ofMillis(100), () ->
        {
            new Study(10);
            Thread.sleep(10);
        });

    }

    @Test
    @DisplayName("스터디 생성")
    void create_new_study() {
        Study study = new Study(10);
        assertAll( // 실패 테스트가 있어도 각각 성공/실패 확인 가능
                () -> assertNotNull(study),
                // expected, actual, msg
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(),
                        () -> "스터디를 처음 만들면 DRAFT 상태다"),
                () -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 인원은 0보다 커야한다")
        );

    }

    @Test
    @Disabled
    void create2() {
        Study study = new Study();
        assertNotNull(study);
        System.out.println("StudyTest.create2");
    }

    @BeforeAll
    static void beforeAll() { // static void로 작성
        System.out.println("StudyTest.beforeAll");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("StudyTest.afterAll");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("StudyTest.beforeEach");
    }

    @AfterEach
    void afterEach() {
        System.out.println("StudyTest.afterEach");
    }
}