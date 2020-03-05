package interpreter

import dsl.ast.model.node.Node
import dsl.ast.parser.Parser
import dsl.ast.parser.StatementParser
import dsl.token.tokenizer.DefaultTokenizer
import java.io.File

class Interpreter {
    fun run(code: String) {
        val tokenizer = DefaultTokenizer()
        val lines = code.split("\n")
        val tokens = tokenizer.tokenize(lines)

        val ast = Parser.repeat(StatementParser()) { nodes, pos -> object : Node(nodes, pos) {}  }.parse(tokens)

        ast.node.pretty()
        println(tokens)
    }

    fun run(file: File) {
        val tokenizer = DefaultTokenizer()
        val lines = file.readLines()
        val tokens = tokenizer.tokenize(lines)
        println(tokens)
    }
}