package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.entity.ParserOutput
import io.smnp.dsl.ast.model.node.AccessOperatorNode
import io.smnp.dsl.ast.model.node.MinusOperatorNode
import io.smnp.dsl.token.model.entity.TokenList
import io.smnp.dsl.token.model.enumeration.TokenType

class UnitParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        val minusOperatorParser = allOf(
            terminal(TokenType.MINUS),
            assert(AtomParser(), "atom")
        ) {
            MinusOperatorNode(it[0], it[1])
        }

        val atom2 = oneOf(
            minusOperatorParser,
            AtomParser()
        )

        return leftAssociativeOperator(atom2, listOf(TokenType.DOT), assert(atom2, "atom")) { lhs, operator, rhs ->
            AccessOperatorNode(lhs, operator, rhs)
        }.parse(input)
    }
}