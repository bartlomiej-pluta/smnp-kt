package com.bartlomiejpluta.smnp.dsl.ast.parser

import com.bartlomiejpluta.smnp.dsl.ast.model.entity.ParserOutput
import com.bartlomiejpluta.smnp.dsl.token.model.entity.TokenList
import com.bartlomiejpluta.smnp.dsl.token.model.enumeration.TokenType

class StatementParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return allOf(
            oneOf(
                ConditionParser(),
                ExpressionParser(),
                BlockParser(),
                ReturnParser(),
                ThrowParser()
            ),
            optional(terminal(TokenType.SEMICOLON))
        ) { (statement) -> statement }.parse(input)
    }
}