package com.bartlomiejpluta.smnp.dsl.ast.parser

import com.bartlomiejpluta.smnp.dsl.ast.model.entity.ParserOutput
import com.bartlomiejpluta.smnp.dsl.token.model.entity.TokenList

class ComplexIdentifierParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return oneOf(
            AssignmentOperatorParser(),
            FunctionCallParser(),
            SimpleIdentifierParser()
        ).parse(input)
    }
}