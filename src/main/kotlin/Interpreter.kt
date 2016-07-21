import java.io.Reader
import java.io.Writer
import java.util.*

class Interpreter(var arrayToken: Array<Token>, val read: Reader, val write: Writer) {
    var array = Array<Byte> (30000, { 0 })
    var current = 0
    var i = 0
    val k = i
    fun interpret() {
        while (i != arrayToken.size) {
            when (arrayToken[i]) {
                Token.LEFT -> {
                    if (current != 0) {
                        current--
                    } else
                        current = 29999
                }

                Token.RIGHT -> {
                    if (current != 29999) {
                        current++
                    } else
                        current = 0
                }

                Token.MINUS -> {
                    array[current]--
                }

                Token.PLUS -> {
                    array[current]++
                }

                Token.READ -> {
                    array[current] = read.read().toByte()
                }

                Token.WRITE -> {
                    write.write(array[current].toString())
                }

                Token.BEGIN -> {
                    if (array[current].toInt() == 0) {
                        while (arrayToken[i] != Token.END) {
                            i++
                        }
                        i++
                    }
                }
                Token.END -> {
                    if (array[current].toInt() != 0) {
                        i = k
                    }
                }
            }
            i++
        }
    }
}