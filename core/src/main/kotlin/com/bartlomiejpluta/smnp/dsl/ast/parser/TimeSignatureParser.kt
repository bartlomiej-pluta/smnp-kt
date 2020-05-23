package com.bartlomiejpluta.smnp.dsl.ast.parser

import com.bartlomiejpluta.smnp.dsl.ast.model.entity.ParserOutput
import com.bartlomiejpluta.smnp.dsl.ast.model.node.TimeSignatureNode
import com.bartlomiejpluta.smnp.dsl.token.model.entity.TokenList
import com.bartlomiejpluta.smnp.dsl.token.model.enumeration.TokenType

class TimeSignatureParser : Parser() {

   override fun tryToParse(input: TokenList): ParserOutput {
      return allOf(
         IntegerLiteralParser(),
         terminal(TokenType.SLASH),
         IntegerLiteralParser()
      ) { (numerator, _, denominator) -> TimeSignatureNode(numerator, denominator) }.parse(input)
   }
}