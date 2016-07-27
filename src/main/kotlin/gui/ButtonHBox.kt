package gui

import javafx.scene.control.TextArea
import javafx.scene.layout.HBox
import javafx.stage.FileChooser
import javafx.stage.StageStyle
import tornadofx.*
import java.io.File
import java.io.FileNotFoundException
import Interpreter
import netscape.security.UserDialogHelper
import java.io.InputStream
import java.io.PrintStream
import java.security.PrivilegedActionException

/**
 * Top panel with buttons.
 */
class ButtonHBox(
        private val workTextArea: TextArea,
        private val lastTextArea: TextArea,
        private val input: InputStream,
        private val output: PrintStream
) : View() {
    override val root = HBox()
    var ifFileCreate = false
    var curFile = ""
    var isPetooh = true


    init {
        with(root) {
            button("Create BF file") {
                setOnAction {
                    createFile(false)
                }
            }
            button("Create PETOOH file") {
                setOnAction {
                    createFile(true)
                }
            }
            button("Open file") {
                setOnAction {
                    openFile()
                    ifFileCreate = true
                }
            }
            button("Save") {

                setOnAction {
                    saveCurFile()
                }
            }
            hbox() {
                hboxConstraints {
                    marginLeftRight(10.0)
                    marginTopBottom(10.0)
                }
            }
            spacing = 20.0
            val buttonCompile = button("Compile") {
                setOnAction {
                    val fragmentCompile = ClassNameDialog { compile(it) }
                    fragmentCompile.openModal(stageStyle = StageStyle.UTILITY)
                }
            }

            button("BF -> Petooh") {
                setOnAction {
                    translateToPetooh()
                }
            }

            button("Petooh -> BF") {
                setOnAction {
                    translateToBF()
                }
            }

            button("Run") {
                setOnAction {
                    runFile()
                }
            }
            button("Creators"){
                setOnAction {

                }
            }
        }
    }

    fun createFile(isTypePetooh: Boolean) {
        if (curFile != "" || !curFile.isEmpty()) {
            File(curFile).writeText(workTextArea.text)
            curFile = ""
        }
        if (!isTypePetooh) {
            isPetooh = false
            lastTextArea.appendText("Create BF file \n")
        } else {
            isPetooh = true
            lastTextArea.appendText("Create PETOOH file \n")
        }
        workTextArea.clear()
        ifFileCreate = true
    }

    fun compile(className: String) {
        if (curFile == "") {
            val isSaved = saveCurFile()
            if (!isSaved) {
                return
            }
        }
        if (isPetooh) {
            val tokens = CoreUtils.petooh.translateToToken(curFile)
            CoreUtils.compiler.compile(tokens, className)
            lastTextArea.appendText(className + " Class compiled! \n")
        } else {
            val tokens = CoreUtils.brainfuck.translateToTokens(curFile)
            CoreUtils.compiler.compile(tokens, className)
            lastTextArea.appendText(className + " Class compiled! \n")
        }
    }

    fun openFile() {
        val arrChoser = arrayOf(FileChooser.ExtensionFilter("Koko file", "*.koko"), FileChooser.ExtensionFilter("Brainfuck file", "*.bf")) // FIXME: слишком длинная строка, разбей
        try {
            val files = chooseFile("Choose file", arrChoser, mode = FileChooserMode.Single)
            val fileName = files[0].toString()
            val file = files[0]
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
    }


    fun saveCurFile(): Boolean {
        if (curFile == "") {
            val fileTypeFilters =
                    arrayOf(FileChooser.ExtensionFilter("Koko file", "*.koko"),FileChooser.ExtensionFilter("BF file", "*.bf"))

            val chosenFiles = chooseFile("Save new file", fileTypeFilters, mode = FileChooserMode.Save)
            if (chosenFiles.isEmpty())
                return false
            curFile = chosenFiles[0].toString()
        }
        File(curFile).writeText(workTextArea.text)
        lastTextArea.appendText(curFile + " saved\n")
        return true
    }

    fun translateToPetooh() {
        if (curFile == "") {
            val isSaved = saveCurFile()
            if (!isSaved) {
                return
            }
        }
        if (isPetooh)
            lastTextArea.appendText("You already have PETOOH code \n")
        else {
            File(curFile).writeText(workTextArea.text)
            val arrChoser = arrayOf(FileChooser.ExtensionFilter("PETOOH file", "*.koko"))
            try {
                val saveFile = chooseFile("Save file", arrChoser, mode = FileChooserMode.Save)
                val fileName = saveFile[0].toString()
                val tokens = CoreUtils.brainfuck.translateToTokens(curFile)
                CoreUtils.petooh.translateToKoko(tokens, fileName)
            } catch(exc: IndexOutOfBoundsException) {
            }
        }
    }

    fun translateToBF() {
        if (curFile == "") {
            val isSaved = saveCurFile()
            if (!isSaved) {
                return
            }
        }

        if (isPetooh) {
            File(curFile).writeText(workTextArea.text)
            val arrChoser = arrayOf(FileChooser.ExtensionFilter("Brainfuck file", "*.bf"))
            try {
                val saveFile = chooseFile("Save file", arrChoser, mode = FileChooserMode.Save)
                val fileName = saveFile[0].toString()
                val tokens = CoreUtils.petooh.translateToToken(curFile)
                CoreUtils.brainfuck.translateToBrainfuck(tokens, fileName)
            } catch(exc: IndexOutOfBoundsException) {
            } catch (exc: PrivilegedActionException) {
            } catch(exc: FileNotFoundException) {
            }
        } else
            lastTextArea.appendText("You already have BF code \n")
    }

    fun runFile() {
        if (curFile == "") {
            val isSaved = saveCurFile()
            if (!isSaved) {
                return
            }
        }
        val interp = Interpreter(read = input, write = output)
        if (isPetooh) {
            val tokens = CoreUtils.petooh.translateToToken(curFile)
            interp.interpret(tokens)
        } else {
            val tokens = CoreUtils.brainfuck.translateToTokens(curFile)
            interp.interpret(tokens)
        }
    }

}
