package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.entity.ParserOutput
import io.smnp.dsl.ast.model.node.MapEntryNode
import io.smnp.dsl.token.model.entity.TokenList
import io.smnp.dsl.token.model.enumeration.TokenType

class MapEntryParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        val keyParser = oneOf(
            LiteralParser(),
            SimpleIdentifierParser()
        )

        return allOf(
            keyParser,
            terminal(TokenType.ARROW),
            assert(SubexpressionParser(), "expression")
        ) { (key, arrowToken, value) -> MapEntryNode(key, arrowToken, value) }.parse(input)
    }
}