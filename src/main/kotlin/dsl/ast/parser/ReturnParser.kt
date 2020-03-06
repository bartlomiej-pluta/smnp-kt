package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.ast.model.node.ReturnNode
import dsl.token.model.entity.TokenList
import dsl.token.model.enumeration.TokenType

class ReturnParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return allOf(
            terminal(TokenType.RETURN),
            optional(ExpressionParser())
        ) {
            ReturnNode(it[1])
        }.parse(input)
    }
}