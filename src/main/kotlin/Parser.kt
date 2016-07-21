import java.io.InputStream
import java.io.PrintStream
import java.util.*

class Parser(val read: InputStream = System.`in`, val write: PrintStream = System.out) {
    private var tokenIndex = 0
    private var keCount = 0

    /**
     * Interprets tokens to Kotlin.
     */
    fun petoohTranslator(tokenString: String): ArrayList<NewToken> {
        var valid = 0
        var flagFunNameChecker = true
        var tokenArray = ArrayList<NewToken>()
        while (tokenIndex != tokenString.length) {
            if ((valid == 0) || (valid == 1)) {
                when {
                    tokenString.startsWith("kO", tokenIndex) -> tokenArray.add(InstructionToken(Token.PLUS))
                    tokenString.startsWith("Ko", tokenIndex) -> tokenArray.add(InstructionToken(Token.MINUS))
                    tokenString.startsWith("kudah", tokenIndex) -> tokenArray.add(InstructionToken(Token.LEFT))
                    tokenString.startsWith("Kudah", tokenIndex) -> tokenArray.add(InstructionToken(Token.RIGHT))
                    tokenString.startsWith("kud", tokenIndex) -> tokenArray.add(InstructionToken(Token.END))
                    tokenString.startsWith("Kud", tokenIndex) -> tokenArray.add(InstructionToken(Token.BEGIN))
                    tokenString.startsWith("kukarek", tokenIndex) -> tokenArray.add(InstructionToken(Token.WRITE))
                    tokenString.startsWith("Kukarek", tokenIndex) -> tokenArray.add(InstructionToken(Token.READ))
                    tokenString.startsWith("Morning", tokenIndex) -> {
                        while (tokenString[tokenIndex] != ' ') {
                            tokenIndex++
                        }
                        if (tokenString[tokenIndex] == ' ') {
                            var cursorSpace = tokenIndex + 1
                            while (tokenString[cursorSpace] != ' ') {
                                cursorSpace++
                            }
                            val funName = tokenString.substring(tokenIndex + 1, cursorSpace)
                            tokenIndex = cursorSpace + 1

                            flagFunNameChecker = checkFunName(funName)
                            if (!flagFunNameChecker) {
                                println("Name of function cannot contain key words")
                            }
                            if (flagFunNameChecker) {
                                if (tokenString.startsWith("Ke", tokenIndex)) {
                                    while (!(tokenString.startsWith("Ke", tokenIndex)))
                                        keCount++
                                }
                                tokenArray.add(FunDefToken(funName, keCount))
                                tokenIndex = tokenString.indexOf(" ")
                            }
                        }
                        valid++
                    }
                    tokenString.startsWith("Evening", tokenIndex) -> {
                        tokenArray.add(InstructionToken(Token.ENDFUN))
                        valid--
                    }
                    tokenString.startsWith("PAR", tokenIndex) -> {
                        var cursorSpace = tokenIndex + 4
                        while (tokenString[cursorSpace] != ' ') {
                            cursorSpace++
                        }
                        val funName = tokenString.substring(tokenIndex + 4, cursorSpace)
                        tokenIndex = cursorSpace
                        flagFunNameChecker = checkFunName(funName)
                        if (!flagFunNameChecker)
                            println("Name of function cannot be a key word")
                        if (flagFunNameChecker) {
                            tokenArray.add(FunCallToken(funName))
                        }
                    }
                }
                tokenIndex++
            } else {
                flagFunNameChecker = false
                println("impossible initialize function inside of other function")
                break
            }
        }
        if (!flagFunNameChecker)
            tokenArray.clear()
        return (tokenArray)
    }

    /**
     * checks if function name consist of key words
     */
    private fun checkFunName(funName: String): Boolean {
        var flagFunNameChecker = false
        var nameIterator = 0
        while(nameIterator != funName.length) {
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