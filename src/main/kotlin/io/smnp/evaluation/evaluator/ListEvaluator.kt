package io.smnp.evaluation.evaluator

import io.smnp.api.model.Value
import io.smnp.dsl.ast.model.node.ListNode
import io.smnp.dsl.ast.model.node.Node
import io.smnp.api.environment.Environment
import io.smnp.evaluation.model.entity.EvaluatorOutput

class ListEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(ListNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val atomEvaluator = ExpressionEvaluator()
        val items = (node as ListNode).items
            .map { atomEvaluator.evaluate(it, environment) }
            .map { it.value!! }
        return EvaluatorOutput.value(Value.list(items))
    }
}