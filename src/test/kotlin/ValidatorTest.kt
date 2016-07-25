
import org.junit.Test
import kotlin.test.assertFalse

class ValidatorTest {
    private val validator = Validator()

    @Test fun validatorTestT() {
        val testTokens = arrayOf(Token.PLUS, Token.MINUS, Token.RIGHT, Token.LEFT, Token.WRITE, Token.READ, Token.BEGIN, Token.END)
        assert(validator.check(testTokens))
    }

    @Test fun validatorTestF1() {
        val testTokens = arrayOf(Token.PLUS, Token.MINUS, Token.RIGHT, Token.LEFT, Token.WRITE, Token.READ, Token.BEGIN, Token.BEGIN)
        assertFalse(validator.check(testTokens))
    }

    @Test fun validatorTestF2() {
        val testTokens = arrayOf(Token.BEGIN, Token.BEGIN, Token.END, Token.END, Token.BEGIN)
        assertFalse(validator.check(testTokens))
    }

    @Test fun validatorTestF3() {
        val testTokens = arrayOf(Token.END, Token.BEGIN)
        assertFalse(validator.check(testTokens))
    }
}