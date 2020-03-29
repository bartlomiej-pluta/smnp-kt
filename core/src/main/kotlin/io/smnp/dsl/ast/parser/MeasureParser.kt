package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.entity.ParserOutput
import io.smnp.dsl.ast.model.node.MeasureNode
import io.smnp.dsl.token.model.entity.TokenList

class MeasureParser : Parser() {

   override fun tryToParse(input: TokenList): ParserOutput {
      return repeat(
         oneOf(
            TimeSignatureParser(),
            ExpressionParser()
            )
      ) { items, position -> MeasureNode(items, position) }.parse(input)
   }
}