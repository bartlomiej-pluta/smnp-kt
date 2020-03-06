package dsl.ast.model.node

import dsl.token.model.entity.TokenPosition

class ListNode(items: List<Node>, position: TokenPosition) : AbstractIterableNode(items, position)