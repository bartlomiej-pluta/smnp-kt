package com.bartlomiejpluta.smnp.dsl.ast.parser

import com.bartlomiejpluta.smnp.dsl.ast.model.entity.ParserOutput
import com.bartlomiejpluta.smnp.dsl.ast.model.node.ThrowNode
import com.bartlomiejpluta.smnp.dsl.token.model.entity.TokenList
import com.bartlomiejpluta.smnp.dsl.token.model.enumeration.TokenType

class ThrowParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return allOf(
            terminal(TokenType.THROW),
            optional(SubexpressionParser())
        ) { (_, value) -> ThrowNode(value) }.parse(input)
    }
}