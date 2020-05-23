package com.bartlomiejpluta.smnp.evaluation.evaluator

import com.bartlomiejpluta.smnp.dsl.ast.model.node.ListNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.Node
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.evaluation.model.entity.EvaluatorOutput
import com.bartlomiejpluta.smnp.type.model.Value

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