import kotlin.test.Test

class TutorialTest {

    @Test
    fun callDefinedFunctions() {
        println(sum(1,2));
    }

    fun sum(a: Int, b: Int): Int {
        return a+b;
    }

}