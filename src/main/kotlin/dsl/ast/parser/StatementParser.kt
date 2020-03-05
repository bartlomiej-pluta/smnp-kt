package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.token.model.entity.TokenList
import dsl.token.model.enumeration.TokenType

class StatementParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return allOf(
            listOf(
                oneOf(
                    listOf(
                        ConditionParser(),
                        ExpressionParser(),
                        BlockParser(),
                        ReturnParser(),
                        ThrowParser()
                    )
                ),
                optional(terminal(TokenType.SEMICOLON))
            )
        ) {
            it[0]
        }.parse(input)
    }
}