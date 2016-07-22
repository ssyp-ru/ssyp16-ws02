import java.io.File
import java.io.Reader
import java.util.*

class MyReader() : Reader() {
    override fun close() {
    }

    override fun read(cbuf: CharArray?, off: Int, len: Int): Int {
        return 0
    }

    private var arr = Array<Int>(13, { 0 })
    private val random = Random()
    private var i = 0

    /**
    writes in a random elements
     */

    private fun reWrite() {
        if (arr[0] == 0) {
            for (k in 0 until arr.size) {
                //arr[k] = (random.nextInt() % 5) + 5
            }
            arr[0] = 2
            arr[1] = 3
        }
        if (i == arr.size) {
            i = 0
        }
    }

    /**
     * fun for Tests
     */
    fun myReturn(k: Int): Int {
        return arr[k]
    }

    override fun read(): Int {
        i++
        reWrite()
        return arr[i]
    }
}