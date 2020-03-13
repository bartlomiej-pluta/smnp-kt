package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.entity.ParserOutput
import io.smnp.dsl.ast.model.node.NotOperatorNode
import io.smnp.dsl.ast.model.node.PowerOperatorNode
import io.smnp.dsl.token.model.entity.TokenList
import io.smnp.dsl.token.model.enumeration.TokenType

class FactorParser : Parser() {
   override fun tryToParse(input: TokenList): ParserOutput {
      val factorParser = leftAssociativeOperator(
         UnitParser(),
         listOf(TokenType.DOUBLE_ASTERISK),
         assert(UnitParser(), "expression")
      ) { lhs, operator, rhs -> PowerOperatorNode(lhs, operator, rhs) }

      val notOperatorParser = allOf(
         terminal(TokenType.NOT),
         assert(factorParser, "expression")
      ) { (notToken, expression) -> NotOperatorNode(notToken, expression) }

      return oneOf(
         notOperatorParser,
         factorParser
      ).parse(input)
   }
}