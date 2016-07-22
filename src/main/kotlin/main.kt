/**
 * Created by SSYP on 7/21/2016.
 */
fun main(s:Array<String>){
    val arrToken = arrayOf(Token.READ, Token.RIGHT, Token.READ, Token.LEFT, Token.BEGIN, Token.RIGHT, Token.BEGIN, Token.RIGHT, Token.PLUS, Token.RIGHT, Token.PLUS, Token.LEFT, Token.LEFT, Token.MINUS, Token.END, Token.RIGHT, Token.BEGIN, Token.LEFT, Token.PLUS, Token.RIGHT, Token.MINUS, Token.END, Token.LEFT, Token.LEFT, Token.MINUS, Token.END, Token.RIGHT, Token.RIGHT, Token.RIGHT, Token.WRITE)
    val write = MyWriter()
    val read = MyReader()
    val interp = Interpreter(arrToken, read, write)
    interp.interpret()
    println(write.myReturn)
}