package io.smnp.util.config

import io.smnp.error.CustomException
import io.smnp.type.enumeration.DataType
import io.smnp.type.matcher.Matcher
import io.smnp.type.model.Value

class ConfigMapSchema {
   private data class Parameter(val matcher: Matcher, val required: Boolean, val default: Value)

   private val parameters = mutableMapOf<String, Parameter>()

   fun required(name: String, matcher: Matcher): ConfigMapSchema {
      parameters[name] = Parameter(matcher, true, Value.void())
      return this
   }

   fun optional(name: String, matcher: Matcher, default: Value = Value.void()): ConfigMapSchema {
      parameters[name] = Parameter(matcher, false, default)
      return this
   }

   fun parse(config: Value): ConfigMap {
      val configMap = config.value as Map<Value, Value>

      return ConfigMap(parameters.mapNotNull { (name, parameter) ->
         val value = configMap[Value.string(name)]
            ?: if (parameter.required) throw CustomException("The '$name' parameter of ${parameter.matcher} is required")
            else parameter.default

         if (!parameter.matcher.match(value) && value.type != DataType.VOID) {
            throw CustomException("Invalid parameter type: '$name' is supposed to be of ${parameter.matcher} type")
         }

         if (value.type == DataType.VOID) null
         else Value.string(name) to value
      }.toMap())
   }
}