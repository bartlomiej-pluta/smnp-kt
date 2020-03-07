package evaluation.evaluator

import data.model.Value
import dsl.ast.model.node.ListNode
import dsl.ast.model.node.Node
import evaluation.environment.Environment
import evaluation.model.entity.EvaluatorOutput

class ListEvaluator : Evaluator {
    override fun evaluate(node: Node, environment: Environment): EvaluatorOutput {
        val atomEvaluator = RootEvaluator()
        val items = (node as ListNode).items
            .map { atomEvaluator.evaluate(it, environment) }
            .map { it.value!! }
        return EvaluatorOutput.value(Value.list(items))
    }
}