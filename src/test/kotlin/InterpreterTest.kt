import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class InterpreterTest {
    @Test fun interpretRight() {
        val arrToken = Array<Token> (30001, { Token.RIGHT })
        val write = MyWriter()
        val read = MyReader()
        val interp = Interpreter(arrToken, read, write)
        interp.interpret()
        assertEquals(1, interp.current)

    }

    @Test fun interpretLeft() {
        val arrToken = Array<Token> (2, { Token.LEFT })
        val write = MyWriter()
        val read = MyReader()
        val interp = Interpreter(arrToken, read, write)
        interp.interpret()
        assertEquals(29998, interp.current)
    }

    @Test fun interpretPlus() {
        val arrToken = Array<Token> (257, { Token.PLUS })
        val write = MyWriter()
        val read = MyReader()
        val interp = Interpreter(arrToken, read, write)
        interp.interpret()
        assertEquals(1, interp.array[0])
    }

    @Test fun interpretMinus() {
        val arrToken = Array<Token> (1, { Token.MINUS })
        val write = MyWriter()
        val read = MyReader()
        val interp = Interpreter(arrToken, read, write)
        interp.interpret()
        assertEquals(-1, interp.array[0])
    }

    @Test fun interpretBegin() {
        val arrToken = arrayOf(Token.MINUS, Token.BEGIN, Token.MINUS, Token.MINUS, Token.RIGHT, Token.END, Token.MINUS)
        val write = MyWriter()
        val read = MyReader()
        val interp = Interpreter(arrToken, read, write)
        interp.interpret()
        assertEquals(-1, interp.array[1])
    }

    @Test fun interpretEnd() {
        val arrToken = arrayOf(Token.PLUS, Token.PLUS, Token.PLUS, Token.BEGIN, Token.PLUS, Token.BEGIN, Token.MINUS, Token.MINUS, Token.END, Token.END)
        val write = MyWriter()
        val read = MyReader()
        val interp = Interpreter(arrToken, read, write)
        interp.interpret()
        assertEquals(0, interp.array[0])
    }

    @Test fun interpretWriter() {
        val arrToken = arrayOf(Token.MINUS, Token.MINUS, Token.WRITE)
        val write = MyWriter()
        val read = MyReader()
        val interp = Interpreter(arrToken, read, write)
        interp.interpret()
        assertEquals("-2", write.myReturn)
    }

    @Test fun interpretReader() {
        val arrToken = arrayOf(Token.READ)
        val write = MyWriter()
        val read = MyReader()
        val interp = Interpreter(arrToken, read, write)
        interp.interpret()
        assertEquals(read.myReturn(1).toByte(), interp.array[0])

    }

    @Test fun finalTest(){
        val arrToken = arrayOf(Token.READ, Token.RIGHT, Token.READ, Token.LEFT, Token.BEGIN, Token.RIGHT, Token.BEGIN, Token.RIGHT, Token.PLUS, Token.RIGHT, Token.PLUS, Token.LEFT, Token.LEFT, Token.MINUS, Token.END, Token.RIGHT, Token.BEGIN, Token.LEFT, Token.PLUS, Token.RIGHT, Token.MINUS, Token.END, Token.LEFT, Token.LEFT, Token.MINUS, Token.END, Token.RIGHT, Token.RIGHT, Token.RIGHT, Token.WRITE)
        val write = MyWriter()
        val read = MyReader()
        val interp = Interpreter(arrToken, read, write)
        interp.interpret()
        assertEquals((read.myReturn(1)*read.myReturn(2)).toString(), write.myReturn)
    }
}
