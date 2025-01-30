package basics;

import org.apache.commons.text.StringSubstitutor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class StringInterpolationTest {

    @Test
    public void operator_test() {
        String expected = "String Interpolation Test By Minseok";
        String first = "Interpolation";
        String second = "By";
        String result = "String " + first + " Test "+ second + " Minseok";
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void format_test() {
        String expected = "String Interpolation Test By Minseok";
        String first = "Interpolation";
        String second = "By";
        String result = String.format("String %s Test %s Minseok", first, second);
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void format_avoid_repeat_test() {
        String expected = "String Interpolation Test By Minseok By Baeldung";
        String first = "Interpolation";
        String second = "By";
        String result = String.format("String %1$s Test %2$s Minseok %2$s Baeldung", first, second);
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void builder_test() {
        String expected = "String Interpolation Test By Minseok";
        String first = "Interpolation";
        String second = "By";
        String result = new StringBuilder()
                .append("String ")
                .append(first)
                .append(" Test ")
                .append(second)
                .append(" Minseok")
                .toString();
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void messageFormat_test() {
        String expected = "String Interpolation Test By Minseok";
        String first = "Interpolation";
        String second = "By";
        String result = MessageFormat.format("String {0} Test {1} Minseok", first, second);
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void apache_commons_test() {
        String expected = "String Interpolation Test with some Java examples By Minseok";
        String baseString = "String ${first} Test with some Java examples ${second} Minseok";
        String first = "Interpolation";
        String second = "By";

        Map<String, String> parameters = new HashMap<>();
        parameters.put("first", first);
        parameters.put("second", second);
        StringSubstitutor substitutor = new StringSubstitutor(parameters);

        String result = substitutor.replace(baseString);
        Assertions.assertEquals(expected, result);
    }
}
