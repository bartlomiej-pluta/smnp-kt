package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.ast.model.node.AccessOperatorNode
import dsl.ast.model.node.MinusOperatorNode
import dsl.token.model.entity.TokenList
import dsl.token.model.enumeration.TokenType

class UnitParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        val minusOperatorParser = allOf(
            listOf(
                terminal(TokenType.MINUS),
                assert(AtomParser(), "atom")
            )
        ) {
            MinusOperatorNode(it[0], it[1])
        }

        val atom2 = oneOf(listOf(
            minusOperatorParser,
            AtomParser()
        ))

        return leftAssociativeOperator(atom2, listOf(TokenType.DOT), assert(atom2, "atom")) {
            lhs, operator, rhs -> AccessOperatorNode(lhs, operator, rhs)
        }.parse(input)
    }
}