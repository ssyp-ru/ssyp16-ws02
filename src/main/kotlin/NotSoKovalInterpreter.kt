import java.io.InputStream
import java.io.PrintStream
import java.util.*

class NotSoKovalInterpreter(private val read: InputStream = System.`in`, private val write: PrintStream = System.out) {

    private val memorySize = 30000
    private var tokenIndex = 0
    private var beginEndcounter = 0

    fun iterpret(tokenArray: List<NewToken>){
        val workSpaces = ArrayList<WorkSpace>()
        workSpaces.add(WorkSpace(memorySize))
        //var currWorkSpace = workSpaces[0]
        val beginFunList = ArrayList<BeginFun>()
        while(tokenIndex != tokenArray.size) {
            when (tokenArray[tokenIndex]) {
                InstructionToken(Token.BEGIN) -> begin(tokenArray, workSpaces[workSpaces.size - 1])
                InstructionToken(Token.END) -> end(tokenArray, workSpaces[workSpaces.size - 1])
                InstructionToken(Token.MINUS) -> workSpaces[workSpaces.size - 1].memory[workSpaces[workSpaces.size - 1].index]--
                InstructionToken(Token.PLUS) -> workSpaces[workSpaces.size - 1].memory[workSpaces[workSpaces.size - 1].index]++
                InstructionToken(Token.RIGHT) -> right(workSpaces[workSpaces.size - 1])
                InstructionToken(Token.LEFT) -> left(workSpaces[workSpaces.size - 1])
                InstructionToken(Token.WRITE) ->
                    write.println(workSpaces[workSpaces.size - 1].memory[workSpaces[workSpaces.size - 1].index])
                InstructionToken(Token.READ) -> workSpaces[workSpaces.size - 1].memory[workSpaces[workSpaces.size - 1].index] = read.read().toByte()
                is FunDefToken -> {
                    beginFunList.add(BeginFun(tokenArray[tokenIndex] as FunDefToken, tokenIndex + 1))
                    while(tokenArray[tokenIndex] != InstructionToken(Token.ENDFUN))
                        tokenIndex++
                }
                is FunCallToken -> {
                    workSpaces.add(WorkSpace(memorySize))
                    for(c in beginFunList){
                        if((tokenArray[tokenIndex] as FunCallToken).name == c.funDef.name) {
                            workSpaces[workSpaces.size - 1].invokePoint = tokenIndex
                            for(i in 1..c.funDef.paramsCount) {
                                with(workSpaces) {
                                    get(size - 1).memory[i - 1] = get(size - 2).memory[get(size - 2).index - i] //FIXME perepolnenie
                                }
                            }
                            tokenIndex = c.funDefIndex
                            break
                        }
                    }
                }
                InstructionToken(Token.ENDFUN) -> {
                    with(workSpaces) {
                        get(size - 2).memory[get(size - 2).index] = get(size - 1).memory[get(size - 1).index]
                        tokenIndex = get(size - 1).invokePoint
                        removeAt(size - 1)
                    }
                }
            }
            tokenIndex++
        }
    }

    fun left(currWS: WorkSpace) {
        with(currWS) {
            if (index != 0) {
                index--
            } else
               index = memorySize - 1
        }
    }

    /**
     * Moves cursor to the right
     */
    fun right(currWS: WorkSpace) {
        with(currWS) {
            if (index != memorySize - 1) {
                index++
            } else
                index = 0
        }
    }

    /**
     * Starts loop if arrayToken[workSpaceIndex] != 0
     */
    private fun begin(arrayToken: List<NewToken>, currWS: WorkSpace) {
        with(currWS) {
            if (memory[index].toInt() == 0) {
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
    }

    /**
     * Ends loop if arrayToken[workSpaceIndex] = 0
     */
    private fun end(arrayToken: List<NewToken>, currWS: WorkSpace) {
        with(currWS) {
            if (memory[index].toInt() != 0) {
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

    class WorkSpace(val memorySize: Int) {
        val memory = ByteArray (memorySize, { 0 })
        var index = 0
        var invokePoint = 0
    }

    class BeginFun(val funDef : FunDefToken, val funDefIndex : Int)
}

