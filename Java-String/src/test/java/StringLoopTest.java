import org.junit.jupiter.api.Test;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class StringLoopTest {

    @Test
    public void forLoopTest() {
        String str = "hello minseok";

        for(int i=0; i<str.length(); i++){
            System.out.printf("%c", str.charAt(i));
        }
    }

    @Test
    public void toCharArrayTest() {
        String str = "hello minseok";
        for(char c : str.toCharArray()) {
            System.out.printf("%c", c);
        }
    }

    @Test
    public void streamTest() {
        String str = "hello minseok";
        str.chars().forEach( c-> {
            System.out.print((char) c);
        });
    }

    @Test
    public void characterIteratorTest() {
        String str = "hello minseok";
        CharacterIterator ci = new StringCharacterIterator(str);
        while (ci.current() != CharacterIterator.DONE) {
            System.out.printf("%c", ci.current());
            ci.next();
        }
    }
}
