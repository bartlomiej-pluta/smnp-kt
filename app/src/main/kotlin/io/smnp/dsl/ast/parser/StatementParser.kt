package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.entity.ParserOutput
import io.smnp.dsl.token.model.entity.TokenList
import io.smnp.dsl.token.model.enumeration.TokenType

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