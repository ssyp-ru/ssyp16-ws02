import org.junit.Test
import org.junit.Assert
import java.util.*
import kotlin.test.assertEquals

class ParserTest {
    @Test fun parserTest(){
        val arr = Parser()
        val arrayToken = ArrayList<NewToken>()
        arrayToken.add(InstructionToken(Token.PLUS))
        arrayToken.add(InstructionToken(Token.MINUS))
        arrayToken.add(InstructionToken(Token.PLUS))
        arrayToken.add(InstructionToken(Token.LEFT))
        arrayToken.add(InstructionToken(Token.PLUS))
        //arrayToken.add(InstructionToken(Token.BEGINFUN))
        arrayToken.add(FunDefToken("name", 3))
        arrayToken.add(InstructionToken(Token.ENDFUN))
        val array = arr.petoohTranslator("KokO Ko   kudahKo  Morning name KeKeKe Evening")
        array[0]
        for(i in 0 until arrayToken.size) {
            assertEquals(true, arrayToken[0].equals(array[0]))
        }
    }



    /*@Test fun funTest(){
        val arr = Interpreter()
        val arrayToken = ArrayList<NewToken>()
        val array = arr.petoohTranslator("Morning Hellow kudahKudahkudahKudahkudahKudahkudahKokukarekkokokukarekKoKoKudahkudahKokukarekKudahkudahKokukarekKudahkudahKokukarekkukarekKudahkudahKudahkudahKudahkudahKudahkudahKudah Evening PAR Hellow PAR Hellow ")
        arr.interpret(array)
    }*/

}