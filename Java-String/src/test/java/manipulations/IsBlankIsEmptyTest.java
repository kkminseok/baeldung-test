package manipulations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IsBlankIsEmptyTest {

    @Test
    public void isEmpty_test() {
        Assertions.assertFalse("Empty string".isEmpty());
        Assertions.assertTrue("".isEmpty());
        Assertions.assertFalse(" ".isEmpty());
        Assertions.assertFalse("\t\n\r\f".isEmpty());
    }

    @Test
    public void isBlank_test() {
        Assertions.assertFalse("Empty string".isBlank());
        Assertions.assertTrue("".isBlank());
        Assertions.assertTrue(" ".isBlank());
        Assertions.assertTrue("\t\n\r\f".isBlank());
    }
}
