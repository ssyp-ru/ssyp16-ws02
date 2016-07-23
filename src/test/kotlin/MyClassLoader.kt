import java.io.InputStream
import java.io.OutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

class MyClassLoader: ClassLoader() {
    fun loadClass(name: String?, barr: ByteArray): Class<*>?{
        return super.defineClass(name, barr, 0, barr.size)
    }
}

class MyInputStream(val str: String): InputStream() {
    var count = 0

    override fun read(): Int{
        count++
        return str[count - 1].toInt()
    }
}

class MyPrintStream(a:StringBuilder): PrintStream(EmptyStream()) {
    val sb = a
    override fun print(x: Char) {
        sb.append(x)
    }
}

class EmptyStream(): OutputStream() {
    override fun write(b: Int) { }
}

fun testClass(code: Array<Token>, input: String, output: String){
    val tokenCompiler = TokenCompiler()
    val classLoader = MyClassLoader()
    val myClass = classLoader.loadClass("MyClass", tokenCompiler.compile(code))
    val methods = myClass?.methods
    val sb = StringBuilder()
    System.setIn(MyInputStream(input))
    System.setOut(MyPrintStream(sb))
    if((methods != null)&&(methods?.size != 0)){
        for(i in methods) {
            if(i.name == "main"){
                i.invoke(null, arrayOf<String>())
                assertEquals(output, sb.toString())

                break
            }
        }
    }
}