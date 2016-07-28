import org.junit.Test

class InterpreterTest {
    @Test fun functionTest(){
        val par = NotSoBadParser()
        val array: Array<NewToken> = par.parse("Morning copy Ke kudah Evening Ko Kud Ko Kukarek kud Ko Ko Ko Kudah PAR copy Kukarek ")
        //val array = par.parse("Morning sum KeKe kudah kudahKudkO KudahKo kudahkudKudah Evening Morning copy Ke kudah Evening Morning sumAll Ke kudah kO KudKudah PAR sumAll Kudah PAR sum Kudah kud kudah Ko Evening KoKoKo Kudah PAR sumAll Kukarek")
        val inter = Interpreter()
        inter.interpret(array)

    }

    /*@Test fun funInFunTest(){
        val petoohStr = Parser()
        val tokenArr = petoohStr.petoohTranslator("")
    }*/
}