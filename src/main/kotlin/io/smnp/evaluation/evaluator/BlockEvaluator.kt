package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.BlockNode
import io.smnp.dsl.ast.model.node.Node
import io.smnp.api.environment.Environment
import io.smnp.evaluation.model.entity.EvaluatorOutput
import io.smnp.evaluation.model.enumeration.EvaluationResult

class BlockEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(BlockNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val evaluator = DefaultEvaluator()
        val ok = (node as BlockNode).statements.all {
            evaluator.evaluate(it, environment).result == EvaluationResult.OK
        }

        return if(ok) EvaluatorOutput.ok() else EvaluatorOutput.fail()
    }
}