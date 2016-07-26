package gui

import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.FileChooser
import javafx.stage.StageStyle
import tornadofx.*
import java.io.File
import PetoohTranslator
import BrainfuckTranslator
import TokenCompiler
import Interpreter
import java.io.FileNotFoundException

var lastTextArea = TextArea()
var workTextArea = TextArea()
var inputTextArea = TextArea()
var curFile = ""
var isPetooh = true
val petooh = PetoohTranslator()
val brainfuck = BrainfuckTranslator()
val compile = TokenCompiler()
val interp = Interpreter()

//var createForm = Form()


class GUIView : View() {
    override val root = VBox()

    val buttonHBox: ButtonHBox by inject()


    init {

        root += buttonHBox
        with(root) {
            workTextArea = textarea {
                setPrefSize(400.0, 400.0)
            }
        }
        with(root) {
            lastTextArea = textarea {
                setPrefSize(100.0, 100.0)
                textProperty()
            }
        inputTextArea =textarea {
            setPrefSize(100.0, 1.0)
        }
        }

    }

}

class ButtonHBox : View() {
    override val root = HBox()
    val cont = CodeController()
    var fragmentCompile = ClassName()


    init {
        with(root) {
            button("Create BF file") {

                setOnAction {
                    workTextArea.clear()
                    isPetooh = false
                    lastTextArea.appendText("Create BF file \n")
                }

            }
            button("Create PETOOH file") {
                setOnAction {
                    workTextArea.clear()
                    isPetooh = true
                    lastTextArea.appendText("Create PETOOH file \n")
                }
            }
            button("Open file") {
                setOnAction {
                    cont.getFile()

                }
            }
            button("Save") {
                setOnAction {
                    File(curFile).writeText(workTextArea.text)
                    lastTextArea.appendText("Saved!\n")
                }
            }
            hbox() {
                hboxConstraints {
                    marginLeftRight(10.0)
                    marginTopBottom(10.0)
                }
            }
            root.spacing = 20.0
            button("Compile") {
                setOnAction {
                    fragmentCompile = ClassName()
                    fragmentCompile.openModal(stageStyle = StageStyle.UTILITY)
                }
            }
            button("BF -> Petooh") {
                setOnAction {
                    if(isPetooh)
                        lastTextArea.appendText("Sorry, you have a PETOOH code \n")
                    else{
                    File(curFile).writeText(workTextArea.text)
                    val arrChoser = arrayOf(FileChooser.ExtensionFilter("Koko file", "*.koko"))
                    try {
                        val saveFile = chooseFile("Save file", arrChoser, mode = FileChooserMode.Save)
                        val fileName = saveFile[0].toString()
                        val tokens = brainfuck.translateToTokens(curFile)
                        petooh.translateToKoko(tokens, fileName)
                    } catch(exc: IndexOutOfBoundsException) {
                    }}
                }
            }
            button("Petooh -> BF") {
                setOnAction {
                    if(isPetooh){
                    File(curFile).writeText(workTextArea.text)
                    val arrChoser = arrayOf(FileChooser.ExtensionFilter("Brainfuck file", "*.bf"))
                    try {
                        val saveFile = chooseFile("Save file", arrChoser, mode = FileChooserMode.Save)
                        val fileName = saveFile[0].toString()
                        val tokens = petooh.translateToToken(curFile)
                        brainfuck.translateToBrainfuck(tokens, fileName)
                    } catch(exc: IndexOutOfBoundsException) {
                    }}
                    else
                        lastTextArea.appendText("Sorry, you have a BF code \n")


                }
            }
            button("Run") {
                setOnAction {
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
        }
    }
}

class CodeController : Controller() {

    fun getFile() {

        val arrChoser = arrayOf(FileChooser.ExtensionFilter("Koko file", "*.koko"), FileChooser.ExtensionFilter("Brainfuck file", "*.bf"))
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
    }

    fun saveFile() {
        try {
            val arrChoser = arrayOf(FileChooser.ExtensionFilter("Koko file", "*.koko"), FileChooser.ExtensionFilter("Brainfuck file", "*.bf"))
            val saveFile = chooseFile("Save file", arrChoser, mode = FileChooserMode.Save)
            val fileName = saveFile[0]
            curFile = fileName.toString()
        } catch(exc: IndexOutOfBoundsException) {
        }
    }
}


class ClassName : Fragment() {
    override val root = VBox()

    init {
        root.setMaxSize(300.0, 60.0)
        root.setMinSize(300.0, 60.0)
        with(root) {
            label("Enter a name of class")
            textfield {
                setOnAction {
                    var className = text
                    if (isPetooh) {
                        val tokens = petooh.translateToToken(curFile)
                        compile.compile(tokens, className)
                        lastTextArea.appendText(className + " Class compile! \n")
                    } else {
                        val tokens = brainfuck.translateToTokens(curFile)
                        compile.compile(tokens, className)
                        lastTextArea.appendText(className + " Class compile! \n")
                    }
                    closeModal()
                }
            }
        }
    }
}



