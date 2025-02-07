package manipulations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class StringConcatenationTest {

    @Test
    public void operator_null_test() {
        String stringOne = "minseok ";
        String stringTwo = null;
        Assertions.assertEquals("minseok null", stringOne + stringTwo);
    }

    @Test
    public void concat_test() {
        String stringOne = "minseok";
        String stringTwo = " is a good man";
        Assertions.assertEquals("minseok is a good man", stringOne.concat(stringTwo));
    }

    @Test
    public void concat_immutable_test() {
        String stringOne = "minseok";
        String stringTwo = " is a good man";
        stringOne.concat(stringTwo);
        Assertions.assertNotEquals("minseok is a good man", stringOne);

        String result = stringOne.concat(stringTwo);

        Assertions.assertEquals("minseok is a good man", result);
    }

    @Test
    public void concat_multiple_concatenation_test() {
        String stringOne = "minseok";
        String stringTwo = " is a";
        String stringThree = "good man";
        String result = stringOne.concat(stringTwo).concat(" ").concat(stringThree);
        String expected = "minseok is a good man";

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void concat_null_test_throw_exception() {
        String stringOne = "minseok";
        String stringTwo = null;
        Assertions.assertThrows(NullPointerException.class, () -> stringOne.concat(stringTwo));
    }

    @Test
    public void stringBuilder_concatenation_test() {
        StringBuilder builderOne = new StringBuilder("minseok");
        StringBuilder builderTwo = new StringBuilder(" is a good man");
        StringBuilder result = builderOne.append(builderTwo);
        String expected = "minseok is a good man";
        Assertions.assertEquals(expected, result.toString());
    }

    @Test
    public void string_format_concatenation_test() {
        String stringOne = "minseok";
        String stringTwo = " is a good man";
        String result = String.format("%s%s", stringOne, stringTwo);
        String expected = "minseok is a good man";
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void string_join_concatenation_test() {
        String stringOne = "minseok";
        String stringTwo = "is a good man";
        String result = String.join(" ", stringOne, stringTwo);
        String expected = "minseok is a good man";
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void stringJoinerClass_concatenation_test() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add("minseok");
        joiner.add("is a good man");
        String expected = "minseok is a good man";

        Assertions.assertEquals(expected, joiner.toString());
    }

    @Test
    public void stream_concatenation_test() {
        List<String> words = Arrays.asList("minseok", "is a good man");
        String result = words.stream().collect(Collectors.joining(" "));
        String expected = "minseok is a good man";

        Assertions.assertEquals(expected, result);
    }
}
