package io.smnp.evaluation.util

import io.smnp.api.enumeration.DataType
import io.smnp.api.model.Value

object NumberUnification {
    fun unify(vararg numbers: Value, int: (List<Int>) -> Value, float: (List<Float>) -> Value): Value {
        if(numbers.any { it.type == DataType.FLOAT }) {
            return float(numbers.map { (it.value as Number).toFloat() })
        }

        return int(numbers.map { (it.value as Number).toInt() })
    }
}