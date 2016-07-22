import org.junit.Test

class TokenCompilerTest {

    /**
     * Tests simple read and write
     */
    @Test fun testReadWrite(){
        testClass(arrayOf(Token.READ, Token.WRITE), "5", "5")
    }

    /**
     * Tests reading twice
     */
    @Test fun testReadTwice(){
        testClass(arrayOf(Token.READ, Token.READ, Token.WRITE), "dk", "k")
    }

    /**
     * Tests plus
     */
    @Test fun testPlus(){
        testClass(arrayOf(Token.READ, Token.PLUS, Token.WRITE), "5", "6")
        testClass(arrayOf(Token.READ, Token.PLUS, Token.PLUS, Token.WRITE), "5", "7")
    }

    /**
     * Tests minus
     */
    @Test fun testMinus(){
        testClass(arrayOf(Token.READ, Token.MINUS, Token.WRITE), "5", "4")
        testClass(arrayOf(Token.READ, Token.MINUS, Token.MINUS, Token.WRITE), "5", "3")
    }

    /**
     * Tests moving cursor left and right
     */
    @Test fun testLeftRight(){
        testClass(arrayOf(Token.READ, Token.PLUS, Token.RIGHT, Token.READ, Token.PLUS, Token.WRITE, Token.LEFT, Token.WRITE), "f8", "9g")
    }

    /**
     * Tests writing twice
     */
    @Test fun testWriteTwice(){
        testClass(arrayOf(Token.READ, Token.WRITE, Token.WRITE), "g", "gg")
    }
}