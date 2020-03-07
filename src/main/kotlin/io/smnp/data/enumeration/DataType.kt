package io.smnp.data.enumeration

import io.smnp.data.entity.Note
import kotlin.reflect.KClass

enum class DataType(val kotlinType: KClass<out Any>?) {
    INT(Int::class),
    FLOAT(Float::class),
    STRING(String::class),
    LIST(List::class),
    MAP(Map::class),
    NOTE(Note::class),
    BOOL(Boolean::class),
    TYPE(DataType::class),
    VOID(null);

    fun isInstance(value: Any?): Boolean {
        if(kotlinType == null) {
            return value == null
        }

        return kotlinType.isInstance(value)
    }

    override fun toString(): String {
        return super.toString().toLowerCase()
    }

    companion object {
        fun inference(value: Any?): DataType {
            if (value == null) {
                return VOID
            }

            return values()
                .filter { it.kotlinType != null }
                .find { it.kotlinType!!.isInstance(value) }
                ?: throw RuntimeException("Cannot inference type for '$value'")
        }
    }
}