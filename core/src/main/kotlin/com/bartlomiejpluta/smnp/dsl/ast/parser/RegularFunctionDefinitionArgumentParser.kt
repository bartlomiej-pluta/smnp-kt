package com.bartlomiejpluta.smnp.dsl.ast.parser

import com.bartlomiejpluta.smnp.dsl.ast.model.entity.ParserOutput
import com.bartlomiejpluta.smnp.dsl.ast.model.node.RegularFunctionDefinitionArgumentNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.UnionTypeNode
import com.bartlomiejpluta.smnp.dsl.token.model.entity.TokenList
import com.bartlomiejpluta.smnp.dsl.token.model.entity.TokenPosition
import com.bartlomiejpluta.smnp.dsl.token.model.enumeration.TokenType

class RegularFunctionDefinitionArgumentParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return allOf(
            optional(terminal(TokenType.DOTS)),
            SimpleIdentifierParser(),
            optional(allOf(
                terminal(TokenType.COLON),
                assert(TypeParser(), "type name")
            ) { (_, type) -> type }) { UnionTypeNode(emptyList(), TokenPosition.NONE) }
        ) { (vararg, identifier, type) ->
            RegularFunctionDefinitionArgumentNode(identifier, type, vararg)
        }.parse(input)
    }
}