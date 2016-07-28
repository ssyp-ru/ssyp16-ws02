package gui

import javafx.beans.binding.StringBinding
import javafx.scene.Cursor
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

        override fun print(x: Char) {
            with(textArea) {
                appendText(x.toString())
                selectAll()
                selectEnd()
                selectBackward()
                nextWord()
                //textArea.selectHome()
            }
        }
    }

    class MyInputStream(private val input: TextField) : InputStream() {
        val inputString = StringBuilder()

        fun addToStream(text: String) {
            inputString.append(text)
        }

        fun readFromTextField() {
            inputString.append(input.text)
        }

        fun clear() {
            if (inputString.isNotEmpty())
                inputString.removeRange(0, inputString.length - 1)
        }

        override fun read(): Int {
            val readChar = inputString.get(0).toInt()
            inputString.deleteCharAt(0)
            return readChar
        }
    }
}