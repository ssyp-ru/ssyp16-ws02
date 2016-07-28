import java.io.File
import java.io.FileNotFoundException
import java.util.ArrayList

/**
 * Translate Brainfuck to Tokens and vice versa.
 * @author Tatyana Nikolaeva
 */
class BrainfuckTranslator {
    /**
     * Takes file with brainfuck code and return Token array.
     */
    fun translateToTokens(fileName: String): Array<Token> {
        val code: String
        try {
            code = File(fileName).readText()
        } catch(exc: FileNotFoundException) {
            println("File not found!")
            return emptyArray()
        }
        val list = ArrayList<Token>()
        for (c in code) {
            val curToken: Token? = when (c) {
                '+' -> Token.PLUS
                '-' -> Token.MINUS
                '>' -> Token.RIGHT
                '<' -> Token.LEFT
                '.' -> Token.WRITE
                ',' -> Token.READ
                '[' -> Token.BEGIN
                ']' -> Token.END
                else -> null
            }
            if (curToken != null)
                list.add(curToken)
        }
        val retArray = list.toTypedArray()
        return retArray
    }

    /**
     * Takes Token array and save in file.
     */
    fun translateToBrainfuck(tokens: Array<Token>, outFileName: String) {
        val file = File(outFileName)
        val stringBuilder = StringBuilder()
        for (c in tokens) {
            stringBuilder.append(when (c) {
                Token.PLUS -> "+"
                Token.MINUS -> "-"
                Token.RIGHT -> ">"
                Token.LEFT -> "<"
                Token.WRITE -> "."
                Token.READ -> ","
                Token.BEGIN -> "["
                Token.END -> "]"
            })
        }
        file.writeText(stringBuilder.toString())
    }
}
