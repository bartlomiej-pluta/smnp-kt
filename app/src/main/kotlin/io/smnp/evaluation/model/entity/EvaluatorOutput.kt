package io.smnp.evaluation.model.entity

import io.smnp.evaluation.model.enumeration.EvaluationResult
import io.smnp.type.model.Value

class EvaluatorOutput private constructor(val result: EvaluationResult, val value: Value?) {

    override fun toString(): String {
        return "$result(${value ?: ""})"
    }

    companion object {
        fun ok(): EvaluatorOutput {
            return EvaluatorOutput(EvaluationResult.OK, null)
        }

        fun value(value: Value): EvaluatorOutput {
            return EvaluatorOutput(EvaluationResult.VALUE, value)
        }

        fun fail(): EvaluatorOutput {
            return EvaluatorOutput(EvaluationResult.FAILED, null)
        }
    }
}