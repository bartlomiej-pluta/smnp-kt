package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.entity.ParserOutput
import io.smnp.dsl.token.model.entity.TokenList
import io.smnp.dsl.token.model.enumeration.TokenType

class AtomParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        val parenthesesParser = allOf(
            terminal(TokenType.OPEN_PAREN),
            ExpressionParser(),
            terminal(TokenType.CLOSE_PAREN)
        ) { (_, expression) -> expression }

        val literalParser = oneOf(
            parenthesesParser,
            ComplexIdentifierParser(),
            ListParser(),
            LiteralParser(),
            MapParser()
        )

        return literalParser.parse(input)
    }
}