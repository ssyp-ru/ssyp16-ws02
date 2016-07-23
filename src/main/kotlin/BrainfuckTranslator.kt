import java.io.File
import java.io.FileNotFoundException
import java.util.ArrayList

/**
Class BrainfuckTranslator translate Brainfuck to Tokens and vice versa.
 */

class BrainfuckTranslator {
    /**
    translatorToken is function which take file with brainfuck code and return Token array
    if file not found
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
        val retArray: Array<Token> = list.toTypedArray()
        return retArray
    }

    /**
    translatorBrainfuck is function which take Token array and safe in file
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
