package dsl.ast.model.entity

import dsl.ast.model.enumeration.ParsingResult
import dsl.ast.model.node.Node

data class ParserOutput private constructor(val result: ParsingResult, val node: Node) {
    fun map(mapper: (Node) -> Node): ParserOutput {
        if(result == ParsingResult.FAILED) {
            throw RuntimeException("Mapping operation is not allowed for failed parsing output")
        }

        return ok(mapper(node))
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