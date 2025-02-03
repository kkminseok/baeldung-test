package manipulations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class CheckStringNumber {


    @Test
    public void plain_java_test() {

        assertTrue(isNumeric("22"));
        assertTrue(isNumeric("5.05"));
        assertTrue(isNumeric("-200"));
        assertTrue(isNumeric("10.0d"));
        assertTrue(isNumeric("           22         "));

        assertFalse(isNumeric(null));
        assertFalse(isNumeric(""));
        assertFalse(isNumeric("abc "));
    }

    public static boolean isNumeric(String s) {
        if(s==null || s.length()==0) return false;
        try {
            double d = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    @Test
    public void regular_expression_java_test() {

        assertTrue(isNumericByRegex("22"));
        assertTrue(isNumericByRegex("5.05"));
        assertTrue(isNumericByRegex("-200"));

        assertFalse(isNumericByRegex(null));
        assertFalse(isNumericByRegex(""));
        assertFalse(isNumericByRegex("abc "));
    }

    public static boolean isNumericByRegex(String s) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if(s==null || s.length()==0) return false;
        return pattern.matcher(s).matches();
    }
}
