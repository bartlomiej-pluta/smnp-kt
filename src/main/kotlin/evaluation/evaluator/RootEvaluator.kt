package evaluation.evaluator

import dsl.ast.model.node.*
import evaluation.environment.Environment
import evaluation.evaluator.Evaluator.Companion.forward
import evaluation.evaluator.Evaluator.Companion.oneOf
import evaluation.model.entity.EvaluatorOutput

class RootEvaluator : Evaluator {
    override fun evaluate(node: Node, environment: Environment): EvaluatorOutput {
        return oneOf(
            forward(IntegerLiteralEvaluator(), IntegerLiteralNode::class),
            forward(FloatLiteralEvaluator(), FloatLiteralNode::class),
            forward(StringLiteralEvaluator(), StringLiteralNode::class),
            forward(BoolLiteralEvaluator(), BoolLiteralNode::class),
            forward(NoteLiteralEvaluator(), NoteLiteralNode::class),
            forward(ListEvaluator(), ListNode::class),
            forward(MapEvaluator(), MapNode::class)
        ).evaluate(node, environment)
    }
}