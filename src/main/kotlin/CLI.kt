class CLI {
    private val brainfuckTranslator = BrainfuckTranslator()
    private val petoohTranslator = PetoohTranslator()
    private val interpreter = Interpreter()
    private val compiler = TokenCompiler()
    private val validator = Validator()

    fun MainMenu() {
        println("PetooKhan version 0.1")
        println("--> 1. Run PETOOH or BF file")
        println("--> 2. PETOOH translate to BF")
        println("--> 3. BF translate to PETOOH")
        println("--> 4. Compile BF or PETOOH file \n")
        when (readLine()) {
            "1" -> {
                var bool = true // FIXME: переименовать; +2
                print("--> Please enter the name of file: ") // FIXME: указать требуемые расширения файлов
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
                var bool = true // FIXME: переимновать; +2
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
            else -> { // FIXME: поддержать команду exit
                println("No such command.") // FIXME: не вылетать после этого
            }
        }
    }

    private fun translate(isPetooh: Boolean) {
        val enterFile: String // FIXME: избавиться от этой паскалевской простыни и when; например, каждую переменную присваивать с if-ом (тернарником)
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
            else -> { // FIXME: много ещё вариантов у Boolean переменной, кроме true и false? +4
                println("Translate error")
                return
            }
        }
        var bool = true // FIXME: переименовать; +2
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

    private fun compiler(isPetooh: Boolean, fileName: String): Boolean { // FIXME: переименовать в глагол (compile)
        val shouldAskAgain: Boolean
        val tokens: Array<Token> // FIXME: убрать паскалевскую простыню
        val code: String // FIXME: переименовать
        if (!isPetooh) {
            tokens = brainfuckTranslator.translateToTokens(fileName)
            code = "BF"
        } else {
            tokens = petoohTranslator.translateToToken(fileName)
            code = "PETOOH"
        }
        if (tokens.isEmpty()) {
            println("Error") // FIXME: какой именно Error? сделать понятнее
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


    private fun interpreter(isPetooh: Boolean, fileName: String): Boolean { // FIXME: переименовать в глагол
        val Tokens: Any // FIXME: 1) убрать паскалевскую простыню; 2) переменные называем с маленькой буквы; 3) Any?! +4
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

        val bool: Boolean // FIXME: переименовать; +2
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

