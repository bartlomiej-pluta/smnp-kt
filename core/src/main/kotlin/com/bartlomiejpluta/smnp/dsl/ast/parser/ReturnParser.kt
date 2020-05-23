package com.bartlomiejpluta.smnp.dsl.ast.parser

import com.bartlomiejpluta.smnp.dsl.ast.model.entity.ParserOutput
import com.bartlomiejpluta.smnp.dsl.ast.model.node.ReturnNode
import com.bartlomiejpluta.smnp.dsl.token.model.entity.TokenList
import com.bartlomiejpluta.smnp.dsl.token.model.enumeration.TokenType

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