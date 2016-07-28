/**
 * Command-line interface for PetoohKhan.
 * @author Maxim Usoltsev
 */
class CLI {
    private val brainfuckTranslator = BrainfuckTranslator()
    private val petoohTranslator = PetoohTranslator()
    private val interpreter = Interpreter()
    private val compiler = TokenCompiler()
    private val validator = Validator()
    private var globalLoopAgain = true
    fun MainMenu() {
        while (globalLoopAgain != false) {
            println("PetooKhan version 0.1")
            println("--> 1. Run PETOOH or BF file")
            println("--> 2. PETOOH translate to BF")
            println("--> 3. BF translate to PETOOH")
            println("--> 4. Compile BF or PETOOH file")
            println("--> 5. Exit")
            when (readLine()) {
                "1" -> {
                    var loopAgain = true
                    print("--> Please enter the name of file (.koko or .bf): ")
                    while (loopAgain != false) {
                        val fileName = readLine() ?: ""
                        println()
                        when {
                            fileName.endsWith(".bf") -> {
                                loopAgain = interpret(false, fileName)
                            }
                            fileName.endsWith(".koko") -> {
                                loopAgain = interpret(true, fileName)
                            }
                            else ->
                                print("--> Error. Please enter the name of an existing PETOOH or BF file: ")
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
                    var loopAgain = true
                    while (loopAgain != false) {
                        print("--> Please enter the name of file: ")
                        val fileName = readLine() ?: ""
                        println()
                        when {
                            fileName.endsWith(".bf") -> {
                                loopAgain = compile(false, fileName)
                            }
                            fileName.endsWith(".koko") -> {
                                loopAgain = compile(true, fileName)
                            }

                        }
                    }

                }

                "5" -> {
                    println("Exit")
                    globalLoopAgain = false
                }
                else -> {
                    println("No such command.")
                }


            }
        }

    }

    private fun translate(isPetooh: Boolean) {
        val enterFile = if (isPetooh) "PETOOH" else "BF"
        val output = if (isPetooh) "(BF)" else "(PETOOH)"
        val endsWith = if (isPetooh) ".koko" else ".bf"
        val endsPlus = if (isPetooh) ".bf" else ".koko"
        val boolType = if (isPetooh) true else false

        var loopAgain = true
        while (loopAgain != false) {
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
                println("Translate in $output")
                loopAgain = false
            }
        }
    }

    private fun compile(isPetooh: Boolean, fileName: String): Boolean {
        val tokens = if (!isPetooh) brainfuckTranslator.translateToTokens(fileName) else petoohTranslator.translateToToken(fileName)
        val code = if (!isPetooh) "BF" else "PETOOH"

        if (tokens.isEmpty()) {
            println("Error. Not found tokens")
            return true
        }
        if (validator.check(tokens)) {
            println()
            print("--> Please enter the name of class: ")
            val outputClass = readLine() ?: ""
            compiler.compile(tokens, outputClass)
            return false
        } else {
            println("Syntax error in $code code")
            print("--> Error. Please enter the name of file again: ")
            return true
        }
    }

    private fun interpret(isPetooh: Boolean, fileName: String): Boolean {
        val tokens = if (isPetooh) petoohTranslator.translateToToken(fileName) else brainfuckTranslator.translateToTokens(fileName)
        val code = if (isPetooh) "PETOOH" else "BF"
        val loopAgain: Boolean
        if (validator.check(tokens)) {
            interpreter.interpret(tokens)
            loopAgain = false
        } else {
            println("Syntax error in $code code")
            print("--> Error. Please enter the name of file file: ")
            loopAgain = true
        }
        return loopAgain
    }
}

