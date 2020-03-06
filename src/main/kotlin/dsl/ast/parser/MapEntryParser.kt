package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.ast.model.node.MapEntryNode
import dsl.token.model.entity.TokenList
import dsl.token.model.enumeration.TokenType

class MapEntryParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        val keyParser = oneOf(listOf(
            LiteralParser(),
            IdentifierParser()
        ))

        return allOf(listOf(
            keyParser,
            terminal(TokenType.ARROW),
            assert(ExpressionParser(), "expression")
        )) {
            MapEntryNode(it[0], it[1], it[2])
        }.parse(input)
    }
}