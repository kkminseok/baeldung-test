package org.example.manipulations;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.concurrent.TimeUnit;


public class CheckStringNumberBenchMarking {
    private static final String TEST_VALUE = String.valueOf(Integer.MAX_VALUE);

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(CheckStringNumberBenchMarking.class.getSimpleName()) // 클래스명을 문자열로 가져와 실행 대상 선택
                .mode(Mode.AverageTime) // 평균시간 측정
                .timeUnit(TimeUnit.NANOSECONDS) // 단위는 ns
                .warmupTime(TimeValue.seconds(1)) // 워밍업 각 반복당 실행시간 지정
                .warmupIterations(2) // 벤치 마크 실행하기전 2번의 워밍업 진행
                .measurementTime(TimeValue.seconds(1)) // 측정 단계에서 각 반복당 실행시간 지정
                .measurementIterations(20) // 벤치마크를 n번 실행하여 평균값을 측정
                .threads(1) // 쓰레드 갯수
                .forks(1) // JVM 갯수 지정
                .shouldFailOnError(true) // 벤치마크 실행 중 오류가 발생하면 실행 중단할지 여부 확인
                .shouldDoGC(true) // 각 벤치마크 실행 전 GC를 실행할지 여부
                .build();
        new Runner(opt).run();
    }

    @Benchmark
    public boolean usingCoreJava() {
        try {
            Integer.parseInt(TEST_VALUE);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Benchmark
    public boolean usingNumberUtils_isCreatable() {
        return NumberUtils.isCreatable(TEST_VALUE);
    }

    @Benchmark
    public boolean usingRegularExpressions() {
        return TEST_VALUE.matches("-?\\d+(\\.\\d+)?");
    }

    @Benchmark
    public boolean usingNumberUtils_isParsable() {
        return NumberUtils.isParsable(TEST_VALUE);
    }

    @Benchmark
    public boolean usingStringUtils_isNumeric() {
        return StringUtils.isNumeric(TEST_VALUE);
    }

    @Benchmark
    public boolean usingStringUtils_isNumericSpace() {
        return StringUtils.isNumericSpace(TEST_VALUE);
    }

}
