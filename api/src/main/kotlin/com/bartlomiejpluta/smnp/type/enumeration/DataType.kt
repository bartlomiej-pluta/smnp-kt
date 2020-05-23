package com.bartlomiejpluta.smnp.type.enumeration

import com.bartlomiejpluta.smnp.data.entity.Note
import com.bartlomiejpluta.smnp.type.model.Value
import kotlin.reflect.KClass

enum class DataType(private val kotlinType: KClass<out Any>, val stringifier: (Any) -> String) {
    INT(Int::class, { it.toString() }),
    FLOAT(Float::class, { it.toString() }),
    STRING(String::class, { it.toString() }),
    LIST(List::class, { "[" + (it as List<Value>).joinToString { v -> v.stringify() } + "]" }),
    MAP(Map::class, { "{" + (it as Map<Value, Value>).map { (k, v) -> "${k.stringify()} -> ${v.stringify()}" }.joinToString() + "}" }),
    NOTE(Note::class, { it.toString() }),
    BOOL(Boolean::class, { it.toString() }),
    TYPE(DataType::class, { it.toString() }),
    VOID(Unit::class, { "void" });

    fun isInstance(value: Any) = kotlinType.isInstance(value)

    fun isNumeric() = this == INT || this == FLOAT

    override fun toString() = super.toString().toLowerCase()
}