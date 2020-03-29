package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.node.TypeSpecifierNode
import io.smnp.dsl.token.model.enumeration.TokenType

class TypeSpecifierParser :
   AbstractIterableParser(
      TokenType.OPEN_ANGLE,
      SingleTypeParser(),
      TokenType.CLOSE_ANGLE,
      createNode = { list, tokenPosition ->
         TypeSpecifierNode(list, tokenPosition)
      })