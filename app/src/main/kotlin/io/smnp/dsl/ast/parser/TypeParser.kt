package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.entity.ParserOutput
import io.smnp.dsl.ast.model.node.SingleTypeNode
import io.smnp.dsl.ast.model.node.UnionTypeNode
import io.smnp.dsl.token.model.entity.TokenList

class TypeParser : Parser() {
    override fun tryToParse(input: TokenList): ParserOutput {
        return mapNode(oneOf(
            SingleTypeParser(),
            UnionTypeParser()
        )) {
            node -> if(node is SingleTypeNode) UnionTypeNode(listOf(node), node.position) else node
        }.parse(input)
    }
}