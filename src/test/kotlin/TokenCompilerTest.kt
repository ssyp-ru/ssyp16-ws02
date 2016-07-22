import org.junit.Test

class TokenCompilerTest {

    @Test fun testReadWrite(){
        testClass(arrayOf(Token.READ, Token.WRITE), "5", "5")
    }

    @Test fun testPlus(){
        testClass(arrayOf(Token.READ, Token.PLUS, Token.WRITE), "5", "6")
        testClass(arrayOf(Token.READ, Token.PLUS, Token.PLUS, Token.WRITE), "5", "7")
    }

    @Test fun testMinus(){
        testClass(arrayOf(Token.READ, Token.MINUS, Token.WRITE), "5", "4")
        testClass(arrayOf(Token.READ, Token.MINUS, Token.MINUS, Token.WRITE), "5", "3")
    }

    @Test fun testLeftRight(){
        testClass(arrayOf(Token.READ, Token.PLUS, Token.RIGHT, Token.READ, Token.PLUS, Token.WRITE, Token.LEFT, Token.WRITE), "58", "96")
    }
}