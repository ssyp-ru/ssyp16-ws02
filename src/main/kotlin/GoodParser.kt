import java.util.*

class GoodParser {
    private fun checkSubStr(str: String, start: Int, subStr: String): Boolean{
        for(i in 0..subStr.length - 1){
            if(str[start + i] != subStr[i])
                return false
        }
        return true
    }
    fun parse(code: String): Array<NewToken>{
        var curPosition = 0

        val tokens = LinkedList<NewToken>()
        while(curPosition < code.length){
            if((code[curPosition] == ' ') || (code[curPosition] == '\n') || (code[curPosition] == '\t'))
                curPosition++
            else{
                when{
                    checkSubStr(code, curPosition, "Ko") -> {
                        tokens.addLast(InstructionToken(Token.PLUS))
                        curPosition += 2
                    }
                    checkSubStr(code, curPosition, "kO") -> {
                        tokens.addLast(InstructionToken(Token.MINUS))
                        curPosition += 2
                    }
                    checkSubStr(code, curPosition, "Kudah") -> {
                        tokens.addLast(InstructionToken(Token.RIGHT))
                        curPosition += 5
                    }
                    checkSubStr(code, curPosition, "kudah") -> {
                        tokens.addLast(InstructionToken(Token.LEFT))
                        curPosition += 5
                    }
                    checkSubStr(code, curPosition, "Kud") -> {
                        tokens.addLast(InstructionToken(Token.BEGIN))
                        curPosition += 3
                    }
                    checkSubStr(code, curPosition, "kud") -> {
                        tokens.addLast(InstructionToken(Token.END))
                        curPosition += 3
                    }
                    checkSubStr(code, curPosition, "Kukarek") -> {
                        tokens.addLast(InstructionToken(Token.WRITE))
                        curPosition += 7
                    }
                    checkSubStr(code, curPosition, "kukarek") -> {
                        tokens.addLast(InstructionToken(Token.READ))
                        curPosition += 7
                    }
                    checkSubStr(code, curPosition, "Evening") -> {
                        tokens.addLast(InstructionToken(Token.ENDFUN))
                        curPosition += 7
                    }
                    checkSubStr(code, curPosition, "Morning") -> {
                        curPosition += 7
                        while((code[curPosition] == ' ') || (code[curPosition] == '\t') || (code[curPosition] == '\n')){
                            curPosition++
                        }
                        var endIndex = curPosition
                        while((code[endIndex] != ' ') && (code[endIndex] != '\t') && (code[endIndex] != '\n')){
                            endIndex++
                        }
                        val funName = code.substring(curPosition, endIndex)
                        var paramsCount = 0
                        curPosition = endIndex
                        while((code[curPosition] == ' ') || (code[curPosition] == '\t') || (code[curPosition] == '\n')){
                            curPosition++
                        }
                        while(checkSubStr(code, curPosition, "Ke")){
                            curPosition += 2
                            paramsCount++
                        }
                        tokens.addLast(FunDefToken(funName, paramsCount))
                    }
                    checkSubStr(code, curPosition, "PAR") -> {
                        curPosition += 3
                        while((code[curPosition] == ' ') || (code[curPosition] == '\t') || (code[curPosition] == '\n')){
                            curPosition++
                        }
                        var endIndex = curPosition
                        while((code[endIndex] != ' ') && (code[endIndex] != '\t') && (code[endIndex] != '\n')){
                            endIndex++
                        }
                        val funName = code.substring(curPosition, endIndex)
                        curPosition = endIndex
                        tokens.addLast(FunCallToken(funName))
                    }
                    else -> {
                        return Array<NewToken>(0, { InstructionToken(Token.PLUS) })
                    }
                }
            }
        }
        return tokens.toTypedArray()
    }
}