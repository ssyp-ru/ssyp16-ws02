import java.io.InputStream
import java.io.PrintStream
import java.util.*

class Parser(val read: InputStream = System.`in`, val write: PrintStream = System.out) {

    /**
     * Interprets tokens to Kotlin.
     */
    fun petoohTranslator(tokenString: String): List<NewToken> {
        var tokenStringIndex = 0
        var keCount = 0
        val tokenArray = ArrayList<NewToken>()
        while (tokenStringIndex != tokenString.length) {
                when {
                    tokenString.startsWith("Ko", tokenStringIndex) -> {
                        tokenArray.add(InstructionToken(Token.PLUS))
                        tokenStringIndex += 1
                    }
                    tokenString.startsWith("kO", tokenStringIndex) -> {
                        tokenStringIndex += 1
                        tokenArray.add(InstructionToken(Token.MINUS))
                    }
                    tokenString.startsWith("kudah", tokenStringIndex) -> {
                        tokenStringIndex += 4
                        tokenArray.add(InstructionToken(Token.LEFT))
                    }
                    tokenString.startsWith("Kudah", tokenStringIndex) -> {
                        tokenStringIndex += 4
                        tokenArray.add(InstructionToken(Token.RIGHT))
                    }
                    tokenString.startsWith("kud", tokenStringIndex) -> {
                        tokenStringIndex += 2
                        tokenArray.add(InstructionToken(Token.END))
                    }
                    tokenString.startsWith("Kud", tokenStringIndex) -> {
                        tokenStringIndex += 2
                        tokenArray.add(InstructionToken(Token.BEGIN))
                    }
                    tokenString.startsWith("Kukarek", tokenStringIndex) -> {
                        tokenStringIndex += 6
                        tokenArray.add(InstructionToken(Token.WRITE))
                    }
                    tokenString.startsWith("kukarek", tokenStringIndex) -> {
                        tokenArray.add(InstructionToken(Token.READ))
                        tokenStringIndex += 6
                    }
                    tokenString.startsWith("Morning", tokenStringIndex) -> {
                        while (tokenString[tokenStringIndex] != ' ') {
                            tokenStringIndex++
                        }
                        if (tokenString[tokenStringIndex] == ' ') {
                            var cursorSpace = tokenStringIndex + 1
                            while (tokenString[cursorSpace] != ' ') {
                                cursorSpace++
                            }
                            val funName = tokenString.substring(tokenStringIndex + 1, cursorSpace)
                            tokenStringIndex = cursorSpace + 1

                            if (!checkFunName(funName)) {
                                println("Name of function cannot contain key words")
                                return emptyList<NewToken>()
                            }
                            if (tokenString.startsWith("Ke", tokenStringIndex)) {
                                while ((tokenString.startsWith("Ke", tokenStringIndex))) {
                                    keCount++
                                    tokenStringIndex += 2
                                }
                            }
                            tokenArray.add(FunDefToken(funName, keCount))
                            keCount = 0
                            tokenStringIndex--
                        }
                    }
                    tokenString.startsWith("Evening", tokenStringIndex) -> {
                        tokenArray.add(InstructionToken(Token.ENDFUN))
                        tokenStringIndex += 6
                    }
                    tokenString.startsWith("PAR", tokenStringIndex) -> {
                        var cursorSpace = tokenStringIndex + 4
                        while (tokenString[cursorSpace] != ' ') {
                            cursorSpace++
                        }
                        val funName = tokenString.substring(tokenStringIndex + 4, cursorSpace)
                        tokenStringIndex = cursorSpace
                        if (!checkFunName(funName))
                            println("Name of function cannot be a key word")
                        if (checkFunName(funName)) {
                            tokenArray.add(FunCallToken(funName))
                        }
                        tokenStringIndex += 2
                    }
                }
                tokenStringIndex++
            }
        return (tokenArray)
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