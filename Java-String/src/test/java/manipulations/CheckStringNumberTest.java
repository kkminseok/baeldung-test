package manipulations;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class CheckStringNumberTest {


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

    @Test
    public void apache_commons_iscreatable_test(){
        assertTrue(NumberUtils.isCreatable("22"));
        assertTrue(NumberUtils.isCreatable("5.05"));
        assertTrue(NumberUtils.isCreatable("-200"));
        assertTrue(NumberUtils.isCreatable("10.0d"));
        assertTrue(NumberUtils.isCreatable("1000L"));
        assertTrue(NumberUtils.isCreatable("0xFF"));
        assertTrue(NumberUtils.isCreatable("07"));
        assertTrue(NumberUtils.isCreatable("2.99e+8"));

        assertFalse(NumberUtils.isCreatable(null));
        assertFalse(NumberUtils.isCreatable(""));
        assertFalse(NumberUtils.isCreatable("abc"));
        assertFalse(NumberUtils.isCreatable("  22  "));
        assertFalse(NumberUtils.isCreatable("09")); //8진수 벗어남.
    }

    @Test
    public void apache_commons_isParsable_test() {
        assertTrue(NumberUtils.isParsable("22"));
        assertTrue(NumberUtils.isParsable("-23"));
        assertTrue(NumberUtils.isParsable("2.2"));
        assertTrue(NumberUtils.isParsable("09"));

        assertFalse(NumberUtils.isParsable(null));
        assertFalse(NumberUtils.isParsable(""));
        assertFalse(NumberUtils.isParsable("6.2f"));
        assertFalse(NumberUtils.isParsable("9.8d"));
        assertFalse(NumberUtils.isParsable("22L"));
        assertFalse(NumberUtils.isParsable("0xFF"));
        assertFalse(NumberUtils.isParsable("2.99e+8"));
    }

    @Test
    public void string_utils_isNumeric_test() {
        assertTrue(StringUtils.isNumeric("123"));
        assertTrue(StringUtils.isNumeric("١٢٣"));
        assertTrue(StringUtils.isNumeric("१२३"));

        assertFalse(StringUtils.isNumeric(null));
        assertFalse(StringUtils.isNumeric(""));
        assertFalse(StringUtils.isNumeric("    "));
        assertFalse(StringUtils.isNumeric("12 3"));
        assertFalse(StringUtils.isNumeric("ab2c"));
        assertFalse(StringUtils.isNumeric("12.3"));
        assertFalse(StringUtils.isNumeric("-123"));
    }

    @Test
    public void string_util_isNumericSpace_test() {
        assertTrue(StringUtils.isNumericSpace("123"));
        assertTrue(StringUtils.isNumericSpace("١٢٣"));
        assertTrue(StringUtils.isNumericSpace(""));
        assertTrue(StringUtils.isNumericSpace("   "));
        assertTrue(StringUtils.isNumericSpace("12 3"));

        assertFalse(StringUtils.isNumericSpace(null));
        assertFalse(StringUtils.isNumericSpace("ab2c"));
        assertFalse(StringUtils.isNumericSpace("12.3"));
        assertFalse(StringUtils.isNumericSpace("-123"));
    }


    public static boolean isNumericByRegex(String s) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if(s==null || s.length()==0) return false;
        return pattern.matcher(s).matches();
    }
}
