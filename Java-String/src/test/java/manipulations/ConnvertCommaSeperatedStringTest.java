package manipulations;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConnvertCommaSeperatedStringTest {

    String countries = "Russia,Germany,England,France,Italy";
    String ranks = "1,2,3,4,5,6,7";

    List<String> convertedCountriesList;
    List<Integer> convertedRankList;

    List<String> expectedCountriesList = Arrays.asList("Russia", "Germany", "England", "France", "Italy");
    List<Integer> expectedRanksList = Arrays.asList(1, 2, 3, 4, 5, 6, 7);

    @Test
    public void core_java_test() {
        List<String> convertedCountriesList = Arrays.asList(countries.split(",", -1));
        String[] splitRanks = ranks.split(",");
        List<Integer> convertedRankList = new ArrayList<Integer>();
        for (String number : splitRanks) {
            convertedRankList.add(Integer.parseInt(number));
        }

        Assertions.assertEquals(expectedCountriesList, convertedCountriesList);
        Assertions.assertEquals(expectedRanksList, convertedRankList);
    }

    @Test
    public void stream_spit_test() {
        List<String> convertedCountriesList = Stream.of(countries.split(",", -1)).collect(Collectors.toList());
        List<Integer> convertedRankList = Stream.of(ranks.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());


        Assertions.assertEquals(expectedCountriesList, convertedCountriesList);
        Assertions.assertEquals(expectedRanksList, convertedRankList);
    }

    @Test
    public void apache_common_test() {
        List<String> convertedCountriesList = Arrays.asList(StringUtils.splitPreserveAllTokens(countries, ","));
        String[] convertedRankArray = StringUtils.split(ranks, ",");
        List<Integer> convertedRankList = new ArrayList<>();
        for (String number: convertedRankArray) {
            convertedRankList.add(Integer.parseInt(number));
        }
        Assertions.assertEquals(expectedCountriesList, convertedCountriesList);
        Assertions.assertEquals(expectedRanksList, convertedRankList);
    }

    @Test
    public void guava_test() {
        List<String> convertedCountriesList = Splitter.on(",")
                .trimResults()
                .splitToList(countries);
        Lists.transform(Splitter.on(",")
                .trimResults()
                .splitToList(ranks), new Function<String, Integer>() {
            @Override
            public Integer apply(String input) {
                return Integer.parseInt(input.trim());
            }
        })

        Assertions.assertEquals(expectedCountriesList, convertedCountriesList);
        Assertions.assertEquals(expectedRanksList, convertedRankList);
    }
}
