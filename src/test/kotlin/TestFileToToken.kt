import org.junit.Test
import kotlin.test.assertEquals


open class TestFileToToken {

    @Test fun testAllMethod() {

        val testObj = PetoohAndToken()
        val arrToken = testObj.setFileToToken("Petooh.txt")

        var bool = false

        if (arrToken is Array<Token>) {
            bool = true
        }

        assertEquals(true, bool)


    }


}