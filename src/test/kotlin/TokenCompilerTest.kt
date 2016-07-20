
import org.junit.Test
import java.io.File

class TokenCompilerTest {
/*
    /**
     * Tests simple read and write
     */
    @Test fun testReadWrite() {
        testClass(arrayOf(Token.READ, Token.WRITE), "5", "5")
    }

    /**
     * Tests reading twice
     */
    @Test fun testReadTwice() {
        testClass(arrayOf(Token.READ, Token.READ, Token.WRITE), "dk", "k")
    }

    /**
     * Tests plus
     */
    @Test fun testPlus() {
        testClass(arrayOf(Token.READ, Token.PLUS, Token.WRITE), "5", "6")
        testClass(arrayOf(Token.READ, Token.PLUS, Token.PLUS, Token.WRITE), "5", "7")
    }

    /**
     * Tests minus
     */
    @Test fun testMinus() {
        testClass(arrayOf(Token.READ, Token.MINUS, Token.WRITE), "5", "4")
        testClass(arrayOf(Token.READ, Token.MINUS, Token.MINUS, Token.WRITE), "5", "3")
    }

    /**
     * Tests moving cursor left and right
     */
    @Test fun testLeftRight() {
        testClass(arrayOf(Token.READ, Token.PLUS, Token.RIGHT, Token.READ, Token.PLUS, Token.WRITE, Token.LEFT, Token.WRITE), "f8", "9g")
    }

    /**
     * Tests writing twice
     */
    @Test fun testWriteTwice() {
        testClass(arrayOf(Token.READ, Token.WRITE, Token.WRITE), "g", "gg")
    }

    @Test fun testToZero() {
        testClass(arrayOf(Token.READ, Token.BEGIN, Token.MINUS, Token.END, Token.WRITE), "8", 0.toChar().toString())
    }

    @Test fun testFactorial() {
        val testFile = File("test.txt")
        testFile.writeText(",>>>>+<<<<[->+[->+>+<<]>[-<+>]>[->[->+>+<<]>[-<+>]<<]>[-]>>[-<<+>>]<<<<<<]>>>>.")
        testClass(BrainfuckTranslator().translateToTokens("test.txt"), 5.toChar().toString(), 120.toChar().toString())
    }

    @Test fun testHelloWorld() {
        val testFile = File("test.txt")
        testFile.writeText("++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>.")
        testClass(BrainfuckTranslator().translateToTokens("test.txt"), "", "Hello World!\n")
    }

    @Test fun testDiv() {
        val testFile = File("test.txt")
        testFile.writeText("+>,+>,<[<+>>[<<->>->+>]>[<<+>>[<+>-]]<<<<[<]>>-]<-.")
        testClass(BrainfuckTranslator().translateToTokens("test.txt"), 6.toChar().toString() + 2.toChar().toString(), 3.toChar().toString())
    }*/

    @Test fun s1mpleFunction() {
        val testFile = File("test.txt")
        testFile.writeText("")
        testClass(arrayOf(FunDefToken("myFunction", 62), InstructionToken(Token.PLUS), InstructionToken(Token.ENDFUN), FunCallToken("myFunction")), "", "")
//        testClass(arrayOf(InstructionToken(Token.PLUS)), "", "")
    }
}