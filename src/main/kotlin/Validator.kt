class Validator {
    fun check(srt: Array<Token>): Boolean { // FIXME: переименовать параметр; +1
        var v = 0 // FIXME: переименовать; +1
        for (c in srt) { // FIXME: переименовать "c"; +1
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
