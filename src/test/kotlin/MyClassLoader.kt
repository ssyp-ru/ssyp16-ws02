import java.io.InputStream
import java.io.OutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

class MyClassLoader : ClassLoader() {
    fun loadClass(name: String?, barr: ByteArray): Class<*>? {
        return super.defineClass(name, barr, 0, barr.size)
    }
}


class MyInputStream(val str: String) : InputStream() {
    private var count = 0


    override fun read(): Int {
        count++
        return str[count - 1].toInt()
    }
}


class MyPrintStream() : PrintStream(EmptyStream()) {
    val sb = StringBuilder()
    override fun println(x: Char) {
        sb.append(x)
    }
}
/**
 * Makes EmptyStream which helps MyPrintStream
 * with interface PrintStream and goes like
 * parameter(unusable)
 */
class EmptyStream() : OutputStream() {
    override fun write(b: Int) {
    }
}
/**
 * Compiles, loads and runs byte-code, tests it
 */
fun testClass(code: Array<Token>, input: String, output: String) {
    val tokenCompiler = TokenCompiler()
    val classLoader = MyClassLoader()
    val myClass = classLoader.loadClass("MyClass", tokenCompiler.compile(code,"MyClass"))
    val methods = myClass?.methods
    System.setIn(MyInputStream(input))
    val str = MyPrintStream()
    System.setOut(str)
    if ((methods != null) && (methods.size != 0)) {
        for (i in methods) {
            if (i.name == "main") {
                i.invoke(null, arrayOf<String>())
                assertEquals(output, str.sb.toString())
                break
            }
        }
    }
}