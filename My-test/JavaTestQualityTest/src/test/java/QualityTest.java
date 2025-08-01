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
}
