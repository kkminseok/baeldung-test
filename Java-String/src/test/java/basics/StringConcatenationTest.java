package basics;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class StringConcatenationTest {


    @Test
    void StringBuilderAppendTest() {
        StringBuilder stringBuilder = new StringBuilder(100);

        stringBuilder.append("hello");
        stringBuilder.append(" my name is");
        stringBuilder.append(" minseok");

        assertEquals("hello my name is minseok", stringBuilder.toString());
    }

    @Test
    void StringBufferMultiThreadTest() {
        //given
        StringBuffer stringBuffer = new StringBuffer(100);

        //when
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            executorService.execute(() -> {
                for (int j = 0; j < 100; j++) {
                    stringBuffer.append(finalI);
                }
            });
        }
        executorService.shutdown();

        //then
        assertFalse( 10 * 100 == stringBuffer.length());
    }

    @Test
    void Operator연산자확인() {
        String myString = "hello " + "my " + "name " + "is " + "minseok";

        assertEquals("hello my name is minseok", myString);
    }

    @Test
    void String_concat() {
        String myString = "hello ".concat("my ")
                .concat("name ")
                .concat("is ")
                .concat("minseok");
        assertEquals("hello my name is minseok", myString);
    }

    @Test
    void String_format() {
        String myString = String.format("%s %s %.2f %s %s, %s...", "I",
                "ate",
                2.5056302,
                "blueberry",
                "pies",
                "oops");

        assertEquals("I ate 2.51 blueberry pies, oops...", myString);
    }

    @Test
    void String_join_test() {
        String [] strings = {"hello", "my", "name", "is", "minseok"};

        String resultString = String.join(" ", strings);

        assertEquals("hello my name is minseok", resultString);
    }

    @Test
    void StringJoiner_test() {
        StringJoiner fruitJoiner = new StringJoiner(", ");
        StringJoiner fruitJoiner2 = new StringJoiner(", ", "fruit: ", " end");

        fruitJoiner.add("Apples");
        fruitJoiner.add("Oranges");
        fruitJoiner.add("Bananas");

        fruitJoiner2.add("Apples");
        fruitJoiner2.add("Oranges");
        fruitJoiner2.add("Bananas");

        assertEquals("Apples, Oranges, Bananas", fruitJoiner.toString());
        assertEquals("fruit: Apples, Oranges, Bananas end", fruitJoiner2.toString());
    }

    @Test
    void arrayToString_test() {
        String[] myFavouriteLanguages = {"Java", "JavaScript", "Python"};

        String toString = Arrays.toString(myFavouriteLanguages);

        assertEquals("[Java, JavaScript, Python]", toString);
    }

    @Test
    void collector_join() {
        List<String> awesomeAnimals = Arrays.asList("Shark", "Panda", "Armadillo");

        String animalString = awesomeAnimals.stream().collect(Collectors.joining(", "));

        assertEquals("Shark, Panda, Armadillo", animalString);
    }



}
