abstract class NewToken()

class InstructionToken(val instr: Token) : NewToken()

class StringToken(val string: String) : NewToken()
