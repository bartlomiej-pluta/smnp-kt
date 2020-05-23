package com.bartlomiejpluta.smnp.dsl.ast.parser

import com.bartlomiejpluta.smnp.dsl.ast.model.entity.ParserOutput
import com.bartlomiejpluta.smnp.dsl.ast.model.node.BlockNode
import com.bartlomiejpluta.smnp.dsl.token.model.entity.TokenList
import com.bartlomiejpluta.smnp.dsl.token.model.enumeration.TokenType

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