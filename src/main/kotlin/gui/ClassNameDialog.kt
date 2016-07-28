package gui

import javafx.scene.layout.VBox
import tornadofx.Fragment
import tornadofx.label
import tornadofx.textfield


class ClassNameDialog(private val doCompile: (String) -> Unit) : Fragment() {
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
                    closeModal()

                }
            }
        }
    }
}