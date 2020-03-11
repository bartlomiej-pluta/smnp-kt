package io.smnp.interpreter

import io.smnp.dsl.ast.parser.RootParser
import io.smnp.dsl.token.tokenizer.DefaultTokenizer
import io.smnp.environment.DefaultEnvironment
import io.smnp.environment.Environment
import io.smnp.evaluation.evaluator.RootEvaluator
import java.io.File

class Interpreter {
    private val tokenizer = DefaultTokenizer()
    private val parser = RootParser()
    private val evaluator = RootEvaluator()

    fun run(code: String) {
        val lines = code.split("\n")
        val tokens = tokenizer.tokenize(lines)
        val ast = parser.parse(tokens)

        val environment = createEnvironment()
        evaluator.evaluate(ast.node, environment)
    }

    private fun createEnvironment(): Environment {
        val environment = DefaultEnvironment()
        environment.loadModule("smnp.lang")

        return environment
    }

    fun run(file: File) {
        val lines = file.readLines()
        val tokens = tokenizer.tokenize(lines)
        val ast = parser.parse(tokens)

        val environment = createEnvironment()
        evaluator.evaluate(ast.node, environment)
    }
}