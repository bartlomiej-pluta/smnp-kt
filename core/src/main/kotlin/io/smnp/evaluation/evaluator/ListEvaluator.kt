package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.ListNode
import io.smnp.dsl.ast.model.node.Node
import io.smnp.environment.Environment
import io.smnp.evaluation.model.entity.EvaluatorOutput
import io.smnp.type.model.Value

class ListEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(ListNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val atomEvaluator = ExpressionEvaluator()
        val items = (node as ListNode).items
            .map { atomEvaluator.evaluate(it, environment) }
            .map { it.value }
        return EvaluatorOutput.value(Value.list(items))
    }
}