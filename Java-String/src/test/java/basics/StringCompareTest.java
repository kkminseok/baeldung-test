package basics;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

public class StringCompareTest {
    @Test
    public void operator_Test() {
        String s1 = "hello minseok";
        String s2 = "hello minseok";
        String s3 = new String("hello minseok");

        Assertions.assertTrue(s1 == s2);
        Assertions.assertFalse(s1 == s3);
    }

    @Test
    public void string_equals_test() {
        String s1 = "hello minseok";
        String s2 = "hello minseok";
        String s3 = "hello MINSEOK";
        String s4 = new String("hello minseok");

        Assertions.assertTrue(s1.equals(s2));
        Assertions.assertTrue(s1.equals(s4));
        Assertions.assertFalse(s1.equals(null));
        Assertions.assertFalse(s1.equals(s3));
    }

    @Test
    public void string_equalsIgnoreCase_test() {
        String s1 = "hello minseok";
        String s2 = new String("hello MINseok");

        Assertions.assertTrue(s1.equalsIgnoreCase(s2));
    }

    @Test
    public void string_compareTo_test() {
        String s1 = "apple";
        String s2 = "banana";
        String s3 = "banana";

        Assertions.assertEquals(s1.compareTo(s2), -1);
        Assertions.assertEquals(s2.compareTo(s1), 1);
        Assertions.assertEquals(s2.compareTo(s3), 0);
    }

    @Test
    public void string_compareToIgnoreCase_test() {
        String s1 = "apple";
        String s2 = "banana";
        String s3 = "BANANA";

        Assertions.assertEquals(s1.compareTo(s2), -1);
        Assertions.assertEquals(s2.compareTo(s1), 1);
        Assertions.assertTrue(s2.compareTo(s3) > 0);
        Assertions.assertEquals(s2.compareToIgnoreCase(s3), 0);
    }

    @Test
    public void object_equals_test() {
        String s1 = "hello minseok";
        String s2 = "hello minseok";
        String s3 = new String("hello minseok");

        Assertions.assertTrue(Objects.equals(s1, s2));
        Assertions.assertTrue(Objects.equals(s1, s3));

        Assertions.assertTrue(Objects.equals(null, null));
        Assertions.assertFalse(Objects.equals(null, s1));
    }

    @Test
    public void apache_equals_test() {
        Assertions.assertTrue(StringUtils.equals(null, null));
        Assertions.assertFalse(StringUtils.equals(null, "minseok "));
        Assertions.assertTrue(StringUtils.equals("minseok", "minseok"));
        Assertions.assertFalse(StringUtils.equals("minseok", "Minseok"));
        Assertions.assertTrue(StringUtils.equalsIgnoreCase("minseok", "minseok"));
        Assertions.assertTrue(StringUtils.equalsIgnoreCase("minseok", "Minseok"));
    }

    @Test
    public void apache_any_equals_test() {
        Assertions.assertTrue(StringUtils.equalsAny(null, null, null));
        Assertions.assertTrue(StringUtils.equalsAny("minseok", "minseok", "abc"));
        Assertions.assertTrue(StringUtils.equalsAny("minseok", null, "minseok"));
        Assertions.assertFalse(StringUtils.equalsAny(null, "abc", "minseok"));
        Assertions.assertFalse(StringUtils.equalsAny("minseok", "abc", "Minseok"));
        Assertions.assertTrue(StringUtils.equalsAnyIgnoreCase("minseok", "abc", "Minseok"));
    }

    @Test
    public void apache_compareTo_test() {
        Assertions.assertEquals(StringUtils.compare(null,null),0);
        Assertions.assertEquals(StringUtils.compare(null,"abc"),-1);
        Assertions.assertEquals(StringUtils.compare("abc","bbc"),-1);
        Assertions.assertEquals(StringUtils.compare("bbc","abc"),1);

        Assertions.assertEquals(StringUtils.compareIgnoreCase("Abc", "bbc"), -1);
        Assertions.assertEquals(StringUtils.compareIgnoreCase("bbc", "Abc"), 1);
        Assertions.assertEquals(StringUtils.compareIgnoreCase("Abc", "abc"), 0);

        Assertions.assertEquals(StringUtils.compare(null, "abc", true),-1);
        Assertions.assertEquals(StringUtils.compare(null, "abc", false),1);

        Assertions.assertEquals(StringUtils.compareIgnoreCase(null, "Abc", true),-1);
        Assertions.assertEquals(StringUtils.compareIgnoreCase(null, "Abc", false),1);
    }
}
