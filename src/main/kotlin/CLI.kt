/**
 * Created by Vedrovski on 22.07.2016.
 */
class CLI {

    //val againMenu = CLI()
    private var flag = 1
    private val brainfuckTranslator = BrainfuckTranslator()
    private val petoohTranslator = PetoohTranslator()

    fun MainMenu() {

        println("PetooKahn version 1.0.1")
        println("--> 1. Run Petooh or BF file")
        println("--> 2. Petooh translate to BF")
        println("--> 3. BF translate to Petooh")
        println("--> 4. Compile BF or Petooh file \n")
        when (readLine()) {
            "1" -> {
                var again = 1
                print("--> Please enter the name of file:")
                while (again != 2) {
                    val fileName = readLine() ?: ""
                    println()
                    when {
                        fileName.startsWith(".bf", fileName.length - 2) -> {
                            val arrBfTokens = brainfuckTranslator.translateToTokens(fileName)
                            //... work interpreter
                            again = 2
                        }
                        fileName.startsWith(".koko", fileName.length - 3) -> {
                           val arrPTokens = petoohTranslator.translateToToken(fileName)
                            //...work interpreter
                            again = 2
                        }
                        else -> print("--> Error. Please enters the name of file again:")
                    }
                }
            }
            "2" -> {

            }
            "3" -> {
                flag = 0

            }
            "4" -> {
                flag = 0

            }
            else
            -> {
                println("No such command.")
                this.MainMenu()
            }
        }


    }
}