abstract class NewToken()

class InstructionToken(val instr: Token) : NewToken() {
    override fun equals(other: Any?) =
            other is InstructionToken && other.instr == this.instr

    override fun hashCode() = instr.hashCode()
}

class FunDefToken(val name: String, val paramsCount: Int) : NewToken(){
    override fun equals(other: Any?) =
            other is FunDefToken && other.name == this.name && other.paramsCount == this.paramsCount

    override fun hashCode(): Int{
        var result = name.hashCode()
        result = 31 * result + paramsCount
        return result
    }
}

class FunCallToken(val name: String): NewToken(){
    override fun equals(other: Any?) =
            other is FunCallToken && other.name == this.name

    override fun hashCode() = name.hashCode()

}

