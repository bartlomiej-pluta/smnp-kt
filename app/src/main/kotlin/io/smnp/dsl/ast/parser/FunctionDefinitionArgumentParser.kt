package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.entity.ParserOutput
import io.smnp.dsl.token.model.entity.TokenList

class FunctionDefinitionArgumentParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return oneOf(
            OptionalFunctionDefinitionArgumentParser(),
            RegularFunctionDefinitionArgumentParser()
        ).parse(input)
    }
}