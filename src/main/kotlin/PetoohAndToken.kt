import sun.invoke.empty.Empty
import java.io.File
import java.io.FileNotFoundException
import java.util.*


class PetoohAndToken {


    fun setFileToToken(fileKoko: String): Array<Token> { //Petooh -> Token
        val strToToken : String
        try {

            strToToken = File(fileKoko).readText()//string with Koko

        }
        catch (exc:FileNotFoundException){

            println("File not found!")
            return emptyArray()

        }
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

        val endFile = File(output)//output File
        var timeStr = StringBuilder()
        for (index in 0 until arrToken.size) {
            if (arrToken[index] == Token.PLUS) {
                timeStr.append("ko")
                continue
            }
            if (arrToken[index] == Token.MINUS) {
                timeStr.append("Ko")
                continue
            }
            if (arrToken[index] == Token.READ) {
                timeStr.append("Kukarek")
                continue
            }
            if (arrToken[index] == Token.END) {
                timeStr.append("kud")
                continue
            }
            if (arrToken[index] == Token.BEGIN) {
                timeStr.append("Kud")
                continue
            }
            if (arrToken[index] == Token.LEFT) {
                timeStr.append("kudah")
                continue
            }
            if (arrToken[index] == Token.RIGHT) {
                timeStr.append("Kudah")
                continue
            }
            if (arrToken[index] == Token.WRITE) {
                timeStr.append("kukarek")
                continue
            }


        }

         val endStr = timeStr.toString()

         endFile.appendText(endStr)

    }

}