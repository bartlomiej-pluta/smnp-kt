package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.*
import io.smnp.evaluation.environment.Environment
import io.smnp.evaluation.evaluator.Evaluator.Companion.forward
import io.smnp.evaluation.evaluator.Evaluator.Companion.oneOf
import io.smnp.evaluation.model.entity.EvaluatorOutput

class DefaultEvaluator : Evaluator {
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