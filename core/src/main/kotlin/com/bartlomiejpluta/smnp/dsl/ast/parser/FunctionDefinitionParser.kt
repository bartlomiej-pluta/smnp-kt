package com.bartlomiejpluta.smnp.dsl.ast.parser

import com.bartlomiejpluta.smnp.dsl.ast.model.entity.ParserOutput
import com.bartlomiejpluta.smnp.dsl.ast.model.node.FunctionDefinitionNode
import com.bartlomiejpluta.smnp.dsl.token.model.entity.TokenList
import com.bartlomiejpluta.smnp.dsl.token.model.enumeration.TokenType

class FunctionDefinitionParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return allOf(
            terminal(TokenType.FUNCTION),
            assert(SimpleIdentifierParser(), "function/method name"),
            assert(FunctionDefinitionArgumentsParser(), "function/method arguments list"),
            assert(BlockParser(), "function/method body")
        ) { (functionToken, identifier, arguments, body) ->
            FunctionDefinitionNode(identifier, arguments, body, functionToken.position)
        }.parse(input)
    }
}