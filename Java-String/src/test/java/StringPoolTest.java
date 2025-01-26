import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringPoolTest {

    @Test
    public void sameMemoryTest() {
        //given
        String s1 = "minseok";
        String s2 = "minseok";

        //then
        Assertions.assertTrue(s1 == s2);
    }

    @Test
    public void new로_생성한객체는_같은메모리를_공유하지않는다() {
        //given
        String s1 = new String("minseok");
        String s2 = new String("minseok");

        //then
        Assertions.assertFalse(s1 == s2);
    }

    @Test
    public void new로_생성한객체와_리터럴String은_동일하지않다() {
        //given
        String s1 = "minseok";
        String s2 = new String("minseok");

        //then
        Assertions.assertFalse(s1 == s2);
    }

    @Test
    public void InterningTest() {
        //given
        String s1 = "minseok";
        String s2 = new String("minseok");

        //when
        Assertions.assertFalse(s1 == s2);
        s2 = s2.intern();

        //then
        Assertions.assertTrue(s1 == s2);
    }

}
