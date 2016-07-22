import org.objectweb.asm.util.ASMifier

fun main(args: Array<String>){
    val compiler = TokenCompiler()
    val arr = arrayOf(Token.READ, Token.WRITE)//, Token.PLUS, Token.RIGHT, Token.RIGHT, Token.PLUS, Token.WRITE, Token.MINUS, Token.LEFT, Token.MINUS)
    compiler.compile(arr)
    //var asm = ASMifier()
    //ASMifier.main(Array<String>(1, {"MyClass"}))
    //println(98.toChar())
}