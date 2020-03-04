package interpreter

import dsl.token.tokenizer.DefaultTokenizer
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream

class Interpreter {
    fun run(code: String) {
        val tokenizer = DefaultTokenizer()
        val lines = code.split("\n")
        val tokens = tokenizer.tokenize(lines)
        println(tokens)
    }

    fun run(file: File) {
        val tokenizer = DefaultTokenizer()
        val lines = file.readLines()
        val tokens = tokenizer.tokenize(lines)
        println(tokens)
    }
}