package io.smnp.dsl.ast.model.node

import io.smnp.dsl.token.model.entity.TokenPosition

abstract class AbstractIterableNode(items: List<Node>, position: TokenPosition) : Node(items, position) {
    val items: List<Node>
    get() = children
}