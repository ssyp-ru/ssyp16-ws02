package gui

import javafx.scene.control.TextArea
import javafx.stage.FileChooser
import tornadofx.Controller
import tornadofx.FileChooserMode
import tornadofx.chooseFile
import java.io.FileNotFoundException

class FileController :Controller() {
    fun getFile(){

        val arrChoser = arrayOf(FileChooser.ExtensionFilter("Koko file", "*.koko"), FileChooser.ExtensionFilter("Brainfuck file", "*.bf")) // FIXME: слишком длинная строка, разбей
        try {
            val files = chooseFile("Choose FILE", arrChoser, mode = FileChooserMode.Single)
            val fileName = files[0].toString()
            var file = files[0]
            curFile = fileName
            if (fileName.endsWith(".koko")) {
                isPetooh = true
                lastTextArea.appendText("PETOOH file \n")
            } else {
                isPetooh = false
                lastTextArea.appendText("BF file \n")
            }

            workTextArea.clear()
            val listText = file.readLines()
            for (i in 0 until listText.size) {
                workTextArea.appendText(listText[i])
                workTextArea.appendText("\r\n")
            }
        } catch (exc: IndexOutOfBoundsException) {
        } catch (exc: FileNotFoundException) {
            lastTextArea.appendText("File not found \n")
        }



        fun saveFile() {
            try {
                val arrChoser = arrayOf(FileChooser.ExtensionFilter("Koko file", "*.koko"), FileChooser.ExtensionFilter("Brainfuck file", "*.bf")) // FIXME: слишком длинная строка
                val saveFile = chooseFile("Save file", arrChoser, mode = FileChooserMode.Save)
                val fileName = saveFile[0]
                curFile = fileName.toString()
            } catch(exc: IndexOutOfBoundsException) {

            }
        }


    }
}