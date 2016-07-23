class CLI {

    //val againMenu = CLI()
    private var flag = 1
    private val brainfuckTranslator = BrainfuckTranslator()
    private val petoohTranslator = PetoohTranslator()
    private val interpreter = Interpreter()
    private val compiler = TokenCompiler()

    fun MainMenu() {

        println("PetooKahn version 1.0.1")
        println("--> 1. Run Petooh or BF file")
        println("--> 2. Petooh translate to BF")
        println("--> 3. BF translate to Petooh")
        println("--> 4. Compile BF or Petooh file \n")
        when (readLine()) {
            "1" -> {
                var again = 1
                print("--> Please enter the name of file: ")
                while (again != 2) {
                    val fileName = readLine() ?: ""
                    println()
                    when {
                        fileName.endsWith(".bf") -> {
                            val arrBFTokens = brainfuckTranslator.translateToTokens(fileName)
                            //<-- validator###
                            interpreter.interpret(arrBFTokens)
                            again = 2
                        }
                        fileName.endsWith(".koko") -> {
                            val arrPTokens = petoohTranslator.translateToToken(fileName)
                            //<-- validator###
                            interpreter.interpret(arrPTokens)
                            again = 2
                        }
                        else -> print("--> Error. Please enter the name of file again: ")
                    }
                }
            }
            "2" -> {
                var again = 1
                while(again != 2) {
                    println("Plese enter the name of Petooh file and name of output file (BF)")
                    print("Petooh file: ")
                    val fileName = readLine() ?: ""
                    println()
                    if (fileName.endsWith(".koko")) {
                    print("Output file: ")
                    val output = readLine() + ".bf"
                    println()
                        val arrBFTokens = petoohTranslator.translateToToken(fileName)
                        petoohTranslator.translateToKoko(arrBFTokens, output)
                        again = 2
                    } else println("--> Error. Please enter the name of file again!")

                }
            }
            "3" -> {
                var again = 1
                while(again != 2) {
                    println("Plese enter the name of BF file and name of output file (Petooh)")
                    print("BF file: ")
                    val fileName = readLine() ?: ""
                    println()
                    if (fileName.endsWith(".bf")) {
                    print("Output file: ")
                    val output = readLine() + ".koko"
                    println()

                        val arrPTokens = brainfuckTranslator.translateToTokens(fileName)
                        brainfuckTranslator.translateToBrainfuck(arrPTokens, output)
                        again = 2
                    }else println("--> Error. Please enters the name of file again!")
                }
            }
            "4" -> {
                var again = 1
                print("--> Please enter the name of file: ")
                while (again != 2) {
                    val fileName = readLine() ?: ""
                    println()
                    when {
                        fileName.endsWith(".bf") -> {
                            val arrBFTokens = brainfuckTranslator.translateToTokens(fileName)
                            //validator
                            compiler.compile(arrBFTokens)
                            again = 2
                        }
                        fileName.endsWith(".koko") -> {
                            val arrPTokens = petoohTranslator.translateToToken(fileName)
                            //validator
                            compiler.compile(arrPTokens)
                            again = 2
                        }
                        else -> print("--> Error. Please enters the name of file again: ")
                    }
                }

            }
            else
            -> {
                println("No such command.")
                this.MainMenu()
            }
        }


    }
}