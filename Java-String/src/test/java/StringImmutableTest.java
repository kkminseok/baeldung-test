import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class StringImmutableTest {

    @Test
    public void 리터럴_String은_같은메모리를_참조한다() {
        //given
        String s1 = "minseok";
        String s2 = "minseok";

        //when

        //then
        assertTrue(s1 == s2);
    }

    @Test
    public void String불변객체확인() {
        //given
        String s1 = "minseok";

        //when
        changeStringToAlice(s1);

        //then
        assertTrue(s1.equals("minseok"));
    }

    private void changeStringToAlice(String s1) {
        s1= "Alice";
    }

    @Test
    void stringIsThreadSafe() {
        // given
        String sharedString = "immutableString";
        AtomicBoolean isConsistent = new AtomicBoolean(true);

        // when
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                // Simulate concurrent access
                if (!sharedString.equals("immutableString")) {
                    isConsistent.set(false);
                }
            });
        }

        // Shutdown the executor and wait for tasks to complete
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            // Wait for all threads to finish
        }

        // then
        assertTrue(isConsistent.get(), "String is not thread-safe!");

        HashMap<String, String> immutableMap = new HashMap<>();
        String key = "immutableKey";
        immutableMap.put(key, "immutableValue");

        // Attempt to modify the key (not possible since String is immutable)
        key = "modifiedKey";

        System.out.println("Immutable Map Retrieval:");
        System.out.println("Value for 'immutableKey': " + immutableMap.get("immutableKey")); // Works
    }

}
