
import java.io.InputStream
import java.io.PrintStream
import java.util.*

class Interpreter(val read: InputStream = System.`in`, val write: PrintStream = System.out) {
    private val const = 30000
    private var array = ByteArray (const, { 0 })
    private var current = 0
    private var index = 0
    private var count = 0

    internal fun pushValues(params: Array<Byte>) {
        for (i in 0..params.size - 1) {
            array[i] = params[i]
        }
    }


    /**
     * Interprets tokens to Kotlin.
     */

    fun interpret(tokenString: String): ArrayList<NewToken> {
        var tokenStr = ArrayList<NewToken>()
        while (index != tokenString.length) {
            when {
                tokenString.startsWith("kO", index) -> tokenStr.add(InstructionToken(Token.PLUS))
                tokenString.startsWith("Ko", index) -> tokenStr.add(InstructionToken(Token.MINUS))
                tokenString.startsWith("kudah", index) -> tokenStr.add(InstructionToken(Token.LEFT))
                tokenString.startsWith("Kudah", index) -> tokenStr.add(InstructionToken(Token.RIGHT))
                tokenString.startsWith("kud", index) -> tokenStr.add(InstructionToken(Token.END))
                tokenString.startsWith("Kud", index) -> tokenStr.add(InstructionToken(Token.BEGIN))
                tokenString.startsWith("kukarek", index) -> tokenStr.add(InstructionToken(Token.WRITE))
                tokenString.startsWith("Kukarek", index) -> tokenStr.add(InstructionToken(Token.READ))
                tokenString.startsWith("Ke", index) -> tokenStr.add(InstructionToken(Token.KE))
                tokenString.startsWith("Morning", index) -> {
                    tokenStr.add(InstructionToken(Token.BEGINFUN))
                    tokenStr.add(StringToken(tokenString.substring(index + 1, tokenString.indexOf(" "))))
                    index = tokenString.indexOf(" ")
                }
                tokenString.startsWith("Evening", index) -> tokenStr.add(InstructionToken(Token.ENDFUN))
            }
            index++
        }


        while()
        return (tokenStr)
    }



    /* /**
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
                when {
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
                when {
                    arrayToken[i] == Token.END -> count++
                    arrayToken[i] == Token.BEGIN -> count--
                }
                if ((arrayToken[i] == Token.BEGIN) && (count == 0)) break
                i--

            }
        }
    }*/
}