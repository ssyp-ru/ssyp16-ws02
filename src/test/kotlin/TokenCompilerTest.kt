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

    @Test fun testDiv() {
        //testClass(arrayOf(InstructionToken(Token.RIGHT), InstructionToken(Token.READ), InstructionToken(Token.RIGHT), InstructionToken(Token.READ), InstructionToken(Token.RIGHT), FunDefToken("div", 2), InstructionToken(Token.LEFT), InstructionToken(Token.LEFT), InstructionToken(Token.LEFT), InstructionToken(Token.PLUS), InstructionToken(Token.RIGHT), InstructionToken(Token.PLUS), InstructionToken(Token.BEGIN), InstructionToken(Token.LEFT), InstructionToken(Token.PLUS), InstructionToken(Token.RIGHT), InstructionToken(Token.RIGHT), InstructionToken(Token.BEGIN), InstructionToken(Token.LEFT), InstructionToken(Token.LEFT), InstructionToken(Token.MINUS), InstructionToken(Token.RIGHT), InstructionToken(Token.RIGHT), InstructionToken(Token.MINUS), InstructionToken(Token.RIGHT), InstructionToken(Token.PLUS), InstructionToken(Token.RIGHT), InstructionToken(Token.END), InstructionToken(Token.RIGHT), InstructionToken(Token.BEGIN), InstructionToken(Token.LEFT), InstructionToken(Token.LEFT), InstructionToken(Token.PLUS), InstructionToken(Token.RIGHT), InstructionToken(Token.RIGHT), InstructionToken(Token.BEGIN), InstructionToken(Token.LEFT), InstructionToken(Token.PLUS), InstructionToken(Token.RIGHT), InstructionToken(Token.MINUS), InstructionToken(Token.END), InstructionToken(Token.END), InstructionToken(Token.LEFT), InstructionToken(Token.LEFT), InstructionToken(Token.LEFT), InstructionToken(Token.LEFT), InstructionToken(Token.BEGIN), InstructionToken(Token.LEFT), InstructionToken(Token.END), InstructionToken(Token.RIGHT), InstructionToken(Token.RIGHT), InstructionToken(Token.MINUS), InstructionToken(Token.END), InstructionToken(Token.LEFT), InstructionToken(Token.MINUS), InstructionToken(Token.ENDFUN), FunCallToken("div"), InstructionToken(Token.WRITE)), 80.toChar().toString() + 10.toChar().toString(), 8.toChar().toString())
//        testClass(arrayOf(InstructionToken(Token.PLUS)), "", "")
        testClass(arrayOf(FunDefToken("div", 2), InstructionToken(Token.PLUS), InstructionToken(Token.RIGHT), InstructionToken(Token.READ), InstructionToken(Token.PLUS), InstructionToken(Token.RIGHT), InstructionToken(Token.READ), InstructionToken(Token.LEFT), InstructionToken(Token.BEGIN), InstructionToken(Token.LEFT), InstructionToken(Token.PLUS), InstructionToken(Token.RIGHT), InstructionToken(Token.RIGHT), InstructionToken(Token.BEGIN), InstructionToken(Token.LEFT), InstructionToken(Token.LEFT), InstructionToken(Token.MINUS), InstructionToken(Token.RIGHT), InstructionToken(Token.RIGHT), InstructionToken(Token.MINUS), InstructionToken(Token.RIGHT), InstructionToken(Token.PLUS), InstructionToken(Token.RIGHT), InstructionToken(Token.END), InstructionToken(Token.RIGHT), InstructionToken(Token.BEGIN), InstructionToken(Token.LEFT), InstructionToken(Token.LEFT), InstructionToken(Token.PLUS), InstructionToken(Token.RIGHT), InstructionToken(Token.RIGHT), InstructionToken(Token.BEGIN), InstructionToken(Token.LEFT), InstructionToken(Token.PLUS), InstructionToken(Token.RIGHT), InstructionToken(Token.MINUS), InstructionToken(Token.END), InstructionToken(Token.END), InstructionToken(Token.LEFT), InstructionToken(Token.LEFT), InstructionToken(Token.LEFT), InstructionToken(Token.LEFT), InstructionToken(Token.BEGIN), InstructionToken(Token.LEFT), InstructionToken(Token.END), InstructionToken(Token.RIGHT), InstructionToken(Token.RIGHT), InstructionToken(Token.MINUS), InstructionToken(Token.END), InstructionToken(Token.LEFT), InstructionToken(Token.MINUS), InstructionToken(Token.ENDFUN), FunCallToken("div"), InstructionToken(Token.WRITE)), 80.toChar().toString() + 10.toChar().toString(), 8.toChar().toString())
    }

    @Test fun testNaGovno() {
        testClass(arrayOf(InstructionToken(Token.READ), InstructionToken(Token.RIGHT), FunCallToken("plus25"), InstructionToken(Token.WRITE), FunDefToken("plus25", 1), FunCallToken("plus5"), InstructionToken(Token.RIGHT), FunCallToken("plus5"), InstructionToken(Token.RIGHT), FunCallToken("plus5"), InstructionToken(Token.RIGHT), FunCallToken("plus5"), InstructionToken(Token.RIGHT), FunCallToken("plus5"), InstructionToken(Token.ENDFUN), FunDefToken("plus5", 1), InstructionToken(Token.LEFT), InstructionToken(Token.PLUS), InstructionToken(Token.PLUS), InstructionToken(Token.PLUS), InstructionToken(Token.PLUS), InstructionToken(Token.PLUS), InstructionToken(Token.ENDFUN)), "a", "z")
    }

    @Test fun testPetoohFun(){
        val testFile = File("test.txt")
        val parser = Parser()
        testFile.writeText("""Morning sum KeKe
                kudah kudah
                Kud
                kO Kudah
                Ko kudah
                kud
                Kudah
                Evening
                Morning copy Ke
                kudah
                Evening
                Morning fibonacci Ke
                PAR copy kO Kudah
                PAR copy kO kudah
                Kud
                kO
                Kud
                PAR fibonacci Kudah
                PAR fibonacci Kudah
                PAR sum kO
                kud
                Ko
                kud
                Ko
                Evening
                kukarek Kudah
                PAR fibonacci Kukarek
        """)
        testClass(parser.petoohTranslator(testFile.readText()).toTypedArray(), 5.toChar().toString(), 8.toChar().toString())
    }

    @Test fun testFibonacci() {
        testClass(arrayOf(FunDefToken("sum", 2), InstructionToken(Token.LEFT), InstructionToken(Token.LEFT), InstructionToken(Token.BEGIN), InstructionToken(Token.MINUS), InstructionToken(Token.RIGHT), InstructionToken(Token.PLUS), InstructionToken(Token.LEFT), InstructionToken(Token.END), InstructionToken(Token.RIGHT), InstructionToken(Token.ENDFUN), FunDefToken("copy", 1), InstructionToken(Token.LEFT), InstructionToken(Token.ENDFUN), FunDefToken("fibonacci", 1), FunCallToken("copy"), InstructionToken(Token.MINUS), InstructionToken(Token.RIGHT), FunCallToken("copy"), InstructionToken(Token.MINUS), InstructionToken(Token.LEFT), InstructionToken(Token.BEGIN), InstructionToken(Token.MINUS), InstructionToken(Token.BEGIN), FunCallToken("fibonacci"), InstructionToken(Token.RIGHT), FunCallToken("fibonacci"), InstructionToken(Token.RIGHT), FunCallToken("sum"),  InstructionToken(Token.MINUS), InstructionToken(Token.END), InstructionToken(Token.PLUS), InstructionToken(Token.END), InstructionToken(Token.PLUS), InstructionToken(Token.ENDFUN), InstructionToken(Token.READ), InstructionToken(Token.RIGHT), FunCallToken("fibonacci"), InstructionToken(Token.WRITE)), 5.toChar().toString(), 8.toChar().toString())
    }
}