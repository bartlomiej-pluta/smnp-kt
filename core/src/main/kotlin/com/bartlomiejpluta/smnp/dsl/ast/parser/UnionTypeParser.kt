package com.bartlomiejpluta.smnp.dsl.ast.parser

import com.bartlomiejpluta.smnp.dsl.ast.model.node.UnionTypeNode
import com.bartlomiejpluta.smnp.dsl.token.model.enumeration.TokenType

class UnionTypeParser : AbstractIterableParser(
   TokenType.OPEN_ANGLE,
   SingleTypeParser(),
   TokenType.CLOSE_ANGLE,
   createNode = { list, tokenPosition ->
      UnionTypeNode(list, tokenPosition)
   })