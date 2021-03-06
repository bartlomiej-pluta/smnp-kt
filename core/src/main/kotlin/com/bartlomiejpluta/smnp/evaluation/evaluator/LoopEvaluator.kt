package com.bartlomiejpluta.smnp.evaluation.evaluator

import com.bartlomiejpluta.smnp.dsl.ast.model.node.IdentifierNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.LoopNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.Node
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.evaluation.model.entity.EvaluatorOutput
import com.bartlomiejpluta.smnp.evaluation.model.enumeration.EvaluationResult
import com.bartlomiejpluta.smnp.evaluation.util.ContextExceptionFactory.contextEvaluationException
import com.bartlomiejpluta.smnp.type.enumeration.DataType.*
import com.bartlomiejpluta.smnp.type.model.Value

class LoopEvaluator : Evaluator() {
   private val defaultEvaluator = assert(DefaultEvaluator(), "correct statement")
   private val expressionEvaluator = assert(ExpressionEvaluator(), "expression")
   override fun supportedNodes() = listOf(LoopNode::class)

   override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
      val (iteratorNode, parametersNode, statementNode, filterNode) = node as LoopNode
      val iterator = expressionEvaluator.evaluate(iteratorNode, environment).value

      environment.pushScope()
      val output = when (iterator.type) {
         INT -> evaluateForInt(iterator, parametersNode, statementNode, filterNode, environment)
         STRING -> evaluateForString(iterator, parametersNode, statementNode, filterNode, environment)
         LIST -> evaluateForList(iterator, parametersNode, statementNode, filterNode, environment)
         MAP -> evaluateForMap(iterator, parametersNode, statementNode, filterNode, environment)
         BOOL -> evaluateForBool(iteratorNode, parametersNode, statementNode, filterNode, environment)
         else -> throw contextEvaluationException(
            "Expected for-loop with int iterator or foreach-loop with string, list or map iterator or while-loop with bool iterator, found ${iterator.typeName}",
            iteratorNode.position,
            environment
         )
      }
      environment.popScope()

      return output
   }

   private fun evaluateForInt(
      iterator: Value,
      parametersNode: Node,
      statementNode: Node,
      filterNode: Node,
      environment: Environment
   ): EvaluatorOutput {
      return output { outputs ->
         var index = 0
         repeat(iterator.value as Int) {
            parameters(parametersNode, { id -> environment.setVariable(id, Value.int(index)) })

            if (filter(filterNode, environment)) {
               outputs.add(defaultEvaluator.evaluate(statementNode, environment))
            }

            index++
         }
      }
   }

   private fun evaluateForString(
      iterator: Value,
      parametersNode: Node,
      statementNode: Node,
      filterNode: Node,
      environment: Environment
   ): EvaluatorOutput {
      return output { outputs ->
         var index = 0
         (iterator.value as String).forEach {
            parameters(parametersNode,
               { id -> environment.setVariable(id, Value.string(it.toString())) },
               { id -> environment.setVariable(id, Value.int(index)) }
            )

            if (filter(filterNode, environment)) {
               outputs.add(defaultEvaluator.evaluate(statementNode, environment))
            }

            index++
         }
      }
   }

   private fun evaluateForList(
      iterator: Value,
      parametersNode: Node,
      statementNode: Node,
      filterNode: Node,
      environment: Environment
   ): EvaluatorOutput {
      return output { outputs ->
         var index = 0
         (iterator.value as List<Value>).forEach { item ->
            parameters(parametersNode,
               { id -> environment.setVariable(id, item) },
               { id -> environment.setVariable(id, Value.int(index)) }
            )

            if (filter(filterNode, environment)) {
               outputs.add(defaultEvaluator.evaluate(statementNode, environment))
            }

            index++
         }
      }
   }

   private fun evaluateForMap(
      iterator: Value,
      parametersNode: Node,
      statementNode: Node,
      filterNode: Node,
      environment: Environment
   ): EvaluatorOutput {
      return output { outputs ->
         var index = 0
         (iterator.value as Map<Value, Value>).forEach { (key, value) ->
            parameters(parametersNode,
               { id -> environment.setVariable(id, value) },
               { id -> environment.setVariable(id, key) },
               { id -> environment.setVariable(id, Value.int(index)) }
            )

            if (filter(filterNode, environment)) {
               outputs.add(defaultEvaluator.evaluate(statementNode, environment))
            }

            index++
         }
      }
   }

   private fun evaluateForBool(
      iteratorNode: Node,
      parametersNode: Node,
      statementNode: Node,
      filterNode: Node,
      environment: Environment
   ): EvaluatorOutput {
      if (parametersNode != Node.NONE) {
         throw contextEvaluationException(
            "Parameters are not supported in the while-loop",
            parametersNode.position,
            environment
         )
      }

      if (filterNode != Node.NONE) {
         throw contextEvaluationException(
            "Filter is not supported in the while-loop",
            filterNode.position,
            environment
         )
      }

      return output { outputs ->
         while (expressionEvaluator.evaluate(iteratorNode, environment).value.value as Boolean) {
            outputs.add(defaultEvaluator.evaluate(statementNode, environment))
         }
      }
   }

   private fun parameters(parametersNode: Node, vararg parameterConsumers: (String) -> Unit) {
      if (parametersNode.children.isNotEmpty()) {
         parameterConsumers.forEachIndexed { index, consumer ->
            if (index < parametersNode.children.size) {
               consumer((parametersNode.children[index] as IdentifierNode).token.rawValue)
            }
         }
      }
   }

   private fun filter(filterNode: Node, environment: Environment): Boolean {
      if (filterNode != Node.NONE) {
         val condition = expressionEvaluator.evaluate(filterNode, environment).value
         if (condition.type != BOOL) {
            throw contextEvaluationException(
               "Filter condition should be evaluated to bool type",
               filterNode.position,
               environment
            )
         }
         return condition.value as Boolean

      }
      return true
   }

   private infix fun output(evaluate: (MutableList<EvaluatorOutput>) -> Unit): EvaluatorOutput {
      val outputs = mutableListOf<EvaluatorOutput>()
      evaluate(outputs)

      return when {
         outputs.all { it.result == EvaluationResult.VALUE } -> EvaluatorOutput.value(Value.list(outputs.map { it.value }))

         // Disclaimer: It musn't be ok() because ExpressionEvaluator expects non-ok success output from each
         // of subevaluators.
         // Because loop needs to act as an expression its evaluator needs to meet ExpressionEvaluator requirements
         // and return value() with VOID type.
         outputs.none { it.result == EvaluationResult.FAILED } -> EvaluatorOutput.value(Value.void())
         else -> EvaluatorOutput.fail()
      }
   }
}