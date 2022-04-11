package yooyeon.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RepeatTest {

    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetition}")
    @DisplayName("테스트 반복하기")
    void repeat(RepetitionInfo repetitionInfo) {
        System.out.println("test " + repetitionInfo.getCurrentRepetition() + "/" + repetitionInfo.getTotalRepetitions());
    }

    @ParameterizedTest(name = "{index} {displayName} msg={0}")
    @ValueSource(strings = {"s1", "s2", "s3"})
    @NullAndEmptySource
        // @NullSource + @EmptySource
    void parameterizedTest(String msg) {
        // s1, s2, s3를 넣어 세 번 반복
        System.out.println("msg = " + msg);
    }

    @ParameterizedTest(name = "{index} {displayName} msg={0}")
    @ValueSource(ints = {1, 2, 3})
    void parameterizedTest(@ConvertWith(StudyConverter.class) Study study) {
        System.out.println("study = " + study.getLimit());
    }

    @CsvSource({"10, '자바 스터디'", "20, '스프링'"})
    @ParameterizedTest(name = "{index} {displayName} msg={0}")
    @DisplayName("CsvSource Test")
    void csvSourceTest(Integer limit, String name) {
        Study study = new Study(limit, name);
        System.out.println("study = " + study);
    }

    @CsvSource({"10, '자바 스터디'", "20, '스프링'"})
    @ParameterizedTest(name = "{index} {displayName} msg={0}")
    @DisplayName("CsvSource Test")
    void csvSourceTest(ArgumentsAccessor argumentsAccessor) {
        Study study = new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
        System.out.println("study = " + study);
    }

    @CsvSource({"10, '자바 스터디'", "20, '스프링'"})
    @ParameterizedTest(name = "{index} {displayName} msg={0}")
    @DisplayName("CsvSource Test")
    void csvSourceTest(@AggregateWith(StudyAggregator.class) Study study) {
        System.out.println("study = " + study);
    }

    // 커스텀
    static class StudyAggregator implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
            return new Study(accessor.getInteger(0), accessor.getString(1));
        }
    }

    static class StudyConverter extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(Study.class, targetType, "Can only convert to Study");
            return new Study(Integer.parseInt(source.toString()));
        }
    }


}
