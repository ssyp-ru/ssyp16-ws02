package gui

import com.sun.deploy.uitoolkit.ui.ConsoleWindow
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import tornadofx.View
import tornadofx.plusAssign
import tornadofx.*



class GUIView: View() {
    override val root = VBox()

    val buttonHBox : ButtonHBox by inject()
    lateinit var b: TextArea
    init{

        root += buttonHBox
        with(root){
            textarea {
                setPrefSize(400.0,400.0)
            }
        }
        with(root){
            textarea {
                setPrefSize(100.0,100.0)
            }
        }
        root += TextField()
    }

}

class ButtonHBox : View(){
    override val root = HBox()


    init {
        root.spacing = 20.0

        root += Button("Compile")
        root += Button("BF -> Petooh")
        root += Button("Run")
        root += Button("Petooh -> BF")
        root += Button("AnotherB")
    }

}