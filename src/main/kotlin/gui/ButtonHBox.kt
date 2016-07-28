package gui

import javafx.scene.control.TextArea
import javafx.scene.layout.HBox
import javafx.stage.FileChooser
import javafx.stage.StageStyle
import tornadofx.*
import java.io.File
import java.io.FileNotFoundException
import Interpreter
import javafx.geometry.Pos
import javafx.scene.control.TextField
import javafx.scene.image.ImageView
import javafx.scene.layout.VBox
import javafx.scene.text.Font
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
//            hbox() {
//                hboxConstraints {
//                    marginLeftRight(10.0)
//                    marginTopBottom(10.0)
//                }
//            }
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

                /*button("Creators"){
                setOnAction {
                    var creat = Creators()
                    (creat.openModal(stageStyle = StageStyle.UTILITY))
                }
            }*/
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
        val arrChoser = arrayOf(FileChooser.ExtensionFilter("Koko file", "*.koko"),
                FileChooser.ExtensionFilter("Brainfuck file", "*.bf"))
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
            var fileTypeFilters = if (isPetooh) arrayOf(FileChooser.ExtensionFilter("Koko file", "*.koko"))
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
            val saveFile = chooseFile("Save file", arrChoser, mode = FileChooserMode.Save)
            val fileName = saveFile[0].toString()
            val tokens = CoreUtils.petooh.translateToToken(curFile)
            CoreUtils.brainfuck.translateToBrainfuck(tokens, fileName)

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
            lastTextArea.appendText("\n No input found. Please write something \n")
            inputTextField.requestFocus()
        }
        lastTextArea.appendText("\nFinished\n")
        // clear input stream
        if (input is GuiIOStream.MyInputStream)
            input.clear()
    }

}

/*class Creators():Fragment(){
    override val root = VBox()
    init{
        with(root){
            setMinSize(380.00,380.00)
            setMaxSize(380.00,380.00)
            imageview("file:\\\\\\C:\\Users\\Vedrovski\\IdeaProjects\\ws02\\PetooKhan(square).jpg")
            label("Creators"){
                font = Font.font("Coral")
                font = Font(20.0)
            }

        }

    }
}*/


