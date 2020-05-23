package com.bartlomiejpluta.smnp.dsl.ast.parser

import com.bartlomiejpluta.smnp.dsl.ast.model.entity.ParserOutput
import com.bartlomiejpluta.smnp.dsl.ast.model.node.FunctionCallNode
import com.bartlomiejpluta.smnp.dsl.token.model.entity.TokenList

class FunctionCallParser : Parser() {
   override fun tryToParse(input: TokenList): ParserOutput {
      return allOf(
         SimpleIdentifierParser(),
         FunctionCallArgumentsParser()
      ) { (identifier, arguments) -> FunctionCallNode(identifier, arguments) }.parse(input)
   }
}