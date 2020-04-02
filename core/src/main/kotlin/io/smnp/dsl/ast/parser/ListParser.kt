package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.node.ListNode
import io.smnp.dsl.token.model.enumeration.TokenType

class ListParser : AbstractIterableParser(
   TokenType.OPEN_SQUARE,
   assert(ExpressionParser(), "expression"),
   TokenType.CLOSE_SQUARE,
   createNode = { list, tokenPosition ->
      ListNode(list, tokenPosition)
   })