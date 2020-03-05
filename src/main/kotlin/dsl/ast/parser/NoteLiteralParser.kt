package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.ast.model.node.NoteLiteralNode
import dsl.token.model.entity.TokenList
import dsl.token.model.enumeration.TokenType

class NoteLiteralParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return terminal(TokenType.NOTE) { NoteLiteralNode(it) }.parse(input)
    }
}