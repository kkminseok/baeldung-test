package manipulations.advance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CheckPalindromeTest {

    @Test
    public void java_core_test() {
        Assertions.assertTrue(isPalindrome("racecar"));
    }

    private static boolean isPalindrome(String text) {
        String clean = text.replaceAll("\\s+", "").toLowerCase();
        int length = clean.length();
        int forward = 0;
        int backward = length - 1;
        while (forward < backward) {
            char forwardChar = clean.charAt(forward++);
            char backwardChar = clean.charAt(backward--);
            if (forwardChar != backwardChar) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void reversing_text_test() {
        Assertions.assertTrue(isPalindromeReverseTheString("racecar"));
    }

    private static boolean isPalindromeReverseTheString(String text) {
        StringBuilder reverse = new StringBuilder();
        String clean = text.replaceAll("\\s+", "");
        char[] plain = clean.toCharArray();
        for (int i = plain.length - 1; i >= 0; i--) {
            reverse.append(plain[i]);
        }
        return text.equals(reverse.toString());
    }

    private static boolean isPalindromeUsingStringBuilder(String text) {
        String clean = text.replaceAll("\\s+", "");
        StringBuilder plain = new StringBuilder(clean);
        StringBuilder reverse = plain.reverse();
        return reverse.toString().equals(clean);
    }

    private static boolean isPalindromeUsingStringBuffer(String text) {
        String clean = text.replaceAll("\\s+", "").toLowerCase();
        StringBuffer plain = new StringBuffer(clean);
        StringBuffer reverse = plain.reverse();
        return reverse.toString().equals(clean);
    }

    @Test
    public void streamAPI_test() {
        Assertions.assertTrue(isPalindromeUsingIntStream("racecar"));
    }

    public static boolean isPalindromeUsingIntStream(String text) {
        String temp = text.replaceAll("\\s+", "").toLowerCase();
        return IntStream.range(0, temp.length() / 2)
                .noneMatch(i -> temp.charAt(i) != temp.charAt(temp.length() - i - 1));
    }

    @Test
    public void recursion_test() {
        Assertions.assertTrue(isPalindromeUsingRecursive("racecar"));
    }

    public static boolean isPalindromeUsingRecursive(String text) {
        String clean = text.replaceAll("\\s+", "").toLowerCase();
        return recursivePalindrome(clean, 0, clean.length() - 1);
    }

    public static boolean recursivePalindrome(String text, int forward, int backward) {
        if (forward == backward) {
            return true;
        }
        if (text.charAt(forward) != text.charAt(backward)) {
            return false;
        }
        if (forward < backward + 1) {
            recursivePalindrome(text, forward + 1, backward - 1);
        }
        return true;
    }

    @Test
    public void anagram_test() {
            Assertions.assertTrue(hasPalindromePermutation("racecar"));
    }

    public static boolean hasPalindromePermutation(String text) {
        long charsWithOddOccurrencesCount = text.chars() // Intstream형태로 변환
                .boxed()// Stream<Intstream>변환
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))// 그룹화작업. key: 문자, value: 갯수
                .values()
                .stream() // 값만가져와서 Long<Stream>으로 재구성
                .filter(count -> count % 2 != 0)// Log<Stream> 값이 1이 아닌것들을 분류
                .count(); // 1이 아닌 것들의 갯수를 셈

        return charsWithOddOccurrencesCount <= 1; // 2를 넘어가면 회문이 아님.
    }
}
