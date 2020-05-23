package com.bartlomiejpluta.smnp.dsl.ast.parser

import com.bartlomiejpluta.smnp.dsl.ast.model.entity.ParserOutput
import com.bartlomiejpluta.smnp.dsl.ast.model.node.IdentifierNode
import com.bartlomiejpluta.smnp.dsl.token.model.entity.TokenList
import com.bartlomiejpluta.smnp.dsl.token.model.enumeration.TokenType

class SimpleIdentifierParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        val identifierParser = terminal(TokenType.IDENTIFIER) { IdentifierNode(it) }

        return oneOf(
            identifierParser
        ).parse(input)
    }
}