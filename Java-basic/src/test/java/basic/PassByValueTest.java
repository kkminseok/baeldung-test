package basic;

import basic.model.Foo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PassByValueTest {

    @Test
    public void 원시타입은_함수에서수정해도_원본값은변하지_않는다() {
        int x = 1;
        int y = 2;

        Assertions.assertEquals(x,1);
        Assertions.assertEquals(y,2);

        modify(x,y);

        Assertions.assertEquals(x,1);
        Assertions.assertEquals(y,2);
    }

    @Test
    public void 참조타입은_함수에서수정하면_원본값이변한다() {
        Foo a = new Foo(1);
        Foo b = new Foo(1);

        Assertions.assertEquals(a.num,1);
        Assertions.assertEquals(b.num,1);

        modify(a,b);

        Assertions.assertEquals(a.num,2);
        Assertions.assertEquals(b.num,1);
    }

    public static void modify(Foo a1, Foo b1) {
        a1.num++;

        b1 = new Foo(1);
        b1.num++;
    }

    public static void modify(int x1, int y1) {
        x1 = 5;
        y1= 10;
    }


}




