package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.ast.model.node.RootNode
import dsl.token.model.entity.TokenList

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