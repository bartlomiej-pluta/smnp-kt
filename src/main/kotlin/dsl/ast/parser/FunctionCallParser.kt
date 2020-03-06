package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.ast.model.node.FunctionCallNode
import dsl.token.model.entity.TokenList

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