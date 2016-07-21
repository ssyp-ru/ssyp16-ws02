import java.io.InputStream
import java.io.PrintStream

class Interpreter(val read: InputStream = System.`in`, val write: PrintStream = System.out) {
    private val memorySize = 30000
    private var workSpace = ByteArray (memorySize, { 0 })
    private var memory = ByteArray (memorySize, { 0 })
    private var flag = true
    private var parIndex = 0
    private var workSpaceIndex = 0
    private var tokenIndex = 0
    private var beginEndcount = 0
    private var keCount = 0


    /**
     * Interprets tokens to kotlin.
     */
    fun interpret(tokenArray: List<NewToken>) {
        var funName = tokenArray[0]
        var funNameIndex = 0
        tokenIndex = 0
        while (tokenIndex != tokenArray.size) {
            if (!flag) {
                memory = workSpace
                workSpace = ByteArray (memorySize, { 0 })
                for (i in 1..keCount) {
                    workSpace[i - 1] = memory[workSpaceIndex - keCount]
                }
                parIndex = workSpaceIndex
                workSpaceIndex = 0
            }
            when (tokenArray[tokenIndex]) {
                InstructionToken(Token.BEGIN) -> begin(tokenArray.toTypedArray())
                InstructionToken(Token.END) -> end(tokenArray.toTypedArray())
                InstructionToken(Token.MINUS) -> workSpaceIndex--
                InstructionToken(Token.PLUS) -> workSpaceIndex++
                InstructionToken(Token.RIGHT) -> right()
                InstructionToken(Token.LEFT) -> left()
                InstructionToken(Token.WRITE) -> {
                    write.print(workSpace[workSpaceIndex])
                }
                InstructionToken(Token.READ) -> workSpace[workSpaceIndex] = read.read().toByte()
                is FunDefToken -> {
                    while (tokenArray[tokenIndex] != InstructionToken(Token.ENDFUN))
                        tokenIndex++
                    tokenIndex++
                }
                InstructionToken(Token.ENDFUN) -> {
                    flag = true
                    memory[parIndex] = workSpace[workSpaceIndex]
                    while (true) {
                        if (tokenArray[funNameIndex] is FunCallToken) {
                            if ((tokenArray[funNameIndex] as FunCallToken).name != (funName as FunCallToken).name) {
                                funNameIndex++
                                tokenIndex = funNameIndex
                                break
                            }
                        } else
                            funNameIndex++
                    }
                    workSpace = memory
                    workSpaceIndex = parIndex
                    tokenIndex++
                }
                is FunCallToken -> {
                    flag = false
                    funName = tokenArray[tokenIndex]
                    funNameIndex = tokenIndex
                    while (true) {
                        if (tokenArray[funNameIndex] is FunDefToken) {
                            if ((tokenArray[funNameIndex] as FunDefToken).name != (funName as FunCallToken).name) {
                                funNameIndex--
                                tokenIndex = funNameIndex
                                break
                            }
                        } else
                            funNameIndex--
                        if (funNameIndex == 0) {
                            println("function must be initialised before calling")
                            break
                        }
                    }
                    funNameIndex++
                }
            }
            tokenIndex++
        }
    }

    /**
     * Moves cursor to the left.
     */
    fun left() {
        if (workSpaceIndex != 0) {
            workSpaceIndex--
        } else
            workSpaceIndex = memorySize - 1
    }

    /**
     * Moves cursor to the right
     */
    fun right() {
        if (workSpaceIndex != memorySize - 1) {
            workSpaceIndex++
        } else
            workSpaceIndex = 0
    }

    /**
     * Starts loop if arrayToken[workSpaceIndex] != 0
     */
    private fun begin(arrayToken: Array<NewToken>) {
        if (workSpace[workSpaceIndex].toInt() == 0) {
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
     * Ends loop if arrayToken[workSpaceIndex] = 0
     */
    private fun end(arrayToken: Array<NewToken>) {
        if (workSpace[workSpaceIndex].toInt() != 0) {
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



