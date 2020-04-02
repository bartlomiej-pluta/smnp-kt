package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.entity.ParserOutput
import io.smnp.dsl.ast.model.node.BlockNode
import io.smnp.dsl.ast.model.node.ExtendNode
import io.smnp.dsl.ast.model.node.Node
import io.smnp.dsl.token.model.entity.TokenList
import io.smnp.dsl.token.model.enumeration.TokenType

class ExtendParser : Parser() {
   override fun tryToParse(input: TokenList): ParserOutput {
      val simpleExtendParser = allOf(
         terminal(TokenType.EXTEND),
         assert(SingleTypeParser(), "type to be extended"),
         terminal(TokenType.WITH),
         assert(
            mapNode(FunctionDefinitionParser()) { BlockNode(Node.NONE, listOf(it), Node.NONE) },
            "method definition"
         )
      ) { (extendToken, targetType, _, method) ->
         ExtendNode(targetType, method, extendToken.position)
      }

      val complexExtendParser = allOf(
         terminal(TokenType.EXTEND),
         assert(SingleTypeParser(), "type to be extended"),
         assert(
            loop(
               terminal(TokenType.OPEN_CURLY),
               assert(FunctionDefinitionParser(), "method definition or }"),
               terminal(TokenType.CLOSE_CURLY)
            ) { begin, methods, end ->
               BlockNode(begin, methods, end)
            }, "block with methods' definitions or 'with' keyword with single method definition"
         )
      ) { (extendToken, targetType, methods) ->
         ExtendNode(targetType, methods, extendToken.position)
      }

      return oneOf(
         simpleExtendParser,
         complexExtendParser
      ).parse(input)
   }
}