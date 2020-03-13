package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.entity.ParserOutput
import io.smnp.dsl.ast.model.node.LoopNode
import io.smnp.dsl.ast.model.node.LoopParametersNode
import io.smnp.dsl.token.model.entity.TokenList
import io.smnp.dsl.token.model.enumeration.TokenType

class LoopParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        val loopParametersParser = allOf(
            terminal(TokenType.AS),
            assert(oneOf(
                mapNode(SimpleIdentifierParser()) { LoopParametersNode(listOf(it), it.position) },
                LoopParametersParser()
            ), "loop parameters")
        ) { it[1] }

        val loopFilterParser = allOf(
            terminal(TokenType.PERCENT),
            assert(SubexpressionParser(), "filter as bool expression")
        ) { it[1] }

        return allOf(
            SubexpressionParser(),
            optional(loopParametersParser),
            terminal(TokenType.CARET),
            assert(StatementParser(), "statement"),
            optional(loopFilterParser)
        ) {
            LoopNode(it[0], it[1], it[2], it[3], it[4])
        }.parse(input)
    }
}