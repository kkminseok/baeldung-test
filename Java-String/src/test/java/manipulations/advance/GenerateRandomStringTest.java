package manipulations.advance;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.text.RandomStringGenerator;
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

    @Test
    public void usingPlainJava8_randomsInt_GeneratingRandomStringNumber() {
        int leftLimit = 48; // numeral 0
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        System.out.println(generatedString);
    }

    @Test
    public void apacheCommonLang_test() {
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = true;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

        System.out.println(generatedString);
    }

    @Test
    public void apacheCommonLang_alphabetic_test() {
        String generatedString = RandomStringUtils.randomAlphabetic(10);
        System.out.println(generatedString);
    }

    @Test
    public void apacheCommonLang_alphanumeric_test() {
        String generatedString = RandomStringUtils.randomAlphanumeric(10);
        System.out.println(generatedString);
    }

    @Test
    public void apacheCommonLang_new_test() {
        // 문자 숫자
        RandomStringGenerator generate = new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(Character::isLetterOrDigit)
                .get();
        // 숫자만
        RandomStringGenerator digitGenerate = new RandomStringGenerator.Builder()
                .withinRange('0', '9')
                .filteredBy(Character::isDigit)
                .get();

        String generatedString = generate.generate(10);
        String digitGeneratedString = digitGenerate.generate(10);

        System.out.println(generatedString);
        System.out.println(digitGeneratedString);
    }
}
