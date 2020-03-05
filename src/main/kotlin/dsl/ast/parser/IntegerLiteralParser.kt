package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.ast.model.node.IntegerLiteralNode
import dsl.token.model.entity.TokenList
import dsl.token.model.enumeration.TokenType

class IntegerLiteralParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return terminal(TokenType.INTEGER) { IntegerLiteralNode(it) }.parse(input)
    }
}