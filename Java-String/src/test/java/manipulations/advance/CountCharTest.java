package manipulations.advance;

import com.google.common.base.CharMatcher;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CountCharTest {

    @Test
    public void java_core_simple_test() {
        String someString = "elephant";
        char someChar = 'e';
        int count = 0;

        for(int i = 0; i< someString.length(); ++i) {
            if(someString.charAt(i) == someChar) {
                count++;
            }
        }

        Assertions.assertEquals(2, count);
    }

    @Test
    public void recursion_test() {
        String someString = "elephant";
        char someChar = 'e';
        int count = 0;


        Assertions.assertEquals(2, useRecursion(someString, someChar, count));
    }

    @Test
    public void regular_expression_test() {
        Pattern pattern = Pattern.compile("[^e]*e");
        Matcher matcher = pattern.matcher("elephant");
        int count=0;
        while (matcher.find()) {
            count++;
        }

        Assertions.assertEquals(2, count);
    }

    @Test
    public void java8_above_test() {
        String someString = "elephant";
        long count = someString.chars().filter(ch -> ch == 'e').count();
        Assertions.assertEquals(2, count);

        long count2 = someString.codePoints().filter(ch -> ch == 'e').count();
        Assertions.assertEquals(2, count2);
    }

    @Test
    public void apache_common_test() {
        int count = StringUtils.countMatches("elephant", "e");

        Assertions.assertEquals(2, count);
    }

    @Test
    public void guava_test() {
        int count = CharMatcher.is('e').countIn("elephant");

        Assertions.assertEquals(2, count);
    }

    @Test
    public void spring_test() {
        int count = org.springframework.util.StringUtils.countOccurrencesOf("elephant", "e");

        Assertions.assertEquals(2, count);
    }

    private static int useRecursion(String someString, char searchedChar, int index) {
        if(index >= someString.length()) {
            return 0;
        }

        int count = someString.charAt(index) == searchedChar ? 1: 0;
        return count + useRecursion(someString, searchedChar, index+1);
    }

}
