package gui

import Interpreter
import javafx.geometry.Pos
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import javafx.stage.FileChooser
import javafx.stage.StageStyle
import tornadofx.*
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream
import java.io.PrintStream

/**
 * Top panel with buttons.
 */
class ButtonHBox(
        private val workTextArea: TextArea,
        private val lastTextArea: TextArea,
        private val input: InputStream,
        private val output: PrintStream,
        private val inputTextField: TextField
) : View() {
    override val root = HBox()
    var curFile = ""
    var isPetooh = true
    var isCreate = false

    init {
        with(root) {
            hbox() {
                alignment = Pos.BASELINE_LEFT
                button("Create BF file") {
                    setOnAction {
                        createFile(false)
                        isPetooh = false
                        isCreate = true
                    }
                }
                button("Create PETOOH file") {
                    setOnAction {
                        createFile(true)
                        isPetooh = true
                        isCreate = true
                    }
                }
                button("Open file") {
                    setOnAction {
                        openFile()
                        isCreate = true
                    }
                }
                button("Save") {
                    setOnAction {
                        saveCurFile()
                    }
                }
            }
            spacing = 50.0
            hbox() {
                alignment = Pos.BASELINE_RIGHT

                button("Compile") {
                    setOnAction {
                        val fragmentCompile = ClassNameDialog { compile(it) }
                        fragmentCompile.openModal(stageStyle = StageStyle.UTILITY)
                    }
                }
                button("Run") {
                    setOnAction {
                        runFile()
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
            lastTextArea.appendText(className + " compiled! \n")
        } else {
            val tokens = CoreUtils.brainfuck.translateToTokens(curFile)
            CoreUtils.compiler.compile(tokens, className)
            lastTextArea.appendText(className + " compiled! \n")
        }
    }

    fun openFile() {
        val arrChoser = arrayOf(FileChooser.ExtensionFilter("PETOOH file", "*.koko"),
                FileChooser.ExtensionFilter("Brainfuck file", "*.bf"))
        try {
            val files = chooseFile("Choose file", arrChoser, mode = FileChooserMode.Single)
            val fileName = files[0].toString()
            val file = files[0]
            curFile = fileName
            readFile(file)
        } catch (exc: IndexOutOfBoundsException) {
        } catch (exc: FileNotFoundException) {
            lastTextArea.appendText("File not found \n")
        }
    }

    fun saveCurFile(): Boolean {
        if (curFile == "") {
            var fileTypeFilters = if (isPetooh) arrayOf(FileChooser.ExtensionFilter("PETOOH file", "*.koko"))
            else
                arrayOf(FileChooser.ExtensionFilter("BF file", "*.bf"))
            if (!isCreate)
                fileTypeFilters = arrayOf(FileChooser.ExtensionFilter("BF file", "*.bf"), FileChooser.ExtensionFilter("Koko file", "*.koko"))

            val chosenFiles = chooseFile("Save new file", fileTypeFilters, mode = FileChooserMode.Save)
            if (chosenFiles.isEmpty())
                return false
            curFile = chosenFiles[0].toString()
            if (curFile.endsWith(".koko"))
                isPetooh = true
            else
                isPetooh = false
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
                val file = saveFile[0]
                val tokens = CoreUtils.brainfuck.translateToTokens(curFile)
                CoreUtils.petooh.translateToKoko(tokens, fileName)
                lastTextArea.appendText("$fileName saved\n")
                readFile(file)
                isPetooh = true

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
        if (!isPetooh)
            lastTextArea.appendText("You already have BF code \n")
        else {
            File(curFile).writeText(workTextArea.text)
            val arrChoser = arrayOf(FileChooser.ExtensionFilter("BF file", "*.bf"))
            try {
                val saveFile = chooseFile("Save file", arrChoser, mode = FileChooserMode.Save)
                val fileName = saveFile[0].toString()
                val file = saveFile[0]
                val tokens = CoreUtils.petooh.translateToToken(curFile)
                CoreUtils.brainfuck.translateToBrainfuck(tokens, fileName)
                lastTextArea.appendText("$fileName saved\n")
                readFile(file)
                isPetooh = false
            } catch(exc: IndexOutOfBoundsException) {
            }
        }
    }

    fun runFile() {
        if (curFile == "") {
            val isSaved = saveCurFile()
            if (!isSaved) {
                return
            }
        }
        // read input from text field
        if (input is GuiIOStream.MyInputStream)
            input.readFromTextField()
        val interp = Interpreter(read = input, write = output)
        File(curFile).writeText(workTextArea.text)
        lastTextArea.appendText("Run \n")
        val tokens = if (isPetooh) {
            CoreUtils.petooh.translateToToken(curFile)
        } else {
            CoreUtils.brainfuck.translateToTokens(curFile)
        }
        try {
            interp.interpret(tokens)
        } catch(exc: IndexOutOfBoundsException) {
            lastTextArea.style {
                textFill = Color.RED
            }
            lastTextArea.appendText("\n No input found. Please write something \n")
            lastTextArea.style {
                //textFill = Color.GREEN
            }
            inputTextField.requestFocus()
        }
        lastTextArea.appendText("\nFinished\n")
        // clear input stream
        if (input is GuiIOStream.MyInputStream)
            input.clear()
    }

    fun readFile(file: File) {
        if (file.toString().endsWith(".koko")) {
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
    }

}




