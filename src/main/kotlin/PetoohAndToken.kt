import java.io.File
import java.util.*

/**
 * Created by Vedrovski on 21.07.2016.
 */
class PetoohAndToken {


    fun setFileToToken(fileKoko: String): Array<Token> { //Petooh -> Token

        val strToToken = File(fileKoko).readText()//string with Koko
        val tokenArray = ArrayList<Token>()//return

        for (index in 0..strToToken.length) {

            if (strToToken.startsWith("ko", index) == true) {

                tokenArray.add(Token.PLUS)
                continue
            }

            if (strToToken.startsWith("Ko", index) == true) {

                tokenArray.add(Token.MINUS)
                continue

            }

            if (strToToken.startsWith("kud", index) == true) {

                tokenArray.add(Token.END)
                continue
            }

            if (strToToken.startsWith("Kud", index) == true) {

                tokenArray.add(Token.BEGIN)
                continue
            }

            if (strToToken.startsWith("kudah", index) == true) {
                tokenArray.add(Token.LEFT)
                continue
            }

            if (strToToken.startsWith("Kudah", index) == true) {

                tokenArray.add(Token.RIGHT)
                continue
            }

            if (strToToken.startsWith("kukarek", index) == true) {

                tokenArray.add(Token.WRITE)
                continue
            }

            if (strToToken.startsWith("Kukarek", index) == true) {

                tokenArray.add(Token.READ)
                continue
            }


        }

        val retArray:Array<Token> = tokenArray.toTypedArray()

        return retArray
    }


    fun setFileToKoko(arrToken: Array<Token>, output: String) { // Token -> Petooh


    }

}