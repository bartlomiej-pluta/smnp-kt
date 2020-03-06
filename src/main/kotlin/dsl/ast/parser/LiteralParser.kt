package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.token.model.entity.TokenList

class LiteralParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return oneOf(
            BoolLiteralParser(),
            FloatLiteralParser(),
            IntegerLiteralParser(),
            NoteLiteralParser(),
            StringLiteralParser()
        ).parse(input)
    }
}