package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.entity.ParserOutput
import io.smnp.dsl.ast.model.node.LogicOperatorNode
import io.smnp.dsl.ast.model.node.RelationOperatorNode
import io.smnp.dsl.ast.model.node.SumOperatorNode
import io.smnp.dsl.token.model.entity.TokenList
import io.smnp.dsl.token.model.enumeration.TokenType

class SubexpressionParser : Parser() {
   override fun tryToParse(input: TokenList): ParserOutput {
      val expr1Parser = leftAssociativeOperator(
         TermParser(),
         listOf(TokenType.PLUS, TokenType.MINUS),
         assert(TermParser(), "expression")
      ) { lhs, operator, rhs ->
         SumOperatorNode(lhs, operator, rhs)
      }

      val expr2Parser = leftAssociativeOperator(
         expr1Parser,
         listOf(TokenType.RELATION, TokenType.OPEN_ANGLE, TokenType.CLOSE_ANGLE),
         assert(expr1Parser, "expression")
      ) { lhs, operator, rhs ->
         RelationOperatorNode(lhs, operator, rhs)
      }

      val expr3Parser = leftAssociativeOperator(
         expr2Parser,
         listOf(TokenType.AND),
         assert(expr2Parser, "expression")
      ) { lhs, operator, rhs ->
         LogicOperatorNode(lhs, operator, rhs)
      }

      val expr4Parser = leftAssociativeOperator(
         expr3Parser,
         listOf(TokenType.OR),
         assert(expr3Parser, "expression")
      ) { lhs, operator, rhs ->
         LogicOperatorNode(lhs, operator, rhs)
      }

      return expr4Parser.parse(input)
   }
}