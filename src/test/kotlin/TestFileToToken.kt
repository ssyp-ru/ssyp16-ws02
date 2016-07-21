import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by Vedrovski on 21.07.2016.
 */
class TestFileToToken {

    @Test fun testAllMethod(){

        val TestObj = PetoohAndToken()
        val arrToken = TestObj.setFileToToken("Petooh.txt")

        var bool = false

        if(arrToken is Array<Token>)
        {
            bool = true
        }

        assertEquals(true,bool)


    }

}