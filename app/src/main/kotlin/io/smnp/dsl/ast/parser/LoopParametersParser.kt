package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.node.LoopParametersNode
import io.smnp.dsl.token.model.enumeration.TokenType

class LoopParametersParser : AbstractIterableParser(
    TokenType.OPEN_PAREN,
    assert(SimpleIdentifierParser(), "identifier"),
    TokenType.CLOSE_PAREN,
    { list, tokenPosition ->
        LoopParametersNode(list, tokenPosition)
    })