package manipulations;

import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


public class SplitSimplyTest {

    @Test
    public void string_split_test() {
        String[] result = "peter,james,thomas" .split(",");
        String[] whiteSpaceResult = "peter james thomas".split(" ");

        String[] expected = {"peter", "james", "thomas"};

        Assertions.assertArrayEquals(result, expected);
        Assertions.assertArrayEquals(whiteSpaceResult, expected);
    }

    @Test
    public void string_split_special_char_test() {
        String [] result = "127.0.0.1".split("\\.");
        Assertions.assertArrayEquals(new String[]{"127","0","0","1"}, result);
    }

    @Test
    public void string_split_regex_test() {
        String[] result = "b a, e, l.d u, n g".split("\\s+|,\\s*|\\.\\s*");
        Assertions.assertArrayEquals(new String[]{"b","a", "e", "l","d", "u", "n", "g"}, result);
    }

    @Test
    public void apache_common_split_test() {
        String[] result = StringUtils.split("peter james thomas");
        String[] whiteSpaceResult = StringUtils.split("peter                     james thomas");
        String[] expected = {"peter", "james", "thomas"};
        Assertions.assertArrayEquals(result, expected);
        Assertions.assertArrayEquals(whiteSpaceResult, expected);
    }

    @Test
    public void guava_split_test() {
        List<String> result = Splitter.on(',')
                .trimResults()
                .omitEmptyStrings()
                .splitToList("peter,james,,thomas");

        Assertions.assertArrayEquals(result.toArray(), new String[]{"peter", "james", "thomas"});
    }

    @Test
    public void stream_trim_and_split_test() {
        String input = "peter    , james, thomas";
        String[] result = Arrays.stream(input.split(","))
                .map(String::trim)
                .toArray(String[]::new);

        Assertions.assertArrayEquals(new String[]{"peter", "james", "thomas"}, result);
    }

    @Test
    public void subString_test() {
        String input = "123456789";
        Assertions.assertArrayEquals(new String[]{"1234","56789"}, new String[]{input.substring(0, 4), input.substring(4)});
    }


}
