package com.bartlomiejpluta.smnp.evaluation.evaluator

import com.bartlomiejpluta.smnp.dsl.ast.model.node.BlockNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.Node
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.evaluation.model.entity.EvaluatorOutput
import com.bartlomiejpluta.smnp.evaluation.model.enumeration.EvaluationResult

class BlockEvaluator(private val dedicatedScope: Boolean) : Evaluator() {
    override fun supportedNodes() = listOf(BlockNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val evaluator = DefaultEvaluator()

        if (dedicatedScope) environment.pushScope()
        val ok = (node as BlockNode).statements.all {
            evaluator.evaluate(it, environment).result != EvaluationResult.FAILED
        }
        if (dedicatedScope) environment.popScope()

        return if (ok) EvaluatorOutput.ok() else EvaluatorOutput.fail()
    }
}