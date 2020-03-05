package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.ast.model.node.StringLiteralNode
import dsl.token.model.entity.TokenList
import dsl.token.model.enumeration.TokenType

class StringLiteralParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return terminal(TokenType.STRING) { StringLiteralNode(it) }.parse(input)
    }
}