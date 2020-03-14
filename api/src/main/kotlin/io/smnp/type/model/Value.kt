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

   override fun toString(): String {
      return "$type($value)"
   }

   companion object {
      fun int(value: Int): Value {
         return Value(DataType.INT, value)
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
               Pair("length", int(value.length))
            )
         )
      }

      fun list(value: List<Value>): Value {
         return Value(
            DataType.LIST, value, hashMapOf(
               Pair("size", int(value.size))
            )
         )
      }

      fun map(value: Map<Value, Value>): Value {
         return Value(
            DataType.MAP, value, hashMapOf(
               Pair("size", int(value.size)),
               Pair("keys", list(value.keys.toList())),
               Pair("values", list(value.values.toList()))
            )
         )
      }

      fun note(value: Note): Value {
         return Value(
            DataType.NOTE, value, hashMapOf(
               Pair("pitch", string(value.pitch.toString())),
               Pair("octave", int(value.octave)),
               Pair("duration", int(value.duration)),
               Pair("dot", bool(value.dot))
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