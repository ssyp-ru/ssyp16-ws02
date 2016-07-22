import org.junit.Test
import kotlin.test.assertEquals

class InterpreterTest {



    @Test fun testRightAndLeftToken() {
        val arrToken = Array (30001, { Token.RIGHT })
        val write = MyPrintStream(StringBuilder())
        val interp = Interpreter(arrToken, MyInputStream(""), write)
        interp.interpret()
        assertEquals("45", write.sb.toString())

    }

    @Test fun finalTest() {
        val arrToken = arrayOf(Token.READ, Token.RIGHT, Token.READ, Token.LEFT, Token.BEGIN, Token.RIGHT, Token.BEGIN, Token.RIGHT, Token.PLUS, Token.RIGHT, Token.PLUS, Token.LEFT, Token.LEFT, Token.MINUS, Token.END, Token.RIGHT, Token.BEGIN, Token.LEFT, Token.PLUS, Token.RIGHT, Token.MINUS, Token.END, Token.LEFT, Token.LEFT, Token.MINUS, Token.END, Token.RIGHT, Token.RIGHT, Token.RIGHT, Token.WRITE)
        val interp = Interpreter(arrToken, read, write)
        interp.interpret()
        assertEquals((read.myReturn(1) * read.myReturn(2)).toString(), write.myReturn.toString())
    }
}
