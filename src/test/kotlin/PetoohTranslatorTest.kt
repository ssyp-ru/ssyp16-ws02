import org.junit.Assert
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class PetoohTranslatorTest {
    private val testObj = PetoohTranslator()
    private val fileKo = "Petooh.txt"
    private val fileOut = "output.txt"
    /**
     *This test uses translateToToken and checks whether the answer is Array<Token>.
     */
    @Test fun testFileToToken() {
        File(fileKo).writeText("Kk koKuduhkoKokuKud ko KO 099991d dsv shdb Kudah kukarek ku ko KOd Ko")
        val arrToken = testObj.translateToToken(fileKo)
        var isArray = false
        if (arrToken is Array<Token>) {
            isArray = true
        }
        assert(isArray)

        File("Petooh2.txt").writeText("Ko kO Kud Kudah 0kkkukarek")
        val actualTokens = testObj.translateToToken("Petooh2.txt")
        val expectedTokens = arrayOf(Token.PLUS, Token.MINUS, Token.BEGIN, Token.RIGHT, Token.READ)

        Assert.assertArrayEquals(expectedTokens, actualTokens)
    }
    /**
    This test uses setFileTiToken and call NotFoundFile, check answer on empty array.
     */
    @Test fun returnNotFoundFile() {
        val arrToken = testObj.translateToToken("NotFile.not")
        assertEquals(true, arrToken.isEmpty())
    }
    /**
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
    This test uses translateToKoko with empty array and check output file on empty or not empty.
     */
    @Test fun emptyArrayTokens() {
        val arrEmpty = emptyArray<Token>()
        testObj.translateToKoko(arrEmpty, "output2.txt")
        val retStr = File("output2.txt").readText()
        assertEquals("", retStr)
    }
}