package com.bartlomiejpluta.smnp.dsl.ast.parser

import com.bartlomiejpluta.smnp.dsl.ast.model.entity.ParserOutput
import com.bartlomiejpluta.smnp.dsl.ast.model.node.ConditionNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.Node
import com.bartlomiejpluta.smnp.dsl.token.model.entity.TokenList
import com.bartlomiejpluta.smnp.dsl.token.model.enumeration.TokenType

class ConditionParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        val ifStatementParser = allOf(
            terminal(TokenType.IF),
            terminal(TokenType.OPEN_PAREN),
            assert(SubexpressionParser(), "expression"),
            terminal(TokenType.CLOSE_PAREN),
            assert(StatementParser(), "statement")
        ) { (ifToken, _, condition, _, trueBranch) ->
            ConditionNode(ifToken, condition, trueBranch, Node.NONE, Node.NONE)
        }

        val ifElseStatementParser = allOf(
            terminal(TokenType.IF),
            terminal(TokenType.OPEN_PAREN),
            assert(SubexpressionParser(), "expression"),
            terminal(TokenType.CLOSE_PAREN),
            assert(StatementParser(), "statement"),
            terminal(TokenType.ELSE),
            assert(StatementParser(), "statement")
        ) { (ifToken, _, condition, _, trueBranch, elseToken, falseBranch) ->
            ConditionNode(ifToken, condition, trueBranch, elseToken, falseBranch)
        }

        return oneOf(
            ifElseStatementParser,
            ifStatementParser
        ).parse(input)
    }
}

// It is required for destructing list of nodes in ifElseStatementParser object
private operator fun <E> List<E>.component6() = this[5];
private operator fun <E> List<E>.component7() = this[6];
