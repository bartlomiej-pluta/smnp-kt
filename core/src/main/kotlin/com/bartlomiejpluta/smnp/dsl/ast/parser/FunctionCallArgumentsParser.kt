package com.bartlomiejpluta.smnp.dsl.ast.parser

import com.bartlomiejpluta.smnp.dsl.ast.model.node.FunctionCallArgumentsNode
import com.bartlomiejpluta.smnp.dsl.token.model.enumeration.TokenType

class FunctionCallArgumentsParser :
   AbstractIterableParser(
      TokenType.OPEN_PAREN,
      ExpressionParser(),
      TokenType.CLOSE_PAREN,
      createNode = { list, tokenPosition ->
         FunctionCallArgumentsNode(list, tokenPosition)
      })