package basic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class VarargsTest {

    @Test
    public void heap_pollution_test() {
        //String one = firstOfFirst(Arrays.asList("one", "two"), Collections.emptyList());

        Assertions.assertThrows(ClassCastException.class, () -> {firstOfFirst(Arrays.asList("one", "two"), Collections.emptyList());});
        //기대하던 결과
        //Assertions.assertEquals("one", one);
    }

    public static String firstOfFirst(List<String>... strings) {
        List<Integer> ints = Collections.singletonList(42);
        Object[] objects = strings;
        objects[0] = ints; // Hepa pollution

        return strings[0].get(0); //ClassCast Exception
    }
}
