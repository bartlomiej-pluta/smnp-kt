package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.Node
import io.smnp.dsl.ast.model.node.ReturnNode
import io.smnp.environment.Environment
import io.smnp.evaluation.model.entity.EvaluatorOutput
import io.smnp.evaluation.model.exception.Return
import io.smnp.type.model.Value

class ReturnEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(ReturnNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val evaluator = ExpressionEvaluator()
        val (valueNode) = node as ReturnNode
        val value = evaluator.evaluate(valueNode, environment).value ?: Value.void()

        // Disclaimer
        // Exception system usage to control program execution flow is really bad idea.
        // However because of lack of 'goto' instruction equivalent in Kotlin
        // there is a need to use some mechanism to break function execution on 'return' statement
        // and immediately go to Environment's method 'invokeFunction()' or 'invokeMethod()',
        // which can handle value that came with exception and return it to code being executed.
        throw Return(value)
    }
}