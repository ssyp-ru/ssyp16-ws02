package gui

import java.io.InputStream
import java.io.OutputStream
import java.io.PrintStream

class GuiIOStream {
    class EmptyStream() : OutputStream() {
        override fun write(b: Int) {
        }
    }

    class MyPrintStream() : PrintStream(EmptyStream()) {

        override fun println(x: Char) {
            lastTextArea.appendText(x.toString())
        }
    }

    class MyInputStream(val str: String) : InputStream() {
        private var count = 0


        override fun read(): Int {
            count++
            return str[count - 1].toInt()
        }
    }
}