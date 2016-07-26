import org.junit.Assert
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

// FIXME: 0) копипастить javadoc у товарища было ошибкой;
// FIXME: 1) строки javadoc-подписей должны начинаться со '*',
// FIXME: 2) не отделяться пустыми строками от подписываемого,
// FIXME: 3) начинаться с глагола и не содержать капитанских имён функций

/**
 * Class BrainfuckTranslatorTests tests functions from BrinfuckTranslator //FIXME: Thank you, Captain! +1
 */
class BrainfuckTranslatorTests {
    private val brainfuckTranslator = BrainfuckTranslator()
    private val testTokens = arrayOf(Token.PLUS, Token.MINUS, Token.RIGHT, Token.LEFT, Token.WRITE, Token.READ, Token.BEGIN, Token.END)
    private val name = "test.txt"
    private val testFile = File("test.txt")

    /**
    testBrainfuckToToken tests translation from Brainfuck to Token. // FIXME: Thank you, Captain! +1
     */
    @Test fun testBrainfuckToToken() {
        testFile.writeText("+-><.,[]")
        Assert.assertArrayEquals(testTokens, brainfuckTranslator.translateToTokens(name)
        )
    }
    /**
    returnFileNotfound tests the case when the file does not exist. // FIXME: Thank you, Captain! +1
     */
    @Test fun returnFileNotFound() {
        val token = brainfuckTranslator.translateToTokens("NotFile.not")
        assert(token.isEmpty())
    }

    /**
    testTokenToBrainfuck tests translation from Token to Brainfuck. // FIXME: Thank you, Captain! +1
     */
    @Test fun testTokenToBrainfuck() {
        brainfuckTranslator.translateToBrainfuck(testTokens, name)
        assertEquals("+-><.,[]", testFile.readText())

    }
    /**
     testEmptyArray tests the case when the array is empty. // FIXME: Thank you, Captain! +1
     */
    @Test fun testEmptyArray() {
        brainfuckTranslator.translateToBrainfuck(emptyArray(), name)
        assertEquals("", testFile.readText())
    }
}

