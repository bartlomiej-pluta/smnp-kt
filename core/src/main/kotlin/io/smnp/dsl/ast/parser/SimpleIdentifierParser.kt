package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.entity.ParserOutput
import io.smnp.dsl.ast.model.node.IdentifierNode
import io.smnp.dsl.token.model.entity.TokenList
import io.smnp.dsl.token.model.enumeration.TokenType

class SimpleIdentifierParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        val identifierParser = terminal(TokenType.IDENTIFIER) { IdentifierNode(it) }

        return oneOf(
            identifierParser
        ).parse(input)
    }
}