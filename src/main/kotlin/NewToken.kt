abstract class NewToken()

class InstructionToken(val instr: Token) : NewToken()

class FunDefToken(val name: String, val paramsCount: Int) : NewToken()

class FunCallToken(val name: String): NewToken()

