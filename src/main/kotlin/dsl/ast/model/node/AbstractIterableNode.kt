package dsl.ast.model.node

import dsl.token.model.entity.TokenPosition

abstract class AbstractIterableNode(items: List<Node>, position: TokenPosition) : Node(items, position)