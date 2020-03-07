package evaluation.evaluator

import data.model.Value
import dsl.ast.model.node.MapEntryNode
import dsl.ast.model.node.MapNode
import dsl.ast.model.node.Node
import evaluation.environment.Environment
import evaluation.model.entity.EvaluatorOutput

class MapEvaluator : Evaluator {
    override fun evaluate(node: Node, environment: Environment): EvaluatorOutput {
        val atomEvaluator = RootEvaluator()
        val value = (node as MapNode).items
            .map { it as MapEntryNode }
            .map { atomEvaluator.evaluate(it.key, environment).value!! to atomEvaluator.evaluate(it.value, environment).value!! }
            .toMap()

        return EvaluatorOutput.value(Value.map(value))
    }
}