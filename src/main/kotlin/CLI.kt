package ssyp.ws02.petooh
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
                var bool = true
                print("--> Please enter the name of file: ")
                while (bool != false) {
                    val fileName = readLine() ?: ""
                    println()
                    when {
                        fileName.endsWith(".bf") -> {
                            bool = interpreter(false, fileName)
                        }
                        fileName.endsWith(".koko") -> {
                            bool = interpreter(true, fileName)
                        }
                        else ->
                            print("--> Error. Please enter the name of file bool: ")
                    }
                }
            }
            "2" -> {
                translate(true)
            }
            "3" -> {
                translate(false)
            }
            "4" -> {
                var bool = true
                while (bool != false) {
                    print("--> Please enter the name of file: ")
                    val fileName = readLine() ?: ""
                    println()
                    when {
                        fileName.endsWith(".bf") -> {
                            bool = compiler(false, fileName)
                        }
                        fileName.endsWith(".koko") -> {
                            bool = compiler(true, fileName)
                        }

                    }
                }

            }
            else -> {
                println("No such command.")
            }
        }
    }

    private fun translate(isPetooh: Boolean) {
        val enterFile: String
        val output: String
        val endsWith: String
        val endsPlus: String
        val boolType: Boolean
        when (isPetooh) {
            true -> {
                enterFile = "PETOOH"
                output = "(BF)"
                endsWith = ".koko"
                endsPlus = ".bf"
                boolType = true
            }
            false -> {
                enterFile = "BF"
                output = "(PETOOH)"
                endsWith = ".bf"
                endsPlus = ".koko"
                boolType = false
            }
            else -> {
                println("Translate error")
                return
            }
        }
        var bool = true
        while (bool != false) {
            println("Please enter the name of $enterFile file and name of output file $output")
            print(enterFile + " file: ")
            val fileName = readLine() ?: ""
            println()
            if (fileName.endsWith(endsWith)) {
                print("Output file: ")
                val outputFile = readLine() + endsPlus
                println()
                if (boolType) {
                    val tokens = petoohTranslator.translateToToken(fileName)
                    if (tokens.isEmpty()) {
                        return
                    }
                    brainfuckTranslator.translateToBrainfuck(tokens, outputFile)
                } else {
                    val tokens = brainfuckTranslator.translateToTokens(fileName)
                    if (tokens.isEmpty()) {
                        return
                    }
                    petoohTranslator.translateToKoko(tokens, outputFile)
                }
                bool = false
            }
        }
    }

    private fun compiler(isPetooh: Boolean, fileName: String): Boolean {
        val shouldAskAgain: Boolean
        val tokens: Array<Token>
        val code: String
        if (!isPetooh) {
            tokens = brainfuckTranslator.translateToTokens(fileName)
            code = "BF"
        } else {
            tokens = petoohTranslator.translateToToken(fileName)
            code = "PETOOH"
        }
        if (tokens.isEmpty()) {
            println("Error")
            return true
        }
        if (validator.check(tokens)) {
            println()
            print("--> Please enter the name of class: ")
            val outputClass = readLine() ?: ""
            compiler.compile(tokens, outputClass)
            shouldAskAgain = false
        } else {
            println("Syntax error in $code code")
            print("--> Error. Please enter the name of file again: ")
            shouldAskAgain = true
        }
        return shouldAskAgain
    }


    private fun interpreter(isPetooh: Boolean, fileName: String): Boolean {
        val Tokens: Any
        val code: String
        when (isPetooh) {
            true -> {
                Tokens = petoohTranslator.translateToToken(fileName)
                code = "PETOOH"
            }
            false -> {
                Tokens = brainfuckTranslator.translateToTokens(fileName)
                code = "BF"
            }
            else -> {
                println("Interpreter error")
                return true
            }
        }

        val bool: Boolean
        if (validator.check(Tokens)) {
            interpreter.interpret(Tokens)
            bool = false
        } else {
            println("Syntax error in $code code")
            print("--> Error. Please enter the name of file file: ")
            bool = true
        }
        return bool
    }


}

