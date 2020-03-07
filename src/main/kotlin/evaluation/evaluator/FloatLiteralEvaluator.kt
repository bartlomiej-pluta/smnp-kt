package evaluation.evaluator

import data.model.Value
import dsl.ast.model.node.FloatLiteralNode
import dsl.ast.model.node.Node
import evaluation.environment.Environment
import evaluation.model.entity.EvaluatorOutput

class FloatLiteralEvaluator : Evaluator {
    override fun evaluate(node: Node, environment: Environment): EvaluatorOutput {
        val value = (node as FloatLiteralNode).token.value as Float
        return EvaluatorOutput.value(Value.float(value))
    }
}