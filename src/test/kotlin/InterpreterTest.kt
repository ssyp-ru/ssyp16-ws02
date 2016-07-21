import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class InterpreterTest {
    @Test fun interpretRight() {
        val s = Array<Token> (30001, { Token.RIGHT })
        val write = System.out.writer()
        val read = System.`in`.reader()
        val interp = Interpreter(s, read, write)
        interp.interpret()
        assertEquals(1, interp.current)

    }

    @Test fun interpretLeft() {
        val s = Array<Token> (2, { Token.LEFT })
        val write = System.out.writer()
        val read = System.`in`.reader()
        val interp = Interpreter(s, read, write)
        interp.interpret()
        assertEquals(29998, interp.current)
    }

    @Test fun interpretPlus() {
        val s = Array<Token> (257, { Token.PLUS })
        val write = System.out.writer()
        val read = System.`in`.reader()
        val interp = Interpreter(s, read, write)
        interp.interpret()
        assertEquals(1, interp.array[0])
    }

    @Test fun interpretMinus() {
        val s = Array<Token> (1, { Token.MINUS })
        val write = System.out.writer()
        val read = System.`in`.reader()
        val interp = Interpreter(s, read, write)
        interp.interpret()
        assertEquals(-1, interp.array[0])
    }

    @Test fun interpretBegin() {
        val s = arrayOf(Token.MINUS, Token.BEGIN, Token.MINUS, Token.MINUS, Token.RIGHT, Token.END, Token.MINUS)
        val write = System.out.writer()
        val read = System.`in`.reader()
        val interp = Interpreter(s, read, write)
        interp.interpret()
        assertEquals(-1, interp.array[1])
    }

    @Test fun interpretEnd() {
        val s = arrayOf(Token.PLUS, Token.PLUS, Token.PLUS, Token.BEGIN, Token.PLUS, Token.BEGIN, Token.MINUS, Token.MINUS, Token.END, Token.END)
        val write = System.out.writer()
        val read = System.`in`.reader()
        val interp = Interpreter(s, read, write)
        interp.interpret()
        assertEquals(0, interp.array[0])
    }

    @Test fun interpretWriter() {
        val s = arrayOf(Token.MINUS, Token.MINUS, Token.WRITE)
        val write = MyWriter()
        val read = MyReader()
        val interp = Interpreter(s, read, write)
        interp.interpret()
        assertEquals("-2", write.r)
    }

    @Test fun interpretReader() {
        val s = arrayOf(Token.READ)
        val write = MyWriter()
        val read = MyReader()
        val interp = Interpreter(s, read, write)
        interp.interpret()
        assertEquals(read.a.toByte(), interp.array[0])

    }
}
