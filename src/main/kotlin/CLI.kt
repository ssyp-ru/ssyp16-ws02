class CLI {
    private val brainfuckTranslator = BrainfuckTranslator()
    private val petoohTranslator = PetoohTranslator()
    private val interpreter = Interpreter()
    private val compiler = TokenCompiler()
    private val validator = Validator()

    fun MainMenu() {
        println("PetooKhan version 1.0.1")
        println("--> 1. Run PETOOH or BF file")
        println("--> 2. PETOOH translate to BF")
        println("--> 3. BF translate to PETOOH")
        println("--> 4. Compile BF or PETOOH file \n")
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
                            if (validator.check(arrBFTokens)) {
                                interpreter.interpret(arrBFTokens)
                                again = 2
                            }
                            else
                                println("Syntax error in BF code")
                        }
                        fileName.endsWith(".koko") -> {
                            val arrPTokens = petoohTranslator.translateToToken(fileName)
                            if (validator.check(arrPTokens)) {
                                interpreter.interpret(arrPTokens)
                                again = 2
                            }
                            else
                                println("Syntax error in PETOOH code")
                        }
                        else ->
                            print("--> Error. Please enter the name of file again: ")
                    }
                }
            }
            "2" -> {
                var again = 1
                while (again != 2) {
                    println("Please enter the name of PETOOH file and name of output file (BF)")
                    print("PETOOH file: ")
                    val fileName = readLine() ?: ""
                    println()
                    if (fileName.endsWith(".koko")) {
                        print("Output file: ")
                        val output = readLine() + ".bf"
                        println()
                        val arrBFTokens = petoohTranslator.translateToToken(fileName)
                        petoohTranslator.translateToKoko(arrBFTokens, output)
                        again = 2
                    } else
                        println("--> Error. Please enter the name of file again!")
                }
            }
            "3" -> {
                var again = 1
                while (again != 2) {
                    println("Plese enter the name of BF file and name of output file (PETOOH)")
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
                    } else
                        println("--> Error. Please enters the name of file again!")
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
                            if (validator.check(arrBFTokens)) {
                                compiler.compile(arrBFTokens)
                                again = 2
                            }
                            else
                                println("Syntax error in BF code")
                        }
                        fileName.endsWith(".koko") -> {
                            val arrPTokens = petoohTranslator.translateToToken(fileName)
                            if (validator.check(arrPTokens)) {
                                compiler.compile(arrPTokens)
                                again = 2
                            }
                            else
                                println("Syntax error in PETOOH code")
                        }
                        else ->
                            print("--> Error. Please enters the name of file again: ")
                    }
                }

            }
            else -> {
                println("No such command.")
                this.MainMenu()
            }
        }
    }
}