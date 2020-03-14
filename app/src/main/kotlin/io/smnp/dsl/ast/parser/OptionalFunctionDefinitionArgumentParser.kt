package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.entity.ParserOutput
import io.smnp.dsl.ast.model.node.OptionalFunctionDefinitionArgumentNode
import io.smnp.dsl.ast.model.node.UnionTypeNode
import io.smnp.dsl.token.model.entity.TokenList
import io.smnp.dsl.token.model.entity.TokenPosition
import io.smnp.dsl.token.model.enumeration.TokenType

class OptionalFunctionDefinitionArgumentParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return allOf(
            SimpleIdentifierParser(),
            optional(allOf(
                terminal(TokenType.COLON),
                assert(TypeParser(), "type name")
            ) { (_, type) -> type }) { UnionTypeNode(emptyList(), TokenPosition.NONE) },
            terminal(TokenType.ASSIGN),
            assert(ExpressionParser(), "expression")
        ) { (identifier, type, _, defaultValue) ->
            OptionalFunctionDefinitionArgumentNode(identifier, type, defaultValue)
        }.parse(input)
    }
}