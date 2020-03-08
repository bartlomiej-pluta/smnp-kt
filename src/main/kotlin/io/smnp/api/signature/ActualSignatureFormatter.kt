package io.smnp.api.signature

import io.smnp.api.enumeration.DataType
import io.smnp.api.model.Value

object ActualSignatureFormatter {
    fun format(arguments: Array<out Value>, parentheses: Boolean = true): String {
        val output = mutableListOf<String>()
        for(argument in arguments) {
            output.add(when(argument.type) {
                DataType.LIST -> listTypes(argument.value as List<Value>)
                DataType.MAP -> mapTypes(argument.value as Map<Value, Value>)
                else -> argument.type.name.toLowerCase()
            })
        }

        return if(parentheses) "(${output.joinToString()})" else output.joinToString()
    }

    private fun listTypes(list: List<Value>, output: MutableList<String> = mutableListOf()): String {
        for (item in list) {
            output.add(when (item.type) {
                DataType.LIST -> listTypes(item.value as List<Value>)
                DataType.MAP -> mapTypes(item.value as Map<Value, Value>)
                else -> item.type.name.toLowerCase()
            })
        }

        return "list<${output.toSet().joinToString()}>"
    }

    private fun mapTypes(map: Map<Value, Value>, output: MutableMap<Value, String> = mutableMapOf()): String {
        for ((k, v) in map) {
            output[k] = when (v.type) {
                DataType.LIST -> listTypes(v.value as List<Value>)
                DataType.MAP -> mapTypes(v.value as Map<Value, Value>)
                else -> v.type.name.toLowerCase()
            }
        }

        return "map<${output.keys.toSet().joinToString()}><${output.values.toSet().joinToString()}}>"
    }
}