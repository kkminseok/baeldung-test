package manipulations;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

public class CapitalizeFirstLetterTest {

    private final String INPUT = "hi there, Nice to Meet You!";

    private final String EXPECTED = "Hi there, Nice to Meet You!";
    @Test
    public void substring_concatenate_test() {
        String output = INPUT.substring(0,1).toUpperCase() + INPUT.substring(1);

        Assertions.assertEquals(EXPECTED, output);
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> "".substring(1));
    }

    @Test
    public void regex_capitalize_test() {
        String output = Pattern.compile("^.").matcher(INPUT).replaceFirst(m -> m.group().toUpperCase());
        String emptyOutput = Pattern.compile("^.").matcher("").replaceFirst(m -> m.group().toUpperCase());

        Assertions.assertEquals(EXPECTED, output);
        Assertions.assertEquals("", emptyOutput);
    }

    @Test
    public void apache_common_capitalize_test() {
        String output = StringUtils.capitalize(INPUT);
        String emptyOutput = StringUtils.capitalize("");
        String nullOutput = StringUtils.capitalize(null);

        Assertions.assertEquals(EXPECTED, output);
        Assertions.assertEquals("", emptyOutput);
        Assertions.assertNull(nullOutput);
    }
}
