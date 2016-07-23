import java.io.InputStream
import java.io.PrintStream
import java.io.Reader
import java.io.Writer

class Interpreter(var arrayToken: Array<Token>, val read: InputStream, val write: PrintStream) {
    var array = ByteArray (30000, {0})
    var current = 0
    var i = 0
    private var count = i

    /**
     * This fun interprets tokens to Kotlin.
     */

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

                Token.MINUS -> array[current]--


                Token.PLUS -> array[current]++


                Token.READ -> array[current] = read.read().toByte()


                Token.WRITE -> {
                    write.print(array[current].toChar())
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
                        while (!((arrayToken[i] == Token.BEGIN) && (count == 0))) {
                            if (arrayToken[i] == Token.END) {
                                count++
                            }
                            if ((arrayToken[i] == Token.BEGIN) && (count > 0)) {
                                count--
                            }
                            if ((arrayToken[i] == Token.BEGIN) && (count == 0)) {
                                break
                            }
                            i--
                        }
                    }
                }
            }
            i++
        }
    }
}