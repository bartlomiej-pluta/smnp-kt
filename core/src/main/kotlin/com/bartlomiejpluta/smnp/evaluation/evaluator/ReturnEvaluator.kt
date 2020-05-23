package com.bartlomiejpluta.smnp.evaluation.evaluator

import com.bartlomiejpluta.smnp.dsl.ast.model.node.Node
import com.bartlomiejpluta.smnp.dsl.ast.model.node.ReturnNode
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.evaluation.model.entity.EvaluatorOutput
import com.bartlomiejpluta.smnp.evaluation.model.exception.Return

class ReturnEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(ReturnNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val evaluator = ExpressionEvaluator()
        val (valueNode) = node as ReturnNode
        val value = evaluator.evaluate(valueNode, environment).value

        // Disclaimer
        // Exception system usage to control program execution flow is really bad idea.
        // However because of lack of 'goto' instruction equivalent in Kotlin
        // there is a need to use some mechanism to break function execution on 'return' statement
        // and immediately go to Environment's method 'invokeFunction()' or 'invokeMethod()',
        // which can handle value that came with exception and return it to code being executed.
        throw Return(value)
    }
}