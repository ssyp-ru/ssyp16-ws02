package ssyp.ws02.petooh
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class InterpreterTest {

/*
    @Test fun multiplyingTest() {
        val bf = BrainfuckTranslator()
        val testFile = File("test.txt")
        testFile.writeText(",>,<[>[>+>+<<-]>[<+>-]<<-]>>>.")
        val arrToken = bf.translateToTokens("test.txt")
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
        val testFile = File("test.txt")
        testFile.writeText("+++>+[<[>++<-]>-<]>.")
        val arrToken = bf.translateToTokens("test.txt")
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
        val testFile = File("test.txt")
        testFile.writeText("++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>.")
        val arrToken = bf.translateToTokens("test.txt")
        val write = MyPrintStream()
        val strb = StringBuilder()
        val interp = Interpreter(MyInputStream(strb.toString()), write)
        interp.interpret(arrToken)
        assertEquals("Hello World!\n", write.sb.toString())
    }

    *@Test fun emptyFileTest(){
        val bf = BrainfuckTranslator()
        val testFile = File("test.txt")
        testFile.writeText("")
        val arrToken = bf.translateToTokens("test.txt")
        val write = MyPrintStream()}*/

    @Test fun emptyFileTest() {
        val bf = BrainfuckTranslator()
        val testFile = File("test.txt")
        testFile.writeText("+>,+>,<[<+>>[<<->>->+>]>[<<+>>[<+>-]]<<<<[<]>>-]<-.")
        val arrToken = bf.translateToTokens("test.txt")
        val write = MyPrintStream()
        val strb = StringBuilder()
        strb.append(80.toChar())
        strb.append(10.toChar())
        val interp = Interpreter(MyInputStream(strb.toString()), write)
        interp.interpret(arrToken)
        assertEquals(8.toChar().toString(), write.sb.toString())
    }
}

