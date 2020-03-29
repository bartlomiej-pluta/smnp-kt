package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.entity.ParserOutput
import io.smnp.dsl.ast.model.node.RootNode
import io.smnp.dsl.token.model.entity.TokenList
import io.smnp.dsl.token.model.enumeration.TokenType

class RootParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return assert(repeat(
            oneOf(
                allOf(ImportParser(), optional(terminal(TokenType.SEMICOLON))) { (import, _) -> import },
                FunctionDefinitionParser(),
                ExtendParser(),
                StatementParser()
            )
        ) { list, tokenPosition ->
            RootNode(list, tokenPosition)
        }, "import statement, function definition, extend statement or any other statement").parse(input)
    }
}