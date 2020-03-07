package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.entity.ParserOutput
import io.smnp.dsl.ast.model.node.FunctionCallNode
import io.smnp.dsl.token.model.entity.TokenList

class FunctionCallParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return allOf(
            SimpleIdentifierParser(),
            FunctionCallArgumentsParser()
        ) {
            FunctionCallNode(it[0], it[1])
        }.parse(input)
    }
}