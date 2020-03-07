package io.smnp.dsl.ast.model.node

import io.smnp.dsl.token.model.entity.TokenPosition

class ListNode(items: List<Node>, position: TokenPosition) : AbstractIterableNode(items, position)