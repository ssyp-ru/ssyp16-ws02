import org.junit.Test
import kotlin.test.assertEquals

class TestTokenToPetooh {

    @Test fun testAllMethod() {

        val testObj = PetoohAndToken()
        val arrToken = testObj.setFileToToken("Petooh.txt")

        testObj.setFileToKoko(arrToken,"output.txt")

        val AnotherarrToken = testObj.setFileToToken("Petooh.txt")

        var bool = false

        if (AnotherarrToken is Array<Token>) {
            bool = true
        }

        assertEquals(true, bool)


    }


}