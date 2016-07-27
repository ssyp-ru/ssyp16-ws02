package gui

//TODO Save,interprer,"chmok-chmok",window,scroll,DISABLE


import javafx.scene.control.Label // FIXME: эх, сейчас бы варнингов пооставлять в коммите
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
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import sun.awt.ScrollPaneWheelScroller
import java.awt.Button
import java.io.FileNotFoundException
import java.security.PrivilegedActionException
import kotlin.reflect.KProperty

//var lastTextArea = TextArea()   console
//var workTextArea = TextArea()
//var inputTextArea = TextArea()
//var curFile = ""
//var isPetooh = true
//val petooh = PetoohTranslator()
//val brainfuck = BrainfuckTranslator()
//val compile = TokenCompiler()
//val interp = Interpreter()
//var ifFileCreate = false


class GUIView : View() {
    override val root = VBox()
    val buttonHBox: ButtonHBox by inject()
    val fileControl: FileController by inject()
    val codeControl: CodeController by inject()
    lateinit var workTextArea: TextArea
    init {
        root += buttonHBox
        with(root) {
           workTextArea = textarea {
                setPrefSize(400.0, 400.0)
                font = Font.font("Verdana")
            }
        }
        with(root) {
                var lastTextArea = textarea {
                setPrefSize(100.0, 100.0)
                textProperty()
                font = Font.font("Verdana")
                style {
                    fontWeight = FontWeight.EXTRA_BOLD
                }
            }
            var inputTextArea = textarea {
                setPrefSize(100.0, 1.0)
                font = Font.font("Verdana")
            }
        }

    }

    operator fun  getValue(buttonHBox: ButtonHBox, property: KProperty<*>): TextArea {
        return  workTextArea
    }
}

class ButtonHBox() : View() { // FIXME: текущий режим (BF или PETOOH) должен где-то отображаться
    override val root = HBox()
    val workArea: TextArea by find(GUIView::class)
    val fileControl = FileController()
    var fragmentCompile = ClassName()
    val buttonController = ButtonController()
    var ifFileCreate = false
    var curFile = ""
    var isPetooh = true
    init {

        with(root) {

            button("Create BF file") {
                setOnAction {
                    buttonController.createFile()
                    isPetooh = false
                    ifFileCreate = true
                    work
                }
            }
            button("Create PETOOH file") {
                setOnAction {
                    buttonController.createFile()
                }
            }
            button("Open file") {
                setOnAction {
                    fileControl.getFile()
                    ifFileCreate = true
                }
            }
            button("Save") { // FIXME: валится с исключением, чини
                setOnAction {
                    buttonController.saveCurFile()
                }
            }
            hbox() {
                hboxConstraints {
                    marginLeftRight(10.0)
                    marginTopBottom(10.0)
                }
            }
            spacing = 20.0
            button("Compile") {
                setOnAction {
                    fragmentCompile = ClassName()
                    fragmentCompile.openModal(stageStyle = StageStyle.UTILITY)
                }
            }

            button("BF -> Petooh") {
                setOnAction {
                    buttonController.translateToPetooh()
                }
            }

            button("Petooh -> BF") {

                setOnAction {
                    buttonController.translateToBF()
                }
            }
            button("Run") {

                setOnAction {
                    buttonController.runFile()
                }
            }
        }
    }
}



class ClassName : Fragment() { // FIXME: вынести в отдельный файл; переименовать. ClassName? Серьёзно?
    override val root = VBox()
    val compile = TokenCompiler()
    init {
        root.setMaxSize(300.0, 60.0)
        root.setMinSize(300.0, 60.0)
        with(root) {
            label("Enter a name of class")
            textfield {
                setOnAction {
                    val className = text
                    if (isPetooh) {
                        val tokens = petooh.translateToToken(curFile)
                        compile.compile(tokens, className)
                        lastTextArea.appendText(className + " Class compiled! \n")
                    } else {
                        val tokens = brainfuck.translateToTokens(curFile)
                        compile.compile(tokens, className)
                        lastTextArea.appendText(className + " Class compiled! \n")

                    }
                }
            }
        }
    }
}




