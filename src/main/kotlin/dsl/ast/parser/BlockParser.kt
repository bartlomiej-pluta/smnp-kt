package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.ast.model.node.BlockNode
import dsl.token.model.entity.TokenList
import dsl.token.model.enumeration.TokenType

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