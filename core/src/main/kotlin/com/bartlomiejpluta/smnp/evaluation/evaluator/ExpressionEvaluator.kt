package com.bartlomiejpluta.smnp.evaluation.evaluator

import com.bartlomiejpluta.smnp.dsl.ast.model.node.Node
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.evaluation.model.entity.EvaluatorOutput
import com.bartlomiejpluta.smnp.evaluation.model.enumeration.EvaluationResult

class ExpressionEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(Node::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val output = oneOf(
            IntegerLiteralEvaluator(), 
            FloatLiteralEvaluator(), 
            StringLiteralEvaluator(), 
            BoolLiteralEvaluator(), 
            NoteLiteralEvaluator(),
            StaffEvaluator(),
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