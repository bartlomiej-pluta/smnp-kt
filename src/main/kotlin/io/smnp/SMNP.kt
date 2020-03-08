package io.smnp

import io.smnp.api.environment.Environment
import io.smnp.dsl.ast.parser.RootParser
import io.smnp.dsl.token.tokenizer.DefaultTokenizer
import io.smnp.evaluation.evaluator.DefaultEvaluator

fun main(args: Array<String>) {
    val code = "[false, [@D], 1, 2, 3, 4]"
    val tokenizer = DefaultTokenizer()
    val parser = RootParser()
    val evaluator = DefaultEvaluator()

    val lines = code.split("\n")
    val tokens = tokenizer.tokenize(lines)
    val ast = parser.parse(tokens)


    val value = evaluator.evaluate(ast.node.children[0], Environment()).value!!

}