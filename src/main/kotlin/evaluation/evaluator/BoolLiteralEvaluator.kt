package evaluation.evaluator

import data.model.Value
import dsl.ast.model.node.BoolLiteralNode
import dsl.ast.model.node.Node
import evaluation.environment.Environment
import evaluation.model.entity.EvaluatorOutput

class BoolLiteralEvaluator : Evaluator {
    override fun evaluate(node: Node, environment: Environment): EvaluatorOutput {
        val value = (node as BoolLiteralNode).token.value as Boolean
        return EvaluatorOutput.value(Value.bool(value))
    }
}