package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.ast.model.node.ImportNode
import dsl.token.model.entity.TokenList
import dsl.token.model.enumeration.TokenType

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