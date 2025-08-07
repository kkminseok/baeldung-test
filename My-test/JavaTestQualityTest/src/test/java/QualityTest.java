import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QualityTest {

    @Test
    @DisplayName("조건 연산자 가변 인수 호출")
    void testConditionalOperatorVarargs() {
        printFormatted("Hello %s%n", "admin");
    }
    private void printFormatted(String format, Object... args) {
//        if(args.length == 0) {
//            System.out.printf(format, "user");
//        } else {
//            System.out.printf(format, args);
//        }

        System.out.printf(format, args.length == 0 ? "user" : args);
    }

    @Test
    @DisplayName("잘못된 메서드 참조 사용")
    void givenWrongMethodReference() {
        List<Integer> list = Arrays.asList(0, -3, 3, -1, 1, 2);
        list.sort(Integer::max);
        // [0, -3 , 3, -1, 1, 2]
        System.out.println(list);
    }

    @Test
    @DisplayName("정수 나눗셈 중 반올림 문제")
    void testIntegerDivisionRoundingIssue() {
        Assertions.assertEquals(process(2), process(3));
    }

    private double process(int value) {
        double half = value / 2;
        return half;
    }

    @Test
    @DisplayName("Integer Min Value특수성")
    void testIntegerMinValueSpecialCase() {
        Assertions.assertEquals(Math.abs(Integer.MIN_VALUE), Integer.MIN_VALUE);
    }

    @Test
    @DisplayName("Double 최솟값 확인")
    void testDoubleMinValue() {
        System.out.println("Double.MIN_VALUE: " + Double.MIN_VALUE);
        System.out.println("-Double.MAX_VALUE" + (-Double.MAX_VALUE));
        Assertions.assertEquals(Double.MIN_VALUE, 4.9E-324);
    }

    @Test
    @DisplayName("replaceAll 메서드 실수 테스트")
    void testReplaceAllMethodMistake() {
        String input = "$0minseok";
        String template = "Hello USER_NAME";
        String result = template.replaceAll("USER_NAME", input);
        Assertions.assertEquals("Hello USER_NAMEminseok", result);
    }

    @Test
    @DisplayName("indexOf 메서드 실수 테스트")
    void testIndexOfMethodMistake() {
        String input = """
                ]AAAAAAAAAA
                AAAAAAAAAA
                AAAAAAAAAA
                AAAAAAAAAA
                AAAAAAAAAA
                AAAAAAAAAA
                AAAAAAAAAA
                AAAAAAAAAA
                AAAAAAAAAA
                AAAAAAAAAA
                AAAAAAA[AA
                v
                """;
        int start = input.indexOf('[');
        int end = input.indexOf(start, ']');
        Assertions.assertEquals(118, start);
        Assertions.assertEquals(122,end);
    }
}
