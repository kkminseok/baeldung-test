package manipulations;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RemoveWhitespaceTest {

    String myString = "   I    am a    wonderful String     !   ";

    @Test
    public void replace_all_whitespace_test() {
        String result = myString.replaceAll("\\s", "");
        Assertions.assertEquals("IamawonderfulString!", result);
    }

    @Test
    public void remove_whitespace_apache_commons_test() {
        String result = StringUtils.deleteWhitespace(myString);
        Assertions.assertEquals("IamawonderfulString!", result);
    }

    @Test
    public void remove_whitespace_one_single_space_test() {
        String result = myString.replaceAll("\\s+", " ");
        Assertions.assertEquals(" I am a wonderful String ! ", result);
        Assertions.assertEquals("I am a wonderful String !", result.trim());
    }

    @Test
    public void remove_whitespace_one_single_apache_commons_test() {
        String result = StringUtils.normalizeSpace(myString);
        Assertions.assertEquals("I am a wonderful String !", result);
    }

    @Test
    public void remove_whitespace_strip_test() {
        String result = myString.strip();
        Assertions.assertEquals("I    am a    wonderful String     !", result);
    }

    @Test
    public void stripLeading_stripTrailing_test() {
        Assertions.assertEquals("I    am a    wonderful String     !   ", myString.stripLeading());
        Assertions.assertEquals("   I    am a    wonderful String     !", myString.stripTrailing());
    }

}
