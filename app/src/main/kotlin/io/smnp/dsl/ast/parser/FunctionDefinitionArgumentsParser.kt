package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.node.FunctionDefinitionArgumentsNode
import io.smnp.dsl.token.model.enumeration.TokenType

class FunctionDefinitionArgumentsParser : AbstractIterableParser(
    TokenType.OPEN_PAREN,
    FunctionDefinitionArgumentParser(),
    TokenType.CLOSE_PAREN,
    { list, tokenPosition ->
        FunctionDefinitionArgumentsNode(list, tokenPosition)
    })