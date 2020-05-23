package com.bartlomiejpluta.smnp.dsl.ast.parser

import com.bartlomiejpluta.smnp.dsl.ast.model.entity.ParserOutput
import com.bartlomiejpluta.smnp.dsl.ast.model.node.AccessOperatorNode
import com.bartlomiejpluta.smnp.dsl.ast.model.node.MinusOperatorNode
import com.bartlomiejpluta.smnp.dsl.token.model.entity.TokenList
import com.bartlomiejpluta.smnp.dsl.token.model.enumeration.TokenType

class UnitParser : Parser() {
   override fun tryToParse(input: TokenList): ParserOutput {
      val minusOperatorParser = allOf(
         terminal(TokenType.MINUS),
         assert(AtomParser(), "expression")
      ) { (minusToken, expression) -> MinusOperatorNode(minusToken, expression) }

      val atom2 = oneOf(
         minusOperatorParser,
         AtomParser()
      )

      return leftAssociativeOperator(
         atom2,
         listOf(TokenType.DOT),
         assert(atom2, "property or method call")
      ) { lhs, operator, rhs -> AccessOperatorNode(lhs, operator, rhs) }.parse(input)
   }
}