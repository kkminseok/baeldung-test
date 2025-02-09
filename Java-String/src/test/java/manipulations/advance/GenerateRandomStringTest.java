package manipulations.advance;

import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.util.Random;

public class GenerateRandomStringTest {

    @Test
    public void usingPlainJava_GeneratingRandomString() {
        byte[] array = new byte[7];
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        System.out.println(generatedString);
    }

    @Test
    public void usingPlainJava_GeneratingRandomString_alphabetically() {
        int leftLimit = 97; // 'a'
        int rightLimit = 122; // 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);

        for (int i = 0; i< targetStringLength; ++i) {
            int randomLimitedInt = leftLimit + (int)(random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char)randomLimitedInt);
        }

        String generatedString = buffer.toString();
        System.out.println(generatedString);
    }

    @Test
    public void usingPlainJava8_randomsInt_GeneratingRandomString() {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        System.out.println(generatedString);
    }
}
