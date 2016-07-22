import org.junit.Assert
import org.junit.Test
import java.io.File
import java.io.FileNotFoundException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 Class BrainfuckTranslatorTests tests functions from BrinfuckTranslator.
 */
class BrainfuckTranslatorTests {
    val brainfuckTranslator = BrainfuckTranslator()
    val testTokens = arrayOf(Token.PLUS, Token.MINUS, Token.RIGHT, Token.LEFT, Token.WRITE, Token.READ, Token.BEGIN, Token.END)
    val name = "test.txt"
    val testFile = File("test.txt")
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
        assertEquals(true, token.isEmpty())
    }

    /**
     testTokenToBrainfuck tests translation from Token to Brainfuck.
     */
    @Test fun testTokenToBrainfuck() {
        brainfuckTranslator.translateToBrainfuck(testTokens, name)
        assertEquals("+-><.,[]",testFile.readText())
    }
}

