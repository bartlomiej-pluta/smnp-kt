package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.token.model.entity.TokenList

class FunctionDefinitionArgumentParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return oneOf(
            OptionalFunctionDefinitionArgumentParser(),
            RegularFunctionDefinitionArgumentParser()
        ).parse(input)
    }
}