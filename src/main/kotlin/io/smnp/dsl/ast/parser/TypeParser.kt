package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.entity.ParserOutput
import io.smnp.dsl.ast.model.node.TypeNode
import io.smnp.dsl.ast.model.node.TypeSpecifiersNode
import io.smnp.dsl.token.model.entity.TokenList

class TypeParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return allOf(
            SimpleIdentifierParser(),
            optional(repeat(TypeSpecifierParser()) { list, tokenPosition -> TypeSpecifiersNode(list, tokenPosition) })
        ) {
            TypeNode(it[0], it[1])
        }.parse(input)
    }
}