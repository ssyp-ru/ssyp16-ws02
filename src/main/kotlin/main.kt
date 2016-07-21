import jdk.internal.org.objectweb.asm.ClassWriter
import jdk.internal.org.objectweb.asm.Opcodes.*


fun main(args: Array<String>){
    val compiler = TokenCompiler()
    val arr = arrayOf(Token.PLUS, Token.RIGHT, Token.RIGHT, Token.PLUS, Token.MINUS, Token.LEFT, Token.MINUS)
    compiler.compile(arr)
}