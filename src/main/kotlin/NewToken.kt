abstract class NewToken()

class InstructionToken(val instr: String) : NewToken()

class IntToken(val number: Int) : NewToken()

class StringToken(val string: String) : NewToken()

