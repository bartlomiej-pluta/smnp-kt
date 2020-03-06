package dsl.ast.parser

import dsl.ast.model.node.ListNode
import dsl.token.model.enumeration.TokenType

class ListParser : AbstractIterableParser(
    TokenType.OPEN_SQUARE,
    SubexpressionParser(),
    TokenType.CLOSE_SQUARE,
    { list, tokenPosition ->
        ListNode(list, tokenPosition)
    })