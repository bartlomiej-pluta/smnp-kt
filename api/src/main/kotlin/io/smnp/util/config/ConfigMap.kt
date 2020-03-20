package io.smnp.util.config

import io.smnp.error.ShouldNeverReachThisLineException
import io.smnp.type.model.Value

class ConfigMap(private val map: Map<Value, Value>) {
   private val raw by lazy { map.map { (key, value) -> key.unwrap() to value }.toMap() as Map<String, Value> }

   operator fun get(key: String): Value {
      return raw[key] ?: throw ShouldNeverReachThisLineException()
   }

   fun <T> getUnwrappedOrDefault(key: String, default: T): T {
      return raw[key]?.unwrap() as T ?: default
   }

   fun containsKey(key: String): Boolean {
      return raw.containsKey(key)
   }

   companion object {
      val EMPTY = ConfigMap(emptyMap())
   }
}