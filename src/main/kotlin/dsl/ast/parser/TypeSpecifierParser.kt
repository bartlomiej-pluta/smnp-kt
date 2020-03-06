package dsl.ast.parser

import dsl.ast.model.node.TypeSpecifierNode
import dsl.token.model.enumeration.TokenType

class TypeSpecifierParser : AbstractIterableParser(TokenType.OPEN_ANGLE, TypeParser(), TokenType.CLOSE_ANGLE, {
    list, tokenPosition -> TypeSpecifierNode(list, tokenPosition)
})