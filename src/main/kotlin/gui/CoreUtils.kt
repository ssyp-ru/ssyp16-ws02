package gui

import BrainfuckTranslator
import PetoohTranslator
import TokenCompiler

object CoreUtils {
    val petooh = PetoohTranslator()
    val brainfuck = BrainfuckTranslator()
    val compiler = TokenCompiler()
}