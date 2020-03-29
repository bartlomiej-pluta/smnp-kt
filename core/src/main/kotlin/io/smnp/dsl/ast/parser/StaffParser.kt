package io.smnp.dsl.ast.parser

import io.smnp.dsl.ast.model.node.StaffNode
import io.smnp.dsl.token.model.enumeration.TokenType

class StaffParser : AbstractIterableParser(
   TokenType.DOLLAR,
   MeasureParser(),
   TokenType.DOUBLE_PIPE,
   TokenType.PIPE,
   { items, position -> StaffNode(items, position) }
)