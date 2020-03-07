package evaluation.evaluator

import data.model.Value
import dsl.ast.model.node.IntegerLiteralNode
import dsl.ast.model.node.Node
import evaluation.environment.Environment
import evaluation.model.entity.EvaluatorOutput

class IntegerLiteralEvaluator : Evaluator {
    override fun evaluate(node: Node, environment: Environment): EvaluatorOutput {
        val value = (node as IntegerLiteralNode).token.value as Int
        return EvaluatorOutput.value(Value.int(value))
    }
}