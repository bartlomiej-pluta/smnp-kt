package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.Node
import io.smnp.dsl.ast.model.node.RootNode
import io.smnp.evaluation.environment.Environment
import io.smnp.evaluation.model.entity.EvaluatorOutput
import io.smnp.evaluation.model.enumeration.EvaluationResult

class RootEvaluator : Evaluator {
    override fun evaluate(node: Node, environment: Environment): EvaluatorOutput {
        if(node !is RootNode) {
            return EvaluatorOutput.fail()
        }

        val evaluator = DefaultEvaluator()
        for(child in node.children) {
            val output = evaluator.evaluate(child, environment)
            if(output.result == EvaluationResult.FAILED) {
                return EvaluatorOutput.fail()
            }
        }

        return EvaluatorOutput.ok()
    }
}