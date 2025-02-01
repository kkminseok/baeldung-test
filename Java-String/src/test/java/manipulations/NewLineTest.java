package manipulations;

import org.junit.jupiter.api.Test;

public class NewLineTest {
    @Test
    public void new_line_window() {
        String line1 = "Humpty Dumpty sat on a wall.";
        String line2 = "Humpty Dumpty had a great fall.";
        String rhyme = line1 + "\r" + line2;
        System.out.println(rhyme);
    }

    @Test
    public void new_line_by_system_option() {
        String line1 = "Humpty Dumpty sat on a wall.";
        String line2 = "Humpty Dumpty had a great fall.";
        String rhyme = line1 + System.lineSeparator() + line2;
        String rhyme2 = line1 + System.getProperty("line.separator") + line2;
        System.out.println(rhyme);
        System.out.println(rhyme2);
    }

    @Test
    public void new_line_by_character() {
        String rhyme = String.format("Humpty Dumpty sat on a wall.%nHumpty Dumpty had a great fall.");
        System.out.println(rhyme);
    }
}
