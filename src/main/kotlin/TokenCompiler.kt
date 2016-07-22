import jdk.internal.org.objectweb.asm.ClassWriter
import jdk.internal.org.objectweb.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Opcodes.*
import java.io.File
import java.io.PrintStream
import java.util.*


class TokenCompiler{
    fun compile(tokens: Array<Token>){
        val writer = File("MyClass.class")
        val cw = ClassWriter(0)
        cw.visit(V1_7, ACC_PUBLIC, "MyClass", null, "java/lang/Object", null)
        val vm = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null)
        with(vm) {
            visitCode()
            //Make Scanner
            visitTypeInsn(NEW, "java/util/Scanner")
            visitInsn(DUP)
            visitFieldInsn(GETSTATIC, "java/lang/System", "in", "Ljava/io/InputStream;")
            visitMethodInsn(INVOKESPECIAL, "java/util/Scanner", "<init>", "(Ljava/io/InputStream;)V", false)
            visitVarInsn(ASTORE, 2)
            //Make array[30000]
            visitIntInsn(SIPUSH, 30000)
            visitIntInsn(NEWARRAY, T_BYTE)
            visitVarInsn(ASTORE, 0)
            //Make counter
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
    }

    /**
     * Add 1 to current element of array
     */
    fun MethodVisitor.sumCompile(){
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
    fun MethodVisitor.subCompile(){
        visitVarInsn(ALOAD, 0)
        visitVarInsn(ILOAD, 1)
        visitInsn(DUP2)
        visitInsn(BALOAD)
        visitInsn(ICONST_M1)
        visitInsn(IADD)
        visitInsn(BASTORE)
    }

    fun MethodVisitor.leftCompile(){
        visitVarInsn(ILOAD, 1)
        visitInsn(ICONST_M1)
        visitInsn(IADD)
        visitVarInsn(ISTORE, 1)
    }

    fun MethodVisitor.rightCompile(){
        visitVarInsn(ILOAD, 1)
        visitInsn(ICONST_1)
        visitInsn(IADD)
        visitVarInsn(ISTORE, 1)
    }

    fun MethodVisitor.readCompile() {
        visitVarInsn(ALOAD, 0)
        visitVarInsn(ILOAD, 1)
        visitVarInsn(ALOAD, 2)
        visitMethodInsn(INVOKEVIRTUAL, "java/util/Scanner", "nextByte", "()B", false)
        visitInsn(BASTORE)
    }

    fun MethodVisitor.writeCompile() {
        visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
        visitVarInsn(ALOAD, 0)
        visitVarInsn(ILOAD, 1)
        visitInsn(BALOAD)
        visitInsn(I2C)
        visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(C)V", false)
    }

}