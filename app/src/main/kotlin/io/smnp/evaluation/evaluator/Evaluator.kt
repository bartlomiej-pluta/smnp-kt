package io.smnp.evaluation.evaluator

import io.smnp.dsl.ast.model.node.Node
import io.smnp.environment.Environment
import io.smnp.evaluation.model.entity.EvaluatorOutput
import io.smnp.evaluation.model.enumeration.EvaluationResult
import io.smnp.evaluation.util.ContextExceptionFactory.contextEvaluationException
import kotlin.reflect.KClass

abstract class Evaluator {
   fun evaluate(node: Node, environment: Environment): EvaluatorOutput {
      if (supportedNodes().any { it.isInstance(node) }) {
         return tryToEvaluate(node, environment)
      }

      return EvaluatorOutput.fail()
   }

   protected abstract fun supportedNodes(): List<KClass<out Node>>
   protected abstract fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput

   companion object {
      fun oneOf(vararg evaluators: Evaluator): Evaluator {
         return object : Evaluator() {
            override fun supportedNodes() = listOf(Node::class)

            override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
               for (evaluator in evaluators) {
                  val output = evaluator.evaluate(node, environment)
                  if (output.result != EvaluationResult.FAILED) {
                     return output
                  }
               }

               return EvaluatorOutput.fail()
            }
         }
      }

      fun repeat(evaluator: Evaluator): Evaluator {
         return object : Evaluator() {
            override fun supportedNodes() = listOf(Node::class)

            override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
               for (child in node.children) {
                  val output = evaluator.evaluate(child, environment)
                  if (output.result == EvaluationResult.FAILED) {
                     return EvaluatorOutput.fail()
                  }
               }

               return EvaluatorOutput.ok()
            }
         }
      }

      fun assert(evaluator: Evaluator, expected: String): Evaluator {
         return object : Evaluator() {
            override fun supportedNodes() = listOf(Node::class)

            override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
               val output = evaluator.evaluate(node, environment)

               if (output.result == EvaluationResult.FAILED) {
                  throw contextEvaluationException("Expected $expected", node.position, environment)
               }

               return output
            }
         }
      }
   }
}