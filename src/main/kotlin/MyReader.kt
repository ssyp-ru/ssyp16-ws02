import java.io.File
import java.io.Reader
import java.util.*

class MyReader(): Reader() {
    override fun close() {
    }

    override fun read(cbuf: CharArray?, off: Int, len: Int): Int {
        return 0
    }

    var a = 0

    override fun read(): Int {
        val r = Random()
        a = (r.nextInt()%255 - 128)
        return a
    }
}