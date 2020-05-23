package com.bartlomiejpluta.smnp.dsl.token.model.entity

data class TokenPosition(val source: String, val line: Int, val beginCol: Int, val endCol: Int) {
   companion object {
      val NONE = TokenPosition("<NONE>", -1, -1, -1)
   }

   override fun toString() = "${line + 1}:${beginCol + 1}"

   val fullString = "Source: $source\nPosition: line ${line + 1}, column ${beginCol + 1}"
}