package com.bartlomiejpluta.smnp.evaluation.model.entity

import com.bartlomiejpluta.smnp.evaluation.model.enumeration.EvaluationResult
import com.bartlomiejpluta.smnp.type.model.Value

class EvaluatorOutput private constructor(val result: EvaluationResult, val value: Value) {

    override fun toString(): String {
        return "$result(${if(result == EvaluationResult.VALUE) value.toString() else ""})"
    }

    companion object {
        fun ok(): EvaluatorOutput {
            return EvaluatorOutput(EvaluationResult.OK, Value.void())
        }

        fun value(value: Value): EvaluatorOutput {
            return EvaluatorOutput(EvaluationResult.VALUE, value)
        }

        fun fail(): EvaluatorOutput {
            return EvaluatorOutput(EvaluationResult.FAILED, Value.void())
        }
    }
}