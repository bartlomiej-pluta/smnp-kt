package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.ast.model.node.ThrowNode
import dsl.token.model.entity.TokenList
import dsl.token.model.enumeration.TokenType

class ThrowParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return allOf(listOf(
            terminal(TokenType.THROW),
            optional(ExpressionParser())
        )) {
            ThrowNode(it[1])
        }.parse(input)
    }
}