package com.bartlomiejpluta.smnp.dsl.ast.parser

import com.bartlomiejpluta.smnp.dsl.ast.model.entity.ParserOutput
import com.bartlomiejpluta.smnp.dsl.ast.model.node.MeasureNode
import com.bartlomiejpluta.smnp.dsl.token.model.entity.TokenList

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