package manipulations;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class RemoveLastCharTest {

    @Test
    public void substring_test() {
        String result = "test";
        String expected = "tes";

        result = removeLastChar(result);
        Assertions.assertEquals(result, expected);
    }

    public static String removeLastChar(String s) {
        return (s == null || s.length() == 0)
                ? null
                : (s.substring(0, s.length() - 1));
    }

    @Test
    public void substring_test_java8_above() {
        String result = "test";
        String expected = "tes";

        result = removeLastCharJava8Above(result);
        Assertions.assertEquals(result, expected);
    }

    public static String removeLastCharJava8Above(String s) {
        return Optional.ofNullable(s)
                .filter(str -> str.length() != 0)
                .map(str -> str.substring(0, str.length() - 1))
                .orElse(s);
    }

    @Test
    public void apache_common_substring_test() {
        String result = "test";
        String expected = "tes";

        result = StringUtils.substring(result, 0, result.length() - 1);
        Assertions.assertEquals(result, expected);
    }

    @Test
    public void chop_test() {
        String result = "test";
        String expected = "tes";
        Assertions.assertEquals(StringUtils.chop(result), expected);
    }

    @Test
    public void regular_expression_test() {
        String result = "test";
        String expected = "tes";
        Assertions.assertEquals(result.replaceAll(".$", ""), expected);
    }

    @Test
    public void regular_expression_test_java8_above() {
        String result = "test";
        String expected = "tes";

        result = removeLastCharRegexJava8Above(result);
        Assertions.assertEquals(result, expected);
    }

    public static String removeLastCharRegexJava8Above(String s) {
        return Optional.ofNullable(s)
                .filter(str -> str.length() != 0)
                .map(str -> str.replaceAll(".$",""))
                .orElse(s);
    }


}
