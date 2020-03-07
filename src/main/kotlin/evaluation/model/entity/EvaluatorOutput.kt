package evaluation.model.entity

import data.model.Value
import evaluation.model.enumeration.EvaluationResult

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