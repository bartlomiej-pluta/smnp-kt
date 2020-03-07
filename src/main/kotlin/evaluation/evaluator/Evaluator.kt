package evaluation.evaluator

import dsl.ast.model.node.Node
import evaluation.environment.Environment
import evaluation.model.entity.EvaluatorOutput
import evaluation.model.enumeration.EvaluationResult
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