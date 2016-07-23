import org.objectweb.asm.util.ASMifier

fun main(args: Array<String>){
    val compiler = TokenCompiler()
    val arr = arrayOf(Token.READ, Token.WRITE)//, Token.PLUS, Token.RIGHT, Token.RIGHT, Token.PLUS, Token.WRITE, Token.MINUS, Token.LEFT, Token.MINUS)
    compiler.compile(arr)
    //var asm = ASMifier()
    //ASMifier.main(Array<String>(1, {"MyClass"}))
    //println(98.toChar())
    val arrToken = arrayOf(Token.READ, Token.RIGHT, Token.READ, Token.LEFT, Token.BEGIN, Token.RIGHT, Token.BEGIN, Token.RIGHT, Token.PLUS, Token.RIGHT, Token.PLUS, Token.LEFT, Token.LEFT, Token.MINUS, Token.END, Token.RIGHT, Token.BEGIN, Token.LEFT, Token.PLUS, Token.RIGHT, Token.MINUS, Token.END, Token.LEFT, Token.LEFT, Token.MINUS, Token.END, Token.RIGHT, Token.RIGHT, Token.RIGHT, Token.WRITE)
//    val write = MyWriter()
//    val read = MyReader()
//    val interp = Interpreter(arrToken, read, write)
//    interp.interpret()
//    println(write.myReturn)

    val againMenu = CLI()
    againMenu.MainMenu()



}