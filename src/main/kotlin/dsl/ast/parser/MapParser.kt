package dsl.ast.parser

import dsl.ast.model.node.MapNode
import dsl.token.model.enumeration.TokenType

class MapParser :
    AbstractIterableParser(TokenType.OPEN_CURLY, MapEntryParser(), TokenType.CLOSE_CURLY, { list, tokenPosition ->
        MapNode(list, tokenPosition)
    })