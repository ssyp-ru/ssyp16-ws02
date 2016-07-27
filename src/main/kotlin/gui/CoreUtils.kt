package gui

import BrainfuckTranslator
import Interpreter
import PetoohTranslator
import TokenCompiler

object CoreUtils {
    val petooh = PetoohTranslator()
    val brainfuck = BrainfuckTranslator()
    val compiler = TokenCompiler()
    val interp = Interpreter()
}