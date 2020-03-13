package io.smnp.dsl.ast.model.node

import io.smnp.dsl.token.model.entity.TokenPosition

class ImportNode(path: List<Node>, position: TokenPosition) : Node(path, position) {
    val path: List<Node>
    get() = children
}