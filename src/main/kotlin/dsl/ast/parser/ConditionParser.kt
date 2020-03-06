package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.ast.model.node.ConditionNode
import dsl.ast.model.node.Node
import dsl.token.model.entity.TokenList
import dsl.token.model.enumeration.TokenType

class ConditionParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        val ifStatementParser = allOf(listOf(
            terminal(TokenType.IF),
            terminal(TokenType.OPEN_PAREN),
            SubexpressionParser(),
            terminal(TokenType.CLOSE_PAREN),
            StatementParser()
        )) {
            ConditionNode(it[0], it[2], it[4], Node.NONE, Node.NONE)
        }

        val ifElseStatementParser = allOf(listOf(
            terminal(TokenType.IF),
            terminal(TokenType.OPEN_PAREN),
            SubexpressionParser(),
            terminal(TokenType.CLOSE_PAREN),
            StatementParser(),
            terminal(TokenType.ELSE),
            StatementParser()
        )) {
            ConditionNode(it[0], it[2], it[4], it[5], it[6])
        }

        return oneOf(listOf(
            ifElseStatementParser,
            ifStatementParser
        )).parse(input)
    }
}