package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.entity.ParserOutput
import io.smnp.dsl.ast.model.node.ConditionNode
import io.smnp.dsl.ast.model.node.Node
import io.smnp.dsl.token.model.entity.TokenList
import io.smnp.dsl.token.model.enumeration.TokenType

class ConditionParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        val ifStatementParser = allOf(
            terminal(TokenType.IF),
            terminal(TokenType.OPEN_PAREN),
            SubexpressionParser(),
            terminal(TokenType.CLOSE_PAREN),
            StatementParser()
        ) {
            ConditionNode(it[0], it[2], it[4], Node.NONE, Node.NONE)
        }

        val ifElseStatementParser = allOf(
            terminal(TokenType.IF),
            terminal(TokenType.OPEN_PAREN),
            SubexpressionParser(),
            terminal(TokenType.CLOSE_PAREN),
            StatementParser(),
            terminal(TokenType.ELSE),
            StatementParser()
        ) {
            ConditionNode(it[0], it[2], it[4], it[5], it[6])
        }

        return oneOf(
            ifElseStatementParser,
            ifStatementParser
        ).parse(input)
    }
}