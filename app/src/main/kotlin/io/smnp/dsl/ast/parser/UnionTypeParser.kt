package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.node.UnionTypeNode
import io.smnp.dsl.token.model.enumeration.TokenType

class UnionTypeParser : AbstractIterableParser(
   TokenType.OPEN_ANGLE,
   SingleTypeParser(),
   TokenType.CLOSE_ANGLE,
   createNode = { list, tokenPosition ->
      UnionTypeNode(list, tokenPosition)
   })