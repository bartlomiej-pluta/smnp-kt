package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.token.model.entity.TokenList
import dsl.token.model.enumeration.TokenType

class AtomParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        val parenthesesParser = allOf(
            listOf(
                terminal(TokenType.OPEN_PAREN),
                ExpressionParser(),
                terminal(TokenType.CLOSE_PAREN)
            )
        ) {
            it[1]
        }
        val literalParser = oneOf(
            listOf(
                parenthesesParser,
                BoolLiteralParser(),
                FloatLiteralParser(),
                IntegerLiteralParser(),
                NoteLiteralParser(),
                StringLiteralParser()
            )
        )

        return literalParser.parse(input)
    }
}