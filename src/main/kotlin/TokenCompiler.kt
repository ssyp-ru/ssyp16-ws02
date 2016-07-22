import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*
import java.io.File
import java.util.*

class TokenCompiler{
    /**
     * Compiles tokensArray
     * @returns byte-code of the class
     */
    private val beginLabelStack = Stack<Label>()
    private val endLabelStack = Stack<Label>()
    private var i = 0
    private var tokens = Array<Token>(0, {Token.PLUS})
    fun compile(tokens: Array<Token>): ByteArray{
        this.tokens = tokens
        val writer = File("MyClass.class")
        val cw = ClassWriter(0)
        cw.visit(V1_7, ACC_PUBLIC, "MyClass", null, "java/lang/Object", null)
        val vm = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null)
        with(vm) {
            visitCode()
            //Make array[30000]
            visitIntInsn(SIPUSH, 30000)
            visitIntInsn(NEWARRAY, T_BYTE)
            visitVarInsn(ASTORE, 0)
            //Make cursor
            visitInsn(ICONST_0)
            visitVarInsn(ISTORE, 1)
            for (i in 0..tokens.size - 1) {
                when (tokens[i]) {
                    Token.PLUS -> sumCompile()
                    Token.MINUS -> subCompile()
                    Token.LEFT -> leftCompile()
                    Token.RIGHT -> rightCompile()
                    Token.WRITE -> writeCompile()
                    Token.READ -> readCompile()
                    Token.BEGIN -> beginCompile()
                    Token.END -> endCompile()
                }
            }
            visitInsn(RETURN)
            visitMaxs(4, 2)
            visitEnd()
        }
        cw.visitEnd()
        writer.writeBytes(cw.toByteArray())
        return cw.toByteArray()
    }

    /**
     * Add 1 to current element of array
     */
    private fun MethodVisitor.sumCompile(){
        visitVarInsn(ALOAD, 0)
        visitVarInsn(ILOAD, 1)
        visitInsn(DUP2)
        visitInsn(BALOAD)
        visitInsn(ICONST_1)
        visitInsn(IADD)
        visitInsn(BASTORE)
    }

    /**
     * Sub 1 from current element of array
     */
    private fun MethodVisitor.subCompile(){
        visitVarInsn(ALOAD, 0)
        visitVarInsn(ILOAD, 1)
        visitInsn(DUP2)
        visitInsn(BALOAD)
        visitInsn(ICONST_M1)
        visitInsn(IADD)
        visitInsn(BASTORE)
    }

    /**
     * Move cursor left
     */
    private fun MethodVisitor.leftCompile(){
        visitVarInsn(ILOAD, 1)
        visitInsn(ICONST_M1)
        visitInsn(IADD)
        visitVarInsn(ISTORE, 1)
    }

    /**
     * Move cursor right
     */
    private fun MethodVisitor.rightCompile(){
        visitVarInsn(ILOAD, 1)
        visitInsn(ICONST_1)
        visitInsn(IADD)
        visitVarInsn(ISTORE, 1)
    }

    /**
     * Read byte from input
     */
    private fun MethodVisitor.readCompile() {
        visitVarInsn(ALOAD, 0)
        visitVarInsn(ILOAD, 1)
        visitFieldInsn(GETSTATIC, "java/lang/System", "in", "Ljava/io/InputStream;")
        visitMethodInsn(INVOKEVIRTUAL, "java/io/InputStream", "read", "()I", false)
        visitInsn(I2B)
        visitInsn(BASTORE)
    }

    /**
     * Write char to output
     */
    private fun MethodVisitor.writeCompile() {
        visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
        visitVarInsn(ALOAD, 0)
        visitVarInsn(ILOAD, 1)
        visitInsn(BALOAD)
        visitInsn(I2C)
        visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(C)V", false)
    }

    /**
     * Compiles beginning of loop and maybe going to end
     */
    private fun MethodVisitor.beginCompile(){
        beginLabelStack.push(Label())
        endLabelStack.push(Label())
        visitLabel(beginLabelStack.lastElement())
        visitFrame(F_FULL, 2, arrayOf("[B", INTEGER), 0, null)
        visitVarInsn(ALOAD, 0)
        visitVarInsn(ILOAD, 1)
        visitInsn(BALOAD)
        visitJumpInsn(IFEQ, endLabelStack.lastElement())
    }

    private fun MethodVisitor.endCompile(){
        visitLabel(endLabelStack.lastElement())
        visitFrame(F_FULL, 2, arrayOf("[B", INTEGER), 0, null)

    }
}