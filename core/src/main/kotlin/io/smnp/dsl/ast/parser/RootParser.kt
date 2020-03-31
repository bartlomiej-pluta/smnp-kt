package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.entity.ParserOutput
import io.smnp.dsl.ast.model.enumeration.ParsingResult
import io.smnp.dsl.ast.model.node.Node
import io.smnp.dsl.ast.model.node.RootNode
import io.smnp.dsl.token.model.entity.TokenList
import io.smnp.dsl.token.model.entity.TokenPosition
import io.smnp.dsl.token.model.enumeration.TokenType

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