package manipulations;

import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class CheckEmptyOrBlankTest {

    @Test
    public void above_java6_empty_check_test() {
        String result = new String();
        String nullResult = null;
        Assertions.assertTrue(result.isEmpty());
        Assertions.assertTrue(nullResult == null || nullResult.isEmpty());
    }

    @Test
    public void blank_check_test() {
        String result ="            ";
        Assertions.assertTrue(result == null || result.isBlank());
    }

    @Test
    public void blank_check_by_trim_test() {
        String result = "            ";
        Assertions.assertTrue(result == null || result.trim().isEmpty());
    }

    @Test
    public void apache_blank_check_test() {
        String result ="            ";
        Assertions.assertTrue(StringUtils.isBlank(result));
    }

    @Test
    public void guava_empty_check_test() {
        String result = null;
        String whiteSpaceSentence = "    ";
        Assertions.assertTrue(Strings.isNullOrEmpty(result));
        Assertions.assertFalse(Strings.isNullOrEmpty(whiteSpaceSentence));
    }

    @Test
    public void spring_core_empty_check_test() {
        String result = null;
        Assertions.assertTrue(ObjectUtils.isEmpty(result));
    }


}
