import org.junit.Assert
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class PetoohTranslatorTest {
    private val testObj = PetoohTranslator()
    private val fileKo = "Petooh.txt"
    private val fileOut = "output.txt"

    /**
    TEST METHOD: translateToToken // FIXME: ясное дело, что это ТЕСТЪ МЕТОДЪ. Удали во всех подписях
    This test uses translateToToken with normal parameters(file found) and checks whether the answer is Array<Token>. // FIXME: у тебя уже нормальная проверка, исправь подпись
     */
    @Test fun testFileToToken() {
        File(fileKo).writeText("Kk koKuduhkoKokuKud ko KO 099991d dsv shdb Kudah kukarek ku ko KOd Ko")
        val arrToken = testObj.translateToToken(fileKo)
        var bool = false // FIXME: уже не смешно; +2
        if (arrToken is Array<Token>) {
            bool = true
        }
        assertEquals(true, bool) // FIXME: assert(имяПеременной)

        File("Petooh2.txt").writeText("Ko kO Kud Kudah 0kkkukarek")
        val actualTokens = testObj.translateToToken("Petooh2.txt")
        val expectedTokens = arrayOf(Token.PLUS, Token.MINUS, Token.BEGIN, Token.RIGHT, Token.READ)

        Assert.assertArrayEquals(expectedTokens, actualTokens)
    }

    /**
    TEST METHOD: translateToToken
    This test uses setFileTiToken and call ecs:NotFoundFile, check answer on empty array. // FIXME: какой ecs?
     */
    @Test fun returnNotFoundFile() {
        val arrToken = testObj.translateToToken("NotFile.not")
        assertEquals(true, arrToken.isEmpty())
    }

    /**
    TEST METHOD: translateToKoko
    This test uses translateToKoko with normal parameters and checks whether the answer is Array<Token>.
     */
    @Test fun testFileToKoko() {
        val testObj = PetoohTranslator()
        File(fileKo).writeText("Kk koKuduhkoKokuKud ko KO 099991d dsv shdb Kudah kukarek ku ko KOd Ko")
        val arrToken = testObj.translateToToken(fileKo)
        testObj.translateToKoko(arrToken, fileOut)
        val AnotherarrToken = testObj.translateToToken(fileKo)
        assert(AnotherarrToken is Array<Token>)
    }

    /**
    TEST METHOD: translateToKoko
    This test uses translateToKoko with empty array and check output file on empty or not empty.
     */
    @Test fun emptyArrayTokens() {
        val arrEmpty = emptyArray<Token>()
        testObj.translateToKoko(arrEmpty, "output2.txt")
        val retStr = File("output2.txt").readText()
        assertEquals("", retStr)
    }
}