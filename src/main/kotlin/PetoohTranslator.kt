import java.io.File
import java.io.FileNotFoundException
import java.util.*

/**
 * Class PetoohTranslator has two methods: translateToToken and translateToKoko
 */
class PetoohTranslator {

    /**
     * Takes a file with Petooh code and returns array with Tokens.
     * If file not found, returns empty array.
     * @param fileKoko name of file
     * @returns array of Tokens
     */
    fun translateToToken(fileKoko: String): Array<Token> { //Petooh -> Token
        val strWithTokens: String
        try {
            strWithTokens = File(fileKoko).readText()
        } catch (exc: FileNotFoundException) {
            println("File not found!")
            return emptyArray()
        }
        val tokenArray = ArrayList<Token>()//return
        for (index in 0..strWithTokens.length) {

            when {
                strWithTokens.startsWith("kO", index) -> tokenArray.add(Token.PLUS)
                strWithTokens.startsWith("Ko", index) -> tokenArray.add(Token.MINUS)
                strWithTokens.startsWith("kudah", index) -> tokenArray.add(Token.LEFT)
                strWithTokens.startsWith("Kudah", index) -> tokenArray.add(Token.RIGHT)
                strWithTokens.startsWith("kud", index) -> tokenArray.add(Token.END)
                strWithTokens.startsWith("Kud", index) -> tokenArray.add(Token.BEGIN)
                strWithTokens.startsWith("kukarek", index) -> tokenArray.add(Token.WRITE)
                strWithTokens.startsWith("Kukarek", index) -> tokenArray.add(Token.READ)
            }
        }

        val retArray: Array<Token> = tokenArray.toTypedArray()

        return retArray//arr
    }

    // FIXME: 1) в javadoc-подписях строки должны начинаться с '*' (см. как выше), 2) javadoc-подписи не отделяются от подписываемой функции или класса
    /**
    Takes an array of Tokens and writes PETOOH code to output file.
    If file not found, creates a new file.
    @param arrToken:Array<Token> array of Token
    @param output:String name of output file
    @returns file with Petooh code
     */

    fun translateToKoko(arrToken: Array<Token>, output: String) { // Token -> Petooh
        val endFile = File(output) //output File
        val timeStr = StringBuilder()

        for (index in 0 until arrToken.size) {
            when (arrToken[index]) {
                Token.PLUS -> timeStr.append("ko")
                Token.MINUS -> timeStr.append("Ko")
                Token.READ -> timeStr.append("Kukarek")
                Token.END -> timeStr.append("kud")
                Token.BEGIN -> timeStr.append("Kud")
                Token.LEFT -> timeStr.append("kudah")
                Token.RIGHT -> timeStr.append("Kudah")
                Token.WRITE -> timeStr.append("kukarek")

            }
        }
        val endStr = timeStr.toString()
        endFile.writeText(endStr) // write if output file  // FIXME: чего? комментарии должны вносить понятность, а не выносить
    }

}