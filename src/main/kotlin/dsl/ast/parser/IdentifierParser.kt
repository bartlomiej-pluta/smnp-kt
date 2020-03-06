package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.ast.model.node.IdentifierNode
import dsl.token.model.entity.TokenList
import dsl.token.model.enumeration.TokenType

class IdentifierParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        val identifierParser = terminal(TokenType.IDENTIFIER) { IdentifierNode(it) }

        return oneOf(listOf(
            identifierParser
        )).parse(input)
    }
}