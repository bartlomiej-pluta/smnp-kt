package com.bartlomiejpluta.smnp.dsl.ast.parser

import com.bartlomiejpluta.smnp.dsl.ast.model.entity.ParserOutput
import com.bartlomiejpluta.smnp.dsl.ast.model.node.SingleTypeNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.UnionTypeNode
import com.bartlomiejpluta.smnp.dsl.token.model.entity.TokenList

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