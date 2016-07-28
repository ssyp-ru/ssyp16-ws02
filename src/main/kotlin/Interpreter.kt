import java.io.InputStream
import java.io.PrintStream
import java.util.*

class Interpreter(private val read: InputStream = System.`in`, private val write: PrintStream = System.out) {
    private val memorySize = 30000
    private var workSpace = ByteArray (memorySize, { 0 })
    private var tokenIndex = 0
    private var beginEndcounter = 0
    private var workSpaceIndex = 0

    /**
     * Interprets tokens to kotlin.
     */
    fun interpret(tokenArray: Array<NewToken>) {
        val functionWorkSpaces = ArrayList<ByteArray>()
        var funcFinder = true //ExFlag
        var parIndex = 0
        var keCount = 0
        var funName = tokenArray[0]
        var funNameIndex = 0
        tokenIndex = 0
        while (tokenIndex != tokenArray.size) {
            if (!funcFinder) {
                for (i in 1..keCount) {
                    functionWorkSpaces[functionWorkSpaces.size - 1][i-1] = workSpace[workSpaceIndex - i]
                }
                parIndex = workSpaceIndex
                workSpaceIndex = keCount
                funcFinder = true
            }
            when (tokenArray[tokenIndex]) {
                InstructionToken(Token.BEGIN) -> begin(tokenArray)
                InstructionToken(Token.END) -> end(tokenArray)
                InstructionToken(Token.MINUS) -> workSpace[workSpaceIndex]--
                InstructionToken(Token.PLUS) -> workSpace[workSpaceIndex]++
                InstructionToken(Token.RIGHT) -> right()
                InstructionToken(Token.LEFT) -> left()
                InstructionToken(Token.WRITE) ->
                    write.println(workSpace[workSpaceIndex])
                InstructionToken(Token.READ) -> workSpace[workSpaceIndex] = read.read().toByte()
                is FunDefToken -> {
                    while (tokenArray[tokenIndex] != InstructionToken(Token.ENDFUN))
                        tokenIndex++
                }
                InstructionToken(Token.ENDFUN) -> {
                    workSpace[workSpaceIndex] = functionWorkSpaces[functionWorkSpaces.size-1][parIndex]
                    while (true) {
                        if (tokenArray[tokenIndex] is FunCallToken) {
                            if ((tokenArray[tokenIndex] as FunCallToken).name == (funName as FunCallToken).name) {
                                break
                            }
                        } else
                            tokenIndex++
                    }
                    workSpaceIndex = parIndex
                }
                is FunCallToken -> {
                    funcFinder = false
                    functionWorkSpaces.add(ByteArray(memorySize, { 0 }))
                    funName = tokenArray[tokenIndex]
                    funNameIndex = tokenIndex
                    while (true) {
                        if (tokenArray[funNameIndex] is FunDefToken) {
                            if ((tokenArray[funNameIndex] as FunDefToken).name == (funName as FunCallToken).name) {
                                tokenIndex = funNameIndex
                                break
                            }
                        } else
                            funNameIndex--
                        if (funNameIndex == -1) {
                            funNameIndex = tokenArray.size - 1
                        }
                        if(funNameIndex == tokenIndex){
                            println("function must be initialized")
                            return
                        }
                    }
                    keCount = (tokenArray[funNameIndex] as FunDefToken).paramsCount
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
            while (!((arrayToken[tokenIndex] == InstructionToken(Token.END)) && (beginEndcounter == 0))) {
                when {
                    arrayToken[tokenIndex] == InstructionToken(Token.END) -> beginEndcounter--
                    arrayToken[tokenIndex] == InstructionToken(Token.BEGIN) -> beginEndcounter++
                }
                if ((arrayToken[tokenIndex] == InstructionToken(Token.END) && (beginEndcounter == 0)))
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
            while (!(arrayToken[tokenIndex] == InstructionToken(Token.BEGIN) && (beginEndcounter == 0))) {
                when {
                    arrayToken[tokenIndex] == InstructionToken(Token.END) -> beginEndcounter++
                    arrayToken[tokenIndex] == InstructionToken(Token.BEGIN) -> beginEndcounter--
                }
                if ((arrayToken[tokenIndex] == InstructionToken(Token.BEGIN) && (beginEndcounter == 0)))
                    break
                tokenIndex--

            }
        }
    }
}
