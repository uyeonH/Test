package yooyeon.test;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class StudyTest {

    @Test
    void create() {
        Study study = new Study();
        assertNotNull(study);
        System.out.println("StudyTest.create");
    }

    @Test
    @Disabled 
    void create2() {
        Study study = new Study();
        assertNotNull(study);
        System.out.println("StudyTest.create2");
    }

    @BeforeAll
    static void beforeAll(){ // static void로 작성
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