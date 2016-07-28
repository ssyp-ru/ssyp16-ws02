/**
 * Validates tokenized Brainfuck/PETOOH code.
 * @author Tatyana Nikolaeva
 */
class Validator {
    fun check(tokens: Array<Token>): Boolean {
        var counter = 0
        for (element in tokens) {
            when (element) {
                Token.BEGIN -> counter++
                Token.END -> counter--
                else -> {
                }
            }
            if (counter < 0)
                return false
        }
        return counter == 0
    }
}
