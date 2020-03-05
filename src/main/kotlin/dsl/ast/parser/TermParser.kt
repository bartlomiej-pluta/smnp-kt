package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.ast.model.node.ProductOperatorNode
import dsl.token.model.entity.TokenList
import dsl.token.model.enumeration.TokenType

class TermParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return leftAssociativeOperator(
            FactorParser(),
            listOf(TokenType.ASTERISK, TokenType.SLASH),
            FactorParser()
        ) {
            lhs, operator, rhs -> ProductOperatorNode(lhs, operator, rhs)
        }.parse(input)
    }
}