package com.bartlomiejpluta.smnp.dsl.ast.parser

import com.bartlomiejpluta.smnp.dsl.ast.model.node.LoopParametersNode
import com.bartlomiejpluta.smnp.dsl.token.model.enumeration.TokenType

class LoopParametersParser : AbstractIterableParser(
   TokenType.OPEN_PAREN,
   assert(SimpleIdentifierParser(), "identifier"),
   TokenType.CLOSE_PAREN,
   createNode = { list, tokenPosition ->
      LoopParametersNode(list, tokenPosition)
   })