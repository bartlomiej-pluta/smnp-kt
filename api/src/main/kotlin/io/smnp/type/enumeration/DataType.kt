package io.smnp.type.enumeration

import io.smnp.data.entity.Note
import io.smnp.type.model.Value
import kotlin.reflect.KClass

enum class DataType(val kotlinType: KClass<out Any>, val stringifier: (Any) -> String) {
    INT(Int::class, { it.toString() }),
    FLOAT(Float::class, { it.toString() }),
    STRING(String::class, { it.toString() }),
    LIST(List::class, { "[" + (it as List<Value>).joinToString { v -> v.stringify() } + "]" }),
    MAP(Map::class, { "{" + (it as Map<Value, Value>).map { (k, v) -> "${k.stringify()} -> ${v.stringify()}" }.joinToString() + "}" }),
    NOTE(Note::class, { it.toString() }),
    BOOL(Boolean::class, { it.toString() }),
    TYPE(DataType::class, { it.toString() }),
    VOID(Unit::class, { "void" });

    fun isInstance(value: Any): Boolean {
        return kotlinType.isInstance(value)
    }

    fun isNumeric(): Boolean {
        return this == INT || this == FLOAT
    }

    override fun toString(): String {
        return super.toString().toLowerCase()
    }
}