package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.entity.ParserOutput
import io.smnp.dsl.token.model.entity.TokenList

class ComplexIdentifierParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return oneOf(
            AssignmentOperatorParser(),
            FunctionCallParser(),
            SimpleIdentifierParser()
        ).parse(input)
    }
}