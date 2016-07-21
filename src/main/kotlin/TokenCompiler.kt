import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*
import java.io.File


class TokenCompiler{
    fun compile(tokens: Array<Token>){
        val writer = File("MyClass.class")
        val cw = ClassWriter(0)
        cw.visit(V1_7, ACC_PUBLIC, "MyClass", null, "java/lang/Object", null)
        val vm = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "(Ljava/lang/String;)V", null, null)
        vm.visitCode()
        vm.visitIntInsn(SIPUSH, 30000)
        vm.visitIntInsn(NEWARRAY, T_INT)
        vm.visitVarInsn(ASTORE, 0)
        vm.visitInsn(ICONST_0)
        vm.visitVarInsn(ISTORE, 1)
        for(i in tokens){
            when(i){
                Token.PLUS -> sumCompile(vm)
                Token.MINUS -> subCompile(vm)
                Token.LEFT -> leftCompile(vm)
                Token.RIGHT -> rightCompile(vm)
            }
        }
        vm.visitInsn(RETURN)
        vm.visitMaxs(4, 2)
        vm.visitEnd()
        cw.visitEnd()
        writer.writeBytes(cw.toByteArray())
    }

    fun sumCompile(vm: MethodVisitor){
        vm.visitVarInsn(ALOAD, 0)
        vm.visitVarInsn(ILOAD, 1)
        vm.visitInsn(DUP2)
        vm.visitInsn(IALOAD)
        vm.visitInsn(ICONST_1)
        vm.visitInsn(IADD)
        vm.visitInsn(IASTORE)
    }

    fun subCompile(vm: MethodVisitor){
        vm.visitVarInsn(ALOAD, 0)
        vm.visitVarInsn(ILOAD, 1)
        vm.visitInsn(DUP2)
        vm.visitInsn(IALOAD)
        vm.visitInsn(ICONST_M1)
        vm.visitInsn(IADD)
        vm.visitInsn(IASTORE)
    }

    fun leftCompile(vm :MethodVisitor){
        vm.visitVarInsn(ILOAD, 1)
        vm.visitInsn(ICONST_M1)
        vm.visitInsn(IADD)
        vm.visitVarInsn(ISTORE, 1)
    }

    fun rightCompile(vm :MethodVisitor){
        vm.visitVarInsn(ILOAD, 1)
        vm.visitInsn(ICONST_1)
        vm.visitInsn(IADD)
        vm.visitVarInsn(ISTORE, 1)
    }

    fun readCompile(vm :MethodVisitor) {

    }
}