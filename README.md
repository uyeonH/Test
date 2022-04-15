# Test

## Assertion

- `assertEquals(expected, actual)` 실제 값이 기대한 값과 같은지 확인

- `assertNotNull(actual)` 값이 null이 아닌지 확인

- `assertTrue(boolean)` 다음 조건이 참(true)인지 확인

- `assertAll(executables...)` 모든 확인 구문 확인

- `assertThrows(expectedType, executable)` 예외 발생 확인
```java
    @Test
    void exception() {
        // 특정 예외가 발생하는지 확인
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        String message = exception.getMessage();
        assertEquals("limit은 0보다 커야함", message);
    }
```

- `assertTimeout(duration, executable)` 특정 시간 안에 실행이 완료되는지 확인


`@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)`

언더바를 빈칸으로 바꾸어 테스트 이름으로 노출

#### 테스트 실행 조건 제약

- 어노테이션 활용
```java
@EnabledOnOs({OS.MAC, OS.WINDOWS})
@DisabledOnOs(OS.LINUX)
@EnabledOnJre({JRE.JAVA_8, JRE.JAVA_9, JRE.JAVA_10, JRE.JAVA_11})
void condition2() {
        // OS 환경 제한
        // JRE 버전 제한
        }
```

- 빌드 환경에서 Tag 설정 시 
build.gradle 파일에 설정 ( 메이븐도 설정 가능 )
```groovy
tasks.named('test') {
    useJUnitPlatform {
        includeTags 'integration | feature2'
        excludeTags 'feature1'
    }
}
```

- 커스텀 태그 만들기 
  - 여러개의 어노테이션을 조합해서 만든 복합 어노테이션
  - composed annotation
  - 반복, 오타를 줄일 수 있음
```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Test
@Tag("fast")
public @interface FastTest {
   
}

```

- 테스트 반복하기
  
  `@RepeatedTest(3)` 테스트 3번 반복 
  
  `@ParameterizedTest` 여러 개의 파라미터에 대해 테스트 가능
  
  `@ValueSource` 리터럴 값의 단일 배열 지정 가능
  
  `@EmptySource` 비어있는 값도 테스트
  
  `@NullSource` Null 값도 테스트

  `@NullAndEmptySource` @EmptySource + @NullSource

```java
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetition}")
    @DisplayName("테스트 반복하기")
    void repeat(RepetitionInfo repetitionInfo) {}

    @ParameterizedTest(name= "{index} {displayName} msg={0}")
    @ValueSource(strings = {"s1", "s2", "s3"})
    void parameterizedTest(String msg) {
        // s1, s2, s3를 넣어 세 번 반복       
    }
```

- 테스트 인스턴스
  - JUnit은 테스트 메소드마다 테스트 인스턴스를 새로 만든다.
  - 테스트에 따라 상태를 유지해야할 경우 JUnit5에서 전략을 변경하면 가능하다.
  - 상태 유지는 통합 테스트에서 유용할 수 있다.
  ```java

  @TestInstance(TestInstance.Lifecycle.PER_CLASS)
  class TestInstanceTest {
    ...
  }
  ```
  - 테스트 클래스마다 인스턴스를 생성한다.
  - `@BeforeAll` `@AfterAll` `@BeforeEach` `@AfterEach` 에서 테스트 간에 공유하는 상태를 초기화 가능하다.
  



- 테스트 순서
`@TestMethodOrder`
    - MethodOrderer 구현체를 설정
      - Alphanumeric
      - OrderAnnoation
      - Random

    ```java
      @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
      class TestOrder {
        @Order(1)
        @Test
        void test_1() {}
      
        @Order(2)
        @Test
        void test_2() {}
    }
    ```
  
- 확장 모델

JUnit5 `@ExtendWith(FindSlowTestExtension.class)`


## Mockito 
스프링부트 2.2+부터는 Mockito 자동 추가 됨

아니면 mockito-core, mockito-junit 추가
```java
    // https://mvnrepository.com/artifact/org.mockito/mockito-core
    testImplementation group: 'org.mockito', name: 'mockito-core', version: '4.4.0'

    // https://mvnrepository.com/artifact/org.mockito/mockito-junit-jupiter
    testImplementation group: 'org.mockito', name: 'mockito-junit-jupiter', version: '4.4.0'

```

- Mock: 진짜 객체와 비슷하게 동작하지만 프로그래머가 직접 그 객체의 행동을 관리하는 객체
- Mockito: Mock 객체를 쉽게 만들고 관리하고 검증할 수 있는 방법 제공

### Mock 객체 생성
#### 방법 1: Mockito.mock()
```java
@ExtendWith(MockitoExtension.class)
class Test {
    
    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;

    @Test
    void createStudyService() {
        MemberService memberService = mock(MemberService.class);
        StudyRepository studyRepository = mock(StudyRepository.class);

        StudyService studyService = new StudyService(memberService, studyRepository);

        assertNotNull(studyService);
    }
}
```


#### 방법 2: @Mock 어노테이션, 매개변수
```java
@ExtendWith(MockitoExtension.class)
class Test {

    @Test
    void createStudyService2(@Mock MemberService memberService,
                            @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }
}
```

## Mock 객체 Stubbing

## Mockito BDD 스타일 API

어플리케이션이 어떻게 <b>행동</b>해야 하는지에 대한 공통된 이해를 구성하는 방법으로 TDD에서 창안

행동에 대한 스펙

Given / When / Then






















