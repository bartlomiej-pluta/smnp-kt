package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.ast.model.node.OptionalFunctionDefinitionArgumentNode
import dsl.token.model.entity.TokenList
import dsl.token.model.enumeration.TokenType

class OptionalFunctionDefinitionArgumentParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return allOf(listOf(
            SimpleIdentifierParser(),
            optional(allOf(listOf(
                terminal(TokenType.COLON),
                TypeParser()
            )){ it[1] }),
            terminal(TokenType.ASSIGN),
            assert(ExpressionParser(), "expression")
        )) {
            OptionalFunctionDefinitionArgumentNode(it[0], it[1], it[3])
        }.parse(input)
    }
}