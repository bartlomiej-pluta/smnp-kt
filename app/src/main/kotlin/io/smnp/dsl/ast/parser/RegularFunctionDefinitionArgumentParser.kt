package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.entity.ParserOutput
import io.smnp.dsl.ast.model.node.RegularFunctionDefinitionArgumentNode
import io.smnp.dsl.ast.model.node.UnionTypeNode
import io.smnp.dsl.token.model.entity.TokenList
import io.smnp.dsl.token.model.entity.TokenPosition
import io.smnp.dsl.token.model.enumeration.TokenType

class RegularFunctionDefinitionArgumentParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return allOf(
            optional(terminal(TokenType.DOTS)),
            SimpleIdentifierParser(),
            optional(allOf(
                terminal(TokenType.COLON),
                TypeParser()
            ) { it[1] }) { UnionTypeNode(emptyList(), TokenPosition.NONE) }
        ) { (vararg, identifier, type) ->
            RegularFunctionDefinitionArgumentNode(identifier, type, vararg)
        }.parse(input)
    }
}