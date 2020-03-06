package dsl.ast.parser

import dsl.ast.model.entity.ParserOutput
import dsl.ast.model.node.TypeNode
import dsl.ast.model.node.TypeSpecifiersNode
import dsl.token.model.entity.TokenList

class TypeParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return allOf(listOf(
            SimpleIdentifierParser(),
            optional(repeat(TypeSpecifierParser()) { list, tokenPosition -> TypeSpecifiersNode(list,  tokenPosition) })
        )) {
            TypeNode(it[0], it[1])
        }.parse(input)
    }
}