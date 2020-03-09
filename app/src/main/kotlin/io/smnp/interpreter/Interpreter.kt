package io.smnp.interpreter

import io.smnp.dsl.ast.parser.RootParser
import io.smnp.dsl.token.tokenizer.DefaultTokenizer
import java.io.File

class Interpreter {
    fun run(code: String) {
        val tokenizer = DefaultTokenizer()
        val parser = RootParser()

        val lines = code.split("\n")
        val tokens = tokenizer.tokenize(lines)
        val ast = parser.parse(tokens)

        ast.node.pretty()
        println(tokens)
    }

    fun run(file: File) {
        val tokenizer = DefaultTokenizer()
        val parser = RootParser()

        val lines = file.readLines()
        val tokens = tokenizer.tokenize(lines)
        val ast = parser.parse(tokens)

        ast.node.pretty()
        println(tokens)
    }
}