package gui

import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import java.io.InputStream
import java.io.OutputStream
import java.io.PrintStream

class GuiIOStream {
    class EmptyStream() : OutputStream() {
        override fun write(b: Int) {
        }
    }

    class GuiConsoleStream(private val textArea: TextArea) : PrintStream(EmptyStream()) {
        override fun println() {
            with(textArea) {
                appendText("\n")
                positionCaret(textArea.length - 1)
            }
        }

        override fun println(s: String) {
            with(textArea) {
                appendText("$s\n")
                positionCaret(textArea.length - 1)
            }
        }

        override fun print(x: Char) {
            with(textArea) {
                appendText(x.toString())
                positionCaret(textArea.length - 1)
            }
        }
    }

    class MyInputStream(private val input: TextField) : InputStream() {
        var inputString = StringBuilder()

        fun addToStream(text: String) {
            inputString.append(text)
        }

        fun readFromTextField() {
            inputString = StringBuilder()
            inputString.append(input.text)
        }

        fun clear() {
            inputString = StringBuilder()
        }

        override fun read(): Int {
            val readChar = inputString[0].toInt()
            inputString.deleteCharAt(0)
            return readChar
        }
    }
}