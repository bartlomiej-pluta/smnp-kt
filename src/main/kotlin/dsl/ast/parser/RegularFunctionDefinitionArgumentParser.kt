package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.ast.model.node.RegularFunctionDefinitionArgumentNode
import dsl.token.model.entity.TokenList
import dsl.token.model.enumeration.TokenType

class RegularFunctionDefinitionArgumentParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return allOf(
            optional(terminal(TokenType.DOTS)),
            SimpleIdentifierParser(),
            optional(allOf(
                terminal(TokenType.COLON),
                TypeParser()
            ) { it[1] })
        ) {
            RegularFunctionDefinitionArgumentNode(it[1], it[2], it[0])
        }.parse(input)
    }
}