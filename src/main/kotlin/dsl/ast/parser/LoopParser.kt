package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.ast.model.node.LoopNode
import dsl.ast.model.node.LoopParametersNode
import dsl.token.model.entity.TokenList
import dsl.token.model.enumeration.TokenType

class LoopParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        val loopParametersParser = allOf(listOf(
            terminal(TokenType.AS),
            oneOf(listOf(
                mapNode(SimpleIdentifierParser()) { LoopParametersNode(listOf(it), it.position) },
                LoopParametersParser()
            ))
        )) { it[1] }

        val loopFilterParser = allOf(listOf(
            terminal(TokenType.PERCENT),
            assert(SubexpressionParser(), "filter as bool expression")
        )) { it[1] }

        return allOf(listOf(
            SubexpressionParser(),
            optional(loopParametersParser),
            terminal(TokenType.CARET),
            StatementParser(),
            optional(loopFilterParser)
        )) {
            LoopNode(it[0], it[1], it[2], it[3], it[4])
        }.parse(input)
    }
}