package yooyeon.test;

import org.junit.jupiter.api.*;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

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

        // 특정 예외가 발생하는지 확인
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        String message = exception.getMessage();
        assertEquals("limit은 0보다 커야함", message);
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