package io.smnp.evaluation.util

import io.smnp.data.enumeration.DataType
import io.smnp.data.model.Value

object NumberUnification {
    fun unify(vararg numbers: Value, intConsumer: (List<Int>) -> Value, floatConsumer: (List<Float>) -> Value): Value {
        if(numbers.any { it.type == DataType.FLOAT }) {
            return floatConsumer(numbers.map { (it.value as Number).toFloat() })
        }

        return intConsumer(numbers.map { (it.value as Number).toInt() })
    }
}