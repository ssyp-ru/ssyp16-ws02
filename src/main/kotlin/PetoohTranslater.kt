import sun.invoke.empty.Empty
import java.io.File
import java.io.FileNotFoundException
import java.util.*

/**
Class PetoohTranslater have two method: setFileToToken and setFileToKoko
 */

class PetoohTranslater {

    /**
    setFileToToken - method which take file with Petooh code and return array with Tokens.
    If file not founded, returned emtpy array type Token.
    @param fileKoko name of file
    @returns array of Tokens
     */

    fun setFileToToken(fileKoko: String): Array<Token> { //Petooh -> Token
        val strWithTokens: String
        try {                                                             ////
            strWithTokens = File(fileKoko).readText()//string with Koko  ////
        } catch (exc: FileNotFoundException) {                          ////       catch(exc: FileNotFoundException)
            println("File not found!")                                   ////
            return emptyArray()                                         ////
        }                                                               ///
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

    /**
    setFileToKoko - method which take array with Tokens and write in output file.
    If file not found , created new file (mention).
    @param arrToken:Array<Token> array of Token
    @param output:String name of output file
    @returns file with Petooh code
     */

    fun setFileToKoko(arrToken: Array<Token>, output: String) { // Token -> Petooh
        val endFile = File(output)//output File
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
        endFile.appendText(endStr)// write if output file
    }

}