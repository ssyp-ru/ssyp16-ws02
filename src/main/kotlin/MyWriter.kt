import java.io.Writer

class MyWriter(): Writer() {
    override fun write(cbuf: CharArray?, off: Int, len: Int) {

    }

    override fun write(str: String) {
        r = str
    }

    override fun flush() {
    }

    override fun close() {
    }

   var r : String? = null
}