package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.entity.ParserOutput
import io.smnp.dsl.token.model.entity.TokenList

class FunctionDefinitionArgumentParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return assert(oneOf(
            OptionalFunctionDefinitionArgumentParser(),
            RegularFunctionDefinitionArgumentParser()
        ), "optional or regular argument definition").parse(input)
    }
}