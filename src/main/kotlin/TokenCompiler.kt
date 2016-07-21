import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*
import java.io.File
import java.util.*
import javax.lang.model.SourceVersion

class TokenCompiler {
    /**
     * Compiles tokensArray
     * @returns byte-code of the class
     */
    private val beginLabelStack = Stack<Label>()
    private val endLabelStack = Stack<Label>()
    private val cw = ClassWriter(ClassWriter.COMPUTE_MAXS)
    private val functionsInfo = HashMap<String, Pair<String, Int>>()

    /**
     * Compiles code in 3 stages
     */
    fun compile(tokens: Array<NewToken>, className: String): ByteArray? {
        if (!SourceVersion.isIdentifier(className) || SourceVersion.isKeyword(className)) {
            println("Your name of class is invalid.")
            return null
        }
        val writer = File(className + ".class")
        cw.visit(V1_7, ACC_PUBLIC, className, null, "java/lang/Object", null)

        // Writes main method
        val vmMain = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null)
        with(vmMain) {
            visitCode()
            // Make array[30000]
            visitIntInsn(SIPUSH, 30000)
            visitIntInsn(NEWARRAY, T_CHAR)
            visitVarInsn(ASTORE, 0)
            // Make cursor
            visitInsn(ICONST_0)
            visitVarInsn(ISTORE, 1)
            var isFun = false
            for (token in tokens) {
                if(token is FunDefToken){
                    functionsInfo.put(token.name, Pair(signatureBuild(token.paramsCount), token.paramsCount))
                }
            }
            for (token in tokens) {
                if (!isFun && (token is InstructionToken)) {
                    when (token.instr) {
                        Token.PLUS -> sumCompile()
                        Token.MINUS -> subCompile()
                        Token.LEFT -> leftCompile()
                        Token.RIGHT -> rightCompile()
                        Token.WRITE -> writeCompile()
                        Token.READ -> readCompile()
                        Token.BEGIN -> beginCompile()
                        Token.END -> endCompile()
                        else -> {
                        }
                    }
                } else if (token is FunDefToken) {
                    isFun = true
                } else if (!isFun && (token is FunCallToken)) {
                    funCallCompile(token.name, className)
                } else if ((token is InstructionToken) && (token.instr == Token.ENDFUN)) {
                    isFun = false
                }

            }
            visitInsn(RETURN)
            visitMaxs(5, 2)
            visitEnd()
        }

