package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.token.model.entity.TokenList

class ComplexIdentifierParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return oneOf(
            AssignmentOperatorParser(),
            FunctionCallParser(),
            SimpleIdentifierParser()
        ).parse(input)
    }
}