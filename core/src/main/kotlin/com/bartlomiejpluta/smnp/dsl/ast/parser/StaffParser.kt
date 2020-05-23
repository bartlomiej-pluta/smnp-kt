package com.bartlomiejpluta.smnp.dsl.ast.parser

import com.bartlomiejpluta.smnp.dsl.ast.model.node.StaffNode
import com.bartlomiejpluta.smnp.dsl.token.model.enumeration.TokenType

class StaffParser : AbstractIterableParser(
   TokenType.DOLLAR,
   MeasureParser(),
   TokenType.DOUBLE_PIPE,
   TokenType.PIPE,
   { items, position -> StaffNode(items, position) }
)