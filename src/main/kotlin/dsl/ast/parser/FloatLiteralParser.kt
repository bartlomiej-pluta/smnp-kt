package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.ast.model.node.FloatLiteralNode
import dsl.token.model.entity.TokenList
import dsl.token.model.enumeration.TokenType

class FloatLiteralParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return terminal(TokenType.FLOAT) { FloatLiteralNode(it) }.parse(input)
    }
}