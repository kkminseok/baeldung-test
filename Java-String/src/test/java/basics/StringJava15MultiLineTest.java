package basics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringJava15MultiLineTest {

    @Test
    void givenAnOldStyleMultilineString_whenComparing_thenEqualsTextBlock() {
        String expected = "<html>\n"
                + "\n"
                + "    <body>\n"
                + "        <span>example text</span>\n"
                + "    </body>\n"
                + "</html>";
        Assertions.assertEquals(getBlockOfHtml(), expected);
    }

    @Test
    void givenAnOldStyleString_whenComparing_thenEqualsTextBlock() {
        String expected = "<html>\n\n    <body>\n        <span>example text</span>\n    </body>\n</html>";
        Assertions.assertEquals(getBlockOfHtml(), expected);
    }

    @Test
    void givenAnIndentedString_thenMatchesIndentedOldStyle() {
        Assertions.assertEquals(getNonStandardIndent(), "    Indent\n");
    }

    @Test
    void text_block_escape_test() {
        Assertions.assertEquals(getTextWithEscapes(), "\"fun\" with\n" +
                "whitespace\n" +
                "and other escapes \"\"\"\n");
    }

    @Test
    void givenATextWithCarriageReturns_thenItContainsBoth() {
        Assertions.assertEquals(getTextWithCarriageReturns(), "separated with\r\n" +
                "carriage returns");
    }

    @Test
    void givenAStringWithEscapedNewLines_thenTheResultHasNoNewLines() {
        String expected = "This is a long test which looks to have a newline but actually does not";
        Assertions.assertEquals(getIgnoredNewLines(), expected);
    }

    @Test
    void givenAStringWithEscapesSpaces_thenTheResultHasLinesEndingWithSpaces() {
        String expected = "line 1\nline 2         \n";
        Assertions.assertEquals(getEscapedSpaces(), expected);
    }

    @Test
    void formatting_multiLine_test() {
        String expected = "Some parameter: end\nText\n";
        Assertions.assertEquals(getFormattedText("end"), expected);
    }


    public String getBlockOfHtml() {
        return """
                <html>

                    <body>
                        <span>example text</span>
                    </body>
                </html>""";
    }

    public String getNonStandardIndent() {
        return """
                    Indent
                """;
    }

    public String getTextWithEscapes() {
        return """
                "fun" with
                whitespace
                and other escapes \"""
                """;
    }

    public String getTextWithCarriageReturns() {
        return """
                separated with\r
                carriage returns""";
    }

    public String getIgnoredNewLines() {
        return """
                This is a long test which looks to \
                have a newline but actually does not""";
    }

    public String getEscapedSpaces() {
        return """
                line 1        
                line 2        \s
                """;
    }

    public String getFormattedText(String parameter) {
        return """
            Some parameter: %s
            Text
            """.formatted(parameter);
    }

}
