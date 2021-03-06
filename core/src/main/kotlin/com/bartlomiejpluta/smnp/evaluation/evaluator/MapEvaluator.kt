package com.bartlomiejpluta.smnp.evaluation.evaluator

import com.bartlomiejpluta.smnp.dsl.ast.model.node.IdentifierNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.MapEntryNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.MapNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.Node
import com.bartlomiejpluta.smnp.environment.Environment
import com.bartlomiejpluta.smnp.evaluation.model.entity.EvaluatorOutput
import com.bartlomiejpluta.smnp.evaluation.util.ContextExceptionFactory.contextEvaluationException
import com.bartlomiejpluta.smnp.type.enumeration.DataType.*
import com.bartlomiejpluta.smnp.type.model.Value

class MapEvaluator : Evaluator() {
   private val evaluator = ExpressionEvaluator()

   override fun supportedNodes() = listOf(MapNode::class)

   override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
      val value = (node as MapNode).items
         .map { it as MapEntryNode }
         .map { getKey(it.key, environment) to evaluator.evaluate(it.value, environment).value }
         .toMap()

      return EvaluatorOutput.value(Value.map(value))
   }

   private fun getKey(keyNode: Node, environment: Environment): Value {
      val key = when (keyNode) {
         is IdentifierNode -> Value.string(keyNode.token.rawValue)
         else -> evaluator.evaluate(keyNode, environment).value
      }

      if (key.type !in listOf(BOOL, INT, NOTE, STRING)) {
         throw contextEvaluationException("Invalid map key's type ${key.typeName}", keyNode.position, environment)
      }

      return key
   }
}