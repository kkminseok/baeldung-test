import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}
