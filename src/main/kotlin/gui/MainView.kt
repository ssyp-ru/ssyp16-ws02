package gui

import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import tornadofx.View
import tornadofx.plusAssign
import tornadofx.style

/**
 * Main window.
 */
class MainView : View() {
    override val root = VBox()
    val workTextArea = TextArea()
    val consoleTextArea = TextArea()
    val inputTextField = TextField()
    val inputStream = GuiIOStream.MyInputStream(inputTextField)
    val printStream = GuiIOStream.GuiConsoleStream(consoleTextArea)
    val buttonHBox = ButtonHBox(workTextArea, consoleTextArea, inputStream, printStream, inputTextField)

    init {
        System.setOut(printStream)

        title = "Petooh Khan"
        primaryStage.isResizable = false
        // init UI elements
        with(workTextArea) {
            setPrefSize(400.0, 400.0)
            font = Font.font("Courier New")
            setOnKeyReleased {
            }
        }
        with(consoleTextArea) {
            isMouseTransparent = false
            isWrapText = true
            isEditable = false
            setOnKeyPressed { }
            setPrefSize(100.0, 100.0)
            textProperty()
            font = Font.font("Verdana")
            style {
                fontWeight = FontWeight.EXTRA_BOLD
                textFill = Color.GREEN
            }
        }
        with(inputTextField) {
            setPrefSize(100.0, 1.0)
            font = Font.font("Verdana")
            setOnMouseClicked {
                inputStream.addToStream(text)
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







