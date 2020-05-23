package com.bartlomiejpluta.smnp.dsl.ast.parser

import com.bartlomiejpluta.smnp.dsl.ast.model.node.ListNode
import com.bartlomiejpluta.smnp.dsl.token.model.enumeration.TokenType

class ListParser : AbstractIterableParser(
   TokenType.OPEN_SQUARE,
   assert(ExpressionParser(), "expression"),
   TokenType.CLOSE_SQUARE,
   createNode = { list, tokenPosition ->
      ListNode(list, tokenPosition)
   })