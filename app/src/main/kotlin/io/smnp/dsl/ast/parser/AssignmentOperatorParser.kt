package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.entity.ParserOutput
import io.smnp.dsl.ast.model.node.AssignmentOperatorNode
import io.smnp.dsl.token.model.entity.TokenList
import io.smnp.dsl.token.model.enumeration.TokenType

class AssignmentOperatorParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return allOf(
            SimpleIdentifierParser(),
            terminal(TokenType.ASSIGN),
            assert(ExpressionParser(), "expression")
        ) {
            AssignmentOperatorNode(it[0], it[1], it[2])
        }.parse(input)
    }
}