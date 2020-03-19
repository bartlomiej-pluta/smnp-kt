package io.smnp.evaluation.evaluator

import io.smnp.data.entity.Note
import io.smnp.dsl.ast.model.node.*
import io.smnp.environment.Environment
import io.smnp.error.EnvironmentException
import io.smnp.error.EvaluationException
import io.smnp.error.PositionException
import io.smnp.evaluation.model.entity.EvaluatorOutput
import io.smnp.math.Fraction
import io.smnp.type.enumeration.DataType
import io.smnp.type.model.Value

class StaffEvaluator : Evaluator() {
   private val evaluator = ExpressionEvaluator()

   override fun supportedNodes() = listOf(StaffNode::class)

   override fun tryToEvaluate(node: Node, environment: Environment): EvaluatorOutput {
      val measures = (node as StaffNode).items
      var currentSignature: Fraction? = null

      val list = measures.map { it as MeasureNode }.flatMap { measure ->
         val evaluatedMeasure = measure.items.mapIndexedNotNull { index, it ->
            when (it) {
               is TimeSignatureNode -> {
                  assertTimeSignaturePosition(index, environment, it)
                  currentSignature = Fraction(
                     (it.numerator as IntegerLiteralNode).token.value as Int,
                     (it.denominator as IntegerLiteralNode).token.value as Int
                  )
                  null
               }
               else -> evaluator.evaluate(it, environment).value
            }
         }

         assertTimeSignature(evaluatedMeasure, currentSignature, environment, measure)

         evaluatedMeasure
      }

      return EvaluatorOutput.value(Value.list(list))
   }

   private fun assertTimeSignaturePosition(
      index: Int,
      environment: Environment,
      it: Node
   ) {
      if (index != 0) {
         throw PositionException(
            EnvironmentException(
               EvaluationException("Time signature can be placed only at the beginning of measure"),
               environment
            ), it.position
         )
      }
   }

   private fun assertTimeSignature(
      evaluatedMeasure: List<Value>,
      currentSignature: Fraction?,
      environment: Environment,
      measure: MeasureNode
   ) {
      val evaluatedSignature = calculateTimeSignature(evaluatedMeasure)

      currentSignature?.let {
         if (evaluatedSignature != it) {
            val simplified = evaluatedSignature.simplified
            throw PositionException(
               EnvironmentException(
                  EvaluationException("Invalid time signature: expected ${it.numerator}/${it.denominator}, got ${simplified.numerator}/${simplified.denominator}"),
                  environment
               ), measure.position
            )
         }
      }
   }

   private fun calculateTimeSignature(values: List<Value>): Fraction {
      return values.fold(Fraction(0, 1)) { acc, value ->
         acc + when (value.type) {
            DataType.NOTE -> (value.value as Note).duration
            DataType.INT -> Fraction(1, value.value as Int)
            DataType.LIST -> calculateTimeSignature(value.value as List<Value>)
            else -> Fraction(0, 1)
         }
      }
   }

}