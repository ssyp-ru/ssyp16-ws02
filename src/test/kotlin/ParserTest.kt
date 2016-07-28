import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

class ParserTest {
    @Test fun parserTest() {
        val petoohStr = Parser()
        val arrayToken = ArrayList<NewToken>()
        arrayToken.add(InstructionToken(Token.PLUS))
        arrayToken.add(InstructionToken(Token.MINUS))
        arrayToken.add(InstructionToken(Token.PLUS))
        arrayToken.add(InstructionToken(Token.LEFT))
        arrayToken.add(InstructionToken(Token.PLUS))
        arrayToken.add(FunDefToken("name", 3))
        arrayToken.add(InstructionToken(Token.ENDFUN))
        val array = petoohStr.petoohTranslator("KokO Ko   kudahKo  Morning name KeKeKe Evening")
        for (i in 0 until arrayToken.size) {
            assert(arrayToken[i].equals(array[i]))
        }
    }

    @Test fun funnyTest() {
        val petoohStr = Parser()
        val arrayToken = ArrayList<NewToken>()
        arrayToken.add(FunDefToken("name", 3))
        for (i in 0..2)
            arrayToken.add(InstructionToken(Token.MINUS))
        arrayToken.add(InstructionToken(Token.ENDFUN))
        for (i in 0..2)
            arrayToken.add(InstructionToken(Token.PLUS))
        arrayToken.add(FunDefToken("kek", 1))
        arrayToken.add(InstructionToken(Token.ENDFUN))
        arrayToken.add(FunCallToken("name"))
        arrayToken.add(InstructionToken(Token.MINUS))
        arrayToken.add(InstructionToken(Token.END))
        arrayToken.add(InstructionToken(Token.BEGIN))
        val array = petoohStr.petoohTranslator("Morning name KeKeKe kOkOkO Evening KoKoKo Morning kek Ke Evening PAR name kO kud Kud")
        for (i in 0 until array.size) {
            assert(arrayToken[i].equals(array[i]))
        }
    }

    @Test fun secondParserTest() {
        val arr = Parser()
        val arrayToken = ArrayList<NewToken>()
        arrayToken.add(FunDefToken("name", 3))
        arrayToken.add(InstructionToken(Token.RIGHT))
        arrayToken.add(InstructionToken(Token.WRITE))
        arrayToken.add(InstructionToken(Token.ENDFUN))
        arrayToken.add(InstructionToken(Token.PLUS))
        arrayToken.add(FunDefToken("kek", 1))
        arrayToken.add(InstructionToken(Token.READ))
        arrayToken.add(InstructionToken(Token.ENDFUN))
        arrayToken.add(FunCallToken("name"))
        arrayToken.add(InstructionToken(Token.MINUS))
        val array = arr.petoohTranslator("Morning name KeKeKe KudahKukarek Evening Ko Morning kek Ke kukarek Evening PAR name\nkO")
        for (i in 0 until array.size) {
            assert(arrayToken[i].equals(array[i]))
        }
    }

    @Test fun parserValidatorTest(){
        val petoohStr = Parser()
        val petoohToken = petoohStr.petoohTranslator("Morning name Morning kek Evening Evening")
        assertEquals(emptyList<NewToken>(), petoohToken)
    }

    @Test fun parserKeyWordNameTest(){
        val petoohStr = Parser()
        val petoohToken = petoohStr.petoohTranslator("Morning KokO Evening")
        assertEquals(emptyList<NewToken>(), petoohToken)
    }
}
