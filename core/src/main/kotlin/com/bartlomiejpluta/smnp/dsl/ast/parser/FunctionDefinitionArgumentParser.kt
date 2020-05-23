package com.bartlomiejpluta.smnp.dsl.ast.parser

import com.bartlomiejpluta.smnp.dsl.ast.model.entity.ParserOutput
import com.bartlomiejpluta.smnp.dsl.token.model.entity.TokenList

class FunctionDefinitionArgumentParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return assert(oneOf(
            OptionalFunctionDefinitionArgumentParser(),
            RegularFunctionDefinitionArgumentParser()
        ), "optional or regular argument definition").parse(input)
    }
}