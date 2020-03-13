package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.entity.ParserOutput
import io.smnp.dsl.ast.model.node.Node
import io.smnp.dsl.token.model.entity.TokenList
import io.smnp.dsl.token.model.entity.TokenPosition
import io.smnp.dsl.token.model.enumeration.TokenType

abstract class AbstractIterableParser(
   private val beginTokenType: TokenType,
   private val itemParser: Parser,
   private val endTokenType: TokenType,
   private val createNode: (List<Node>, TokenPosition) -> Node
) : Parser() {
   override fun tryToParse(input: TokenList): ParserOutput {
      val emptyIterableParser = allOf(
         terminal(beginTokenType),
         terminal(endTokenType)
      ) { (beginToken) -> createNode(listOf(), beginToken.position) }

      val nonEmptyIterableParser = allOf(
         terminal(beginTokenType),
         allOf(
            itemParser,
            optional(repeat(allOf(
               terminal(TokenType.COMMA),
               itemParser
            ) { (_, item) -> item }) { list, position -> object : Node(list, position) {} }
            )) { (firstItem, otherAggregatedItems) ->
            object : Node(listOf(firstItem) + otherAggregatedItems.children, TokenPosition.NONE) {}
         },
         terminal(endTokenType)
      ) { (beginToken, aggregatedItems) -> createNode(aggregatedItems.children.toList(), beginToken.position) }

      return oneOf(
         emptyIterableParser,
         nonEmptyIterableParser
      ).parse(input)
   }

}