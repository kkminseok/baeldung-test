package basics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
 * 참고: https://www.baeldung.com/java-string-initialization
 */
public class StringBasicInitTest {

    String fieldString;

    @Test
    void String_생성방법() {
        String usingNew = new String("minseok");
        String usingLiteral = "minseok";
    }


    @Test
    void 지역변수와_멤버변수차이() {
        //given
        String localVarString;

        //when
        // System.out.println(localVarString); <- Compile error

        //then
        assertNull(fieldString);
    }

    @Test
    void 지역변수_null초기화시_멤버변수_초기값과같아야한다() {
        //given
        String localVarString = null;

        //when

        //then
        assertEquals(localVarString, fieldString);
    }

    @Test
    void Literals_로선언시_동일성을지닌다() {
        //given
        String literalOne = "Minseok";
        String literalTwo = "Minseok";

        //then
        assertTrue(literalOne == literalTwo);
    }

    @Test
    void new_로선언시_동일성을지니지않는다() {
        //given
        String literalOne = new String("Minseok");
        String literalTwo = new String("Minseok");

        //then
        assertFalse(literalOne == literalTwo);
    }

    @Test
    void 빈_문자열_선언방법_및_동일성_동등성_확인() {
        //given
        String emptyLiteral = "";
        String emptyNewString = new String("");
        String emptyString = new String();

        //then
        assertFalse(emptyLiteral == emptyNewString);
        assertFalse(emptyLiteral == emptyString);
        assertFalse(emptyNewString == emptyString);
        assertEquals(emptyLiteral, emptyNewString);
        assertEquals(emptyLiteral, emptyString);
    }

    @Test
    void null_인경우_print에는null이출력되는이유() {
        /*
         * 실제 System.out.println()의 구현방식
         */

        //given
        Object localVarString=null;

        //when
        String str = getString(String.valueOf(localVarString));

        //then
        System.out.println(str);
    }

    private String getString(Object obj) {
        return (obj == null) ? "null" : obj.toString();
    }


}
