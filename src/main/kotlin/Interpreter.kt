import java.io.InputStream
import java.io.PrintStream

class Interpreter(val read: InputStream = System.`in`, val write: PrintStream = System.out) {
    private val const = 30000
    private var array = ByteArray (const, { 0 })
    private var current = 0
    private var i = 0
    private var count = i


    /**
     * Interprets tokens to Kotlin.
     */

    fun interpret(val String) {
        while (i != arrayToken.size) {
            when{



            }
            i++
        }
    }

    /**
     * Moves cursor left.
     */

    fun left() {
        if (current != 0) {
            current--
        } else
            current = const - 1
    }

    /**
     * Moves cursor right
     */

    fun right() {
        if (current != const - 1) {
            current++
        } else
            current = 0
    }

    /**
     * Starts loop if array[current] = 0
     */

    private fun begin(arrayToken: Array<Token>) {
        if (array[current].toInt() == 0) {
            while (!((arrayToken[i] == Token.END) && (count == 0))) {
                when{
                    arrayToken[i] == Token.END -> count--
                    arrayToken[i] == Token.BEGIN -> count++
                }
                if ((arrayToken[i] == Token.END) && (count == 0)) break
                i++

            }
        }
    }

    /**
     * Ends loop if array[current] = 0
     */

    private fun end(arrayToken: Array<Token>) {
        if (array[current].toInt() != 0) {
            while (!((arrayToken[i] == Token.BEGIN) && (count == 0))) {
                when{
                    arrayToken[i] == Token.END -> count++
                    arrayToken[i] == Token.BEGIN -> count--
                }
                if ((arrayToken[i] == Token.BEGIN) && (count == 0)) break
                i--

            }
        }
    }
}