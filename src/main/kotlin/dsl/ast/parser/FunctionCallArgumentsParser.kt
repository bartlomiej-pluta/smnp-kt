package dsl.ast.parser

import dsl.ast.model.node.FunctionCallArgumentsNode
import dsl.token.model.enumeration.TokenType

class FunctionCallArgumentsParser :
    AbstractIterableParser(TokenType.OPEN_PAREN, ExpressionParser(), TokenType.CLOSE_PAREN, { list, tokenPosition ->
        FunctionCallArgumentsNode(list, tokenPosition)
    })