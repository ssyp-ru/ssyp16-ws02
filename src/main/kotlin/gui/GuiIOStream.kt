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
            textArea.appendText(x.toString()) // FIXME: with(textArea)
            textArea.selectAll()
            textArea.selectEnd()
            textArea.selectBackward()
            textArea.nextWord()
            //textArea.selectHome()
        }

    }

    class MyInputStream(private val input:TextField) : InputStream() {

        fun addChar(text:String){
            inputString.append(text)
        }


        val inputString = StringBuilder()

        override fun read(): Int {

            val readChar = inputString.get(0).toInt()
            inputString.deleteCharAt(0)
            return readChar
        }
    }
}