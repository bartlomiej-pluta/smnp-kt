package com.bartlomiejpluta.smnp.dsl.ast.parser

import com.bartlomiejpluta.smnp.dsl.ast.model.node.FunctionDefinitionArgumentsNode
import com.bartlomiejpluta.smnp.dsl.token.model.enumeration.TokenType

class FunctionDefinitionArgumentsParser : AbstractIterableParser(
   TokenType.OPEN_PAREN,
   FunctionDefinitionArgumentParser(),
   TokenType.CLOSE_PAREN,
   createNode = { list, tokenPosition ->
      FunctionDefinitionArgumentsNode(list, tokenPosition)
   })