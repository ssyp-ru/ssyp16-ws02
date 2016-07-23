class Validator {
    fun check(srt: Array<Token>): Boolean {
        var v = 0
        for (c in srt) {
            when (c) {
                Token.BEGIN -> v++
                Token.END -> v--
                else -> {
                }
            }
           if (v < 0)
               return false
        }
        return v == 0
    }
}
