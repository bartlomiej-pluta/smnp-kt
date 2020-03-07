package io.smnp.evaluation.evaluator

import io.smnp.data.model.Value
import io.smnp.dsl.ast.model.node.MapEntryNode
import io.smnp.dsl.ast.model.node.MapNode
import io.smnp.dsl.ast.model.node.Node
import io.smnp.evaluation.environment.Environment
import io.smnp.evaluation.model.entity.EvaluatorOutput

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