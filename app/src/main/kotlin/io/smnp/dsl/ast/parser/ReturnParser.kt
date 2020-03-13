package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.entity.ParserOutput
import io.smnp.dsl.ast.model.node.ReturnNode
import io.smnp.dsl.token.model.entity.TokenList
import io.smnp.dsl.token.model.enumeration.TokenType

class ReturnParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return allOf(
            terminal(TokenType.RETURN),
            optional(assert(ExpressionParser(), "expression"))
        ) { (_, value) ->
            ReturnNode(value)
        }.parse(input)
    }
}