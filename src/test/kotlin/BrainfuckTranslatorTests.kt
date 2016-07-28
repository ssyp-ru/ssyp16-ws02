import org.junit.Assert
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class BrainfuckTranslatorTests {
    private val brainfuckTranslator = BrainfuckTranslator()
    private val testTokens = arrayOf(Token.PLUS, Token.MINUS, Token.RIGHT, Token.LEFT, Token.WRITE, Token.READ, Token.BEGIN, Token.END)
    private val name = "test.txt"
    private val testFile = File("test.txt")

    @Test fun testBrainfuckToToken() {
        testFile.writeText("+-><.,[]")
        Assert.assertArrayEquals(testTokens, brainfuckTranslator.translateToTokens(name))
    }

    @Test fun returnFileNotFound() {
        val token = brainfuckTranslator.translateToTokens("NotFile.not")
        assert(token.isEmpty())
    }

    @Test fun testTokenToBrainfuck() {
        brainfuckTranslator.translateToBrainfuck(testTokens, name)
        assertEquals("+-><.,[]", testFile.readText())

    }

    @Test fun testEmptyArray() {
        brainfuckTranslator.translateToBrainfuck(emptyArray(), name)
        assertEquals("", testFile.readText())
    }
}

