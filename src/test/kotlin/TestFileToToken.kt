import org.junit.Test
import java.io.File
import java.io.FileNotFoundException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
//!!!!!!!!!!!! DELETE FILE.TXT,NEXT TEST

open class TestFileToToken {
    val testObj = PetoohTranslater()
    val fileKo = "Petooh.txt"
    val fileOut = "output.txt"

    /**
     TEST METHOD: setFileToToken
     This test use setFileToToken with NORMAL parameters(FILE FOUND) and check answer on type(Array<Token>)
     */
    @Test fun testFileToToken() {
        val newFile = File(fileKo).writeText("Kk koKuduhkoKokuKud ko KO 099991d dsv shdb Kudah kukarek ku ko KOd Ko")

        val arrToken = testObj.setFileToToken(fileKo)

        var bool = false

        if (arrToken is Array<Token>) {
            bool = true
        }
        assertEquals(true, bool)

    }

    /**
    TEST METHOD: setFileToToken
    This test use setFileTiToken and call ecs:NotFoundFile , check answer on empty array
     */
    @Test fun returnNotFoundFile() {

        val arrToken = testObj.setFileToToken("NotFile.not")
        assertEquals(true,arrToken.isEmpty())
    }

    /**
    TEST METHOD: setFileToKoko
    This test use setFileToKoko with NORMAL parameters(FILE FOUND) and check answer on type(Array<Token>)
     */
    @Test fun testFileToKoko() {

        val testObj = PetoohTranslater()

        val newFile = File(fileKo).writeText("Kk koKuduhkoKokuKud ko KO 099991d dsv shdb Kudah kukarek ku ko KOd Ko")
        val arrToken = testObj.setFileToToken(fileKo)

        testObj.setFileToKoko(arrToken,fileOut)

        val AnotherarrToken = testObj.setFileToToken(fileKo)

        var bool = false

        if (AnotherarrToken is Array<Token>) {
            bool = true
        }

        assertEquals(true, bool)


    }

    /**
    TEST METHOD: setFileToKoko
    This test use setFileToKoko with empty array and check output file on empty or not empty
     */
    @Test fun emptyArrayTokens(){

        val arrEmpty = emptyArray<Token>()

        testObj.setFileToKoko(arrEmpty,"output2.txt")

        val retStr = File("output2.txt").readText()

        assertEquals("",retStr)

    }


}