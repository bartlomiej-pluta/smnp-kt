package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.token.model.entity.TokenList

class ExpressionParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return oneOf(
            LoopParser(),
            SubexpressionParser()
        ).parse(input)
    }
}