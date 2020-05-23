package com.bartlomiejpluta.smnp.callable.signature

import com.bartlomiejpluta.smnp.type.enumeration.DataType
import com.bartlomiejpluta.smnp.type.model.Value

object ActualSignatureFormatter {
    fun format(value: Value): String {
        return format(arrayOf(value), false)
    }

    fun format(arguments: Array<out Value>, parentheses: Boolean = true): String {
        val output = mutableListOf<String>()
        for(argument in arguments) {
            output.add(when(argument.type) {
                DataType.LIST -> listTypes(
                    argument.value as List<Value>
                )
                DataType.MAP -> mapTypes(
                    argument.value as Map<Value, Value>
                )
                else -> argument.typeName
            })
        }

        return if(parentheses) "(${output.joinToString()})" else output.joinToString()
    }

    private fun listTypes(list: List<Value>, output: MutableList<String> = mutableListOf()): String {
        for (item in list) {
            output.add(when (item.type) {
                DataType.LIST -> listTypes(
                    item.value as List<Value>
                )
                DataType.MAP -> mapTypes(
                    item.value as Map<Value, Value>
                )
                else -> item.typeName
            })
        }

        return "list<${output.toSet().joinToString()}>"
    }

    private fun mapTypes(map: Map<Value, Value>, output: MutableMap<String, String> = mutableMapOf()): String {
        for ((k, v) in map) {
            output[k.typeName] = when (v.type) {
                DataType.LIST -> listTypes(
                    v.value as List<Value>
                )
                DataType.MAP -> mapTypes(
                    v.value as Map<Value, Value>
                )
                else -> v.typeName
            }
        }

        return "map<${output.keys.toSet().joinToString()}><${output.values.toSet().joinToString()}>"
    }
}