import org.junit.Test
import kotlin.test.assertEquals


class TestFileToToken {

    @Test fun testAllMethod() {

        val TestObj = PetoohAndToken()
        val arrToken = TestObj.setFileToToken("Petooh.txt")

        var bool = false

        if (arrToken is Array<Token>) {
            bool = true
        }

        assertEquals(true, bool)


    }


}