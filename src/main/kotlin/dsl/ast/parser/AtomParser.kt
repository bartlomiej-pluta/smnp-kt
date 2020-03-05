package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.token.model.entity.TokenList

class AtomParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        val literalParser = oneOf(
            listOf(
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