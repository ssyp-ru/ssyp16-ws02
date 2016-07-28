package gui

//TODO interprer,"chmok-chmok",window,DISABLE

import BrainfuckTranslator
import Interpreter
import PetoohTranslator
import TokenCompiler
import com.sun.xml.internal.ws.org.objectweb.asm.Label
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.control.TextFormatter
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import tornadofx.*

/**
 * Main window.
 */
class MainView : View() {
    override val root = VBox()
    val codeControl: CodeController by inject() // FIXME: не используется же. Или удали, или используй
    val workTextArea = TextArea()
    val consoleTextArea = TextArea()
    val inputTextField = TextField()
    val inputStream = GuiIOStream.MyInputStream(inputTextField)
    val printStream = GuiIOStream.GuiConsoleStream(consoleTextArea)
    val buttonHBox = ButtonHBox(workTextArea, consoleTextArea,inputStream,printStream)
    val codeStr = StringBuilder()
            init {

        // init UI elements
        with(workTextArea) {
            setPrefSize(400.0, 400.0)
            font = Font.font("Verdana")
            setOnKeyReleased {
                //textFormatter.filter.andThen(,)
            }
        }
        with(consoleTextArea) {
            isMouseTransparent = false
            isWrapText = true
            setPrefSize(100.0, 100.0)
            textProperty()
            font = Font.font("Verdana")
            style {
                fontWeight = FontWeight.EXTRA_BOLD
            }
        }
        with(inputTextField) {
            setPrefSize(100.0, 1.0)
            font = Font.font("Verdana")
            setOnAction {
                inputStream.addChar(text)
            }
        }

        // add all UI elements to root
        with(root) {
            this += buttonHBox
            this += workTextArea
            this += consoleTextArea
            this += inputTextField
        }
    }



}

class ClassNameDialog(private val doCompile: (String) -> Unit) : Fragment() { // FIXME: вынеси в отдельный файл
    override val root = VBox()

    init {
        root.setMaxSize(300.0, 60.0)
        root.setMinSize(300.0, 60.0)
        with(root) {
            label("Enter a name of class")
            textfield {
                setOnAction {
                    val className = text
                    doCompile(className)

                }
            }
        }
    }
}






