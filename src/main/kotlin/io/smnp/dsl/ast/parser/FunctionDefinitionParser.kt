package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.entity.ParserOutput
import io.smnp.dsl.ast.model.node.FunctionDefinitionNode
import io.smnp.dsl.token.model.entity.TokenList
import io.smnp.dsl.token.model.enumeration.TokenType

class FunctionDefinitionParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return allOf(
            terminal(TokenType.FUNCTION),
            assert(SimpleIdentifierParser(), "function/method name"),
            assert(FunctionDefinitionArgumentsParser(), "function/method arguments list"),
            assert(BlockParser(), "function/method body")
        ) {
            FunctionDefinitionNode(it[1], it[2], it[3], it[0].position)
        }.parse(input)
    }
}