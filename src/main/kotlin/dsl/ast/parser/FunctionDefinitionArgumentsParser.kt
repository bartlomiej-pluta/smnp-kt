package dsl.ast.parser

import dsl.ast.model.node.FunctionDefinitionArgumentsNode
import dsl.token.model.enumeration.TokenType

class FunctionDefinitionArgumentsParser : AbstractIterableParser(
    TokenType.OPEN_PAREN,
    FunctionDefinitionArgumentParser(),
    TokenType.CLOSE_PAREN,
    { list, tokenPosition ->
        FunctionDefinitionArgumentsNode(list, tokenPosition)
    })