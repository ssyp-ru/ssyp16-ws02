import org.junit.Test
import kotlin.test.assertEquals

class InterpreterTest {


    @Test fun multiplyingTest() {
        val bf = BrainfuckTranslator()
        val arrToken = bf.translateToTokens("kukarek.txt")
        val write = MyPrintStream()
        val strb = StringBuilder()
        strb.append(" ")
        strb.append(33.toChar())
        val interp = Interpreter(MyInputStream(strb.toString()), write)
        interp.interpret(arrToken)
        assertEquals((' '.toInt() * 33).toByte().toChar().toString(), write.sb.toString())
    }

    @Test fun secondMultiplyingTest() {
        val bf = BrainfuckTranslator()
        val arrToken = bf.translateToTokens("secondTest.txt")
        val write = MyPrintStream()
        val strb = StringBuilder()
        strb.append(" ")
        strb.append(33.toChar())
        val interp = Interpreter(MyInputStream(strb.toString()), write)
        interp.interpret(arrToken)
        assertEquals(6.toChar().toString(), write.sb.toString())
    }

    @Test fun helloWorldTest() {
        val bf = BrainfuckTranslator()
        val arrToken = bf.translateToTokens("thirdTest.txt")
        val write = MyPrintStream()
        val strb = StringBuilder()
        val interp = Interpreter(MyInputStream(strb.toString()), write)
        interp.interpret(arrToken)
        assertEquals("Hello World!\n", write.sb.toString())
    }

    @Test fun emptyFileTest(){
        val bf = BrainfuckTranslator()
        val arrToken = bf.translateToTokens("emptyFile.txt")
        val write = MyPrintStream()
        val strb = StringBuilder()
        val interp = Interpreter(MyInputStream(strb.toString()), write)
        interp.interpret(arrToken)
        assertEquals("", write.sb.toString())
    }

}

