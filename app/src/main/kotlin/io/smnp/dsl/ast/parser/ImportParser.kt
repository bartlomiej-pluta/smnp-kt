package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.entity.ParserOutput
import io.smnp.dsl.ast.model.node.ImportNode
import io.smnp.dsl.token.model.entity.TokenList
import io.smnp.dsl.token.model.enumeration.TokenType

class ImportParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        val pathParser = oneOf(
            UnitParser(),
            SimpleIdentifierParser(),
            StringLiteralParser()
        )

        return allOf(
            terminal(TokenType.IMPORT),
            pathParser
        ) {
            ImportNode(it[1], it[0].position)
        }.parse(input)
    }
}