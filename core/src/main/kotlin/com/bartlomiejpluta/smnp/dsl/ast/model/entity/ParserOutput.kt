package com.bartlomiejpluta.smnp.dsl.ast.model.entity

import com.bartlomiejpluta.smnp.dsl.ast.model.enumeration.ParsingResult
import com.bartlomiejpluta.smnp.dsl.ast.model.node.Node

class ParserOutput private constructor(val result: ParsingResult, val node: Node) {
    fun map(mapper: (Node) -> Node): ParserOutput {
        return if(result == ParsingResult.OK) ok(mapper(node)) else fail()
    }

    companion object {
        fun ok(node: Node): ParserOutput {
            return ParserOutput(ParsingResult.OK, node)
        }

        fun fail(): ParserOutput {
            return ParserOutput(ParsingResult.FAILED, Node.NONE)
        }
    }
}