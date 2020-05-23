package com.bartlomiejpluta.smnp.dsl.ast.parser

import com.bartlomiejpluta.smnp.dsl.ast.model.node.TypeSpecifierNode
import com.bartlomiejpluta.smnp.dsl.token.model.enumeration.TokenType

class TypeSpecifierParser :
   AbstractIterableParser(
      TokenType.OPEN_ANGLE,
      SingleTypeParser(),
      TokenType.CLOSE_ANGLE,
      createNode = { list, tokenPosition ->
         TypeSpecifierNode(list, tokenPosition)
      })