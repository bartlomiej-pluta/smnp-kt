package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.node.MapNode
import io.smnp.dsl.token.model.enumeration.TokenType

class MapParser :
   AbstractIterableParser(
      TokenType.OPEN_CURLY,
      MapEntryParser(),
      TokenType.CLOSE_CURLY,
      createNode = { list, tokenPosition ->
         MapNode(list, tokenPosition)
      })