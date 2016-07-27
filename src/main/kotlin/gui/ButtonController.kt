package gui

import javafx.stage.FileChooser
import tornadofx.Controller
import tornadofx.FileChooserMode
import tornadofx.chooseFile
import java.io.File
import java.io.FileNotFoundException
import java.security.PrivilegedActionException
import Interpreter
import java.awt.TextArea


class ButtonController:Controller(){

    fun createFile(isTypePetooh: Boolean, curFile:String,workTextArea:TextArea) : String{
        if (curFile != "" || !curFile.isEmpty()) {
            File(curFile).writeText(workTextArea.text)
            return ""
        }
        workTextArea.

        lastTextArea.appendText("Create BF file \n")

    }

    fun saveCurFile(){
        try {
            if (curFile == "" || curFile.isEmpty()) {
                var arrChoser = emptyArray<FileChooser.ExtensionFilter>()
                if (isPetooh) {
                    arrChoser = arrayOf(FileChooser.ExtensionFilter("Koko file", "*.koko"))
                } else {
                    arrChoser = arrayOf(FileChooser.ExtensionFilter("BF file", "*.bf"))
                }
                val saveFile = chooseFile("Save new file", arrChoser, mode = FileChooserMode.Save)
                curFile = saveFile[0].toString()
            }
        } catch(exc: IndexOutOfBoundsException) {
        } catch(exc: PrivilegedActionException) {
        } catch(exc: FileNotFoundException) {
        }
        File(curFile).writeText(workTextArea.text)
        lastTextArea.appendText(curFile + " saved\n")
    }

    fun translateToPetooh(){
        if (isPetooh)
            lastTextArea.appendText("You already have PETOOH code \n")
        else {
            File(curFile).writeText(workTextArea.text)
            val arrChoser = arrayOf(FileChooser.ExtensionFilter("PETOOH file", "*.koko"))
            try {
                val saveFile = chooseFile("Save file", arrChoser, mode = FileChooserMode.Save)
                val fileName = saveFile[0].toString()
                val tokens = brainfuck.translateToTokens(curFile)
                petooh.translateToKoko(tokens, fileName)
            } catch(exc: IndexOutOfBoundsException) {
            }
        }
    }

    fun translateToBF(){
        if (isPetooh) {
            File(curFile).writeText(workTextArea.text)
            val arrChoser = arrayOf(FileChooser.ExtensionFilter("Brainfuck file", "*.bf"))
            try {
                val saveFile = chooseFile("Save file", arrChoser, mode = FileChooserMode.Save)
                val fileName = saveFile[0].toString()
                val tokens = petooh.translateToToken(curFile)
                brainfuck.translateToBrainfuck(tokens, fileName)
            } catch(exc: IndexOutOfBoundsException) {
            }
        } else
            lastTextArea.appendText("You already have BF code \n")
    }


    fun runFile(){
        val interp = Interpreter()
        if (isPetooh) {
            val tokens = petooh.translateToToken(curFile)
            interp.interpret(tokens)
        } else {
            val tokens = brainfuck.translateToTokens(curFile)
            interp.interpret(tokens)
        }
    }
}