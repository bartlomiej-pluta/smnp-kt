package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.Node
import io.smnp.environment.Environment
import io.smnp.evaluation.model.entity.EvaluatorOutput
import io.smnp.evaluation.model.enumeration.EvaluationResult

class ExpressionEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(Node::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val output = oneOf(
            IntegerLiteralEvaluator(), 
            FloatLiteralEvaluator(), 
            StringLiteralEvaluator(), 
            BoolLiteralEvaluator(), 
            NoteLiteralEvaluator(), 
            ListEvaluator(), 
            MapEvaluator(),
            IdentifierEvaluator(),

            MinusOperatorEvaluator(), 
            NotOperatorEvaluator(),
            PowerOperatorEvaluator(),
            ProductOperatorEvaluator(),
            SumOperatorEvaluator(),
            AccessOperatorEvaluator(),
            LogicOperatorEvaluator(),
            RelationOperatorEvaluator(),
            AssignmentOperatorEvaluator(),

            FunctionCallEvaluator(),

            LoopEvaluator()
        ).evaluate(node, environment)

        if(output.result == EvaluationResult.OK) {
            throw RuntimeException("One evaluator of expression evaluator has returned a success output with no value, ${node.position}")
        }

        return output
    }
}