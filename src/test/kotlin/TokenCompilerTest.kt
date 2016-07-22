import org.junit.Test

class TokenCompilerTest {

    @Test fun testReadWrite(){
        testClass(arrayOf(Token.READ, Token.WRITE), "5", "5")
    }

    @Test fun testPlus(){
        testClass(arrayOf(Token.READ, Token.PLUS, Token.WRITE), "5", "6")
        testClass(arrayOf(Token.READ, Token.PLUS, Token.PLUS, Token.WRITE), "5", "7")
    }
}