package com.bartlomiejpluta.smnp.dsl.ast.parser

import com.bartlomiejpluta.smnp.dsl.ast.model.entity.ParserOutput
import com.bartlomiejpluta.smnp.dsl.ast.model.node.ProductOperatorNode
import com.bartlomiejpluta.smnp.dsl.token.model.entity.TokenList
import com.bartlomiejpluta.smnp.dsl.token.model.enumeration.TokenType

class TermParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return leftAssociativeOperator(
            FactorParser(),
            listOf(TokenType.ASTERISK, TokenType.SLASH),
            assert(FactorParser(), "expression")
        ) { lhs, operator, rhs ->
            ProductOperatorNode(lhs, operator, rhs)
        }.parse(input)
    }
}