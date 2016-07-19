
import org.junit.Assert
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

/**

Class BrainfuckTranslatorTests tests functions from BrinfuckTranslator
 */


class BrainfuckTranslatorTests {
    private val brainfuckTranslator = BrainfuckTranslator()
    private val testTokens = arrayOf(Token.PLUS, Token.MINUS, Token.RIGHT, Token.LEFT, Token.WRITE, Token.READ, Token.BEGIN, Token.END)
    private val name = "test.txt"
    private val testFile = File("test.txt")

    /**
    testBrainfuckToToken tests translation from Brainfuck to Token.
     */
    @Test fun testBrainfuckToToken() {
        testFile.writeText("+-><.,[]")
        Assert.assertArrayEquals(testTokens, brainfuckTranslator.translateToTokens(name)
        )
    }
    /**
    returnFileNotfound tests the case when the file does not exist.
     */
    @Test fun returnFileNotFound() {
        val token = brainfuckTranslator.translateToTokens("NotFile.not")
        assert(token.isEmpty())
    }

    /**
    testTokenToBrainfuck tests translation from Token to Brainfuck.
     */
    @Test fun testTokenToBrainfuck() {
        brainfuckTranslator.translateToBrainfuck(testTokens, name)
        assertEquals("+-><.,[]", testFile.readText())

    }
    /**
     testEmptyArray tests the case when the array is empty.
     */
    @Test fun testEmptyArray() {
        brainfuckTranslator.translateToBrainfuck(emptyArray(), name)
        assertEquals("", testFile.readText())
    }
}

