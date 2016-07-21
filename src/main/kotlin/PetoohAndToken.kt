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

        val retArray: Array<Token> = tokenArray.toTypedArray()

        return retArray//arr
    }


    fun setFileToKoko(arrToken: Array<Token>, output: String) { // Token -> Petooh

        val strToToken = File(output)//output File
        for (index in 0 until arrToken.size) {
            if (arrToken[index] == Token.PLUS) {
                strToToken.appendText("ko")
                continue
            }
            if (arrToken[index] == Token.MINUS) {
                strToToken.appendText("Ko")
                continue
            }
            if (arrToken[index] == Token.READ) {
                strToToken.appendText("Kukarek")
                continue
            }
            if (arrToken[index] == Token.END) {
                strToToken.appendText("kud")
                continue
            }
            if (arrToken[index] == Token.BEGIN) {
                strToToken.appendText("Kud")
                continue
            }
            if (arrToken[index] == Token.LEFT) {
                strToToken.appendText("kudah")
                continue
            }
            if (arrToken[index] == Token.RIGHT) {
                strToToken.appendText("Kudah")
                continue
            }
            if (arrToken[index] == Token.WRITE) {
                strToToken.appendText("kukarek")
                continue
            }


        }



    }

}