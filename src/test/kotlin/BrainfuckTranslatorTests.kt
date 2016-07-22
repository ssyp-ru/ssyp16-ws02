import org.junit.Test
import java.io.File
import java.io.FileNotFoundException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class BrainfuckTranslatorTests {
    val brainfuckTranslator = BrainfuckTranslator()
    val testTokens = arrayOf(Token.PLUS, Token.MINUS, Token.PLUS)
    val name = "test.txt"
    val testFile = File("test.txt")
    @Test fun testBrainfuckToToken() {
        testFile.writeText("+-+")
        assertEquals(testTokens, brainfuckTranslator.translateToTokens(name)
        )
    }

    @Test fun fileNotFound() {
        assertFailsWith<FileNotFoundException> {
            val token = brainfuckTranslator.translateToTokens("NotFile.not")
        }

    }

    @Test fun returnFileNotFound() {
        val token = brainfuckTranslator.translateToTokens("NotFile.not")
        assertEquals(true, token.isEmpty())
    }

    @Test fun testTokenToBrainfuck() {
        brainfuckTranslator.translateToBrainfuck(testTokens, name)
    }
}

