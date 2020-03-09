package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.node.TypeSpecifierNode
import io.smnp.dsl.token.model.enumeration.TokenType

class TypeSpecifierParser :
    AbstractIterableParser(TokenType.OPEN_ANGLE, TypeParser(), TokenType.CLOSE_ANGLE, { list, tokenPosition ->
        TypeSpecifierNode(list, tokenPosition)
    })