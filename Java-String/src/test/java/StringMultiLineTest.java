import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StringMultiLineTest {

    String newLine;

    @BeforeEach
    public void setUp() {
        newLine = System.getProperty("line.separator");
    }

    @Test
    public void text_block_test() {
        String sentence = """
                happy new year
                Stay healthy always.
                2025-01-30
                """;
        System.out.println(sentence);
    }

    @Test
    public void text_concat_test() {
        String sentence = "happy new year"
                .concat(newLine)
                .concat("Stay healthy always.")
                .concat(newLine)
                .concat("2025-01-30")
                .concat(newLine);
        System.out.println(sentence);
    }

    @Test
    public void text_operator_test() {
        String sentence = "happy new year"
                + newLine
                + "Stay healthy always."
                + newLine
                + "2025-01-30"
                + newLine;
        System.out.println(sentence);
    }

    @Test
    public void join_string_test() {
        String sentence = String.join(newLine
                , "happy new year"
                , "Stay healthy always."
                , "2025-01-30");
        System.out.println(sentence);
    }

    @Test
    public void builder_multiline_test() {
        String sentence = new StringBuilder()
                .append("happy new year")
                .append(newLine)
                .append("Stay healthy always.")
                .append(newLine)
                .append("2025-01-30")
                .toString();
        System.out.println(sentence);
    }

    @Test
    public void writer_test() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter =  new PrintWriter(stringWriter);
        printWriter.println("happy new year");
        printWriter.println("Stay healthy always.");
        printWriter.println("2025-01-30");
        System.out.println(stringWriter.toString());
    }

    @Test
    public void read_file_test() {
        try {
            System.out.println(new String(Files.readAllBytes(Paths.get("src/test/resources/multi-line.txt"))));
        } catch (IOException e) {
            System.out.println("error");
        }
    }
}
