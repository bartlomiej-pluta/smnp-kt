package com.bartlomiejpluta.smnp.dsl.ast.parser

import com.bartlomiejpluta.smnp.dsl.ast.model.entity.ParserOutput
import com.bartlomiejpluta.smnp.dsl.ast.model.enumeration.ParsingResult
import com.bartlomiejpluta.smnp.dsl.ast.model.node.Node
import com.bartlomiejpluta.smnp.dsl.ast.model.node.RootNode
import com.bartlomiejpluta.smnp.dsl.token.model.entity.TokenList
import com.bartlomiejpluta.smnp.dsl.token.model.entity.TokenPosition
import com.bartlomiejpluta.smnp.dsl.token.model.enumeration.TokenType

class RootParser : Parser() {
   override fun tryToParse(input: TokenList): ParserOutput {
      val parser = assert(
         oneOf(
            allOf(ImportParser(), optional(terminal(TokenType.SEMICOLON))) { (import, _) -> import },
            FunctionDefinitionParser(),
            ExtendParser(),
            StatementParser()
         ), "import statement, function definition, extend statement or any other statement"
      )

      val items = mutableListOf<Node>()
      while (input.hasMore()) {
         parser.parse(input).let {
            if (it.result == ParsingResult.OK) {
               items += it.node
            }
         }
      }

      return ParserOutput.ok(RootNode(items, items.firstOrNull()?.position ?: TokenPosition.NONE))
   }
}