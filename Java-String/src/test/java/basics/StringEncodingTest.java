package basics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringEncodingTest {

    @Test
    public void default_encoding_test() {
        try {
            System.out.println(decodeText("The façade pattern is a software design pattern.", StandardCharsets.US_ASCII, CodingErrorAction.IGNORE));
        } catch (IOException e) {
            System.out.println("error");
        }
    }

    @Test
    public void set_ignore_test() {
        try {
            Assertions.assertEquals(
                    "The faade pattern is a software design pattern.",
                    decodeText(
                            "The façade pattern is a software design pattern.",
                            StandardCharsets.US_ASCII,
                            CodingErrorAction.IGNORE));
        } catch (IOException e) {
            System.out.println("error");
        }
    }

    @Test
    public void set_replace_test() {
        try {
            Assertions.assertEquals(
                    "The fa��ade pattern is a software design pattern.",
                    decodeText(
                            "The façade pattern is a software design pattern.",
                            StandardCharsets.US_ASCII,
                            CodingErrorAction.REPLACE));
        } catch (IOException e) {
            System.out.println("error");
        }
    }

    @Test
    public void set_report_test() {
        Assertions.assertThrows(
                MalformedInputException.class,
                () -> decodeText(
                        "The façade pattern is a software design pattern.",
                        StandardCharsets.US_ASCII,
                        CodingErrorAction.REPORT));
    }

    private String decodeText(String input, Charset charset,
                              CodingErrorAction codingErrorAction) throws IOException {
        CharsetDecoder charsetDecoder = charset.newDecoder();
        charsetDecoder.onMalformedInput(codingErrorAction);
        return
                new BufferedReader(
                        new InputStreamReader(
                                new ByteArrayInputStream(input.getBytes()),
                                charsetDecoder)).readLine();
    }

    @Test
    public void ascii_encoding_test() {
        try {
            Assertions.assertEquals(convertToBinary("T", "US-ASCII"), "01010100");
            Assertions.assertEquals(convertToBinary("語", "Big5"), "10111011 01111001");
            Assertions.assertEquals(convertToBinary("T", "UTF-32"), "00000000 00000000 00000000 01010100");
            Assertions.assertEquals(convertToBinary("T", "UTF-8"), "01010100");
            Assertions.assertEquals(convertToBinary("語", "UTF-8"), "11101000 10101010 10011110");
        } catch (UnsupportedEncodingException e) {
            System.out.println("error");
        }
    }

    private String convertToBinary(String input, String encoding)
            throws UnsupportedEncodingException {
        byte[] encodedInput = input.getBytes(encoding);
        return IntStream.range(0, encodedInput.length)
                .mapToObj(i -> String.format("%8s", Integer.toBinaryString(encodedInput[i] & 0xFF))
                        .replace(" ", "0")) // 음수를 피하기 위해 & 0xFF 사용
                .collect(Collectors.joining(" "));
    }

}
