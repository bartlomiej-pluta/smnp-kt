package com.bartlomiejpluta.smnp.dsl.ast.parser

import com.bartlomiejpluta.smnp.dsl.ast.model.entity.ParserOutput
import com.bartlomiejpluta.smnp.dsl.ast.model.node.AssignmentOperatorNode
import com.bartlomiejpluta.smnp.dsl.token.model.entity.TokenList
import com.bartlomiejpluta.smnp.dsl.token.model.enumeration.TokenType

class AssignmentOperatorParser : Parser() {
   override fun tryToParse(input: TokenList): ParserOutput {
      return allOf(
         SimpleIdentifierParser(),
         terminal(TokenType.ASSIGN),
         assert(ExpressionParser(), "expression")
      ) { (target, assignToken, value) -> AssignmentOperatorNode(target, assignToken, value) }.parse(input)
   }
}