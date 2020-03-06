package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.token.model.entity.TokenList
import dsl.token.model.enumeration.TokenType

class AtomParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        val parenthesesParser = allOf(
            terminal(TokenType.OPEN_PAREN),
            ExpressionParser(),
            terminal(TokenType.CLOSE_PAREN)
        ) {
            it[1]
        }

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