package evaluation.evaluator

import data.entity.Note
import data.model.Value
import dsl.ast.model.node.Node
import dsl.ast.model.node.NoteLiteralNode
import evaluation.environment.Environment
import evaluation.model.entity.EvaluatorOutput

class NoteLiteralEvaluator : Evaluator {
    override fun evaluate(node: Node, environment: Environment): EvaluatorOutput {
        val value = (node as NoteLiteralNode).token.value as Note
        return EvaluatorOutput.value(Value.note(value))
    }
}