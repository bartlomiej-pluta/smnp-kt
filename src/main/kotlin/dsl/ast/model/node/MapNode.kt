package dsl.ast.model.node

import dsl.token.model.entity.TokenPosition

class MapNode(items: List<Node>, position: TokenPosition) : AbstractIterableNode(items, position)