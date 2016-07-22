fun main(args: Array<String>){
    val compiler = TokenCompiler()
    val arr = arrayOf(Token.READ, Token.WRITE)//, Token.PLUS, Token.RIGHT, Token.RIGHT, Token.PLUS, Token.WRITE, Token.MINUS, Token.LEFT, Token.MINUS)
    compiler.compile(arr)
    //println(98.toChar())
}