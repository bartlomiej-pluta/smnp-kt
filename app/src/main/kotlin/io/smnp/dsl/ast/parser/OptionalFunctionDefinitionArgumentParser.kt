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
                TypeParser()
            ) { it[1] }) { UnionTypeNode(emptyList(), TokenPosition.NONE) },
            terminal(TokenType.ASSIGN),
            assert(ExpressionParser(), "expression")
        ) {
            OptionalFunctionDefinitionArgumentNode(it[0], it[1], it[3])
        }.parse(input)
    }
}