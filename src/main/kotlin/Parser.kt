import java.io.InputStream
import java.io.PrintStream
import java.util.*

class Parser(val read: InputStream = System.`in`, val write: PrintStream = System.out) {
    private val whitespaces = listOf<Char>(' ', '\t', '\n')

    /**
     * Translate petooh to tokens.
     */
    fun petoohTranslator(tokenString: String): List<NewToken> {
        var tokenStringIndex = 0
//        var keCount = 0
        val tokenArray = ArrayList<NewToken>()
        while (tokenStringIndex != tokenString.length) {
            when {
                tokenString.startsWith("Ko", tokenStringIndex) -> {
                    tokenArray.add(InstructionToken(Token.PLUS))
                    tokenStringIndex += 2
                }
                tokenString.startsWith("kO", tokenStringIndex) -> {
                    tokenStringIndex += 2
                    tokenArray.add(InstructionToken(Token.MINUS))
                }
                tokenString.startsWith("kudah", tokenStringIndex) -> {
                    tokenStringIndex += 5
                    tokenArray.add(InstructionToken(Token.LEFT))
                }
                tokenString.startsWith("Kudah", tokenStringIndex) -> {
                    tokenStringIndex += 5
                    tokenArray.add(InstructionToken(Token.RIGHT))
                }
                tokenString.startsWith("kud", tokenStringIndex) -> {
                    tokenStringIndex += 3
                    tokenArray.add(InstructionToken(Token.END))
                }
                tokenString.startsWith("Kud", tokenStringIndex) -> {
                    tokenStringIndex += 3
                    tokenArray.add(InstructionToken(Token.BEGIN))
                }
                tokenString.startsWith("Kukarek", tokenStringIndex) -> {
                    tokenStringIndex += 7
                    tokenArray.add(InstructionToken(Token.WRITE))
                }
                tokenString.startsWith("kukarek", tokenStringIndex) -> {
                    tokenArray.add(InstructionToken(Token.READ))
                    tokenStringIndex += 7
                }
                tokenString.startsWith("Morning", tokenStringIndex) -> {
                    tokenStringIndex += 7
                    while (tokenStringIndex < tokenString.length && tokenString[tokenStringIndex] in whitespaces) {
                        tokenStringIndex++
                    }
                    // we are now on the next symbol after ' '
                    if (tokenStringIndex >= tokenString.length) {
                        println("Syntax error")
                        return emptyList()
                    }
                    // extract function name
                    var nextWhitespaceIndex = tokenStringIndex
                    do {
                        nextWhitespaceIndex++
                    } while (tokenString[nextWhitespaceIndex] !in whitespaces)
                    val funName = tokenString.substring(tokenStringIndex, nextWhitespaceIndex)
                    // move tokenStringIndex the symbol after the next whitespace
                    tokenStringIndex = nextWhitespaceIndex + 1

                    if (!checkFunName(funName)) {
                        println("Name of function cannot contain key words")
                        return emptyList<NewToken>()
                    }

                    var keCount = 0
                    while (tokenString.startsWith("Ke", tokenStringIndex)) {
                        keCount++
                        tokenStringIndex += 2
                    }
                    tokenArray.add(FunDefToken(funName, keCount))
                }
                tokenString.startsWith("Evening", tokenStringIndex) -> {
                    tokenArray.add(InstructionToken(Token.ENDFUN))
                    tokenStringIndex += 7
                }
                tokenString.startsWith("PAR", tokenStringIndex) -> {
                    tokenStringIndex += 3
                    while (tokenStringIndex < tokenString.length && tokenString[tokenStringIndex] in whitespaces) {
                        tokenStringIndex++
                    }
                    // we are now on the next symbol after ' '
                    if (tokenStringIndex >= tokenString.length) {
                        println("Syntax error")
                        return emptyList()
                    }
                    // extract function name
                    var nextWhitespaceIndex = tokenStringIndex
                    do {
                        nextWhitespaceIndex++
                    } while (tokenString[nextWhitespaceIndex] !in whitespaces)
                    val funName = tokenString.substring(tokenStringIndex, nextWhitespaceIndex)
                    tokenStringIndex = nextWhitespaceIndex + 1
                    if (!checkFunName(funName)) {
                        println("Name of function cannot be a key word")
                        return emptyList()
                    }
                    tokenArray.add(FunCallToken(funName))
                }
                tokenString[tokenStringIndex] in whitespaces ->
                    ++tokenStringIndex
            }
        }
        val validator = ParserValidator()
        if (validator.validator(tokenArray))
            return (tokenArray)
        else
            return emptyList()
    }

    /**
     * checks if function name consist of key words
     */
    private fun checkFunName(funName: String): Boolean {
        var flagFunNameChecker = false
        var nameIterator = 0
        while (nameIterator != funName.length) {
            when {
                funName.startsWith("kO", nameIterator) -> nameIterator += 2
                funName.startsWith("Ko", nameIterator) -> nameIterator += 2
                funName.startsWith("kudah", nameIterator) -> nameIterator += 5
                funName.startsWith("Kudah", nameIterator) -> nameIterator += 5
                funName.startsWith("kud", nameIterator) -> nameIterator += 3
                funName.startsWith("Kud", nameIterator) -> nameIterator += 3
                funName.startsWith("kukarek", nameIterator) -> nameIterator += 7
                funName.startsWith("Kukarek", nameIterator) -> nameIterator += 7
                funName.startsWith("Morning", nameIterator) -> nameIterator += 7
                funName.startsWith("Evening", nameIterator) -> nameIterator += 7
                funName.startsWith("PAR", nameIterator) -> nameIterator += 3
                else -> {
                    flagFunNameChecker = true
                    nameIterator = funName.length
                }
            }

        }

        return flagFunNameChecker
    }
}