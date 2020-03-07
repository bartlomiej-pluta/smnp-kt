package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.Node
import io.smnp.evaluation.environment.Environment
import io.smnp.evaluation.model.entity.EvaluatorOutput
import io.smnp.evaluation.model.enumeration.EvaluationResult
import kotlin.reflect.KClass

interface Evaluator {
    fun evaluate(node: Node, environment: Environment): EvaluatorOutput

    companion object {
        fun forward(evaluator: Evaluator, vararg nodes: KClass<out Node>): Evaluator {
            return object : Evaluator {
                override fun evaluate(node: Node, environment: Environment): EvaluatorOutput {
                    if(nodes.any { it.isInstance(node) }) {
                        return evaluator.evaluate(node, environment)
                    }

                    return EvaluatorOutput.fail()
                }
            }
        }

        fun oneOf(vararg evaluators: Evaluator): Evaluator {
            return object : Evaluator {
                override fun evaluate(node: Node, environment: Environment): EvaluatorOutput {
                    for(evaluator in evaluators) {
                        val output = evaluator.evaluate(node, environment)
                        if(output.result != EvaluationResult.FAILED) {
                            return output
                        }
                    }

                    return EvaluatorOutput.fail()
                }
            }
        }
    }
}