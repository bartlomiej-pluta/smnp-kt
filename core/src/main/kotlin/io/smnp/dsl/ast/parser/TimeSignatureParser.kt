package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.entity.ParserOutput
import io.smnp.dsl.ast.model.node.TimeSignatureNode
import io.smnp.dsl.token.model.entity.TokenList
import io.smnp.dsl.token.model.enumeration.TokenType

class TimeSignatureParser : Parser() {

   override fun tryToParse(input: TokenList): ParserOutput {
      return allOf(
         IntegerLiteralParser(),
         terminal(TokenType.SLASH),
         IntegerLiteralParser()
      ) { (numerator, _, denominator) -> TimeSignatureNode(numerator, denominator) }.parse(input)
   }
}