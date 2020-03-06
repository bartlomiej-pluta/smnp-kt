package dsl.ast.parser

import dsl.ast.model.node.LoopParametersNode
import dsl.token.model.enumeration.TokenType

class LoopParametersParser : AbstractIterableParser(TokenType.OPEN_PAREN, IdentifierParser(), TokenType.CLOSE_PAREN, {
    list, tokenPosition -> LoopParametersNode(list, tokenPosition)
})