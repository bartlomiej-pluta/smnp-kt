package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.entity.ParserOutput
import io.smnp.dsl.ast.model.node.RootNode
import io.smnp.dsl.token.model.entity.TokenList

class RootParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return repeat(
            oneOf(
                ImportParser(),
                FunctionDefinitionParser(),
                ExtendParser(),
                StatementParser()
            )
        ) { list, tokenPosition ->
            RootNode(list, tokenPosition)
        }.parse(input)
    }
}