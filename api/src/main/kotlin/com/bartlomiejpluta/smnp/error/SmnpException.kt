package com.bartlomiejpluta.smnp.error

abstract class SmnpException(val friendlyName: String, message: String? = null, val exception: SmnpException? = null) : Exception(message) {
   val exceptionChain: List<SmnpException>
   get() {
      if(exception == null) {
         return emptyList()
      }

      return listOf(this, *exception.exceptionChain.toTypedArray())
   }
}