package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.entity.ParserOutput
import io.smnp.dsl.ast.model.node.SingleTypeNode
import io.smnp.dsl.ast.model.node.TypeSpecifiersNode
import io.smnp.dsl.token.model.entity.TokenList

class SingleTypeParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return allOf(
            SimpleIdentifierParser(),
            optional(repeat(TypeSpecifierParser()) { list, tokenPosition -> TypeSpecifiersNode(list, tokenPosition) })
        ) { (identifier, specifiers) -> SingleTypeNode(identifier, specifiers) }.parse(input)
    }
}