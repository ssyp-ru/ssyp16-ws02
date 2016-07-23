import org.junit.Test
import kotlin.test.assertEquals

class InterpreterTest {

    @Test fun finalTest() {
    val bf = BrainfuckTranslator()
    val arrToken = bf.translateToTokens("kukarek.txt")
    //val arrToken = arrayOf(Token.READ, Token.RIGHT, Token.READ, Token.LEFT, Token.BEGIN, Token.RIGHT, Token.BEGIN, Token.RIGHT, Token.PLUS, Token.RIGHT, Token.PLUS, Token.LEFT, Token.LEFT, Token.MINUS, Token.END, Token.RIGHT, Token.BEGIN, Token.LEFT, Token.PLUS, Token.RIGHT, Token.MINUS, Token.END, Token.LEFT, Token.LEFT, Token.MINUS, Token.END, Token.RIGHT, Token.RIGHT, Token.RIGHT, Token.WRITE)
    val write = MyPrintStream(StringBuilder())
    val strb = StringBuilder()
    strb.append(" ")
    strb.append(33.toChar())
    val interp = Interpreter(arrToken, MyInputStream(strb.toString()), write)
    interp.interpret()
    assertEquals((' '.toInt() * 33).toByte().toChar().toString(), write.sb.toString())
    }
}

