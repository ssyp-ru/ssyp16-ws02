import java.io.InputStream
import java.io.PrintStream

class Interpreter(val read: InputStream = System.`in`, val write: PrintStream = System.out) {
    private val memorySize = 30000
    private var memory = ByteArray (memorySize, { 0 })
    private var curMemoryIndex = 0
    private var tokenIndex = 0
    private var beginEndCounter = tokenIndex


    /**
     * Interprets tokens to Kotlin.
     */
    fun interpret(arrayToken: Array<Token>) {
        while (tokenIndex != arrayToken.size) {
            when (arrayToken[tokenIndex]) {
                Token.LEFT -> left()
                Token.RIGHT -> right()
                Token.MINUS -> memory[curMemoryIndex]--
                Token.PLUS -> memory[curMemoryIndex]++
                Token.READ -> memory[curMemoryIndex] = read.read().toByte()
                Token.WRITE -> write.println(memory[curMemoryIndex].toChar())
                Token.BEGIN -> begin(arrayToken)
                Token.END -> end(arrayToken)
            }
            tokenIndex++
        }
    }

    /**
     * Moves cursor to the left.
     */
    fun left() {
        if (curMemoryIndex != 0) {
            curMemoryIndex--
        } else
            curMemoryIndex = memorySize - 1
    }

    /**
     * Moves cursor to the right
     */
    fun right() {
        if (curMemoryIndex != memorySize - 1) {
            curMemoryIndex++
        } else
            curMemoryIndex = 0
    }

    /**
     * Starts loop if array[curMemoryIndex] != 0
     */
    private fun begin(arrayToken: Array<Token>) {
        if (memory[curMemoryIndex].toInt() == 0) {
            while (!((arrayToken[tokenIndex] == Token.END) && (beginEndCounter == 0))) {
                when {
                    arrayToken[tokenIndex] == Token.END -> beginEndCounter--
                    arrayToken[tokenIndex] == Token.BEGIN -> beginEndCounter++
                }
                if ((arrayToken[tokenIndex] == Token.END) && (beginEndCounter == 0)) break
                tokenIndex++

            }
        }
    }

    /**
     * Ends loop if array[curMemoryIndex] != 0
     */
    private fun end(arrayToken: Array<Token>) {
        if (memory[curMemoryIndex].toInt() != 0) {
            while (!((arrayToken[tokenIndex] == Token.BEGIN) && (beginEndCounter == 0))) {
                when {
                    arrayToken[tokenIndex] == Token.END -> beginEndCounter++
                    arrayToken[tokenIndex] == Token.BEGIN -> beginEndCounter--
                }
                if ((arrayToken[tokenIndex] == Token.BEGIN) && (beginEndCounter == 0)) break
                tokenIndex--

            }
        }
    }
}