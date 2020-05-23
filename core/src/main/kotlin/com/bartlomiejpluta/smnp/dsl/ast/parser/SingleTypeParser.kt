package com.bartlomiejpluta.smnp.dsl.ast.parser

import com.bartlomiejpluta.smnp.dsl.ast.model.entity.ParserOutput
import com.bartlomiejpluta.smnp.dsl.ast.model.node.SingleTypeNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.TypeSpecifiersNode
import com.bartlomiejpluta.smnp.dsl.token.model.entity.TokenList

class SingleTypeParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return allOf(
            SimpleIdentifierParser(),
            optional(repeat(TypeSpecifierParser()) { list, tokenPosition -> TypeSpecifiersNode(list, tokenPosition) })
        ) { (identifier, specifiers) -> SingleTypeNode(identifier, specifiers) }.parse(input)
    }
}