package io.smnp.evaluation.evaluator

import io.smnp.data.enumeration.DataType
import io.smnp.dsl.ast.model.node.ConditionNode
import io.smnp.dsl.ast.model.node.Node
import io.smnp.dsl.ast.model.node.NoneNode
import io.smnp.error.EvaluationException
import io.smnp.evaluation.environment.Environment
import io.smnp.evaluation.model.entity.EvaluatorOutput

class ConditionEvaluator : Evaluator() {
    override fun supportedNodes() = listOf(ConditionNode::class)

    override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
        val expressionEvaluator = ExpressionEvaluator()
        val defaultEvaluator = DefaultEvaluator()
        val (conditionNode, trueBranchNode, falseBranchNode) = (node as ConditionNode)
        val condition = expressionEvaluator.evaluate(conditionNode, environment).value!!

        if(condition.type != DataType.BOOL) {
            throw EvaluationException("Condition should be of bool type, found '${condition.value}'", conditionNode.position)
        }

        if(condition.value!! as Boolean) {
            return defaultEvaluator.evaluate(trueBranchNode, environment)
        } else if(falseBranchNode !is NoneNode) {
            return defaultEvaluator.evaluate(falseBranchNode, environment)
        }

        return EvaluatorOutput.fail()
    }
}