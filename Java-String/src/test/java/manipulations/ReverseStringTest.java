package manipulations;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ReverseStringTest {

    @Test
    public void for_loop_reverse_test() {
        String s1 = "abcd";
        String expected = "dcba";

        Assertions.assertEquals(expected, forLoopReverseString(s1));
        Assertions.assertNull(forLoopReverseString(null));
        Assertions.assertEquals(StringUtils.EMPTY, forLoopReverseString(""));
    }

    @Test
    public void string_builder_reverse_test() {
        String s1 = "abcd";
        String expected = "dcba";

        Assertions.assertEquals(expected, new StringBuilder(s1).reverse().toString());
    }

    @Test
    public void string_intStream_range_test() {
        String s1 = "abcd";
        String expected = "dcba";

        String result = reverseUsingIntStreamRangeMethod(s1);

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void whenReverseStringUsingStreamRangeMethod_ThenCorrectStringIsReturned() {
        String s1 = "abcd";
        String expected = "dcba";

        String reversed = reverseUsingIntStreamRangeMethod(s1);
        String reversedNull = reverseUsingIntStreamRangeMethod(null);
        String reversedEmpty = reverseUsingIntStreamRangeMethod(StringUtils.EMPTY);

        Assertions.assertEquals(expected, reversed);
        Assertions.assertNull(reversedNull);
        Assertions.assertEquals(StringUtils.EMPTY, reversedEmpty);
    }

    @Test
    public void whenReverseStringUsingStreamOfMethod_ThenCorrectStringIsReturned() {
        String s1 = "abcd";
        String expected = "dcba";
        String reversed = reverseUsingStreamMethod(s1);
        String reversedNull = reverseUsingStreamMethod(null);
        String reversedEmpty = reverseUsingStreamMethod(StringUtils.EMPTY);

        Assertions.assertEquals(expected, reversed);
        Assertions.assertNull(reversedNull);
        Assertions.assertEquals(StringUtils.EMPTY, reversedEmpty);
    }

    public static String reverseUsingStreamMethod(String str) {
        if (str == null) {
            return null;
        }

        return Stream.of(str)
                .map(i -> new StringBuilder(i).reverse())
                .collect(Collectors.joining());
    }



    public static String reverseUsingIntStreamRangeMethod(String str) {
        if (str == null) {
            return null;
        }

        char[] charArray = str.toCharArray();
        return IntStream.range(0, str.length())
                .mapToObj(i -> charArray[str.length() - i - 1])
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    public static String forLoopReverseString(String input) {
        if (input == null)
            return null;

        String result = "";
        for (int i = input.length() - 1; i >= 0; i--) {
            result += input.charAt(i);
        }

        return result;
    }


}