        var vmFun: MethodVisitor? = null
        var isFun = false
        for (token in tokens) {
            if (token is FunDefToken) {
                vmFun = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, token.name, functionsInfo[token.name]?.first, null, null)
                vmFun.visitCode()
                for (i in token.paramsCount - 1 downTo 0) {
                    vmFun.visitVarInsn(ILOAD, i)
                    vmFun.visitVarInsn(ISTORE, i + 2)
                }
                // Make array[30000]
                vmFun.visitIntInsn(SIPUSH, 30000)
                vmFun.visitIntInsn(NEWARRAY, T_CHAR)
                vmFun.visitVarInsn(ASTORE, 0)
                for (i in token.paramsCount - 1 downTo 0) {
                    vmFun.visitVarInsn(ALOAD, 0)
                    vmFun.visitIntInsn(SIPUSH, i)
                    vmFun.visitVarInsn(ILOAD, i + 2)
                    vmFun.visitInsn(CASTORE)
                }
                // Make cursor
                vmFun.visitIntInsn(SIPUSH, token.paramsCount)
                vmFun.visitVarInsn(ISTORE, 1)
                isFun = true

            } else if (isFun && (token is FunCallToken)) {
                vmFun?.funCallCompile(token.name, className)
            } else if (isFun && (token is InstructionToken) && (vmFun != null)) {
                when (token.instr) {
                    Token.PLUS -> vmFun.sumCompile()
                    Token.MINUS -> vmFun.subCompile()
                    Token.LEFT -> vmFun.leftCompile()
                    Token.RIGHT -> vmFun.rightCompile()
                    Token.WRITE -> vmFun.writeCompile()
                    Token.READ -> vmFun.readCompile()
                    Token.BEGIN -> vmFun.beginCompile()
                    Token.END -> vmFun.endCompile()
                    Token.ENDFUN -> {
                        isFun = false
                        vmFun.funEndingCompile()
                    }
                    else -> {
                    }
                }
            }

        }

        cw.visitEnd()
        writer.writeBytes(cw.toByteArray())
        return cw.toByteArray()
    }

    /**
     * Adds 1 to current element of array
     */
    private fun MethodVisitor.sumCompile() {
        visitVarInsn(ALOAD, 0)
        visitVarInsn(ILOAD, 1)
        visitInsn(DUP2)
        visitInsn(CALOAD)
        //visitIntInsn(BIPUSH, 1)
        visitInsn(ICONST_1)
        visitInsn(IADD)
        visitInsn(CASTORE)
    }

    /**
     * Subs 1 from current element of array
     */
    private fun MethodVisitor.subCompile() {
        visitVarInsn(ALOAD, 0)
        visitVarInsn(ILOAD, 1)
        visitInsn(DUP2)
        visitInsn(CALOAD)
        //visitIntInsn(BIPUSH, -1)
        visitInsn(ICONST_M1)
        visitInsn(IADD)
        visitInsn(CASTORE)
    }

    /**
     * Moves cursor to the left
     */
    private fun MethodVisitor.leftCompile() {
        visitVarInsn(ILOAD, 1)
        visitInsn(ICONST_1)
        visitInsn(ISUB)
        visitInsn(DUP)
        val label = Label()
        visitJumpInsn(IFGE, label)
        visitInsn(POP)
        visitIntInsn(SIPUSH, 29999)
        visitLabel(label)
        visitFrame(F_FULL, 2, arrayOf("[C", INTEGER), 1, arrayOf(INTEGER))
        visitVarInsn(ISTORE, 1)
    }

    /**
     * Moves cursor to the right
     */
    private fun MethodVisitor.rightCompile() {
        visitVarInsn(ILOAD, 1)
        visitInsn(ICONST_1)
        visitInsn(IADD)
        val label = Label()
        visitInsn(DUP)
        visitIntInsn(SIPUSH, 29999)
        visitJumpInsn(IF_ICMPLE, label)
        visitInsn(POP)
        visitInsn(ICONST_0)
        visitLabel(label)
        visitFrame(F_FULL, 2, arrayOf("[C", INTEGER), 1, arrayOf(INTEGER))
        visitIntInsn(ISTORE, 1)
    }

    /**
     * Reads byte from input
     */
    private fun MethodVisitor.readCompile() {
        visitVarInsn(ALOAD, 0)
        visitVarInsn(ILOAD, 1)
        visitFieldInsn(GETSTATIC, "java/lang/System", "in", "Ljava/io/InputStream;")
        visitMethodInsn(INVOKEVIRTUAL, "java/io/InputStream", "read", "()I", false)
        visitInsn(I2B)
        visitInsn(CASTORE)
    }

    /**
     * Writes char to output
     */
    private fun MethodVisitor.writeCompile() {
        visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
        visitVarInsn(ALOAD, 0)
        visitVarInsn(ILOAD, 1)
        visitInsn(CALOAD)
        visitInsn(I2C)
        visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(C)V", false)
    }

    /**
     * Compiles beginning of loop
     */
    private fun MethodVisitor.beginCompile() {
        beginLabelStack.push(Label())
        endLabelStack.push(Label())
        visitLabel(beginLabelStack.lastElement())
        visitFrame(F_FULL, 2, arrayOf("[C", INTEGER), 0, null)
        visitVarInsn(ALOAD, 0)
        visitVarInsn(ILOAD, 1)
        visitInsn(CALOAD)
        visitJumpInsn(IFEQ, endLabelStack.lastElement())
    }

    /**
     * Compiles exit from loop
     */
    private fun MethodVisitor.endCompile() {
        visitJumpInsn(GOTO, beginLabelStack.lastElement())
        visitLabel(endLabelStack.lastElement())
        visitFrame(F_FULL, 2, arrayOf("[C", INTEGER), 0, null)
        beginLabelStack.pop()
        endLabelStack.pop()
    }

    /**
     * Makes fun description
     * @param paramsCount Number of parameters
     * @return description of function
     */
    private fun MethodVisitor.signatureBuild(paramsCount: Int): String {
        val sb = StringBuilder()
        sb.append("(")
        for (i in 1..paramsCount)
            sb.append("C")
        sb.append(")C")
        return sb.toString()
    }

    /**
     * Calls function
     * @param name Name of function
     * @param className Name of output class
     */
    private fun MethodVisitor.funCallCompile(name: String, className: String) {
        visitIntInsn(SIPUSH, functionsInfo[name]!!.second)
        visitVarInsn(NEWARRAY, T_CHAR)
        visitVarInsn(ASTORE, 2)
        for (i in 1..functionsInfo[name]!!.second) {
            visitIntInsn(ILOAD, 1)
            visitIntInsn(SIPUSH, i)
            visitInsn(ISUB)
            visitInsn(DUP)
            val label = Label()
            visitJumpInsn(IFGE, label)
            visitIntInsn(SIPUSH, 30000)
            visitInsn(IADD)
            visitLabel(label)
            visitFrame(F_FULL, 3, arrayOf("[C", INTEGER, "[C"), 1, arrayOf(INTEGER))

            visitVarInsn(ALOAD, 2)
            visitInsn(SWAP)
            visitIntInsn(SIPUSH, i - 1)
            visitInsn(SWAP)
            visitVarInsn(ALOAD, 0)
            visitInsn(SWAP)
            visitInsn(CALOAD)
            visitInsn(CASTORE)
        }
        for (i in 1..functionsInfo[name]!!.second) {
            visitVarInsn(ALOAD, 2)
            visitIntInsn(SIPUSH, i - 1)
            visitInsn(CALOAD)
        }
        visitMethodInsn(INVOKESTATIC, className, name, functionsInfo[name]?.first, false)
        visitVarInsn(ALOAD, 0)
        visitInsn(SWAP)
        visitVarInsn(ILOAD, 1)
        visitInsn(SWAP)
        visitInsn(CASTORE)
    }

    /**
     * Compiles end of function and returns result of function
     */
    private fun MethodVisitor.funEndingCompile() {
        visitVarInsn(ALOAD, 0)
        visitVarInsn(ILOAD, 1)
        visitInsn(CALOAD)
        visitFrame(F_FULL, 2, arrayOf("[C", INTEGER), 1, arrayOf(INTEGER))
        visitInsn(IRETURN)
        visitMaxs(10, 10)
        visitEnd()
    }
}