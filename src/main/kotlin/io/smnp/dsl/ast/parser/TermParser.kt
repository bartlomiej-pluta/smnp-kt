package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.entity.ParserOutput
import io.smnp.dsl.ast.model.node.ProductOperatorNode
import io.smnp.dsl.token.model.entity.TokenList
import io.smnp.dsl.token.model.enumeration.TokenType

class TermParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return leftAssociativeOperator(
            FactorParser(),
            listOf(TokenType.ASTERISK, TokenType.SLASH),
            FactorParser()
        ) { lhs, operator, rhs ->
            ProductOperatorNode(lhs, operator, rhs)
        }.parse(input)
    }
}