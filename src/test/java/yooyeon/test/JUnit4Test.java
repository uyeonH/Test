package yooyeon.test;

import org.junit.Before;
import org.junit.Test;

public class JUnit4Test {
    @Before
    public void before() {
        System.out.println("JUnit4Test.before");
    }

    @Test
    public void createTest() {
        System.out.println("JUnit4Test.createTest");
    }
}
