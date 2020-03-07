package io.smnp

import io.smnp.dsl.ast.parser.RootParser
import io.smnp.dsl.token.tokenizer.DefaultTokenizer
import io.smnp.evaluation.environment.Environment
import io.smnp.evaluation.evaluator.RootEvaluator

fun main(args: Array<String>) {
    val code = "{ \"a\" -> [14, 15, 16], 4 -> @c }"
    val tokenizer = DefaultTokenizer()
    val parser = RootParser()
    val evaluator = RootEvaluator()

    val lines = code.split("\n")
    val tokens = tokenizer.tokenize(lines)
    val ast = parser.parse(tokens)

    val value = evaluator.evaluate(ast.node, Environment())

    println(value)
}