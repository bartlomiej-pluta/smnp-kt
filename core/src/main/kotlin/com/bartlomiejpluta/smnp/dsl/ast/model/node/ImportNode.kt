package com.bartlomiejpluta.smnp.dsl.ast.model.node

import com.bartlomiejpluta.smnp.dsl.token.model.entity.TokenPosition

class ImportNode(path: List<Node>, position: TokenPosition) : Node(path, position) {
    val path: List<Node>
    get() = children
}