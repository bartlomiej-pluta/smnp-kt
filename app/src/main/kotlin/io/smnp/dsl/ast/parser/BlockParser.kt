package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.entity.ParserOutput
import io.smnp.dsl.ast.model.node.BlockNode
import io.smnp.dsl.token.model.entity.TokenList
import io.smnp.dsl.token.model.enumeration.TokenType

class BlockParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return loop(
            terminal(TokenType.OPEN_CURLY),
            assert(StatementParser(), "statement or }"),
            terminal(TokenType.CLOSE_CURLY)
        ) { begin, list, end ->
            BlockNode(begin, list, end)
        }.parse(input)
    }
}