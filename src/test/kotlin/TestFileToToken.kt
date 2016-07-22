import org.junit.Assert
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

//!!!!!!!!!!!! Delete txt files for next tests

open class TestFileToToken {
    val testObj = PetoohTranslater()
    val fileKo = "Petooh.txt"
    val fileOut = "output.txt"

    /**
    TEST METHOD: setFileToToken
    This test uses setFileToToken with normal parameters(file found) and checks whether the answer is Array<Token>
     */
    @Test fun testFileToToken() {
        File(fileKo).writeText("Kk koKuduhkoKokuKud ko KO 099991d dsv shdb Kudah kukarek ku ko KOd Ko")
        val arrToken = testObj.setFileToToken(fileKo)
        var bool = false
        if (arrToken is Array<Token>) {
            bool = true
        }
        assertEquals(true, bool)

        File(fileKo + "2").writeText("Ko kO Kud Kudah 0kkkukarek")
        val actualTokens = testObj.setFileToToken(fileKo + "2")
        val expectedTokens = arrayOf(Token.MINUS, Token.PLUS, Token.BEGIN, Token.RIGHT, Token.WRITE)

        Assert.assertArrayEquals(
                expectedTokens,
                actualTokens
        )
    }

    /**
    TEST METHOD: setFileToToken
    This test uses setFileTiToken and call ecs:NotFoundFile, check answer on empty array
     */
    @Test fun returnNotFoundFile() {
        val arrToken = testObj.setFileToToken("NotFile.not")
        assertEquals(true, arrToken.isEmpty())
    }

    /**
    TEST METHOD: setFileToKoko
    This test uses setFileToKoko with normal parameters and checks whether the answer is Array<Token>
     */
    @Test fun testFileToKoko() {
        val testObj = PetoohTranslater()
        File(fileKo).writeText("Kk koKuduhkoKokuKud ko KO 099991d dsv shdb Kudah kukarek ku ko KOd Ko")
        val arrToken = testObj.setFileToToken(fileKo)
        testObj.setFileToKoko(arrToken, fileOut)
        val AnotherarrToken = testObj.setFileToToken(fileKo)
        var bool = false
        if (AnotherarrToken is Array<Token>) {
            bool = true
        }
        assertEquals(true, bool)
    }

    /**
    TEST METHOD: setFileToKoko
    This test uses setFileToKoko with empty array and check output file on empty or not empty
     */
    @Test fun emptyArrayTokens() {
        val arrEmpty = emptyArray<Token>()
        testObj.setFileToKoko(arrEmpty, "output2.txt")
        val retStr = File("output2.txt").readText()
        assertEquals("", retStr)
    }
}