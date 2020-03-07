package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.node.FunctionCallArgumentsNode
import io.smnp.dsl.token.model.enumeration.TokenType

class FunctionCallArgumentsParser :
    AbstractIterableParser(TokenType.OPEN_PAREN, ExpressionParser(), TokenType.CLOSE_PAREN, { list, tokenPosition ->
        FunctionCallArgumentsNode(list, tokenPosition)
    })