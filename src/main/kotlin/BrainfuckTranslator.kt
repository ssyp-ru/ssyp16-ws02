import java.io.File
import java.io.FileNotFoundException
import java.util.ArrayList

class BrainfuckTranslator {
    fun translatorToken(fileName: String): Array<Token> {
        val code: String
        try {
            code = File(fileName).readText()
        } catch(exc: FileNotFoundException) {
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

    fun translatorBrainfuck(tokens: Array<Token>, outFileName: String) {
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
