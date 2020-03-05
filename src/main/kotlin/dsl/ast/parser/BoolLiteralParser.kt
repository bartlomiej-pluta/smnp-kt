package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.ast.model.node.BoolLiteralNode
import dsl.token.model.entity.TokenList
import dsl.token.model.enumeration.TokenType

class BoolLiteralParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return terminal(TokenType.BOOL) { BoolLiteralNode(it) }.parse(input)
    }
}