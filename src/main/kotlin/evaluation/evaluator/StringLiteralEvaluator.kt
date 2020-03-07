package evaluation.evaluator

import data.model.Value
import dsl.ast.model.node.Node
import dsl.ast.model.node.StringLiteralNode
import evaluation.environment.Environment
import evaluation.model.entity.EvaluatorOutput

class StringLiteralEvaluator : Evaluator {
    override fun evaluate(node: Node, environment: Environment): EvaluatorOutput {
        val value = (node as StringLiteralNode).token.value as String
        return EvaluatorOutput.value(Value.string(value))
    }
}