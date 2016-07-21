import java.io.InputStream
import java.io.PrintStream
import java.util.*

class Interpreter(val read: InputStream = System.`in`, val write: PrintStream = System.out) {
    private val memorySize = 30000
    private var workArray = ByteArray (memorySize, { 0 })
    private var memory = ByteArray (memorySize, { 0 })
    private var flag = true
    private var parIndex = 0
    private var workArrIndex = 0
    private var tokenIndex = 0
    private var beginEndcount = 0
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
                                tokenArray.add(InstructionToken(Token.BEGINFUN))
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
                            tokenArray.add(InstructionToken(Token.PAR))
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


    fun interpret(tokenArray: ArrayList<NewToken>) {
        var funNameIndex = 0
        tokenIndex = 0
        while (tokenIndex != tokenArray.size) {
            if (!flag) {
                memory = workArray
                workArray = ByteArray (memorySize, { 0 })
                for (i in 1..keCount) {
                    workArray[i - 1] = memory[workArrIndex - keCount]
                }
                parIndex = workArrIndex
                workArrIndex = 0
            } else {
                workArray = memory
                workArrIndex = parIndex
            }
            when (tokenArray[tokenIndex]) {
                InstructionToken(Token.BEGIN) -> begin(tokenArray.toTypedArray())
                InstructionToken(Token.END) -> end(tokenArray.toTypedArray())
                InstructionToken(Token.MINUS) -> workArrIndex--
                InstructionToken(Token.PLUS) -> workArrIndex++
                InstructionToken(Token.RIGHT) -> right()
                InstructionToken(Token.LEFT) -> left()
                InstructionToken(Token.WRITE) -> write.print(workArray[workArrIndex])
                InstructionToken(Token.READ) -> workArray[workArrIndex] = read.read().toByte()
                InstructionToken(Token.BEGINFUN) -> {
                    while (tokenArray[tokenIndex] != InstructionToken(Token.ENDFUN))
                        tokenIndex++
                }
                InstructionToken(Token.ENDFUN) -> {
                    flag = true
                    memory[parIndex] = workArray[workArrIndex]
                    while (tokenIndex != funNameIndex)
                        tokenIndex++

                }
                InstructionToken(Token.PAR) -> {
                    flag = false
                    val funName = tokenArray[tokenIndex + 1]
                    funNameIndex = tokenIndex
                    while (tokenArray[funNameIndex] != funName) {
                        if(tokenArray[funNameIndex] is FunDefToken) {
                            if (tokenArray[funNameIndex] != funName) {
                                funNameIndex--
                            }
                            else
                                tokenIndex = funNameIndex
                        }
                        else
                            funNameIndex --


                        }
                        funNameIndex--
                    }
                }
            }
            tokenIndex++
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


    /**
     * Moves cursor to the left.
     */
    fun left() {
        if (workArrIndex != 0) {
            workArrIndex--
        } else
            workArrIndex = memorySize - 1
    }

    /**
     * Moves cursor to the right
     */

    fun right() {
        if (workArrIndex != memorySize - 1) {
            workArrIndex++
        } else
            workArrIndex = 0
    }

    /**
     * Starts loop if arrayToken[workArrIndex] != 0
     */
    private fun begin(arrayToken: Array<NewToken>) {
        if (workArray[workArrIndex].toInt() == 0) {
            while (!((arrayToken[tokenIndex] == InstructionToken(Token.END)) && (beginEndcount == 0))) {
                when {
                    arrayToken[tokenIndex] == InstructionToken(Token.END) -> beginEndcount--
                    arrayToken[tokenIndex] == InstructionToken(Token.BEGIN) -> beginEndcount++
                }
                if ((arrayToken[tokenIndex] == InstructionToken(Token.END) && (beginEndcount == 0)))
                    break
                tokenIndex++
            }
        }
    }

    /**
     * Ends loop if arrayToken[workArrIndex] = 0
     */
    private fun end(arrayToken: Array<NewToken>) {
        if (workArray[workArrIndex].toInt() != 0) {
            while (!(arrayToken[tokenIndex] == InstructionToken(Token.BEGIN) && (beginEndcount == 0))) {
                when {
                    arrayToken[tokenIndex] == InstructionToken(Token.END) -> beginEndcount++
                    arrayToken[tokenIndex] == InstructionToken(Token.BEGIN) -> beginEndcount--
                }
                if ((arrayToken[tokenIndex] == InstructionToken(Token.BEGIN) && (beginEndcount == 0)))
                    break
                tokenIndex--

            }
        }
    }
}



