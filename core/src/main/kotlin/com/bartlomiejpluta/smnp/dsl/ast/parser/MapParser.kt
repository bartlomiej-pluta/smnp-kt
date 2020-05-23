package com.bartlomiejpluta.smnp.dsl.ast.parser

import com.bartlomiejpluta.smnp.dsl.ast.model.node.MapNode
import com.bartlomiejpluta.smnp.dsl.token.model.enumeration.TokenType

class MapParser :
   AbstractIterableParser(
      TokenType.OPEN_CURLY,
      MapEntryParser(),
      TokenType.CLOSE_CURLY,
      createNode = { list, tokenPosition ->
         MapNode(list, tokenPosition)
      })