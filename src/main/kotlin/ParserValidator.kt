class ParserValidator() {
    fun validator(tokenArray: List<NewToken>): Boolean {
        var beginEndCounter = 0
        var morningEveningValidator = 0
        for (index in 0 until tokenArray.size) {
            when {
                tokenArray[index] == InstructionToken(Token.BEGIN) -> beginEndCounter++
                tokenArray[index] == InstructionToken(Token.END) -> beginEndCounter--
                tokenArray[index] is FunDefToken -> {
                    morningEveningValidator++
                    if (morningEveningValidator > 1)
                        return false
                }
                tokenArray[index] == InstructionToken(Token.ENDFUN) -> {
                    morningEveningValidator--
                    if (morningEveningValidator < 0)
                        return false
                }
            }
        }
        if ((beginEndCounter != 0) || (morningEveningValidator != 0))
            return false
        else
            return true
    }
}