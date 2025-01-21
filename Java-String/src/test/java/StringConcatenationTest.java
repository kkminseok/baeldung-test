import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

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

}
