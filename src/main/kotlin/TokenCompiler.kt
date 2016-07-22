import jdk.internal.org.objectweb.asm.ClassWriter
import jdk.internal.org.objectweb.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Opcodes.*
import java.io.File
import java.io.PrintStream
import java.util.*

class TokenCompiler{
    /**
     * Compiles tokensArray
     * @returns byte-code of the class
     */
    fun compile(tokens: Array<Token>): ByteArray{
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
            for (i in tokens) {
                when (i) {
                    Token.PLUS -> sumCompile()
                    Token.MINUS -> subCompile()
                    Token.LEFT -> leftCompile()
                    Token.RIGHT -> rightCompile()
                    Token.WRITE -> writeCompile()
                    Token.READ -> readCompile()
                }
            }
            visitInsn(RETURN)
            visitMaxs(5, 3)
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

}