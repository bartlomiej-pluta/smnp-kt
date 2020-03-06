package dsl.ast.model.node

import dsl.token.model.entity.TokenPosition

class LoopParametersNode(items: List<Node>, position: TokenPosition) : AbstractIterableNode(items, position)