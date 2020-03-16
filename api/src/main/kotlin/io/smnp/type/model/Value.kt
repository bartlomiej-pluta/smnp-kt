package io.smnp.type.model

import io.smnp.data.entity.Note
import io.smnp.error.ShouldNeverReachThisLineException
import io.smnp.type.enumeration.DataType

data class Value(val type: DataType, val value: Any, val properties: Map<String, Value> = emptyMap()) {
   init {
      if (!type.isInstance(value)) {
         throw RuntimeException("'$value' is not of type $type")
      }
   }

   fun stringify() = type.stringifier(value)

   val typeName: String
      get() = type.toString()

   fun unwrapCollections(): Any {
      return when (type) {
         DataType.LIST -> (value as List<Value>).map { it.unwrapCollections() }
         DataType.MAP -> (value as Map<Value, Value>).map { (k, v) -> k.unwrapCollections() to v.unwrapCollections() }.toMap()
         else -> this
      }
   }

   fun unwrap(): Any {
      return when (type) {
         DataType.LIST -> (value as List<Value>).map { it.unwrap() }
         DataType.MAP -> (value as Map<Value, Value>).map { (k, v) -> k.unwrap() to v.unwrap() }.toMap()
         else -> value
      }
   }

   override fun toString(): String {
      return "$type($value)"
   }

   companion object {
      fun int(value: Int): Value {
         return Value(DataType.INT, value)
      }

      fun wrap(obj: Any): Value {
         return when (obj) {
            is Unit -> void()
            is Int -> int(obj)
            is Float -> float(obj)
            is Boolean -> bool(obj)
            is String -> string(obj)
            is Note -> note(obj)
            is List<*> -> list((obj as List<Any>).map { wrap(it) })
            is Map<*, *> -> map((obj as Map<Any, Any>).map { (k, v) -> wrap(k) to wrap(v) }.toMap())
            else -> throw ShouldNeverReachThisLineException()
         }
      }

      fun float(value: Float): Value {
         return Value(
            DataType.FLOAT,
            value
         )
      }

      fun numeric(value: Number): Value {
         return when (value::class) {
            Int::class -> int(value.toInt())
            Float::class -> float(value.toFloat())
            else -> throw ShouldNeverReachThisLineException()
         }
      }

      fun string(value: String): Value {
         return Value(
            DataType.STRING, value, hashMapOf(
               "length" to int(value.length)
            )
         )
      }

      fun list(value: List<Value>): Value {
         return Value(
            DataType.LIST, value, hashMapOf(
               "size" to int(value.size)
            )
         )
      }

      fun map(value: Map<Value, Value>): Value {
         return Value(
            DataType.MAP, value, hashMapOf(
               "size" to int(value.size),
               "keys" to list(value.keys.toList()),
               "values" to list(value.values.toList())
            )
         )
      }

      fun note(value: Note): Value {
         return Value(
            DataType.NOTE, value, hashMapOf(
               "pitch" to string(value.pitch.toString()),
               "octave" to int(value.octave),
               "duration" to map(
                  mapOf(
                     string("numerator") to int(value.duration.numerator),
                     string("denominator") to int(value.duration.denominator)
                  )
               )
            )
         )
      }

      fun bool(value: Boolean): Value {
         return Value(DataType.BOOL, value)
      }

      fun type(value: DataType): Value {
         return Value(DataType.TYPE, value)
      }

      fun void(): Value {
         return Value(DataType.VOID, Unit)
      }
   }
}