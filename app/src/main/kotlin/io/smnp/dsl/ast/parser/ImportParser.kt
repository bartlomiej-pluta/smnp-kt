package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.entity.ParserOutput
import io.smnp.dsl.ast.model.node.ImportNode
import io.smnp.dsl.ast.model.node.Node
import io.smnp.dsl.token.model.entity.TokenList
import io.smnp.dsl.token.model.enumeration.TokenType

class ImportParser : Parser() {
   override fun tryToParse(input: TokenList): ParserOutput {
      val pathParser = allOf(
         SimpleIdentifierParser(),
         optional(repeat(allOf(
            terminal(TokenType.DOT),
            SimpleIdentifierParser()
         ) { (_, child) -> child }) { segments, position ->
            object : Node(segments, position) {}
         })) { (firstSegment, otherSegments) ->
         object : Node(listOf(firstSegment) + otherSegments.children, firstSegment.position) {}
      }


      return allOf(
         terminal(TokenType.IMPORT),
         assert(pathParser, "module canonical name")
      ) { (import, path) ->
         ImportNode(path.children, import.position)
      }.parse(input)
   }
}