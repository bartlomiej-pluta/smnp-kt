package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.ast.model.node.NotOperatorNode
import dsl.ast.model.node.PowerOperatorNode
import dsl.token.model.entity.TokenList
import dsl.token.model.enumeration.TokenType

class FactorParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        val factorParser = leftAssociativeOperator(
            UnitParser(),
            listOf(TokenType.DOUBLE_ASTERISK),
            UnitParser()
        ) { lhs, operator, rhs ->
            PowerOperatorNode(lhs, operator, rhs)
        }

        val notOperatorParser = allOf(listOf(
            terminal(TokenType.NOT),
            factorParser
        )) {
            NotOperatorNode(it[0], it[1])
        }

        return oneOf(listOf(
            notOperatorParser,
            factorParser
        )).parse(input)
    }
}