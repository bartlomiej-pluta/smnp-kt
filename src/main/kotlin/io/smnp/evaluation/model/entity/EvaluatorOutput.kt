package io.smnp.evaluation.model.entity

import io.smnp.data.model.Value
import io.smnp.evaluation.model.enumeration.EvaluationResult

class EvaluatorOutput private constructor(val result: EvaluationResult, val value: Value?) {
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