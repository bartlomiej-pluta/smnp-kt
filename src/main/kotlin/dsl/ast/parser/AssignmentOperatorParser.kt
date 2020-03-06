package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.ast.model.node.AssignmentOperatorNode
import dsl.token.model.entity.TokenList
import dsl.token.model.enumeration.TokenType

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